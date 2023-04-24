package com.example.auctionapp.tools

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

fun EditText.textChangedFlow(): Flow<String> {
 return callbackFlow {
  val textChangedListener = object : TextWatcher {
   override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

   override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    trySendBlocking(s?.toString().orEmpty())
   }

   override fun afterTextChanged(s: Editable?) {}
  }
  this@textChangedFlow.addTextChangedListener(textChangedListener)
  awaitClose {
   this@textChangedFlow.removeTextChangedListener(textChangedListener)
  }
 }
}