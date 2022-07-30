package com.example.themovies.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseListViewModel : ViewModel() {

    var retryLoadData = {}

    private val _isVisibleList = MutableLiveData(false)
    val isVisibleList: LiveData<Boolean> = _isVisibleList

    private val _isVisibleLoading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _isVisibleLoading

    private val _isVisibleError = MutableLiveData(false)
    val error: LiveData<Boolean> = _isVisibleError

    fun showList() {
        _isVisibleList.value = true
        _isVisibleLoading.value = false
        _isVisibleError.value = false
    }

    fun showLoading() {
        _isVisibleList.value = false
        _isVisibleLoading.value = true
        _isVisibleError.value = false
    }

    fun showError() {
        _isVisibleList.value = false
        _isVisibleLoading.value = false
        _isVisibleError.value = true
    }

}