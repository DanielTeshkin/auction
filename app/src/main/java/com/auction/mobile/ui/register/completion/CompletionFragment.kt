package com.auction.mobile.ui.register.completion

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.CompletionFragmentBinding
import com.auction.mobile.domain.models.CitiesModel
import com.auction.mobile.tools.isValidEmail
import com.auction.mobile.tools.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletionFragment : Fragment(R.layout.completion_fragment) {


    private val viewModel by viewModels<CompletionViewModel>()
    private val binding by viewBinding(CompletionFragmentBinding::bind)

    private var citiesList: List<CitiesModel> = emptyList()
    private var citiesNameList: List<String> = emptyList()
    var citiesAdapter: ArrayAdapter<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCities()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleData()
        initView()
    }

    private fun initView() {
        with(binding) {
            btnEditProfile.setOnClickListener {
                viewModel.postInfo(
                    etName.text.toString(),
                    etSurname.text.toString(),
                    etPatronymic.text.toString(),
                    citiesList.first { it.name == etCity.text.toString() }.id,
                    etEmail.text.toString()
                )
            }
            etName.doAfterTextChanged {
                btnEditProfile.isEnabled = isAllNotEmpty()
            }
            etSurname.doAfterTextChanged {
                btnEditProfile.isEnabled = isAllNotEmpty()
            }
            etPatronymic.doAfterTextChanged {
                btnEditProfile.isEnabled = isAllNotEmpty()
            }
            etCity.doOnTextChanged { text, start, before, count ->
                if (text?.length == 0) {
                    citiesAdapter = ArrayAdapter(
                        requireContext(),
                        R.layout.item_city,
                        citiesNameList
                    )
                    etCity.setAdapter(citiesAdapter)
                } else {
                    citiesAdapter?.filter?.filter(text)

                }

            }
            etCity.doAfterTextChanged {

                btnEditProfile.isEnabled = isAllNotEmpty()
            }
            etEmail.doAfterTextChanged {
                btnEditProfile.isEnabled = isAllNotEmpty()
            }
        }
    }

    private fun isAllNotEmpty(): Boolean {
        return binding.etName.text.isNotEmpty()
                && binding.etSurname.text.isNotEmpty()
                && binding.etPatronymic.text.isNotEmpty()
                && binding.etCity.text.isNotEmpty()
                && binding.etEmail.text.isValidEmail()
    }

    private fun handleData() {
        with(viewModel) {
            citiesLive.observe(viewLifecycleOwner) {
                this@CompletionFragment.citiesList = it
                this@CompletionFragment.citiesNameList = it.map { it.name }
                citiesAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_city,
                    citiesNameList
                )
                binding.etCity.setAdapter(citiesAdapter)
            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
            successLive.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    R.id.action_completionFragment_to_mainFragment
                )
            }

        }

    }

}