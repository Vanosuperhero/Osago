package com.example.beskorsravniosago.network

data class Offers (
    val offers: List<Offer>,
    val actionText: String
)

data class Offer (
    val name: String,
    val rating: Double,
    val price: Int,
    val branding: Branding
)

data class Branding (
    val fontColor: String,
    val backgroundColor: String,
    val iconTitle: String,
    val name: String,
    val bankLogoUrlPDF: String = "",
    val bankLogoUrlSVG: String = "",
)