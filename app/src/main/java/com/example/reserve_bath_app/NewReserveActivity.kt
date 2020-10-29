package com.example.reserve_bath_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_reserve.*
import java.util.*
import java.util.Calendar.DAY_OF_WEEK

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
                    if (it.id == btnArr[j].id) {
                        Log.d("TestLog", "j = ${j}")
                        dayChange(j)
                    }
                }
            }
        }

        todayButtonOn()
    }

    private fun todayButtonOn(){
        //오늘의 요일 구하기
        var toDay =(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))

        //숫자의 규칙성을 찾아 월요일 = 0 ..일요일 = 6으로 sDay 변수에 대입
        sDay = if(toDay == 1) 6 else (toDay) -2

        //오늘을 선택
        dayOn(sDay)
    }

    private fun dayChange(clickedDay: Int) {
        dayOff()
        dayOn(clickedDay)

    }
    private fun dayOff() {
        btnArr.get(sDay).setBackgroundResource(R.drawable.btn_unselected_day)
        btnArr.get(sDay).setTextColor(resources.getColor(R.color.colorButtonGray))
    }
    private fun dayOn(clickedDay: Int) {
        btnArr.get(clickedDay).setBackgroundResource(R.drawable.btn_selected_day)
        btnArr.get(clickedDay).setTextColor(resources.getColor(R.color.colorBlack))
        sDay = clickedDay
    }

}