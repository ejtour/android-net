package com.hll_sc_app.app.purchasetemplate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.PurchaseTemplateBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.OrgSelectWindow;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/4
 */

@Route(path = RouterConfig.PURCHASE_TEMPLATE)
public class PurchaseTemplateActivity extends BaseLoadActivity implements IPurchaseTemplateContract.IPurchaseTemplateView {
    @BindView(R.id.trl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.trl_tab_one)
    TextView mGroup;
    @BindView(R.id.trl_tab_one_arrow)
    TriangleView mGroupArrow;
    @BindView(R.id.trl_tab_two)
    TextView mShop;
    @BindView(R.id.trl_tab_two_arrow)
    TriangleView mShopArrow;
    @BindView(R.id.trl_list_view)
    RecyclerView mListView;
    @BindView(R.id.trl_refresh_view)
    SmartRefreshLayout mRefreshView;
    private ContextOptionsWindow mOptionsWindow;
    private PurchaseTemplateAdapter mAdapter;
    private OrgSelectWindow mPurchaserWindow;
    private OrgSelectWindow mShopWindow;
    private View mTipView;
    private EmptyView mEmptyView;
    private IPurchaseTemplateContract.IPurchaseTemplatePresenter mPresenter;
    private String mPurchaserID;
    private String mShopID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_tab_two_refresh_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = PurchaseTemplatePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mEmptyView.setOnActionClickListener(() -> {
            mAdapter.setEmptyView(mTipView);
            mPresenter.start();
        });
    }

    private void initView() {
        mTitleBar.setHeaderTitle("客户采购模板");
        mTitleBar.setRightBtnClick(this::showWindow);
        mGroup.setText("采购商集团");
        mShop.setText("采购商门店");
        mListView.setPadding(0, UIUtils.dip2px(5), 0, 0);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(95), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new PurchaseTemplateAdapter();
        mTipView = View.inflate(this, R.layout.view_purchase_template_tip, null);
        mAdapter.setEmptyView(mTipView);
        mListView.setAdapter(mAdapter);
        mEmptyView = EmptyView.newBuilder(this)
                .create();
        mEmptyView.setNetError();
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        initWindow();
    }

    private void initWindow() {
        mPurchaserWindow = new OrgSelectWindow(this)
                .setClickListener((adapter, view, position) -> {
                    mPurchaserWindow.dismiss();
                    NameValue item = (NameValue) adapter.getItem(position);
                    mPurchaserWindow.select(item);
                    if (item != null && !item.getValue().equals(mPurchaserID)) {
                        mPurchaserID = item.getValue();
                        mGroup.setText(item.getName());
                        mShopID = null;
                        mShop.setText("采购商门店");
                        mAdapter.setNewData(null);
                        mPresenter.searchShop();
                    }
                })
                .setSearchListener(result -> mPresenter.searchPurchaser());
        mPurchaserWindow.setOnDismissListener(() -> {
            mGroupArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mGroup.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
        mShopWindow = new OrgSelectWindow(this)
                .setClickListener((adapter, view, position) -> {
                    mShopWindow.dismiss();
                    NameValue item = (NameValue) adapter.getItem(position);
                    mShopWindow.select(item);
                    if (item != null) {
                        mShopID = item.getValue();
                        mShop.setText(item.getName());
                        mPresenter.queryList();
                    }
                })
                .setSearchType("门店")
                .setSearchListener(result -> mPresenter.searchShop());
        mShopWindow.setOnDismissListener(() -> {
            mShopArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mShop.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
    }

    private void showWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_PURCHASE_TEMPLATE)))
                    .setListener((adapter, view1, position) -> {
                        mOptionsWindow.dismiss();
                        if (CommonUtils.isEmpty(mAdapter.getData()))
                            showToast("当前没有可导出的合作客户");
                        else mPresenter.export("");
                    });
        }
        mOptionsWindow.showAsDropDownFix(mTitleBar, Gravity.END);
    }

    @OnClick({R.id.trl_tab_one_btn, R.id.trl_tab_two_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.trl_tab_one_btn:
                showPurchaserWindow(view);
                break;
            case R.id.trl_tab_two_btn:
                showShopWindow(view);
                break;
        }
    }

    private void showShopWindow(View view) {
        mShopArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mShop.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mShopWindow.showAsDropDownFix(view);
    }

    private void showPurchaserWindow(View view) {
        mGroupArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mGroup.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mPurchaserWindow.showAsDropDownFix(view);
    }

    @Override
    public void setOrgData(int type, List<NameValue> list) {
        if (type == 0) mPurchaserWindow.setListData(list);
        else mShopWindow.setListData(list);
    }

    @Override
    public void setData(List<PurchaseTemplateBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list))
                if (mAdapter.getEmptyView() != mTipView) mAdapter.setEmptyView(mTipView);
            mAdapter.setNewData(list);
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            if (mAdapter.getEmptyView() != mEmptyView) mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public String getPurchaserID() {
        return mPurchaserID;
    }

    @Override
    public String getShopID() {
        return mShopID;
    }

    @Override
    public String getPurchaserWords() {
        return mPurchaserWindow.getSearchWords();
    }

    @Override
    public String getShopWords() {
        return mShopWindow.getSearchWords();
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
