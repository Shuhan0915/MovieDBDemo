package com.example.moviedbdemo.utils

import androidx.lifecycle.MutableLiveData

// extension method to merge new list into MutableLiveData<List<T>>
fun <T> MutableLiveData<List<T>>.addAll(items: List<T>?) {
    val updatedItems = this.value as ArrayList
    if (items != null) {
        updatedItems.addAll(items)
    }
    this.postValue(updatedItems)
}