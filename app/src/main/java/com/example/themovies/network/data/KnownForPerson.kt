package com.example.themovies.network.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KnownForPerson(
    val id: Int? = 0,
    @SerializedName("media_type") val type: String? = ""
) : Parcelable
