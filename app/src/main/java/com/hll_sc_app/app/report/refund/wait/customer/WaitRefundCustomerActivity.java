package com.hll_sc_app.app.report.refund.wait.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.refund.RefundCustomerBean;
import com.hll_sc_app.bean.report.refund.RefundCustomerResp;
import com.hll_sc_app.bean.report.search.SearchResultItem;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ExcelFooter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 待退客户统计
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_WAIT_REFUND_CUSTOMER)
public class WaitRefundCustomerActivity extends BaseLoadActivity implements IWaitRefundCustomerContract.IWaitRefundCustomerView {
    private static final int WAIT_REFUND_CUSTOMER_CODE = 11001;
    private static final int[] WIDTH_ARRAY = {150, 120, 80, 80, 60, 60, 60, 60, 60};
    @BindView(R.id.wrc_excel)
    ExcelLayout mExcel;
    @BindView(R.id.wrc_title_bar)
    TitleBar mTitleBar;
    private IWaitRefundCustomerContract.IWaitRefundCustomerPresenter mPresenter;
    @BindView(R.id.wrc_search_view)
    SearchView mSearchView;
    private ContextOptionsWindow mExportOptionsWindow;
    private ExcelFooter mFooter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_wait_refund_customer);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        mPresenter = WaitRefundCustomerPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        initExcel();
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_SEARCH,
                        WaitRefundCustomerActivity.this, WAIT_REFUND_CUSTOMER_CODE);
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mReq.put("purchaserID", "");
                    mReq.put("shopID", "");
                }
                mPresenter.start();
            }
        });
    }

    private void initExcel() {
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] dataArray = generateColumnData();
        mExcel.setColumnDataList(dataArray);
        mFooter.updateItemData(dataArray);
        mExcel.setHeaderView(View.inflate(this, R.layout.view_report_wait_refund_customer_header, null));
        mExcel.setFooterView(mFooter);
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
    public void setData(RefundCustomerResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(resp.convertToRowData().toArray(array));
        List<RefundCustomerBean> records = resp.getGroupVoList();
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WAIT_REFUND_CUSTOMER_CODE && resultCode == RESULT_OK) {
            SearchResultItem bean = data.getParcelableExtra("result");
            mReq.put(bean.getType() == 0 ? "purchaserID" : "shopID", bean.getShopMallId());
            mSearchView.showSearchContent(!TextUtils.isEmpty(bean.getName()), bean.getName());
        }
    }
}
