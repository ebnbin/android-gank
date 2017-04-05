package com.ebnbin.gank.feature.daysviewpager;

import com.ebnbin.ebapplication.model.EBModel;
import com.google.gson.annotations.SerializedName;

/**
 * History model.
 */
final class HistoryModel extends EBModel {
    @SerializedName("error")
    private boolean mError;
    @SerializedName("results")
    private String[] mResults;

    private HistoryModel() {
    }

    public boolean isError() {
        return mError;
    }

    public String[] getResults() {
        return mResults;
    }

    /**
     * 如果非 error 且 result 不为空则有效.
     */
    @Override
    public boolean isValid() {
        return !mError && mResults != null && mResults.length > 0;
    }
}
