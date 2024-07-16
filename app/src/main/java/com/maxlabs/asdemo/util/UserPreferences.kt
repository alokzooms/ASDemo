package com.maxlabs.asdemo.util

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUserCredentials(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.USERNAME, username)
        editor.putString(Constants.PASSWORD, password)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(Constants.USERNAME, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(Constants.PASSWORD, null)
    }

    fun clearUserCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove(Constants.USERNAME)
        editor.remove(Constants.PASSWORD)
        editor.apply()
    }
}
