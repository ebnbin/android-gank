package com.ebnbin.gank.feature.category

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.ebnbin.eb.context.EBActionBarFragment
import com.ebnbin.eb.util.EBUtil
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

        viewPager.offscreenPageLimit = 1
        viewPager.pageMargin = resources.getDimensionPixelSize(R.dimen.days_page_margin)
        viewPager.setPageMarginDrawable(ColorDrawable(EBUtil.getColorAttr(context, R.attr.ebColorPlaceholder)))

        if (bottomNavigationParentFragment != null) {
            appBarLayout.stateListAnimator = null
        }

        if (actionBarParentFragment != null) {
            actionBarParentFragment!!.toolbar.setTitle(R.string.home_category)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState == null) {
            viewPager.setCurrentItem(1, false)
        }
    }
}
