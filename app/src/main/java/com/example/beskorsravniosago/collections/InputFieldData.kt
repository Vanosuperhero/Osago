package com.example.beskorsravniosago.collections

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType


data class InputFieldData(
    var livedata: MutableState<String>,
    var setFun: (String) -> Unit,
    val title: Int,
    var placeholder: Int,
    var keyboardType: KeyboardType
    )

