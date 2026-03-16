package com.example.laba3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PizzaViewModel : ViewModel() {
    val orderSummary = MutableLiveData<String>()
}