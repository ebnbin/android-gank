package com.ebnbin.gank.feature.day

import com.ebnbin.eb.net.NetModel
import com.ebnbin.eb.util.time.EBDate
import java.text.ParseException

/**
 * "http://gank.io/api/day/history".
 */
class HistoryModel private constructor() : NetModel() {
    private var error: Boolean = false
    private var results: List<String>? = null

    /**
     * 返回一个日期从小到大排序的 [EBDate] [ArrayList].
     */
    val timestamps: ArrayList<EBDate> by lazy {
        val timestamps = ArrayList<EBDate>()
        if (results != null) {
            for (dateString in results!!.reversed()) {
                try {
                    val timestamp = EBDate.new(dateString)
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
