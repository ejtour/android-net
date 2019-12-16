package com.hll_sc_app.app.report.refund.wait.product;

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
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ProductNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.refund.RefundProductBean;
import com.hll_sc_app.bean.report.refund.RefundProductResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
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
 * 待退统计
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.REPORT_WAIT_REFUND_PRODUCT_DETAIL)
public class WaitRefundProductActivity extends BaseLoadActivity implements WaitRefundProductContract.IWaitRefundProductView {
    private static final int[] WIDTH_ARRAY = {120, 140, 100, 80, 80, 80};
    @BindView(R.id.wrc_excel)
    ExcelLayout mExcel;
    @BindView(R.id.wrc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.wrc_search_view)
    SearchView mSearchView;
    private WaitRefundProductContract.IWaitRefundProductPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private ContextOptionsWindow mExportOptionsWindow;
    private ExcelFooter mFooter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_wait_refund_customer);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        mPresenter = WaitRefundProductPresenter.newInstance();
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
                SearchActivity.start(WaitRefundProductActivity.this,
                        "", ProductNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mReq.put("productName", searchContent);
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
        mExcel.setHeaderView(generateHeader());
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
    public void setData(RefundProductResp resp, boolean append) {
        CharSequence[] array = {};
        mFooter.updateRowDate(resp.convertToRowData().toArray(array));
        List<RefundProductBean> records = resp.getRecords();
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(records) && records.size() == 20);
        mExcel.setData(records, append);
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

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("商品编码", "商品名称", "商品规格", "单位", "数量", "退货金额");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < 3; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]));
        for (int i = 4; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)) {
                mSearchView.showSearchContent(true, name);
            }
        }
    }
}
