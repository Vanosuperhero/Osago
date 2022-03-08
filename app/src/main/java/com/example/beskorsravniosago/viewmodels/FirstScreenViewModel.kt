package com.example.beskorsravniosago.viewmodels

import androidx.compose.material.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beskorsravniosago.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import com.example.beskorsravniosago.collections.InputFieldData
import com.example.beskorsravniosago.collections.coefficients
import com.example.beskorsravniosago.collections.offers
import com.example.beskorsravniosago.network.*
import retrofit2.await

enum class ApiStatus{LOADING,ERROR,DONE}
enum class Inputs{BASE,POWER,TERRITORY,ACCIDENT,AGE,LIMIT}

class FirstScreenViewModel : ViewModel() {

    private val _statusCoefficients = mutableStateOf(ApiStatus.LOADING)
    var statusCoefficients : MutableState<ApiStatus> = _statusCoefficients

    private val _statusOffers = mutableStateOf(ApiStatus.LOADING)
    var statusOffers : MutableState<ApiStatus> = _statusOffers

    private val _expanded = mutableStateOf(false)
    var expanded: MutableState<Boolean> = _expanded

    fun refresh() {
        viewModelScope.launch {
            _expanded.value =! _expanded.value
        }
    }

    private val _inputBase = mutableStateOf("")
    private var inputBase: MutableState<String> = _inputBase

    private val _inputPower = mutableStateOf("")
    private var inputPower: MutableState<String> = _inputPower

    private val _inputTerritory = mutableStateOf("")
    private var inputTerritory: MutableState<String> = _inputTerritory

    private val _inputAccident = mutableStateOf("")
    private var inputAccident: MutableState<String> = _inputAccident

    private val _inputAge = mutableStateOf("")
    private var inputAge: MutableState<String> = _inputAge

    private val _inputLimit = mutableStateOf("")
    private var inputLimit: MutableState<String> = _inputLimit


    private var fieldBase = InputFieldData(inputBase,R.string.first_field,R.string.first_field_after,R.string.first_placeholder,KeyboardType.Text)
    private var fieldPower = InputFieldData(inputPower,R.string.second_field,R.string.second_field,R.string.second_placeholder,KeyboardType.Number)
    private var fieldTerritory = InputFieldData(inputTerritory,R.string.third_field,R.string.third_field,R.string.third_placeholder,KeyboardType.Number)
    private var fieldAccident = InputFieldData(inputAccident,R.string.fourth_field,R.string.fourth_field,R.string.fourth_placeholder,KeyboardType.Number)
    private var fieldAge = InputFieldData(inputAge,R.string.fifth_field,R.string.fifth_field,R.string.fifth_placeholder,KeyboardType.Number)
    private var fieldLimit = InputFieldData(inputLimit,R.string.sixth_field,R.string.sixth_field,R.string.sixth_placeholder,KeyboardType.Number)

    val fieldList = listOf(fieldBase, fieldPower, fieldTerritory, fieldAccident, fieldAge, fieldLimit)

    private val _field = mutableStateOf(0)
    var field: MutableState<Int> = _field

    fun setInput(input: String) {
        viewModelScope.launch {
            fieldList[field.value].livedata.value = input
        }
    }

    fun setDataToSheet(field: Int) {
        viewModelScope.launch {
            _field.value = field
        }
    }

    private val _liveCoefficients = mutableStateOf(coefficients)
    var liveCoefficients: MutableState<Factors> = _liveCoefficients


    fun getDataCoefficients(){
        viewModelScope.launch {
            val getDataDeferred = Api.retrofitApi.pushPost()
                try {
                    _statusCoefficients.value = ApiStatus.LOADING
                    val listResult = getDataDeferred.await()
                    _liveCoefficients.value = listResult
                    _statusCoefficients.value = ApiStatus.DONE
                }catch (e:Exception) {
                    _statusCoefficients.value = ApiStatus.ERROR
                }
        }
    }

    private val _liveOffers = mutableStateOf(offers)
    var liveOffers: MutableState<Offers> = _liveOffers

    fun getDataOffers(){
        viewModelScope.launch {
            val getDataDeferred = ApiOffer.retrofitApi.pushPost()
            try {
                _statusOffers.value = ApiStatus.LOADING
                val listResult = getDataDeferred.await()
                _liveOffers.value = listResult
                _statusOffers.value = ApiStatus.DONE
            }catch (e:Exception) {
                _statusOffers.value = ApiStatus.ERROR
            }
        }
    }

    suspend fun next() {
        if (Inputs.values()[_field.value] != Inputs.LIMIT) {
            setDataToSheet(field.value + 1)
        } else{
            collapse()
        }
    }

    fun back() {
        if (Inputs.values()[_field.value] != Inputs.BASE) {
            setDataToSheet(field.value - 1)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    suspend fun expand(){
        bottomSheetScaffoldState.show()
    }
    @OptIn(ExperimentalMaterialApi::class)
    suspend fun collapse(){
        bottomSheetScaffoldState.hide()
    }

    private val _confirm = mutableStateOf(false)
    var confirm: MutableState<Boolean> = _confirm


    fun confirm() {
        viewModelScope.launch {
            _confirm.value  = true
        }
    }

    fun unConfirm() {
        viewModelScope.launch {
            _confirm.value  = false
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    val bottomSheetScaffoldState = ModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
            isSkipHalfExpanded = true,
    )

    private val _offer: MutableState<Offer?> = mutableStateOf(null)
    var offer: MutableState<Offer?> = _offer

    fun offer(offer: Offer?) {
        viewModelScope.launch {
            _offer.value  = offer
        }
    }


}

