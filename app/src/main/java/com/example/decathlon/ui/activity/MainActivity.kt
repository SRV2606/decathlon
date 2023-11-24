package com.example.decathlon.ui.activity


import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendar.collectEvent
import com.example.data.utlils.NetworkUtil
import com.example.decathlon.R
import com.example.decathlon.base.BaseActivity
import com.example.decathlon.databinding.ActivityMainBinding
import com.example.decathlon.ui.adapters.SkuItemsListAdapter
import com.example.decathlon.viewmodel.MainViewModel
import com.example.domain.domain.models.DecathlonSKUItemBean
import com.example.domain.domain.models.ListSorters
import com.example.domain.models.ClientResult
import dagger.hilt.android.AndroidEntryPoint
import ru.alexbykov.nopaginate.callback.OnLoadMoreListener
import ru.alexbykov.nopaginate.paginate.NoPaginate
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnLoadMoreListener {


    private lateinit var paginate: NoPaginate
    private val mainViewModel by viewModels<MainViewModel>()

    private var page = 1
    private var totalResults: Int? = 0
    private var searchQuery = ""
    private var skuList: MutableList<DecathlonSKUItemBean> = mutableListOf()

    private var currentSelectedChip = ListSorters.NOTHING

    @Inject
    lateinit var networkUtil: NetworkUtil
    private val skuAdapter by lazy {
        SkuItemsListAdapter(itemClickListener = {
            // we can open any other view , or bottomsheet or anythign here if needed with data

        }, context = this@MainActivity)
    }


    override fun readArguments(extras: Intent) {

    }

    override fun setupUi() {
        mainViewModel.getInitialHeroProductsWithPagination(page)
        handleSearch()
        setupRecyclerView()
    }


    private fun handleSearch() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //on submit of the query then hiding keyboard
                    mainViewModel.getFilteredItemsWithPagination(searchQuery = query, page = page)
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchQuery = newText
                    binding.searchView.isSubmitButtonEnabled = true
                }
                if (newText == null || newText.length < 2 || newText.isEmpty()) {
                    //clearing the movie list totally if the conditions meet
                    skuList.clear()
                    page = 1
                }
                return true
            }

        })


    }


    private fun setupRecyclerView() {
        binding.listRV.layoutManager =
            GridLayoutManager(this, 2)
        binding.listRV.adapter = skuAdapter
        paginate = NoPaginate.with(binding.listRV)
            .setLoadingTriggerThreshold(1)
            .setOnLoadMoreListener(this)
            .build()
    }

    override fun observeData() {
        collectEvent(mainViewModel.skuList) {
            when (it) {
                is ClientResult.InProgress -> {
                    if (skuList.isEmpty() && searchQuery.isEmpty()) {
                        //loading screen for first time
                        renderLoadingScreen(true)
                    }
                    updateLoadingStateOfPagination(true)
                    updateErrorStateOfPagination(false)
                }

                is ClientResult.Success -> {
                    if (skuList.isEmpty() && searchQuery.isEmpty()) {
                        renderLoadingScreen(false)
                    }
                    updateLoadingStateOfPagination(false)
                    updateErrorStateOfPagination(false)
                    renderSuccessScreen(it.data)
                }

                is ClientResult.Error -> {
                    updateLoadingStateOfPagination(false)
                    updateErrorStateOfPagination(true)
                    renderErrorScreenWithRetry(it)
                }
            }
        }


    }

    private fun renderSuccessScreen(clientResult: List<DecathlonSKUItemBean>) {
        if (binding.retryLayoutHolder.retryLayoutCL.visibility == View.VISIBLE) {
            binding.retryLayoutHolder.retryLayoutCL.visibility = View.GONE
        }
        if (binding.noMovieFoundHolder.retryLayoutCL.visibility == View.VISIBLE) {
            binding.noMovieFoundHolder.retryLayoutCL.visibility = View.GONE
        }
        if (binding.listRV.visibility == View.GONE) {
            binding.listRV.visibility = View.VISIBLE
        }


        totalResults = clientResult.size
        if (clientResult.isEmpty()) {
            renderNoMovieFoundScreen()
        } else {
//            if something from prev search is still relevent
            if (skuList.isNotEmpty() && searchQuery.isNotEmpty()) {
                skuList.filter { searchBean ->
                    searchBean.name.contains(searchQuery)
                }
            }
            clientResult.forEachIndexed { index, search ->
                skuList.add(search)
            }
            skuAdapter.submitList(skuList.toList())
        }
    }

    override fun setListener() {
        binding.sortByBrandChip.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                skuList.clear()
                page = 1
                currentSelectedChip = ListSorters.BRAND
                mainViewModel.getSortedItemsWithPagination(page, ListSorters.BRAND)
            } else {
                skuList.clear()
                page = 1
                currentSelectedChip = ListSorters.NOTHING
                mainViewModel.getInitialHeroProductsWithPagination(page)
            }
        }

        binding.sortByPriceChip.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                skuList.clear()
                page = 1
                currentSelectedChip = ListSorters.PRICE
                mainViewModel.getSortedItemsWithPagination(page, ListSorters.PRICE)
            } else {
                skuList.clear()
                page = 1
                currentSelectedChip = ListSorters.NOTHING
                mainViewModel.getInitialHeroProductsWithPagination(page)
            }
        }
    }

    override fun onDestroy() {
        if (::paginate.isInitialized)
            paginate.unbind()
        super.onDestroy()
    }

    private fun updateLoadingStateOfPagination(isLoading: Boolean) {
        if (::paginate.isInitialized)
            paginate.showLoading(isLoading)
    }

    private fun updateErrorStateOfPagination(isError: Boolean) {
        if (::paginate.isInitialized)
            paginate.showError(isError)
    }

    private fun hasLoadedAllItems(): Boolean {
        if (::paginate.isInitialized) {
            val totalPages = totalResults?.div(10)?.minus(1)
            if (page >= totalPages!!) {
                paginate.setNoMoreItems(true)
                page = 1
                return true
            }
        }
        return false
    }

    override fun onLoadMore() {
        if (!hasLoadedAllItems()) {
            page++
            if (searchQuery.isNotEmpty()) {
                mainViewModel.getFilteredItemsWithPagination(searchQuery, page)
            } else if (currentSelectedChip != ListSorters.NOTHING) {
                mainViewModel.getSortedItemsWithPagination(page, currentSelectedChip)
            } else {
                mainViewModel.getInitialHeroProductsWithPagination(page)
            }
        }
    }

    private fun renderLoadingScreen(isLoading: Boolean) {
        if (isLoading) {
            binding.circularProgressView.visibility = View.VISIBLE
            binding.listRV.visibility = View.GONE
        } else {
            binding.circularProgressView.visibility = View.GONE
            binding.listRV.visibility = View.VISIBLE
        }
    }

    private fun renderErrorScreenWithRetry(clientResult: ClientResult.Error) {
        binding.listRV.visibility = View.GONE
        binding.retryLayoutHolder.retryLayoutCL.visibility = View.VISIBLE
        binding.retryLayoutHolder.retryMessageTV.text = clientResult.error.message
        binding.retryLayoutHolder.retryCTA.setOnClickListener {
            if (searchQuery.isEmpty()) {
                mainViewModel.getInitialHeroProductsWithPagination(page)
            } else {
                mainViewModel.getFilteredItemsWithPagination(searchQuery, page)
            }
        }
    }

    private fun renderNoMovieFoundScreen(hasNetwork: Boolean = true) {
        binding.listRV.visibility = View.GONE
        binding.noMovieFoundHolder.retryLayoutCL.visibility = View.VISIBLE
        binding.noMovieFoundHolder.retryMessageTV.text =
            if (hasNetwork) "Nothing Found" else "No Network detected"
    }


}