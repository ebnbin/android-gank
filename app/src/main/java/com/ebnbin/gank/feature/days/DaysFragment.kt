package com.ebnbin.gank.feature.days

import android.os.Bundle
import android.view.View
import com.ebnbin.ebapplication.context.EBActionBarFragment
import com.ebnbin.ebapplication.feature.about.AboutDialogFragment
import com.ebnbin.gank.R
import com.ebnbin.gank.feature.days.dayviewpager.DayViewPagerFragment

/**
 * 显示每日内容.
 */
class DaysFragment : EBActionBarFragment() {
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayViewPagerFragment = DayViewPagerFragment()
        fragmentHelper.set(dayViewPagerFragment)

        initActionBar()
    }

    private fun initActionBar() {
        val toolbar = actionBarParentFragment?.toolbar ?: return

        // TODO Redundant？
        toolbar.setTitle(R.string.app_label)
        toolbar.inflateMenu(R.menu.fragment_days)
        toolbar.menu.findItem(R.id.about).setOnMenuItemClickListener({
            AboutDialogFragment.showDialog(childFragmentManager)
            true
        })
    }
}
