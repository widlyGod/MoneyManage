package com.example.widly.manage.presenter;

import com.example.widly.manage.base.BasePresenter;
import com.example.widly.manage.fragment.MainFragment;
import com.example.widly.manage.fragment.MineFragment;
import com.example.widly.manage.fragment.ProjectFragment;
import com.example.widly.manage.utils.PageManager;
import com.example.widly.manage.view.HomeView;

public class HomePresenter extends BasePresenter<HomeView> {

    public HomePresenter(HomeView view) {
        super(view);
    }

    public void changeFragmentchangeFragment(PageManager pageManager, int index) {
        switch (index) {
            case 0:
                pageManager.switchFragment(ProjectFragment.class, null, PageManager.REPLACE_WITH_BUNDLE);
                break;
            case 1:
                pageManager.switchFragment(MainFragment.class, null, PageManager.REPLACE_WITH_BUNDLE);
                break;
            default:
                pageManager.switchFragment(MineFragment.class, null, PageManager.REPLACE_WITH_BUNDLE);
                break;
        }
        mView.nowFragment(index);
    }

}
