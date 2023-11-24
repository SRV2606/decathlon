package com.example.decathlon.ui.viewHolders

import android.R
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.decathlon.base.BaseViewHolder
import com.example.decathlon.databinding.ItemSkuCardBinding
import com.example.domain.domain.models.DecathlonSKUItemBean


class SkuItemViewHolder(
    private val binding: ItemSkuCardBinding,
    private val context: Context
) : BaseViewHolder<DecathlonSKUItemBean>(binding) {


    override fun setItem(
        data: DecathlonSKUItemBean?,
        itemClickListener: ((DecathlonSKUItemBean) -> Unit)?
    ) {

        data?.let {
            with(binding) {
                skuNameTV.text = it.name
                skuBrandTV.text = it.brand
                skuPriceTV.text = it.price.toString()
                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_dialog_info)
                Glide.with(context).load(it.imageUrl).apply(options).into(skuImageIV)
            }
        }
    }
}


