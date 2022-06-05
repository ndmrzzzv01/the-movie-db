package com.example.themovies.data

import com.google.gson.annotations.SerializedName

data class People(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("profile_path") val profilePath: String? = null,
    val biography: String? = null,
    val birthday: String? = null,
    @SerializedName("place_of_birth") val placeOfBirth: String? = null,
    @SerializedName("known_for_department") val knownForDepartment: String? = null
): RecordType()
