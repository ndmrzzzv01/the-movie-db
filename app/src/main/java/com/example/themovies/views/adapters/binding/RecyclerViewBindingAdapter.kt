package com.example.themovies.views.adapters.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.network.data.Actor
import com.example.themovies.network.data.RecordType
import com.example.themovies.screens.InnerItemsAdapter

@BindingAdapter("observeList")
fun RecyclerView.observeList(list: List<RecordType>?) {
    if (list != null) {
        this.visibility = View.VISIBLE
        this.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        this.adapter = InnerItemsAdapter(list)
    }
}