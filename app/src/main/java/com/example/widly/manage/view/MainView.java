package com.example.widly.manage.view;

import com.example.widly.manage.base.BaseView;
import com.example.widly.manage.entity.ConsumotionBean;

import java.util.List;

public interface MainView extends BaseView {

    void getConsumotions(List<ConsumotionBean> consumotionBeans);
}
