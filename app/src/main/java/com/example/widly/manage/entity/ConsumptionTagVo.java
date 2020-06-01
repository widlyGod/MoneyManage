package com.example.widly.manage.entity;

import java.io.Serializable;

public class ConsumptionTagVo implements Serializable {

    public ConsumptionTagVo(String tag) {
        this.tag = tag;
    }

    private String tag;
    private boolean isSelect = false;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
