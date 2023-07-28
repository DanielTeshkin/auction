package com.auction.mobile.ui.my_active_auctions

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.PagerLifecycle
import com.auction.mobile.R
import com.auction.mobile.databinding.DetailProductItemBinding
import com.auction.mobile.domain.models.PhotosModel
import com.auction.mobile.domain.models.ProductHistoryModel
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.models.RacePriceModel
import com.auction.mobile.tools.PreferencesHelper
import com.auction.mobile.tools.getOnlyTime
import com.auction.mobile.tools.withArguments
import com.auction.mobile.ui.detail.adapter.PhotosAdapter
import com.auction.mobile.ui.detail.adapter.PhotosAdapterDelegate
import com.auction.mobile.ui.my_active_auctions.adapted.HistoryAdapter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MyActiveAuctionsPage : Fragment(R.layout.detail_product_item),
    PhotosAdapterDelegate.OnPhotoClickListener, PagerLifecycle {

    private var photosAdapter: PhotosAdapter by autoCleared()

    private val binding by viewBinding(DetailProductItemBinding::bind)
    private val viewModel by viewModels<MyActiveAuctionsPageViewModel>()
    private var historyAdapter: HistoryAdapter by autoCleared()

    private lateinit var info: ProductModel
    private var isRaceOpen = false
    private var startDate: String? = null
    private var endDate: String? = null
    private var item: ProductModel? = null
    private var currentPrice = 0L
    private var minStep = 0L
    private var minPrice = 0L
    private var intermediatePrice = 0L
    private var apiPrice = 0L
    var job: Job? = null
    private var isShowFull = false
    private var historyList = arrayListOf<ProductHistoryModel>()
    @Inject
    lateinit var prefs: PreferencesHelper

    private lateinit var myPhone: String
    private var lastCalledClient: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info = arguments?.getSerializable(ACTIVE_PRODUCT_ITEM) as ProductModel
        myPhone = prefs.mPhoneNumber
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bind()
        initData()
        job = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                viewModel.getDetailInfo(info.id, 0)
            }
        }
    }

    private fun bind() {
        binding.tvExpand.isGone = false
        binding.registerTimeStart.isGone = true
        binding.registerTimeEnd.isGone = true
        binding.like.isGone = true
        binding.apply.isGone = true
        binding.llSearchButtons.isGone = false
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
            binding.downPriceBtn.isEnabled = false
        }
    }

    private fun initData() {
        viewModel.infoFlow.observe(viewLifecycleOwner) {product ->
            apiPrice = product.price
/*            val entries = arrayListOf<Entry>()
            it.priceHistory?.sortedBy { it.price }?.forEach { entry ->
                val onlyTime = entry.dateCreated.getOnlyTime()
                entries.add(Entry(SimpleDateFormat("HH:mm").parse(onlyTime).time.toFloat(), entry.price.toFloat()))
            }

            val dataset = LineDataSet(entries, "График изменения цены")
            val yAxis = binding.lineChart.xAxis
            yAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val timestamp = value.toLong()
                    val date = Date(timestamp)
                    return dateFormat.format(date)
                }
            }
            val newData = LineData(dataset)
            binding.lineChart.apply {
                data = newData
                invalidate()
            }*/
            if (product.priceHistory?.isNotEmpty() == true) {
                val sortedList =  product.priceHistory.sortedByDescending { it.price }
                lastCalledClient = sortedList[0].user.username
                binding.call.isEnabled = isButtonEnabled()
                Log.d("CHECK", product.priceHistory.size.toString())
                Log.d("CHECK", historyList.size.toString())
                if (product.priceHistory.size != historyList.size) {
                    historyList.clear()
                    historyList.addAll(product.priceHistory)
                    if (historyAdapter.items.size != 5) {
                        if (historyAdapter.items.size == 0) {
                            if (product.priceHistory.size > 5)
                                historyAdapter.items = sortedList.subList(0, 5)
                            else
                                historyAdapter.items = sortedList.subList(0, product.priceHistory.size)
//                            historyAdapter.items =sortedList.subList(0, 5)
                        } else {
                            historyAdapter.items = sortedList
                            binding.historyRV.scrollToPosition(0)
                        }
                    } else {
                        if (product.priceHistory.size > 5)
                            historyAdapter.items = sortedList.subList(0, 5)
                        else
                            historyAdapter.items = sortedList.subList(0, product.priceHistory.size)
                    }
                }
            }
            binding.tvExpand.setOnClickListener {
                if (product.priceHistory?.isNotEmpty() == true) {
                    if (!isShowFull) {
                        historyAdapter.items = product.priceHistory.sortedByDescending { it.price }
                        binding.historyRV.scrollToPosition(0)
                        isShowFull = true
                        binding.tvExpand.text = "Свернуть"
                    } else {
                        if (product.priceHistory.size > 4)
                            historyAdapter.items = product.priceHistory.sortedByDescending { it.price }.subList(0, 5)
                        else
                            historyAdapter.items = product.priceHistory.sortedByDescending { it.price }.subList(0, product.priceHistory.size)
                        isShowFull = false
                        binding.tvExpand.text = "Развернуть"
                    }
                }
            }
            binding.priceHistoryTitle.isGone = false
//            historyAdapter.items = it.priceHistory.sortedByDescending { it.price }
            binding.currentPriceFromApi.text =
                getString(R.string.current_price_from_api, product.price.toString())
            if (currentPrice != product.price) {
                if (currentPrice < product.price) {
                    binding.currentPrice.text =
                        getString(R.string.current_price, product.price.toString())
                    currentPrice = product.price
                }
            } else {
                binding.downPriceBtn.isEnabled = false
            }
        }
    }

    private fun initView() {
        photosAdapter = PhotosAdapter(this)
        binding.viewPager.adapter = photosAdapter
        historyAdapter = HistoryAdapter(prefs.mPhoneNumber)
        with(binding.historyRV) {
            isGone = false
            adapter = historyAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            setHasFixedSize(false)
        }

        binding.raicePriceBtn.setOnClickListener {
            intermediatePrice += minPrice
            currentPrice += minStep
            binding.currentPrice.text = getString(R.string.current_price, currentPrice.toString())
            binding.call.isEnabled = isButtonEnabled()
            binding.downPriceBtn.isEnabled = currentPrice > minPrice
        }
        binding.downPriceBtn.setOnClickListener {
            intermediatePrice -= minPrice
            currentPrice -= minStep
            binding.currentPrice.text = getString(R.string.current_price, currentPrice.toString())
            binding.call.isEnabled = isButtonEnabled()
            binding.downPriceBtn.isEnabled = currentPrice > apiPrice
        }
        binding.status.isGone = true
    }

    private fun isButtonEnabled(): Boolean {
        return currentPrice > apiPrice && myPhone != lastCalledClient
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

    override fun onDestroyPagerView() {
        job?.cancel()
        job = null
    }
}