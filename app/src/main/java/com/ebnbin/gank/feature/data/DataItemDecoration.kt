package com.ebnbin.gank.feature.data

import android.content.Context
import com.ebnbin.eb.view.SpacingItemDecoration
import com.ebnbin.gank.R

/**
 * Day [android.support.v7.widget.RecyclerView.ItemDecoration].
 */
internal class DataItemDecoration(context: Context) : SpacingItemDecoration() {
    init {
        addSpacingType(DataEntity.DATA, 0, context.resources.getDimensionPixelSize(R.dimen.day_item_offset), 0)
    }
}
