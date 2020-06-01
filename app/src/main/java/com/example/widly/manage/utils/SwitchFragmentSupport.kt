package com.example.widly.manage.utils

import android.support.v4.app.Fragment

/**
 * Created by jmw on 2018/11/15
 */
interface SwitchFragmentSupport {

    var lastSwitchShowFragment: Fragment?
    var parentSwitchFragmentSupport: SwitchFragmentSupport?
}