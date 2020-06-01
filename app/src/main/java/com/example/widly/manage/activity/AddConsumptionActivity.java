package com.example.widly.manage.activity;

import android.app.DatePickerDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.shenzhenpolytechnic.graduationdesign.R;
import com.example.widly.manage.adapter.ConsumptionTagAdapter;
import com.example.widly.manage.base.BaseActivity;
import com.example.widly.manage.base.BasePresenter;
import com.example.widly.manage.entity.ConsumotionBean;
import com.example.widly.manage.entity.ConsumptionTagVo;
import com.example.widly.manage.utils.DBManager;
import com.hwangjr.rxbus.RxBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class AddConsumptionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_tag_list)
    RecyclerView rvTagList;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    ConsumptionTagAdapter consumptionTagAdapter;
    List<ConsumptionTagVo> consumptionTagVos;
    int mYear, mMonth, mDay;
    int type = -1;
    int price;

    @Override
    public void initView() {
        consumptionTagVos = new ArrayList<>();
        consumptionTagVos.add(new ConsumptionTagVo("饮食"));
        consumptionTagVos.add(new ConsumptionTagVo("服饰"));
        consumptionTagVos.add(new ConsumptionTagVo("护肤"));
        consumptionTagVos.add(new ConsumptionTagVo("缴费"));
        consumptionTagVos.add(new ConsumptionTagVo("日用"));
        consumptionTagVos.add(new ConsumptionTagVo("交通"));
        consumptionTagVos.add(new ConsumptionTagVo("通讯"));
        consumptionTagVos.add(new ConsumptionTagVo("娱乐"));
        consumptionTagVos.add(new ConsumptionTagVo("运动"));
        consumptionTagVos.add(new ConsumptionTagVo("其他"));
        consumptionTagAdapter = new ConsumptionTagAdapter(R.layout.item_consumption_tag, consumptionTagVos);
        rvTagList.setLayoutManager(new GridLayoutManager(this, 6));
        rvTagList.setAdapter(consumptionTagAdapter);
        consumptionTagAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (ConsumptionTagVo consumptionTagVo : consumptionTagVos) {
                    consumptionTagVo.setSelect(false);
                }
                consumptionTagVos.get(position).setSelect(true);
                adapter.notifyDataSetChanged();
                type = position;
            }
        });
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH)+1;
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        final String data = "日期 : " + mYear + "年" + mMonth  + "月" + mDay + "日 ";
        tvDate.setText(data);
    }

    @Override
    public void initListener() {
        ivBack.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_consumption;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        (view1, year, month, dayOfMonth) -> {
                            mYear = year;
                            mMonth = month + 1;
                            mDay = dayOfMonth;
                            final String data = "日期 : " + year + "年-" + (month + 1) + "月-" + dayOfMonth + "日 ";
                            tvDate.setText(data);
                        },
                        mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.tv_commit:
                if (type == -1) {
                    Toast.makeText(this, "请选择消费类型", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (etPrice.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "请输入消费金额", Toast.LENGTH_SHORT).show();
                    break;
                }
                ConsumotionBean consumotionBean = new ConsumotionBean(type, Integer.parseInt(etPrice.getText().toString().trim()), mYear, mMonth, mDay);
                DBManager.getInstance(this).insertUser(consumotionBean);
                RxBus.get().post(consumotionBean);
                finish();
                break;
        }
    }

}
