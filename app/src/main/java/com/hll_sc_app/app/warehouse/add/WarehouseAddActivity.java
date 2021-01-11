package com.hll_sc_app.app.warehouse.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.app.warehouse.recommend.WarehouseGroupListAdapter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓管理-新签代仓客户
 * 我是货主-新签代仓公司
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
@Route(path = RouterConfig.WAREHOUSE_ADD, extras = Constant.LOGIN_EXTRA)
public class WarehouseAddActivity extends BaseLoadActivity implements WarehouseAddContract.IWarehouseAddView {
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private WarehouseAddPresenter mPresenter;
    private WarehouseGroupListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_add);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = WarehouseAddPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        mEdtSearch.setHint(isShipper() ? "请输入代仓公司名称" : "请输入客户名称");
        mEdtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return true;
        });
        mEdtSearch.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s
            -> mImgClear.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (isShipper()) {
                    mPresenter.queryMoreWarehouseList();
                } else {
                    mPresenter.queryMorePurchaserList();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (isShipper()) {
                    mPresenter.queryWarehouseList(false);
                } else {
                    mPresenter.queryPurchaserList(false);
                }
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        if (isShipper()) {
            mAdapter = new WarehouseGroupListAdapter();
        } else {
            mAdapter = new WarehouseGroupListAdapter(WarehouseGroupListAdapter.TYPE_ADD);
        }
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = (PurchaserBean) adapter.getItem(position);
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_DETAILS, isShipper() ? bean.getGroupID() :
                    bean.getPurchaserID());
            }
        });
        String tips = isShipper() ? "您可以输入代仓公司名称查找代仓公司" : "您可以输入客户名称查找客户";
        EmptyView emptyView = EmptyView.newBuilder(this).setImage(R.drawable.ic_search_empty_purchaser)
            .setTips(tips).create();
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 我是货主
     *
     * @return true-是
     */
    private boolean isShipper() {
        // 如果为非自营的客户则是货主，是自营的客户则是
        return !UserConfig.isSelfOperated();
    }

    private void toSearch() {
        mEdtSearch.clearFocus();
        ViewUtils.clearEditFocus(mEdtSearch);
        if (isShipper()) {
            mPresenter.queryWarehouseList(true);
        } else {
            mPresenter.queryPurchaserList(true);
        }
    }

    @OnClick({R.id.img_back, R.id.img_clear, R.id.txt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_clear:
                mEdtSearch.setText(null);
                mAdapter.setNewData(null);
                break;
            case R.id.txt_search:
                toSearch();
                break;
            default:
                break;
        }
    }

    @Override
    public void showPurchaserList(List<PurchaserBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showWarehouseList(List<PurchaserBean> list, boolean append, int totalNum) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(totalNum != mAdapter.getItemCount());
    }

    @Override
    public String getSearchParam() {
        return mEdtSearch.getText().toString().trim();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }
}
