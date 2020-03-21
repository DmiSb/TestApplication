package ru.dmisb.test_app.ui.common

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment

// https://habr.com/ru/post/489746/

const val DEFAULT_BUNDLE_KEY = "default_bundle_key"

class BinderReference<T>(val value: T?) : Binder()

val Any?.bundle: Bundle?
    get() = if (this == null) null else Bundle().also {
        it.putBinder(DEFAULT_BUNDLE_KEY, BinderReference(this))
    }

inline fun <reified T> Bundle?.value(): T =
    this?.getBinder(DEFAULT_BUNDLE_KEY)?.let {
        if (it is BinderReference<*>) it.value as T else null
    } as T

inline fun <reified Arg> Fragment.defaultArg() = lazy<Arg>(LazyThreadSafetyMode.NONE) {
    arguments.value()
}