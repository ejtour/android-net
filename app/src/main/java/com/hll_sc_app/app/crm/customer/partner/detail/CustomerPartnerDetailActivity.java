package com.hll_sc_app.app.crm.customer.partner.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.app.crm.customer.seas.CustomerSeasAdapter;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.app.submit.BackType;
import com.hll_sc_app.app.submit.IBackType;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

@Route(path = RouterConfig.CRM_CUSTOMER_PARTNER_DETAIL)
public class CustomerPartnerDetailActivity extends BaseLoadActivity implements ICustomerPartnerDetailContract.ICustomerPartnerDetailView, IBackType {
    @BindView(R.id.acc_search_view)
    SearchView mSearchView;
    @BindView(R.id.acc_image)
    GlideImageView mImage;
    @BindView(R.id.acc_name)
    TextView mName;
    @BindView(R.id.acc_contact)
    TextView mContact;
    @BindView(R.id.acc_way)
    TextView mWay;
    @BindView(R.id.acc_title)
    TextView mTitle;
    @BindView(R.id.acc_list_view)
    RecyclerView mListView;
    @BindView(R.id.acc_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mPurchaserID;
    @Autowired(name = "object1")
    boolean mIsAll;
    private ICustomerPartnerDetailContract.ICustomerPartnerDetailPresenter mPresenter;
    private CustomerSeasAdapter mAdapter;
    private EmptyView mEmptyView;

    public static void start(String purchaserID, boolean isAll) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_PARTNER_DETAIL, purchaserID, isAll);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_customer_partner_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
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

    private void initData() {
        mPresenter = CustomerPartnerDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setHint("搜索门店");
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CustomerPartnerDetailActivity.this, searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
        mAdapter = new CustomerSeasAdapter();
        mAdapter.setType(mIsAll ? 2 : 1);
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
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

    @OnClick(R.id.acc_info_group)
    public void onViewClicked() {
        RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, mPurchaserID);
    }

    @Override
    public void setData(CooperationShopListResp resp, boolean append) {
        CooperationPurchaserDetail groupInfo = resp.getGroupInfo();
        mImage.setImageURL(groupInfo.getLogoUrl());
        mName.setText(groupInfo.getName());
        mWay.setText(String.format("默认结算方式：%s", CooperationDetailActivity.getSettlementWay(groupInfo.getDefaultSettlementWay())));
        mContact.setText(String.format("联系人：%s / %s", groupInfo.getLinkman(), groupInfo.getMobile()));
        String source = String.format("本集团共计 %s 个门店，其中 %s 个新店", resp.getShopTotal(), resp.getNewTotal());

        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                source.indexOf("计") + 1, source.indexOf("个"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)),
                source.indexOf("中") + 1, source.lastIndexOf("个"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTitle.setText(ss);

        List<PurchaserShopBean> list = resp.getShopList();
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有数据");
            }
            mAdapter.setNewData(list, true);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getPurchaserID() {
        return mPurchaserID;
    }

    @Override
    public boolean isAll() {
        return mIsAll;
    }

    @Override
    public BackType getBackType() {
        return BackType.PARTNER_DETAIL_LIST;
    }
}
