package com.hll_sc_app.app.deliverymanage.minimum.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.deliverymanage.minimum.purchaser.PurchaserMinimumActivity;
import com.hll_sc_app.app.deliverymanage.minimum.purchaser.shop.ShopMinimumActivity;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.delivery.AreaListBean;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.DeliveryPurchaserBean;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.GirdSimpleDecoration;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 起送金额详情
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
@Route(path = RouterConfig.DELIVERY_MINIMUM_DETAIL, extras = Constant.LOGIN_EXTRA)
public class DeliveryMinimumDetailActivity extends BaseLoadActivity implements DeliveryMinimumDetailContract.IDeliveryMinimumDetailView {
    public static final String TYPE_AREA = "0";
    public static final Pattern PRICE = Pattern.compile("^[0-9]{1,6}([.]{1}[0-9]{0,2})?$");
    @BindView(R.id.recyclerView_area)
    RecyclerView mRecyclerViewArea;
    @BindView(R.id.recyclerView_purchaser)
    RecyclerView mRecyclerViewPurchaser;
    @Autowired(name = "parcelable")
    DeliveryMinimumBean mBean;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.edt_divideName)
    EditText mEdtDivideName;
    @BindView(R.id.edt_sendPrice)
    EditText mEdtSendPrice;
    @BindView(R.id.txt_settings)
    TextView mTxtSettings;
    @BindView(R.id.txt_tips)
    TextView mTxtTips;
    @BindView(R.id.txt_purchaser)
    TextView mTxtPurchaser;

    private AreaListAdapter mAreaAdapter;
    private SingleSelectionDialog mDialog;
    private PurchaserListAdapter mPurchaserAdapter;
    private DeliveryMinimumDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_minimum_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryMinimumDetailPresenter.newInstance();
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
        mTxtTitle.setText(isAdd() ? "新增起送金额" : "编辑起送金额");
        if (mBean != null) {
            mEdtDivideName.setText(mBean.getDivideName());
            mEdtSendPrice.setText(CommonUtils.formatNumber(mBean.getSendPrice()));
            mTxtSettings.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            mTxtSettings.setTag(mBean.getSettings());
            mTxtSettings.setText(isAreaType() ? "根据地区设置" : "根据采购商设置");
        } else {
            // 默认为根据地区设置
            mTxtSettings.setTag(TYPE_AREA);
            mTxtSettings.setText("根据地区设置");
        }
        showType();
        mEdtSendPrice.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
            if (s.toString().startsWith(".")) {
                s.insert(0, TYPE_AREA);
            }
            if (!PRICE.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                showToast("起送金额7位数以内");
            }
        });
        mAreaAdapter = new AreaListAdapter();
        mAreaAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProvinceListBean bean = (ProvinceListBean) adapter.getItem(position);
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.DELIVERY_AREA, bean);
            }
        });
        mRecyclerViewArea.addItemDecoration(new GirdSimpleDecoration(4));
        mRecyclerViewArea.setAdapter(mAreaAdapter);

        mPurchaserAdapter = new PurchaserListAdapter();
        mPurchaserAdapter.setOnItemClickListener((adapter, view, position) -> {
            DeliveryPurchaserBean bean = (DeliveryPurchaserBean) adapter.getItem(position);
            if (bean != null) {
                ShopMinimumActivity.start(bean.getPurchaserID(), bean.getPurchaserName(), mBean != null ?
                        mBean.getSendAmountID() : "",
                    bean.getPurchaserShopList());
            }
        });
        mRecyclerViewPurchaser.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mRecyclerViewPurchaser.setAdapter(mPurchaserAdapter);
    }

    private boolean isAdd() {
        return mBean == null;
    }

    /**
     * 根据选择的不同类型展示不同的 UI
     */
    private void showType() {
        if (isAreaType()) {
            // 地区选择
            mTxtTips.setText("可选范围");
            mTxtPurchaser.setVisibility(View.GONE);
            mRecyclerViewArea.setVisibility(View.VISIBLE);
            mRecyclerViewPurchaser.setVisibility(View.GONE);
        } else {
            // 采购商选择
            mTxtTips.setText("注意：针对选择的采购商设置起送金额后，不受地区起送金额限制");
            mTxtPurchaser.setVisibility(View.VISIBLE);
            mRecyclerViewArea.setVisibility(View.GONE);
            mRecyclerViewPurchaser.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onEvent(ProvinceListBean bean) {
        // 根据地区设置
        if (bean == null) {
            return;
        }
        List<ProvinceListBean> beans = mAreaAdapter.getData();
        if (!CommonUtils.isEmpty(beans)) {
            for (int position = 0; position < beans.size(); position++) {
                ProvinceListBean provinceListBean = beans.get(position);
                if (TextUtils.equals(provinceListBean.getProvinceCode(), bean.getProvinceCode())) {
                    provinceListBean.setCityList(bean.getCityList());
                    provinceListBean.setOptionalNum(bean.getOptionalNum());
                    provinceListBean.setSelectedNum(bean.getSelectedNum());
                    provinceListBean.setSelect(bean.getSelectedNum() != 0);
                    mAreaAdapter.notifyItemChanged(position);
                    break;
                }
            }
        }
    }

    @Subscribe
    public void onEvent(DeliveryPurchaserBean bean) {
        // 根据采购商设置
        if (bean == null) {
            return;
        }
        List<DeliveryPurchaserBean> purchaserBeans = mPurchaserAdapter.getData();
        if (!CommonUtils.isEmpty(purchaserBeans)) {
            boolean find = false;
            for (int i = 0, size = purchaserBeans.size(); i < size; i++) {
                DeliveryPurchaserBean purchaserBean = purchaserBeans.get(i);
                if (TextUtils.equals(purchaserBean.getPurchaserID(), bean.getPurchaserID())) {
                    find = true;
                    purchaserBean.setPurchaserShopNum(bean.getPurchaserShopNum());
                    purchaserBean.setPurchaserShopList(bean.getPurchaserShopList());
                    if (bean.getPurchaserShopNum() != 0) {
                        mPurchaserAdapter.notifyItemChanged(i);
                    } else {
                        mPurchaserAdapter.remove(i);
                    }
                    break;
                }
            }
            if (!find && bean.getPurchaserShopNum() != 0) {
                mPurchaserAdapter.addData(bean);
            }
        } else if (bean.getPurchaserShopNum() != 0) {
            List<DeliveryPurchaserBean> list = new ArrayList<>();
            list.add(bean);
            mPurchaserAdapter.setNewData(list);
        }
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.ll_settings, R.id.txt_settings, R.id.txt_purchaser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            case R.id.ll_settings:
            case R.id.txt_settings:
                if (isAdd()) {
                    showSettingDialog();
                }
                break;
            case R.id.txt_purchaser:
                // 选择采购商
                PurchaserMinimumActivity.start(mBean != null ? mBean.getSendAmountID() : "",
                    (ArrayList<DeliveryPurchaserBean>) mPurchaserAdapter.getData());
                break;
            default:
                break;
        }
    }

    private void toSave() {
        if (TextUtils.isEmpty(mEdtDivideName.getText().toString().trim())) {
            showToast("分组名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(mEdtSendPrice.getText().toString().trim())) {
            showToast("起送金额不能为空");
            return;
        }
        if (mTxtSettings.getTag() == null) {
            showToast("选择类型不能为空");
            return;
        }
        DeliveryMinimumReq req = new DeliveryMinimumReq();
        if (mTxtSettings.getTag() != null) {
            req.setSettings((String) mTxtSettings.getTag());
        }
        req.setAmount(mEdtSendPrice.getText().toString().trim());
        req.setName(mEdtDivideName.getText().toString().trim());

        if (isAreaType()) {
            req.setCodeList(getCodeList());
        } else {
            if (CommonUtils.isEmpty(mPurchaserAdapter.getData())) {
                showToast("请选择合作采购商");
                return;
            }
            req.setPurchaserList(mPurchaserAdapter.getData());
        }
        if (isAdd()) {
            req.setSupplyID(UserConfig.getGroupID());
            req.setType("1");
        } else {
            req.setSupplyID(mBean.getSupplyID());
            req.setType("2");
            req.setSendAmountID(mBean.getSendAmountID());
            req.setSupplyShopID(mBean.getSupplyShopID());
        }
        mPresenter.editDeliveryMinimum(req);
    }

    private void showSettingDialog() {
        if (mDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("根据地区设置", TYPE_AREA));
            list.add(new NameValue("根据采购商设置", "1"));
            mDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .setTitleText("选择类型")
                .refreshList(list)
                .select(list.get(0))
                .setOnSelectListener(nameValue -> {
                    if (!TextUtils.equals(mTxtSettings.getText(), nameValue.getName())) {
                        mTxtSettings.setText(nameValue.getName());
                        mTxtSettings.setTag(nameValue.getValue());
                        showType();
                    }
                })
                .create();
        }
        mDialog.show();
    }

    /**
     * 获取已选的区编码列表
     *
     * @return 已选的区编码列表
     */
    private List<String> getCodeList() {
        List<String> codeList = new ArrayList<>();
        List<ProvinceListBean> provinceListBeans = mAreaAdapter.getData();
        if (!CommonUtils.isEmpty(provinceListBeans) && isAreaType()) {
            for (ProvinceListBean bean : provinceListBeans) {
                // 省
                if (bean.isSelect()) {
                    // 市
                    List<CityListBean> cityListBeans = bean.getCityList();
                    if (!CommonUtils.isEmpty(cityListBeans)) {
                        for (CityListBean cityListBean : cityListBeans) {
                            // 区
                            List<AreaListBean> areaListBeans = cityListBean.getAreaList();
                            if (!CommonUtils.isEmpty(areaListBeans)) {
                                for (AreaListBean areaListBean : areaListBeans) {
                                    if (TextUtils.equals("3", areaListBean.getFlag())) {
                                        codeList.add(areaListBean.getAreaCode());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return codeList;
    }

    @Override
    public void showAreaList(List<ProvinceListBean> list) {
        mAreaAdapter.setNewData(list);
    }

    @Override
    public void showPurchaserList(List<DeliveryPurchaserBean> list) {
        mPurchaserAdapter.setNewData(list);
    }

    @Override
    public DeliveryMinimumBean getDeliveryMinimumBean() {
        return mBean;
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void editSuccess() {
        if (isAdd()) {
            showToast("新增起送金额成功");
        } else {
            showToast("编辑起送金额成功");
        }
        ARouter.getInstance().build(RouterConfig.DELIVERY_MINIMUM)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    @Override
    public boolean isAreaType() {
        boolean flag = false;
        if (mTxtSettings.getTag() != null) {
            flag = TextUtils.equals(TYPE_AREA, (CharSequence) mTxtSettings.getTag());
        }
        return flag;
    }

    class PurchaserListAdapter extends BaseQuickAdapter<DeliveryPurchaserBean, BaseViewHolder> {
        PurchaserListAdapter() {
            super(R.layout.item_delivery_minimum_detail_purchaser);
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryPurchaserBean item) {
            helper.setText(R.id.txt_purchaserName, item.getPurchaserName())
                .setText(R.id.txt_purchaserShopNum, "已选" + item.getPurchaserShopNum() + "个门店");
        }
    }

    class AreaListAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {
        AreaListAdapter() {
            super(R.layout.item_delivery_minimum_detail_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProvinceListBean item) {
            helper.setText(R.id.txt_provinceName, item.getProvinceName())
                .setText(R.id.txt_selectedNum, "已选" + item.getSelectedNum())
                .setText(R.id.txt_optionalNum, "可选" + item.getOptionalNum())
                .getView(R.id.content).setSelected(item.isSelect());
        }
    }
}
