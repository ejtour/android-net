package com.hll_sc_app.app.report.credit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.refund.search.RefundSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.credit.CreditBean;
import com.hll_sc_app.bean.report.search.SearchResultItem;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */
@Route(path = RouterConfig.REPORT_CREDIT)
public class CustomerCreditActivity extends BaseLoadActivity implements ICustomerCreditContract.ICustomerCreditView {
    @BindView(R.id.rcc_search_view)
    SearchView mSearchView;
    @BindView(R.id.rcc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rcc_arrow)
    TriangleView mArrow;
    @BindView(R.id.rcc_date)
    TextView mDate;
    @BindView(R.id.rcc_list_view)
    RecyclerView mListView;
    @BindView(R.id.rcc_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private ContextOptionsWindow mOptionsWindow;
    private ContextOptionsWindow mExportOptionsWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private ICustomerCreditContract.ICustomerCreditPresenter mPresenter;
    private CustomerCreditAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_customer_credit);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupIDs", UserConfig.getGroupID());
        String year = String.valueOf(CalendarUtils.getYear());
        mDate.setText(String.format("%s年", year));
        mReq.put("year", year);
        mPresenter = CustomerCreditPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mSearchView.setSearchTextLeft();
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                RefundSearchActivity.start(CustomerCreditActivity.this);
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
        mAdapter = new CustomerCreditAdapter();
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            SearchResultItem bean = data.getParcelableExtra("result");
            mReq.put(bean.getType() == 0 ? "purchaserID" : "shopID", bean.getShopMallId());
            mReq.put(bean.getType() == 0 ? "shopID" : "purchaserID", "");
            mSearchView.showSearchContent(!TextUtils.isEmpty(bean.getName()), bean.getName());
        }
    }

    @OnClick(R.id.rcc_filter_btn)
    public void selectYear(View view) {
        mArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            int year = CalendarUtils.getYear();
            for (int i = year - 3; i <= year + 1; i++) {
                list.add(new OptionsBean(R.drawable.ic_filter_option,String.format("%s年", i)));
            }
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(list)
                    .setListener((adapter, view1, position) -> {
                        OptionsBean bean = (OptionsBean) adapter.getItem(position);
                        if (bean == null) return;
                        mOptionsWindow.dismiss();
                        String label = bean.getLabel();
                        mDate.setText(label);
                        mReq.put("year", label.substring(0, label.length() - 1));
                        mPresenter.start();
                    });
            mOptionsWindow.setOnDismissListener(() -> {
                mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
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

    @Override
    public void setData(List<CreditBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list) && mAdapter.getEmptyViewCount() == 0) {
                mAdapter.setEmptyView(EmptyView.newBuilder(this)
                        .setImage(R.drawable.ic_char_empty)
                        .setTips("当前日期下没有统计数据噢")
                        .create());
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }
}
