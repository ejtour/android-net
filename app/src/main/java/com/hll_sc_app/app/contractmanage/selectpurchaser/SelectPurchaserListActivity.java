package com.hll_sc_app.app.contractmanage.selectpurchaser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.add.ContractManageAddActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.contract.ContractGroupShopBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择合作采购商/门店
 */
@Route(path = RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD_SELECT_PURCHASER, extras = Constant.LOGIN_EXTRA)
public class SelectPurchaserListActivity extends BaseLoadActivity implements ISelectPurchaserContract.IView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlTitle;
    @Autowired(name = "bean")
    ContractGroupShopBean mBean;
    @Autowired(name = "isGroup")
    boolean mIsGroup;
    @Autowired(name = "isNew")
    boolean mIsNew = true;
    private ListAdapter mAdapter;
    private ISelectPurchaserContract.IPresent mPresenter;
    private PurchaserBean mSelectPurchaser;
    private ContextOptionsWindow mTitleOptionWindow;

    /**
     *
     * @param bean
     * @param isGroup
     * @param isNew
     */
    public static void start(ContractGroupShopBean bean, boolean isGroup, boolean isNew) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD_SELECT_PURCHASER)
                .withParcelable("bean", bean)
                .withBoolean("isGroup", isGroup)
                .withBoolean("isNew", isNew)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_select_purchaser);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = SelectPurchaserPresent.newInstance();
        mPresenter.register(this);
        mPresenter.queryList(true);
    }

    @Override
    public boolean isGroup() {
        return mIsGroup;
    }

    private void initView() {
        if (mIsGroup) {
            mTxtTitle.setText(mBean.isCooperation() ? "合作客户" : "意向客户");
        } else {
            mTxtTitle.setText("选择门店");
        }

        mTxtTitle.setOnClickListener(v -> {
            if (mTitleOptionWindow == null) {
                mTitleOptionWindow = new ContextOptionsWindow(this);
                List<OptionsBean> optionsBeans = new ArrayList<>();
                optionsBeans.add(new OptionsBean(OptionType.OPTION_SELECT_PURCHASER));
                optionsBeans.add(new OptionsBean(OptionType.OPTION_SELECT_CUSTOMER));
                int padh = UIUtils.dip2px(10);
                mTitleOptionWindow.setListPadding(padh, 0, padh, 0);
                mTitleOptionWindow
                        .refreshList(optionsBeans)
                        .setItemGravity(Gravity.CENTER)
                        .setListener((adapter, view1, position) -> {
                            mTitleOptionWindow.dismiss();
                            mBean.setPurchaserType(position+1);
                            OptionsBean item = (OptionsBean) adapter.getItem(position);
                            if (item == null) {
                                return;
                            }
                            mTxtTitle.setText(item.getLabel());
                            mPresenter.refresh();
                        });
            }
            mTitleOptionWindow.showAsDropDownFix(mRlTitle, Gravity.CENTER_HORIZONTAL);
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new ListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            NameValue bean = mAdapter.getItem(position);
            if (bean != null) {
                if (mIsGroup) {
                    mBean.setPurchaserID(bean.getValue());
                    mBean.setPurchaserName(bean.getName());
                    mBean.setShopID("");
                    mBean.setShopName("");
                } else {
                    mBean.setShopID(bean.getValue());
                    mBean.setShopName(bean.getName());
                }
                if (mIsGroup && mBean.isCooperation()) {//合作采购商且是集团 去门店
                    SelectPurchaserListActivity.start(mBean, false, mIsNew);
                } else {//意项集团或者合作门店
                    if (mIsNew) {
                        ContractManageAddActivity.start(mBean);
                    } else {//返回新建/编辑合同页面
                        ARouter.getInstance()
                                .build(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD)
                                .withParcelable("parcelable", mBean)
                                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .setProvider(new LoginInterceptor())
                                .navigation(this);
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        });
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectPurchaserListActivity.this,
                        searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.refresh();
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.quereMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(true);
    }


    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
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

    @Override
    public void querySuccess(List<NameValue> purchaseBeanList, boolean isMore) {
        if (isMore) {
            mAdapter.addData(purchaseBeanList);
        } else {
            if (CommonUtils.isEmpty(purchaseBeanList)) {
                mAdapter.setEmptyView(EmptyView.newBuilder(this)
                        .setTipsTitle(getEmptyTipstitle())
                        .create());
                mAdapter.setNewData(null);
            } else {
                mAdapter.setNewData(purchaseBeanList);
            }
        }
        if (!CommonUtils.isEmpty(purchaseBeanList)) {
            mRefreshLayout.setEnableLoadMore(purchaseBeanList.size() == 20);
        }
    }

    @Override
    public ContractGroupShopBean getContractBean() {
        return mBean;
    }

    @Override
    public String getSearchText() {
        return mSearchView.getSearchContent();
    }

    private class ListAdapter extends BaseQuickAdapter<NameValue, BaseViewHolder> {
        public ListAdapter(@Nullable List<NameValue> data) {
            super(R.layout.list_item_select_view, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.getView(R.id.img_arrow).setVisibility(mBean.isCooperation() && mIsGroup ? View.VISIBLE : View.GONE);
            helper.getView(R.id.img_ok).setVisibility((!mBean.isCooperation() || !mIsGroup) ? View.VISIBLE : View.GONE);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, NameValue item) {
            String groupId = mBean.getPurchaserID();
            String shopId = mBean.getShopID();
            helper.setText(R.id.txt_name, item.getName())
                    .setVisible(R.id.img_ok, (!mBean.isCooperation() && TextUtils.equals(groupId, item.getValue())) ||
                            ((!mIsGroup) && TextUtils.equals(shopId, item.getValue())))
                    .setVisible(R.id.img_arrow, mBean.isCooperation() && mIsGroup);

            if(mBean.isCooperation() && mIsGroup){//合作采购下的集团列表
                helper.setTextColor(R.id.txt_name, Color.parseColor(TextUtils.equals(groupId, item.getValue())?"#5695D2":"#666666"));
            }else {
                helper.setTextColor(R.id.txt_name, Color.parseColor("#666666"));
            }

        }
    }

    private String getEmptyTipstitle() {
        if (mBean.isCooperation()) {
            return TextUtils.isEmpty(getSearchText()) ? "您还没有合作客户噢" : "没有符合搜索条件的合作客户";
        } else {
            return TextUtils.isEmpty(getSearchText()) ? "您还没有意向客户噢" : "没有符合搜索条件的意向客户";
        }
    }
}
