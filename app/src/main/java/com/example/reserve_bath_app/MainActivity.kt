package com.example.reserve_bath_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerInit()

        addReserveDatas()
    }

    private fun addReserveDatas() {
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(1),"06", "06", 10))
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(6),"12", "11", 20))
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(11),"16", "30", 30))

        viewPager.adapter   ?.notifyDataSetChanged()
    }

    private fun viewPagerInit() {
        viewPager.adapter = ViewPagerAdapter(applicationContext)
        viewPager.setPageTransformer(false, CustomPageTransformer())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //myAdapter.notifyDataSetChanged()
    }

}