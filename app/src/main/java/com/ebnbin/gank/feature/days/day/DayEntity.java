package com.ebnbin.gank.feature.days.day;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

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
     */
    @NonNull
    public static ArrayList<DayEntity> newDayEntities(@Nullable DayModel dayModel) {
        ArrayList<DayEntity> dayEntities = new ArrayList<>();

        if (dayModel == null || dayModel.isError()) {
            return dayEntities;
        }

        DayModel.ResultsModel resultsModel = dayModel.getResults();
        if (resultsModel == null) {
            return dayEntities;
        }

        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.FULI, resultsModel.getFuli());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.IOS, resultsModel.getIOS());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.ANDROID, resultsModel.getAndroid());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.QIANDUAN, resultsModel.getQianduan());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.XIATUIJIAN, resultsModel.getXiatuijian());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.TUOZHANZIYUAN, resultsModel.getTuozhanziyuan());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.APP, resultsModel.getApp());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.XIUXISHIPIN, resultsModel.getXiuxishipin());

        return dayEntities;
    }

    /**
     * 添加某类型的数据.
     *
     * @param dayEntities
     *         要添加到的 ArrayList.
     * @param category
     *         类型.
     * @param dataModels
     *         该类型的数据.
     */
    private static void addTypeEntities(@NonNull ArrayList<DayEntity> dayEntities, @NonNull String category,
            @Nullable DayModel.ResultsModel.DataModel[] dataModels) {
        if (dataModels == null || dataModels.length <= 0) {
            return;
        }

        Category categoryEntity = new Category(category);
        dayEntities.add(categoryEntity);

        for (DayModel.ResultsModel.DataModel dataModel : dataModels) {
            Data dataEntity = new Data(dataModel);
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
     * DataModel 类型实体.
     */
    static final class Data extends DayEntity {
        @NonNull
        public final String fuli;
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

        private Data(@NonNull DayModel.ResultsModel.DataModel dataModel) {
            fuli = dataModel.getValidFuli();
            desc = dataModel.getValidDesc();
            imageA = dataModel.getValidImageA();
            imageB = dataModel.getValidImageB();
            imageC = dataModel.getValidImageC();
            url = dataModel.getValidUrl();
        }

        @Override
        public int getItemType() {
            return DATA;
        }
    }
}
