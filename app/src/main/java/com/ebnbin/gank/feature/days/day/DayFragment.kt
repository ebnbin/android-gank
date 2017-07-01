package com.ebnbin.gank.feature.days.day

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ebnbin.eb.util.EBUtil
import com.ebnbin.eb.util.Timestamp
import com.ebnbin.ebapplication.context.EBActionBarFragment
import com.ebnbin.ebapplication.context.EBFragment
import com.ebnbin.ebapplication.net.NetModelCallback
import com.ebnbin.gank.R
import com.ebnbin.gank.feature.data.DataAdapter
import com.ebnbin.gank.feature.data.DataEntity
import com.ebnbin.gank.feature.data.DataItemDecoration
import com.ebnbin.gank.feature.data.DataLayoutManager
import okhttp3.Call
import okhttp3.Response
import java.util.*

/**
 * 用 [RecyclerView] 展示某日期的数据.
 */
class DayFragment : EBFragment() {
    private val timestamp: Timestamp by lazy {
        arguments.getParcelable<Timestamp>(ARG_TIMESTAMP)
    }

    //*****************************************************************************************************************

    override fun overrideContentViewLayout(): Int {
        return R.layout.days_day_fragment
    }

    private val dayRecyclerView: RecyclerView by lazy {
        stateView.findViewById(R.id.day) as RecyclerView
    }

    private val layoutManager: DataLayoutManager by lazy {
        DataLayoutManager(context)
    }

    private val adapter: DataAdapter by lazy {
        DataAdapter()
    }

    private val itemDecoration: DataItemDecoration by lazy {
        DataItemDecoration(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dayRecyclerView.layoutManager = layoutManager
        adapter.listeners.add(object : DataAdapter.Listener() {
            override fun onDataClick(data: DataEntity.Data) {
                super.onDataClick(data)

                if (data.dataModel.validUrl != null) {
                    ebActivity.loadUrl(data.dataModel.validUrl!!)
                }
            }
        })
        dayRecyclerView.adapter = adapter
        dayRecyclerView.addItemDecoration(itemDecoration)
        dayRecyclerView.setItemViewCacheSize(32)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (!restoreDay(savedInstanceState)) {
            netGetDay()
        }
    }

    /**
     * 恢复 [dayModel].

     * @return 是否成功恢复.
     */
    private fun restoreDay(savedInstanceState: Bundle?): Boolean {
        if (savedInstanceState == null) {
            return false
        }

        dayModel = savedInstanceState.getSerializable(STATE_DAY_MODEL) as DayModel
        if (dayModel == null) {
            return false
        }

        adapter.setNewData(dayModel)

        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState == null) {
            return
        }

        outState.putSerializable(STATE_DAY_MODEL, dayModel)
    }

    //*****************************************************************************************************************
    // Net.

    private var dayModel: DayModel? = null

    fun getDayModel(callback: DayModelCallback) {
        if (dayModel == null) {
            dayModelCallbacks.add(callback)
        } else {
            callback.onGetDayModel(dayModel!!)
        }
    }

    private val dayModelCallbacks = ArrayList<DayModelCallback>()

    abstract class DayModelCallback {
        open fun onGetDayModel(dayModel: DayModel) {}
    }

    private val dayUrl: String by lazy {
        "http://gank.io/api/day/%04d/%02d/%02d".format(timestamp.year, timestamp.month, timestamp.day)
    }

    /**
     * Gets [DayModel] model and sets data.
     */
    private fun netGetDay() {
        val url = dayUrl
        netGet(url, object : NetModelCallback<DayModel>() {
            override fun onSuccess(call: Call, model: DayModel, response: Response,
                    byteArray: ByteArray) {
                super.onSuccess(call, model, response, byteArray)

                if (userVisibleHint) {
                    actionBarParentFragment?.setActionBarMode(EBActionBarFragment.ActionBarMode.SCROLL, true, true,
                            true)
                }

                dayModel = model

                for (dayModelCallback in dayModelCallbacks) {
                    dayModelCallback.onGetDayModel(dayModel!!)
                }
                dayModelCallbacks.clear()

                adapter.setNewData(dayModel)

                // TODO Preload 福利 image.
            }
        })
    }

    @CallSuper override fun onFront() {
        super.onFront()

        EBUtil.handler.post {
            if (actionBarParentFragment != null) {
                actionBarParentFragment!!.setNestedScrollingChild(dayRecyclerView)
                actionBarParentFragment!!.setActionBarMode(EBActionBarFragment.ActionBarMode.SCROLL, false, null,
                        false)
            }
        }
    }

    companion object {
        //*************************************************************************************************************
        // Arguments.

        private val ARG_TIMESTAMP = "timestamp"

        fun newInstance(timestamp: Timestamp): DayFragment {
            val args = Bundle()
            args.putParcelable(ARG_TIMESTAMP, timestamp)

            val dayFragment = DayFragment()
            dayFragment.arguments = args

            return dayFragment
        }

        //*************************************************************************************************************
        // Instance state.

        private val STATE_DAY_MODEL = "day_model"
    }
}
