package com.ebnbin.gank.base;

import com.google.gson.annotations.SerializedName;

/**
 * Base response model.
 */
public abstract class BaseResponseModel extends BaseModel {
    @SerializedName("error")
    private boolean mError;

    public boolean isError() {
        return mError;
    }

    public abstract Object getResults();
}
