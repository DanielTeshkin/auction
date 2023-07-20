package com.auction.mobile.ui.open

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.R
import com.auction.mobile.databinding.OpenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenFragment: Fragment(R.layout.open_fragment) {

    private val binding by viewBinding(OpenFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            btnSignUp.setOnClickListener {
                findNavController().navigate(
                    R.id.action_openFragment_to_confirmNumberFragment
                )
            }
            btnSignIn.setOnClickListener {
                findNavController().navigate(
                    R.id.action_openFragment_to_loginFragment
                )
            }
        }
    }

}