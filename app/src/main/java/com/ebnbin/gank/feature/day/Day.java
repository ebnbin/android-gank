package com.ebnbin.gank.feature.day;

import android.support.annotation.NonNull;

import com.ebnbin.gank.base.BaseModel;
import com.ebnbin.gank.base.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Day model.
 */
final class Day extends BaseResponseModel {
    @SerializedName("results")
    private Results mResults;
    @SerializedName("category")
    private String[] mCategory;

    private Day() {
    }

    @Override
    public Results getResults() {
        return mResults;
    }

    public String[] getCategory() {
        return mCategory;
    }

    /**
     * Results model.
     */
    static final class Results extends BaseModel {
        @SerializedName(Data.ANDROID)
        private Data[] mAndroid;
        @SerializedName(Data.APP)
        private Data[] mApp;
        @SerializedName(Data.IOS)
        private Data[] mIOS;
        @SerializedName(Data.XIUXISHIPIN)
        private Data[] mXiuxishipin;
        @SerializedName(Data.QIANDUAN)
        private Data[] mQianduan;
        @SerializedName(Data.TUOZHANZIYUAN)
        private Data[] mTuozhanziyuan;
        @SerializedName(Data.XIATUIJIAN)
        private Data[] mXiatuijian;
        @SerializedName(Data.FULI)
        private Data[] mFuli;

        private Results() {
        }

        public Data[] getAndroid() {
            return mAndroid;
        }

        public Data[] getApp() {
            return mApp;
        }

        public Data[] getIOS() {
            return mIOS;
        }

        public Data[] getXiuxishipin() {
            return mXiuxishipin;
        }

        public Data[] getQianduan() {
            return mQianduan;
        }

        public Data[] getTuozhanziyuan() {
            return mTuozhanziyuan;
        }

        public Data[] getXiatuijian() {
            return mXiatuijian;
        }

        public Data[] getFuli() {
            return mFuli;
        }

        /**
         * Data model.
         */
        static final class Data extends BaseModel {
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

            private Data() {
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

            @NonNull
            public String getValidDesc() {
                return mDesc == null ? "" : mDesc;
            }
        }
    }
}
