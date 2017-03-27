package com.ebnbin.gank.feature.day;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebnbin.eb.util.Util;
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

                loadImage(helper.getView(R.id.fuli), data.fuli, true);

                helper.setText(R.id.desc, data.desc);

                loadImage(helper.getView(R.id.imageA), data.imageA, false);

                loadImage(helper.getView(R.id.imageB), data.imageB, false);

                loadImage(helper.getView(R.id.imageC), data.imageC, false);

                break;
            }
        }
    }

    /**
     * Loads an images.
     */
    private void loadImage(@NonNull ImageView imageView, @Nullable String path, boolean resize) {
        boolean hasPath = !Util.isEmpty(path);
        imageView.setVisibility(hasPath ? View.VISIBLE : View.GONE);
        if (!hasPath) {
            return;
        }

        imageView.post(() -> {
            Context context = imageView.getContext();

            RequestCreator requestCreator = Picasso
                    .with(context)
                    .load(path)
                    .stableKey(path);

            if (resize) {
                int targetWidth = imageView.getWidth();
                if (targetWidth > 0) {
                    requestCreator = requestCreator.resize(targetWidth, 0);
                }
            }

            int tintColor = context.getColor(R.color.eb_hint);

            Drawable placeholderDrawable = VectorDrawableCompat.create(context.getResources(),
                    R.drawable.day_placeholder, null);
            if (placeholderDrawable != null) {
                placeholderDrawable.setTint(tintColor);

                requestCreator = requestCreator.placeholder(placeholderDrawable);
            }

            Drawable errorDrawable = VectorDrawableCompat.create(context.getResources(), R.drawable.day_error, null);
            if (errorDrawable != null) {
                errorDrawable.setTint(tintColor);

                requestCreator = requestCreator.error(errorDrawable);
            }

            requestCreator.into(imageView);
        });
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
