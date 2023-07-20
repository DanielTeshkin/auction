package com.auction.mobile.ui.my_active_auctions

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.FragmentActiveAuctionsBinding
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.ui.my_active_auctions.adapted.ActiveAuctionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyActiveAuctionsFragment : Fragment(R.layout.fragment_active_auctions) {

    private val binding by viewBinding(FragmentActiveAuctionsBinding::bind)
    private val viewModel by viewModels<MyActiveAuctionsViewModel>()

    private lateinit var adapter: ActiveAuctionsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts()
        handleData()
    }

    override fun onDestroyView() {
        Log.d("TTT", "onDestroyView")
        for (fragment in adapter.fragments) {
            this.requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commitNow()
            binding.vp.invalidate()
        }

        super.onDestroyView()
    }

    private fun addTab(screen: List<ProductModel>) {
        TabLayoutMediator(binding.tabs, binding.vp) { tab, position ->
            screen.forEach {
                tab.text = it.title
            }
        }.attach()
    }

    private fun handleData() {
        viewModel.productLive.observe(viewLifecycleOwner) {
            adapter = ActiveAuctionsPagerAdapter(this.requireActivity(), it)
            binding.nothingToShow.isGone = it.isNotEmpty()
            binding.vp.adapter = adapter
            addTab(it)
        }
    }
}