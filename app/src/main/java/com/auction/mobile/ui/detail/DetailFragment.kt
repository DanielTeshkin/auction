package com.auction.mobile.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.DetailProductItemBinding
import com.auction.mobile.domain.models.PhotosModel
import com.auction.mobile.domain.models.ProductModel
import com.auction.mobile.domain.models.RacePriceModel
import com.auction.mobile.tools.getOnlyTime
import com.auction.mobile.tools.isDateAfterToday
import com.auction.mobile.tools.isDateBeforeToday
import com.auction.mobile.tools.toast
import com.auction.mobile.ui.MainViewModel
import com.auction.mobile.ui.detail.adapter.PhotosAdapter
import com.auction.mobile.ui.detail.adapter.PhotosAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_product_item),
    PhotosAdapterDelegate.OnPhotoClickListener {

    private val binding by viewBinding(DetailProductItemBinding::bind)
    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private var photosAdapter: PhotosAdapter by autoCleared()
    private var isRaceOpen = false
    private var startDate: String? = null
    private var endDate: String? = null
    private var item: ProductModel? = null
    private var currentPrice = 0L
    private var minStep = 0L
    private var minPrice = 0L
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavorite()
        viewModel.getBid()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getDetailInfo(args.id)
        lifecycleScope.launch {
            handleData()
        }
        binding.like.setOnClickListener {
            if (viewModel.favoriteLive.value?.firstOrNull { it.id == item?.id } != null) viewModel.deleteProductFromFavorite(
                item!!
            ) else viewModel.insertToDb(
                item!!
            )
        }

    }

    private suspend fun handleData() {
        with(viewModel) {
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }

            failLive.observe(viewLifecycleOwner) {
                toast("Что то пошло не так")
            }
            successLive.observe(viewLifecycleOwner) {
                toast("Сумма успешно повышена")
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
            infoFlow.collect { info ->
                if (info != null) {
                    binding.registerPrice.isGone = false
                    binding.registerPrice.text = getString(R.string.reg_price, info.registrationPrice.toString())
                    binding.downPriceBtn.text = getString(
                        R.string.down_price,
                    )
                    binding.raicePriceBtn.text = getString(
                        R.string.raice_price,
                    )
                    binding.currentPrice.text =
                        getString(R.string.current_price, info.price.toString())
                    minPrice = info.price
                    currentPrice = info.price
                    minStep = info.rateHikePrice.toDouble().toLong()
                    item = info
                    binding.minStep.text = getString(R.string.min_step, info.rateHikePrice)
                    photosAdapter.items = info.photos
                    binding.price.text = getString(R.string.price, info.price.toString())
                    startDate = info.startDate
                    endDate = info.endDate
                    favoriteLive.observe(viewLifecycleOwner) {
                        binding.like.isChecked = it?.firstOrNull { it.id == info.id } != null

                    }
                    binding.name.text = info.title.toString()
                    binding.note.text = info.description.toString()
                    binding.date.text =
                        getString(
                            R.string.auction_start_text,
                            info.startDate!!,
                            info.endDate!!.getOnlyTime()
                        )
//                    binding.endDate.text =
//                        getString(R.string.end_date_text, info.endDate!!)
                    binding.registerTimeStart.text =
                        getString(R.string.register_time_start, info.startRegistration)
                    binding.registerTimeEnd.text =
                        getString(R.string.register_time_end, info.endRegistration)
                    val isAfterToday = startDate!!.isDateBeforeToday()
                    val isNotEnd = endDate!!.isDateAfterToday()
                    Log.d("TTT", isAfterToday.toString())
                    Log.d("TTT", isNotEnd.toString())
                    val r = isAfterToday && isNotEnd && !args.isItBid
                    val rd =
                        info.startRegistration.isDateBeforeToday() && info.endRegistration.isDateAfterToday() && !args.isItBid
                    binding.llSearchButtons.isGone =!r
                    binding.call.isGone = !r
                    binding.llRaicePrice.isGone = !r
                    binding.llSearchButtons.isGone =!rd
                    binding.apply.isGone = !rd
                    binding.apply.isGone = mainViewModel.bidLive.value!!.firstOrNull { it.id == item?.id } != null
                    binding.apply.setOnClickListener {
                        viewModel.createBid(info.id)
                    }
                    binding.call.setOnClickListener {
                        viewModel.racePrice(
                            id = info.id,
                            info = RacePriceModel(currentPrice.toString())
                        )
                    }
                }
            }
        }

    }

    private fun initView() {
        photosAdapter = PhotosAdapter(this)
        binding.viewPager.adapter = photosAdapter

        binding.call.setOnClickListener {
//            binding.newPriceET.isGone = isRaceOpen
//            binding.raise.isGone = isRaceOpen
//            binding.call.isGone = !isRaceOpen
//            isRaceOpen = !isRaceOpen
        }
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
        binding.raise.setOnClickListener {
        }
        binding.status.isGone = !args.isItBid
        args.status?.let {
            binding.status.text = getString(R.string.status, it)
        }
    }

    override fun onItemClick(photo: PhotosModel) {
    }
}