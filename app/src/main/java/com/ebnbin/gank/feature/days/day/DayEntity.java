package com.ebnbin.gank.feature.days.day;

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
     */
    @NonNull
    public static ArrayList<DayEntity> newDayEntities(@Nullable DayModel dayModel) {
        ArrayList<DayEntity> dayEntities = new ArrayList<>();

        if (dayModel == null) {
            return dayEntities;
        }

        DayModel.ResultsModel resultsModel = dayModel.getResults();
        if (resultsModel == null) {
            return dayEntities;
        }

        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.FULI, resultsModel.getFuli());
        addTypeEntities(dayEntities, DayModel.ResultsModel.DataModel.IOS, resultsModel.getIos());
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
            @Nullable List<DayModel.ResultsModel.DataModel> dataModels) {
        if (dataModels == null || dataModels.size() <= 0) {
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
        public final DayModel.ResultsModel.DataModel dataModel;

        private Data(@NonNull DayModel.ResultsModel.DataModel dataModel) {
            this.dataModel = dataModel;
        }

        @Override
        public int getItemType() {
            return DATA;
        }
    }
}
