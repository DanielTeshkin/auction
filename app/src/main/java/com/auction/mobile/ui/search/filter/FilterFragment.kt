package com.auction.mobile.ui.search.filter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.SearchFilterFragmentBinding
import com.auction.mobile.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment(R.layout.search_filter_fragment) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding by viewBinding(SearchFilterFragmentBinding::bind)
    private val viewModel by viewModels<FilterViewModel>()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel.getCities()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleData()
        mainViewModel.minPriceLiveData.value?.let {
            binding.priceMin.setText(it)
        }
        mainViewModel.maxPriceLiveData.value?.let {
            binding.priceMax.setText(it)
        }
        binding.etCity.setOnItemClickListener { parent, view, position, id ->
            mainViewModel.selectedCityNameLiveData.value = parent.getItemAtPosition(position).toString()
        }

        binding.apply.setOnClickListener {
            mainViewModel.setMinPrice(binding.priceMin.text.toString())
            mainViewModel.setMaxPrice(binding.priceMax.text.toString())

            Log.d("TTT", binding.etCity.text.toString())
            mainViewModel.setCity(
                if (binding.etCity.text.toString() != "") {
                    mainViewModel.citiesLiveData.value?.find { it.name == binding.etCity.text.toString() }?.id
                        ?: ""
                } else {
                    ""
                }

            )
            findNavController().popBackStack()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.reset.setOnClickListener {
            mainViewModel.selectedCityNameLiveData.value = ""
            mainViewModel.setCity("")
            mainViewModel.setMinPrice("")
            mainViewModel.setMaxPrice("")
            findNavController().popBackStack()
        }

    }

    private fun handleData() {
            val adapter = ArrayAdapter(
                requireActivity(),
                R.layout.item_city,
                mainViewModel.citiesLiveData.value!!.map { it.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.etCity.setAdapter(adapter)
        if (mainViewModel.selectedCityNameLiveData.value != null) {
            binding.etCity.setText(mainViewModel.selectedCityNameLiveData.value)
        }
    }


}