package com.example.themovies.views.adapters.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("textOrHideIfEmpty")
fun setTextOrHideIfEmpty(view: TextView, value: String?) {
    if (value == "0") {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("dotAfterThreeNumbers")
fun setDotAfterThreeNumbers(view: TextView, value: String?) {
    val length = value?.length ?: 0
    if (length > 3) {
        val builder = StringBuilder(value.toString())
        var dots = length / 3
        if (length % 3 == 0) dots -= 1
        for (i in 1..dots) {
            builder.insert(length - (i * 3), ".")
        }
        view.text = "$builder$"
    } else {
        view.text = "$value$"
    }
}