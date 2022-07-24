package com.bilalov.testapplicationforappricot.data.request.rowData

import androidx.navigation.NavController

data class RowInfo(
    var startAddress: String,
    var vehicleName: String,
    var orderTime: String,
    var endAddress: String,
    var priceAmount: String,
    var priceCurrency: String,
    var photo: String,
    var navController: NavController,
    var vehicleCar: String
)