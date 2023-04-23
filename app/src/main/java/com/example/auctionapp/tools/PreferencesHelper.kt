package com.example.auctionapp.tools

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(
@ApplicationContext context: Context
) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("mainPreferences", Context.MODE_PRIVATE)
    }
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var mAccessToken: String
        get() = sharedPreferences.getString("mAccessToken", "") ?: ""
        set(value) = sharedPreferences.edit { it.putString("mAccessToken", value) }

    var mRefreshToken: String
        get() = sharedPreferences.getString("mRefreshToken", "") ?: ""
        set(value) = sharedPreferences.edit {it.putString("mRefreshToken", value)}

}