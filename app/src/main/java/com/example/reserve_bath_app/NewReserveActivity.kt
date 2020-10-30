package com.example.reserve_bath_app

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_reserve.*
import java.util.*

class NewReserveActivity : AppCompatActivity() {

    //var reserveData : ReserveData? = null
    var selectData = SelectData("", 0, "", 0,true)


    lateinit var btnArr : Array<Button>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reserve)
        buttonInit()
        setCroller()
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
        btn_SelectDayOrDate.setOnClickListener {
            if(selectData.ifDay) {
                createDatePicker()
            }
            else{
                daySelectMode()
            }
        }

        todayButtonOn()
    }

    private fun todayButtonOn(){
        //오늘의 요일 구하기
        var toDay =(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))

        //숫자의 규칙성을 찾아 월요일 = 0 ..일요일 = 6으로 selectData.day 변수에 대입
        selectData.day = if(toDay == 1) 6 else (toDay) -2

        //오늘을 선택
        selectDayOn(selectData.day)
    }

    private fun dayChange(clickedDay: Int) {
        selectDayOff()
        selectDayOn(clickedDay)
    }

    private fun selectDayOff() {
        btnArr.get(selectData.day).setBackgroundResource(R.drawable.btn_unselected_day)
        btnArr.get(selectData.day).setTextColor(resources.getColor(R.color.colorButtonGray))
    }

    private fun selectDayOn(clickedDay: Int) {
        btnArr.get(clickedDay).setBackgroundResource(R.drawable.btn_selected_day)
        btnArr.get(clickedDay).setTextColor(resources.getColor(R.color.colorBlack))
        selectData.day = clickedDay
    }

    private fun createDatePicker() {
        val inflater: LayoutInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val myView: View = inflater.inflate(R.layout.alertdatepickerform, null)
        val datePicker = myView.findViewById<DatePicker>(R.id.datePicker)
        var text_SelectDate = ""
        val dlg =
            AlertDialog.Builder(this@NewReserveActivity)
        dlg.setCancelable(false)
            .setView(myView)
            .setPositiveButton("확인") { dialog, which ->

                text_ReservingDay.text = "${datePicker.year}년 ${(datePicker.month + 1)}월 ${datePicker.dayOfMonth}일 목욕예약"


                selectData.date = "${datePicker.year}.${String.format("%02d", (datePicker.month + 1))}.${String.format("%02d", datePicker.dayOfMonth)}"

                Log.d("TestLog", "date = ${selectData.date}")
                dateSelectMode()
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun daySelectMode(){
        selectData.ifDay = true
        text_ShowDayOrDate.text = "요일 선택"
        btn_SelectDayOrDate.text = "날짜 선택"
        for (i in 0..6) {
            btnArr[i].visibility = View.VISIBLE
        }
        text_ReservingDay.visibility = View.GONE
    }

    private fun dateSelectMode(){
        selectData.ifDay = false
        text_ShowDayOrDate.text = "날짜 선택"
        btn_SelectDayOrDate.text = "요일 선택"

        for (i in 0..6) {
            btnArr[i].visibility = View.GONE
        }
        text_ReservingDay.visibility = View.VISIBLE
    }
    private fun setCroller() {
        croller.setOnProgressChangedListener { progress ->
            val temp = progress + 13
            selectData.temp = temp
            select_Temper.text = "$temp ºC"
            croller.backCircleColor =
            when(temp) {
                14 -> Color.parseColor("#FF2196F3")
                25 -> Color.parseColor("#FFA2CBEC")
                38 -> Color.parseColor("#FFFF9800")
                41 -> Color.parseColor("#FFF15321")
                44 -> Color.parseColor("#FFF44336")
                else -> croller.backCircleColor
            }
    }
    }
    private fun updateCroller(temp:Int,colorString:String){
        select_Temper.setText("${temp}ºC")
        croller.backCircleColor = Color.parseColor("colorString")
    }


}