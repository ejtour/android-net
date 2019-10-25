package com.hll_sc_app.app.report.customerLack.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.customerLack.CustomerLackItem;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackSummary;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author chukun
 * @since 2019/08/14
 */

@Route(path = RouterConfig.REPORT_CUSTOMER_LACK_DETAIL)
public class CustomerLackDetailActivity extends BaseLoadActivity implements CustomerLackDetailContract.ICustomerLackDetailView {
    private static final int COLUMN_NUM = 8;
    private static final int[] WIDTH_ARRAY = {150, 120, 80, 100, 80, 80, 90,100};

    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.txt_options)
    ImageView exportView;
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    private CustomerLackDetailContract.ICustomerLackDetailPresenter mPresenter;
    @Autowired(name = "parcelable")
    CustomerLackReq customerLackParams = new CustomerLackReq();
    private ContextOptionsWindow mOptionsWindow;

    private static String purchaserID;
    private static String shopID;
    private static String mStartDate;
    private static String mEndDate;


    public static void start(CustomerLackSummary item, String startDate, String endDate){
        CustomerLackReq params = new CustomerLackReq();
        params.setType(1);
        params.setShopID(item.getShopID());
        params.setPurchaserID(item.getPurchaserID());
        params.setStartDate(startDate);
        params.setEndDate(endDate);
        purchaserID = item.getPurchaserID();
        shopID = item.getShopID();
        mStartDate  = startDate;
        mEndDate = endDate;
        RouterUtil.goToActivity(RouterConfig.REPORT_CUSTOMER_LACK_DETAIL, params);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_customer_lack_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = CustomerLackDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mExcel.setHeaderView(generateHeader());
        mExcel.setColumnDataList(generateColumnData());
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

    @OnClick({R.id.img_back, R.id.txt_options, R.id.edt_search, R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_options:
                showOptionsWindow(exportView);
                break;
            case R.id.edt_search:
                SearchActivity.start(CustomerLackDetailActivity.this,
                        "", CommonSearch.class.getSimpleName());
                break;
            case R.id.img_clear:
                customerLackParams.setProductKeyword("");
                edtSearch.setText("");
                mPresenter.refresh();
                imgClear.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)) {
                edtSearch.setText(name);
                customerLackParams.setProductKeyword(name);
                imgClear.setVisibility(View.VISIBLE);
            } else {
                customerLackParams.setProductKeyword("");
            }
            mPresenter.refresh();
        }
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
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
        row.updateItemData(array);
        row.updateRowDate("商品名称", "规格/单位", "订货量", "订货金额", "发货量", "缺货量", "验货金额","缺货率");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    @Override
    public CustomerLackReq getRequestParams(){
        customerLackParams.setStartDate(mStartDate);
        customerLackParams.setEndDate(mEndDate);
        customerLackParams.setPurchaserID(purchaserID);
        customerLackParams.setShopID(shopID);
        customerLackParams.setType(2);
        return customerLackParams;
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
        Utils.bindEmail(this, this::export);
    }

    @Override
    public void export(String email) {
        Gson gson = new Gson();
        String json = gson.toJson(customerLackParams);
        mPresenter.export(json,email);
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[COLUMN_NUM];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[6] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[6]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[7] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[7]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }

    @Override
    public void setList(List<CustomerLackItem> beans, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(beans) && beans.size() == 20);
        mExcel.setData(beans, append);
    }
}
