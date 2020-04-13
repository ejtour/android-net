package com.hll_sc_app.app.goods.add.selectproductowner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 选择货主列表
 */
@Route(path = RouterConfig.ACTIVITY_SELECT_PRODUCT_OWNER)
public class SelectProductOwnerActivity extends BaseLoadActivity implements ISelectContractListContract.IView {
    private Unbinder unbinder;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView mList;
    //    @Autowired(name = "beans")
//    ArrayList<ContractListResp.ContractBean> mSelectBeans;
    @Autowired(name = "bean")
    WareHouseShipperBean mSelectBean;

    private ISelectContractListContract.IPresent mPresent;

    private ListAdapter mAdapter;

    public static void start(Activity activity, int requestCode, WareHouseShipperBean contractBean) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_CONTRACT_SELECT_MAIN_CONTRACT)
                .withParcelable("bean", contractBean)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_select_purchaser);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mPresent = SelectContractListPresent.newInstance();
        mPresent.register(this);
        mTxtTitle.setText("选择货主");
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.quereMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
        mAdapter = new ListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSelectBean = mAdapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra("bean", mSelectBean);
            setResult(RESULT_OK, intent);
            finish();
        });

        mList.setAdapter(mAdapter);

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectProductOwnerActivity.this,
                        searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.refresh();
            }
        });
        mPresent.queryList(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefresh.closeHeaderOrFooter();
    }

    @Override
    public String getContractName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showList(List<WareHouseShipperBean> contractBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(contractBeans);
        } else {
            if (CommonUtils.isEmpty(contractBeans)) {
                mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("当前没有货主").create());
                mAdapter.setNewData(null);
            } else {
                mAdapter.setNewData(contractBeans);
            }
        }
    }


    private class ListAdapter extends BaseQuickAdapter<WareHouseShipperBean, BaseViewHolder> {
        public ListAdapter(@Nullable List<WareHouseShipperBean> data) {
            super(R.layout.list_item_select_view, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, WareHouseShipperBean item) {
            helper.setText(R.id.txt_name, item.getPurchaserName());
            helper.setVisible(R.id.img_ok, mSelectBean.equals(item));
        }
    }


}
