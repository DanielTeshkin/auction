package com.example.auctionapp.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.DetailProductItemBinding
import com.example.auctionapp.domain.models.PhotosModel
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.domain.models.RacePriceModel
import com.example.auctionapp.tools.getOnlyTime
import com.example.auctionapp.tools.isDateAfterToday
import com.example.auctionapp.tools.isDateBeforeToday
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.detail.adapter.PhotosAdapter
import com.example.auctionapp.ui.detail.adapter.PhotosAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavorite()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getDetailInfo(args.id)
        lifecycleScope.launch {
            handleData()
        }
        binding.like.setOnClickListener {
            val check = viewModel.favoriteLive.value?.contains(item) == true
            Log.d("TTT", check.toString())
            if (viewModel.favoriteLive.value?.contains(item) == true) viewModel.deleteProductFromFavorite(
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
                    binding.like.isChecked = favoriteLive.value?.contains(info) == true
                    binding.minStep.text = getString(R.string.min_step, info.rateHikePrice)
                    photosAdapter.items = info.photos
                    binding.price.text = getString(R.string.price, info.price.toString())
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
                    val r = isAfterToday && isNotEnd
                    val rd =
                        info.startRegistration.isDateBeforeToday() && info.endRegistration.isDateAfterToday() && !args.isItBid
                    binding.call.isGone = !r
                    binding.llRaicePrice.isGone = !r
                    binding.apply.isGone = !rd
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
//        args.status?.let {
//            binding.status.text = getString(R.string.status, it)
//        }
    }

    override fun onItemClick(photo: PhotosModel) {
    }
}