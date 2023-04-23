package com.example.auctionapp.ui.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.auctionapp.R
import com.example.auctionapp.databinding.ItemImagesBinding
import com.example.auctionapp.domain.models.PhotosModel
import com.example.auctionapp.tools.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class PhotosAdapterDelegate (
    private val listener: OnPhotoClickListener
) :
    AbsListItemAdapterDelegate<PhotosModel, PhotosModel, PhotosAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: PhotosModel,
        items: MutableList<PhotosModel>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(
                ItemImagesBinding::inflate
            ),
            listener
        )
    }

    override fun onBindViewHolder(item: PhotosModel, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }


    class Holder(
        private val binding: ItemImagesBinding,
        listener: OnPhotoClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                this.listener = listener
            }
        }

        fun bind(photo: PhotosModel) = with(binding) {
           this.photo = photo
            Glide.with(itemView)
                .load(photo.file)
                .placeholder(R.drawable.no_image)
                .into(avatar)

        }
    }
    interface OnPhotoClickListener {
        fun onItemClick(photo: PhotosModel)
    }
}