package com.hll_sc_app.app.report.refund.wait.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.refund.WaitRefundCustomerResp;
import com.hll_sc_app.bean.report.refund.WaitRefundReq;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import com.hll_sc_app.bean.report.search.SearchResultItem;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待退客户统计
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_WAIT_REFUND_CUSTOMER_DETAIL)
public class WaitRefundCustomertDetailActivity extends BaseLoadActivity implements WaitRefundCustomerDetailContract.IWaitRefundCustomerDetailView {
    private static final int WAIT_REFUND_CUSTOMERT_CODE = 11001;
    private static final int COLUMN_NUM = 9;
    private static final int[] WIDTH_ARRAY = {150,120,80, 80, 60, 60,60,60,60};
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rog_title_bar)
    TitleBar mTitleBar;
    private WaitRefundCustomerDetailContract.IWaitRefundCustomerDetailPresenter mPresenter;
    WaitRefundReq mParam = new WaitRefundReq();
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    private ContextOptionsWindow mExportOptionsWindow;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_wait_refund_customer_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mPresenter = WaitRefundCustomerDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMoreRefundCustomerDetail();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryRefundCustomerDetail(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.rog_title_bar,R.id.img_clear,R.id.edt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
                mParam.setProductName("");
                mParam.setPurchaserID("");
                mParam.setShopID("");
                edtSearch.setText("");
                mPresenter.queryRefundCustomerDetail(true);
                imgClear.setVisibility(View.GONE);
                break;
            case R.id.rog_title_bar:
               showExportOptionsWindow(mTitleBar);
                break;
            case R.id.edt_search:
                RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_SEARCH, this, WAIT_REFUND_CUSTOMERT_CODE);
            default:
                break;
        }
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
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        Utils.bindEmail(this, (email) -> mPresenter.exportRefundCustomerDetail(email, reqParams));
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String reqParams = gson.toJson(getRequestParams());
        mPresenter.exportRefundCustomerDetail(email, reqParams);
    }


    @Override
    public WaitRefundReq getRequestParams(){
        return mParam;
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private View generateHeader(boolean isDisPlay) {
        View view = View.inflate(this,R.layout.item_report_refund_customer_detail_header,null);
        if(isDisPlay) {
            mExcel.setHeaderView(view);
        }
        return view;
    }

    private View generatorFooter(WaitRefundCustomerResp refundCustomerResp, boolean isDisplay){
        ExcelRow row = new ExcelRow(this);
        if(isDisplay) {
            row.updateChildView(COLUMN_NUM);
            ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
            array[0] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[0]));
            array[1] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[1]));
            array[2] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[2]));
            array[3] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[3]));
            array[4] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[4]));
            array[5] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[5]));
            array[6] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[6]));
            array[7] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[7]));
            array[8] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[8]));
            row.updateItemData(array);
            row.updateRowDate("合计", "----", refundCustomerResp.getTotalRefundBillNum()+"", refundCustomerResp.getTotalRefundProductNum()+"",
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalCashAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalBankCardAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalOnLineAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalAccountAmount())),
                    CommonUtils.formatMoney(Double.parseDouble(refundCustomerResp.getTotalRefundAmount())));
            row.setBackgroundResource(R.drawable.bg_excel_header);
        }
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[8] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[8]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }


    @Override
    public void showRefundCustomerDetail(WaitRefundCustomerResp refundCustomerResp, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(refundCustomerResp.getGroupVoList()) && refundCustomerResp.getGroupVoList().size() == 20);
        if (!CommonUtils.isEmpty(refundCustomerResp.getGroupVoList())) {
            mExcel.setData(refundCustomerResp.getGroupVoList(), append);
            mExcel.setHeaderView(generateHeader(true));
            mExcel.setFooterView(generatorFooter(refundCustomerResp, true));
        } else {
            mExcel.setData(new ArrayList<>(), append);
            generateHeader(append);
            mExcel.setFooterView(generatorFooter(refundCustomerResp, append));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WAIT_REFUND_CUSTOMERT_CODE && resultCode == RESULT_OK) {
            SearchResultItem bean = data.getParcelableExtra("result");
            edtSearch.setText(bean.getName());
            imgClear.setVisibility(View.VISIBLE);
            WaitRefundReq requestParams = getRequestParams();
            if(bean.getType()==0) {
                requestParams.setPurchaserID(bean.getShopMallId());
            }else{
                requestParams.setShopID(bean.getShopMallId());
            }
            mPresenter.queryRefundCustomerDetail(true);
        }
    }

}
