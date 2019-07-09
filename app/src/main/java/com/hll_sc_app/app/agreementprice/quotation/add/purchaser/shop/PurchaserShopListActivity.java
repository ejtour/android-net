package com.hll_sc_app.app.agreementprice.quotation.add.purchaser.shop;

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
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.event.SearchEvent;
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

/**
 * 选择合作采购商-选择门店
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER_SHOP, extras = Constant.LOGIN_EXTRA)
public class PurchaserShopListActivity extends BaseLoadActivity implements PurchaserShopListContract.IPurchaserListView {
    public static final String STRING_ALL = "全部";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @Autowired(name = "parcelable")
    QuotationBean mQuotationBean;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    private PurchaserShopListAdapter mAdapter;
    private EmptyView mEmptyView;
    private PurchaserShopPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_add_purchaser_shop);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = PurchaserShopPresenter.newInstance();
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
        mTxtTitle.setText(mQuotationBean.getPurchaserName());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new PurchaserShopListAdapter();
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
                if (TextUtils.equals("0", mQuotationBean.getIsWarehouse())) {
                    OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_SEARCH);
                } else {
                    showToast("代仓门店暂时不支持搜索");
                }
            }

            @Override
            public void toSearch(String searchContent) {
                toQueryShopList();
            }
        });
    }

    private void toQueryShopList() {
        if (TextUtils.equals("1", mQuotationBean.getIsWarehouse())) {
            mPresenter.queryCooperationWarehouseDetail(mQuotationBean.getPurchaserID());
        } else {
            mPresenter.queryPurchaserShopList(mQuotationBean.getPurchaserID());
        }
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
            mQuotationBean.setShopIDs(TextUtils.join(",", selectList));
            mQuotationBean.setShopIDNum(String.valueOf(selectList.size()));
            EventBus.getDefault().post(mQuotationBean);
            finish();
        }
    }

    /**
     * 返回时候逻辑处理
     */
    private void toClose() {
        String shopId = mQuotationBean.getShopIDs();
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
                if (TextUtils.equals(STRING_ALL, shopBean.getShopName())) {
                    mQuotationBean.setIsAllShop(shopBean.isSelect() ? "1" : "0");
                    continue;
                }
                if (shopBean.isSelect()) {
                    selectList.add(shopBean.getShopID());
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
                    boolean warehouse = TextUtils.equals("1", mQuotationBean.getIsWarehouse());
                    RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER, this, warehouse);
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
            String shopId = mQuotationBean.getShopIDs();
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

    class PurchaserShopListAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        PurchaserShopListAdapter() {
            super(R.layout.item_purchaser_shop_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean bean) {
            helper.setText(R.id.txt_shopName, bean.getShopName())
                .getView(R.id.img_select).setSelected(bean.isSelect());
        }
    }
}
