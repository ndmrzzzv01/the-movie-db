package com.example.themovies.network.data

import com.google.gson.annotations.SerializedName

data class Person(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("profile_path") val posterPath: String? = null,
    val biography: String? = null,
    val birthday: String? = null,
    @SerializedName("place_of_birth") val placeOfBirth: String? = null,
    @SerializedName("known_for_department") val knownForDepartment: String? = null
) : RecordType()
