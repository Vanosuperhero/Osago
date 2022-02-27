package com.example.beskorsravniosago.collections

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType


data class InputFieldData(
    var livedata: MutableState<String>,
    val title: Int,
    val titleAfter: Int,
    var placeholder: Int,
    var keyboardType: KeyboardType
    )

