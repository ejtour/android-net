package com.hll_sc_app.app.contractmanage.selectpurchaser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.PurchaserBean;
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
 * 选择合作采购商
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
    @Autowired(name = "id")
    String mId;
    private PurchaserListAdapter mAdapter;
    private ISelectPurchaserContract.IPresent mPresenter;
    private PurchaserBean mSelectPurchaser;
    private ContextOptionsWindow mTitleOptionWindow;

    public static void start(Activity activity, int requestCode, String id) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD_SELECT_PURCHASER)
                .withString("id", id)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_select_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = SelectPurchaserPresent.newInstance();
        mPresenter.register(this);
        mPresenter.queryList(true);
    }

    private void initView() {
        mTxtTitle.setOnClickListener(v -> {
            if (mTitleOptionWindow == null) {
                mTitleOptionWindow = new ContextOptionsWindow(this);
                List<OptionsBean> optionsBeans = new ArrayList<>();
                optionsBeans.add(new OptionsBean(OptionType.OPTION_SELECT_PURCHASER));
                optionsBeans.add(new OptionsBean(OptionType.OPTION_SELECT_CUSTOMER));
                int padh = UIUtils.dip2px(10);
                mTitleOptionWindow.setListPadding(padh,0,padh,0);
                mTitleOptionWindow
                        .refreshList(optionsBeans)
                        .setItemGravity(Gravity.CENTER)
                        .setListener((adapter, view1, position) -> {
                            mTitleOptionWindow.dismiss();
                            mTxtTitle.setTag(position + 1);
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
        mAdapter = new PurchaserListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserBean bean = mAdapter.getItem(position);
            if (bean != null) {
                bean.setPurchaserType(getListType());
                mId = bean.getPurchaserID();
                mAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("purchaser", bean);
                setResult(RESULT_OK, intent);
                finish();
            }
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
    public void querySuccess(List<PurchaserBean> purchaseBeanList, boolean isMore) {
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
    public int getListType() {
        Object o = mTxtTitle.getTag();
        if (o == null) {
            return 1;
        }
        return Integer.parseInt(o.toString());
    }



    @Override
    public String getSearchText() {
        return mSearchView.getSearchContent();
    }

    private class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {
        public PurchaserListAdapter(@Nullable List<PurchaserBean> data) {
            super(R.layout.list_item_select_view, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_name, item.getPurchaserName())
                    .setVisible(R.id.img_ok, TextUtils.equals(mId, item.getPurchaserID()));
        }
    }

    private String getEmptyTipstitle() {
        if (getListType() == 0) {
            return TextUtils.isEmpty(getSearchText()) ? "您还没有合作采购商噢" : "没有符合搜索条件的合作采购商";
        } else {
            return TextUtils.isEmpty(getSearchText()) ? "您还没有意向客户噢" : "没有符合搜索条件的意向客户";
        }
    }
}
