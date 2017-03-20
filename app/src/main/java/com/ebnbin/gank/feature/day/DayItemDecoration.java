package com.ebnbin.gank.feature.day;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebnbin.gank.R;

/**
 * Day {@link RecyclerView.ItemDecoration}. 需要在 {@link RecyclerView#setAdapter(RecyclerView.Adapter)} 之后添加.
 */
final class DayItemDecoration extends RecyclerView.ItemDecoration {
    private final int mItemOffset;

    DayItemDecoration(@NonNull Context context) {
        mItemOffset = context.getResources().getDimensionPixelSize(R.dimen.day_item_offset);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return;
        }

        int position = parent.getChildLayoutPosition(view);
        if (position < 1) {
            return;
        }

        int type = adapter.getItemViewType(position);
        int lastType = adapter.getItemViewType(position - 1);

        if (type != DayEntity.DATA || lastType == DayEntity.CATEGORY) {
            return;
        }

        outRect.set(0, mItemOffset, 0, 0);
    }
}
