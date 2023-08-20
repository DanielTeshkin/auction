package com.auction.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.auction.mobile.databinding.ActivityMainBinding
import com.auction.mobile.tools.PreferencesHelper
import com.auction.mobile.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), Logout {

    @Inject
    lateinit var prefs: PreferencesHelper
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCities()
        viewModel.getAllFavorite {}
        viewModel.getBid()
            if (prefs.mAccessToken != "") {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(binding.fragment.id) as NavHostFragment
                val navController = navHostFragment.navController
                val inflater = navController.navInflater
                val graph = inflater.inflate(R.navigation.nav_graph)
                graph.setStartDestination(R.id.mainFragment)
                navController.graph = graph
            }
    }

    private fun setStartDest(dest: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragment.id) as NavHostFragment
        val navController = navHostFragment.navController
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(dest)
        navController.graph = graph
    }

    override fun logout(dest: Int) {
        setStartDest(dest)
    }
}