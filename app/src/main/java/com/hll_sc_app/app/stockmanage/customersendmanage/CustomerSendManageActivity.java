package com.hll_sc_app.app.stockmanage.customersendmanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.invwarn.TopSingleSelectWindow;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ShopNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.stockmanage.CustomerSendManageListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_STOCK_CUSTOMER_SEND)
public class CustomerSendManageActivity extends BaseLoadActivity implements ICustomerSendManageContract.IView {
    @BindView(R.id.txt_house_name)
    TextView mTxtHouseName;
    @BindView(R.id.search)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ICustomerSendManageContract.IPresent mPresent;
    private Unbinder unbinder;
    private CustomerSendAdpater mAdapter;
    private TopSingleSelectWindow<HouseBean> mSelectHouseWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_send_manage);
        unbinder = ButterKnife.bind(this);
        mPresent = CustomerSendManagePresent.newInstance();
        mPresent.register(this);
        initView();
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CustomerSendAdpater(null);
        mRecyclerView.setAdapter(mAdapter);

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CustomerSendManageActivity.this,
                        searchContent, ShopNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.refresh();
            }
        });
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

    @Override
    public void getHouseListSuccess(List<HouseBean> houseBeans) {
        if (mSelectHouseWindow == null) {
            mSelectHouseWindow = new TopSingleSelectWindow<>(this, item -> item.getHouseName());
            houseBeans.add(0, new HouseBean("全部仓库", "-1"));
            mSelectHouseWindow.refreshList(houseBeans);
            mSelectHouseWindow.setSeletct(0);
            mSelectHouseWindow.setListener(houseBean -> {
                mTxtHouseName.setTag(houseBean);
                mTxtHouseName.setText(houseBean.getHouseName());
                mPresent.refresh();
            });
        }
    }

    @Override
    public String getSearchContent() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getHouseId() {
        if (mTxtHouseName.getTag() == null) {
            return "-1";
        } else {
            return ((HouseBean) mTxtHouseName.getTag()).getId();
        }
    }

    @Override
    public void queryCustomerSendManageListSuccess(CustomerSendManageListResp resp, boolean isMore) {
        if (isMore && resp.getRecords().size() > 0) {
            mAdapter.addData(resp.getRecords());
        } else if (!isMore) {
            if (resp.getRecords().size() == 0) {
                mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("当前条件下没有数据").create());
            }
            mAdapter.setNewData(resp.getRecords());
        }

        if (!CommonUtils.isEmpty(resp.getRecords())) {
            mRefreshLayout.setEnableLoadMore(resp.getRecords().size() == mPresent.getPageSize());
        }
    }

    @OnClick({R.id.txt_house_name,R.id.img_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_house_name:
                if (mSelectHouseWindow != null) {
                    mSelectHouseWindow.showAsDropDown(mTxtHouseName);
                }
                break;
            case R.id.img_close:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    private class CustomerSendAdpater extends BaseQuickAdapter<CustomerSendManageListResp.RecordsBean, BaseViewHolder> {
        public CustomerSendAdpater(@Nullable List<CustomerSendManageListResp.RecordsBean> data) {
            super(R.layout.list_item_customer_send_manage, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomerSendManageListResp.RecordsBean item) {
            helper.setText(R.id.txt_shop_name, item.getShopName())
                    .setText(R.id.txt_group_name, item.getPurchaserName())
                    .setText(R.id.txt_stock_name, "发货仓库：" + (TextUtils.isEmpty(item.getHouseName()) ? "无" : item.getHouseName()));

            ((GlideImageView) helper.getView(R.id.glide_img)).setImageURL(item.getImgUrl());
        }
    }
}
