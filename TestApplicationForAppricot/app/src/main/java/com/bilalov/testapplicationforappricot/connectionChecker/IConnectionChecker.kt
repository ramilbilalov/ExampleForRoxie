package com.bilalov.testapplicationforappricot.connectionChecker

import android.content.Context

interface IConnectionChecker {

    fun isOnline(context: Context): Boolean

}