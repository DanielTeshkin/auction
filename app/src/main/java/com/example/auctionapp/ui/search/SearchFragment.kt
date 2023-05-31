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
import com.example.auctionapp.tools.*
import com.example.auctionapp.ui.MainViewModel
import com.example.auctionapp.ui.main.MainFragment
import com.example.auctionapp.ui.main.MainFragmentDirections
import com.example.auctionapp.ui.search.adapter.ProductAdapter
import com.example.auctionapp.ui.search.adapter.ProductAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
            viewModel.getProducts(
                searchText,
                sortInfo,
                mainViewModel.minPriceLiveData.value ?: "",
                mainViewModel.maxPriceLiveData.value ?: "",
                mainViewModel.selectedCityLiveData.value ?: ""
            )
        }
        binding.filterBtn.setOnClickListener {
            findTopNavController().navigate(
                R.id.action_mainFragment_to_filterFragment
            )
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
                    viewModel.getProducts(
                        text, sortInfo, mainViewModel.minPriceLiveData.value ?: "",
                        mainViewModel.maxPriceLiveData.value ?: "",
                        mainViewModel.selectedCityLiveData.value ?: ""
                    )
                }
                .collect()
        }
    }

    private fun handleData() {
        with(viewModel) {
            productLive.observe(viewLifecycleOwner) { products ->
                val filteredProducts = products.filter { it.startRegistration.isDateAfterToday() && it.endRegistration.isDateBeforeToday() }
                binding.nothingToShow.isGone = filteredProducts.isNotEmpty()
                productAdapter.items = filteredProducts

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
//        if (findNavController().graph.id == R.navigation.nav_graph) {
//            findNavController().navigate(
//                MainFragmentDirections.actionMainFragmentToDetailFragment(
//                    id = product.id
//                )
//            )
//        } else {
        findTopNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                id = product.id,
                isItBid = false,
                status = null
            )
        )
//        }

    }

    override fun onFavoriteClick(product: FavoriteProductModel) {
        if (product.isFavorite) viewModel.deleteProductFromFavorite(product.toProductModel()) else viewModel.insertToDb(
            product.toProductModel()
        )
    }

    override fun onBidClick(id: String) {
        viewModel.createBid(id)
    }

}