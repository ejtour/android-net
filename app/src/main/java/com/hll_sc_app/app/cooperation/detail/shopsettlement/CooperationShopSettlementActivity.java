package com.hll_sc_app.app.cooperation.detail.shopsettlement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.details.BaseCooperationDetailsFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.AccountPayView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

/**
 * 合作采购商详情-选择结算方式
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, extras = Constant.LOGIN_EXTRA)
public class CooperationShopSettlementActivity extends BaseLoadActivity implements CooperationShopSettlementContract.ICooperationShopSettlementView {
    public static final String PAY_CASH = "1";
    public static final String PAY_ACCOUNT = "2";
    public static final String PAY_ONLINE = "3";
    public static final String PAY_CARD = "4";
    public static final String TERM_WEEK = "1";
    public static final String TERM_MONTH = "2";
    public static final String[] STRINGS_WEEK = {"每周日", "每周一", "每周二", "每周三", "每周四", "每周五", "每周六"};
    @Autowired(name = "parcelable", required = true)
    ShopSettlementReq mReq;
    @Autowired(name = "detail")
    CooperationPurchaserDetail mPurchaserDetail;
    @Autowired(name = "shopBean")
    PurchaserShopBean mShopDetail;
    @BindView(R.id.css_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.css_settle_list)
    RecyclerView mListView;
    @BindView(R.id.css_pay_account)
    AccountPayView mPayAccount;
    @BindView(R.id.css_deliver)
    TextView mDeliver;
    @BindView(R.id.css_inspect)
    TextView mInspect;
    @BindView(R.id.css_level)
    TextView mLevel;
    @BindView(R.id.css_date_range)
    TextView mDateRange;
    @BindView(R.id.css_settle_body)
    ConstraintLayout mSettleBody;
    @BindView(R.id.css_content_body)
    ConstraintLayout mContentBody;
    @BindView(R.id.css_remain_num)
    TextView mRemainNum;
    @BindView(R.id.css_verify_group)
    Group mVerifyGroup;
    @BindView(R.id.css_verify_edit)
    EditText mVerifyEdit;
    @BindView(R.id.css_settle_label)
    TextView mRequestLabel;
    private Adapter mAdapter;

    private CooperationShopSettlementPresenter mPresenter;

    public static void start(ShopSettlementReq req, CooperationPurchaserDetail detail) {
        ARouter.getInstance()
                .build(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT)
                .withParcelable("parcelable", req)
                .withParcelable("detail", detail)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    public static void start(ShopSettlementReq req, PurchaserShopBean shopBean) {
        ARouter.getInstance()
                .build(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT)
                .withParcelable("parcelable", req)
                .withParcelable("shopBean", shopBean)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_settlement);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = CooperationShopSettlementPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
        initData();
    }

    private void initData() {
        if (TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_ADD) ||
                TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_REPEAT)) {
            mPresenter.queryGroupParameterInSetting(mReq.getPurchaserID());
        }

        //集团进入
        if (mPurchaserDetail != null) {
            boolean isAddAgain = mPurchaserDetail.getCooperationActive() == 1;
            boolean isWaitAgree = TextUtils.equals(mPurchaserDetail.getStatus(), "0");
            boolean isNewAdd = TextUtils.equals(mPurchaserDetail.getStatus(), "3");
            boolean isAdd = isAddAgain || isNewAdd || isWaitAgree;
            mContentBody.setVisibility(isAdd ? View.VISIBLE : View.GONE);
            if (isAdd) { // 只有新增的时候才显示，默认提供一个值
                mReq.setInspector("2");
                mTitleBar.setHeaderTitle("合作设置");
            }
        }
    }

    private void initView() {
        SpannableString text = new SpannableString("*");
        text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mRequestLabel.append(text);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f)));
        mListView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new Adapter();
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            UIUtils.hideActivitySoftKeyboard(this);
            mPayAccount.setVisibility(mAdapter.select(mAdapter.getItem(position)) ? View.VISIBLE : View.GONE);
        });
    }

    @OnClick(R.id.css_pay_mgr)
    void toPayManager() {
        RouterUtil.goToActivity(RouterConfig.PAY_MANAGE);
    }

    @OnClick(R.id.css_deliver_mgr)
    void toDeliverManger() {
        RouterUtil.goToActivity(RouterConfig.DELIVERY_TYPE_SET);
    }

    @OnClick(R.id.css_confirm)
    void toConfirm() {
        Set<String> set = mAdapter.getSelectTypes();
        if (CommonUtils.isEmpty(set)) {
            showToast("请选择结算方式");
            return;
        }
        mReq.setSettlementWay(TextUtils.join(",", set));
        if (mPayAccount.getVisibility() == View.VISIBLE) {
            mReq.setAccountPeriodType(mPayAccount.getType());
            mReq.setAccountPeriod(mPayAccount.getPeriod());
            mReq.setSettleDate(mPayAccount.getSettleDate());
        }

        if (TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_AGREE)) {
            // 合作采购商-详细资料-同意合作
            mPresenter.editCooperationPurchaser(mReq);
        } else if (TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_ADD)) {
            // 合作采购商-详细资料-添加合作采购商
            mReq.setActionType("normal");
            mPresenter.addCooperationPurchaser(mReq);
        } else if (TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_REPEAT)) {
            // 合作采购商-详细资料-重新验证合作采购商
            mReq.setActionType("revalidation");
            mPresenter.addCooperationPurchaser(mReq);
        } else {
            if (changeGroupProperty()) {
                showChangeRangeWindow();
            } else {
                mPresenter.editShopSettlement(mReq);
            }
        }
    }

    @OnTouch(R.id.css_scroll_view)
    boolean onTouch() {
        UIUtils.hideActivitySoftKeyboard(this);
        return false;
    }

    @OnClick(R.id.css_inspect)
    void selectInspector() {
        List<NameValue> list = new ArrayList<>();
        list.add(new NameValue("采购商验货", "1"));
        list.add(new NameValue("供应商验货", "2"));
        showDialog(list, mInspect.getHint(), nameValue -> {
            mInspect.setText(nameValue.getName());
            mReq.setInspector(nameValue.getValue());
        });
    }

    @OnClick(R.id.css_deliver)
    void selectDeliver() {
        mPresenter.queryDeliveryList();
    }

    @OnClick(R.id.css_level)
    void selectLevel(TextView view) {
        List<NameValue> list = new ArrayList<>(2);
        list.add(new NameValue("普通客户", "0"));
        list.add(new NameValue("VIP客户", "1"));
        showDialog(list, view.getHint(), nameValue -> {
            mLevel.setText(nameValue.getName());
            mReq.setCustomerLevel(nameValue.getValue());
        });
    }


    @OnClick(R.id.css_date_range)
    void selectDateRange() {
        mPresenter.queryDeliveryPeriod();
    }

    private void showDialog(List<NameValue> list, CharSequence title, SingleSelectionDialog.OnSelectListener<NameValue> listener) {
        SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .refreshList(list)
                .setTitleText(title)
                .setOnSelectListener(listener)
                .create()
                .show();
    }

    @Override
    public void showDeliveryDialog(String deliveryWay) {
        if (TextUtils.isEmpty(deliveryWay)) {
            showToast("配送方式列表为空");
            return;
        }
        List<String> ways = Arrays.asList(deliveryWay.split(","));
        Collections.sort(ways);
        List<NameValue> list = new ArrayList<>(3);
        for (String way : ways) {
            switch (way.trim()) {
                case "1":
                    list.add(new NameValue("自有物流配送", "1"));
                    break;
                case "2":
                    list.add(new NameValue("上门自提", "2"));
                    break;
                case "3":
                    list.add(new NameValue("三方物流配送", "3"));
                    break;
            }
        }
        showDialog(list, mDeliver.getHint(), nameValue -> {
            mDeliver.setText(nameValue.getName());
            mReq.setDeliveryWay(nameValue.getValue());
        });
    }

    @Override
    public void showDeliveryPeriodWindow(List<DeliveryPeriodBean> list) {
        if (CommonUtils.isEmpty(list)) {
            showToast("到货时间列表查询为空");
            return;
        }
        SingleSelectionDialog.newBuilder(this, (SingleSelectionDialog.WrapperName<DeliveryPeriodBean>) bean
                -> bean.getArrivalStartTime() + "-" + bean.getArrivalEndTime())
                .setTitleText(mDateRange.getHint())
                .setOnSelectListener(bean -> {
                    mDateRange.setText(String.format("%s-%s", bean.getArrivalStartTime(),
                            bean.getArrivalEndTime()));
                    mReq.setDeliveryPeriod(mDateRange.getText().toString());
                })
                .refreshList(list)
                .create().show();
    }

    /**
     * 修改集团参数
     *
     * @return true-时
     */
    private boolean changeGroupProperty() {
        return TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_PROPERTY);
    }

    private void showChangeRangeWindow() {
        SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("是否统一修改结算方式")
            .setMessage("是否要修改集团下所有门店的结算方\n式，确认修改后将统一所有门店结算方式")
            .setButton((dialog, item) -> {
                dialog.dismiss();
                if (item == 1) {
                    mReq.setChangeAllShops("0");
                } else {
                    mReq.setChangeAllShops("1");
                }
                mPresenter.editShopSettlement(mReq);
            }, "确认统一", "暂不统一")
            .create().show();
    }

    public static String getPayTermStr(int payTerm) {
        return payTerm > STRINGS_WEEK.length ? "" : STRINGS_WEEK[payTerm];
    }

    @OnTextChanged(R.id.css_verify_edit)
    public void onTextChanged(CharSequence s) {
        mRemainNum.setText(String.valueOf(100 - s.length()));
    }

    @Override
    public void showSettlementList(SettlementBean bean) {
        List<String> payTypeEnum = bean.getPayTypeEnum();
        if (!CommonUtils.isEmpty(payTypeEnum)) {
            List<String> rawList = new ArrayList<>();
            rawList.add(PAY_ONLINE);
            rawList.add(PAY_CASH);
            rawList.add(PAY_ACCOUNT);
            rawList.retainAll(payTypeEnum);
            mAdapter.setNewData(rawList);
        } else {
            mSettleBody.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mReq.getSettlementWay())) {
            mAdapter.selectBySettlementWay(mReq.getSettlementWay());
            mPayAccount.setData(mReq.getAccountPeriodType(), mReq.getAccountPeriod(), mReq.getSettleDate());
            mPayAccount.setVisibility(mReq.getSettlementWay().contains(PAY_ACCOUNT) ? View.VISIBLE : View.GONE);
        } else {
            mPayAccount.setData(bean.getPayTermType(), bean.getPayTerm(), bean.getSettleDate());
        }
    }

    @Override
    public void editSuccess() {
        String path;
        if (TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_AGREE) ||
            TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_ADD) ||
            TextUtils.equals(mReq.getFrom(), BaseCooperationDetailsFragment.FROM_COOPERATION_DETAILS_REPEAT)) {
            path = RouterConfig.COOPERATION_PURCHASER_LIST;
        } else {
            path = RouterConfig.COOPERATION_PURCHASER_DETAIL;
        }
        ARouter.getInstance().build(path)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    @Override
    public void showEditText() {
        mVerifyGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public String getVerification() {
        String verification = null;
        if (mVerifyGroup.getVisibility() == View.VISIBLE) {
            verification = mVerifyEdit.getText().toString().trim();
        }
        return verification;
    }

    static class Adapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private final Set<String> selectTypes = new HashSet<>();

        Adapter() {
            super(0);
        }

        private boolean select(String type) {
            if (!TextUtils.isEmpty(type)) {
                if (selectTypes.contains(type)) {
                    selectTypes.remove(type);
                } else {
                    selectTypes.add(type);
                }
                notifyItemChanged(mData.indexOf(type));
            }
            return selectTypes.contains(PAY_ACCOUNT);
        }

        Set<String> getSelectTypes() {
            return selectTypes;
        }

        void selectBySettlementWay(String settlementWay) {
            String[] strings = settlementWay.split(",");
            Collections.addAll(selectTypes, strings);
            notifyDataSetChanged();
        }

        @SuppressLint("ResourceType")
        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextColor(ContextCompat.getColorStateList(parent.getContext(), R.drawable.color_state_on_pri_off_222));
            textView.setTextSize(13);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45)));
            textView.setCompoundDrawablePadding(UIUtils.dip2px(10));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bg_selector_check_box, 0, 0, 0);
            return new BaseViewHolder(textView);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.itemView.setSelected(selectTypes.contains(item));
            ((TextView) helper.itemView).setText(getLabel(item));
        }

        private String getLabel(String type) {
            switch (type) {
                case PAY_CASH:
                    return "货到付款";
                case PAY_ACCOUNT:
                    return "账期支付";
                case PAY_ONLINE:
                    return "在线支付";
                default:
                    return "";
            }
        }
    }
}
