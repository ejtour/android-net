package com.hll_sc_app.app.marketingsetting.coupon.selectshops;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.helper.MarketingHelper;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CustomerNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.event.MarketingSelectShopEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.marketingsetting.CouponSendReq;
import com.hll_sc_app.bean.marketingsetting.MarketingCustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
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

@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_SELECT_GROUPS)
public class SelectGroupsActivity extends BaseLoadActivity implements ISelectContract.ISearchGroupsView {
    @BindView(R.id.title)
    TitleBar mTitle;
    @BindView(R.id.search)
    SearchView mSearch;
    @BindView(R.id.recyclerView)
    RecyclerView mList;
    @BindView(R.id.check_all)
    CheckBox mCheckAll;
    @BindView(R.id.txt_confirm)
    TextView mConfirm;
    @Autowired(name = "object0")
    ArrayList<CouponSendReq.GroupandShopsBean> mSelectList;
    @Autowired(name = "object1")
    String mTitleText;
    private Unbinder unbinder;

    private Map<String, CouponSendReq.GroupandShopsBean> mSelectMap = new HashMap<>();

    private GroupItemAdapter mGroupAdapter;

    private SelectGroupsPresent mPresent;

    private int currentGroupIndex = -1;


    public static void start(ArrayList<CouponSendReq.GroupandShopsBean> customerListBeans) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_MARKETING_COUPON_SELECT_GROUPS)
                .withParcelableArrayList("object0", customerListBeans)
                .setProvider(new LoginInterceptor()).navigation();
    }

    public static void start(ArrayList<MarketingCustomerBean> marketingCustomerBeans, String title) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_MARKETING_COUPON_SELECT_GROUPS)
                .withParcelableArrayList("object0", MarketingHelper.convertCustomerToCouponBean(marketingCustomerBeans))
                .withString("object1", title)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_select_groups);
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

    private Map<String, CouponSendReq.GroupandShopsBean> transformSelect(List<CouponSendReq.GroupandShopsBean> customerListBeans) {
        Map<String, CouponSendReq.GroupandShopsBean> map = new HashMap<>();
        if (customerListBeans != null) {
            for (CouponSendReq.GroupandShopsBean bean : customerListBeans) {
                map.put(bean.getPurchaserID(), bean);
            }
        }
        return map;
    }

    private void initView() {
        if (!TextUtils.isEmpty(mTitleText)) {
            mTitle.setHeaderTitle(mTitleText);
        }
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mGroupAdapter = new GroupItemAdapter(null);
        mList.setAdapter(mGroupAdapter);
        mGroupAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean purchaserBean = mGroupAdapter.getItem(position);
            if (purchaserBean == null) return;
            switch (view.getId()) {
                case R.id.txt_group_select:
                    currentGroupIndex = position;
                    boolean isDefaultAll = mSelectMap.get(purchaserBean.getPurchaserID()) != null && mSelectMap.get(purchaserBean.getPurchaserID()).getShopIDList() == null;
                    SelectShopsActivity.start(purchaserBean.getPurchaserID(), getSelectShops(purchaserBean), isDefaultAll);
                    break;
                case R.id.txt_group_name:
                    if (mSelectMap.get(purchaserBean.getPurchaserID()) == null) {
                        CouponSendReq.GroupandShopsBean customerListBean = new CouponSendReq.GroupandShopsBean();
                        customerListBean.setScope(1);
                        customerListBean.setPurchaserID(purchaserBean.getPurchaserID());
                        customerListBean.setPurchaserName(purchaserBean.getPurchaserName());
                        customerListBean.setShopCount(CommonUtils.getInt(purchaserBean.getShopCount()));
                        mSelectMap.put(purchaserBean.getPurchaserID(), customerListBean);
                    } else {
                        mSelectMap.remove(purchaserBean.getPurchaserID());
                    }
                    mGroupAdapter.notifyDataSetChanged();
                    updateConfirm(mSelectMap.size());
                    mCheckAll.setChecked(mSelectMap.size() == mGroupAdapter.getItemCount());
                    break;
                default:
                    break;
            }
        });
        mSearch.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectGroupsActivity.this,
                        searchContent, CustomerNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.getGroups();
            }
        });

        mPresent = SelectGroupsPresent.newInstance();
        mPresent.register(this);
        mPresent.getGroups();
    }

    private ArrayList<PurchaserShopBean> getSelectShops(PurchaserBean currentPurchaser) {
        if (mSelectMap.get(currentPurchaser.getPurchaserID()) == null) {
            return null;
        } else {
            ArrayList<PurchaserShopBean> shopListBeans = new ArrayList<>();
            List<String> shopIds = mSelectMap.get(currentPurchaser.getPurchaserID()).getShopIDList();
            if (shopIds == null) {//说明是在集团页面直接全选，并没有获取到具体的shopid
                return null;
            } else {
                for (String shopID : mSelectMap.get(currentPurchaser.getPurchaserID()).getShopIDList()) {
                    PurchaserShopBean shopListBean = new PurchaserShopBean();
                    shopListBean.setShopID(shopID);
                    shopListBeans.add(shopListBean);
                }
            }

            return shopListBeans;
        }
    }

    private void updateConfirm(int number) {
        mConfirm.setEnabled(number != 0);
        mConfirm.setText(String.format("确认(%s)", number));
    }

    @Override
    public void showGroups(CooperationPurchaserResp resp) {
        Map<String, CouponSendReq.GroupandShopsBean> map = new HashMap<>();
        if (resp.getRecords().size() == 0) {
            mGroupAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("当前没有可选择的用户").create());
            mGroupAdapter.setNewData(null);
        } else {
            if (!CommonUtils.isEmpty(resp.getRecords()) && mSelectMap.size() > 0) {
                for (PurchaserBean record : resp.getRecords()) {
                    if (mSelectMap.containsKey(record.getPurchaserID())) {
                        mSelectMap.get(record.getPurchaserID()).setShopCount(CommonUtils.getInt(record.getShopCount()));
                        map.put(record.getPurchaserID(), mSelectMap.get(record.getPurchaserID()));
                    }
                }
            }
            mGroupAdapter.setNewData(resp.getRecords());
        }
        mSelectMap = map;
        mCheckAll.setChecked(mSelectMap.size() == mGroupAdapter.getItemCount());
        updateConfirm(mSelectMap.size());
    }

    @Override
    public String getSearchText() {
        return mSearch.getSearchContent();
    }

    @OnClick({R.id.check_all, R.id.txt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_all:
                if (mSelectMap.size() == mGroupAdapter.getItemCount()) {
                    mSelectMap.clear();
                } else {
                    for (PurchaserBean bean : mGroupAdapter.getData()) {
                        CouponSendReq.GroupandShopsBean customerListBean = new CouponSendReq.GroupandShopsBean();
                        customerListBean.setPurchaserName(bean.getPurchaserName());
                        customerListBean.setPurchaserID(bean.getPurchaserID());
                        customerListBean.setScope(1);
                        customerListBean.setShopCount(CommonUtils.getInt(bean.getShopCount()));
                        mSelectMap.put(bean.getPurchaserID(), customerListBean);
                    }
                }
                mGroupAdapter.notifyDataSetChanged();
                updateConfirm(mSelectMap.size());
                break;
            case R.id.txt_confirm:
                ArrayList<CouponSendReq.GroupandShopsBean> customerListBeans = new ArrayList<>();
                for (String id : mSelectMap.keySet()) {
                    customerListBeans.add(mSelectMap.get(id));
                }
                EventBus.getDefault().post(customerListBeans);
                finish();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onSubscribe(MarketingSelectShopEvent event) {
        if (event.getSelecShops() != null && currentGroupIndex > -1) {
            PurchaserBean currentGroupBean = mGroupAdapter.getItem(currentGroupIndex);
            if (currentGroupBean == null) return;
            ArrayList<String> shopIds = new ArrayList<>();
            for (PurchaserShopBean shopListBean : event.getSelecShops().getSelectShops()) {
                shopIds.add(shopListBean.getShopID());
            }
            if (mSelectMap.get(currentGroupBean.getPurchaserID()) != null) {
                if (shopIds.size() != 0) {
                    mSelectMap.get(currentGroupBean.getPurchaserID()).setShopIDList(shopIds);
                    mSelectMap.get(currentGroupBean.getPurchaserID()).setScope(event.getSelecShops().isSelectAll() ? 1 : 0);
                } else {
                    mSelectMap.remove(currentGroupBean.getPurchaserID());
                }
            } else if (shopIds.size() != 0) {
                CouponSendReq.GroupandShopsBean groupandShopsBean = new CouponSendReq.GroupandShopsBean();
                groupandShopsBean.setPurchaserID(currentGroupBean.getPurchaserID());
                groupandShopsBean.setPurchaserName(currentGroupBean.getPurchaserName());
                groupandShopsBean.setScope(event.getSelecShops().isSelectAll() ? 1 : 0);
                groupandShopsBean.setShopIDList(shopIds);
                groupandShopsBean.setShopCount(CommonUtils.getInt(currentGroupBean.getShopCount()));
                mSelectMap.put(currentGroupBean.getPurchaserID(), groupandShopsBean);
            }
            mGroupAdapter.notifyDataSetChanged();
            updateConfirm(mSelectMap.size());
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

    private class GroupItemAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {
        public GroupItemAdapter(@Nullable List<PurchaserBean> data) {
            super(R.layout.list_item_marketing_select_group, data);

        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            holder.addOnClickListener(R.id.txt_group_select);
            holder.addOnClickListener(R.id.txt_group_name);
            return holder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            boolean isSelect = mSelectMap.containsKey(item.getPurchaserID());
            helper.getView(R.id.txt_group_name).setSelected(isSelect);
            helper.setText(R.id.txt_group_name, item.getPurchaserName())
                    .setText(R.id.txt_group_select, isSelect ?
                            mSelectMap.get(item.getPurchaserID()).getScope() == 1 ? "已全选门店" : String.format("已选%s个门店", mSelectMap.get(item.getPurchaserID()).getShopIDList().size()) :
                            "");
        }
    }
}
