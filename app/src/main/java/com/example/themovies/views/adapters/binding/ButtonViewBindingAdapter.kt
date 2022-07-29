package com.example.themovies.views.adapters.binding

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.example.themovies.network.data.RecordType
import com.like.LikeButton

@BindingAdapter("isLikedButton")
fun LikeButton.isLikedButton(value: Boolean?) {
    this.isLiked = value != false
}

@BindingAdapter("isVisibleViewIfListNotNull")
fun ImageButton.isVisibleViewIfListNotNull(list: List<RecordType>?) {
    if (list != null) this.visibility = View.VISIBLE
    else this.visibility = View.GONE

}