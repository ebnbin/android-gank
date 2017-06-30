package com.ebnbin.gank.feature.data

enum class Category(val title: String, val url: String = title) {
    ALL("全部", "all"),
    FULI(DataModel.FULI),
    IOS(DataModel.IOS),
    ANDROID(DataModel.ANDROID),
    QIANDUAN(DataModel.QIANDUAN),
    XIATUIJIAN(DataModel.XIATUIJIAN),
    TUOZHANZIYUAN(DataModel.TUOZHANZIYUAN),
    APP(DataModel.APP),
    XIUXISHIPIN(DataModel.XIUXISHIPIN)
}
