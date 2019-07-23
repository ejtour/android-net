package com.hll_sc_app.app.aftersales.audit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.detail.AfterSalesDetailActivity;
import com.hll_sc_app.app.aftersales.goodsoperation.GoodsOperationActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.PurchaserListResp;
import com.hll_sc_app.bean.event.AfterSalesEvent;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.aftersales.PurchaserShopSelectWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

@Route(path = RouterConfig.AFTER_SALES_AUDIT)
public class AuditActivity extends BaseLoadActivity implements IAuditActivityContract.IAuditActivityView {

    @BindView(R.id.asa_header)
    TitleBar mHeader;
    @BindView(R.id.asa_tab)
    SlidingTabLayout mTab;
    /**
     * 门店
     */
    @BindView(R.id.asa_purchase)
    TextView mShop;
    /**
     * 申请日期
     */
    @BindView(R.id.asa_date)
    TextView mDate;
    /**
     * 退货类型
     */
    @BindView(R.id.asa_type)
    TextView mType;
    @BindView(R.id.asa_pager)
    ViewPager mPager;
    @BindView(R.id.asa_purchase_arrow)
    TriangleView mShopArrow;
    @BindView(R.id.asa_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.asa_type_arrow)
    TriangleView mTypeArrow;
    /**
     * 采购商选择弹窗
     */
    private PurchaserShopSelectWindow mSelectionWindow;
    /**
     * 日期选择弹窗
     */
    private DateRangeWindow mDateRangeWindow;
    private final AuditParam mParam = new AuditParam();
    private ContextOptionsWindow mOptionsWindow;
    private SingleSelectionWindow<NameValue> mTypeWindow;
    private IAuditActivityContract.IAuditActivityPresenter mPresenter;
    private PurchaserListResp mPurchaserListResp;

    public AuditParam getAuditParam() {
        return mParam;
    }

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_AUDIT);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_after_sales_audit);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = AuditActivityPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AfterSalesDetailActivity.REQ_CODE && data != null) {
                AfterSalesBean parcelable = data.getParcelableExtra("parcelable");
                refreshCurrentData(parcelable);
            } else if (requestCode == GoodsOperationActivity.REQ_CODE) {
                EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.RELOAD_ITEM));
            }
        }
    }

    private void initView() {
        String[] titles = {"全部", "客服审核", "司机提货", "仓库收货", "财务审核"};
        CheckPagerAdapter adapter = new CheckPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mTab.setViewPager(mPager, titles);
        mHeader.setRightBtnClick(this::showOptionsWindow);
    }

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAILS)))
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        exportOrder();
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private void exportOrder() {
        EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.EXPORT_ORDER));
    }

    void refreshCurrentData(AfterSalesBean bean) {
        EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.UPDATE_ITEM, bean));
    }

    @Override
    public void cachePurchaserShopList(PurchaserListResp resp) {
        mPurchaserListResp = resp;
    }

    class CheckPagerAdapter extends FragmentPagerAdapter {

        CheckPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return AuditFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @OnClick(R.id.asa_purchase_btn)
    public void selectShop(View view) {
        if (mPurchaserListResp == null) {
            mPresenter.start();
            return;
        }
        mShopArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mShop.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        showPurchaserWindow(view);
    }

    @OnClick(R.id.asa_type_btn)
    public void selectType(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        showTypeWindow(view);
    }

    /**
     * 初始化采购商选择弹窗
     */
    private void showPurchaserWindow(View view) {
        if (mPurchaserListResp == null) return;
        if (mSelectionWindow == null) {
            mSelectionWindow = PurchaserShopSelectWindow.create(this, (purchaserID, shopID, list) -> {
                mSelectionWindow.dismiss();
                mParam.setPurchaserID(purchaserID);
                mParam.setPurchaserShopID(shopID);
                EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.REFRESH_LIST));
            }).setLeftList(mPurchaserListResp.getList()).setRightList(null);
            mSelectionWindow.setOnDismissListener(() -> {
                mShopArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mShop.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mSelectionWindow.showAsDropDownFix(view);
    }

    private void showTypeWindow(View view) {
        if (mTypeWindow == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("全部", "0"));
            list.add(new NameValue("自由退款", "1"));
            list.add(new NameValue("快速退款", "2"));
            mTypeWindow = new SingleSelectionWindow<>(this, NameValue::getName);
            mTypeWindow.refreshList(list);
            mTypeWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mTypeWindow.setSelectListener(nameValue -> {
                mParam.setSourceType(Integer.valueOf(nameValue.getValue()));
                mType.setText(nameValue.getName());
                EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.REFRESH_LIST));
            });
        }
        mTypeWindow.showAsDropDownFix(view);
    }

    @OnClick(R.id.asa_date_btn)
    public void selectDate(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        initDateWindow();
        mDateRangeWindow.showAsDropDownFix(view);
    }

    /**
     * 初始化日期弹窗
     */
    private void initDateWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                String beginTemp = mParam.getStartTime();
                String endTemp = mParam.getEndTime();
                if (start == null && end == null) {
                    mParam.setStartTime(null);
                    mParam.setEndTime(null);
                    mDate.setText("申请退换货日期");
                    if (beginTemp != null && endTemp != null) {
                        EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.REFRESH_LIST));
                    }
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    mParam.setStartTime(CalendarUtils.toLocalDate(calendarStart.getTime()));
                    mParam.setEndTime(CalendarUtils.toLocalDate(calendarEnd.getTime()));
                    mDate.setText(String.format("%s~%s", startStr, endStr));
                    if ((beginTemp == null && endTemp == null) ||
                            !mParam.getStartTime().equals(beginTemp) ||
                            !mParam.getEndTime().equals(endTemp)) {
                        EventBus.getDefault().post(new AfterSalesEvent(AfterSalesEvent.REFRESH_LIST));
                    }
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
    }
}
