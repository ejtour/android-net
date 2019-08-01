package com.hll_sc_app.app.deliverymanage.minimum.purchaser;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.hll_sc_app.app.deliverymanage.minimum.purchaser.shop.ShopMinimumActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsRelevanceSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.DeliveryPurchaserBean;
import com.hll_sc_app.bean.event.GoodsRelevanceSearchEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择合作采购商-最小起购量设置
 *
 * @author zhuyingsong
 * @date 2019/7/31
 */
@Route(path = RouterConfig.DELIVERY_MINIMUM_PURCHASER, extras = Constant.LOGIN_EXTRA)
public class PurchaserMinimumActivity extends BaseLoadActivity implements PurchaserMinimumContract.IPurchaserMinimumView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mSendAmountId;
    @Autowired(name = "parcelable")
    ArrayList<DeliveryPurchaserBean> mList;
    private EmptyView mEmptyView;
    private PurchaserListAdapter mAdapter;
    private PurchaserMinimumPresenter mPresenter;


    public static void start(String id, ArrayList<DeliveryPurchaserBean> value) {
        ARouter.getInstance().build(RouterConfig.DELIVERY_MINIMUM_PURCHASER)
            .withString("object0", id)
            .withParcelableArrayList("parcelable", value)
            .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_add_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = PurchaserMinimumPresenter.newInstance();
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new PurchaserListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserBean bean = (PurchaserBean) adapter.getItem(position);
            if (bean != null) {
                ShopMinimumActivity.start(bean.getPurchaserID(), bean.getPurchaserName(), mSendAmountId,
                    getSelectIds(bean.getPurchaserID()));
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有合作采购商").create();
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, GoodsRelevanceSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryPurchaserList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserList(false);
            }
        });
    }

    private List<String> getSelectIds(String purchaserId) {
        List<String> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(mList)) {
            for (DeliveryPurchaserBean bean : mList) {
                if (TextUtils.equals(bean.getPurchaserID(), purchaserId)) {
                    list = bean.getPurchaserShopList();
                    break;
                }
            }
        }
        return list;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Subscribe
    public void onEvent(GoodsRelevanceSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
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
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter() {
            super(R.layout.item_purchaser_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean bean) {
            helper.setText(R.id.txt_purchaserName, bean.getPurchaserName());
        }
    }
}
