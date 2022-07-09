package com.example.themovies.network.data

interface RecordClick {
    fun onRecordClickListener(id: Int, type: Record)
}

enum class Record {
    Movie,
    TV,
    People
}