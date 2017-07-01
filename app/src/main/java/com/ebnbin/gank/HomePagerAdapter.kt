package com.ebnbin.gank

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.ebnbin.eb.util.EBRuntimeException
import com.ebnbin.ebapplication.context.EBBottomNavigationPagerAdapter
import com.ebnbin.ebapplication.feature.webview.WebViewActionBarFragment
import com.ebnbin.gank.feature.category.CategoryFragment
import com.ebnbin.gank.feature.days.DaysFragment

class HomePagerAdapter(fm: FragmentManager) : EBBottomNavigationPagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "首页"
            1 -> return "分类"
            2 -> return "ebnbin"
        }

        throw EBRuntimeException()
    }

    override fun getPageIcon(position: Int): Int {
        when (position) {
            0 -> return R.drawable.home
            1 -> return R.drawable.home_subject
            2 -> return R.drawable.home_person
        }

        throw EBRuntimeException()
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return DaysFragment()
            1 -> return CategoryFragment()
            2 -> return WebViewActionBarFragment.newInstance("http://ebnbin.com")
        }

        throw EBRuntimeException()
    }

    override fun getCount(): Int {
        return 3
    }
}
