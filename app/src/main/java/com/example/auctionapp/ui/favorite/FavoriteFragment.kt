package com.example.auctionapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.FavoriteFragmentBinding
import com.example.auctionapp.domain.models.FavoriteProductModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.models.toProductModel
import com.example.auctionapp.tools.findTopNavController
import com.example.auctionapp.ui.MainViewModel
import com.example.auctionapp.ui.main.MainFragmentDirections
import com.example.auctionapp.ui.search.SearchFragmentDirections
import com.example.auctionapp.ui.search.adapter.ProductAdapter
import com.example.auctionapp.ui.search.adapter.ProductAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.favorite_fragment), ProductAdapterDelegate.OnItemClickListener {

    private val viewModel by viewModels<FavoriteViewModel>()
    private val binding by viewBinding(FavoriteFragmentBinding::bind)
    private val mainViewModel by activityViewModels<MainViewModel>()
    private var productAdapter by autoCleared<ProductAdapter>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAll()
        handleLive()
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

    }

    private fun handleLive() {
        viewModel.liveItems.observe(viewLifecycleOwner) {
            productAdapter.items = it
        }
        mainViewModel.liveFavoriteItems.observe(viewLifecycleOwner) {
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
        viewModel.deleteProductFromFavorite(product.toProductModel())
    }
}