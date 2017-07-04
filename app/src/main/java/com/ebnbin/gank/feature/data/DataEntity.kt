package com.ebnbin.gank.feature.data

import android.os.Parcel
import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Day entity.
 */
abstract class DataEntity : MultiItemEntity, Parcelable {
    /**
     * Category 类型实体.
     */
    internal class Category internal constructor(val category: com.ebnbin.gank.feature.data.Category) : DataEntity() {
        constructor(parcel: Parcel) : this(parcel.readSerializable() as com.ebnbin.gank.feature.data.Category)

        override fun getItemType() = CATEGORY
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeSerializable(category)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Category> {
            override fun createFromParcel(parcel: Parcel): Category {
                return Category(parcel)
            }

            override fun newArray(size: Int): Array<Category?> {
                return arrayOfNulls(size)
            }
        }
    }

    /**
     * DataModel 类型实体.
     */
    internal class Data internal constructor(val dataModel: DataModel) : DataEntity() {
        constructor(parcel: Parcel) : this(parcel.readSerializable() as DataModel)

        override fun getItemType() = DATA
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeSerializable(dataModel)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Data> {
            override fun createFromParcel(parcel: Parcel): Data {
                return Data(parcel)
            }

            override fun newArray(size: Int): Array<Data?> {
                return arrayOfNulls(size)
            }
        }
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
