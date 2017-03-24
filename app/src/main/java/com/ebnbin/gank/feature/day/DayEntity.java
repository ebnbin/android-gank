package com.ebnbin.gank.feature.day;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Day entity.
 */
abstract class DayEntity implements MultiItemEntity {
    /**
     * 类型.
     */
    public static final int CATEGORY = 0;
    /**
     * 数据.
     */
    public static final int DATA = 1;

    /**
     * Creates new data for {@link DayAdapter}.
     *
     * @param day
     *         Day model.
     */
    @NonNull
    public static List<DayEntity> newDayEntities(@Nullable Day day) {
        List<DayEntity> dayEntities = new ArrayList<>();

        if (day == null || day.isError()) {
            return dayEntities;
        }

        Day.Results results = day.getResults();
        if (results == null) {
            return dayEntities;
        }

        addTypeEntities(dayEntities, Day.Results.Data.FULI, results.getFuli());
        addTypeEntities(dayEntities, Day.Results.Data.IOS, results.getIOS());
        addTypeEntities(dayEntities, Day.Results.Data.ANDROID, results.getAndroid());
        addTypeEntities(dayEntities, Day.Results.Data.QIANDUAN, results.getQianduan());
        addTypeEntities(dayEntities, Day.Results.Data.XIATUIJIAN, results.getXiatuijian());
        addTypeEntities(dayEntities, Day.Results.Data.TUOZHANZIYUAN, results.getTuozhanziyuan());
        addTypeEntities(dayEntities, Day.Results.Data.APP, results.getApp());
        addTypeEntities(dayEntities, Day.Results.Data.XIUXISHIPIN, results.getXiuxishipin());

        return dayEntities;
    }

    /**
     * 添加某类型的数据.
     *
     * @param dayEntities
     *         要添加到的 List.
     * @param category
     *         类型.
     * @param datas
     *         该类型的数据.
     */
    private static void addTypeEntities(@NonNull List<DayEntity> dayEntities, @NonNull String category,
            @Nullable Day.Results.Data[] datas) {
        if (datas == null || datas.length <= 0) {
            return;
        }

        Category categoryEntity = new Category(category);
        dayEntities.add(categoryEntity);

        for (Day.Results.Data data : datas) {
            Data dataEntity = new Data(data);
            dayEntities.add(dataEntity);
        }
    }

    /**
     * Category 类型实体.
     */
    static final class Category extends DayEntity {
        @NonNull
        public final String category;

        private Category(@NonNull String category) {
            this.category = category;
        }

        @Override
        public int getItemType() {
            return CATEGORY;
        }
    }

    /**
     * Data 类型实体.
     */
    static final class Data extends DayEntity {
        @NonNull
        public final String desc;
        @NonNull
        public final String imageA;
        @NonNull
        public final String imageB;
        @NonNull
        public final String imageC;
        @NonNull
        public final String url;

        private Data(@NonNull Day.Results.Data data) {
            desc = data.getValidDesc();
            imageA = data.getValidImageA();
            imageB = data.getValidImageB();
            imageC = data.getValidImageC();
            url = data.getValidUrl();
        }

        @Override
        public int getItemType() {
            return DATA;
        }
    }
}
