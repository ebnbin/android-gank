package com.ebnbin.gank

import android.os.Bundle

import com.ebnbin.ebapplication.context.EBActivity
import com.ebnbin.gank.feature.days.DaysFragment

class MainActivity : EBActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val daysFragment = DaysFragment()
        fragmentHelper.set(daysFragment)
    }
}
