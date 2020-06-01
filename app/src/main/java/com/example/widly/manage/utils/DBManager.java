package com.example.widly.manage.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.widly.manage.entity.ConsumotionBean;
import com.example.widly.manage.entity.ConsumotionBeanDao;
import com.example.widly.manage.entity.DaoMaster;
import com.example.widly.manage.entity.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DBManager {
    private final static String dbName = "db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    public void insertUser(ConsumotionBean consumotionBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ConsumotionBeanDao consumotionBeanDao = daoSession.getConsumotionBeanDao();
        consumotionBeanDao.insert(consumotionBean);
    }

    public List<ConsumotionBean> queryUserList(int year) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        ConsumotionBeanDao consumotionBeanDao = daoSession.getConsumotionBeanDao();
        QueryBuilder<ConsumotionBean> qb = consumotionBeanDao.queryBuilder();
        qb.where(ConsumotionBeanDao.Properties.Year.gt(year)).orderAsc(ConsumotionBeanDao.Properties.Month).orderAsc(ConsumotionBeanDao.Properties.Day);
        List<ConsumotionBean> list = qb.list();
        return list;
    }

}