package com.example.auctionapp.ui.login.pass_recovery

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.FragmentPassportRecoveryBinding
import com.example.auctionapp.databinding.PasswordRecoveryFragmentBinding
import com.example.auctionapp.tools.toast
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
                    R.id.action_passwordRecoveryFragment_to_mainFragment
                )
            }
            resetPassFail.observe(viewLifecycleOwner) {
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