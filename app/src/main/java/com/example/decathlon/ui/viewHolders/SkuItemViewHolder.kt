package com.example.decathlon.ui.viewHolders

import android.content.Context
import com.bumptech.glide.Glide
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
                Glide.with(context).load(it.imageUrl).into(skuImageIV)
            }
        }
    }
}


