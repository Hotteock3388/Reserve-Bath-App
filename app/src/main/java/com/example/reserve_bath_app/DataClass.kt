package com.example.reserve_bath_app

data class ReserveData(
    var date: String,
    var time: String,
    var temp: Int
    )

data class SelectData(
    var date: String,
    var day: Int = 0,
    var time: String,
    var temp: Int,
    var ifDay: Boolean = true
)
