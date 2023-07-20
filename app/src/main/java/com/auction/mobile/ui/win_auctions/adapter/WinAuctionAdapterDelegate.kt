package com.auction.mobile.ui.win_auctions.adapter

import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.auction.mobile.R
import com.auction.mobile.databinding.ItemWinAuctionBinding
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.tools.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class WinAuctionAdapterDelegate (
    private val listener: OnItemClickListener
) :
    AbsListItemAdapterDelegate<ProductModel, ProductModel, WinAuctionAdapterDelegate.Holder>() {

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
                ItemWinAuctionBinding::inflate
            ),
            listener
        )
    }

    override fun onBindViewHolder(
        item: ProductModel,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }


    class Holder(
        private val binding: ItemWinAuctionBinding,
        listener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                this.listener = listener
            }
        }

        private val context = binding.root.context
        fun bind(product: ProductModel) = with(binding) {
            this.product = product
            price.text = context.getString(R.string.end_price, product.price.toString())
            Glide.with(itemView)
                .load(product.photos?.first()?.file)
                .placeholder(R.drawable.no_image)
                .into(avatar)

            userName.text = product.author.username
            if (product.author.lastname == null && product.author.firstname == null && product.author.patronymic == null)
                fioAuthor.isGone = true
            else
                fioAuthor.text = context.getString(R.string.fio_text, product.author.lastname ?: "", product.author.firstname ?: "", product.author.patronymic ?: "")

            mail.text = product.author.email
        }

    }

    interface OnItemClickListener {
        fun onItemClick(product: ProductModel)
    }
}