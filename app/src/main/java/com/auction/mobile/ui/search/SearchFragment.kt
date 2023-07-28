package com.auction.mobile.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.SearchFragmentBinding
import com.auction.mobile.domain.models.FavoriteProductModel
import com.auction.mobile.domain.models.toProductModel
import com.auction.mobile.tools.*
import com.auction.mobile.ui.MainViewModel
import com.auction.mobile.ui.main.MainFragmentDirections
import com.auction.mobile.ui.search.adapter.ProductAdapter
import com.auction.mobile.ui.search.adapter.ProductAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    private var sortText = ""
    private var currentSortInfo = ""

    @Inject
    lateinit var prefs: PreferencesHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (prefs.mPhoneNumber == "") viewModel.getInfo()
        search()
        handleData()
        initView()

    }


    private fun initView() {
        productAdapter = ProductAdapter(this, false)
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
                    sortText = text
                    currentSortInfo = sortInfo
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
                val filteredProducts =
                    products.filter { it.startRegistration.isDateBeforeToday() && it.endRegistration.isDateAfterToday() }
                binding.nothingToShow.isGone = filteredProducts.isNotEmpty()
                productAdapter.items = filteredProducts

            }
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
            viewModel.infoLive.observe(viewLifecycleOwner) {
                prefs.mPhoneNumber = it.phone
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
        viewModel.getProducts(
            sortText, currentSortInfo, mainViewModel.minPriceLiveData.value ?: "",
            mainViewModel.maxPriceLiveData.value ?: "",
            mainViewModel.selectedCityLiveData.value ?: ""
        )
    }

}