package com.example.themovies.network.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KnownForPerson(
    val id: Int? = 0
) : Parcelable
