package com.example.auctionapp.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.auctionapp.domain.models.ProductModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductAdapter(
    listener: ProductAdapterDelegate.OnItemClickListener
) : AsyncListDifferDelegationAdapter<ProductModel>(ProductDiffUtilCallback()) {


    init {
        delegatesManager.addDelegate(ProductAdapterDelegate(listener))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }




    class ProductDiffUtilCallback : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }
    }


}