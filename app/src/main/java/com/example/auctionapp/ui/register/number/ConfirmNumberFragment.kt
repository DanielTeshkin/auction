package com.example.auctionapp.ui.register.number

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.ConfirmNumberFragmentBinding
import com.example.auctionapp.domain.models.ConfirmPhoneModel
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.login.LoginFragment.Companion.FORGOT_PASS
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfirmNumberFragment : Fragment(R.layout.confirm_number_fragment) {

    private val viewModel by viewModels<ConfirmNumberViewModel>()
    private val binding by viewBinding(ConfirmNumberFragmentBinding::bind)
    private var forgotPass = false

    private var isMaskFilled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf {
            it.containsKey(FORGOT_PASS)
        }?.let {
            forgotPass = it.getBoolean(FORGOT_PASS)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleData()
        phoneNumberMask()
        handleView()
    }

    private fun handleView() {
        if (forgotPass) {
            binding.btnSignInNext.setOnClickListener {
                viewModel.sendNumberForPassRecovery(getString(R.string.number_format, binding.etNumber.text.toString().replace(Regex("[^\\d]"), "")))
            }
            binding.title.text = "Восстановление пароля"
        } else {
            binding.btnSignInNext.setOnClickListener {
                viewModel.sendNumber(getString(R.string.number_format, binding.etNumber.text.toString().replace(Regex("[^\\d]"), "")))
            }
        }

    }

    private fun handleData() {
        viewModel.dataFlow.observe(viewLifecycleOwner) {
            findNavController().navigate(
                ConfirmNumberFragmentDirections.actionConfirmNumberFragmentToConfirmCodeFragment(
                    number = binding.etNumber.text.toString(),
                    isPassRecovery = forgotPass
                )
            )
        }
        viewModel.failFlow.observe(viewLifecycleOwner) { message ->
            Log.d("failFlow", message)
            toast(message)
        }
        viewModel.progressFlow.observe(viewLifecycleOwner) {
            binding.progress.isGone = !it
        }
    }

    private fun phoneNumberMask() {
        val affineFormats: MutableList<String> = ArrayList()
        affineFormats.add("([000]) [000]-[00]-[00]")
        val listener =
            MaskedTextChangedListener.Companion.installOn(binding.etNumber,
                "([000]) [000]-[00]-[00]",
                affineFormats,
                AffinityCalculationStrategy.PREFIX,
                object : MaskedTextChangedListener.ValueListener {
                    override fun onTextChanged(
                        maskFilled: Boolean, extractedValue: String,
                        formattedValue: String
                    ) {
                        binding.etNumber.setTextColor(Color.parseColor("#3E4155"))
                        isMaskFilled = maskFilled
                        if (maskFilled) {
                            val imm: InputMethodManager =
                                context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view?.windowToken, 0)
                        }
                        binding.btnSignInNext.isEnabled = isMaskFilled
                    }
                }
            )

        binding.etNumber.hint = listener.placeholder()
    }
}