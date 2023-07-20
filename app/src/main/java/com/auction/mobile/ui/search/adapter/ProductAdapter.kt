package com.auction.mobile.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.auction.mobile.domain.models.FavoriteProductModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductAdapter(
    listener: ProductAdapterDelegate.OnItemClickListener,
    isFromFavorite: Boolean,
    ) : AsyncListDifferDelegationAdapter<FavoriteProductModel>(ProductDiffUtilCallback()) {


    init {
        delegatesManager.addDelegate(ProductAdapterDelegate(isFromFavorite, listener))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }




    class ProductDiffUtilCallback : DiffUtil.ItemCallback<FavoriteProductModel>() {
        override fun areItemsTheSame(oldItem: FavoriteProductModel, newItem: FavoriteProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteProductModel, newItem: FavoriteProductModel): Boolean {
            return oldItem == newItem
        }
    }


}