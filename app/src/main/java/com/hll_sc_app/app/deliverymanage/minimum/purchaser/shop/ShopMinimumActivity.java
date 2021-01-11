package com.hll_sc_app.app.deliverymanage.minimum.purchaser.shop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.deliverymanage.minimum.area.DeliveryAreaActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.delivery.DeliveryPurchaserBean;
import com.hll_sc_app.bean.delivery.ShopMinimumBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择合作采购商门店-最小起购量设置
 *
 * @author zhuyingsong
 * @date 2019/7/31
 */
@Route(path = RouterConfig.DELIVERY_MINIMUM_PURCHASER_SHOP, extras = Constant.LOGIN_EXTRA)
public class ShopMinimumActivity extends BaseLoadActivity implements ShopMinimumContract.IShopMinimumView {
    public static final String DISABLE = "0";
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    @BindView(R.id.recyclerView_city)
    RecyclerView mRecyclerViewCity;
    @BindView(R.id.recyclerView_shop)
    RecyclerView mRecyclerViewShop;
    @Autowired(name = "object0")
    String mPurchaserId;
    @Autowired(name = "object1")
    String mPurchaserName;
    @Autowired(name = "object2")
    String mSendAmountId;
    @Autowired(name = "parcelable")
    ArrayList<String> mPurchaserShopList;
    private CityListAdapter mCityAdapter;
    private PurchaserShopListAdapter mShopAdapter;

    public static void start(String id, String name, String amountId, List<String> list) {
        ARouter.getInstance().build(RouterConfig.DELIVERY_MINIMUM_PURCHASER_SHOP)
            .withString("object0", id)
            .withString("object1", name)
            .withString("object2", amountId)
            .withStringArrayList("parcelable", (ArrayList<String>) list)
            .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_purchaser_shop);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        ShopMinimumPresenter mPresenter = ShopMinimumPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTxtTitle.setText(mPurchaserName);
        mRecyclerViewCity.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mCityAdapter = new CityListAdapter();
        mCityAdapter.setOnItemClickListener((adapter, view, position) -> selectCity(adapter, position));
        mRecyclerViewCity.setAdapter(mCityAdapter);
        mRecyclerViewShop.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mShopAdapter = new PurchaserShopListAdapter();
        mShopAdapter.setOnItemClickListener((adapter, view, position) -> selectShop(adapter, position));
        mRecyclerViewShop.setAdapter(mShopAdapter);
    }

    private void selectCity(BaseQuickAdapter adapter, int position) {
        ShopMinimumBean bean = (ShopMinimumBean) adapter.getItem(position);
        if (bean != null) {
            unSelectCity();
            bean.setSelect(true);
            adapter.notifyDataSetChanged();
            mShopAdapter.setNewData(bean.getPurchaserShops());
        }
    }

    private void selectShop(BaseQuickAdapter adapter, int position) {
        PurchaserShopBean bean = (PurchaserShopBean) adapter.getItem(position);
        if (bean != null && !TextUtils.equals(bean.getIsActive(), DISABLE)) {
            // 全选
            if (TextUtils.equals(bean.getShopName(), DeliveryAreaActivity.STRING_ALL)) {
                ShopMinimumActivity.this.selectAllShop(!bean.isSelect());
                ShopMinimumActivity.this.checkSelectAllCity();
                return;
            }
            // 普通选择
            bean.setSelect(!bean.isSelect());
            adapter.notifyItemChanged(position);
            ShopMinimumActivity.this.checkSelectAllShop();
            ShopMinimumActivity.this.checkSelectAllCity();
        }
    }

    private void unSelectCity() {
        List<ShopMinimumBean> list = mCityAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (ShopMinimumBean bean : list) {
                bean.setSelect(false);
            }
        }
    }

    private void selectAllShop(boolean select) {
        List<PurchaserShopBean> shopBeans = mShopAdapter.getData();
        if (!CommonUtils.isEmpty(shopBeans)) {
            for (PurchaserShopBean shopBean : shopBeans) {
                shopBean.setSelect(select);
            }
            mShopAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 判断是否选中了所有的地区
     */
    private void checkSelectAllCity() {
        boolean select = true;
        List<ShopMinimumBean> beans = mCityAdapter.getData();
        if (!CommonUtils.isEmpty(beans)) {
            CITY:
            for (ShopMinimumBean bean : beans) {
                List<PurchaserShopBean> shopBeans = bean.getPurchaserShops();
                if (!CommonUtils.isEmpty(shopBeans)) {
                    for (PurchaserShopBean shopBean : shopBeans) {
                        if (!TextUtils.equals(shopBean.getIsActive(), DISABLE) && !shopBean.isSelect()) {
                            select = false;
                            break CITY;
                        }
                    }
                }
            }
        }
        mImgAllCheck.setSelected(select);
    }

    /**
     * 判断是否选中了所有的门店
     */
    private void checkSelectAllShop() {
        boolean select = true;
        List<PurchaserShopBean> beans = mShopAdapter.getData();
        if (!CommonUtils.isEmpty(beans)) {
            for (PurchaserShopBean shopBean : beans) {
                if (!TextUtils.equals(shopBean.getShopName(), DeliveryAreaActivity.STRING_ALL)
                    && !TextUtils.equals(shopBean.getIsActive(), DISABLE)
                    && !shopBean.isSelect()) {
                    select = false;
                    break;
                }
            }
            PurchaserShopBean bean = beans.get(0);
            if (TextUtils.equals(bean.getShopName(), DeliveryAreaActivity.STRING_ALL)) {
                bean.setSelect(select);
                mShopAdapter.notifyItemChanged(0);
            }
        }
    }

    @OnClick({R.id.img_close, R.id.rl_allCheck, R.id.txt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.rl_allCheck:
                mImgAllCheck.setSelected(!mImgAllCheck.isSelected());
                selectAllShop(mImgAllCheck.isSelected());
                break;
            case R.id.txt_save:
                toSave();
                break;
            default:
                break;
        }
    }

    private void toSave() {
        DeliveryPurchaserBean bean = new DeliveryPurchaserBean();
        bean.setPurchaserID(mPurchaserId);
        bean.setPurchaserName(mPurchaserName);
        List<ShopMinimumBean> shopMinimumBeans = mCityAdapter.getData();
        List<String> shopIds = new ArrayList<>();
        if (!CommonUtils.isEmpty(shopMinimumBeans)) {
            for (ShopMinimumBean shopMinimumBean : shopMinimumBeans) {
                List<PurchaserShopBean> shopBeans = shopMinimumBean.getPurchaserShops();
                if (!CommonUtils.isEmpty(shopBeans)) {
                    for (PurchaserShopBean shopBean : shopBeans) {
                        if (!TextUtils.equals("全部", shopBean.getShopName())
                            && !TextUtils.equals(shopBean.getIsActive(), DISABLE) && shopBean.isSelect()) {
                            shopIds.add(shopBean.getShopID());
                        }
                    }
                }
            }
        }
        bean.setPurchaserShopList(shopIds);
        bean.setPurchaserShopNum(shopIds.size());
        EventBus.getDefault().post(bean);
        ARouter.getInstance().build(RouterConfig.DELIVERY_MINIMUM_DETAIL)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    @Override
    public String getPurchaserId() {
        return mPurchaserId;
    }

    @Override
    public String getSendAmountId() {
        return mSendAmountId;
    }

    @Override
    public List<String> getPurchaserShopList() {
        return mPurchaserShopList;
    }

    @Override
    public void showPurchaserShopList(List<ShopMinimumBean> list) {
        mCityAdapter.setNewData(list);
        if (!CommonUtils.isEmpty(list)) {
            selectCity(mCityAdapter, 0);
        }
        checkSelectAllShop();
        checkSelectAllCity();
    }

    class CityListAdapter extends BaseQuickAdapter<ShopMinimumBean, BaseViewHolder> {

        CityListAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShopMinimumBean item) {
            helper.setText(R.id.txt_categoryName, item.getShopProvince())
                .setBackgroundColor(R.id.txt_categoryName, item.isSelect() ? 0xFFF3F3F3 : 0xFFFFFFFF);
        }
    }

    class PurchaserShopListAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {
        PurchaserShopListAdapter() {
            super(R.layout.item_delivery_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            helper.setText(R.id.txt_name, item.getShopName())
                .setTextColor(R.id.txt_name, getTextColor(item));
            ImageView imgSelect = helper.getView(R.id.img_select);
            if (TextUtils.equals(item.getIsActive(), DISABLE)) {
                imgSelect.setSelected(true);
                imgSelect.setEnabled(false);
            } else {
                imgSelect.setEnabled(true);
                imgSelect.setSelected(item.isSelect());
            }
        }

        private int getTextColor(PurchaserShopBean item) {
            int color;
            if (TextUtils.equals(item.getIsActive(), DISABLE)) {
                color = Color.parseColor("#999999");
            } else {
                color = item.isSelect() ? Color.parseColor("#222222") : Color.parseColor("#666666");
            }
            return color;
        }
    }
}
