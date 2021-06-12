package com.example.reserve_bath_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    lateinit var adapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPagerInit()

        addReserveData()

        btn_newReserve.setOnClickListener {
            startActivity(Intent(this, NewReserveActivity::class.java))
        }

    }

    private fun addReserveData() {
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(1),"06", "06"))
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(6),"12", "11"))
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(11),"16", "30"))

        viewPager.adapter?.notifyDataSetChanged()
    }

    private fun viewPagerInit() {
        adapter = ViewPagerAdapter(applicationContext, Singleton.reserveDataList)
        viewPager.adapter = adapter
        viewPager.setPageTransformer(false, CustomPageTransformer())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewPager.adapter?.notifyDataSetChanged()

    }

}