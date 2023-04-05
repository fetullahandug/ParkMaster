package com.example.parkmaster.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parkmaster.data.model.Violation
import com.parse.*

class Database() {

    //UserDatabase
    private var _username = ""
    val username: String
        get() = _username

    private var _user = MutableLiveData<ParseUser>()
    val user: LiveData<ParseUser>
        get() = _user

    //Violation
    private var _violations = MutableLiveData<MutableList<Violation>>()
    val violations: MutableLiveData<MutableList<Violation>>
        get() = _violations

    //UserDatabase
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

    fun loadViolation4App() : MutableList<Violation>?{
        var violationList = mutableListOf<Violation>()

        val userQuery = ParseQuery<ParseUser>("_User")
        userQuery.whereEqualTo("username", _username)

        try {
            val userLog = userQuery.first
            val violationQuery = ParseQuery<ParseObject>("Violation")
            violationQuery.whereEqualTo("politess", userLog)
            violationQuery.findInBackground { objects: List<ParseObject>?, e: ParseException? ->
                if (e == null) {
                    violationList = initializeViolations(objects!!)
                    Log.e("Database","Violation List loaded!")
                } else {
                    Log.e("Database", "Loading of Violations failed!")
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return violationList
    }

    fun initializeViolations(listOfParseObjects: List<ParseObject>?) : MutableList<Violation> {
        var violationsList = mutableListOf<Violation>()

        if (listOfParseObjects != null) {
            for(violation in listOfParseObjects) {
                val newViolation = Violation(
                    violation.getString("address").toString(),
                    violation.getString("housenumber").toString(),
                    violation.getInt("postcode"),
                    violation.getString("city").toString(),
                    violation.getString("date").toString(),
                    violation.getString("date").toString(),
                    violation.getString("type").toString(),
                    violation.getString("vehicle_id").toString(),
                    username
                )

                violationsList.add(newViolation)
            }
        }

        return violationsList
    }

}