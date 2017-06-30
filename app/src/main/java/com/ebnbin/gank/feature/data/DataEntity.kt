package com.ebnbin.gank.feature.data

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.ebnbin.gank.feature.category.CategoryModel
import com.ebnbin.gank.feature.days.day.DayModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * Day entity.
 */
internal abstract class DataEntity : MultiItemEntity {
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
        val CATEGORY = 0
        /**
         * 数据.
         */
        val DATA = 1

        /**
         * Creates new data for [DataAdapter].
         */
        fun newDayEntities(dayModel: DayModel?): ArrayList<DataEntity> {
            val entities = ArrayList<DataEntity>()

            if (dayModel == null) {
                return entities
            }

            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.FULI, dayModel.results!!.fuli)
            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.IOS, dayModel.results!!.ios)
            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.ANDROID, dayModel.results!!.android)
            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.QIANDUAN, dayModel.results!!.qianduan)
            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.XIATUIJIAN, dayModel.results!!.xiatuijian)
            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.TUOZHANZIYUAN, dayModel.results!!.tuozhanziyuan)
            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.APP, dayModel.results!!.app)
            addTypeEntities(entities, com.ebnbin.gank.feature.data.Category.XIUXISHIPIN, dayModel.results!!.xiuxishipin)

            return entities
        }

        fun newCategoryEntities(categoryModel: CategoryModel?): ArrayList<DataEntity> {
            val entities = ArrayList<DataEntity>()

            if (categoryModel == null || categoryModel.results == null) {
                return entities
            }

            categoryModel.results!!.mapTo(entities) { Data(it) }

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

            val categoryEntity = Category(category)
            entities.add(categoryEntity)

            dataModels.mapTo(entities) { Data(it) }
        }
    }
}
