package com.bilalov.testapplicationforappricot.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bilalov.testapplicationforappricot.R
import com.bilalov.testapplicationforappricot.settingsStorage.SettingCheckerStorageImpl
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.net.URL


var bitmapImage: MutableState<Bitmap?> = mutableStateOf(null)
private val storage = SettingCheckerStorageImpl

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Composable
fun SecondScreen(
    imageName: String?,
    startAddress: String?,
    endAddress: String?,
    orderDate: String?,
    price: String?,
    vehicleName: String?,
    vehicleCar: String?,
) {
    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    Log.e("CHECK_2", "${sharedPreferences.all.containsKey(imageName)}")


    if (isOnline(context) && !sharedPreferences.all.containsKey(imageName)) {

        GlobalScope.launch(Dispatchers.IO) {
            val imageUrl = URL("https://www.roxiemobile.ru/careers/test/images/$imageName")
            bitmapState.value =
                BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())

            val baos = ByteArrayOutputStream()
            bitmapState.value?.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            val compressImage = baos.toByteArray()
            val sEncodedImage: String = Base64.encodeToString(compressImage, Base64.DEFAULT)

            editor.apply {
                putString("$imageName", sEncodedImage)
                Log.d("PUT_STRING", "Key: $imageName")
            }.apply()

            storage.setChecked(newValue = true)
            storage.saveChanges()
            storage.init(context)

        }

    } else {
        loadData(mSharedPreferences = sharedPreferences, imageName, context)
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        item {
            Card(
                modifier = Modifier
                    .padding(57.dp, 50.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 8.dp,
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Log.e("CHECK_1", "${sharedPreferences.all.containsKey(imageName)}")
                    Log.e("CHECK_Storage", "${storage.getChecked()}")
                    if (!sharedPreferences.all.containsKey(imageName) && isOnline(context)) {
                        bitmapState.value?.asImageBitmap()?.let {
                            Image(
                                bitmap = it,
                                contentDescription = "With internet",
                            )
                        }
                    } else if (sharedPreferences.all.containsKey(imageName)) {
                        bitmapImage.value?.asImageBitmap()?.let {
                            Image(bitmap = it, contentDescription = "With out internet")
                            GlobalScope.launch {
                                delay(600000)
                                editor.remove(imageName).apply()
                            }
                        }
                    } else if (!isOnline(context)) {
                        val image: Painter = painterResource(id = R.drawable.error_img)
                        Image(
                            painter = image,
                            contentDescription = "Error",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Model Car: $vehicleCar",
                        )
                        Text(
                            text = "$vehicleName",
                            fontSize = 20.sp
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "From: $startAddress",
                            fontSize = 14.sp

                        )
                        Text(
                            text = "To: $endAddress",
                            fontSize = 14.sp
                        )
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            elevation = 5.dp,
                            modifier = Modifier
                                .padding(8.dp),
                            border = BorderStroke(1.dp, Color.Red)
                        ) {
                            Text(
                                text = "$price",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                        }
                    }
                    Text(
                        text = "$orderDate",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

private fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}

private fun loadData(
    mSharedPreferences: SharedPreferences,
    imageName: String?,
    context: Context,
) {
    if (mSharedPreferences.contains("$imageName")) {
        val encodedImage: String? = mSharedPreferences.getString("$imageName", "null")
        val b: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        bitmapImage.value = BitmapFactory.decodeByteArray(b, 0, b.size)
        storage.setChecked(newValue = false)
        storage.saveChanges()
        storage.init(context)
    }
}



