package com.bilalov.testapplicationforappricot.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bilalov.testapplicationforappricot.RowProfile
import com.bilalov.testapplicationforappricot.request.Response
import com.bilalov.testapplicationforappricot.size
import com.bilalov.testapplicationforappricot.ui.theme.TestApplicationForAppricotTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun DefaultPreview(
    responseObject: Response?,
    navController: NavHostController,
) {

    TestApplicationForAppricotTheme {
        var refreshing by rememberSaveable { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress { _, dragAmount ->
                        Log.e("TTT_Scan", "$dragAmount")
                    }
                }
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = {
                    refreshing = true
                },
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(4f)
                        .background(Color.DarkGray)
                        .padding(bottom = 8.dp)
                ) {
                    items(count = 1) {
                        var i = 0
                        size = responseObject?.size.toString()
                        responseObject?.sortByDescending { it.orderTime } //Сортировка Json
                        if (!responseObject.isNullOrEmpty()) {
                            Log.e("RESPONSE_DEFAULT_PREVIEW", responseObject.toString())
                            Log.e("SIZE", size)

                            while (i < size.toInt()) { //Заполненение UI-списка
                                MyRow(
                                    item = RowProfile(
                                        startAddress = responseObject[i].startAddress.city + " " + responseObject[i].startAddress.address,
                                        vehicleName = responseObject[i].vehicle.driverName,
                                        vehicleCar = responseObject[i].vehicle.modelName,
                                        orderTime = responseObject[i].orderTime,
                                        endAddress = responseObject[i].endAddress.city + " " + responseObject[i].endAddress.address,
                                        priceAmount = responseObject[i].price.amount.toString(),
                                        priceCurrency = responseObject[i].price.currency,
                                        photo = responseObject[i].vehicle.photo,
                                        navController = navController
                                    )
                                )
                                i++
                            }
                        }
                    }
                }
            }
        }
    }
}

