package com.example.widly.manage.base;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;


public abstract class BasePresenter<V extends BaseView> implements LifecycleObserver {

    protected V mView;
    protected Context context;

    public BasePresenter(V view) {
        attachView(view);
    }

    private void attachView(V view) {
        mView = view;
    }

    public void onAttach(Context context) {
        this.context = context;
    }


}