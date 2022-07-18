package com.bilalov.testapplicationforappricot.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoaderView(
    isLoading: Boolean,
    content: @Composable () -> Unit,
) = if (isLoading) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
    ) {

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(count = 1) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 10.dp)
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading...", modifier = Modifier.padding(7.dp))
                }
            }
        }
    }
} else {
    content()
}
