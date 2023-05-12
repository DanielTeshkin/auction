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
            if (viewModel.favoriteLive.value?.contains(item) == true) viewModel.deleteProductFromFavorite(item!!) else viewModel.insertToDb(
                item!!
            )
        }

    }

    private suspend fun handleData() {
        with(viewModel) {
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
            infoFlow.collect { info ->
                if (info != null) {
                    item = info
                    binding.like.isChecked = favoriteLive.value?.contains(info) == true
                    photosAdapter.items = info.photos
                    binding.price.text = getString(R.string.price, info.price.toString())
                    startDate = info.startDate
                    endDate = info.endDate
                    binding.name.text = info.title.toString()
                    binding.note.text = info.description.toString()
                    binding.date.text =
                        getString(R.string.auction_start_text, info.startDate!!)
                    binding.endDate.text =
                        getString(R.string.auction_start_text, info.endDate!!)
                    val isAfterToday = isDateAfterToday(startDate!!)
                    val isNotEnd = isDateAfterToday(endDate!!)
                    val r = !isAfterToday && isNotEnd
                    binding.call.isEnabled = r
                }
            }
        }

    }

    private fun isDateAfterToday(dateTimeStr: String): Boolean {
        val dateTime = LocalDateTime.ofInstant(
            Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateTimeStr)),
            ZoneId.systemDefault()
        )
        val now = LocalDateTime.now()
        return dateTime.isAfter(now)
    }

    private fun formatDate(dateTimeStr: String): String {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(dateTimeStr, formatter)
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    }

    private fun initView() {
        photosAdapter = PhotosAdapter(this)
        binding.viewPager.adapter = photosAdapter


        binding.call.setOnClickListener {
            binding.newPriceET.isGone = isRaceOpen
            binding.raise.isGone = isRaceOpen
            binding.call.isGone = !isRaceOpen
            isRaceOpen = !isRaceOpen
        }
        binding.raise.setOnClickListener {
            if (binding.newPriceET.text.toString()
                    .toInt() > (viewModel.infoFlow.value?.price?.toInt() ?: 0)
            ) {
                viewModel.raisePrice(args.id, binding.newPriceET.text.toString(), {
                    binding.newPriceET.isGone = isRaceOpen
                    binding.raise.isGone = isRaceOpen
                    binding.call.isGone = !isRaceOpen
                    toast("Сумма успешно повышена")
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                    viewModel.getDetailInfo(args.id)
                    isRaceOpen = !isRaceOpen
                }, {
                    toast("Что то пошло не так")
                })
            } else {
                toast("Новая сумма должна быть выше предыдущей!")
            }


        }

    }

    override fun onItemClick(photo: PhotosModel) {
    }
}