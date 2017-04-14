package com.ebnbin.gank.feature.days.dayviewpager;

import com.ebnbin.ebapplication.model.EBModel;
import com.google.gson.annotations.SerializedName;

/**
 * History model.
 */
final class HistoryModel extends EBModel {
    private static final long serialVersionUID = 1L;

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
