package com.ebnbin.gank

import android.os.Bundle
import com.ebnbin.ebapplication.context.EBActivity

class MainActivity : EBActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeFragment = HomeFragment()
        fragmentHelper.set(homeFragment)
    }

    override fun enableDoubleBackFinish(): Boolean {
        return true
    }
}
