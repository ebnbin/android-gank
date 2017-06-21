package com.ebnbin.gank.feature.days

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
    }

    override fun overrideHasOptionsMenu(): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.fragment_days, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                AboutDialogFragment.showDialog(childFragmentManager)

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
