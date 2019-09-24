package com.hll_sc_app.app.complainmanage.purchaserlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ComplainSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;
import com.hll_sc_app.bean.event.ComplainManageEvent;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.adapter.SelectItemAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 投诉详情-选择采购商集团/选择门店
 */
@Route(path = RouterConfig.ACTIVITY_SELECT_PURCHASER_LIST, extras = Constant.LOGIN_EXTRA)
public class SelectPurchaserListActivity extends BaseLoadActivity implements ISelectPurchaserListContract.IView {

    @Autowired(name = "purchaserId")
    String mPurchaserId;
    @Autowired(name = "selectedId")
    String mSelectedId;
    @Autowired(name = "type")
    int mType;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mProductListView;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private Unbinder unbinder;
    private ISelectPurchaserListContract.IPresent mPresent;
    private SelectItemAdapter<ReportFormSearchResp.ShopMallBean> mAdpter;


    public static void start(@TYPE int type, Activity activity, int requestCode, String purchaserId,String selectedId) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_SELECT_PURCHASER_LIST)
                .withString("purchaserId", purchaserId)
                .withString("selectedId", selectedId)
                .withInt("type", type)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_purchaser_list);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        mPresent = SelectPurchaserListPresent.newInstance();
        mPresent.register(this);
        mPresent.queryList(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mType == TYPE.GROUP ? "选择采购商集团" : "选门店");
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, ComplainSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.queryList(false);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
        mAdpter = new SelectItemAdapter<>(null, new SelectItemAdapter.Config<ReportFormSearchResp.ShopMallBean>() {
            @Override
            public String getName(ReportFormSearchResp.ShopMallBean shopMallBean) {
                return shopMallBean.getName();
            }

            @Override
            public boolean isSelected(ReportFormSearchResp.ShopMallBean shopMallBean) {
                return TextUtils.equals(mSelectedId, shopMallBean.getShopmallID());
            }
        });
        mAdpter.setOnItemClickListener((adapter, view, position) -> {
            ReportFormSearchResp.ShopMallBean shopMallBean = mAdpter.getItem(position);
            mSelectedId = shopMallBean.getShopmallID();
            mAdpter.notifyDataSetChanged();
            Intent intent = new Intent();
            intent.putExtra("bean", shopMallBean);
            setResult(RESULT_OK, intent);
            finish();
        });
        mProductListView.setAdapter(mAdpter);
    }

    @Subscribe(sticky = true)
    public void onEvent(ComplainManageEvent complainManageEvent) {
        if (complainManageEvent.getTarget() == ComplainManageEvent.TARGET.SELECT_PURCHASER_LIST) {
            mSearchView.showSearchContent(!TextUtils.isEmpty(complainManageEvent.getSearchContent()), complainManageEvent.getSearchContent());
        }

    }

    @Override
    public void queySuccess(ReportFormSearchResp resp, boolean isMore) {
        mAdpter.setNewData(resp.getList());
        mRefreshLayout.setEnableAutoLoadMore(resp.getList().size() == mPresent.getPageSize());
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getPurchaserId() {
        return mType == TYPE.SHOP ? mPurchaserId : null;
    }

    @Override
    public int getSearchType() {
        return mType;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }


    @IntDef({TYPE.SHOP, TYPE.GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
        int GROUP = 0;
        int SHOP = 1;

    }
}
