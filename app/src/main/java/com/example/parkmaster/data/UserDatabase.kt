package com.example.parkmaster.data

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parse.Parse
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.SignUpCallback

class UserDatabase {

    private var _loggedInUser = MutableLiveData<ParseUser>()
    val loggedInUser: LiveData<ParseUser>
        get() = _loggedInUser

    private var _username = ""
    val username: String
        get() = _username

    private var _user = MutableLiveData<ParseUser>()
    val user: LiveData<ParseUser>
        get() = _user

    fun login(username: String, password: String) : ParseUser {
        var user = ParseUser()
            user.username = username
            user.setPassword(password)

        ParseUser.logInInBackground(username, password) { user, parseException: ParseException? ->
            if(user != null) {
                Log.i("Login-Information", "Du wurdest erfolreich eingeloggt!")
                _username = username
                _user.value = user
            }else {
                Log.e("Login-Information", "Einloggen fehlgeschlagen!")
                ParseUser.logOut()
                if(parseException != null) {
                    Log.e("Login-Error", parseException.message.toString())
                }
            }
        }

        return user
    }

}