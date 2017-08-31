package com.ebnbin.gank.feature.day

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.ebnbin.eb.util.AppUtil
import com.ebnbin.eb.util.time.EBDate
import java.util.ArrayList

/**
 * Days [PagerAdapter].
 */
internal class DayPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val timestamps = ArrayList<EBDate>()

    private val dayFragments = SparseArray<DayPageFragment>()

    /**
     * Sets new data.
     */
    fun setData(timestamps: List<EBDate>) {
        this.timestamps.clear()
        this.timestamps.addAll(timestamps)

        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return DayPageFragment.newInstance(timestamps[position])
    }

    override fun getCount(): Int {
        return timestamps.size
    }

    override fun getItemPosition(any: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val dayFragment = super.instantiateItem(container, position) as DayPageFragment
        dayFragments.put(position, dayFragment)

        return dayFragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, any: Any) {
        dayFragments.remove(position)

        super.destroyItem(container, position, any)
    }

    fun getDayFragment(position: Int): DayPageFragment? {
        return dayFragments.get(position)
    }

    abstract class FirstFuliCallback {
        open fun onGetFirstFuli(url: String?) {}
    }

    fun getFirstFuli(position: Int, callback: FirstFuliCallback) {
        AppUtil.handler.post(Runnable {
            val dayFragment = getDayFragment(position)
            if (dayFragment == null) {
                callback.onGetFirstFuli(null)

                return@Runnable
            }

            dayFragment.getDayModel(object : DayPageFragment.DayModelCallback() {
                override fun onGetDayModel(dayModel: DayModel) {
                    super.onGetDayModel(dayModel)

                    callback.onGetFirstFuli(dayModel.results!!.validFirstFuli)
                }
            })
        })
    }
}
