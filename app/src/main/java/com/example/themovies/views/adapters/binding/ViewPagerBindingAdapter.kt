package com.example.themovies.views.adapters.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.themovies.network.data.RecordType
import com.example.themovies.screens.detail.people.data.knownfor.KnownForAdapter

@BindingAdapter("observeList")
fun ViewPager2.observeList(list: List<RecordType>?) {
    val adapter = KnownForAdapter(listOf())

    list?.let {
        adapter.updateList(it)
    }
    this.adapter = adapter
}