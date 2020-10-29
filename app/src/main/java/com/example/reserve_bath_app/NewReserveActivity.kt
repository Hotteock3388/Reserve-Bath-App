package com.example.reserve_bath_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_reserve.*

class NewReserveActivity : AppCompatActivity() {

    //var reserveData : ReserveData? = null
    lateinit var selectData: SelectData
    var sDay = 0


    lateinit var btnArr : Array<Button>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reserve)
        buttonInit()

    }

    private fun buttonInit(){
        btnArr = arrayOf(btn_Day1,btn_Day2,btn_Day3,btn_Day4,btn_Day5,btn_Day6,btn_Day7)
        
        for(i in 0..6){
            btnArr[i].setOnClickListener {
                for(j in 0..6) {
                    if (it.id == btnArr[j].id) { Log.d("TestLog", "j = ${j}") }
                }
            }

        }
    }

}