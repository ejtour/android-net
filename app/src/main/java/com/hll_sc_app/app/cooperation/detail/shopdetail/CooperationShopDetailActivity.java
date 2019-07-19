package com.hll_sc_app.app.cooperation.detail.shopdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

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
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
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
    }

    @OnClick({R.id.img_close, R.id.ll_settlementWay, R.id.ll_salesRepresentativeName,
        R.id.ll_driverName, R.id.ll_deliveryWay, R.id.ll_cooperationSource, R.id.ll__deliveryPeriod
        , R.id.txt_del, R.id.txt_agree, R.id.txt_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void editSuccess() {
        showToast("修改成功");
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_DETAIL)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }
}
