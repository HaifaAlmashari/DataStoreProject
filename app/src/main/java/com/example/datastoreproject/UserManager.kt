package com.example.datastoreproject

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    //Create the dataStore
    private val dataStore = context.createDataStore(name = "user_prefs")

    //Create some keys
    companion object {
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
    }

    //Store user data
    suspend fun storeUser(age: Int, name: String) {
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
        }
    }

    //Create an age flow
    // What is flow?
    // a type that can emit multiple values sequentially (Good)
    val userAgeFlow: Flow<Int> = dataStore.data.map {
        val age = it[USER_AGE_KEY] ?: 0

        if (age < 18) {
            Toast.makeText(context, "The user is under 18", Toast.LENGTH_SHORT).show()
        }
        age
    }

    //Create a name flow
    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }

}