package com.example.widly.manage.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shenzhenpolytechnic.graduationdesign.R;
import com.example.widly.manage.base.BaseActivity;
import com.example.widly.manage.presenter.HomePresenter;
import com.example.widly.manage.utils.PageManager;
import com.example.widly.manage.view.HomeView;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<HomePresenter> implements View.OnClickListener, HomeView {

    @BindView(R.id.fr_change)
    FrameLayout frChange;
    @BindView(R.id.iv_project)
    ImageView ivProject;
    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.ll_main_tab)
    LinearLayout llMainTab;

    private ArrayList<Fragment> mFragments;
    private PageManager mPageManager;

    @Override
    public void initView() {
        mPageManager = new PageManager(this, R.id.fr_change);
        mFragments = new ArrayList<>();
        mPresenter.changeFragmentchangeFragment(mPageManager, 1);
    }

    @Override
    public void initListener() {
        ivProject.setOnClickListener(this);
        ivMain.setOnClickListener(this);
        ivMine.setOnClickListener(this);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_project:
                mPresenter.changeFragmentchangeFragment(mPageManager, 0);
                break;
            case R.id.iv_main:
                mPresenter.changeFragmentchangeFragment(mPageManager, 1);
                break;
            case R.id.iv_mine:
                mPresenter.changeFragmentchangeFragment(mPageManager, 2);
                break;
        }
    }

    private void selectItem(int index) {
        switch (index) {
            case 0:
                ivProject.setImageDrawable(getResources().getDrawable(R.mipmap.ic_project_select, null));
                ivMain.setImageDrawable(getResources().getDrawable(R.mipmap.ic_main, null));
                ivMine.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mine, null));
                break;
            case 1:
                ivProject.setImageDrawable(getResources().getDrawable(R.mipmap.ic_project, null));
                ivMain.setImageDrawable(getResources().getDrawable(R.mipmap.ic_main_select, null));
                ivMine.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mine, null));
                break;
            default:
                ivProject.setImageDrawable(getResources().getDrawable(R.mipmap.ic_project, null));
                ivMain.setImageDrawable(getResources().getDrawable(R.mipmap.ic_main, null));
                ivMine.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mine_select, null));
                break;
        }
    }

    @Override
    public void nowFragment(int index) {
        selectItem(index);
    }
}
