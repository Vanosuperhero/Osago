package com.example.beskorsravniosago.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beskorsravniosago.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import com.example.beskorsravniosago.collections.InputFieldData
import com.example.beskorsravniosago.network.Api
import com.example.beskorsravniosago.network.Factors
import com.example.beskorsravniosago.network.coefficients
import retrofit2.await


class FirstScreenViewModel : ViewModel() {

    private val _expanded = mutableStateOf(false)
    var expanded: MutableState<Boolean> = _expanded

    fun refresh() {
        viewModelScope.launch {
            _expanded.value =! _expanded.value
        }
    }

    private val _input1 = mutableStateOf("")
    private var input1: MutableState<String> = _input1

    private val _input2 = mutableStateOf("")
    private var input2: MutableState<String> = _input2

    private val _input3 = mutableStateOf("")
    private var input3: MutableState<String> = _input3

    private val _input4 = mutableStateOf("")
    private var input4: MutableState<String> = _input4

    private val _input5 = mutableStateOf("")
    private var input5: MutableState<String> = _input5

    private val _input6 = mutableStateOf("")
    private var input6: MutableState<String> = _input6

    var field1 = InputFieldData(input1,::setInput1,R.string.first_field,R.string.first_placeholder,KeyboardType.Text)
    var field2 = InputFieldData(input2,::setInput2,R.string.second_field,R.string.second_placeholder,KeyboardType.Number)
    var field3 = InputFieldData(input3,::setInput3,R.string.third_field,R.string.third_placeholder,KeyboardType.Number)
    var field4 = InputFieldData(input4,::setInput4,R.string.fourth_field,R.string.fourth_placeholder,KeyboardType.Number)
    var field5 = InputFieldData(input5,::setInput5,R.string.fifth_field,R.string.fifth_placeholder,KeyboardType.Number)
    var field6 = InputFieldData(input6,::setInput6,R.string.sixth_field,R.string.sixth_placeholder,KeyboardType.Number)

    private val _field = mutableStateOf(field1)
    var field: MutableState<InputFieldData> = _field

    private fun setInput1(input: String) {
        viewModelScope.launch {
            _input1.value = input
        }
    }
    private fun setInput2(input: String) {
        viewModelScope.launch {
            _input2.value = input
        }
    }
    private fun setInput3(input: String) {
        viewModelScope.launch {
            _input3.value = input
        }
    }
    private fun setInput4(input: String) {
        viewModelScope.launch {
            _input4.value = input
        }
    }
    private fun setInput5(input: String) {
        viewModelScope.launch {
            _input5.value = input
        }
    }
    private fun setInput6(input: String) {
        viewModelScope.launch {
            _input6.value = input
        }
    }
    fun setDataToSheet(field: InputFieldData) {
        viewModelScope.launch {
            _field.value = field
        }
    }

    private val _liveCoefficients = mutableStateOf(coefficients)
    var liveCoefficients: MutableState<Factors> = _liveCoefficients

    private var i = 0

    fun getDataCoefficients(){
        viewModelScope.launch {
            val getDataDeferred = Api.retrofitService.pushPost()
            if (i > 0) {
                try {
                    val listResult = getDataDeferred.await()
                    _liveCoefficients.value = listResult
                }catch (e:Exception) {
                }
            }
            i++
        }
    }

}

