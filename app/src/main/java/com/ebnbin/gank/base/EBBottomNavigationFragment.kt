package com.ebnbin.gank.base

import android.os.Bundle
import android.view.View
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.ebnbin.eb.app.EBFragment
import com.ebnbin.eb.util.EBUtil
import com.ebnbin.gank.R

abstract class EBBottomNavigationFragment : EBFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFragmentHelper()
    }

    private fun initFragmentHelper() {
        fragmentHelper.defaultGroup = fragmentContainerId
    }

    override val contentView = R.layout.eb_fragment_bottom_navigation

    val bottomNavigation: AHBottomNavigation by lazy {
        findViewById<AHBottomNavigation>(R.id.eb_bottom_navigation)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation.defaultBackgroundColor = EBUtil.getColorAttr(context, R.attr.ebColorCard)
        bottomNavigation.accentColor = EBUtil.getColorAttr(context, R.attr.colorAccent)
        bottomNavigation.inactiveColor = EBUtil.getColorAttr(context, R.attr.ebColorIconDisabled)

        bottomNavigation.setOnTabSelectedListener { position, _ ->
            val item = bottomNavigation.getItem(position)
            if (item is EBBottomNavigationItem) {
                fragmentHelper.show(item.fragment, item.getTitle(context))
            }
            true
        }
    }

    companion object {
        val fragmentContainerId = R.id.eb_fragment_container
    }
}
