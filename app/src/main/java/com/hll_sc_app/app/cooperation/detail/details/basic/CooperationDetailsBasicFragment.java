package com.hll_sc_app.app.cooperation.detail.details.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.app.cooperation.detail.details.CooperationButtonView;
import com.hll_sc_app.app.cooperation.detail.shopadd.CooperationSelectShopActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.DeliveryPeriodBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 合作采购商详情-详细资料-基本信息
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public class CooperationDetailsBasicFragment extends BaseCooperationDetailsFragment implements CooperationDetailsBasicContract.IGoodsRelevanceListView {
    Unbinder unbinder;
    @BindView(R.id.img_logoUrl)
    GlideImageView mImgLogoUrl;
    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_resourceType)
    TextView mTxtResourceType;
    @BindView(R.id.txt_groupCity)
    TextView mTxtGroupCity;
    @BindView(R.id.txt_groupAddress)
    TextView mTxtGroupAddress;
    @BindView(R.id.txt_linkman)
    TextView mTxtLinkman;
    @BindView(R.id.txt_mobile)
    TextView mTxtMobile;
    @BindView(R.id.txt_fax)
    TextView mTxtFax;
    @BindView(R.id.txt_groupMail)
    TextView mTxtGroupMail;
    @BindView(R.id.txt_defaultSettlementWay)
    TextView mTxtDefaultSettlementWay;
    @BindView(R.id.txt_maintainLevel)
    TextView mTxtMaintainLevel;
    @BindView(R.id.txt_defaultDeliveryWay)
    TextView mTxtDefaultDeliveryWay;
    @BindView(R.id.txt_customerLevel)
    TextView mTxtCustomerLevel;
    @BindView(R.id.txt_agreeTime)
    TextView mTxtAgreeTime;
    @BindView(R.id.txt_deliveryPeriod)
    TextView mTxtDeliveryPeriod;
    @BindView(R.id.txt_verification)
    TextView mTxtVerification;
    @BindView(R.id.txt_reply)
    TextView mTxtReply;
    @BindView(R.id.txt_shopsNum)
    TextView mTxtShopsNum;
    @BindView(R.id.txt_verification_title)
    TextView mTxtVerificationTitle;
    @BindView(R.id.buttonView)
    CooperationButtonView mButtonView;

    private CooperationPurchaserDetail mDetail;
    private CooperationDetailsBasicPresenter mPresenter;

    public static CooperationDetailsBasicFragment newInstance(CooperationPurchaserDetail bean) {
        Bundle args = new Bundle();
        args.putParcelable("parcelable", bean);
        CooperationDetailsBasicFragment fragment = new CooperationDetailsBasicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = CooperationDetailsBasicPresenter.newInstance();
        mPresenter.register(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDetail = bundle.getParcelable("parcelable");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cooperation_details_basic, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showView();
        return rootView;
    }

    private void showView() {
        mButtonView.showButton(mDetail.getActionType(), mDetail.getStatus());
        mButtonView.setListener(this, mDetail);
        mImgLogoUrl.setImageURL(mDetail.getLogoUrl());
        mTxtName.setText(mDetail.getName());
        mTxtResourceType.setText(getResourceType(mDetail.getResourceType()));

        mTxtGroupCity.setText(String.format("%s-%s", mDetail.getGroupCity(), mDetail.getGroupDistrict()));
        mTxtGroupAddress.setText(checkNull(mDetail.getGroupAddress()));
        mTxtLinkman.setText(checkNull(mDetail.getLinkman()));
        mTxtMobile.setText(checkNull(mDetail.getMobile()));
        mTxtFax.setText(checkNull(mDetail.getFax()));
        mTxtGroupMail.setText(checkNull(mDetail.getGroupMail()));

        mTxtDefaultSettlementWay.setText(CooperationDetailActivity.getSettlementWay(mDetail.getDefaultSettlementWay()));
        mTxtMaintainLevel.setText(getMaintainLevel(mDetail.getMaintainLevel()));
        mTxtDefaultDeliveryWay.setText(CooperationDetailActivity.getDeliveryWay(mDetail.getDefaultDeliveryWay()));
        mTxtCustomerLevel.setText(getCustomerLevel(mDetail.getCustomerLevel()));

        Date date = CalendarUtils.parse(mDetail.getAgreeTime(), CalendarUtils.FORMAT_HH_MM_SS);
        String dateString = null;
        if (date != null) {
            dateString = CalendarUtils.format(date, "yyyy/MM/dd");
        }
        mTxtAgreeTime.setText(checkNull(dateString));
        mTxtDeliveryPeriod.setText(checkNull(mDetail.getDefaultDeliveryPeriod()));
        mTxtVerification.setText(checkNull(mDetail.getVerification()));
        mTxtReply.setText(checkNull(mDetail.getReply()));
        mTxtShopsNum.setText(String.format("需合作%s个门店", CommonUtils.isEmpty(mDetail.getShopDetailList()) ? "0" :
            mDetail.getShopDetailList().size()));
        checkItem();
    }

    private String getResourceType(String type) {
        String resourceType = "无";
        if (TextUtils.equals(type, "0")) {
            resourceType = "二十二城";
        } else if (TextUtils.equals(type, "1")) {
            resourceType = "哗啦啦供应链";
        } else if (TextUtils.equals(type, "2")) {
            resourceType = "天财供应链";
        }
        return resourceType;
    }

    private String checkNull(String s) {
        if (TextUtils.isEmpty(s)) {
            return "暂无";
        }
        return s;
    }

    private String getMaintainLevel(String level) {
        String resourceType = "无";
        if (TextUtils.equals(level, "0")) {
            resourceType = "门店级维护";
        } else if (TextUtils.equals(level, "1")) {
            resourceType = "集团级别维护";
        }
        return resourceType;
    }

    private String getCustomerLevel(String customerLevel) {
        String result = "无";
        if (TextUtils.equals(customerLevel, "0")) {
            result = "普通客户";
        } else if (TextUtils.equals(customerLevel, "1")) {
            result = "VIP客户";
        }
        return result;
    }

    /**
     * 不同情况下显示的 Item 不同
     */
    private void checkItem() {
        String status = mDetail.getStatus();
        String actionType = mDetail.getActionType();
        switch (status) {
            case "0":
                findView(R.id.ll_defaultDeliveryWay).setVisibility(View.GONE);
                findView(R.id.ll_maintainLevel).setVisibility(View.GONE);
                findView(R.id.ll_customerLevel).setVisibility(View.GONE);
                findView(R.id.ll_agreeTime).setVisibility(View.GONE);
                findView(R.id.ll_deliveryPeriod).setVisibility(View.GONE);
                findView(R.id.ll_reply).setVisibility(View.GONE);
                if (TextUtils.equals(actionType, CooperationButtonView.TYPE_MY_APPLICATION)) {
                    // 我发出的邀请，等待别人同意
                    mTxtVerificationTitle.setText("我发送的");
                    findView(R.id.ll_shopsNum).setVisibility(View.GONE);
                    mTxtDefaultSettlementWay.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    setOnClickListener(R.id.ll_defaultSettlementWay, null);
                } else if (TextUtils.equals(actionType, CooperationButtonView.TYPE_COOPERATION_APPLICATION)) {
                    // 别人发出的申请，等待我同意
                    mTxtVerificationTitle.setText("采购商说");
                    findView(R.id.ll_defaultSettlementWay).setVisibility(View.GONE);
                }
                break;
            case "1":
                findView(R.id.ll_maintainLevel).setVisibility(View.GONE);
                findView(R.id.ll_defaultDeliveryWay).setVisibility(View.GONE);
                findView(R.id.ll_customerLevel).setVisibility(View.GONE);
                findView(R.id.ll_agreeTime).setVisibility(View.GONE);
                findView(R.id.ll_deliveryPeriod).setVisibility(View.GONE);
                findView(R.id.ll_shopsNum).setVisibility(View.GONE);

                if (TextUtils.equals(actionType, CooperationButtonView.TYPE_MY_APPLICATION)) {
                    // 我发出的邀请，别人拒绝
                    mTxtDefaultSettlementWay.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    setOnClickListener(R.id.ll_defaultSettlementWay, null);
                    mTxtVerificationTitle.setText("我发送的");
                } else if (TextUtils.equals(actionType, CooperationButtonView.TYPE_COOPERATION_APPLICATION)) {
                    // 别人发出的申请，我拒绝
                    findView(R.id.ll_defaultSettlementWay).setVisibility(View.GONE);
                    mTxtVerificationTitle.setText("采购商说");
                }
                break;
            case "2":
                findView(R.id.ll_verification).setVisibility(View.GONE);
                findView(R.id.ll_reply).setVisibility(View.GONE);
                findView(R.id.ll_shopsNum).setVisibility(View.GONE);
                break;
            case "3":
                // 从未添加过
                findView(R.id.ll_defaultSettlementWay).setVisibility(View.GONE);
                findView(R.id.ll_maintainLevel).setVisibility(View.GONE);
                findView(R.id.ll_defaultDeliveryWay).setVisibility(View.GONE);
                findView(R.id.ll_customerLevel).setVisibility(View.GONE);
                findView(R.id.ll_agreeTime).setVisibility(View.GONE);
                findView(R.id.ll_deliveryPeriod).setVisibility(View.GONE);
                findView(R.id.ll_verification).setVisibility(View.GONE);
                findView(R.id.ll_reply).setVisibility(View.GONE);
                findView(R.id.ll_shopsNum).setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.ll_defaultSettlementWay, R.id.ll_maintainLevel, R.id.ll_defaultDeliveryWay, R.id.ll_deliveryPeriod})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_defaultSettlementWay:
                toSelect(CooperationSelectShopActivity.TYPE_SETTLEMENT);
                break;
            case R.id.ll_defaultDeliveryWay:
                toSelect(CooperationSelectShopActivity.TYPE_DELIVERY);
                break;
            case R.id.ll_deliveryPeriod:
                mPresenter.queryDeliveryPeriod();
                break;
            case R.id.ll_maintainLevel:
                break;
            default:
                break;
        }
    }

    private void toSelect(String actionType) {
        ShopSettlementReq req = new ShopSettlementReq();
        req.setFrom(FROM_COOPERATION_DETAILS_PROPERTY);
        req.setActionType(actionType);
        req.setGroupID(UserConfig.getGroupID());
        req.setPurchaserID(mDetail.getPurchaserID());
        switch (actionType) {
            case CooperationSelectShopActivity.TYPE_SETTLEMENT:
                // 修改结算方式
                req.setSettlementWay(mDetail.getDefaultSettlementWay());
                req.setAccountPeriodType(mDetail.getDefaultAccountPeriodType());
                req.setAccountPeriod(mDetail.getDefaultAccountPeriod());
                req.setSettleDate(mDetail.getDefaultSettleDate());
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, req);
                break;
            case CooperationSelectShopActivity.TYPE_DELIVERY:
                // 修改配送方式
                req.setDeliveryWay(mDetail.getDefaultDeliveryWay());
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DELIVERY, req);
                break;
            case CooperationSelectShopActivity.TYPE_DELIVERY_PERIOD:
                // 修改到货时间
                req.setDeliveryPeriod(mTxtDeliveryPeriod.getText().toString());
                showChangeRangeWindow(req);
                break;
            default:
                break;
        }
    }

    private void showChangeRangeWindow(ShopSettlementReq req) {
        SuccessDialog.newBuilder(requireActivity())
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("是否统一修改配送时间")
            .setMessage("是否要修改集团下所有门店的配送时\n间，确认修改后将统一所有门店配送时间")
            .setButton((dialog, item) -> {
                dialog.dismiss();
                if (item == 1) {
                    req.setChangeAllShops("0");
                } else {
                    req.setChangeAllShops("1");
                }
                mPresenter.editShopDeliveryPeriod(req);
            }, "确认修改", "暂不修改").create().show();
    }

    @Override
    public void showDeliveryPeriodWindow(List<DeliveryPeriodBean> list) {
        if (CommonUtils.isEmpty(list)) {
            showToast("到货时间列表查询为空");
            return;
        }
        SingleSelectionDialog.newBuilder(requireActivity(), (SingleSelectionDialog.WrapperName<DeliveryPeriodBean>) bean
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
