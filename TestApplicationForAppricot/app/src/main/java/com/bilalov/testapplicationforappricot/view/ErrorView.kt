package com.bilalov.testapplicationforappricot.view

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bilalov.testapplicationforappricot.R
import com.bilalov.testapplicationforappricot.ui.theme.TestApplicationForAppricotTheme
import com.bilalov.testapplicationforappricot.viewModel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun ErrorView(url: String, context: Application, viewModel: MainViewModel) {
    TestApplicationForAppricotTheme {

        var refreshing by rememberSaveable { mutableStateOf(false) }
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.radio_tower))
        val progress by animateLottieCompositionAsState(composition)

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress { _, dragAmount ->
                    }
                }
        ) {
            LaunchedEffect(refreshing) {
                if (refreshing) {
                    delay(1000)
                    viewModel.sendApi(url, context)
                    if (viewModel.statusApi.value == "OK") {
                        Toast.makeText(context, "Connection is good", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Error, connection is lost", Toast.LENGTH_SHORT)
                            .show()
                    }
                    refreshing = false
                }
            }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = {
                    refreshing = true
                },
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(count = 1) {
                        LottieAnimation(composition = composition, progress = progress)
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                        ) {
                            Text(
                                text = "Please check your internet connections and swipe up to refresh",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(35.dp, 0.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}