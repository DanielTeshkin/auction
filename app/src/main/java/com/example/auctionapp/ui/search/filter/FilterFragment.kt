package com.example.auctionapp.ui.search.filter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.room.util.getColumnIndex
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.SearchFilterFragmentBinding
import com.example.auctionapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment: Fragment(R.layout.search_filter_fragment) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding by viewBinding(SearchFilterFragmentBinding::bind)
    private val viewModel by viewModels<FilterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCities()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleData()
        binding.apply.setOnClickListener {
            mainViewModel.setMinPrice(binding.priceMin.text.toString())
            mainViewModel.setMaxPrice(binding.priceMax.text.toString())
            parentFragmentManager.popBackStack()
        }
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.reset.setOnClickListener {
            mainViewModel.setMinPrice("")
            mainViewModel.setMaxPrice("")
            parentFragmentManager.popBackStack()
        }
    }

    private fun handleData() {
        viewModel.citiesLiveData.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                it.map { it.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.etCity.setAdapter(adapter)
        }
    }


}