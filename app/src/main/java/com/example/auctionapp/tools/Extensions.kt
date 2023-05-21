package com.example.auctionapp.tools

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.auctionapp.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.toast(msg: String) {
 Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show()
}

fun CharSequence?.isValidEmail() =
 !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Fragment.findTopNavController(): NavController {
 val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment?
 return topLevelHost?.navController ?: findNavController()
}

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

 fun String.isDateAfterToday(): Boolean {
 val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
 val dateTime = LocalDateTime.parse(this, formatter)
 val now = LocalDateTime.now()
 return dateTime.isAfter(now)
}

fun String.getOnlyTime(): String {
 val inputDate = this
 val inputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
 val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

 val date = inputFormat.parse(inputDate)
 return date?.let { outputFormat.format(it) } ?: ""
}

fun String.isDateBeforeToday(): Boolean {
 val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
 val dateTime = LocalDateTime.parse(this, formatter)
 val now = LocalDateTime.now()
 return dateTime.isBefore(now)
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