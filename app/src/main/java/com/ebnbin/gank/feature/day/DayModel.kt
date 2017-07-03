package com.ebnbin.gank.feature.day

import android.text.TextUtils

import com.ebnbin.ebapplication.model.EBModel
import com.ebnbin.gank.feature.data.DataModel
import com.google.gson.annotations.SerializedName

/**
 * "http://gank.io/api/day/2015/08/07".
 */
class DayModel private constructor() : EBModel() {
    private var category: Array<String>? = null
    private var error: Boolean = false
    var results: ResultsModel? = null
        private set

    /**
     * 如果非 error 且 result 不为空且 result 有效则有效.
     */
    override val isValid: Boolean
        get() = !error && results != null && results!!.isValid

    //*****************************************************************************************************************

    class ResultsModel private constructor() : EBModel() {
        @SerializedName(DataModel.ANDROID) var android: List<DataModel>? = null
            private set
        @SerializedName(DataModel.APP) var app: List<DataModel>? = null
            private set
        @SerializedName(DataModel.IOS) var ios: List<DataModel>? = null
            private set
        @SerializedName(DataModel.XIUXISHIPIN) var xiuxishipin: List<DataModel>? = null
            private set
        @SerializedName(DataModel.QIANDUAN) var qianduan: List<DataModel>? = null
            private set
        @SerializedName(DataModel.TUOZHANZIYUAN) var tuozhanziyuan: List<DataModel>? = null
            private set
        @SerializedName(DataModel.XIATUIJIAN) var xiatuijian: List<DataModel>? = null
            private set
        @SerializedName(DataModel.FULI) var fuli: List<DataModel>? = null
            private set

        /**
         * 如果所有数据有效且存在不为空的数据则有效.
         */
        override val isValid: Boolean
            get() = isDatasValidIgnoreEmpty(fuli)
                    && isDatasValidIgnoreEmpty(ios)
                    && isDatasValidIgnoreEmpty(android)
                    && isDatasValidIgnoreEmpty(qianduan)
                    && isDatasValidIgnoreEmpty(xiatuijian)
                    && isDatasValidIgnoreEmpty(tuozhanziyuan)
                    && isDatasValidIgnoreEmpty(app)
                    && isDatasValidIgnoreEmpty(xiuxishipin)
                    && (!isDatasEmpty(fuli)
                    || !isDatasEmpty(ios)
                    || !isDatasEmpty(android)
                    || !isDatasEmpty(qianduan)
                    || !isDatasEmpty(xiatuijian)
                    || !isDatasEmpty(tuozhanziyuan)
                    || !isDatasEmpty(app)
                    || !isDatasEmpty(xiuxishipin))

        /**
         * 如果为空或所有数据有效则有效.
         */
        private fun isDatasValidIgnoreEmpty(dataModels: List<DataModel>?): Boolean {
            if (isDatasEmpty(dataModels)) return true

            return dataModels!!.any { it.isValid }
        }

        /**
         * 返回是否为空.
         */
        private fun isDatasEmpty(dataModels: List<DataModel>?): Boolean {
            return dataModels == null || dataModels.isEmpty()
        }

        /**
         * 有效的第一个福利 url.
         */
        val validFirstFuli: String? by lazy {
            if (isDatasEmpty(fuli) || !isDatasValidIgnoreEmpty(fuli) || TextUtils.isEmpty(fuli!![0].validFuli)) null
            else fuli!![0].validFuli
        }
    }
}
