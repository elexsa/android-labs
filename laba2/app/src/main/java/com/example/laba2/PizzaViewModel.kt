package com.example.laba2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PizzaViewModel : ViewModel() {
    val orderSummary = MutableLiveData<String>()
}