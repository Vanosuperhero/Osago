package com.example.beskorsravniosago.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Offers (
    val offers: List<Offer>,
    val actionText: String
)
@Parcelize
data class Offer (
    val name: String,
    val rating: Double,
    val price: Int,
    val branding: Branding
):Parcelable

@Parcelize

data class Branding (
    val fontColor: String,
    val backgroundColor: String,
    val iconTitle: String,
    val name: String,
    val bankLogoUrlPDF: String = "",
    val bankLogoUrlSVG: String = "",
):Parcelable
