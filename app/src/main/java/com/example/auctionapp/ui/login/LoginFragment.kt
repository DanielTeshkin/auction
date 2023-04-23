package com.example.auctionapp.ui.login

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.LoginFragmentBinding
import com.example.auctionapp.tools.PreferencesHelper
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.register.code.ConfirmCodeFragment.Companion.IS_IT_REGISTER
import com.example.auctionapp.ui.register.code.ConfirmCodeFragment.Companion.PHONE_NUMBER
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.login_fragment) {

    private var isItRegister = false
    private var number: String? = null
    private val binding by viewBinding(LoginFragmentBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()
    private var isMaskFilled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.takeIf {
            it.containsKey(IS_IT_REGISTER)
        }?.let {
            isItRegister = it.getBoolean(IS_IT_REGISTER)
        }
        arguments?.takeIf {
            it.containsKey(PHONE_NUMBER)
        }?.let {
            number = it.getString(PHONE_NUMBER)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        handleLive()
        phoneNumberMask()

    }

    private fun handleLive() {
        with(viewModel) {
            successLive.observe(viewLifecycleOwner) {
                if (isItRegister) {
                    findNavController().navigate(R.id.action_loginFragment_to_completionFragment)
                } else {
                    findNavController().navigate(R.id.action_loginFragment_to_searchFragment)
                }
            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
        }

    }

    private fun isNotEmpty(): Boolean {
        return binding.etNumber.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()
    }
    private fun initUi() {
        with(binding) {
            number?.let {
                etNumber.setText(it)
            }
            btnSignInNext.setOnClickListener {
                    viewModel.sendLogoPass(getString(R.string.number_format, binding.etNumber.text.toString().replace(Regex("[^\\d]"), "")), etPassword.text.toString(), isItRegister)
            }
            etNumber.doAfterTextChanged {
                btnSignInNext.isEnabled = isNotEmpty()
            }
            etPassword.doAfterTextChanged {
                btnSignInNext.isEnabled = isNotEmpty()
            }
            if (isItRegister) {
                btnForgotPassword.isGone = true
                imageButton.isGone = true
                title.text = getString(R.string.create_pass)
                subtitle.text = getString(R.string.create_pass_subtitle)
            } else {
                title.text = getString(R.string.login_title_text)
                subtitle.text = getString(R.string.login_subtitle_text)
            }
            imageButton.setOnClickListener {
                findNavController().popBackStack()
            }
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

