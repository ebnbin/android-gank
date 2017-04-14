package com.ebnbin.gank;

import com.ebnbin.ebapplication.context.EBApplication;
import com.ebnbin.ebapplication.util.EBAppUtil;

public final class GankApplication extends EBApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initEBAppUtil();
    }

    private void initEBAppUtil() {
        EBAppUtil.init(BuildConfig.APPLICATION_ID);
    }
}
