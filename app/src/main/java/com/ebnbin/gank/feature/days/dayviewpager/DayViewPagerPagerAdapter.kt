package com.ebnbin.gank.feature.days.dayviewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.ebnbin.eb.util.EBUtil
import com.ebnbin.eb.util.Timestamp
import com.ebnbin.gank.feature.days.day.DayFragment
import com.ebnbin.gank.feature.days.day.DayModel
import java.util.*

/**
 * Days [PagerAdapter].
 */
internal class DayViewPagerPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val timestamps = ArrayList<Timestamp>()

    private val dayFragments = SparseArray<DayFragment>()

    /**
     * Sets new data.
     */
    fun setData(timestamps: List<Timestamp>) {
        this.timestamps.clear()
        this.timestamps.addAll(timestamps)

        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return DayFragment.newInstance(timestamps[position])
    }

    override fun getCount(): Int {
        return timestamps.size
    }

    override fun getItemPosition(any: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val dayFragment = super.instantiateItem(container, position) as DayFragment
        dayFragments.put(position, dayFragment)

        return dayFragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, any: Any) {
        dayFragments.remove(position)

        super.destroyItem(container, position, any)
    }

    fun getDayFragment(position: Int): DayFragment? {
        return dayFragments.get(position)
    }

    abstract class FirstFuliCallback {
        open fun onGetFirstFuli(url: String?) {}
    }

    fun getFirstFuli(position: Int, callback: FirstFuliCallback) {
        EBUtil.handler.post(Runnable {
            val dayFragment = getDayFragment(position)
            if (dayFragment == null) {
                callback.onGetFirstFuli(null)

                return@Runnable
            }

            dayFragment.getDayModel(object : DayFragment.DayModelCallback() {
                override fun onGetDayModel(dayModel: DayModel) {
                    super.onGetDayModel(dayModel)

                    callback.onGetFirstFuli(dayModel.results!!.validFirstFuli)
                }
            })
        })
    }
}
