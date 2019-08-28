package com.hll_sc_app.app.marketingsetting.coupon.selectshops;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SelectShopSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.CooperationShopsListResp;
import com.hll_sc_app.bean.event.MarketingSelectShopEvent;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_SELECT_SHOPS)
public class SelectShopsActivity extends BaseLoadActivity implements ISelectContract.ISearchShopsView {
    @BindView(R.id.title)
    TitleBar mTitle;
    @BindView(R.id.search)
    SearchView mSearch;
    @BindView(R.id.recyclerView)
    RecyclerView mList;
    @BindView(R.id.check_all)
    CheckBox mCheckAll;
    @Autowired(name = "object0")
    ArrayList<CooperationShopsListResp.ShopListBean> mSelectList;
    @Autowired(name = "purchaserID")
    String purchaserID;
    @Autowired(name = "isDefaultAll")
    boolean isDefaultAll;
    private Unbinder unbinder;

    private Map<String, CooperationShopsListResp.ShopListBean> mSelectMap = new HashMap<>();

    private ShopItemAdapter mShopAdapter;

    private SelectShopsPresent mPresent;


    public static void start(String purchaserID, ArrayList<CooperationShopsListResp.ShopListBean> customerListBeans, boolean isDefaultAll) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_MARKETING_COUPON_SELECT_SHOPS)
                .withParcelableArrayList("object0", customerListBeans)
                .withString("purchaserID", purchaserID)
                .withBoolean("isDefaultAll", isDefaultAll)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_select_shop);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this);
        mSelectMap = transformSelect(mSelectList);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private Map<String, CooperationShopsListResp.ShopListBean> transformSelect(List<CooperationShopsListResp.ShopListBean> customerListBeans) {
        Map<String, CooperationShopsListResp.ShopListBean> map = new HashMap<>();
        if (customerListBeans != null) {
            for (CooperationShopsListResp.ShopListBean bean : customerListBeans) {
                map.put(bean.getShopID(), bean);
            }
        }
        return map;
    }

    private void initView() {
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mShopAdapter = new ShopItemAdapter(null);
        mList.setAdapter(mShopAdapter);
        mShopAdapter.setOnItemClickListener((adapter, view, position) -> {
            CooperationShopsListResp.ShopListBean shopListBean = mShopAdapter.getItem(position);
            if (mSelectMap.containsKey(shopListBean.getShopID())) {
                mSelectMap.remove(shopListBean.getShopID());
            } else {
                mSelectMap.put(shopListBean.getShopID(), shopListBean);
            }
            mCheckAll.setChecked(mSelectMap.size() == mShopAdapter.getItemCount());
            mShopAdapter.notifyDataSetChanged();
        });
        mSearch.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, SelectShopSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.getShops();
            }
        });

        mPresent = SelectShopsPresent.newInstance();
        mPresent.register(this);
        mPresent.getShops();

        mTitle.setRightBtnClick(v -> {
            mSelectList = new ArrayList<>(mSelectMap.values());
            EventBus.getDefault().post(new MarketingSelectShopEvent(0, null,
                    new MarketingSelectShopEvent.SelecShops(mSelectList.size() == mShopAdapter.getItemCount(), mSelectList)));
            finish();
        });
    }


    @OnClick({R.id.rl_check_all, R.id.check_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_check_all:
            case R.id.check_all:
                if (mSelectMap.size() != mShopAdapter.getItemCount()) {
                    for (CooperationShopsListResp.ShopListBean shopListBean : mShopAdapter.getData()) {
                        mSelectMap.put(shopListBean.getShopID(), shopListBean);
                    }
                } else {
                    mSelectMap.clear();
                }
                mCheckAll.setChecked(mSelectMap.size() == mShopAdapter.getItemCount());
                mShopAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    @Subscribe
    public void onSubscribe(MarketingSelectShopEvent event) {
        if (!event.isGroupScope()) {
            mSearch.showSearchContent(true, event.getSearchText());
        }
    }

    @Override
    public String getPurchaserID() {
        return purchaserID;
    }

    @Override
    public String getSearchText() {
        return mSearch.getSearchContent();
    }

    @Override
    public void showShops(CooperationShopsListResp resp) {
        if (resp.getShopList().size() == 0) {
            mShopAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("当前没有可选择的门店").create());
            mShopAdapter.setNewData(null);
        } else {
            if (isDefaultAll) {
                mSelectList = (ArrayList<CooperationShopsListResp.ShopListBean>) resp.getShopList();
                mSelectMap = transformSelect(mSelectList);
            }
            mShopAdapter.setNewData(resp.getShopList());
        }
        mCheckAll.setChecked(mSelectMap.size() == mShopAdapter.getItemCount());
    }

    private class ShopItemAdapter extends BaseQuickAdapter<CooperationShopsListResp.ShopListBean, BaseViewHolder> {
        public ShopItemAdapter(@Nullable List<CooperationShopsListResp.ShopListBean> data) {
            super(R.layout.list_item_marketing_select_shop, data);

        }

        @Override
        protected void convert(BaseViewHolder helper, CooperationShopsListResp.ShopListBean item) {
            boolean isSelect = mSelectMap.containsKey(item.getShopID());
            helper.setChecked(R.id.checkbox, isSelect)
                    .setText(R.id.txt_shop_name, item.getShopName())
                    .setTextColor(R.id.txt_shop_name, Color.parseColor(isSelect ? "#222222" : "#666666"));

        }
    }
}
