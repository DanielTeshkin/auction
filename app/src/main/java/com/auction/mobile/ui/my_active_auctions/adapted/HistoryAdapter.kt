package com.auction.mobile.ui.my_active_auctions.adapted

import androidx.recyclerview.widget.DiffUtil
import com.auction.mobile.domain.models.ProductHistoryModel
import com.auction.mobile.ui.search.adapter.ProductAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class HistoryAdapter(private val phone: String): AsyncListDifferDelegationAdapter<ProductHistoryModel>(HistoryDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(HistoryAdapterDelegate(phone))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class HistoryDiffUtilCallback : DiffUtil.ItemCallback<ProductHistoryModel>() {
        override fun areItemsTheSame(oldItem: ProductHistoryModel, newItem: ProductHistoryModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductHistoryModel, newItem: ProductHistoryModel): Boolean {
            return oldItem == newItem
        }
    }
}