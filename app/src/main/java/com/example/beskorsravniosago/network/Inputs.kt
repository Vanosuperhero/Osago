package com.example.beskorsravniosago.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InputCoefs (
    val inputBase: String = "",
    val inputPower: String = "",
    val inputTerritory: String = "",
    val inputAccident: String = "",
    val inputAge: String = "",
    val inputLimit: String = "",
):Parcelable




