package com.hll_sc_app.app.cooperation.add;

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
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.CooperationPurchaserActivity;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
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
 * 合作采购商-搜索新增
 *
 * @author zhuyingsong
 * @date 2019/7/17
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_ADD, extras = Constant.LOGIN_EXTRA)
public class CooperationAddActivity extends BaseLoadActivity implements CooperationAddContract.ICooperationAddView {
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private CooperationAddPresenter mPresenter;
    private CooperationPurchaserActivity.PurchaserListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_purchaser_add);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationAddPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
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
                mPresenter.queryPurchaserList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new CooperationPurchaserActivity.PurchaserListAdapter(true);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = (PurchaserBean) adapter.getItem(position);
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, bean.getPurchaserID());
            }
        });
        EmptyView emptyView = EmptyView.newBuilder(this)
            .setTips("您可以根据采购商名称或ID搜索\n以添加新的合作采购商")
            .setImage(R.drawable.ic_search_empty_purchaser)
            .create();
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void toSearch() {
        mEdtSearch.clearFocus();
        ViewUtils.clearEditFocus(mEdtSearch);
        mPresenter.queryPurchaserList(true);
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
    public String getSearchParam() {
        return mEdtSearch.getText().toString().trim();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }
}
