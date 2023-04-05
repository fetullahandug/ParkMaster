package com.example.parkmaster.data.model

import java.util.Date

class Violation(
    val address: String,
    val housenumber: String,
    val postcode: Int,
    val city: String,
    val date: String,
    val time: String,
    val type: String,
    val vehicle_id: String,
    val politess: String
)