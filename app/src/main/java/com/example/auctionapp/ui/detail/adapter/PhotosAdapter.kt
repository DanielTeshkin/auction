package com.example.auctionapp.ui.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.auctionapp.domain.models.PhotosModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.ui.search.adapter.ProductAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class PhotosAdapter(
    listener: PhotosAdapterDelegate.OnPhotoClickListener
) : AsyncListDifferDelegationAdapter<PhotosModel>(ProductDiffUtilCallback()) {


    init {
        delegatesManager.addDelegate(PhotosAdapterDelegate(listener))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class ProductDiffUtilCallback : DiffUtil.ItemCallback<PhotosModel>() {
        override fun areItemsTheSame(oldItem: PhotosModel, newItem: PhotosModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotosModel, newItem: PhotosModel): Boolean {
            return oldItem == newItem
        }
    }


}