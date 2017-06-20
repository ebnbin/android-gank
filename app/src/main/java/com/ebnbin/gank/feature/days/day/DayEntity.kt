package com.ebnbin.gank.feature.days.day

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.util.*

/**
 * Day entity.
 */
internal abstract class DayEntity : MultiItemEntity {
    /**
     * Category 类型实体.
     */
    internal class Category internal constructor(val category: String) : DayEntity() {
        override fun getItemType() = CATEGORY
    }

    /**
     * DataModel 类型实体.
     */
    internal class Data internal constructor(val dataModel: DayModel.ResultsModel.DataModel) : DayEntity() {
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
         * Creates new data for [DayAdapter].
         */
        fun newDayEntities(dayModel: DayModel?): ArrayList<DayEntity> {
            val dayEntities = ArrayList<DayEntity>()

            if (dayModel == null) {
                return dayEntities
            }

            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.FULI, dayModel.results!!.fuli)
            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.IOS, dayModel.results!!.ios)
            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.ANDROID, dayModel.results!!.android)
            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.QIANDUAN, dayModel.results!!.qianduan)
            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.XIATUIJIAN, dayModel.results!!.xiatuijian)
            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.TUOZHANZIYUAN,
                    dayModel.results!!.tuozhanziyuan)
            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.APP, dayModel.results!!.app)
            addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.XIUXISHIPIN, dayModel.results!!.xiuxishipin)

            return dayEntities
        }

        /**
         * 添加某类型的数据.
         *
         * @param dayEntities 要添加到的 [ArrayList].
         * @param category 类型.
         * @param dataModels 该类型的数据.
         */
        private fun addTypeEntities(dayEntities: ArrayList<DayEntity>, category: String,
                dataModels: List<DayModel.ResultsModel.DataModel>?) {
            if (dataModels == null || dataModels.isEmpty()) {
                return
            }

            val categoryEntity = Category(category)
            dayEntities.add(categoryEntity)

            dataModels.mapTo(dayEntities) { Data(it) }
        }
    }
}
