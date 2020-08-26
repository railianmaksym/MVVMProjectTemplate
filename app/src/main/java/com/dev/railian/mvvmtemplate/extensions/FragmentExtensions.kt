package com.dev.railian.mvvmtemplate.extensions

import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

fun Fragment.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = requireView().systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        requireView().systemUiVisibility = flags
    }
}

fun Fragment.applyTopInset(vararg views: View) {
    ViewCompat.setOnApplyWindowInsetsListener(requireView()) { _, insets ->
        views.forEach {
            it.setPadding(
                it.paddingLeft,
                insets.systemWindowInsetTop,
                it.paddingRight,
                it.paddingBottom
            )
        }
        insets
    }
}