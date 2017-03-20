package com.ebnbin.gank.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

/**
 * Base model.
 */
public abstract class BaseModel {
    /**
     * Parses current model to Json.
     *
     * @return Json string.
     */
    @NonNull
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
