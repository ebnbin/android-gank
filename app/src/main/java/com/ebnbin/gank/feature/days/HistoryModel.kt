package com.ebnbin.gank.feature.days

import com.ebnbin.eb.util.Timestamp
import com.ebnbin.ebapplication.model.EBModel
import com.google.gson.annotations.SerializedName
import java.text.ParseException

/**
 * "http://gank.io/api/day/history".
 */
class HistoryModel private constructor() : EBModel() {
    @SerializedName("error") private var isError: Boolean = false
    @SerializedName("results") private var results: List<String>? = null

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

    override val isValid: Boolean
        get() = !isError && timestamps.isNotEmpty()
}
