package com.ebnbin.gank.feature.category

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.ebnbin.ebapplication.context.EBActionBarFragment
import com.ebnbin.gank.R

class CategoryFragment : EBActionBarFragment() {
    override fun overrideContentViewLayout(): Int {
        return R.layout.category_fragment
    }

    private val tabLayout: TabLayout by lazy {
        stateView.findViewById(R.id.tab_layout) as TabLayout
    }

    private val viewPager: ViewPager by lazy {
        stateView.findViewById(R.id.view_pager) as ViewPager
    }

    private val pagerAdapter: CategoryPagerAdapter by lazy {
        CategoryPagerAdapter(childFragmentManager)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout
        viewPager

        viewPager.adapter = pagerAdapter
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)
    }
}
