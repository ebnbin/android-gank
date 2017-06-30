package com.ebnbin.gank.feature.category

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

internal class CategoryPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val data: List<CategoryItemFragment.Category> by lazy {
        val result = ArrayList<CategoryItemFragment.Category>()
        result.add(CategoryItemFragment.Category.ALL)
        result.add(CategoryItemFragment.Category.FULI)
        result.add(CategoryItemFragment.Category.IOS)
        result.add(CategoryItemFragment.Category.ANDROID)
        result.add(CategoryItemFragment.Category.QIANDUAN)
        result.add(CategoryItemFragment.Category.XIATUIJIAN)
        result.add(CategoryItemFragment.Category.TUOZHANZIYUAN)
        result.add(CategoryItemFragment.Category.APP)
        result.add(CategoryItemFragment.Category.XIUXISHIPIN)
        result
    }

    override fun getItem(position: Int): Fragment {
        return CategoryItemFragment.newInstance(data[position])
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return data[position].title
    }
}
