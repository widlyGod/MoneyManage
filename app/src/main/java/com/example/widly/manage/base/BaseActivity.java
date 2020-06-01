package com.example.widly.manage.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected T mPresenter;

    protected Bundle savedInstanceState;
    private Unbinder BKbind;

    public LinearLayout topBarLl;
    protected long preClickTime = 0;
    public static final long INTERVAL = 1000L;

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
        setContentView(provideContentViewId());
        BKbind = ButterKnife.bind(this);
        this.savedInstanceState = savedInstanceState;
        if (mPresenter != null) {
            mPresenter.onAttach(this);
        }
        initView();
        initListener();
        RxBus.get().register(this);
    }


    public void onViewClick(View view) {
    }

    protected boolean isDoubleClick() {
        if (System.currentTimeMillis() - preClickTime < INTERVAL) {
            Logger.e(System.currentTimeMillis() + "////");
            return true;
        }
        Logger.e("noDouble click" + preClickTime);
        preClickTime = System.currentTimeMillis();
        return false;
    }

    public abstract void initView();

    public abstract void initListener();

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract @LayoutRes
    int provideContentViewId();

    /**
     * onDestroy 时自动解绑
     * 未测试
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BKbind != null) {
            BKbind.unbind();
        }
        RxBus.get().unregister(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void hideSoftKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

    /**
     * 跳转Activity
     *
     * @param cls
     */
    protected void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    /**
     * 跳转Activity
     *
     * @param cls
     */
    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转Activity
     *
     * @param cls
     */
    protected void openActivity(Class<?> cls, int flag) {
        startActivityForResult(new Intent(this, cls), flag);
    }

    /**
     * 跳转Activity
     *
     * @param cls
     */
    protected void openActivity(Class<?> cls, int flag, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, flag);
    }

}
