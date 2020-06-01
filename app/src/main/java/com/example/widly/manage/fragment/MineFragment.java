package com.example.widly.manage.fragment;

import android.view.View;

import com.example.shenzhenpolytechnic.graduationdesign.R;
import com.example.widly.manage.base.BaseFragment;
import com.example.widly.manage.presenter.MainPresenter;

public class MineFragment extends BaseFragment<MainPresenter> {
    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void loadData() {

    }
}
