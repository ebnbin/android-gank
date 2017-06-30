package com.ebnbin.gank.feature.category

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.ebnbin.ebapplication.context.EBFragment

class CategoryItemFragment : EBFragment() {
    private val category: Category by lazy {
        arguments.getSerializable(ARG_CATEGORY) as Category
    }

    override fun overrideContentView(): View? {
        val result = Button(context)
        result.text = category.title
        return result
    }

    enum class Category(val title: String) {
        ALL("全部"),
        FULI("福利"),
        IOS("iOS"),
        ANDROID("Android"),
        QIANDUAN("前端"),
        XIATUIJIAN("瞎推荐"),
        TUOZHANZIYUAN("拓展资源"),
        APP("App"),
        XIUXISHIPIN("休息视频")
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: Category): CategoryItemFragment {
            val fragment = CategoryItemFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARG_CATEGORY, category)
            fragment.arguments = arguments
            return fragment
        }
    }
}
