package com.ebnbin.gank.feature.category

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ebnbin.gank.feature.data.Category

internal class CategoryPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val data: List<Category> by lazy {
        val result = ArrayList<Category>()
        result.add(Category.ALL)
        result.add(Category.FULI)
        result.add(Category.IOS)
        result.add(Category.ANDROID)
        result.add(Category.QIANDUAN)
        result.add(Category.XIATUIJIAN)
        result.add(Category.TUOZHANZIYUAN)
        result.add(Category.APP)
        result.add(Category.XIUXISHIPIN)
        result
    }

    override fun getItem(position: Int): Fragment {
        return CategoryPageFragment.newInstance(data[position])
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return data[position].title
    }
}
