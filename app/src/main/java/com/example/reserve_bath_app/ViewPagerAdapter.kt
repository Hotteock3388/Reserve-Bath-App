package com.example.reserve_bath_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter


class ViewPagerAdapter(context: Context) : PagerAdapter() {
    private var context = context
    private lateinit var layoutInflater: LayoutInflater


    override fun getCount(): Int {
        return Singleton.reserveDataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.remov eView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(com.example.reserve_bath_app.R.layout.viewpager_item, null)

        container.addView(view)

        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }
}
