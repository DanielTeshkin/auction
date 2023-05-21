package com.example.auctionapp.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.auctionapp.R
import com.example.auctionapp.databinding.ItemProductBinding
import com.example.auctionapp.domain.models.FavoriteProductModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.tools.getOnlyTime
import com.example.auctionapp.tools.inflate
import com.example.auctionapp.tools.isDateAfterToday
import com.example.auctionapp.tools.isDateBeforeToday
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class ProductAdapterDelegate(
    private val listener: OnItemClickListener
) :
    AbsListItemAdapterDelegate<FavoriteProductModel, FavoriteProductModel, ProductAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: FavoriteProductModel,
        items: MutableList<FavoriteProductModel>,
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

    override fun onBindViewHolder(
        item: FavoriteProductModel,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }


    class Holder(
        private val binding: ItemProductBinding,
        listener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                this.listener = listener
                this.listenerFavorite = listener
            }
        }

        private val context = binding.root.context
        fun bind(product: FavoriteProductModel) = with(binding) {
            like.isChecked = product.isFavorite
            like.setOnClickListener {
                product.isFavorite = !product.isFavorite
            }
            this.product = product
            registerTimeStart.text = context.getString(R.string.register_time_start, product.startRegistration)
            registerTimeEnd.text = context.getString(R.string.register_time_end, product.endRegistration)
            price.text = context.getString(R.string.price, product.price.toString())
            title.text = product.title
            Glide.with(itemView)
                .load(product.photos?.first()?.file)
                .placeholder(R.drawable.no_image)
                .into(avatar)
            date.text = context.getString(
                R.string.auction_start_text,
                product.startDate!!,
                product.endDate!!.getOnlyTime()
            )
//            endDate.text = context.getString(
//                R.string.end_date_text,
//                product.endDate!!
//            )
        }

        private fun formatDate(dateTimeStr: String): String {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val dateTime = LocalDateTime.parse(dateTimeStr, formatter)
            return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
        }

    }

    interface OnItemClickListener {
        fun onItemClick(product: FavoriteProductModel)

        fun onFavoriteClick(product: FavoriteProductModel)

        fun onBidClick(id: String)
    }
}











