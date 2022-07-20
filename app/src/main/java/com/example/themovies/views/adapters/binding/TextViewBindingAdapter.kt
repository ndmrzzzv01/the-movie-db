package com.example.themovies.views.adapters.binding

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("textOrHideIfEmpty")
fun setTextOrHideIfEmpty(view: TextView, value: String?) {
    view.isVisible = value != "0"
}

@BindingAdapter("hideText")
fun hideText(view: TextView, value: String?) {
    view.isVisible = value?.isNotBlank() == true
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

@BindingAdapter("url")
fun setUrl(view: TextView, url: String?) {
    if (url?.isNotBlank() == true) {
        view.visibility = View.VISIBLE
        view.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            view.context.startActivity(intent)
        }
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("textMinutes")
fun setTextForMinutes(view: TextView, value: String?) {
    view.text = "$value minutes"
}