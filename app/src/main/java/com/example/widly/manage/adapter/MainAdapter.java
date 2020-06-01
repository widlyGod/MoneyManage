package com.example.widly.manage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shenzhenpolytechnic.graduationdesign.R;
import com.example.widly.manage.entity.ConsumotionBean;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    List<ConsumotionBean> consumotionBeans;

    public MainAdapter(List<ConsumotionBean> consumotionBeans) {
        this.consumotionBeans = consumotionBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consumotion_detail, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(consumotionBeans.get(position));
    }

    @Override
    public long getHeaderId(int position) {
        int headId = 0;
        if (position == 0 || consumotionBeans.size() == 0) {
            return 0;
        }
        for (int i = 1; i <= position; i++) {
            if (consumotionBeans.get(i).getMonth() == consumotionBeans.get(i - 1).getMonth()) {

            } else {
                headId++;
            }
        }
        return headId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_year_head, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        Log.e("///", "onBindHeaderViewHolder: " + position);
        textView.setText(consumotionBeans.get(position).getMonth() + "月");
    }

    @Override
    public int getItemCount() {
        return consumotionBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_type)
        TextView tvType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(ConsumotionBean consumotionBean) {
            if(consumotionBean.isShowDay()) {
                tvDay.setVisibility(View.VISIBLE);
                tvDay.setText(consumotionBean.getDay() + "日");
            }else {
                tvDay.setVisibility(View.GONE);
            }
            tvPrice.setText(consumotionBean.getPrice() + "元");

            String[] strArray = {"饮食", "服饰", "护肤", "缴费", "日用", "交通", "通讯", "娱乐", "运动", "其他"};
            tvType.setText(strArray[consumotionBean.getType()]);

        }
    }
}
