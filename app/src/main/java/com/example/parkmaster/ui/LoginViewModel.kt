package com.example.parkmaster.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkmaster.data.UserDatabase
import com.parse.Parse
import com.parse.ParseUser
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var userDatabase = UserDatabase()

    private var _loggedInUser = MutableLiveData<ParseUser>()
    val loggedInUser: LiveData<ParseUser>
        get() = _loggedInUser

    private var _username = ""
    val username: String
        get() = _username

    private var loginData = mutableMapOf<String, String>()
    private var ld_filled = false

    fun addLoginData(username: String, password: String) {
        _username = username
        loginData["username"] = username
        loginData["password"] = password
        ld_filled = true
    }

    fun login() : Boolean{
        if(ld_filled) {
            viewModelScope.launch {
                userDatabase.login(loginData["username"]!!, loginData["password"]!!)
            }
            _loggedInUser.value = userDatabase.loggedInUser.value
            _username = userDatabase.username

            return true
        }else {
            Log.e("Login-Information", "Keine Logindaten erfasst!")
            return false
        }
    }

}