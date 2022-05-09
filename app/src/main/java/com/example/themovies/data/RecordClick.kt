package com.example.themovies.data

interface RecordClick {
    fun onRecordClickListener(id: Int, type: Record)
}

enum class Record {
    Movie,
    TV,
    People
}