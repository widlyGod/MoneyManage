package com.example.widly.manage.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.shenzhenpolytechnic.graduationdesign.R;
import com.example.widly.manage.activity.AddConsumptionActivity;
import com.example.widly.manage.adapter.MainAdapter;
import com.example.widly.manage.base.BaseFragment;
import com.example.widly.manage.entity.ConsumotionBean;
import com.example.widly.manage.presenter.MainPresenter;
import com.example.widly.manage.view.MainView;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseFragment<MainPresenter> implements MainView {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    List<ConsumotionBean> consumotionBeans = new ArrayList<>();
    MainAdapter mainAdapter;

    @Override
    public void initView(View rootView) {
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mainAdapter = new MainAdapter(consumotionBeans);
        recycle.setLayoutManager(new LinearLayoutManager(mActivity));
        recycle.setAdapter(mainAdapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mainAdapter);
        recycle.addItemDecoration(headersDecor);
    }

    @Override
    public void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, AddConsumptionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void loadData() {
        mPresenter.loadConsumotions();
    }


    @Override
    public void getConsumotions(List<ConsumotionBean> consumotionBeans) {
        this.consumotionBeans.clear();
        this.consumotionBeans.addAll(consumotionBeans);
        for (int i = 1; i <= consumotionBeans.size()-1; i++) {
            if (consumotionBeans.get(i).getMonth() == consumotionBeans.get(i - 1).getMonth()) {
                if (consumotionBeans.get(i).getDay() == consumotionBeans.get(i - 1).getDay()) {
                    consumotionBeans.get(i).setShowDay(false);
                } else {
                    consumotionBeans.get(i).setShowDay(true);
                }
            }
        }

        mainAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void consumotionBean(ConsumotionBean consumotionBean) {
        mPresenter.loadConsumotions();
    }
}
