package com.example.auctionapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.DetailProductItemBinding
import com.example.auctionapp.domain.models.PhotosModel
import com.example.auctionapp.ui.detail.adapter.PhotosAdapter
import com.example.auctionapp.ui.detail.adapter.PhotosAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_product_item),
    PhotosAdapterDelegate.OnPhotoClickListener {

    private val binding by viewBinding(DetailProductItemBinding::bind)
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private var photosAdapter: PhotosAdapter by autoCleared()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDetailInfo(args.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getDetailInfo(args.id)
        lifecycleScope.launch {
            handleData()
        }
    }

    private suspend fun handleData() {
        with(viewModel) {
                infoFlow.collect { info ->
                    if (info != null) {
                        photosAdapter.items = info.photos
                        binding.price.text = info.price.toString()
                        binding.name.text = info.title.toString()
                        binding.note.text = info.description.toString()
                    }
                }

        }
    }

    private fun initView() {
        photosAdapter = PhotosAdapter(this)
        binding.viewPager.adapter = photosAdapter
    }

    override fun onItemClick(photo: PhotosModel) {
    }
}