package com.example.parkmaster.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkmaster.data.Database
import com.example.parkmaster.data.UserDatabase
import com.example.parkmaster.data.model.Violation
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    //LOGIN
    private var database = Database()

    private var _loggedInUser = MutableLiveData<ParseUser>()
    val loggedInUser: LiveData<ParseUser>
        get() = _loggedInUser

    private var loginData = mutableMapOf<String, String>()
    private var ld_filled = false

    private var _violations = MutableLiveData<List<Violation>>()
    val violations: LiveData<List<Violation>>
        get() = _violations

    //Login
    fun addLoginData(username: String, password: String) {
        loginData["username"] = username
        loginData["password"] = password
        ld_filled = true
    }

    fun login() {
        if (ld_filled) {
            var user = database.login(loginData["username"]!!, loginData["password"]!!)
            _loggedInUser.value = user
        } else {
            Log.e("Login-Information", "Keine Logindaten erfasst!")
        }
    }

    fun loadViolations() {
        _violations.value = database.loadViolation4App()
        println()
    }

}