package com.ebnbin.gank

import android.os.Bundle
import com.ebnbin.eb.app.EBActivity

class MainActivity : EBActivity() {
    private val homeFragment: HomeFragment by lazy { HomeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            fragmentHelper.set(homeFragment)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        fragmentHelper.set(homeFragment)
    }

    override val enableDoubleBackFinish = true
}
