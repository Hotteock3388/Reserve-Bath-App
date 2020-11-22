package com.example.reserve_bath_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.reserve_bath_app.Singleton.reserveDataList
import kotlinx.android.synthetic.main.viewpager_item.view.*
import java.util.*


class ViewPagerAdapter(context: Context) : PagerAdapter() {
    private var context = context
    private lateinit var layoutInflater: LayoutInflater


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        return Singleton.reserveDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(com.example.reserve_bath_app.R.layout.viewpager_item, null)

        view.viewPagerItem_LeftTime.text = "${position}번째 페이지"
        container.addView(view)
        return view
    }

    private fun getDayOfWeek(position : Int) : String{
        return when("${reserveDataList[position].date.dayOfWeek}".toInt()){
            7 -> "토"
            6 -> "금"
            5 -> "목"
            4 -> "수"
            3 -> "화"
            2 -> "월"
            1 -> "일"
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


}
