package com.hll_sc_app.app.crm.customer.seas.detail;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.seas.detail.analysis.CustomerSeasAnalysisFragment;
import com.hll_sc_app.app.crm.customer.seas.detail.order.CustomerSeasOrderFragment;
import com.hll_sc_app.app.crm.customer.seas.detail.shop.CustomerSeasShopFragment;
import com.hll_sc_app.app.crm.customer.seas.detail.visit.CustomerSeasVisitFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

@Route(path = RouterConfig.CRM_CUSTOMER_SEAS_DETAIL)
public class CustomerSeasDetailActivity extends BaseLoadActivity {
    private static final int REQ_CODE = 0x103;
    @BindView(R.id.csd_name)
    TextView mName;
    @BindView(R.id.csd_today)
    TextView mToday;
    @BindView(R.id.csd_week)
    TextView mWeek;
    @BindView(R.id.csd_month)
    TextView mMonth;
    @BindView(R.id.csd_no_settled)
    TextView mNoSettled;
    @BindView(R.id.csd_return_num)
    TextView mReturnNum;
    @BindView(R.id.csd_week_ratio)
    TextView mWeekRatio;
    @BindView(R.id.csd_week_progress)
    ProgressBar mWeekProgress;
    @BindView(R.id.csd_week_progress_label)
    TextView mWeekProgressLabel;
    @BindView(R.id.csd_month_ratio)
    TextView mMonthRatio;
    @BindView(R.id.csd_month_progress)
    ProgressBar mMonthProgress;
    @BindView(R.id.csd_month_progress_label)
    TextView mMonthProgressLabel;
    @BindView(R.id.csd_order_time)
    TextView mOrderTime;
    @BindView(R.id.csd_visit_time)
    TextView mVisitTime;
    @BindView(R.id.csd_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindViews({R.id.csd_allot, R.id.csd_receive})
    List<View> mButtons;
    @BindView(R.id.csd_view_pager)
    ViewPager mViewPager;
    @Autowired(name = "parcelable")
    PurchaserShopBean mBean;
    @Autowired(name = "object")
    boolean mOnlyShop;
    private List<Fragment> mFragments;
    private NumberFormat mPercentInstance;

    {
        mPercentInstance = NumberFormat.getPercentInstance();
        mPercentInstance.setMaximumFractionDigits(2);
        mPercentInstance.setMinimumFractionDigits(2);
    }

    public static void start(Activity context, PurchaserShopBean bean, boolean onlyShop) {
        ARouter.getInstance().build(RouterConfig.CRM_CUSTOMER_SEAS_DETAIL)
                .withParcelable("parcelable", bean)
                .withBoolean("object", onlyShop)
                .setProvider(new LoginInterceptor())
                .navigation(context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_customer_seas_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        updateData();
        initView();
    }

    private void updateData() {
        mName.setText(mBean.getShopName());
        String source = String.format("%s/%s单  |  单均%s元", CommonUtils.formatMoney(mBean.getTodayBillAmount()), mBean.getTodayBillNum(), CommonUtils.formatMoney(mBean.getCurrentAvgAmount()));
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(new RelativeSizeSpan(0.73f), source.indexOf("/"), source.indexOf("单") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.73f), source.lastIndexOf("单"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mToday.setText(spannableString);
        source = String.format("%s/%s单  |  日均%s笔/单均%s元", CommonUtils.formatMoney(mBean.getSevenBillAmount()), mBean.getSevenBillNum(),
                CommonUtils.formatNum(mBean.getSevenAvgBillNum()), CommonUtils.formatMoney(mBean.getSevenDayAvgAmount()));
        spannableString = new SpannableString(source);
        spannableString.setSpan(new RelativeSizeSpan(0.73f), source.indexOf("/"), source.indexOf("单") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.73f), source.lastIndexOf("日"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mWeek.setText(spannableString);
        source = String.format("%s/%s单  |  日均%s笔/单均%s元", CommonUtils.formatMoney(mBean.getThirtyBillAmount()), mBean.getThirtyBillNum(),
                CommonUtils.formatNum(mBean.getThirtyDayAvgBillNum()), CommonUtils.formatMoney(mBean.getThirtyDayAvgAmount()));
        spannableString = new SpannableString(source);
        spannableString.setSpan(new RelativeSizeSpan(0.73f), source.indexOf("/"), source.indexOf("单") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.73f), source.lastIndexOf("日"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mMonth.setText(spannableString);
        source = String.format("%s/%s单", CommonUtils.formatMoney(mBean.getUnsettledBillAmount()), mBean.getUnsettledBillNum());
        spannableString = new SpannableString(source);
        spannableString.setSpan(new RelativeSizeSpan(0.73f), source.indexOf("/"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mNoSettled.setText(spannableString);
        mReturnNum.setText(String.format("%s笔", mBean.getReturnBillNum()));
        mWeekProgressLabel.setText(String.format("%s/%s", mBean.getCurrentWeekBillNum(), mBean.getPreWeekBillNum()));
        mMonthRatio.setText(mPercentInstance.format(getRate(mBean.getCurrentWeekBillNum(), mBean.getPreWeekBillNum())));
        if (mBean.getPreWeekBillNum() == 0) {
            mWeekProgress.setMax(1);
            mWeekProgress.setProgress(1);
        } else {
            mWeekProgress.setMax(mBean.getPreWeekBillNum());
            mWeekProgress.setProgress(mBean.getCurrentWeekBillNum());
        }
        mMonthProgressLabel.setText(String.format("%s/%s", mBean.getCurrentMonthBillNum(), mBean.getPreMonthBillNum()));
        mMonthRatio.setText(mPercentInstance.format(getRate(mBean.getCurrentMonthBillNum(), mBean.getPreMonthBillNum())));
        if (mBean.getPreMonthBillNum() == 0) {
            mMonthProgress.setMax(1);
            mMonthProgress.setProgress(1);
        } else {
            mMonthProgress.setMax(mBean.getPreMonthBillNum());
            mMonthProgress.setProgress(mBean.getCurrentMonthBillNum());
        }
        mOrderTime.setText(DateUtil.getReadableTime(mBean.getLastBillTime(), CalendarUtils.FORMAT_DATETIME));
        mVisitTime.setText(DateUtil.getReadableTime(mBean.getLastVisitTime(), CalendarUtils.FORMAT_DATE_TIME));
    }

    protected double getRate(double current, double last) {
        return last == 0 ? 1 : (current - last) / last;
    }

    private void initView() {
        String[] titles;
        if (mOnlyShop) {
            mFragments = Collections.singletonList(CustomerSeasShopFragment.newInstance(mBean));
            titles = new String[]{"门店信息"};
            mTabLayout.setIndicatorColor(Color.TRANSPARENT);
        } else {
            ButterKnife.apply(mButtons, (view, index) -> view.setVisibility(View.VISIBLE));
            mFragments = Arrays.asList(CustomerSeasShopFragment.newInstance(mBean), CustomerSeasOrderFragment.newInstance(mBean.getShopID()),
                    CustomerSeasVisitFragment.newInstance(mBean.getShopID()), CustomerSeasAnalysisFragment.newInstance());
            titles = new String[]{"门店信息", "订单记录", "拜访记录", "商户分析"};
        }
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setViewPager(mViewPager, titles);
    }

    @OnClick({R.id.csd_allot, R.id.csd_receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.csd_allot:
                break;
            case R.id.csd_receive:
                break;
        }
    }
}
