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
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.SearchFragmentBinding
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.tools.textChangedFlow
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.search.adapter.ProductAdapter
import com.example.auctionapp.ui.search.adapter.ProductAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment),
    ProductAdapterDelegate.OnItemClickListener {

    private val binding by viewBinding(SearchFragmentBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private var productAdapter: ProductAdapter by autoCleared()
    private var searchJob: Job? = null
    private var sort: Boolean = false
    private var searchText: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleData()
        search()
    }


    private fun initView() {
        productAdapter = ProductAdapter(this)
        with(binding.rv) {
            adapter = productAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
        }
        binding.sort.setOnClickListener {
            sort = !sort
            val sortInfo = if (sort) "price" else ""
            viewModel.getProducts(searchText, sortInfo)
        }
    }

    private fun search() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            binding.searchEt.textChangedFlow().onStart { emit("") }
                .debounce(500)
                .distinctUntilChanged()
                .mapLatest { text ->
                    searchText = text
                    Log.d("productsLive", "SEARCH")
                    val sortInfo = if (sort) "price" else ""
                    viewModel.getProducts(text, sortInfo)
                }
                .collect()
        }
    }

    private fun handleData() {
        with(viewModel) {
            productLive.observe(viewLifecycleOwner) { products ->
                Log.d("productsLive", "LIVE")
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


    override fun onItemClick(product: ProductModel) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                id = product.id
            )
        )
    }

}