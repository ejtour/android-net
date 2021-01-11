package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchasershop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择合作采购商-选择门店
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
@Route(path = RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER_SHOP, extras = Constant.LOGIN_EXTRA)
public class ShipperPurchaserShopSelectActivity extends BaseLoadActivity implements ShipperPurchaserShopSelectContract.IShopListView {
    public static final String STRING_ALL = "全部";
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable")
    ShipperShopResp.PurchaserBean mBean;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    @BindView(R.id.txt_bottom_cancel)
    TextView mTxtBottomCancel;
    @BindView(R.id.txt_bottom_del)
    TextView mTxtBottomDel;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;

    private EmptyView mEmptyView;
    private ShopListAdapter mAdapter;
    private ShipperPurchaserShopSelectPresenter mPresenter;
    private Map<String, ShipperShopResp.ShopBean> mSelectMap;

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_purchaser_shop_select);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = ShipperPurchaserShopSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTxtConfirm.setText(isDetail() ? "删除" : "确定");
        mSelectMap = new HashMap<>();
        mTxtTitle.setText(mBean.getPurchaserName());
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreShopList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryShopList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mAdapter = new ShopListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ShipperShopResp.ShopBean bean = (ShipperShopResp.ShopBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (isDetail()) {
                if (!isCancelStatus()) {
                    return;
                }
            }
            if (TextUtils.equals(STRING_ALL, bean.getShopName())) {
                selectAll(!bean.isSelect());
            } else {
                bean.setSelect(!bean.isSelect());
                addOrRemove(bean);
                checkSelectAll();
                adapter.notifyItemChanged(position);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有合作采购商门店数据").create();
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ShipperPurchaserShopSelectActivity.this,
                        searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryShopList(true);
            }
        });
    }

    private void selectAll(boolean select) {
        List<ShipperShopResp.ShopBean> shopBeans = mAdapter.getData();
        if (CommonUtils.isEmpty(shopBeans)) {
            return;
        }
        for (ShipperShopResp.ShopBean bean : shopBeans) {
            bean.setSelect(select);
            addOrRemove(bean);
        }
        mAdapter.notifyDataSetChanged();
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

    private void addOrRemove(ShipperShopResp.ShopBean bean) {
        if (bean == null) {
            return;
        }
        if (TextUtils.equals(bean.getShopName(), STRING_ALL)) {
            return;
        }
        if (bean.isSelect()) {
            if (!mSelectMap.containsKey(bean.getShopID())) {
                mSelectMap.put(bean.getShopID(), bean);
            }
        } else {
            mSelectMap.remove(bean.getShopID());
        }
        showDelCount();
    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.txt_bottom_cancel, R.id.txt_bottom_del})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.img_close) {
            toClose();
        } else if (id == R.id.txt_confirm) {
            if (isDetail()) {
                toCancel();
            } else {
                toConfirm("insert");
            }
        } else if (id == R.id.txt_bottom_cancel) {
            toCancel();
        } else if (id == R.id.txt_bottom_del) {
            showDelTipsDialog();
        }
    }

    /**
     * 返回时候逻辑处理
     */
    private void toClose() {
        finish();
    }

    /**
     * 详情页面
     *
     * @return true-详情
     */
    private boolean isDetail() {
        return mBean != null && mBean.isDetail();
    }

    private void toCancel() {
        if (isCancelStatus()) {
            mRlBottom.setVisibility(View.GONE);
            mTxtConfirm.setText("删除");
        } else {
            mRlBottom.setVisibility(View.VISIBLE);
            mTxtConfirm.setText("确定");
        }
        mAdapter.notifyDataSetChanged();
        showDelCount();
    }

    private void toConfirm(String actionType) {
        List<String> listSelect = getSelectList();
        if (!CommonUtils.isEmpty(listSelect)) {
            mPresenter.editWarehousePurchaser(listSelect, actionType);
        } else {
            showToast("您还没有选中采购商门店");
        }
    }

    /**
     * 删除关系提示框
     */
    private void showDelTipsDialog() {
        SuccessDialog.newBuilder(this)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setMessageTitle("确定要删除这些门店么")
            .setMessage("删除选中的门店将导致这些门店\n不能被代仓公司配送货物")
            .setButton((dialog, item) -> {
                if (item == 1) {
                    toConfirm("delete");
                }
                dialog.dismiss();
            }, "我再看看", "确定删除")
            .create().show();
    }

    /**
     * 目前处于删除状态
     *
     * @return true-删除状态
     */
    private boolean isCancelStatus() {
        return isDetail() && TextUtils.equals(mTxtConfirm.getText(), "确定");
    }

    private void showDelCount() {
        if (isDetail()) {
            mTxtBottomDel.setText(String.format(Locale.getDefault(), "确定删除（%d）", mSelectMap.size()));
        }
    }

    /**
     * 获取选中的门店
     *
     * @return 选中的门店列表
     */
    private List<String> getSelectList() {
        return new ArrayList<>(mSelectMap.keySet());
    }

    @Override
    public void onBackPressed() {
        toClose();
    }

    @Override
    public void showShopList(List<ShipperShopResp.ShopBean> list, boolean append, int total) {
        if (!CommonUtils.isEmpty(list)) {
            for (ShipperShopResp.ShopBean shopBean : list) {
                if (mSelectMap.containsKey(shopBean.getShopID())) {
                    shopBean.setSelect(true);
                }
            }
        }
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            if (!isDetail() && !CommonUtils.isEmpty(list)) {
                ShipperShopResp.ShopBean shopBeanAll = new ShipperShopResp.ShopBean();
                shopBeanAll.setShopName(STRING_ALL);
                list.add(0, shopBeanAll);
            }
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != (total + 1));
        checkSelectAll();
    }

    private void checkSelectAll() {
        List<ShipperShopResp.ShopBean> shopBeans = mAdapter.getData();
        if (CommonUtils.isEmpty(shopBeans)) {
            return;
        }
        boolean select = true;
        for (ShipperShopResp.ShopBean bean : shopBeans) {
            if (!TextUtils.equals(bean.getShopName(), STRING_ALL)) {
                if (!bean.isSelect()) {
                    select = false;
                    break;
                }
            }
        }
        ShipperShopResp.ShopBean firstBean = shopBeans.get(0);
        if (firstBean != null && TextUtils.equals(STRING_ALL, firstBean.getShopName())) {
            firstBean.setSelect(select);
            mAdapter.notifyItemChanged(0);
        }
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public ShipperShopResp.PurchaserBean getPurchaserBean() {
        return mBean;
    }

    @Override
    public void editSuccess() {
        ARouter.getInstance().build(RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    class ShopListAdapter extends BaseQuickAdapter<ShipperShopResp.ShopBean, BaseViewHolder> {

        ShopListAdapter() {
            super(R.layout.item_purchaser_shop_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShipperShopResp.ShopBean bean) {
            helper.setText(R.id.txt_shopName, bean.getShopName())
                .getView(R.id.img_select).setSelected(bean.isSelect());
            if (isDetail()) {
                // 删除模式下面展示选中
                helper.setGone(R.id.img_select, isCancelStatus());
            }
        }
    }
}
