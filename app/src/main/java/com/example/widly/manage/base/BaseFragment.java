package com.example.widly.manage.base;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.hwangjr.rxbus.RxBus;

import butterknife.ButterKnife;


public abstract class BaseFragment<T extends BasePresenter> extends LazyLoadFragment implements BaseView{

    protected T mPresenter;
    private View rootView;
    protected AppCompatActivity mActivity;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        RxBus.get().register(this);
        if (mPresenter != null) {
            mPresenter.onAttach(mActivity);
        }
    }

    @Override
    public void hideSoftKeyboard() {
        View view = mActivity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            ButterKnife.bind(this, rootView);

            initView(rootView);
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    /**
     * StateView的根布局，默认是整个界面，如果需要变换可以重写此方法
     */

    public View getStateViewRoot() {
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    /**
     * 初始化一些view
     *
     * @param rootView
     */
    public abstract void initView(View rootView);

    /**
     * 设置listener的操作
     */
    public abstract void initListener();


    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract @LayoutRes
    int provideContentViewId();

    //加载数据
    protected abstract void loadData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter = null;
        }
        rootView = null;
        RxBus.get().unregister(this);
    }


}
