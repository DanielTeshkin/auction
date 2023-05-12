package com.example.auctionapp.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.SearchFragmentBinding
import com.example.auctionapp.domain.models.FavoriteProductModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.models.toProductModel
import com.example.auctionapp.tools.findTopNavController
import com.example.auctionapp.tools.textChangedFlow
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.MainViewModel
import com.example.auctionapp.ui.main.MainFragmentDirections
import com.example.auctionapp.ui.search.adapter.ProductAdapter
import com.example.auctionapp.ui.search.adapter.ProductAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_fragment),
    ProductAdapterDelegate.OnItemClickListener {

    private val binding by viewBinding(SearchFragmentBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()
    private var productAdapter by autoCleared<ProductAdapter>()
    private var searchJob: Job? = null
    private var sort: Boolean = false
    private var searchText: String = ""
    private val mainViewModel by activityViewModels<MainViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search()
        handleData()
        initView()
        Log.d("FFF", mainViewModel.maxPriceLiveData.value.toString())
        Log.d("FFF", mainViewModel.minPriceLiveData.value.toString())
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
            findTopNavController().navigate(
                R.id.filterFragment
            )
            sort = !sort
            val sortInfo = if (sort) "price" else ""
            viewModel.getProducts(searchText, sortInfo, "", "", "")
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
                    val sortInfo = if (sort) "price" else ""
                    viewModel.getProducts(text, sortInfo,"", "", "")
                }
                .collect()
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
            mainViewModel.liveFavoriteItems.observe(viewLifecycleOwner) {
            }
            mainViewModel.maxPriceLiveData.observe(viewLifecycleOwner) {
                Log.d("TTT", "max $it")
            }
            mainViewModel.minPriceLiveData.observe(viewLifecycleOwner) {
                Log.d("TTT", "min $it")
            }
        }
    }


    override fun onItemClick(product: FavoriteProductModel) {
        findTopNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                id = product.id
            )
        )
    }

    override fun onFavoriteClick(product: FavoriteProductModel) {
        if (product.isFavorite) viewModel.deleteProductFromFavorite(product.toProductModel()) else viewModel.insertToDb(
            product.toProductModel()
        )
    }

}