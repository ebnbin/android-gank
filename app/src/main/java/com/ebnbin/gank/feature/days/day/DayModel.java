package com.ebnbin.gank.feature.days.day;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ebnbin.ebapplication.model.EBModel;
import com.google.gson.annotations.SerializedName;

/**
 * Day model.
 */
final class DayModel extends EBModel {
    private static final long serialVersionUID = 1L;

    @SerializedName("category")
    private String[] mCategory;
    @SerializedName("error")
    private boolean mError;
    @SerializedName("results")
    private ResultsModel mResults;

    private DayModel() {
    }

    public String[] getCategory() {
        return mCategory;
    }

    public boolean isError() {
        return mError;
    }

    public ResultsModel getResults() {
        return mResults;
    }

    //*****************************************************************************************************************
    // Valid.

    /**
     * 如果非 error 且 result 不为空且 result 有效则有效.
     */
    @Override
    public boolean isValid() {
        return !mError && mResults != null && mResults.isValid();
    }

    //*****************************************************************************************************************

    /**
     * Results model.
     */
    static final class ResultsModel extends EBModel {
        private static final long serialVersionUID = 1L;

        @SerializedName(DataModel.ANDROID)
        private DataModel[] mAndroid;
        @SerializedName(DataModel.APP)
        private DataModel[] mApp;
        @SerializedName(DataModel.IOS)
        private DataModel[] mIOS;
        @SerializedName(DataModel.XIUXISHIPIN)
        private DataModel[] mXiuxishipin;
        @SerializedName(DataModel.QIANDUAN)
        private DataModel[] mQianduan;
        @SerializedName(DataModel.TUOZHANZIYUAN)
        private DataModel[] mTuozhanziyuan;
        @SerializedName(DataModel.XIATUIJIAN)
        private DataModel[] mXiatuijian;
        @SerializedName(DataModel.FULI)
        private DataModel[] mFuli;

        private ResultsModel() {
        }

        public DataModel[] getAndroid() {
            return mAndroid;
        }

        public DataModel[] getApp() {
            return mApp;
        }

        public DataModel[] getIOS() {
            return mIOS;
        }

        public DataModel[] getXiuxishipin() {
            return mXiuxishipin;
        }

        public DataModel[] getQianduan() {
            return mQianduan;
        }

        public DataModel[] getTuozhanziyuan() {
            return mTuozhanziyuan;
        }

        public DataModel[] getXiatuijian() {
            return mXiatuijian;
        }

        public DataModel[] getFuli() {
            return mFuli;
        }

        //*************************************************************************************************************
        // Valid.

        /**
         * 如果存在有效的数据则有效.
         */
        @Override
        public boolean isValid() {
            return isDatasValid(mFuli)
                    || isDatasValid(mIOS)
                    || isDatasValid(mAndroid)
                    || isDatasValid(mQianduan)
                    || isDatasValid(mXiatuijian)
                    || isDatasValid(mTuozhanziyuan)
                    || isDatasValid(mApp)
                    || isDatasValid(mXiuxishipin);
        }

        /**
         * 如果不为空且所有数据有效则有效.
         */
        private boolean isDatasValid(@Nullable DataModel[] dataModels) {
            if (dataModels == null || dataModels.length <= 0) {
                return false;
            }

            for (DataModel dataModel : dataModels) {
                if (!dataModel.isValid()) {
                    return false;
                }
            }

            return true;
        }

        @NonNull
        public String getValidFuliUrl() {
            return isDatasValid(mFuli) ? mFuli[0].mUrl : "";
        }

        //*************************************************************************************************************

        /**
         * Data model.
         */
        static final class DataModel extends EBModel {
            private static final long serialVersionUID = 1L;

            public static final String FULI = "福利";
            public static final String IOS = "iOS";
            public static final String ANDROID = "Android";
            public static final String QIANDUAN = "前端";
            public static final String XIATUIJIAN = "瞎推荐";
            public static final String TUOZHANZIYUAN = "拓展资源";
            public static final String APP = "App";
            public static final String XIUXISHIPIN = "休息视频";

            @SerializedName("_id")
            private String mId;
            @SerializedName("createdAt")
            private String mCreatedAt;
            @SerializedName("desc")
            private String mDesc;
            @SerializedName("images")
            private String[] mImages;
            @SerializedName("publishedAt")
            private String mPublishedAt;
            @SerializedName("source")
            private String mSource;
            @SerializedName("type")
            private String mType;
            @SerializedName("url")
            private String mUrl;
            @SerializedName("used")
            private boolean mUsed;
            @SerializedName("who")
            private String mWho;

            private DataModel() {
            }

            public String getId() {
                return mId;
            }

            public String getCreatedAt() {
                return mCreatedAt;
            }

            public String getDesc() {
                return mDesc;
            }

            public String[] getImages() {
                return mImages;
            }

            public String getPublishedAt() {
                return mPublishedAt;
            }

            public String getSource() {
                return mSource;
            }

            public String getType() {
                return mType;
            }

            public String getUrl() {
                return mUrl;
            }

            public boolean isUsed() {
                return mUsed;
            }

            public String getWho() {
                return mWho;
            }

            //*********************************************************************************************************
            // Valid.

            /**
             * 如果 _id 不为空则有效.
             */
            @Override
            public boolean isValid() {
                return !TextUtils.isEmpty(mId);
            }

            @NonNull
            public String getValidFuli() {
                return !FULI.equals(mType) || mUrl == null ? "" : mUrl;
            }

            @NonNull
            public String getValidDesc() {
                return mDesc == null ? "" : mDesc;
            }

            @NonNull
            public String getValidImageA() {
                return mImages == null || mImages.length <= 0 ? "" : mImages[0];
            }

            @NonNull
            public String getValidImageB() {
                return mImages == null || mImages.length <= 1 ? "" : mImages[1];
            }

            @NonNull
            public String getValidImageC() {
                return mImages == null || mImages.length <= 2 ? "" : mImages[2];
            }

            @NonNull
            public String getValidUrl() {
                return mUrl == null ? "" : mUrl;
            }
        }
    }
}
