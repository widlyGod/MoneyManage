package com.example.widly.manage.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;


@Entity
public class ConsumotionBean {

    @Id
    private Long id;

    private int type;
    private int price;
    private int year;
    private int month;
    private int day;
    @Transient
    private boolean showDay = true;

    @Generated(hash = 742817764)
    public ConsumotionBean(Long id, int type, int price, int year, int month,
            int day) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public ConsumotionBean(int type, int price, int year, int month, int day) {
        this.type = type;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Generated(hash = 1893423236)
    public ConsumotionBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isShowDay() {
        return showDay;
    }

    public void setShowDay(boolean showDay) {
        this.showDay = showDay;
    }

    public boolean getShowDay() {
        return this.showDay;
    }
}
