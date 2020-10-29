package com.example.reserve_bath_app

class ReserveDataList private constructor() {


    companion object{
        @Volatile private var instance: ReserveDataList? = null

        @JvmStatic fun getInstance(): ReserveDataList =
            instance ?: synchronized(this) {
                instance ?: ReserveDataList().also {
                    instance = it
                }
            }


    }
}