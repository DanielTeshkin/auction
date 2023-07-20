package com.auction.mobile.ui.register.code

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.ConfirmCodeFragmentBinding
import com.auction.mobile.tools.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmCodeFragment : Fragment(R.layout.confirm_code_fragment) {

    companion object {
        const val IS_IT_REGISTER = "IS_IT_REGISTER"
        const val PHONE_NUMBER = "PHONE_NUMBER"
    }

    private val binding by viewBinding(ConfirmCodeFragmentBinding::bind)
    private val viewModel by viewModels<ConfirmCodeViewModel>()
    private val args by navArgs<ConfirmCodeFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleUI()
        handleData()
    }

    private fun handleData() {
        with(viewModel) {
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
            successLive.observe(viewLifecycleOwner) {
                if (args.IsPassRecovery) {
                    findNavController().navigate(
                        ConfirmCodeFragmentDirections.actionConfirmCodeFragmentToPasswordRecoveryFragment(
                            args.number.replace(Regex("[^\\d]"), "")
                        )
                    )
                } else {
                    findNavController().navigate(
                        R.id.action_confirmCodeFragment_to_loginFragment,
                        bundleOf(
                            IS_IT_REGISTER to true,
                            PHONE_NUMBER to args.number.replace(Regex("[^\\d]"), "")
                        )
                    )
                }

            }
        }
    }

    private fun handleUI() {
        with(binding) {
            btnSignInNext.setOnClickListener {
                if (args.IsPassRecovery) {
                    viewModel.sendCodeForPassRecovery(
                        getString(
                            R.string.number_format,
                            args.number.replace(Regex("[^\\d]"), "")
                        ), etCode.text.toString()
                    )
                } else {
                    viewModel.sendCode(
                        getString(
                            R.string.number_format,
                            args.number.replace(Regex("[^\\d]"), "")
                        ), etCode.text.toString()
                    )
                }
            }
            etCode.doAfterTextChanged {
                btnSignInNext.isEnabled = etCode.text.isNotEmpty()
            }
            subtitle.text = getString(R.string.confirm_number_subtitle_text, args.number)
        }
    }


}