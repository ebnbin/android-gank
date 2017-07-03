package com.ebnbin.gank.feature.data

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Day entity.
 */
abstract class DataEntity : MultiItemEntity {
    /**
     * Category 类型实体.
     */
    internal class Category internal constructor(val category: com.ebnbin.gank.feature.data.Category) : DataEntity() {
        override fun getItemType() = CATEGORY
    }

    /**
     * DataModel 类型实体.
     */
    internal class Data internal constructor(val dataModel: DataModel) : DataEntity() {
        override fun getItemType() = DATA
    }

    companion object {
        /**
         * 类型.
         */
        const val CATEGORY = 0
        /**
         * 数据.
         */
        const val DATA = 1
    }
}
