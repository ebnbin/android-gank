package com.ebnbin.gank.feature.days.dayviewpager

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.view.MenuItem
import android.view.View
import com.ebnbin.eb.util.EBUtil
import com.ebnbin.eb.util.Timestamp
import com.ebnbin.ebapplication.context.EBFragment
import com.ebnbin.ebapplication.net.NetModelCallback
import com.ebnbin.gank.R
import com.ebnbin.gank.feature.days.HistoryModel
import com.ebnbin.recyclercalendarview.recyclerdatepicker.RecyclerDatePickerSupportDialogFragment
import okhttp3.Call
import okhttp3.Response

/**
 * 用 [ViewPager] 展示多个 [com.ebnbin.gank.feature.days.day.DayFragment].
 */
class DayViewPagerFragment : EBFragment(), RecyclerDatePickerSupportDialogFragment.Callback {
    override fun overrideContentViewLayout(): Int {
        return R.layout.days_day_view_pager_fragment
    }

    private val daysViewPager: ViewPager by lazy {
        stateView.findViewById(R.id.days) as ViewPager
    }

    private val pagerAdapter: DayViewPagerPagerAdapter by lazy {
        DayViewPagerPagerAdapter(childFragmentManager)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daysViewPager.adapter = pagerAdapter
        daysViewPager.offscreenPageLimit = 1
        daysViewPager.pageMargin = resources.getDimensionPixelSize(R.dimen.days_page_margin)
        daysViewPager.setPageMarginDrawable(ColorDrawable(EBUtil.getColorAttr(context, R.attr.ebColorPlaceholder)))
        daysViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            private var dragged = false

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                setTitle(position)

                if (actionBarParentFragment != null && dragged) {
                    dragged = false

                    actionBarParentFragment!!.appBarLayout.setExpanded(true, true)
                }

                pagerAdapter.getFirstFuli(position, object : DayViewPagerPagerAdapter.FirstFuliCallback() {
                    override fun onGetFirstFuli(url: String?) {
                        super.onGetFirstFuli(url)

                        // TODO: 福利 image.
                    }
                })
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    dragged = true
                }
            }

            /**
             * Sets title of [ActionBar].
             */
            private fun setTitle(position: Int) {
                val toolbar = actionBarParentFragment?.toolbar ?: return

                val timestamp = pagerAdapter.timestamps[position]
                val title = getString(R.string.days_title, timestamp.year, timestamp.month, timestamp.day)

                toolbar.title = title
            }
        })

        initActionBar()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (!restoreHistory(savedInstanceState)) {
            netGetHistory()
        }
    }

    /**
     * 恢复 [historyModel].

     * @return 是否成功恢复.
     */
    fun restoreHistory(savedInstanceState: Bundle?): Boolean {
        if (savedInstanceState == null) {
            return false
        }

        historyModel = savedInstanceState.getSerializable(STATE_HISTORY_MODEL) as HistoryModel?
        if (historyModel == null) {
            return false
        }

        pagerAdapter.setData(historyModel!!.timestamps)

        val defaultCurrentItem = pagerAdapter.count - 1
        val currentItem = savedInstanceState.getInt(STATE_CURRENT_ITEM, defaultCurrentItem)
        if (currentItem >= 0 && currentItem < pagerAdapter.count) {
            daysViewPager.setCurrentItem(currentItem, false)
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState == null) {
            return
        }

        outState.putSerializable(STATE_HISTORY_MODEL, historyModel)
        outState.putInt(STATE_CURRENT_ITEM, daysViewPager.currentItem)
    }

    //*****************************************************************************************************************
    // Net.

    private var historyModel: HistoryModel? = null

    /**
     * Gets history and sets data to [DayViewPagerPagerAdapter].
     */
    private fun netGetHistory() {
        val url = "http://gank.io/api/day/history"
        netGet(url, object : NetModelCallback<HistoryModel>() {
            override fun onSuccess(call: Call, model: HistoryModel, response: Response,
                    byteArray: ByteArray) {
                super.onSuccess(call, model, response, byteArray)

                historyModel = model
                pagerAdapter.setData(historyModel!!.timestamps)

                if (calendarMenuItem != null) {
                    calendarMenuItem!!.isVisible = true
                }

                val item = pagerAdapter.count - 1
                if (item >= 0 && item < pagerAdapter.count) {
                    daysViewPager.setCurrentItem(item, false)
                }
            }
        })
    }

    //*****************************************************************************************************************
    // ActionBar.

    private var calendarMenuItem: MenuItem? = null

    private fun initActionBar() {
        val toolbar = actionBarParentFragment?.toolbar ?: return

        toolbar.inflateMenu(R.menu.fragment_days_day_view_pager)
        calendarMenuItem = toolbar.menu.findItem(R.id.calendar)
        calendarMenuItem!!.isVisible = historyModel != null
        toolbar.menu.findItem(R.id.calendar).setOnMenuItemClickListener({
            RecyclerDatePickerSupportDialogFragment.showDialog(childFragmentManager, historyModel!!.timestamps,
                    pagerAdapter.timestamps[daysViewPager.currentItem])
            true
        })
    }

    //*****************************************************************************************************************

    override fun onSelected(date: Timestamp) {
        val position = pagerAdapter.timestamps.indexOf(date)
        if (position != -1) {
            daysViewPager.setCurrentItem(position, true)
        }
    }

    companion object {
        //*************************************************************************************************************
        // Instance state.

        private val STATE_HISTORY_MODEL = "history_model"
        private val STATE_CURRENT_ITEM = "current_item"
    }
}
