package com.example.auctionapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.SearchFragmentBinding
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.search.adapter.ProductAdapter
import com.example.auctionapp.ui.search.adapter.ProductAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment),
    ProductAdapterDelegate.OnItemClickListener {

    private val binding by viewBinding(SearchFragmentBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private var productAdapter: ProductAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        handleData()
    }


    private fun initView() {
        productAdapter = ProductAdapter(this)
        with(binding.rv) {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(false)
        }

        binding.sort.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }
    }


    private fun handleData() {
        with(viewModel) {
            lifecycleScope.launch {
                productFlow.collect { products ->
                    Log.d("productsLive", products.toString())
                    productAdapter.items = products
                }
            }
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
        }
    }


    private fun initData() {
        viewModel.getProducts()
    }

    override fun onItemClick(product: ProductModel) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                id = product.id
            )
        )
    }

}