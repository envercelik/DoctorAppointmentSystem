package com.envercelik.doctorappointmentsystem

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:error")
fun TextInputLayout.error(errorMessage: String?) {
    error = errorMessage
    isErrorEnabled = errorMessage != null
}

@BindingAdapter("app:isVisible", requireAll = true)
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}