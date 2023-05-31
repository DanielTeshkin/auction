package com.example.auctionapp.ui.my_active_auctions

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.DetailProductItemBinding
import com.example.auctionapp.domain.models.PhotosModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.models.RacePriceModel
import com.example.auctionapp.tools.getOnlyTime
import com.example.auctionapp.tools.withArguments
import com.example.auctionapp.ui.detail.adapter.PhotosAdapter
import com.example.auctionapp.ui.detail.adapter.PhotosAdapterDelegate
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyActiveAuctionsPage : Fragment(R.layout.detail_product_item),
    PhotosAdapterDelegate.OnPhotoClickListener {

    private var photosAdapter: PhotosAdapter by autoCleared()

    private val binding by viewBinding(DetailProductItemBinding::bind)
    private val viewModel by viewModels<MyActiveAuctionsPageViewModel>()

    private lateinit var info: ProductModel
    private var isRaceOpen = false
    private var startDate: String? = null
    private var endDate: String? = null
    private var item: ProductModel? = null
    private var currentPrice = 0L
    private var minStep = 0L
    private var minPrice = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info = arguments?.getSerializable(ACTIVE_PRODUCT_ITEM) as ProductModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bind()
        initData()
        while (true) {
            viewModel.getDetailInfo(info.id, 5000)
        }
    }


    private fun bind() {
        binding.registerTimeStart.isGone = true
        binding.registerTimeEnd.isGone = true
        binding.like.isGone = true
        binding.apply.isGone = true
        binding.downPriceBtn.text =
            getString(R.string.down_price)
        binding.raicePriceBtn.text =
            getString(R.string.raice_price)
        binding.currentPrice.text = getString(R.string.current_price, info.price.toString())
        minPrice = info.price
        currentPrice = info.price
        minStep = info.rateHikePrice.toDouble().toLong()
        this.info = info
        binding.minStep.text = getString(R.string.min_step, info.rateHikePrice)
        photosAdapter.items = info.photos
        startDate = info.startDate
        endDate = info.endDate
        binding.name.text = info.title.toString()
        binding.note.text = info.description.toString()
        binding.date.text =
            getString(
                R.string.auction_start_text,
                info.startDate!!,
                info.endDate!!.getOnlyTime()
            )
        binding.registerTimeStart.text =
            getString(R.string.register_time_start, info.startRegistration)
        binding.registerTimeEnd.text =
            getString(R.string.register_time_end, info.endRegistration)
        binding.call.isGone = false
        binding.llRaicePrice.isGone = false
        binding.call.setOnClickListener {
            viewModel.racePrice(id = info.id, info = RacePriceModel(currentPrice.toString()))
            viewModel.getDetailInfo(info.id, 0)
        }
    }

    private fun initData() {
        viewModel.infoFlow.observe(viewLifecycleOwner) {
            binding.currentPrice.text = getString(R.string.current_price, it.price.toString())
        }
    }

    private fun initView() {
        photosAdapter = PhotosAdapter(this)
        binding.viewPager.adapter = photosAdapter

        binding.raicePriceBtn.setOnClickListener {
            currentPrice += minStep
            binding.currentPrice.text = getString(R.string.current_price, currentPrice.toString())
            binding.call.isEnabled = currentPrice > minPrice
            binding.downPriceBtn.isEnabled = currentPrice > minPrice
        }
        binding.downPriceBtn.setOnClickListener {
            currentPrice -= minStep
            binding.currentPrice.text = getString(R.string.current_price, currentPrice.toString())
            binding.call.isEnabled = currentPrice > minPrice
            binding.downPriceBtn.isEnabled = currentPrice > minPrice
        }
        binding.status.isGone = true
    }


    companion object {
        const val ACTIVE_PRODUCT_ITEM = "active product item"
    }

    fun newInstance(item: ProductModel): MyActiveAuctionsPage {
        return MyActiveAuctionsPage().withArguments {
            putSerializable(ACTIVE_PRODUCT_ITEM, item)
        }
    }

    override fun onItemClick(photo: PhotosModel) {

    }


}