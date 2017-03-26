package com.ebnbin.gank.feature.day;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebnbin.gank.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

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
        Context context = helper.convertView.getContext();

        switch (helper.getItemViewType()) {
            case DayEntity.CATEGORY: {
                DayEntity.Category category = (DayEntity.Category) item;

                helper.setText(R.id.category, category.category);

                break;
            }
            case DayEntity.DATA: {
                DayEntity.Data data = (DayEntity.Data) item;

                helper.convertView.setOnClickListener(v -> {
                    for (Listener listener : listeners) {
                        listener.onConvertViewClick(data);
                    }
                });

                // TODO: OOM.
                String fuli = data.fuli;
                boolean hasFuli = !TextUtils.isEmpty(fuli);
                helper.setVisible(R.id.fuli, hasFuli);
                if (hasFuli) {
                    ImageView fuliImageView = helper.getView(R.id.fuli);
                    fuliImageView.post(() -> {
                        RequestCreator requestCreator = Picasso
                                .with(context)
                                .load(fuli)
                                .stableKey(fuli);

                        int width = fuliImageView.getWidth();
                        if (width > 0) {
                            requestCreator = requestCreator.resize(width, 0);
                        }

                        requestCreator
                                .placeholder(R.drawable.day_placeholder)
                                .error(R.drawable.day_error)
                                .into(fuliImageView);
                    });
                }

                helper.setText(R.id.desc, data.desc);

                String imageA = data.imageA;
                boolean hasImageA = !TextUtils.isEmpty(imageA);
                helper.setVisible(R.id.imageA, hasImageA);
                if (hasImageA) {
                    Picasso
                            .with(context)
                            .load(imageA)
                            .stableKey(imageA)
                            .placeholder(R.drawable.day_placeholder)
                            .error(R.drawable.day_error)
                            .into((ImageView) helper.getView(R.id.imageA));
                }

                String imageB = data.imageB;
                boolean hasImageB = !TextUtils.isEmpty(imageB);
                helper.setVisible(R.id.imageB, hasImageB);
                if (hasImageB) {
                    Picasso
                            .with(context)
                            .load(imageB)
                            .stableKey(imageB)
                            .placeholder(R.drawable.day_placeholder)
                            .error(R.drawable.day_error)
                            .into((ImageView) helper.getView(R.id.imageB));
                }

                String imageC = data.imageC;
                boolean hasImageC = !TextUtils.isEmpty(imageC);
                helper.setVisible(R.id.imageC, hasImageC);
                if (hasImageC) {
                    Picasso
                            .with(context)
                            .load(imageC)
                            .stableKey(imageC)
                            .placeholder(R.drawable.day_placeholder)
                            .error(R.drawable.day_error)
                            .into((ImageView) helper.getView(R.id.imageC));
                }

                break;
            }
        }
    }

    /**
     * Sets {@link DayModel} data.
     */
    public void setDay(@Nullable DayModel dayModel) {
        List<DayEntity> dayEntities = DayEntity.newDayEntities(dayModel);
        setNewData(dayEntities);
    }

    //*****************************************************************************************************************
    // Listeners.

    public final List<Listener> listeners = new ArrayList<>();

    static abstract class Listener {
        void onConvertViewClick(@NonNull DayEntity.Data data) {
        }
    }
}
