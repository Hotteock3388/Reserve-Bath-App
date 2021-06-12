package com.example.reserve_bath_app

import java.time.LocalDate

data class ReserveData(
    var date : LocalDate = LocalDate.now(),
    var hour : String = "",
    var minute : String = ""
    )

data class SelectData(
    var selectLocalDate: LocalDate = LocalDate.now(),
    var day: Int = 0,
    var ifDay: Boolean = true,
    var selectPast : Boolean = false


)
