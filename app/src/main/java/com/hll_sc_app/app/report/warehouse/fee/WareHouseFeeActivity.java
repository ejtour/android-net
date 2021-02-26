package com.hll_sc_app.app.report.warehouse.fee;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.PurchaserSelectWindow;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.warehouse.WareHouseFeeBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REPORT_WAREHOUSE_FEE)
public class WareHouseFeeActivity extends BaseLoadActivity implements IWareHouseFeeContract.IWareHouseFeeView {
    private static final int[] WIDTH_ARRAY = {40, 170, 160, 120, 120, 120, 120};
    @BindView(R.id.trl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.trl_tab_one)
    TextView mShipper;
    @BindView(R.id.trl_tab_one_arrow)
    TriangleView mShipperArrow;
    @BindView(R.id.trl_tab_two)
    TextView mDate;
    @BindView(R.id.trl_tab_two_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.tte_excel)
    ExcelLayout mExcel;
    private IWareHouseFeeContract.IWareHouseFeePresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private PurchaserSelectWindow mPurchaserWindow;
    private AtomicInteger mIndex = new AtomicInteger();
    private List<PurchaserBean> mShipperList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_two_excel);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        Date date = new Date();
        mDate.setTag(R.id.date_start, CalendarUtils.getFirstDateInMonth(date));
        mDate.setTag(R.id.date_end, date);
        updateSelectedDate();
        mPresenter = WareHouseFeePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
    }

    private void initView() {
        mTitleBar.setHeaderTitle("代仓服务费统计");
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mShipper.setText("货主");
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setHeaderView(generateHeader());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(this, tip);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void cacheShipperList(List<PurchaserBean> purchaserBeans) {
        if (!CommonUtils.isEmpty(purchaserBeans)) {
            PurchaserBean bean = new PurchaserBean();
            bean.setPurchaserName("全部");
            purchaserBeans.add(0, bean);
        }
        mShipperList = purchaserBeans;
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        mPresenter.export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("序号", "货主集团", "合作时长", "收费模式", "应收服务费", "已收服务费", "未收服务费");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]));
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]));
        for (int i = 4; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @OnClick({R.id.trl_tab_two_btn})
    public void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectedDate();
                mPresenter.loadList();
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }


    @Override
    public void setData(List<WareHouseFeeBean> list, boolean append) {
        if (!append) {
            mIndex.set(0);
        }
        if (!CommonUtils.isEmpty(list))
            for (WareHouseFeeBean bean : list) {
                bean.setSequenceNo(mIndex.incrementAndGet());
            }
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(list) && list.size() == 20);
        mExcel.setData(list, append);
    }

    @OnClick(R.id.trl_tab_one_btn)
    public void showPurchaserWindow(View view) {
        if (mShipperList == null) {
            mPresenter.getShipperList();
            return;
        }
        mShipperArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mShipper.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new PurchaserSelectWindow(this, mShipperList);
            mPurchaserWindow.setListener(bean -> {
                if (TextUtils.equals(bean.getPurchaserName(), "全部")) {
                    mShipper.setText("货主");
                } else {
                    mShipper.setText(bean.getPurchaserName());
                }
                mReq.put("shipperID", bean.getPurchaserID());
                mPresenter.loadList();
            });
            mPurchaserWindow.setOnDismissListener(() -> {
                mShipperArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mShipper.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mPurchaserWindow.showAsDropDownFix(view);
    }
}
