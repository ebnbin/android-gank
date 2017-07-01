package com.ebnbin.gank

import android.os.Bundle
import android.view.View
import com.ebnbin.ebapplication.context.EBBottomNavigationFragment

class HomeFragment : EBBottomNavigationFragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationViewPager.adapter = HomePagerAdapter(childFragmentManager)
        bottomNavigation.setupWithViewPager(bottomNavigationViewPager)
    }
}
