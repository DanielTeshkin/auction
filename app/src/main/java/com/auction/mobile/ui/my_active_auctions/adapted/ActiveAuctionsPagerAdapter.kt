package com.auction.mobile.ui.my_active_auctions.adapted

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.ui.my_active_auctions.MyActiveAuctionsPage

class ActiveAuctionsPagerAdapter(fm: FragmentActivity, private val items: List<ProductModel>) :
    FragmentStateAdapter(fm) {

    var fragments: ArrayList<Fragment> = arrayListOf()
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        val item = items[position]
        val frag = MyActiveAuctionsPage().newInstance(item)
        fragments.add(frag)
        return frag
    }

}