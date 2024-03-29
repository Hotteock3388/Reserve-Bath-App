package com.example.reserve_bath_app

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_reserve.*
import kotlinx.android.synthetic.main.alertdatepickerform.*
import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.*

class NewReserveActivity : AppCompatActivity() {

    //var reserveData : ReserveData? = null
    private var selectData = SelectData()
    private var reserveData = ReserveData()

    lateinit var btnArr : Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reserve)
        buttonInit()
        setText_leftTime()

        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            setText_leftTime()
        }
        btn_Reserve.setOnClickListener {
            reserveBath()
        }

    }

    private fun buttonInit(){
        btnArr = arrayOf(btn_Day1,btn_Day2,btn_Day3,btn_Day4,btn_Day5,btn_Day6,btn_Day7)

        for(i in 0..6){
            btnArr[i].setOnClickListener {
                for(j in 0..6) {
                    if (it.id == btnArr[j].id) {
                        dayChange(j)
                    }
                }
            }
        }
        btn_SelectDayOrDate.setOnClickListener {
            if(selectData.ifDay)
                createDatePicker()
            else
                daySelectMode()
        }

        todayButtonOn()
        // 30분 후 목욕
        timePicker.minute += 30
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
        setText_leftTime()
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
        val dlg =
            AlertDialog.Builder(this@NewReserveActivity)
        dlg.setCancelable(false)
            .setView(myView)
            .setPositiveButton("확인") { dialog, which ->

                text_ReservingDay.text = "${datePicker.year}년 ${(datePicker.month + 1)}월 ${datePicker.dayOfMonth}일 목욕예약"


                selectData.selectLocalDate = LocalDate.of(datePicker.year, datePicker.month+1, datePicker.dayOfMonth)

                dateSelectMode()
                setText_leftTime()
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
        setText_leftTime()
    }

    private fun dateSelectMode(){
        selectData.ifDay = false
        text_ShowDayOrDate.text = "날짜 선택"
        btn_SelectDayOrDate.text = "요일 선택"

        for (i in 0..6) {
            btnArr[i].visibility = View.GONE
        }
        text_ReservingDay.visibility = View.VISIBLE
        setText_leftTime()
    }


    fun setText_leftTime() {
        selectData.selectPast = false
        var leftTimeMinutes: Long = 0
        val cal = Calendar.getInstance()

        var nowHour = cal[Calendar.HOUR_OF_DAY]
        var nowMinute = cal[Calendar.MINUTE]

        var leftDay = 0
        val leftHour: Long
        val leftMinute: Long

        var toDay =(cal.get(Calendar.DAY_OF_WEEK))
        toDay = if(toDay == 1) 6 else (toDay) -2

        if(selectData.ifDay){
            while(toDay != selectData.day){
                ++leftDay
                if (toDay == 6) {
                    toDay = 0
                    continue
                }
                ++toDay
            }
            reserveData.date = LocalDate.now().plusDays(leftDay.toLong())
        }
        else{
            leftDay = ChronoUnit.DAYS.between(LocalDate.now(), selectData.selectLocalDate).toInt()
            reserveData.date = selectData.selectLocalDate
        }

        leftTimeMinutes += leftDay * 60 * 24 + (timePicker.hour - nowHour) * 60.toLong() + timePicker.minute - nowMinute.toLong()

        if(leftTimeMinutes < 0){
            if(selectData.ifDay){
                leftTimeMinutes += 60 * 24 * 7
                reserveData.date = LocalDate.now().plusDays(7)
            }
            else
                selectData.selectPast = true
        }

        leftDay = (leftTimeMinutes / (60 * 24)).toInt()
        leftTimeMinutes %= 60 * 24.toLong()

        leftHour = leftTimeMinutes / 60
        leftTimeMinutes %= 60

        leftMinute = leftTimeMinutes

        text_LeftTime.text = "${leftDay}일 ${leftHour}시간 ${leftMinute}분 후에 알람이 울립니다."
    }

    private fun reserveBath(){
        //Log.d("TestLog", "date = ${reserveData.date}, time = ${timePicker.hour} : ${timePicker.minute}, temp = ${croller.progress + 13}")
        if(selectData.selectPast)
        {
            Toast.makeText(this, "과거를 예약 할 순 없어요!", Toast.LENGTH_SHORT).show()
            return
        }
        reserveData.hour = "${String.format("%02d", timePicker.hour)}"
        reserveData.minute = "${String.format("%02d", timePicker.minute)}"

        Singleton.reserveDataList.add(reserveData)
        setResult(Activity.RESULT_OK)
        finish()

        for(i in 0 until Singleton.reserveDataList.size){
            Log.d("TestLog", "dataList $i = ${Singleton.reserveDataList[i].date} - ${Singleton.reserveDataList[i].hour} : ${Singleton.reserveDataList[i].minute}")
        }
    }


}