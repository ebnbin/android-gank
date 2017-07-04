package com.ebnbin.gank.feature.category

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.ebnbin.gank.R

class EBLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.eb_load_more_view
    }

    override fun getLoadingViewId(): Int {
        return R.id.eb_load_more_loading_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.eb_load_more_load_end_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.eb_load_more_load_fail_view
    }
}
