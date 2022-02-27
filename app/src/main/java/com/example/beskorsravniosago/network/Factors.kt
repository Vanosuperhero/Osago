package com.example.beskorsravniosago.network

data class Factor (
    val title: String,
    val headerValue: String,
    val value: String,
    val name: String,
    val detailText: String,
)

data class Factors (
    val factors: List<Factor>
)

