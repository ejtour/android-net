package com.hll_sc_app.app.marketingsetting.coupon.selectshops;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.event.MarketingSelectShopEvent;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

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
    ArrayList<PurchaserShopBean> mSelectList;
    @Autowired(name = "purchaserID")
    String purchaserID;
    @Autowired(name = "isDefaultAll")
    boolean isDefaultAll;
    private Unbinder unbinder;

    private Map<String, PurchaserShopBean> mSelectMap = new HashMap<>();

    private ShopItemAdapter mShopAdapter;

    private SelectShopsPresent mPresent;


    public static void start(String purchaserID, ArrayList<PurchaserShopBean> customerListBeans, boolean isDefaultAll) {
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
        unbinder = ButterKnife.bind(this);
        mSelectMap = transformSelect(mSelectList);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private Map<String, PurchaserShopBean> transformSelect(List<PurchaserShopBean> customerListBeans) {
        Map<String, PurchaserShopBean> map = new HashMap<>();
        if (customerListBeans != null) {
            for (PurchaserShopBean bean : customerListBeans) {
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
            PurchaserShopBean shopListBean = mShopAdapter.getItem(position);
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
                SearchActivity.start(SelectShopsActivity.this,
                        searchContent, SelectShopSearch.class.getSimpleName());
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
            EventBus.getDefault().post(new MarketingSelectShopEvent(new MarketingSelectShopEvent.SelecShops(mSelectList.size() == mShopAdapter.getItemCount(), mSelectList)));
            finish();
        });
    }


    @OnClick({R.id.rl_check_all, R.id.check_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_check_all:
            case R.id.check_all:
                if (mSelectMap.size() != mShopAdapter.getItemCount()) {
                    for (PurchaserShopBean shopListBean : mShopAdapter.getData()) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearch.showSearchContent(true, name);
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
    public void showShops(CooperationShopListResp resp) {
        if (resp.getShopList().size() == 0) {
            mShopAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("当前没有可选择的门店").create());
            mShopAdapter.setNewData(null);
        } else {
            if (isDefaultAll) {
                mSelectList = (ArrayList<PurchaserShopBean>) resp.getShopList();
                mSelectMap = transformSelect(mSelectList);
            }
            mShopAdapter.setNewData(resp.getShopList());
        }
        mCheckAll.setChecked(mSelectMap.size() == mShopAdapter.getItemCount());
    }

    private class ShopItemAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {
        public ShopItemAdapter(@Nullable List<PurchaserShopBean> data) {
            super(R.layout.list_item_marketing_select_shop, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            boolean isSelect = mSelectMap.containsKey(item.getShopID());
            helper.setChecked(R.id.checkbox, isSelect)
                    .setText(R.id.txt_shop_name, item.getShopName())
                    .setTextColor(R.id.txt_shop_name, Color.parseColor(isSelect ? "#222222" : "#666666"));
        }
    }
}
