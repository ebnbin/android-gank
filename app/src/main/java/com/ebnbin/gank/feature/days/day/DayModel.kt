package com.ebnbin.gank.feature.days.day

import android.text.TextUtils

import com.ebnbin.ebapplication.model.EBModel
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

        //*************************************************************************************************************

        class DataModel private constructor() : EBModel() {
            @SerializedName("_id") private var id: String? = null
            private var createdAt: String? = null
            private var desc: String? = null
            private var images: List<String>? = null
            private var publishedAt: String? = null
            private var source: String? = null
            private var type: String? = null
            private var url: String? = null
            private var isUsed: Boolean = false
            private var who: String? = null

            /**
             * 如果 _id 不为空则有效.
             */
            override val isValid: Boolean
                get() = !TextUtils.isEmpty(id)

            val validFuli: String? by lazy {
                if (FULI != type || TextUtils.isEmpty(url)) null else url
            }

            val validDesc: String? by lazy {
                if (TextUtils.isEmpty(desc)) null else desc
            }

            val validImageA: String? by lazy {
                if (images == null || images!!.isEmpty() || TextUtils.isEmpty(images!![0])) null else images!![0]
            }

            val validImageB: String? by lazy {
                if (images == null || images!!.size <= 1 || TextUtils.isEmpty(images!![1])) null else images!![1]
            }

            val validImageC: String? by lazy {
                if (images == null || images!!.size <= 2 || TextUtils.isEmpty(images!![2])) null else images!![2]
            }

            val validUrl: String? by lazy {
                if (TextUtils.isEmpty(url)) null else url
            }

            companion object {
                const val FULI = "福利"
                const val IOS = "iOS"
                const val ANDROID = "Android"
                const val QIANDUAN = "前端"
                const val XIATUIJIAN = "瞎推荐"
                const val TUOZHANZIYUAN = "拓展资源"
                const val APP = "App"
                const val XIUXISHIPIN = "休息视频"
            }
        }
    }
}
