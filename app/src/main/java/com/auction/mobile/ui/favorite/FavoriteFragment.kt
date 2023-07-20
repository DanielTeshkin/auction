package com.auction.mobile.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.FavoriteFragmentBinding
import com.auction.mobile.domain.models.FavoriteProductModel
import com.auction.mobile.domain.models.toProductModel
import com.auction.mobile.tools.findTopNavController
import com.auction.mobile.ui.MainViewModel
import com.auction.mobile.ui.main.MainFragmentDirections
import com.auction.mobile.ui.search.adapter.ProductAdapter
import com.auction.mobile.ui.search.adapter.ProductAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.favorite_fragment),
    ProductAdapterDelegate.OnItemClickListener {

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
        productAdapter = ProductAdapter(this, true )
        with(binding.rv) {
            adapter = productAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
        }

    }

    private fun handleLive() {
        viewModel.liveItems.observe(viewLifecycleOwner) {
            binding.nothingToShow.isGone = it.isNotEmpty()
            productAdapter.items = it
        }
    }

    override fun onItemClick(product: FavoriteProductModel) {
        findTopNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                id = product.id,
                isItBid = false,
                status = null
            )
        )
    }

    override fun onFavoriteClick(product: FavoriteProductModel) {
        viewModel.deleteProductFromFavorite(product.toProductModel())
    }

    override fun onBidClick(id: String) {

    }
}