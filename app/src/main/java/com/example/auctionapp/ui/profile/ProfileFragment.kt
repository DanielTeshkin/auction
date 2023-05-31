package com.example.auctionapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.auctionapp.R
import com.example.auctionapp.databinding.FragmentProfileBinding
import com.example.auctionapp.domain.models.ProductModel
import com.example.auctionapp.tools.findTopNavController
import com.example.auctionapp.tools.toast
import com.example.auctionapp.ui.main.MainFragmentDirections
import com.example.auctionapp.ui.win_auctions.WinAuctionsFragmentDirections
import com.example.auctionapp.ui.win_auctions.WinAuctionsViewModel
import com.example.auctionapp.ui.win_auctions.adapter.WinAuctionAdapter
import com.example.auctionapp.ui.win_auctions.adapter.WinAuctionAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), WinAuctionAdapterDelegate.OnItemClickListener {

    private val viewModel by viewModels<ProfileViewModel>()
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private var productAdapter by autoCleared<WinAuctionAdapter>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInfo()
        viewModel.getProducts()
        handleData()
        initView()
    }


    private fun initView() {
        productAdapter = WinAuctionAdapter(this)
        with(binding.rv) {
            adapter = productAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
        }
    }


    private fun handleData() {
        with(viewModel) {
            progressLive.observe(viewLifecycleOwner) {
                binding.progress.isGone = !it
            }
            infoLive.observe(viewLifecycleOwner) {
                binding.fio.text = getString(
                    R.string.fio_text,
                    it.lastName,
                    it.firstName,
                    it.patronymic
                )

                binding.mail.text = getString(R.string.email, it.email)
                binding.number.text = getString(R.string.phone, it.phone)
                binding.address.text = getString(R.string.address, it.city?.displayName)
            }
            failLive.observe(viewLifecycleOwner) {
                toast(it)
            }
            productLive.observe(viewLifecycleOwner) { products ->
                binding.nothingToShow.isGone = products.isNotEmpty()
                productAdapter.items = products

            }

        }
    }

    override fun onItemClick(product: ProductModel) {
        findTopNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                product.id,
                false,
                ""
            )
        )
    }
}