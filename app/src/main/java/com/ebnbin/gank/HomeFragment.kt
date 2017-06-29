package com.ebnbin.gank

import android.os.Bundle
import android.view.View
import com.ebnbin.eb.util.Timestamp
import com.ebnbin.ebapplication.context.EBBottomNavigationFragment
import com.ebnbin.ebapplication.context.EBBottomNavigationItem
import com.ebnbin.ebapplication.feature.webview.WebViewActionBarFragment
import com.ebnbin.gank.feature.days.DaysFragment
import com.ebnbin.gank.feature.days.day.DayFragment

class HomeFragment : EBBottomNavigationFragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daysFragment = DaysFragment()
        bottomNavigation.addItem(EBBottomNavigationItem(getString(R.string.home), R.drawable.home, daysFragment))
        val dayFragment = DayFragment.newInstance(Timestamp.newInstance(2017, 6, 15, true))
        bottomNavigation.addItem(EBBottomNavigationItem("0615", R.drawable.home_subject, dayFragment))
        val webViewActionBarFragment = WebViewActionBarFragment.newInstance("http://ebnbin.com")
        bottomNavigation.addItem(EBBottomNavigationItem(getString(R.string.eb_ebnbin), R.drawable.home_person,
                webViewActionBarFragment))

        bottomNavigation.setCurrentItem(0, true)
    }
}
