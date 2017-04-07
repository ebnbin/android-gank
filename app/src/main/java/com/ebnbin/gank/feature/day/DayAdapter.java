package com.ebnbin.gank.feature.day;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebnbin.gank.R;
import com.squareup.picasso.Callback;
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

                helper.getView(R.id.data).setOnClickListener(v -> {
                    for (Listener listener : listeners) {
                        listener.onDataClick(data);
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
        boolean hasPath = !TextUtils.isEmpty(path);
        imageView.setVisibility(hasPath ? View.VISIBLE : View.GONE);
        if (!hasPath) {
            return;
        }

        imageView.setOnClickListener(null);

        imageView.post(() -> {
            Context context = imageView.getContext();

            RequestCreator requestCreator = Picasso
                    .with(context)
                    .load(path)
                    .tag(path)
                    .stableKey(path);

            if (resize) {
                int targetWidth = imageView.getWidth();
                if (targetWidth > 0) {
                    requestCreator.resize(targetWidth, 0);
                }
            }

            Drawable placeholderDrawable = context.getDrawable(R.drawable.day_placeholder);
            if (placeholderDrawable != null) {
                requestCreator.placeholder(placeholderDrawable);
            }

            Drawable errorDrawable = context.getDrawable(R.drawable.day_error);
            if (errorDrawable != null) {
                requestCreator.error(errorDrawable);
            }

            requestCreator.into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    imageView.setOnClickListener(v -> loadImage(imageView, path, resize));
                }
            });
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
        void onDataClick(@NonNull DayEntity.Data data) {
        }
    }
}
