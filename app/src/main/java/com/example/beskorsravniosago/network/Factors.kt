package com.example.beskorsravniosago.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Factor (
    val title: String,
    val headerValue: String,
    val value: String,
    val name: String,
    val detailText: String,
):Parcelable

@Parcelize
data class Factors (
    val factors: List<Factor>
):Parcelable


