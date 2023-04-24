package com.example.auctionapp.ui.app

import android.app.Application
import com.example.auctionapp.tools.PreferencesHelper
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}