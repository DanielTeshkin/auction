package com.example.auctionapp.tools

import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun Fragment.toast(msg: String) {
 Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()
}

fun CharSequence?.isValidEmail() =
 !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun <T : ViewBinding> ViewGroup.inflate(
 inflateBinding: (
  inflater: LayoutInflater,
  root: ViewGroup?,
  attachToRoot: Boolean
 ) -> T, attachToRoot: Boolean = false
): T {
 val inflater = LayoutInflater.from(context)
 return inflateBinding(inflater, this, attachToRoot)
}