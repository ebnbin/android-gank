package com.ebnbin.gank;

import com.ebnbin.eb.util.EBUtil;
import com.ebnbin.ebapplication.context.EBApplication;

public final class GankApplication extends EBApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initEBUtil();
    }

    private void initEBUtil() {
        EBUtil.debug = BuildConfig.DEBUG;
    }
}
