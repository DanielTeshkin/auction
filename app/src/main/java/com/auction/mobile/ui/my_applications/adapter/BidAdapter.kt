package com.auction.mobile.ui.my_applications.adapter

import androidx.recyclerview.widget.DiffUtil
import com.auction.mobile.domain.models.MyBidModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class BidAdapter(
    listener: BidAdapterDelegate.OnItemClickListener
) : AsyncListDifferDelegationAdapter<MyBidModel>(ProductDiffUtilCallback()) {


    init {
        delegatesManager.addDelegate(BidAdapterDelegate(listener))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }




    class ProductDiffUtilCallback : DiffUtil.ItemCallback<MyBidModel>() {
        override fun areItemsTheSame(oldItem: MyBidModel, newItem: MyBidModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyBidModel, newItem: MyBidModel): Boolean {
            return oldItem == newItem
        }
    }


}