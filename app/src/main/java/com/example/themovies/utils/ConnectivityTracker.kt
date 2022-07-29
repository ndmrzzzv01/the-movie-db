package com.example.themovies.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.screens.RecordAdapter

class ConnectivityTracker {

    fun isNetworkConnected(context: Context): Boolean {
        val connect: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }

    fun recyclerViewConnect(recyclerView: RecyclerView, adapter: RecordAdapter, context: Context) {
        recyclerView.visibility = View.VISIBLE
        val gridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount && adapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
    }

}