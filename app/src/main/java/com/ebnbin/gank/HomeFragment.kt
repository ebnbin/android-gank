package com.ebnbin.gank

import android.os.Bundle
import android.view.View
import com.ebnbin.ebapplication.context.EBBottomNavigationFragment
import com.ebnbin.ebapplication.context.EBBottomNavigationItem
import com.ebnbin.ebapplication.feature.webview.WebViewFragment
import com.ebnbin.gank.feature.category.CategoryFragment
import com.ebnbin.gank.feature.days.DaysFragment

class HomeFragment : EBBottomNavigationFragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val daysFragment = DaysFragment()
        bottomNavigation.addItem(EBBottomNavigationItem(getString(R.string.home), R.drawable.home, daysFragment))
        val categoryFragment = CategoryFragment()
        bottomNavigation.addItem(EBBottomNavigationItem("Category", R.drawable.home_subject, categoryFragment))
        val webViewActionBarFragment = WebViewFragment.newInstance("http://ebnbin.com")
        bottomNavigation.addItem(EBBottomNavigationItem(getString(R.string.eb_ebnbin), R.drawable.home_person,
                webViewActionBarFragment))

        bottomNavigation.setCurrentItem(0, true)
    }
}
