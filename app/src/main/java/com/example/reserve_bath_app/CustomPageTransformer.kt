package com.example.reserve_bath_app

import android.view.View
import androidx.viewpager.widget.ViewPager

class CustomPageTransformer : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.scaleX = 0.8F;
        page.scaleY = 0.8F;
        page.rotation = 40.0F * position;
        //page.translationY = position * 300
        page.translationY = Math.abs(300* position).toFloat()
    }
}