package com.example.auctionapp.ui.my_active_auctions.adapted

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.ui.my_active_auctions.MyActiveAuctionsPage

class ActiveAuctionsPagerAdapter(fm: FragmentActivity, private val items: List<ProductModel>) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        val item = items[position]
        return MyActiveAuctionsPage().newInstance(item)
    }


}