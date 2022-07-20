package com.example.themovies.network.data

interface RecordClick {
    fun onRecordClickListener(id: Int, type: Record, customParameter: Any? = null)
}

enum class Record {
    Movie,
    TV,
    People
}