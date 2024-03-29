package com.auction.mobile.ui.my_applications.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.auction.mobile.R
import com.auction.mobile.databinding.ItemBidBinding
import com.auction.mobile.domain.models.MyBidModel
import com.auction.mobile.tools.getOnlyTime
import com.auction.mobile.tools.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class BidAdapterDelegate(
    private val listener: OnItemClickListener
) :
    AbsListItemAdapterDelegate<MyBidModel, MyBidModel, BidAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: MyBidModel,
        items: MutableList<MyBidModel>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(
                ItemBidBinding::inflate
            ),
            listener
        )
    }

    override fun onBindViewHolder(
        item: MyBidModel,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }


    class Holder(
        private val binding: ItemBidBinding,
        listener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                this.listener = listener
                this.listenerFavorite = listener
            }
        }

        private val context = binding.root.context
        fun bind(product: MyBidModel) = with(binding) {
//            like.setOnClickListener {
//                product.isFavorite = !product.isFavorite
//            }
            registrationPrice.text = context.getString(R.string.reg_price, product.product.registrationPrice.toString())
            status.text = context.getString(R.string.status, product.status)
            this.product = product
            registerTimeStart.text = context.getString(R.string.register_time_start, product.product.startRegistration)
            registerTimeEnd.text = context.getString(R.string.register_time_end, product.product.endRegistration)
            price.text = context.getString(R.string.price, product.product.price.toString())
            title.text = product.product.title
            Glide.with(itemView)
                .load(product.product.photos?.first()?.file)
                .placeholder(R.drawable.no_image)
                .into(avatar)
            date.text = context.getString(
                R.string.auction_start_text,
                product.product.startDate!!,
                product.product.endDate!!.getOnlyTime()
            )
        }

    }

    interface OnItemClickListener {
        fun onItemClick(product: MyBidModel)
    }
}