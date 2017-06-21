package com.ebnbin.gank.feature.days.day

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ebnbin.gank.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Day [android.support.v7.widget.RecyclerView.Adapter].
 */
internal class DayAdapter : BaseMultiItemQuickAdapter<DayEntity, BaseViewHolder>(null) {
    init {
        addItemType(DayEntity.CATEGORY, R.layout.days_day_item_category)
        addItemType(DayEntity.DATA, R.layout.days_day_item_data)
    }

    override fun convert(helper: BaseViewHolder, item: DayEntity) {
        when (helper.itemViewType) {
            DayEntity.CATEGORY -> {
                val category = item as DayEntity.Category

                helper.setText(R.id.category, category.category)
            }
            DayEntity.DATA -> {
                val data = item as DayEntity.Data

                helper.getView<View>(R.id.data).setOnClickListener {
                    for (listener in listeners) {
                        listener.onDataClick(data)
                    }
                }

                loadImage(helper.getView<ImageView>(R.id.fuli), data.dataModel.validFuli, true)

                helper.setText(R.id.desc, data.dataModel.validDesc)

                loadImage(helper.getView<ImageView>(R.id.imageA), data.dataModel.validImageA, false)
                loadImage(helper.getView<ImageView>(R.id.imageB), data.dataModel.validImageB, false)
                loadImage(helper.getView<ImageView>(R.id.imageC), data.dataModel.validImageC, false)
            }
        }
    }

    /**
     * Loads an image.
     */
    private fun loadImage(imageView: ImageView, url: String?, resize: Boolean) {
        imageView.visibility = if (url == null) View.GONE else View.VISIBLE
        url ?: return

        imageView.setOnClickListener(null)

        imageView.post {
            val requestCreator = Picasso
                    .with(mContext)
                    .load(url)
                    .tag(url)
                    .stableKey(url)

            if (resize && imageView.width > 0) {
                requestCreator.resize(imageView.width, 0)
            }

            val placeholderDrawable = mContext.getDrawable(R.drawable.day_placeholder)
            if (placeholderDrawable != null) {
                requestCreator.placeholder(placeholderDrawable)
            }

            val errorDrawable = mContext.getDrawable(R.drawable.day_error)
            if (errorDrawable != null) {
                requestCreator.error(errorDrawable)
            }

            requestCreator.into(imageView, object : Callback {
                override fun onSuccess() {}

                override fun onError() {
                    imageView.setOnClickListener { loadImage(imageView, url, resize) }
                }
            })
        }
    }

    fun setNewData(dayModel: DayModel?) {
        setNewData(DayEntity.newDayEntities(dayModel))
    }

    //*****************************************************************************************************************
    // Listeners.

    val listeners = ArrayList<Listener>()

    internal abstract class Listener {
        internal open fun onDataClick(data: DayEntity.Data) {}
    }
}
