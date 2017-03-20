package com.ebnbin.gank.feature.day;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebnbin.gank.R;

/**
 * Day {@link RecyclerView.Adapter}.
 */
final class DayAdapter extends BaseMultiItemQuickAdapter<DayEntity, BaseViewHolder> {
    DayAdapter() {
        super(null);

        addItemType(DayEntity.CATEGORY, R.layout.day_item_category);
        addItemType(DayEntity.DATA, R.layout.day_item_data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DayEntity item) {
        switch (helper.getItemViewType()) {
            case DayEntity.CATEGORY: {
                DayEntity.Category category = (DayEntity.Category) item;

                helper.setText(R.id.category, category.category);

                break;
            }
            case DayEntity.DATA: {
                DayEntity.Data data = (DayEntity.Data) item;

                helper.setText(R.id.desc, data.desc);

                break;
            }
        }
    }
}
