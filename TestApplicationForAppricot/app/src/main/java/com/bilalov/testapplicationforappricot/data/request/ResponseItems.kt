package com.bilalov.testapplicationforappricot.data.request

data class ResponseItems(
    val endAddress: EndAddress,
    val id: Int,
    val orderTime: String,
    val price: Price,
    val startAddress: StartAddress,
    val vehicle: Vehicle
)