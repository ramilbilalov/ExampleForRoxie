package com.bilalov.testapplicationforappricot.settingsStorage

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.util.Base64
import com.bilalov.testapplicationforappricot.view.bitmapImage


object SettingCheckerStorageImpl : ISettingCheckerStorage {
    private const val prefName = "SettingCheckerStoragePrefs"
    private const val prefFirstCheck = "$prefName.firstCheck"

    private var sharedPreferences: SharedPreferences? = null

    private var secondCheck: Boolean = false


    fun init(context: Context) {

        if (sharedPreferences != null) {
            return
        }
        sharedPreferences =
            context.applicationContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        secondCheck = sharedPreferences?.getBoolean(prefFirstCheck, false) ?: true
    }

    override fun setChecked(newValue: Boolean) {
        secondCheck = newValue
    }

    override fun getChecked(): Boolean {
        return secondCheck
    }

    override fun saveChanges() {
        sharedPreferences?.edit()?.putBoolean(prefFirstCheck, secondCheck)?.apply()

    }

    override fun loadData(
        mSharedPreferences: SharedPreferences,
        imageName: String?,
        context: Context,
        storage: ISettingCheckerStorage
    ) {
        if (mSharedPreferences.contains("$imageName")) {
            val encodedImage: String? = mSharedPreferences.getString("$imageName", "null")
            val b: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            bitmapImage.value = BitmapFactory.decodeByteArray(b, 0, b.size)
        }
    }


}