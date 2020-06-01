package com.example.widly.manage.presenter;

import android.util.Log;

import com.example.widly.manage.base.BasePresenter;
import com.example.widly.manage.entity.ConsumotionBean;
import com.example.widly.manage.utils.DBManager;
import com.example.widly.manage.view.MainView;

import java.util.List;

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        super(view);
    }

    public void loadConsumotions(){
        List<ConsumotionBean> consumotionBeans = DBManager.getInstance(context).queryUserList(2019);
//        ConsumotionBean consumotionBean;
//        for (int i = 0; i < consumotionBeans.size() - 1; i++) {//冒泡趟数
//            for (int j = 0; j < consumotionBeans.size() - i - 1; j++) {
//                if (consumotionBeans.get(j + 1).getMonth() < consumotionBeans.get(j).getMonth()) {
//                    consumotionBean = consumotionBeans.get(j);
//                    consumotionBeans.set(j, consumotionBeans.get(j + 1));
//                    consumotionBeans.set(j + 1, consumotionBean);
//                }
//            }
//        }

        mView.getConsumotions(consumotionBeans);
        String a = "";
        for (ConsumotionBean bean : consumotionBeans) {
            a = a + bean.getMonth();
        }
        Log.d("////", "loadData: " + a);
    }
}
