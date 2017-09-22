package com.ebnbin.gank.base

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.ebnbin.eb.app.EBFragment

class EBBottomNavigationItem(title: String, resource: Int, val fragment: EBFragment)
    : AHBottomNavigationItem(title, resource)
