package com.example.pruebaa.data

import android.content.Context

class Prefs (context: Context){

    val SHARED_NAME="UsersSh"
    val SHARED_USER_DATA="userData"
    val storage=context.getSharedPreferences(SHARED_NAME, 0)

    fun saveUsers(user:String){
        storage.edit().putString(SHARED_USER_DATA, user).apply()
    }

    fun getUsers():String{
        return storage.getString(SHARED_USER_DATA, "")!!
    }

}