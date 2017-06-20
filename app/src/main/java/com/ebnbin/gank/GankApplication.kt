package com.ebnbin.gank

import com.ebnbin.ebapplication.context.EBApplication

class GankApplication : EBApplication() {
    override fun applicationId() = BuildConfig.APPLICATION_ID
}
