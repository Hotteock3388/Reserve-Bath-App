package com.example.reserve_bath_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.clans.fab.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabSetting()
    }

    private fun fabSetting() {
        fab_NewReserve.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, NewReserveActivity::class.java)
            intent.putExtra("Data", "")
            startActivity(intent)
            overridePendingTransition(R.anim.rightin_activity, R.anim.not_move_activity)
        })
    }
}