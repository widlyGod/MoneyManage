package com.example.widly.manage.utils;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by hy on 15/9/17.
 * <p/>
 * problem: 1 快速切换回出现context为空的问题,解决方式有两种
 * a:使用getActivity().getApplicationContext(), 但是这种方式会导致startActivity outside的问题,可酌情使用
 * b:在onCreateView() 时给context设置个引用
 */
public class PageManager {
    private static final String TAG = "PageManager";
    private FragmentActivity mActivity;
    private Fragment mCurShowFragment;
    private int mFrameLayoutId;
    private FragmentManager mFragmentManager;
    /**
     * 定义操作类型
     */
    public static final int REPLACE = 0;
    public static final int ADD = 1;
    public static final int ADD_WITH_BUNDLE = 2;
    public static final int REPLACE_WITH_BUNDLE = 3;
    /**
     * 缓存显示过的Fragment
     */
    private Map<String, Fragment> mFragmentCache = new HashMap<>();
    /**
     * 记录打开的Fragment历史,用于返回键
     */
    private LinkedList<String> mFragmentHistory = new LinkedList<>();


    public PageManager(FragmentActivity Activity, int FrameLayoutId) {
        this.mActivity = Activity;
        this.mFrameLayoutId = FrameLayoutId;
        this.mFragmentManager = Activity.getSupportFragmentManager();
    }


    /**
     * retrieve a Fragment object
     *
     * @param targetClazz
     * @return if targetFragment was showed, return null; or return Fragment object
     */
    private Fragment getFragment(Class<? extends Fragment> targetClazz) {
        /** 判断当前切换的Fragment是否与当前Fragment相同 */
        String simpleName = targetClazz.getSimpleName();
        if (mCurShowFragment != null && mCurShowFragment.getClass().getSimpleName().equals(simpleName)) {
            Log.d(TAG, "targetFragmetn is same as CurrentFragmetn: " + simpleName);
            return null;
        }
        Log.i(TAG, "getFragment(Class<? extends Fragment> targetClazz): " + simpleName);
        Fragment targetFragment = null;
        /** 判断Fragment是否打开过; 打开过则直接复用 */
//        if (mFragmentCache.containsKey(simpleName)) {
//            /** 复用Fragment */
//            targetFragment = mFragmentCache.get(simpleName);
//        } else {
        /** 没有打开过该Fragment, 则创建该Fragment */
        try {
            Constructor<? extends Fragment> constructor = targetClazz.getConstructor();
            targetFragment = constructor.newInstance();
            // 将新创建的UI实例，存入缓存器中
            mFragmentCache.put(simpleName, targetFragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("constructor new mInstance error");
        }
//        }

        return targetFragment;
    }

    private void addFragmentToStack(@NonNull Fragment targetFragment, int type) {
        if (null == targetFragment)
            return;
        Log.d(TAG, "addFragmentToStack: " + targetFragment.getClass().getSimpleName());
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        /**
         * 如果当前栈里面包含当前想要切换的Fragment记录, 则删除旧的记录;
         *
         * 解决栈顶出现重复记录, 造成Fragment切换失败的问题;
         */
        for(int index = 0; index < mFragmentHistory.size(); ++index) {
            String name = mFragmentHistory.get(index);
            if (name.equals(targetFragment.getClass().getSimpleName())) {
                mFragmentHistory.remove(index);
                break;
            }
        }

        switch (type) {
            case ADD:
                mFragmentHistory.addFirst(targetFragment.getClass().getSimpleName());
                break;

            case REPLACE:
                if (mFragmentHistory.size() > 0)
                    mFragmentHistory.removeFirst();
                mFragmentHistory.addFirst(targetFragment.getClass().getSimpleName());
                break;
        }
        /**
         * 解决Can not perform this action after onSaveInstanceState问题;
         * onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后再给它添加Fragment就会出错。
         *
         * 解决办法:commit() 方法替换成 commitAllowingStateLoss()
         */
//        transaction.replace(mFrameLayoutId, targetFragment).commit();

//        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_left_out);
        transaction.replace(mFrameLayoutId, targetFragment).commitAllowingStateLoss();
        mCurShowFragment = targetFragment;

        Log.d(TAG, "addFragmentToStack ======================================");
        for (String str : mFragmentHistory) {
            Log.d(TAG, "Stack: " + str);
        }
    }


    public void switchFragment(@NonNull Class<? extends Fragment> targetClazz, Bundle bundle, int type) {
        Fragment targetFragment = getFragment(targetClazz);
        if (targetFragment != null) {
            //clearQueue Fragment parameter
            targetFragment.setArguments(null);
            Log.d(TAG, "switch " + targetFragment.getClass().getSimpleName());
            switch (type) {
                case REPLACE:
                    addFragmentToStack(targetFragment, REPLACE);
                    break;
                case REPLACE_WITH_BUNDLE:
                    targetFragment.setArguments(bundle);
                    addFragmentToStack(targetFragment, REPLACE);
                    break;

                case ADD:
                    addFragmentToStack(targetFragment, ADD);
                    break;

                case ADD_WITH_BUNDLE:
                    /** 添加参数 */
                    targetFragment.setArguments(bundle);
                    addFragmentToStack(targetFragment, ADD);
                    break;
            }
        }
    }


    public void clearStack() {
        Iterator iterator = mFragmentCache.entrySet().iterator();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            transaction.remove((Fragment) entry.getValue());
            //停止接收Bus事件
//            if (entry.getValue() instanceof ButKnifeFragment) {
//                ((ButKnifeFragment) entry.getValue()).unregisterRxBus();
//            }
        }
        transaction.commitAllowingStateLoss();

        mFragmentHistory.clear();
        mFragmentCache.clear();
        mCurShowFragment = null;
    }


    public boolean goBack(Bundle bundle) {
        if (mFragmentHistory.size() > 1) {
            mFragmentHistory.removeFirst();
            String simpleName = mFragmentHistory.getFirst();
            Log.d(TAG, "goBack target: " + simpleName);
            Fragment targetFragment = mFragmentCache.get(simpleName);

            /** 切换 */
            if (targetFragment != null) {
                /**
                 * 返回数据
                 * 如果没有返回数据,则需要设置为null,否则会返回之前设定的数据
                 */
                if (bundle != null)
                    targetFragment.setArguments(bundle);
                else
                    targetFragment.setArguments(null);

                FragmentTransaction transaction = mFragmentManager
                        .beginTransaction();
//                transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_left_out);
                transaction.replace(mFrameLayoutId, targetFragment).commit();
                mCurShowFragment = targetFragment;

                Log.d(TAG, "========================================");
                for (String str : mFragmentHistory) {
                    Log.d(TAG, "Stack: " + str);
                }
                return true;
            } else {
                Log.e(TAG, "go back_icon targetFragment is failed, because it is null");
            }
        }
        return false;
    }

    /**
     * 返回当前正在显示的Fragment
     *
     * @return Fragment
     */
    public Fragment getShowFragment() {
        return mCurShowFragment;
    }

    /**
     * 返回缓存里的Fragment对象
     *
     * @param fragment
     * @return
     */
    public Fragment getCachedFragment(@NonNull String fragment) {
        return mFragmentCache.get(fragment);
    }

    public Map<String, Fragment> getFragmentCache() {
        return mFragmentCache;
    }
}