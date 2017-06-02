package com.ebnbin.gank;

import com.ebnbin.eb.util.EBUtil;
import com.ebnbin.ebapplication.context.EBApplication;

public final class GankApplication extends EBApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initEBAppUtil();
    }

    private void initEBAppUtil() {
        EBUtil.INSTANCE.init(BuildConfig.APPLICATION_ID);
    }
}
