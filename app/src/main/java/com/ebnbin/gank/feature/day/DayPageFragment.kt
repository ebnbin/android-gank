package com.ebnbin.gank.feature.day

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.ebnbin.eb.context.EBActionBarFragment
import com.ebnbin.eb.net.NetModelCallback
import com.ebnbin.eb.util.time.EBDate
import com.ebnbin.gank.feature.data.Category
import com.ebnbin.gank.feature.data.DataEntity
import com.ebnbin.gank.feature.data.DataFragment
import com.ebnbin.gank.feature.data.DataModel
import okhttp3.Call
import okhttp3.Response
import java.util.ArrayList

/**
 * 用 [RecyclerView] 展示某日期的数据.
 */
class DayPageFragment : DataFragment() {
    private val timestamp: EBDate by lazy {
        arguments.getParcelable<EBDate>(ARG_TIMESTAMP)
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

        dayModel = savedInstanceState.getSerializable(STATE_DAY_MODEL) as DayModel?
        if (dayModel == null) {
            return false
        }

        adapter.setNewData(convertData(dayModel!!))

        return true
    }

    // TODO Allow to be null.
    private fun convertData(dayModel: DayModel): List<DataEntity> {
        val entities = ArrayList<DataEntity>()

        addTypeEntities(entities, Category.FULI, dayModel.results!!.fuli)
        addTypeEntities(entities, Category.IOS, dayModel.results!!.ios)
        addTypeEntities(entities, Category.ANDROID, dayModel.results!!.android)
        addTypeEntities(entities, Category.QIANDUAN, dayModel.results!!.qianduan)
        addTypeEntities(entities, Category.XIATUIJIAN, dayModel.results!!.xiatuijian)
        addTypeEntities(entities, Category.TUOZHANZIYUAN, dayModel.results!!.tuozhanziyuan)
        addTypeEntities(entities, Category.APP, dayModel.results!!.app)
        addTypeEntities(entities, Category.XIUXISHIPIN, dayModel.results!!.xiuxishipin)

        return entities
    }

    /**
     * 添加某类型的数据.
     *
     * @param entities 要添加到的 [ArrayList].
     * @param category 类型.
     * @param dataModels 该类型的数据.
     */
    private fun addTypeEntities(entities: ArrayList<DataEntity>, category: com.ebnbin.gank.feature.data.Category,
            dataModels: List<DataModel>?) {
        if (dataModels == null || dataModels.isEmpty()) {
            return
        }

        val categoryEntity = DataEntity.Category(category)
        entities.add(categoryEntity)

        dataModels.mapTo(entities) { DataEntity.Data(it) }
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

    private val actionBarParentFragment: EBActionBarFragment? by lazy {
        getTParentEBFragment(EBActionBarFragment::class)
    }

    /**
     * Gets [DayModel] model and sets data.
     */
    private fun netGetDay() {
        val url = dayUrl
        netGet(url, object : NetModelCallback<DayModel>() {
            override fun onSuccess(call: Call, model: DayModel, json: String, response: Response) {
                super.onSuccess(call, model, json, response)

                if (userVisibleHint) {
                    actionBarParentFragment?.setActionBarMode(EBActionBarFragment.ActionBarMode.SCROLL, true, true,
                            true)
                }

                dayModel = model

                for (dayModelCallback in dayModelCallbacks) {
                    dayModelCallback.onGetDayModel(dayModel!!)
                }
                dayModelCallbacks.clear()

                adapter.setNewData(convertData(dayModel!!))

                // TODO Preload 福利 image.
            }
        })
    }

    companion object {
        //*************************************************************************************************************
        // Arguments.

        private val ARG_TIMESTAMP = "timestamp"

        fun newInstance(timestamp: EBDate): DayPageFragment {
            val args = Bundle()
            args.putParcelable(ARG_TIMESTAMP, timestamp)

            val dayFragment = DayPageFragment()
            dayFragment.arguments = args

            return dayFragment
        }

        //*************************************************************************************************************
        // Instance state.

        private val STATE_DAY_MODEL = "day_model"
    }
}
