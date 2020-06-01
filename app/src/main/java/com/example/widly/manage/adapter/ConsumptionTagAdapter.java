package com.example.widly.manage.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shenzhenpolytechnic.graduationdesign.R;
import com.example.widly.manage.entity.ConsumptionTagVo;

import java.util.List;

public class ConsumptionTagAdapter extends BaseQuickAdapter<ConsumptionTagVo, BaseViewHolder> {

    public ConsumptionTagAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsumptionTagVo item) {
        helper.setText(R.id.cb_tag, item.getTag());
        if (item.isSelect()) {
            helper.getView(R.id.cb_tag).setSelected(true);
        } else {
            helper.getView(R.id.cb_tag).setSelected(false);
        }

        helper.addOnClickListener(R.id.cb_tag);
    }

}
