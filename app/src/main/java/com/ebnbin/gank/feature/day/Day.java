package com.ebnbin.gank.feature.day;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Day model.
 */
final class Day {
    @SerializedName("category")
    private String[] mCategory;
    @SerializedName("error")
    private boolean mError;
    @SerializedName("results")
    private Results mResults;

    private Day() {
    }

    public String[] getCategory() {
        return mCategory;
    }

    public boolean isError() {
        return mError;
    }

    public Results getResults() {
        return mResults;
    }

    @Override
    public String toString() {
        return "Day{" +
                "mCategory=" + Arrays.toString(mCategory) +
                ", mError=" + mError +
                ", mResults=" + mResults +
                '}';
    }

    /**
     * Results model.
     */
    static final class Results {
        @SerializedName("Android")
        private Data[] mAndroid;
        @SerializedName("App")
        private Data[] mApp;
        @SerializedName("iOS")
        private Data[] mIOS;
        @SerializedName("休息视频")
        private Data[] mXiuxishipin;
        @SerializedName("前端")
        private Data[] mQianduan;
        @SerializedName("拓展资源")
        private Data[] mTuozhanziyuan;
        @SerializedName("瞎推荐")
        private Data[] mXiatuijian;
        @SerializedName("福利")
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

        @Override
        public String toString() {
            return "Results{" +
                    "mAndroid=" + Arrays.toString(mAndroid) +
                    ", mApp=" + Arrays.toString(mApp) +
                    ", mIOS=" + Arrays.toString(mIOS) +
                    ", mXiuxishipin=" + Arrays.toString(mXiuxishipin) +
                    ", mQianduan=" + Arrays.toString(mQianduan) +
                    ", mTuozhanziyuan=" + Arrays.toString(mTuozhanziyuan) +
                    ", mXiatuijian=" + Arrays.toString(mXiatuijian) +
                    ", mFuli=" + Arrays.toString(mFuli) +
                    '}';
        }

        /**
         * Data model.
         */
        static final class Data {
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

            @Override
            public String toString() {
                return "Data{" +
                        "mId='" + mId + '\'' +
                        ", mCreatedAt='" + mCreatedAt + '\'' +
                        ", mDesc='" + mDesc + '\'' +
                        ", mImages=" + Arrays.toString(mImages) +
                        ", mPublishedAt='" + mPublishedAt + '\'' +
                        ", mSource='" + mSource + '\'' +
                        ", mType='" + mType + '\'' +
                        ", mUrl='" + mUrl + '\'' +
                        ", mUsed=" + mUsed +
                        ", mWho='" + mWho + '\'' +
                        '}';
            }
        }
    }
}
