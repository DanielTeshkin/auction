package com.example.auctionapp.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.auctionapp.R
import com.example.auctionapp.databinding.ItemProductBinding
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.tools.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class ProductAdapterDelegate (
    private val listener: OnItemClickListener
) :
    AbsListItemAdapterDelegate<ProductModel, ProductModel, ProductAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: ProductModel,
        items: MutableList<ProductModel>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(
                ItemProductBinding::inflate
            ),
            listener
        )
    }

    override fun onBindViewHolder(item: ProductModel, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }


    class Holder(
        private val binding: ItemProductBinding,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                this.listener = listener
            }
        }

        fun bind(product: ProductModel) = with(binding) {
            this.product = product
            price.text = product.price.toString()
            title.text = product.title
            Glide.with(itemView)
                .load(product.photos?.first()?.file)
                .placeholder(R.drawable.no_image)
                .into(avatar)

        }
    }
    interface OnItemClickListener {
        fun onItemClick(product: ProductModel)
    }
}











