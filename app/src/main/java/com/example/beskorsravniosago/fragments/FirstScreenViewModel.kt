package com.example.beskorsravniosago.fragments


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FirstScreenViewModel : ViewModel() {


    init {

    }

    private val _expanded = MutableLiveData<Boolean>(false)
    var expanded: LiveData<Boolean> = _expanded

    fun refresh() {
        viewModelScope.launch {
            _expanded.value =! _expanded.value!!
        }
    }
}


