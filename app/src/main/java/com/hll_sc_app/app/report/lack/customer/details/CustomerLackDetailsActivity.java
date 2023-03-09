package com.hll_sc_app.app.report.lack.customer.details;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.lack.CustomerLackDetailsBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author chukun
 * @since 2019/08/14
 */

@Route(path = RouterConfig.REPORT_CUSTOMER_LACK_DETAIL)
public class CustomerLackDetailsActivity extends BaseLoadActivity implements ICustomerLackDetailsContract.ICustomerLackDetailsView {
    private static final int[] WIDTH_ARRAY = {150, 120, 80, 100, 80, 80, 90, 100};
    @BindView(R.id.cld_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.cld_search_view)
    SearchView mSearchView;
    @BindView(R.id.cld_excel)
    ExcelLayout mExcel;
    @Autowired(name = "object0")
    String mPurchaserID;
    @Autowired(name = "object1")
    String mShopID;
    @Autowired(name = "object2")
    String mStartDate;
    @Autowired(name = "object3")
    String mEndDate;
    private ICustomerLackDetailsContract.ICustomerLackDetailPresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    public static void start(String purchaserID, String shopID, String startDate, String endDate) {
        RouterUtil.goToActivity(RouterConfig.REPORT_CUSTOMER_LACK_DETAIL, purchaserID, shopID, startDate, endDate);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_customer_lack_details);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("type", "2");
        mReq.put("startDate", mStartDate);
        mReq.put("endDate", mEndDate);
        mReq.put("purchaserID", mPurchaserID);
        mReq.put("shopID", mShopID);
        mReq.put("groupID", UserConfig.getGroupID());
        mPresenter = CustomerLackDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
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

        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CustomerLackDetailsActivity.this,
                        searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mReq.put("productKeyword", searchContent);
                mPresenter.start();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        mPresenter.export(null);
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("商品名称", "规格/单位", "订货量", "订货金额", "发货量", "缺货量", "验货金额", "缺货率");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
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
    public void exportReportID(String reportID, IExportView export) {
        Utils.exportReportID(this, reportID, export);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        for (int i = 2; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void setList(List<CustomerLackDetailsBean> beans, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(beans) && beans.size() == 20);
        mExcel.setData(beans, append);
    }
}
