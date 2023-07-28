package com.auction.mobile.ui.my_active_auctions.adapted

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auction.mobile.R
import com.auction.mobile.databinding.ItemPriceHistoryBinding
import com.auction.mobile.databinding.ItemProductBinding
import com.auction.mobile.domain.models.FavoriteProductModel
import com.auction.mobile.domain.models.ProductHistoryModel
import com.auction.mobile.tools.PreferencesHelper
import com.auction.mobile.tools.getOnlyTime
import com.auction.mobile.tools.inflate
import com.auction.mobile.ui.search.adapter.ProductAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import javax.inject.Inject

class HistoryAdapterDelegate(private val phone: String) :
    AbsListItemAdapterDelegate<ProductHistoryModel, ProductHistoryModel, HistoryAdapterDelegate.Holder>() {

    private var usersWithNumber: MutableMap<String, Int> = mutableMapOf()
    private var userNumber = 1

    override fun isForViewType(
        item: ProductHistoryModel,
        items: MutableList<ProductHistoryModel>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(
            parent.inflate(
                ItemPriceHistoryBinding::inflate,
            ),
            phone
        )

    }

    override fun onBindViewHolder(
        item: ProductHistoryModel,
        holder: Holder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)

    }

    inner class Holder(
        private val binding: ItemPriceHistoryBinding,
        private val phone: String
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductHistoryModel) {
            binding.apply {
                val context = binding.root.context
                item.user.apply {
                    if (phone == item.user.username) {
                        binding.tvName.text = "Вы"
                    } else if (usersWithNumber.keys.contains(item.user.username)) {
                        binding.tvName.text = context.getString(R.string.user_number, usersWithNumber[item.user.username].toString())
                    } else {
                        usersWithNumber[item.user.username] = userNumber
                        binding.tvName.text = context.getString(R.string.user_number, userNumber.toString())
                        userNumber++
                    }
//                    if (firstname != null && lastname != null) binding.tvName.text = context.getString(
//                        R.string.full_name, lastname, lastname) else binding.tvName.text = username
                }
                tvPrice.text = item.price.toString()
                tvDate.text = item.dateCreated.getOnlyTime()
            }
        }
    }

}