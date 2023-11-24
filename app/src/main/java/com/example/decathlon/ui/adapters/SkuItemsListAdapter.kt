package com.example.decathlon.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.decathlon.base.BaseViewHolder
import com.example.decathlon.databinding.ItemSkuCardBinding
import com.example.decathlon.ui.viewHolders.SkuItemViewHolder
import com.example.domain.domain.models.DecathlonSKUItemBean

class SkuItemsListAdapter(
    private val itemClickListener: (DecathlonSKUItemBean) -> Unit,
    private val context: Context,

    ) : ListAdapter<DecathlonSKUItemBean, BaseViewHolder<*>>(
    DIFF_CALLBACK
) {


    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<DecathlonSKUItemBean>() {
                override fun areItemsTheSame(
                    oldItem: DecathlonSKUItemBean,
                    newItem: DecathlonSKUItemBean
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: DecathlonSKUItemBean,
                    newItem: DecathlonSKUItemBean
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return SkuItemViewHolder(
            ItemSkuCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as SkuItemViewHolder).setItem(
            getItem(position), itemClickListener
        )
    }


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)

    }
}