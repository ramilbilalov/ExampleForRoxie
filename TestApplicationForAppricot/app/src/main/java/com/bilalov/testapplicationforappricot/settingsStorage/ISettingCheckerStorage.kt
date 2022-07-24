package com.bilalov.testapplicationforappricot.settingsStorage

import android.content.Context
import android.content.SharedPreferences

interface ISettingCheckerStorage {

    fun setChecked(newValue:Boolean)

    fun getChecked(): Boolean?

    fun saveChanges()

    fun loadData(mSharedPreferences: SharedPreferences,
                 imageName: String?,
                 context: Context, storage: ISettingCheckerStorage)

}