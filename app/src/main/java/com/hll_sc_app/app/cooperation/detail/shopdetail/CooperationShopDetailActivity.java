package com.hll_sc_app.app.cooperation.detail.shopdetail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.app.cooperation.detail.shopadd.CooperationSelectShopActivity;
import com.hll_sc_app.app.cooperation.detail.shopsaleman.CooperationShopSalesActivity;
import com.hll_sc_app.app.cooperation.detail.shopsettlement.CooperationShopSettlementActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.bean.event.CooperationEvent;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情-门店详情
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL, extras = Constant.LOGIN_EXTRA)
public class CooperationShopDetailActivity extends BaseLoadActivity implements CooperationShopDetailContract.ICooperationShopDetailView {
    public static final String STATUS_WAIT = "0";
    public static final String STATUS_AGREE = "2";
    @Autowired(name = "parcelable", required = true)
    PurchaserShopBean mShopBean;
    @BindView(R.id.img_imagePath)
    GlideImageView mImgImagePath;
    @BindView(R.id.txt_shopName)
    TextView mTxtShopName;
    @BindView(R.id.txt_shopAdmin)
    TextView mTxtShopAdmin;
    @BindView(R.id.txt_shopPhone)
    TextView mTxtShopPhone;
    @BindView(R.id.txt_shopProvince)
    TextView mTxtShopCity;
    @BindView(R.id.txt_shopAddress)
    TextView mTxtShopAddress;
    @BindView(R.id.txt_settlementWay)
    TextView mTxtSettlementWay;
    @BindView(R.id.txt_salesRepresentativeName)
    TextView mTxtSalesRepresentativeName;
    @BindView(R.id.txt_driverName)
    TextView mTxtDriverName;
    @BindView(R.id.txt_deliveryWay)
    TextView mTxtDeliveryWay;
    @BindView(R.id.txt_cooperationSource)
    TextView mTxtCooperationSource;
    @BindView(R.id.txt_agreeTime)
    TextView mTxtAgreeTime;
    @BindView(R.id.txt_deliveryPeriod)
    TextView mTxtDeliveryPeriod;
    @BindView(R.id.txt_del)
    TextView mTxtDel;
    @BindView(R.id.txt_agree)
    TextView mTxtAgree;
    @BindView(R.id.txt_reject)
    TextView mTxtReject;
    @BindView(R.id.ll_status0)
    LinearLayout mLlStatus0;
    private CooperationShopDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = CooperationShopDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        showView();
    }

    private void showView() {
        mImgImagePath.setImageURL(mShopBean.getImagePath());
        mTxtShopName.setText(mShopBean.getShopName());
        mTxtShopAdmin.setText(mShopBean.getShopAdmin());
        mTxtShopPhone.setText(mShopBean.getShopPhone());
        mTxtShopCity.setText(String.format("%s-%s", mShopBean.getShopCity(), mShopBean.getShopDistrict()));
        mTxtShopAddress.setText(mShopBean.getShopAddress());
        mTxtSettlementWay.setText(CooperationDetailActivity.getSettlementWay(mShopBean.getSettlementWay()));
        mTxtSalesRepresentativeName.setText(mShopBean.getSalesRepresentativeName());
        mTxtDriverName.setText(mShopBean.getDriverName());
        mTxtDeliveryWay.setText(CooperationDetailActivity.getDeliveryWay(mShopBean.getDeliveryWay()));
        Date date = CalendarUtils.parse(mShopBean.getAgreeTime(), CalendarUtils.FORMAT_HH_MM_SS);
        if (date != null) {
            mTxtAgreeTime.setText(CalendarUtils.format(date, "yyyy/MM/dd"));
        }
        mTxtDeliveryPeriod.setText(mShopBean.getDeliveryPeriod());
        if (TextUtils.equals(mShopBean.getStatus(), STATUS_WAIT)) {
            mLlStatus0.setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(mShopBean.getStatus(), STATUS_AGREE)) {
            mTxtDel.setVisibility(View.VISIBLE);
        }
        mTxtCooperationSource.setText(CommonUtils.isEmpty(mShopBean.getCooperationSource()) ? "暂无" : "点击查看合作方式");

        //已停止合作的 不显示任何操作按钮 及不能操作页面
        if (mShopBean.getCooperationActive() == 1) {
            mTxtDel.setVisibility(View.GONE);
            mLlStatus0.setVisibility(View.GONE);
            mTxtSettlementWay.setCompoundDrawables(null, null, null, null);
            mTxtSalesRepresentativeName.setCompoundDrawables(null, null, null, null);
            mTxtDriverName.setCompoundDrawables(null, null, null, null);
            mTxtDeliveryWay.setCompoundDrawables(null, null, null, null);
            mTxtCooperationSource.setCompoundDrawables(null, null, null, null);
            mTxtDeliveryPeriod.setCompoundDrawables(null, null, null, null);
        } else if (UserConfig.crm()) {
            adjustCrm();
        }
    }

    private void adjustCrm() {
        mTxtDel.setVisibility(View.GONE);
        mLlStatus0.setVisibility(View.GONE);
        findViewById(R.id.ll_settlementWay).setVisibility(View.GONE);
        findViewById(R.id.ll_salesRepresentativeName).setVisibility(View.GONE);
        findViewById(R.id.ll_driverName).setVisibility(View.GONE);
        findViewById(R.id.ll_deliveryWay).setVisibility(View.GONE);
        findViewById(R.id.ll_cooperationSource).setVisibility(View.GONE);
        mTxtDeliveryPeriod.setCompoundDrawables(null, null, null, null);
        findViewById(R.id.ll_deliveryPeriod).setClickable(false);
    }

    @OnClick({R.id.img_close, R.id.ll_settlementWay, R.id.ll_salesRepresentativeName,
        R.id.ll_driverName, R.id.ll_deliveryWay, R.id.ll_cooperationSource, R.id.ll_deliveryPeriod
        , R.id.txt_del, R.id.txt_agree, R.id.txt_reject})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
            return;
        }
        //已停止状态 不作任何操作
        if (mShopBean.getCooperationActive() == 1) {
            return;
        }
        switch (view.getId()) {
            case R.id.txt_agree:
                mPresenter.editCooperationShop("agree");
                break;
            case R.id.txt_reject:
                mPresenter.editCooperationShop("refuse");
                break;
            case R.id.txt_del:
                mPresenter.delCooperationShop();
                break;
            case R.id.ll_settlementWay:
                toSelect(CooperationSelectShopActivity.TYPE_SETTLEMENT);
                break;
            case R.id.ll_deliveryWay:
                toSelect(CooperationSelectShopActivity.TYPE_DELIVERY);
                break;
            case R.id.ll_salesRepresentativeName:
                toSelect(CooperationSelectShopActivity.TYPE_SALESMAN);
                break;
            case R.id.ll_driverName:
                toSelect(CooperationSelectShopActivity.TYPE_DRIVER);
                break;
            case R.id.ll_deliveryPeriod:
                mPresenter.queryDeliveryPeriod();
                break;
            case R.id.ll_cooperationSource:
                if (!CommonUtils.isEmpty(mShopBean.getCooperationSource())) {
                    RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL_SOURCE,
                        new ArrayList<>(mShopBean.getCooperationSource()));
                }
                break;
            default:
                break;
        }
    }

    private void toSelect(String actionType) {
        ShopSettlementReq req = new ShopSettlementReq();
        req.setActionType(actionType);
        req.setChangeAllShops("0");
        req.setGroupID(UserConfig.getGroupID());
        req.setPurchaserID(mShopBean.getPurchaserID());
        switch (actionType) {
            case CooperationSelectShopActivity.TYPE_SETTLEMENT:
                req.setShopIds(Collections.singletonList(mShopBean.getShopID()));
                req.setSettlementWay(mShopBean.getSettlementWay());
                req.setAccountPeriodType(mShopBean.getAccountPeriodType());
                req.setAccountPeriod(mShopBean.getAccountPeriod());
                req.setSettleDate(mShopBean.getSettleDate());
                CooperationShopSettlementActivity.start(req, mShopBean);
                break;
            case CooperationSelectShopActivity.TYPE_DELIVERY:
                req.setShopIds(Collections.singletonList(mShopBean.getShopID()));
                req.setDeliveryWay(mShopBean.getDeliveryWay());
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DELIVERY, req);
                break;
            case CooperationSelectShopActivity.TYPE_SALESMAN:
                req.setShopIDs(mShopBean.getShopID());
                req.setEmployeeID(mShopBean.getSalesRepresentativeID());
                CooperationShopSalesActivity.start(this, req);
                break;
            case CooperationSelectShopActivity.TYPE_DRIVER:
                req.setShopIDs(mShopBean.getShopID());
                req.setEmployeeID(mShopBean.getDriverID());
                CooperationShopSalesActivity.start(this, req);
                break;
            case CooperationSelectShopActivity.TYPE_DELIVERY_PERIOD:
                req.setShopIds(Collections.singletonList(mShopBean.getShopID()));
                req.setDeliveryPeriod(mTxtDeliveryPeriod.getText().toString());
                mPresenter.editShopDeliveryPeriod(req);
                break;
            default:
                break;
        }
    }

    @Override
    public void editSuccess() {
        EventBus.getDefault().post(new CooperationEvent(CooperationEvent.SHOP_CHANGED));
        finish();
    }

    @Override
    public PurchaserShopBean getShopBean() {
        return mShopBean;
    }

    @Override
    public void showDeliveryPeriodWindow(List<DeliveryPeriodBean> list) {
        if (CommonUtils.isEmpty(list)) {
            showToast("到货时间列表查询为空");
            return;
        }
        SingleSelectionDialog.newBuilder(this, (SingleSelectionDialog.WrapperName<DeliveryPeriodBean>) bean
            -> bean.getArrivalStartTime() + "-" + bean.getArrivalEndTime())
            .setTitleText("到货时间选择")
            .setOnSelectListener(bean -> {
                mTxtDeliveryPeriod.setText(String.format("%s-%s", bean.getArrivalStartTime(),
                    bean.getArrivalEndTime()));
                toSelect(CooperationSelectShopActivity.TYPE_DELIVERY_PERIOD);
            })
            .refreshList(list)
            .create().show();
    }
}
