package com.ebnbin.gank

import android.os.Bundle
import android.view.View
import com.ebnbin.eb.context.EBBottomNavigationFragment
import com.ebnbin.eb.context.EBBottomNavigationItem
import com.ebnbin.gank.feature.category.CategoryFragment
import com.ebnbin.gank.feature.day.DayFragment

class HomeFragment : EBBottomNavigationFragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryFragment = CategoryFragment()
        bottomNavigation.addItem(EBBottomNavigationItem(getString(R.string.home_category), R.drawable.home_category,
                categoryFragment))
        val daysFragment = DayFragment()
        bottomNavigation.addItem(EBBottomNavigationItem(getString(R.string.home_day), R.drawable.home_day,
                daysFragment))
//        val webViewActionBarFragment = WebViewFragment2.newInstance("http://gank.io/", false)
//        bottomNavigation.addItem(EBBottomNavigationItem(getString(R.string.home_website), R.drawable.home_website,
//                webViewActionBarFragment))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState == null) {
            bottomNavigation.setCurrentItem(1, true)

            return
        }

        bottomNavigation.setCurrentItem(savedInstanceState.getInt(STATE_CURRENT_ITEM), true)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState == null) {
            return
        }

        outState.putInt(STATE_CURRENT_ITEM, bottomNavigation.currentItem)
    }

    companion object {
        private const val STATE_CURRENT_ITEM = "current_item"
    }
}
