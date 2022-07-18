package com.bilalov.testapplicationforappricot.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.bilalov.testapplicationforappricot.request.Response
import com.bilalov.testapplicationforappricot.view.DefaultPreview
import com.bilalov.testapplicationforappricot.view.SecondScreen
import com.bilalov.testapplicationforappricot.viewmodel.MainViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    responseObject: Response?,
    context: Application?
) {
    NavHost(navController = navController, startDestination = Screen.Main.screenName) {
        composable(route = Screen.Main.screenName) {
            if (context != null) {
                DefaultPreview(
                    responseObject = responseObject,
                    navController = navController
                )
            }

        }
        composable(route = Screen.SecondView.screenName + "/{imageName}/{startAddress}/{date}/{endAddress}/{price}/{vehicleName}/{vehicleCar}",
            arguments = listOf(
                navArgument(name = route.toString()) {
                    type = NavType.StringType
                    defaultValue = "Unknown"
                    nullable = true
                }
            )) { entry ->
            SecondScreen(
                imageName = entry.arguments?.getString("imageName"),
                startAddress = entry.arguments?.getString("startAddress"),
                endAddress = entry.arguments?.getString("endAddress"),
                orderDate = entry.arguments?.getString("date"),
                price = entry.arguments?.getString("price"),
                vehicleName = entry.arguments?.getString("vehicleName"),
                vehicleCar = entry.arguments?.getString("vehicleCar")
            )
        }

    }
}



