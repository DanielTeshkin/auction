package com.auction.mobile.ui.login.pass_recovery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.FragmentPassportRecoveryBinding
import com.auction.mobile.tools.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordRecoveryFragment : Fragment(R.layout.fragment_passport_recovery) {

    private val binding by viewBinding(FragmentPassportRecoveryBinding::bind)
    private val viewModel by viewModels<PasswordRecoveryViewModel>()
    private val args by navArgs<PasswordRecoveryFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClick()
        handleUi()
        handleLive()
    }

    private fun handleLive() {
        with(viewModel) {
            resetPassSuccess.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    R.id.action_passwordRecoveryFragment_to_loginFragment
                )
            }
            resetPassFail.observe(viewLifecycleOwner) {
                Log.d("TTT", "resetPassFail $it")
                toast(it)
            }
        }
    }

    private fun handleUi() {
        binding.etPassword.doAfterTextChanged {
            binding.btnSignInNext.isEnabled = checkLength()
        }
        binding.etConfirmNewPassword.doAfterTextChanged {
            binding.btnSignInNext.isEnabled = checkLength()
        }
    }

    private fun checkLength(): Boolean {
        return binding.etPassword.text.length == binding.etConfirmNewPassword.text.length
                && binding.etConfirmNewPassword.text.isNotEmpty()
                && binding.etPassword.text.isNotEmpty()
    }

    private fun handleClick() {
        binding.btnSignInNext.setOnClickListener {
            viewModel.passReset(
                args.number,
                binding.etPassword.text.toString(),
                binding.etConfirmNewPassword.text.toString()
            )
        }
    }

}