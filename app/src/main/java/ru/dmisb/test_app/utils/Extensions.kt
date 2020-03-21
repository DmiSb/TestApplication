package ru.dmisb.test_app.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import ru.dmisb.test_app.R

fun TextInputLayout.setIsValid(isValid: Boolean?) {
    this.setEndIconDrawable(if (isValid == true) R.drawable.ic_check else 0)
}

fun MaterialButton.enabled(isEnabled: Boolean?) {
    this.isEnabled = isEnabled == true
    this.alpha = if (isEnabled == true) 1f else 0.5f
}

fun View?.hideKeyboard() {
    if (this != null) {
        val context = this.context
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun Context?.alert(
    title: String,
    message: String,
    positive: (() -> Unit)? = null,
    negative: (() -> Unit)? = null
) {
    this?.let {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
        builder.setPositiveButton(R.string.button_ok) { _, _ ->
            positive?.invoke()
        }
        if (negative != null)
           builder.setNegativeButton(R.string.button_cancel) { _, _ ->
               negative.invoke()
           }
        val dialog = builder.create()
        dialog.show()
    }
}