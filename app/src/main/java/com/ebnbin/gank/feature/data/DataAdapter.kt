package com.ebnbin.gank.feature.data

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ebnbin.gank.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Day [android.support.v7.widget.RecyclerView.Adapter].
 */
internal class DataAdapter : BaseMultiItemQuickAdapter<DataEntity, BaseViewHolder>(null) {
    init {
        addItemType(DataEntity.CATEGORY, R.layout.days_day_item_category)
        addItemType(DataEntity.DATA, R.layout.days_day_item_data)
    }

    override fun convert(helper: BaseViewHolder, item: DataEntity) {
        when (helper.itemViewType) {
            DataEntity.CATEGORY -> {
                val category = item as DataEntity.Category

                helper.setText(R.id.category, category.category.title)
            }
            DataEntity.DATA -> {
                val data = item as DataEntity.Data

                helper.getView<android.view.View>(R.id.data).setOnClickListener {
                    for (listener in listeners) {
                        listener.onDataClick(data)
                    }
                }

                loadImage(helper.getView<android.widget.ImageView>(R.id.fuli), data.dataModel.validFuli, true)

                helper.setText(com.ebnbin.gank.R.id.desc, data.dataModel.validDesc)

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

            val placeholderDrawable = mContext.getDrawable(R.drawable.days_day_placeholder)
            if (placeholderDrawable != null) {
                requestCreator.placeholder(placeholderDrawable)
            }

            val errorDrawable = mContext.getDrawable(R.drawable.days_day_error)
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

    //*****************************************************************************************************************
    // Listeners.

    val listeners = ArrayList<Listener>()

    internal abstract class Listener {
        internal open fun onDataClick(data: DataEntity.Data) {}
    }
}
