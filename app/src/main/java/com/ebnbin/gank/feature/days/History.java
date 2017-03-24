package com.ebnbin.gank.feature.days;

import com.ebnbin.ebapplication.base.EBModel;
import com.google.gson.annotations.SerializedName;

/**
 * History model.
 */
final class History extends EBModel {
    @SerializedName("error")
    private boolean mError;
    @SerializedName("results")
    private String[] mResults;

    private History() {
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
