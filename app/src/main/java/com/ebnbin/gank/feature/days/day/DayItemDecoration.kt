package com.ebnbin.gank.feature.days.day

import android.content.Context

import com.ebnbin.ebapplication.view.SpacingItemDecoration
import com.ebnbin.gank.R

/**
 * Day [android.support.v7.widget.RecyclerView.ItemDecoration].
 */
internal class DayItemDecoration(context: Context) : SpacingItemDecoration() {
    init {
        addSpacingType(DayEntity.DATA, 0, context.resources.getDimensionPixelSize(R.dimen.day_item_offset), 0)
    }
}
