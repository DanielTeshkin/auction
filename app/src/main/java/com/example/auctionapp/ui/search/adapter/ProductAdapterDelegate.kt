package com.example.auctionapp.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.auctionapp.R
import com.example.auctionapp.databinding.ItemProductBinding
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.tools.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class ProductAdapterDelegate(
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
            price.text = binding.root.context.getString(R.string.price, product.price.toString())
            title.text = product.title
            Glide.with(itemView)
                .load(product.photos?.first()?.file)
                .placeholder(R.drawable.no_image)
                .into(avatar)
            dateTV.text = binding.root.context.getString(
                R.string.date_format,
                formatDate(product.startDate!!),
                formatDate(product.endDate!!)
            )
        }

        private fun formatDate(dateTimeStr: String): String {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val dateTime = LocalDateTime.parse(dateTimeStr, formatter)
            return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }

    }

    interface OnItemClickListener {
        fun onItemClick(product: ProductModel)
    }
}











