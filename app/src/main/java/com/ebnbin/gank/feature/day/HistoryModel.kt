package com.ebnbin.gank.feature.day

import com.ebnbin.eb.util.Timestamp
import com.ebnbin.ebapplication.model.EBModel
import java.text.ParseException

/**
 * "http://gank.io/api/day/history".
 */
class HistoryModel private constructor() : EBModel() {
    private var error: Boolean = false
    private var results: List<String>? = null

    /**
     * 返回一个日期从小到大排序的 [Timestamp] [ArrayList].
     */
    val timestamps: ArrayList<Timestamp> by lazy {
        val timestamps = ArrayList<Timestamp>()
        if (results != null) {
            for (dateString in results!!.reversed()) {
                try {
                    val timestamp = Timestamp.newInstance(dateString, "yyyy-MM-dd", true)
                    timestamps.add(timestamp)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }
        timestamps
    }

    /**
     * 如果 error 为 `false` 且日期字符串有效则有效.
     */
    override val isValid: Boolean
        get() = !error && timestamps.isNotEmpty()
}
