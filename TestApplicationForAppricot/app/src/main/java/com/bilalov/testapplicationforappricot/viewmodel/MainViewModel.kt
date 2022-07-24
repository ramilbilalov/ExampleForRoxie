package com.bilalov.testapplicationforappricot.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bilalov.testapplicationforappricot.data.request.Response
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainViewModel(application: Application, url: String) : ViewModel() {

    val statusApi = MutableLiveData<String>()

    private var id: String = ""
    var responseObject: Response? = null

    init {
        sendApi(url, application)
    }

    fun sendApi(url: String, application: Application) {
        Fuel.get(url)
            .response { request, response, result ->

                Log.d("TAG_REQ_1", request.toString())
                Log.d("TAG_REQ_2", response.toString())
                Log.d("TAG_REQ_3", result.toString())

                val responseObjectType =
                    object : TypeToken<Response>() {}.type

                if (response.isSuccessful) {
                    responseObject = Gson().fromJson(
                        response.body().asString("application/json; charset=utf-8"),
                        responseObjectType
                    ) as? Response

                    id = responseObject?.get(0)?.id.toString()
                    statusApi.value = response.responseMessage

                } else {
                    statusApi.value = response.responseMessage
                    Toast.makeText(
                        application,
                        "Код ошибки: ${response.statusCode}. Проверьте интернет подключение",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}