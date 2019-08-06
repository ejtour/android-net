package com.hll_sc_app.app.orientationsale.cooperation.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;

import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.event.SearchEvent;
import com.hll_sc_app.bean.orientation.OrientationListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.ORIENTATION_COOPERATION_SHOP, extras = Constant.LOGIN_EXTRA)
public class CooperationShopActivity extends BaseLoadActivity implements ICooperationShopContract.ICooperationShopView {

    public static final String STRING_ALL = "全部";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @Autowired(name = "parcelable", required = true)
    OrientationListBean mOrientationListBean;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    private CooperationShopActivity.CooperationShopAdapter mAdapter;
    private EmptyView mEmptyView;
    private ICooperationShopContract.ICooperationShopPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_purchaser_shop);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
        toQueryShopList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTxtTitle.setText(mOrientationListBean.getPurchaserName());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new CooperationShopActivity.CooperationShopAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserShopBean bean = (PurchaserShopBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (TextUtils.equals(STRING_ALL, bean.getShopName())) {
                selectAll(!bean.isSelect());
            } else {
                bean.setSelect(!bean.isSelect());
                checkSelectAll();
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
                toQueryShopList();
            }
        });
    }

    private void toQueryShopList() {
        mPresenter.queryPurchaserShopList(mOrientationListBean.getPurchaserID());
    }

    private void selectAll(boolean select) {
        if (mAdapter == null || CommonUtils.isEmpty(mAdapter.getData())) {
            return;
        }
        List<PurchaserShopBean> list = mAdapter.getData();
        for (PurchaserShopBean bean : list) {
            bean.setSelect(select);
        }
        mAdapter.notifyDataSetChanged();
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
            if (!TextUtils.equals(bean.getShopName(), STRING_ALL)) {
                if (!bean.isSelect()) {
                    select = false;
                    break;
                }
            }
        }
        list.get(0).setSelect(select);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEvent(SearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            toClose();
        } else if (view.getId() == R.id.txt_confirm) {
            List<String> selectList = getSelectList();
            if (CommonUtils.isEmpty(selectList)) {
                showToast("您还没有选择门店");
                return;
            }
            mOrientationListBean.setPurchaserShopIDs(TextUtils.join(",", selectList));
            ARouter.getInstance()
                    .build(RouterConfig.ORIENTATION_DETAIL)
                    .withParcelable("parcelable", mOrientationListBean)
                    .withBoolean("reload", true)
                    .withBoolean("item", true)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .setProvider(new LoginInterceptor())
                    .navigation(this);

        }
    }

    /**
     * 返回时候逻辑处理
     */
    private void toClose() {
        String shopId = mOrientationListBean.getPurchaserShopIDs();
        if (TextUtils.isEmpty(shopId)) {
            finish();
            return;
        }
        // 填写过数据后进行提示
        showTipsDialog();
    }

    /**
     * 获取选中的门店
     *
     * @return 选中的门店列表
     */
    private List<String> getSelectList() {
        List<String> selectList = new ArrayList<>();
        List<PurchaserShopBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (PurchaserShopBean shopBean : list) {
                if (shopBean.isSelect()) {
                    if (shopBean.getShopID() != null) {
                        selectList.add(shopBean.getShopID());
                    }
                }
            }
        }
        return selectList;
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
                        RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER, this);
                    }
                    dialog.dismiss();
                }, "确认离开", "我再想想").create().show();
    }

    @Override
    public void onBackPressed() {
        toClose();
    }

    @Override
    public void showPurchaserShopList(List<PurchaserShopBean> list) {
        if (!CommonUtils.isEmpty(list)) {
            String shopId = mOrientationListBean.getPurchaserShopIDs();
            if (!TextUtils.isEmpty(shopId)) {
                for (PurchaserShopBean shopBean : list) {
                    if (TextUtils.equals(STRING_ALL, shopBean.getShopName())) {
                        continue;
                    }
                    shopBean.setSelect(shopId.contains(shopBean.getShopID()));
                }
            }
        }
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
        checkSelectAll();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    class CooperationShopAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        CooperationShopAdapter() {
            super(R.layout.item_orientation_purchaser_shop);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean bean) {
            helper.setText(R.id.txt_shopName, bean.getShopName())
                    .getView(R.id.img_select).setSelected(bean.isSelect());
        }
    }
}
