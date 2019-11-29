package com.hll_sc_app.app.blacklist.cooperation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

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
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.orientation.OrientationListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.BLACK_COOPERATION_PURCHASER, extras = Constant.LOGIN_EXTRA)
public class CooperationActivity extends BaseLoadActivity implements ICooperationContract.ICooperationPurchaserView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    private CooperationActivity.PurchaserListAdapter mAdapter;
    private EmptyView mEmptyView;
    private CooperationPresenter mPresenter;

    @Autowired(name = "parcelable", required = true)
    OrientationListBean mOrientationListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        toQueryGroupList();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new CooperationActivity.PurchaserListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrientationListBean bean = (OrientationListBean) adapter.getItem(position);
            if(mOrientationListBean != null) {
                bean.setFrom(mOrientationListBean.getFrom());
                bean.setId(mOrientationListBean.getId());
                if(bean.getPurchaserID().equalsIgnoreCase(mOrientationListBean.getPurchaserID())) {
                    bean.setPurchaserShopIDs(mOrientationListBean.getPurchaserShopIDs());
                }
            } else {
                bean.setFrom(0);
            }
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.BLACK_COOPERATION_SHOP, bean);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有合作采购商").create();
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CooperationActivity.this,
                        searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                toQueryGroupList();
            }
        });
    }

    private void toQueryGroupList() {
        mPresenter.queryCooperationPurchaserList();
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

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showPurchaserList(List<OrientationListBean> list, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    class PurchaserListAdapter extends BaseQuickAdapter<OrientationListBean, BaseViewHolder> {

        PurchaserListAdapter() {
            super(R.layout.item_orientation_purchaser);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrientationListBean bean) {
            if(mOrientationListBean != null && mOrientationListBean.getPurchaserID().equals(bean.getPurchaserID())) {
                helper.setTextColor(R.id.txt_purchaserName,0XFF5695D2);
            }
            helper.setText(R.id.txt_purchaserName, bean.getPurchaserName());
        }
    }
}
