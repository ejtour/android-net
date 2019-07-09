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
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.event.SearchEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
            if (TextUtils.equals("全部", bean.getShopName())) {
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
            if (!TextUtils.equals(bean.getShopName(), "全部")) {
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

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    @Override
    public void showPurchaserShopList(List<PurchaserShopBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
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
