package com.example.reserve_bath_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter : MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabInit()
        RecyclerViewInit()
        addReserveDatas()
    }

    private fun addReserveDatas() {
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(1),"06", "06", 10))
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(6),"12", "11", 20))
        Singleton.reserveDataList.add(ReserveData(LocalDate.now().plusDays(11),"16", "30", 30))
    }

    private fun RecyclerViewInit() {

//어댑터를 생성한다

//데이터를 생성해야한다(서버통신을 안 할 경우 이렇게)
        myAdapter = MyAdapter(applicationContext)

//데이터를 어댑터 안에 넣는다
        myAdapter.reserveDataList = Singleton.reserveDataList

//어댑터의 데이터가 변했다는 notify를 날린다
        myAdapter.notifyDataSetChanged()

//실제 RecyclerView의 adapter를 만든 adapter로 설정한다
        recyclerView_PreviousSettings.adapter = myAdapter


    }


    private fun fabInit() {
        fab_NewReserve.setOnClickListener{
            val intent = Intent(this, NewReserveActivity::class.java)
            intent.putExtra("Data", "")
            startActivityForResult(intent, 100)
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity)
        }

        fab_ShowPreviousSettings.setOnClickListener {
            startActivityForResult(Intent(this, ViewPagerTestActivity::class.java), 100)
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        myAdapter.notifyDataSetChanged()
    }

}