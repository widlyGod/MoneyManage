package com.example.widly.manage.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import com.example.shenzhenpolytechnic.graduationdesign.R;
import com.example.widly.manage.base.BaseFragment;
import com.example.widly.manage.entity.ConsumotionBean;
import com.example.widly.manage.presenter.MainPresenter;
import com.example.widly.manage.view.MainView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.hwangjr.rxbus.annotation.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseFragment<MainPresenter> implements MainView {

    @BindView(R.id.chart1)
    PieChart chart;

    @Override
    public void initView(View rootView) {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(Typeface.createFromAsset(mActivity.getAssets(), "OpenSans-Light.ttf"));
        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
//        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTypeface(Typeface.createFromAsset(mActivity.getAssets(), "OpenSans-Regular.ttf"));
        chart.setEntryLabelTextSize(12f);
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("统计");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        return s;
    }

    private void setData( List<ConsumotionBean> consumotionBeans) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        int typePrice0 = 0;
        int typePrice1 = 0;
        int typePrice2 = 0;
        int typePrice3 = 0;
        int typePrice4 = 0;
        int typePrice5 = 0;
        int typePrice6 = 0;
        int typePrice7 = 0;
        int typePrice8 = 0;
        int typePrice9 = 0;
        for (ConsumotionBean consumotionBean : consumotionBeans) {
            switch (consumotionBean.getType()) {
                case 0:
                    typePrice0 = +consumotionBean.getPrice();
                    break;
                case 1:
                    typePrice1 = +consumotionBean.getPrice();
                    break;
                case 2:
                    typePrice2 = +consumotionBean.getPrice();
                    break;
                case 3:
                    typePrice3 = +consumotionBean.getPrice();
                    break;
                case 4:
                    typePrice4 = +consumotionBean.getPrice();
                    break;
                case 5:
                    typePrice5 = +consumotionBean.getPrice();
                    break;
                case 6:
                    typePrice6 = +consumotionBean.getPrice();
                    break;
                case 7:
                    typePrice7 = +consumotionBean.getPrice();
                    break;
                case 8:
                    typePrice8 = +consumotionBean.getPrice();
                    break;
                case 9:
                    typePrice9 = +consumotionBean.getPrice();
                    break;
            }
        }
        List<Integer> allPrice = new ArrayList<>();
        allPrice.add(typePrice0);
        allPrice.add(typePrice1);
        allPrice.add(typePrice2);
        allPrice.add(typePrice3);
        allPrice.add(typePrice4);
        allPrice.add(typePrice5);
        allPrice.add(typePrice6);
        allPrice.add(typePrice7);
        allPrice.add(typePrice8);
        allPrice.add(typePrice9);
        String[] strArray = {"饮食", "服饰", "护肤", "缴费", "日用", "交通", "通讯", "娱乐", "运动", "其他"};
        for (int i = 0; i < allPrice.size(); i++) {
            if (allPrice.get(i) > 0) {
                entries.add(new PieEntry((float) allPrice.get(i),
                        strArray[i]));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.createFromAsset(mActivity.getAssets(), "OpenSans-Light.ttf"));
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

    @Override
    public void initListener() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void loadData() {
        mPresenter.loadConsumotions();
    }

    @Override
    public void getConsumotions(List<ConsumotionBean> consumotionBeans) {
        setData(consumotionBeans);
    }

    @Subscribe
    public void consumotionBean(ConsumotionBean consumotionBean) {
        mPresenter.loadConsumotions();
    }

}
