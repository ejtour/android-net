package com.hll_sc_app.app.cooperation.detail.shopadd;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.event.SearchEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情- 新增门店
 *
 * @author zhuyingsong
 * @date 2019/7/17
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_ADD_SHOP, extras = Constant.LOGIN_EXTRA)
public class CooperationAddShopActivity extends BaseLoadActivity implements CooperationAddShopContract.ICooperationAddShopView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    @BindView(R.id.txt_checkNum)
    TextView mTxtCheckNum;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    @Autowired(name = "object0")
    String mPurchaserId;
    private EmptyView mEmptyView;
    private CooperationAddShopPresenter mPresenter;
    private Map<String, PurchaserShopBean> mSelectMap;
    private CooperationDetailActivity.PurchaserShopListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_add_shop);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mSelectMap = new HashMap<>();
        initView();
        mPresenter = CooperationAddShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserShopList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserShopList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new CooperationDetailActivity.PurchaserShopListAdapter(mSelectMap);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserShopBean bean = (PurchaserShopBean) adapter.getItem(position);
            if (bean != null) {
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
                SearchActivity.start(searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryPurchaserShopList(true);
            }
        });
    }

    private void addOrRemove(PurchaserShopBean shopBean) {
        if (shopBean == null) {
            return;
        }
        String shopId = shopBean.getShopID();
        if (mSelectMap.containsKey(shopId)) {
            mSelectMap.remove(shopId);
        } else {
            mSelectMap.put(shopId, shopBean);
        }
    }

    /**
     * 判断是否设置全选
     */
    private void checkSelectAll() {
        if (mAdapter == null || CommonUtils.isEmpty(mAdapter.getData())) {
            return;
        }
        List<PurchaserShopBean> list = mAdapter.getData();
        boolean select = true;
        for (PurchaserShopBean bean : list) {
            if (!mSelectMap.containsKey(bean.getShopID())) {
                select = false;
                break;
            }
        }
        mImgAllCheck.setSelected(select);
        mTxtCheckNum.setText(String.format(Locale.getDefault(), "已选：%d", mSelectMap.size()));
        mTxtConfirm.setEnabled(!mSelectMap.isEmpty());
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Subscribe
    public void onEvent(SearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.img_allCheck, R.id.txt_allCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                toClose();
                break;
            case R.id.txt_confirm:
                EventBus.getDefault().post(new ArrayList<>(mSelectMap.values()));
                finish();
                break;
            case R.id.img_allCheck:
            case R.id.txt_allCheck:
                mImgAllCheck.setSelected(!mImgAllCheck.isSelected());
                selectAll(mImgAllCheck.isSelected());
                break;
            default:
                break;
        }
    }

    /**
     * 返回时候逻辑处理
     */
    private void toClose() {
        if (mSelectMap.isEmpty()) {
            finish();
            return;
        }
        // 填写过数据后进行提示
        showTipsDialog();
    }

    private void selectAll(boolean select) {
        if (mAdapter == null || CommonUtils.isEmpty(mAdapter.getData())) {
            return;
        }
        if (select) {
            List<PurchaserShopBean> list = mAdapter.getData();
            for (PurchaserShopBean bean : list) {
                if (!mSelectMap.containsKey(bean.getShopID())) {
                    mSelectMap.put(bean.getShopID(), bean);
                }
            }
        } else {
            mSelectMap.clear();
        }
        mAdapter.notifyDataSetChanged();
        mTxtCheckNum.setText(String.format(Locale.getDefault(), "已选：%d", mSelectMap.size()));
        mTxtConfirm.setEnabled(!mSelectMap.isEmpty());
    }

    /**
     * 退出提示对话框
     */
    private void showTipsDialog() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确认要离开么")
                .setMessage("您已经填写了部分数据，离开会\n丢失当前已填写的数据")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    if (item == 0) {
                        finish();
                    }
                    dialog.dismiss();
                }, "确认离开", "我再想想").create().show();
    }

    @Override
    public void onBackPressed() {
        toClose();
    }

    @Override
    public void showPurchaserShopList(List<PurchaserShopBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
        checkSelectAll();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getPurchaserId() {
        return mPurchaserId;
    }
}
