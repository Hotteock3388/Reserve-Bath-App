package com.example.reserve_bath_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.reserve_bath_app.Singleton.reserveDataList
import kotlinx.android.synthetic.main.activity_new_reserve.*
import kotlinx.android.synthetic.main.viewpager_item.view.*
import java.nio.file.attribute.PosixFileAttributeView
import java.time.DayOfWeek
import java.util.*


class ViewPagerAdapter(context: Context) : PagerAdapter() {
    private var context = context
    private lateinit var layoutInflater: LayoutInflater


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        return reserveDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(com.example.reserve_bath_app.R.layout.viewpager_item, null)
        val reserveCal: Calendar = Calendar.getInstance()

        setTextViewsText(view, reserveCal, position)
        container.addView(view)
        return view
    }

    private fun getDayOfWeek(position : Int) : String{
        return when(reserveDataList[position].date.dayOfWeek){
            DayOfWeek.SATURDAY -> "토"
            DayOfWeek.FRIDAY -> "금"
            DayOfWeek.THURSDAY -> "목"
            DayOfWeek.WEDNESDAY -> "수"
            DayOfWeek.TUESDAY -> "화"
            DayOfWeek.MONDAY -> "월"
            DayOfWeek.SUNDAY -> "일"
            else -> "?"
        }
    }

    private fun initReserveCal(paramCalendar: Calendar, paramInt: Int) {
        paramCalendar.set(Calendar.YEAR, reserveDataList[paramInt].date.year)
        paramCalendar.set(Calendar.MONTH, reserveDataList[paramInt].date.monthValue)
        paramCalendar.set(Calendar.DAY_OF_MONTH, reserveDataList[paramInt].date.dayOfMonth)
        paramCalendar.set(Calendar.HOUR_OF_DAY, reserveDataList[paramInt].hour.toInt())
        paramCalendar.set(Calendar.MINUTE,reserveDataList[paramInt].minute.toInt())
    }
    private fun setTextViewsText(view: View, reserveCal: Calendar, position: Int) {


        view.viewPagerItem_Temper.text = "${reserveDataList[position].temp}°C"

        initReserveCal(reserveCal, position)

        setReserveDate(view, reserveCal, position)

        setReserveTime(view, position)

        setLeftTime(view, position, reserveCal)
    }

    private fun setLeftTime(view: View, position: Int, reserveCal: Calendar) {
        var leftTimeString = ""

        var nowCal = Calendar.getInstance()
        nowCal.set(2, nowCal.get(2) + 1)

        var leftTimeMilliSeconds = (reserveCal.timeInMillis - nowCal.timeInMillis)/1000
        val day = 60 * 60 * 24
        val hour = 60 * 60
        val minute = 60

        if(leftTimeMilliSeconds >= day){
            leftTimeString = "${leftTimeMilliSeconds / day}일 "
            leftTimeMilliSeconds %= day
        }
        if(leftTimeMilliSeconds % day >= hour){
            leftTimeString += "${leftTimeMilliSeconds / hour}시간 "
            leftTimeMilliSeconds %= hour
        }
        leftTimeString += "${leftTimeMilliSeconds / minute}분"

        view.viewPagerItem_LeftTime.text = leftTimeString
    }

    private fun setReserveDate(view: View, reserveCal: Calendar, position: Int) {
        view.viewPagerItem_ReserveDate.text = "${reserveCal.get(Calendar.MONTH)}월 ${reserveCal.get(Calendar.DAY_OF_MONTH)}일 ${getDayOfWeek(position)}요일 "
    }

    fun setReserveTime(view: View, position: Int){
        var reserveTimeString : String  = ""
        if(reserveDataList[position].hour.toInt() > 12) {
            view.viewPagerItem_MorningOrAfternoon.text = "오후"
            reserveTimeString = String.format("%02d", (reserveDataList[position].hour.toInt() - 12))
        }
        else{
            reserveTimeString = reserveDataList[position].hour
        }
        reserveTimeString += ":" + reserveDataList[position].minute

        view.viewPagerItem_reserveTime.text = reserveTimeString
    }


}
