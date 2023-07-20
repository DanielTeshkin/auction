package com.auction.mobile.ui.win_auctions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.FragmentWinAuctionsBinding
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.tools.findTopNavController
import com.auction.mobile.tools.toast
import com.auction.mobile.ui.win_auctions.adapter.WinAuctionAdapter
import com.auction.mobile.ui.win_auctions.adapter.WinAuctionAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WinAuctionsFragment: Fragment(R.layout.fragment_win_auctions), WinAuctionAdapterDelegate.OnItemClickListener {

    private val binding by viewBinding(FragmentWinAuctionsBinding::bind)
    private val viewModel by viewModels<WinAuctionsViewModel>()
    private var productAdapter by autoCleared<WinAuctionAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getProducts()
        handleData()
    }

    private fun initView() {
        productAdapter = WinAuctionAdapter(this)
        with(binding.rv) {
            adapter = productAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
        }
    }

    private fun handleData() {
        with(viewModel) {
            productLive.observe(viewLifecycleOwner) { products ->
                productAdapter.items = products

            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
        }
    }

    override fun onItemClick(product: ProductModel) {
        findTopNavController().navigate(
            WinAuctionsFragmentDirections.actionWinAuctionsFragmentToDetailFragment(
                product.id,
                isItBid = false,
                status = ""
            )
        )
    }


}