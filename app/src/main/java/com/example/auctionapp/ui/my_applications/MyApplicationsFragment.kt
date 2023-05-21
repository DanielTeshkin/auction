package com.example.auctionapp.ui.my_applications

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.MyApplicationsFragmentBinding
import com.example.auctionapp.domain.models.MyBidModel
import com.example.auctionapp.tools.findTopNavController
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.main.MainFragmentDirections
import com.example.auctionapp.ui.my_applications.adapter.BidAdapter
import com.example.auctionapp.ui.my_applications.adapter.BidAdapterDelegate
import com.example.auctionapp.ui.search.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyApplicationsFragment : Fragment(R.layout.my_applications_fragment),
    BidAdapterDelegate.OnItemClickListener {

    private val viewModel by viewModels<MyApplicationsViewModel>()
    private val binding by viewBinding(MyApplicationsFragmentBinding::bind)
    private var productAdapter by autoCleared<BidAdapter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleData()
        initView()
    }

    private fun initView() {
        productAdapter = BidAdapter(this)
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
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
        }
    }

    override fun onItemClick(product: MyBidModel) {
        findTopNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                product.product.id,
                isItBid = true,
                status = product.status
            )
        )
    }
}