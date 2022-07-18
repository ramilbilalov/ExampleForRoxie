package com.bilalov.testapplicationforappricot

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bilalov.testapplicationforappricot.navigation.Navigation
import com.bilalov.testapplicationforappricot.request.Response
import com.bilalov.testapplicationforappricot.ui.theme.TestApplicationForAppricotTheme
import com.bilalov.testapplicationforappricot.view.ErrorView
import com.bilalov.testapplicationforappricot.view.LoaderView
import com.bilalov.testapplicationforappricot.viewmodel.MainFactory
import com.bilalov.testapplicationforappricot.viewmodel.MainViewModel


var size: String = ""
var responseObject: Response? = null
lateinit var viewModel: MainViewModel

var url: String = "https://www.roxiemobile.ru/careers/test/orders.json"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TestApplicationForAppricotTheme {
                var isLoading by remember { mutableStateOf(false) }
                isLoading = true
                LoaderView(isLoading = isLoading) {
                }
            }
        }

        viewModel = ViewModelProvider(
            this,
            MainFactory(application, url)
        )[MainViewModel::class.java]

        viewModel.statusApi.observe(this) {

            if (viewModel.statusApi.value == "OK") {

                responseObject = viewModel.responseObject

                setContent {
                    TestApplicationForAppricotTheme {
                        val navController = rememberNavController()
                        Navigation(
                            navController,
                            responseObject = responseObject,
                            context = Application()
                        )
                    }
                }

            } else {
                setContent {
                    TestApplicationForAppricotTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            ErrorView(url = url, context = application, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

}

class RowProfile(
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


