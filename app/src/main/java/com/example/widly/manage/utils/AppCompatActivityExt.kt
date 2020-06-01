package com.example.widly.manage.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/**
 * 切换fragment，如果isRemove参数传false，则fragment参数必须传本地保存的变量，不能每次new，否则可能内存泄漏
 * @param isRemove 当在其他fragment切换到该 [fragment] 时是否调用remove，否则hide
 */
fun AppCompatActivity.switchFragment(fragment: Fragment, containerViewId: Int, isRemove: Boolean = false, tag: String? = null) {
    if (this !is SwitchFragmentSupport || fragment !is SwitchFragmentSupport) {
        throw IllegalStateException("must implement SwitchFragmentSupport")
    }

    val current = lastSwitchShowFragment
    if (current === fragment)
        return

    fragment.parentSwitchFragmentSupport = this
    lastSwitchShowFragment = fragment
    val arguments = fragment.arguments ?: Bundle()
    arguments.putBoolean("switchFragment.isRemove", isRemove)
    fragment.arguments = arguments
    fragmentTransaction {
        current?.run {
            if (current.arguments?.getBoolean("switchFragment.isRemove") == true)
                remove(this)
            else
                hide(this)
        }
        if (!fragment.isAdded) {
            tag?.run {
                add(containerViewId, fragment, tag)
            } ?: add(containerViewId, fragment)

        }
        show(fragment)
    }
}

/**
 * 切换fragment，如果isRemove参数传false，则fragment参数必须传本地保存的变量，不能每次new，否则可能内存泄漏
 * @param isRemove 当在其他fragment切换到该 [fragment] 时是否调用remove，否则hide
 */
fun Fragment.switchFragment(fragment: Fragment, containerViewId: Int, isRemove: Boolean = false, tag: String? = null) {
    if (this !is SwitchFragmentSupport || fragment !is SwitchFragmentSupport) {
        throw IllegalStateException("must implement SwitchFragmentSupport")
    }

    val current = lastSwitchShowFragment
    if (current === fragment)
        return

    fragment.parentSwitchFragmentSupport = this
    lastSwitchShowFragment = fragment
    val arguments = fragment.arguments ?: Bundle()
    arguments.putBoolean("switchFragment.isRemove", isRemove)
    fragment.arguments = arguments
    fragmentTransaction {
        current?.run {
            if (current.arguments?.getBoolean("switchFragment.isRemove") == true)
                remove(this)
            else
                hide(this)
        }
        if (!fragment.isAdded) {
            tag?.run {
                add(containerViewId, fragment, tag)
            } ?: add(containerViewId, fragment)

        }
        show(fragment)
    }
}

inline fun AppCompatActivity.fragmentTransaction(call: FragmentTransaction.() -> Unit) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.call()
    transaction.commitAllowingStateLoss()
}

inline fun Fragment.fragmentTransaction(call: FragmentTransaction.() -> Unit) {
    val transaction = childFragmentManager.beginTransaction()
    transaction.call()
    transaction.commitAllowingStateLoss()
}