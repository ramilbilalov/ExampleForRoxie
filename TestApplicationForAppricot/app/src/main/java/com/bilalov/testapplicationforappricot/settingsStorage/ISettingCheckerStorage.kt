package com.bilalov.testapplicationforappricot.settingsStorage

interface ISettingCheckerStorage {

    fun setChecked(newValue:Boolean)

    fun getChecked(): Boolean?

    fun saveChanges()


}