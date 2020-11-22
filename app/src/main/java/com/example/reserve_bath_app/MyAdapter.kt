package com.example.reserve_bath_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    //var reserveDataList = mutableListOf<ReserveData>()
    var reserveDataList = Singleton.reserveDataList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem_show_reservestatus,
            parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = reserveDataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(reserveData = reserveDataList[position])
    }

}

class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    //View를 저장할 수 있는 변수
    val textViewdate = itemView.findViewById<TextView>(R.id.textView_Date)
    val textViewTime = itemView.findViewById<TextView>(R.id.textView_Time)
    val textViewTemper = itemView.findViewById<TextView>(R.id.textView_Temper)
    //View와 데이터를 연결시키는 함수
    fun bind(reserveData: ReserveData) {

        textViewdate.text = reserveData.date.toString()
        textViewTime.text = "${reserveData.hour} : ${reserveData.minute}"
        textViewTemper.text = reserveData.temp.toString()
    }
}
