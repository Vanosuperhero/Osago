package com.example.beskorsravniosago.viewmodels

import androidx.compose.material.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beskorsravniosago.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.KeyboardType
import com.example.beskorsravniosago.collections.InputFieldData
import com.example.beskorsravniosago.collections.coefficients
import com.example.beskorsravniosago.collections.offers
import com.example.beskorsravniosago.network.Api
import com.example.beskorsravniosago.network.ApiOffer
import com.example.beskorsravniosago.network.Factors
import com.example.beskorsravniosago.network.Offers
import retrofit2.await

enum class ApiStatus{LOADING,ERROR,DONE}
class FirstScreenViewModel : ViewModel() {

    private val _statusCoefficients = mutableStateOf(ApiStatus.LOADING)
    var statusCoefficients : MutableState<ApiStatus> = _statusCoefficients

    private val _statusOffers = mutableStateOf(ApiStatus.LOADING)
    var statusOffers : MutableState<ApiStatus> = _statusOffers

    private val _expanded = mutableStateOf(false)
    var expanded: MutableState<Boolean> = _expanded

    private val _sheetState = mutableStateOf(false)
    var sheetState: MutableState<Boolean> = _sheetState

    fun refresh() {
        viewModelScope.launch {
            _expanded.value =! _expanded.value
        }
    }

    fun refreshSheet() {
        viewModelScope.launch {
            _sheetState.value =! _sheetState.value
        }
    }

    private val _showKeyboard = mutableStateOf(false)
    var showKeyboard: MutableState<Boolean> = _showKeyboard

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
            when (_field.value) {
                0 -> _inputBase.value = input
                1 -> inputPower.value = input
                2 -> _inputTerritory.value = input
                3 -> _inputAccident.value = input
                4 -> _inputAge.value = input
                5 -> _inputLimit.value = input
            }
        }
    }

    fun setImage(index: Int):Int {
        var painter = 0
        viewModelScope.launch {
            when (index) {
                0 -> painter = (R.drawable.soglas)
                1 -> painter = (R.drawable.inngo)
                2 -> painter = (R.drawable.alpha)
                3 -> painter = (R.drawable.sogaz_c)
                4 -> painter = (R.drawable.renes)
                5 -> painter = (R.drawable.uralsib)
            }
        }
        return painter
    }

    fun setDataToSheet(field: Int) {
        viewModelScope.launch {
            _field.value = field
            _showKeyboard.value =! _showKeyboard.value
        }
    }


    private val _liveCoefficients = mutableStateOf(coefficients)
    var liveCoefficients: MutableState<Factors> = _liveCoefficients

    private var i = 0

    fun getDataCoefficients(){
        viewModelScope.launch {
            val getDataDeferred = Api.retrofitApi.pushPost()
            if (i > 0) {
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
        i++
    }

    private val _liveOffers = mutableStateOf(offers)
    var liveOffers: MutableState<Offers> = _liveOffers

    private fun getDataOffers(){
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
        if (field.value < 5) {
            setDataToSheet(field.value + 1)
        } else{
            collapse()
        }
    }

    fun back() {
        if (field.value > 0) {
            setDataToSheet(field.value - 1)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    suspend fun expand(){
        bottomSheetScaffoldState.bottomSheetState.expand()
    }
    @OptIn(ExperimentalMaterialApi::class)
    suspend fun collapse(){
        bottomSheetScaffoldState.bottomSheetState.collapse()
    }

    @OptIn(ExperimentalMaterialApi::class)
    val bottomSheetScaffoldState = BottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed),
        drawerState = DrawerState(DrawerValue.Closed),
        snackbarHostState =  SnackbarHostState()
    )

    init {
        getDataOffers()
    }
}

