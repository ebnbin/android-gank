package com.ebnbin.gank.feature.data

import android.text.TextUtils
import com.ebnbin.eb.model.EBModel
import com.google.gson.annotations.SerializedName

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
        if (Category.FULI.title != type || TextUtils.isEmpty(url)) null else url
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
