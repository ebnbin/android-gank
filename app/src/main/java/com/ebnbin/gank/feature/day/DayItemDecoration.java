package com.ebnbin.gank.feature.day;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.ebnbin.ebapplication.view.SpacingItemDecoration;
import com.ebnbin.gank.R;

/**
 * Day {@link RecyclerView.ItemDecoration}.
 */
final class DayItemDecoration extends SpacingItemDecoration {
    DayItemDecoration(@NonNull Context context) {
        addSpacingType(DayEntity.DATA, context, 0, R.dimen.day_item_offset, 0);
    }
}
