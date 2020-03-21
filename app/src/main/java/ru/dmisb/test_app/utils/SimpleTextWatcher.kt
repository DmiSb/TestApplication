package ru.dmisb.test_app.utils

import android.text.Editable
import android.text.TextWatcher

open class SimpleTextWatcher(
    private val textChanged: (text: String) -> Unit
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable?) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textChanged.invoke(s.toString())
    }
}