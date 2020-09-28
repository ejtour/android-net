package com.hll_sc_app.app.marketingsetting.product.add;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.CouponRuleAdapter;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingProductAdapter;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingRuleAdapter;
import com.hll_sc_app.app.marketingsetting.coupon.selectshops.SelectGroupsActivity;
import com.hll_sc_app.app.marketingsetting.helper.MarketingHelper;
import com.hll_sc_app.app.marketingsetting.product.MarketingRule;
import com.hll_sc_app.app.marketingsetting.product.check.ProductMarketingCheckActivity;
import com.hll_sc_app.app.marketingsetting.product.selectcoupon.CouponSelectListActivity;
import com.hll_sc_app.app.marketingsetting.selectarea.SelectAreaActivity;
import com.hll_sc_app.app.marketingsetting.selectproduct.ProductSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.MarketingEvent;
import com.hll_sc_app.bean.event.SingleListEvent;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.marketingsetting.AreaListBean;
import com.hll_sc_app.bean.marketingsetting.CouponSendReq;
import com.hll_sc_app.bean.marketingsetting.GiveBean;
import com.hll_sc_app.bean.marketingsetting.MarketingCustomerBean;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddResp;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;
import com.hll_sc_app.bean.marketingsetting.SelectCouponListBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.DateTimePickerDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_DZ;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_MANZHE;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_MJ;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_MJ_ORDER;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_MZ;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_ZJ;
import static com.hll_sc_app.app.marketingsetting.product.MarketingRule.RULE_ZQ;

/**
 * 新增商品促销
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST_ADD, extras = Constant.LOGIN_EXTRA)
public class ProductMarketingAddActivity extends BaseLoadActivity implements IProductMarketingAddContract.IView {

    private static final String TIME_RANG_START = "201701010000";
    private static final String TIME_RANG_END = "203012312359";
    private static final int REQUEST_SELECT_AREA = 100;
    private static final int REQUEST_SELECT_COUPON = 101;
    private static final String REQUEST_SELECT_AREA_NAME = "area";
    private static final String TIME_FORMATE_SHOW = "yyyy/MM/dd HH:mm";
    private static final String TIME_FORMATE = "yyyyMMddHHmm";

    @Autowired(name = "parcelable")
    MarketingDetailCheckResp mDetail;
    @Autowired(name = "discountType")
    int mDiscountType;
    @Autowired(name = "isCopy")
    boolean mIsCopy;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.edit_theme)
    EditText mEditTheme;
    @BindView(R.id.txt_time_start_select)
    TextView mTxtStartTime;
    @BindView(R.id.txt_time_end_select)
    TextView mTxtEndTime;
    @BindView(R.id.list_marketing_product)
    RecyclerView mMarketingProductList;
    @BindView(R.id.txt_empty_word)
    TextView mEmptyWord;
    @BindView(R.id.txt_rule_select)
    TextView mRuleSelect;
    //    @BindView(R.id.view_coupon_select)
//    CouponRuleSelectView mCouponRuleSelectView;
    @BindView(R.id.list_rule)
    RecyclerView mListRule;
    @BindView(R.id.ll_rule_dz)
    LinearLayout mRuleDZLayout;
    @BindView(R.id.txt_add_rule)
    TextView mRuleAdd;
    @BindView(R.id.group_ladder)
    Group mGroupLadder;
    @BindView(R.id.txt_area_select)
    TextView mTxtAreaSelect;
    @BindView(R.id.switch_ladder)
    Switch mSwitchLadder;
    @BindView(R.id.edt_rule_dz)
    EditText mEdtRuleDZ;
    @BindView(R.id.group_add_product)
    Group mGroupAddProduct;
    @BindView(R.id.txt_no_return)
    TextView mTxtNoReturn;
    @BindView(R.id.txt_customer_scope)
    TextView mCustomerScope;
    @BindView(R.id.group_specify_customer)
    Group mGroupSpecifyCustomer;
    @BindView(R.id.txt_specify_customer)
    TextView mSpecifyCustomer;
    private Unbinder unbinder;
    private DateTimePickerDialog.Builder mDateTimeDialogBuilder;
    private SingleSelectionDialog mSingleRuleDilog;
    private SingleSelectionDialog mCustomerDialog;
    private MarketingRuleAdapter mMarketingRuleAdapter;
    private CouponRuleAdapter mCouponRuleAdapter;
    private ProductMarketingAddPresenter mPresenter;
    private SelectCouponListBean mSelectCoupon;
    private int currentSelectCouponIndex = -1;
    /**
     * 选择的时间
     */
    private long startTime = Calendar.getInstance().getTimeInMillis();
    private long endTime = startTime;

    private ArrayList<MarketingCustomerBean> mSelectCustomer = new ArrayList<>();

    /**
     * 选择商品的adapter
     */
    private MarketingProductAdapter mMarketingProductAdpater;

    /**
     * 选择的地区
     *
     * @param savedInstanceState
     */
    private HashMap<String, ArrayList<AreaBean.ChildBeanX>> selectedAreaMap = new HashMap<>();

    public static void start(MarketingDetailCheckResp detailCheckResp, int discountType) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST_ADD)
                .withParcelable("parcelable", detailCheckResp)
                .withInt("discountType", discountType)
                .setProvider(new LoginInterceptor()).navigation();
    }

    public static void startByCopy(MarketingDetailCheckResp detailCheckResp, int discountType) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST_ADD)
                .withParcelable("parcelable", detailCheckResp)
                .withInt("discountType", discountType)
                .withBoolean("isCopy", true)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_marketing_product_add);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        initView();
        setInitValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);

    }

    private void initView() {
        mTitleBar.setHeaderTitle(String.format("新增%s促销", getMarketingTypeName()));
        mGroupAddProduct.setVisibility(mDiscountType == 1 ? View.GONE : View.VISIBLE);
        mEmptyWord.setVisibility(mDiscountType == 1 ? View.GONE : View.VISIBLE);
        mPresenter = ProductMarketingAddPresenter.newInstance();
        mPresenter.register(this);
        /*活动商品列表*/
        mMarketingProductList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMarketingProductList.setNestedScrollingEnabled(false);
        /*默认是普通编辑模式*/
        mMarketingProductAdpater = new MarketingProductAdapter(null, MarketingProductAdapter.Modal.EDIT);
        mMarketingProductList.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mMarketingProductList.setAdapter(mMarketingProductAdpater);
        mMarketingProductAdpater.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.img_delete) {
                adapter.remove(position);
                mEmptyWord.setVisibility(adapter.getData().size() > 0 ? View.GONE : View.VISIBLE);
                updateProductNoReturnCheck(mMarketingProductAdpater.getData());
            } else if (view.getId() == R.id.rl_no_return) {
                SkuGoodsBean skuGoodsBean = mMarketingProductAdpater.getItem(position);
                if (skuGoodsBean == null) {
                    return;
                }
                skuGoodsBean.setNonRefund(skuGoodsBean.getNonRefund() == 0 ? 1 : 0);
//                mMarketingProductAdpater.notifyDataSetChanged();//这个方法会触发列表抖动
                ((CheckBox) view.findViewById(R.id.check_no_return)).setChecked(skuGoodsBean.getNonRefund() == 1);
                updateProductNoReturnCheck(mMarketingProductAdpater.getData());
            } else if (view.getId() == R.id.check_no_return) {
                SkuGoodsBean skuGoodsBean = mMarketingProductAdpater.getItem(position);
                if (skuGoodsBean == null) {
                    return;
                }
                skuGoodsBean.setNonRefund(skuGoodsBean.getNonRefund() == 0 ? 1 : 0);
//                mMarketingProductAdpater.notifyDataSetChanged();//这个方法会触发列表抖动
                ((CheckBox) view).setChecked(skuGoodsBean.getNonRefund() == 1);
                updateProductNoReturnCheck(mMarketingProductAdpater.getData());
            }
        });
        /*促销规则列表*/
        mListRule.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        /*增加促销规则按钮*/
        mRuleAdd.setOnClickListener(v -> {
            NameValue nameValue = (NameValue) mRuleSelect.getTag();
            addRule(Integer.parseInt(nameValue.getValue()));
        });
        /*阶梯规则switch*/
        mSwitchLadder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {//打开阶梯
                if (mListRule.getVisibility() == View.VISIBLE) {
                    mRuleAdd.setVisibility(View.VISIBLE);
                }
            } else {//关闭阶梯:赠券判断mCouponRuleAdapter 其他判断mMarketingRuleAdapter
                if ((TextUtils.equals(getRuleType() + "", RULE_ZQ.getKey()) && mCouponRuleAdapter != null && mCouponRuleAdapter.getData().size() > 1) ||
                        (mMarketingRuleAdapter != null && mMarketingRuleAdapter.getData().size() > 1)) {
                    showToast("您已设置阶梯促销，若要关闭，请先删除阶梯促销规则");
                    mSwitchLadder.setChecked(true);
                } else {
                    mRuleAdd.setVisibility(View.GONE);
                }
            }
        });

        /*头部保存*/
        mTitleBar.setRightBtnClick(v -> {
            onSave();
        });
//
//        /*赠券点击跳转优惠券*/
//        mCouponRuleSelectView.setSelectListener(() -> {
//            CouponSelectListActivity.start(this, REQUEST_SELECT_COUPON, mSelectCoupon);
//        });

        /*打折输入格式监听*/
        mEdtRuleDZ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (TextUtils.equals(content, ".")) {
                    mEdtRuleDZ.setText("");
                    return;
                } else if (content.contains(".") && content.length() - content.indexOf(".") > 2) {
                    mEdtRuleDZ.setText(s.subSequence(0, s.length() - 1));
                    mEdtRuleDZ.setSelection(s.length() - 1);
                    showToast("折扣仅允许小数点后一位");
                    return;
                }
                /*更新商品打折后的价格*/
                boolean isEmpty = TextUtils.isEmpty(content);
                for (SkuGoodsBean skuGoodsBean : mMarketingProductAdpater.getData()) {
                    if (isEmpty) {
                        skuGoodsBean.setDiscountPrice("");
                    } else {
                        skuGoodsBean.setDiscountPrice(CommonUtils.mulStringPercentage(content, skuGoodsBean.getProductPrice(), 10).toString());
                    }
                }
                mMarketingProductAdpater.notifyDataSetChanged();
            }
        });

    }

    /**
     * 编辑模式 需要设置页面的初始值
     */
    private void setInitValue() {
        if (mDetail == null) {
            return;
        }
        //更改标题
        mTitleBar.setHeaderTitle((mIsCopy ? "新增" : "编辑") + getMarketingTypeName() + "促销");
        //促销主题
        mEditTheme.setText(mDetail.getDiscountName());
        //开始结束时间
        mTxtStartTime.setText(CalendarUtils.getDateFormatString(mDetail.getDiscountStartTime(), TIME_FORMATE, TIME_FORMATE_SHOW));
        mTxtEndTime.setText(CalendarUtils.getDateFormatString(mDetail.getDiscountEndTime(), TIME_FORMATE, TIME_FORMATE_SHOW));
        mTxtStartTime.setTag(CalendarUtils.parse(mDetail.getDiscountStartTime(), TIME_FORMATE));
        mTxtEndTime.setTag(CalendarUtils.parse(mDetail.getDiscountEndTime(), TIME_FORMATE));
        //活动商品
        mMarketingProductAdpater.setNewData(mDetail.getProductList());
        mEmptyWord.setVisibility(View.GONE);
        //是否阶梯促销
        mSwitchLadder.setChecked(false);
        //促销规则
        mRuleSelect.setText(mDetail.getRuleTypeName());
        MarketingRule rule = MarketingRule.getRuleEnum(mDetail.getDiscountRuleType());
        mRuleSelect.setTag(new NameValue(rule.getValue(), rule.getKey()));
        //活动内容
        toggleRuleType(mDetail.getDiscountRuleType() + "", false);
        if (TextUtils.equals(mDetail.getDiscountRuleType() + "", MarketingRule.RULE_ZQ.getKey())) {
//            mCouponRuleAdapter = new CouponRuleAdapter(mDetail.getRuleList(), CouponRuleAdapter.EDIT, mDiscountType);
            mCouponRuleAdapter.setNewData(mDetail.getRuleList());
            mSwitchLadder.setChecked(mDetail.getRuleList().size() > 1);
            RuleListBean ruleBean = mDetail.getRuleList().get(0);
            List<GiveBean> giveBeans = JsonUtil.parseJsonList(ruleBean.getGiveTarget(), GiveBean.class);
            mSelectCoupon = new SelectCouponListBean();
            mSelectCoupon.setDiscountID(giveBeans.get(0).getGiveTargetID());
            mSelectCoupon.setDiscountName(giveBeans.get(0).getGiveTargetName());
        } else if (TextUtils.equals(mDetail.getDiscountRuleType() + "", MarketingRule.RULE_DZ.getKey())) {
            mMarketingProductAdpater.setModal(MarketingProductAdapter.Modal.EDIT_DZ);
            RuleListBean ruleBean = mDetail.getRuleList().get(0);
            mEdtRuleDZ.setText(ruleBean.getRuleDiscountValue());
        } else {
            mMarketingRuleAdapter.setNewData(mDetail.getRuleList());
            mSwitchLadder.setChecked(mDetail.getRuleList().size() > 1);
        }

        //活动地区
        mTxtAreaSelect.setText(mDetail.getAreaDesc());
        if (mDetail.getAreaScope() == 1) {//全国
            selectedAreaMap = transformAreaBeanToMap(UIUtils.getAreaList(this, false));
        } else {
            selectedAreaMap = transformFlatAreaToMap(mDetail.getAreaList());
        }
        mTxtAreaSelect.setTag(mDetail.getAreaScope() == 1);

        mCustomerScope.setTag(mDetail.getCustomerScope());
        mCustomerScope.setText(getCustomerScope(mDetail.getCustomerScope()));

        if (mDetail.getCustomerScope() == 3 && !TextUtils.isEmpty(mDetail.getCustomerDesc())) {
            mGroupSpecifyCustomer.setVisibility(View.VISIBLE);
            mSpecifyCustomer.setText(String.format("已选%s", mDetail.getCustomerDesc()));
            if (!CommonUtils.isEmpty(mDetail.getCustomerList())) {
                mSelectCustomer = new ArrayList<>(mDetail.getCustomerList());
            }
        }
    }

    private String getCustomerScope(int scope) {
        switch (scope) {
            case 1:
                return "全部客户";
            case 2:
                return "全部合作客户";
            case 3:
                return "指定客户";
            default:
                return "";
        }
    }

    private String getMarketingTypeName() {
        return mDiscountType == 1 ? "订单" : "商品";
    }

    /**
     * 新增一条规则
     *
     * @param ruleType
     */
    private void addRule(int ruleType) {
        if (mCouponRuleAdapter != null && TextUtils.equals(ruleType + "", RULE_ZQ.getKey())) {
            mCouponRuleAdapter.addData(getNewRuleListBean());
        } else if (mMarketingRuleAdapter != null) {
            mMarketingRuleAdapter.addData(getNewRuleListBean());
        }
    }

    /**
     * 保存
     */
    private void onSave() {
        if (checkInput()) {
            if (mDetail == null || mIsCopy) {
                mPresenter.addMarketingProduct();
            } else {
                mPresenter.modifyMarketingProduct();
            }
        }
    }

    private void toggleRuleType(String ruleType, boolean isInitData) {
        if (TextUtils.equals(ruleType, RULE_ZJ.getKey())) {
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
            mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
            initRuleListAdapter(Integer.parseInt(ruleType), isInitData);
            mMarketingProductAdpater.setModal(MarketingProductAdapter.Modal.EDIT);
        } else if (TextUtils.equals(ruleType, RULE_MZ.getKey())) {
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
            mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
            initRuleListAdapter(Integer.parseInt(ruleType), isInitData);
            mMarketingProductAdpater.setModal(MarketingProductAdapter.Modal.EDIT);
        } else if (TextUtils.equals(ruleType, RULE_ZQ.getKey())) {
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
//            if (mDiscountType == 1) {
                mGroupLadder.setVisibility(View.VISIBLE);
                mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            /*} else {
                mGroupLadder.setVisibility(View.GONE);
                mRuleAdd.setVisibility(View.GONE);
            }*/
            initRuleListAdapter(Integer.parseInt(ruleType), isInitData);
            mMarketingProductAdpater.setModal(MarketingProductAdapter.Modal.EDIT);
        } else if (TextUtils.equals(ruleType, RULE_MJ.getKey()) ||TextUtils.equals(ruleType, RULE_MJ_ORDER.getKey())) {
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
            mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
            initRuleListAdapter(Integer.parseInt(ruleType), isInitData);
            mMarketingProductAdpater.setModal(MarketingProductAdapter.Modal.EDIT);
        } else if (TextUtils.equals(ruleType, RULE_DZ.getKey())) {
            mRuleDZLayout.setVisibility(View.VISIBLE);
            mListRule.setVisibility(View.GONE);
            mRuleAdd.setVisibility(View.GONE);
            mGroupLadder.setVisibility(View.GONE);
            mMarketingProductAdpater.setModal(MarketingProductAdapter.Modal.EDIT_DZ);
        }else if(TextUtils.equals(ruleType, RULE_MANZHE.getKey())){
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
            mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
            initRuleListAdapter(Integer.parseInt(ruleType), isInitData);
            mMarketingProductAdpater.setModal(MarketingProductAdapter.Modal.EDIT);
        }
    }

    /**
     * 将数组形式的地理数据转为map结构
     */
    public static HashMap<String, ArrayList<AreaBean.ChildBeanX>> transformAreaBeanToMap(List<AreaBean> areaBeans) {
        HashMap<String, ArrayList<AreaBean.ChildBeanX>> flatMap = new HashMap<>();
        if (areaBeans == null || areaBeans.size() == 0) {
            return null;
        }
        for (AreaBean areaBean : areaBeans) {
            flatMap.put(areaBean.getCode(), (ArrayList<AreaBean.ChildBeanX>) areaBean.getChild());
        }
        return flatMap;
    }

    /**
     * 将铺平的地理数据转为map结构
     */
    public static HashMap<String, ArrayList<AreaBean.ChildBeanX>> transformFlatAreaToMap(List<AreaListBean> areaListBeans) {
        HashMap<String, ArrayList<AreaBean.ChildBeanX>> flatMap = new HashMap<>();
        if (areaListBeans == null || areaListBeans.size() == 0) {
            return null;
        }
        for (AreaListBean areaListBean : areaListBeans) {
            if (flatMap.containsKey(areaListBean.getProvinceCode())) {
                AreaBean.ChildBeanX cityBean = new AreaBean.ChildBeanX();
                cityBean.setCode(areaListBean.getCityCode());
                cityBean.setName(areaListBean.getCityName());
                cityBean.setpName(areaListBean.getProvinceName());
                cityBean.setpCode(areaListBean.getProvinceCode());
                flatMap.get(areaListBean.getProvinceCode()).add(cityBean);
            } else {
                ArrayList<AreaBean.ChildBeanX> cityBeans = new ArrayList<>();
                AreaBean.ChildBeanX cityBean = new AreaBean.ChildBeanX();
                cityBean.setCode(areaListBean.getCityCode());
                cityBean.setName(areaListBean.getCityName());
                cityBean.setpName(areaListBean.getProvinceName());
                cityBean.setpCode(areaListBean.getProvinceCode());
                cityBeans.add(cityBean);
                flatMap.put(areaListBean.getProvinceCode(), cityBeans);
            }
        }
        return flatMap;
    }

    private RuleListBean getNewRuleListBean() {
        RuleListBean ruleListBean = new RuleListBean();
        List<GiveBean> giveBeans = new ArrayList<>();
        giveBeans.add(new GiveBean());
        ruleListBean.setGiveList(giveBeans);
        return ruleListBean;
    }

    /**
     * 校验输入
     *
     * @return
     */
    private boolean checkInput() {
        if (getRuleType() < 0) {
            showToast("请选择促销规则");
            return false;
        } else if (TextUtils.isEmpty(getMarketingTheme())) {
            showToast("请填写促销主题");
            return false;
        } else if (TextUtils.isEmpty(getStartTime())) {
            showToast("请选择开始时间");
            return false;
        } else if (TextUtils.isEmpty(getEndTime())) {
            showToast("请选择结束时间");
            return false;
        } else if (mTxtAreaSelect.getText().toString().length() == 0) {
            showToast("请选择活动区域");
            return false;
        } else if (mDiscountType == 2 && mMarketingProductAdpater.getData().size() == 0) {
            showToast("请选择活动商品");
            return false;
        }
        //直降、满减、满赠
        if (TextUtils.equals(getRuleType() + "", RULE_ZJ.getKey()) ||
                TextUtils.equals(getRuleType() + "", RULE_MZ.getKey()) ||
                TextUtils.equals(getRuleType() + "", RULE_MJ.getKey()) ||
                TextUtils.equals(getRuleType() + "", RULE_MJ_ORDER.getKey())
        ) {
            if (mMarketingRuleAdapter == null || mMarketingRuleAdapter.getData().size() == 0) {
                showToast("请填写至少一个促销规则");
                return false;
            }
        }
        //赠券
        else if (TextUtils.equals(getRuleType() + "", RULE_ZQ.getKey())) {
            if (!isCouponInfoComplete()) {
                showToast("请填写至少一个促销规则");
                return false;
            }
        }
        //打折
        else if (TextUtils.equals(getRuleType() + "", RULE_DZ.getKey()) && mEdtRuleDZ.getText().toString().trim().length() == 0) {
            showToast("请填写打折规则");
            return false;
        }

        return true;
    }

    private void initRuleListAdapter(int ruleType, boolean isInitData) {
        BaseQuickAdapter<RuleListBean, BaseViewHolder> currentAdapter;
        if (TextUtils.equals((ruleType + ""), RULE_ZQ.getKey())) {
            if (mCouponRuleAdapter == null) {
                mCouponRuleAdapter = new CouponRuleAdapter(null, CouponRuleAdapter.EDIT, mDiscountType);
                mCouponRuleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    switch (view.getId()) {
                        case R.id.txt_coupon_select:
                            currentSelectCouponIndex = position;
                            GiveBean giveBean = mCouponRuleAdapter.getItem(position).getGiveList().get(0);
                            mSelectCoupon = new SelectCouponListBean();
                            mSelectCoupon.setDiscountID(giveBean.getGiveTargetID());
                            mSelectCoupon.setDiscountName(giveBean.getGiveTargetName());
                            CouponSelectListActivity.start(this, REQUEST_SELECT_COUPON, mSelectCoupon);
                            break;
                        case R.id.img_delete:
                            mCouponRuleAdapter.remove(position);
                            break;
                        default:
                            break;
                    }
                });
            }
            currentAdapter = mCouponRuleAdapter;

        } else {
            if (mMarketingRuleAdapter == null) {
                mMarketingRuleAdapter = new MarketingRuleAdapter(null, true, mDiscountType);
                mMarketingRuleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    adapter.remove(position);
                });
            }
            mMarketingRuleAdapter.setRultType(ruleType);
            mMarketingRuleAdapter.setNewData(null);
            currentAdapter = mMarketingRuleAdapter;
        }


        mListRule.setAdapter(currentAdapter);
        if (isInitData) {
            currentAdapter.setNewData(null);
            currentAdapter.addData(getNewRuleListBean());
            currentAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 赠券模式下，是否填写完全
     *
     * @return
     */
    private boolean isCouponInfoComplete() {
        if (mCouponRuleAdapter == null || mCouponRuleAdapter.getData().size() == 0) {
            return false;
        }
        List<RuleListBean> ruleListBeans = mCouponRuleAdapter.getData();
        for (RuleListBean ruleListBean : ruleListBeans) {
            if (TextUtils.isEmpty(ruleListBean.getRuleCondition()) ||
                    TextUtils.isEmpty(ruleListBean.getRuleDiscountValue())) {
                return false;
            }
            GiveBean giveBean = ruleListBean.getGiveList().get(0);
            if (giveBean == null || TextUtils.isEmpty(giveBean.getGiveTargetID())) {
                return false;
            }
        }

        return true;
    }

    /***
     * 全部不可退货的checkbox的选中的状态
     * @param skuGoodsBeans
     */
    private void updateProductNoReturnCheck(List<SkuGoodsBean> skuGoodsBeans) {
        int isSelectedTimes = 0;
        for (SkuGoodsBean skuGoodsBean : skuGoodsBeans) {
            if (skuGoodsBean.getNonRefund() == 1) {
                isSelectedTimes++;
            }
        }

        //遍历：看是否勾选不可退货 -1 一个没有,0:部分,1:全都选了
        int checkResult = isSelectedTimes == skuGoodsBeans.size() ? 1 : isSelectedTimes > 0 ? 0 : -1;
        if (checkResult == 1) {
            mTxtNoReturn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_selector_check_box, 0);
            mTxtNoReturn.setSelected(true);
        } else if (checkResult == 0) {
            mTxtNoReturn.setSelected(false);
            mTxtNoReturn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_selector_check_box_part, 0);
        } else {
            mTxtNoReturn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_selector_check_box, 0);
            mTxtNoReturn.setSelected(false);
        }
    }

    @Subscribe
    public void onEvent(SingleListEvent<SkuGoodsBean> event) {
        if (event.getClazz() != SkuGoodsBean.class) return;
        List<SkuGoodsBean> skuGoodsBeans = event.getList();
        if (CommonUtils.isEmpty(skuGoodsBeans)) {
            mEmptyWord.setVisibility(View.VISIBLE);
        } else {
            mEmptyWord.setVisibility(View.GONE);
            /*如果是打折的。需要将商品重新计算价格*/
            if (mMarketingProductAdpater.getModal() == MarketingProductAdapter.Modal.EDIT_DZ) {
                for (SkuGoodsBean skuGoodsBean : skuGoodsBeans) {
                    skuGoodsBean.setDiscountPrice(CommonUtils.mulStringPercentage(mEdtRuleDZ.getText().toString(), skuGoodsBean.getProductPrice(), 10).toString());
                }
            }
            mMarketingProductAdpater.setNewData(skuGoodsBeans);
            updateProductNoReturnCheck(skuGoodsBeans);
        }
    }

    @OnClick({R.id.txt_time_start_select, R.id.txt_time_end_select, R.id.txt_add_product, R.id.txt_rule_select,
            R.id.txt_area_select, R.id.txt_no_return, R.id.txt_customer_scope, R.id.txt_specify_customer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_time_start_select:
                initDateTimeBuilder();
                mDateTimeDialogBuilder = mDateTimeDialogBuilder
                        .setPreCallback(new DateTimePickerDialog.SelectPreCallback() {
                            @Override
                            public boolean select(Date time) {
                                Date endTimeTag = (Date) mTxtEndTime.getTag();
                                if (endTimeTag != null && endTimeTag.before(time)) {
                                    showToast("起始时间需小于结束时间");
                                    return false;
                                }
                                return true;
                            }
                        })
                        .setCallback(new DateTimePickerDialog.SelectCallback() {
                            @Override
                            public void select(Date time) {
                                mTxtStartTime.setTag(time);
                                mTxtStartTime.setText(CalendarUtils.format(time, TIME_FORMATE_SHOW));

                            }
                        })
                        .setTitle("活动开始时间");

                Date preStart = (Date) mTxtStartTime.getTag();
                if (preStart != null) {
                    mDateTimeDialogBuilder.setSelectTime(preStart.getTime());
                }
                mDateTimeDialogBuilder.create().show();

                break;
            case R.id.txt_time_end_select:
                initDateTimeBuilder();
                mDateTimeDialogBuilder = mDateTimeDialogBuilder
                        .setPreCallback(new DateTimePickerDialog.SelectPreCallback() {
                            @Override
                            public boolean select(Date time) {
                                Date startTimeTag = (Date) mTxtStartTime.getTag();
                                if (startTimeTag != null && startTimeTag.after(time)) {
                                    showToast("结束时间需大于起始时间");
                                    return false;
                                }
                                return true;
                            }
                        })
                        .setCallback(new DateTimePickerDialog.SelectCallback() {
                            @Override
                            public void select(Date time) {
                                mTxtEndTime.setTag(time);
                                mTxtEndTime.setText(CalendarUtils.format(time, TIME_FORMATE_SHOW));
                            }
                        })
                        .setTitle("活动结束时间");

                Date preEnd = (Date) mTxtEndTime.getTag();
                if (preEnd != null) {
                    mDateTimeDialogBuilder.setSelectTime(preEnd.getTime());
                }
                mDateTimeDialogBuilder.create().show();
                break;
            case R.id.txt_add_product:
                ProductSelectActivity.start(getClass().getSimpleName(), "选择活动商品", new ArrayList<>(mMarketingProductAdpater.getData()));
                break;
            case R.id.txt_rule_select:
               /* if (mDetail != null) {
                    showToast("不能更改类型");
                    return;
                }*/
                /*促销规则类型window*/
                if (mSingleRuleDilog == null) {
                    mSingleRuleDilog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                            .refreshList(getRules())
                            .setTitleText("选择类型")
                            .select((NameValue) mRuleSelect.getTag())
                            .setOnSelectListener(bean -> {
                                mRuleSelect.setText(bean.getName());
                                mRuleSelect.setTag(bean);
                                toggleRuleType(bean.getValue(), true);
                            }).create();
                }
                mSingleRuleDilog.show();
                break;
            case R.id.txt_area_select:
                SelectAreaActivity.start(this, REQUEST_SELECT_AREA, REQUEST_SELECT_AREA_NAME, "选择区域", selectedAreaMap);
                break;
            case R.id.txt_no_return:
                /*商品促销-全部不可退货checkbox事件*/
                if (mDiscountType == 2) {//商品促销
                    mTxtNoReturn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_selector_check_box, 0);
                    mTxtNoReturn.setSelected(!mTxtNoReturn.isSelected());
                    for (SkuGoodsBean skuGoodsBean : mMarketingProductAdpater.getData()) {
                        skuGoodsBean.setNonRefund(mTxtNoReturn.isSelected() ? 1 : 0);
                    }
                    mMarketingProductAdpater.notifyDataSetChanged();
                }
                break;
            case R.id.txt_customer_scope:
                if (mCustomerDialog == null) {
                    List<NameValue> nameValues = new ArrayList<>();
                    nameValues.add(new NameValue("全部客户", "1"));
                    nameValues.add(new NameValue("全部合作客户", "2"));
                    nameValues.add(new NameValue("指定客户", "3"));
                    mCustomerDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                            .refreshList(nameValues)
                            .setTitleText("选择活动客户")
                            .setOnSelectListener(nameValue -> {
                                if (nameValue.getName().equals(mCustomerScope.getText().toString()))
                                    return;
                                mCustomerScope.setText(nameValue.getName());
                                mCustomerScope.setTag(nameValue.getValue());
                                mGroupSpecifyCustomer.setVisibility(Integer.parseInt(nameValue.getValue()) == 3 ? View.VISIBLE : View.GONE);
                            })
                            .create();
                }
                mCustomerDialog.show();
                break;
            case R.id.txt_specify_customer:
                SelectGroupsActivity.start(mSelectCustomer, "选择客户");
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(ArrayList<CouponSendReq.GroupandShopsBean> customers) {
        mSelectCustomer = MarketingHelper.convertCouponBeanToCustomer(customers);
        int shopCount = 0;
        for (MarketingCustomerBean bean : mSelectCustomer) {
            shopCount += bean.getShopCount();
        }
        mSpecifyCustomer.setText(String.format("已选%s个集团,%s家门店", customers.size(), shopCount));
    }

    private void initDateTimeBuilder() {
        if (mDateTimeDialogBuilder == null) {
            mDateTimeDialogBuilder = DateTimePickerDialog.newBuilder(this)
                    .setBeginTime(CalendarUtils.parse(TIME_RANG_START, Constants.UNSIGNED_YYYY_MM_DD_HH_MM).getTime())
                    .setEndTime(CalendarUtils.parse(TIME_RANG_END, Constants.UNSIGNED_YYYY_MM_DD_HH_MM).getTime());
        }
    }

    private List<NameValue> getRules() {
        List<NameValue> statusBeans = new ArrayList<>();
        if (mDiscountType != 1) {//商品活动   订单活动没有直降
            statusBeans.add(new NameValue(RULE_ZJ.getValue(), RULE_ZJ.getKey()));
            statusBeans.add(new NameValue(RULE_ZQ.getValue(), RULE_ZQ.getKey()));
            statusBeans.add(new NameValue(RULE_MJ.getValue(), RULE_MJ.getKey()));
            statusBeans.add(new NameValue(RULE_DZ.getValue(), RULE_DZ.getKey()));
        } else if (mDiscountType == 1) {//订单活动
            statusBeans.add(new NameValue(RULE_ZQ.getValue(), RULE_ZQ.getKey()));
            statusBeans.add(new NameValue(RULE_MJ_ORDER.getValue(), RULE_MJ_ORDER.getKey()));
            statusBeans.add(new NameValue(RULE_MANZHE.getValue(), RULE_MANZHE.getKey()));
        }
        return statusBeans;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            /*选择活动区域*/
            case REQUEST_SELECT_AREA:
                if (resultCode == RESULT_OK) {
                    selectedAreaMap = (HashMap) data.getSerializableExtra(REQUEST_SELECT_AREA_NAME);
                    int provinceNum = selectedAreaMap.size();
                    int citysNum = 0;
                    Iterator<ArrayList<AreaBean.ChildBeanX>> iterator = selectedAreaMap.values().iterator();
                    while (iterator.hasNext()) {
                        citysNum += iterator.next().size();
                    }
                    mTxtAreaSelect.setText(citysNum == UIUtils.SUPPORT_CITY_NUM ? "全国" : String.format("%s个省，%s个市", provinceNum, citysNum));
                    mTxtAreaSelect.setTag(citysNum == UIUtils.SUPPORT_CITY_NUM);
                }
                break;
            case REQUEST_SELECT_COUPON:
                if (resultCode == RESULT_OK) {
                    mSelectCoupon = data.getParcelableExtra(CouponSelectListActivity.RESULT_NAME);
                    GiveBean giveBean = mCouponRuleAdapter.getData().get(currentSelectCouponIndex).getGiveList().get(0);
                    giveBean.setGiveTargetName(mSelectCoupon.getDiscountName());
                    giveBean.setGiveTargetID(mSelectCoupon.getDiscountID());
                    mCouponRuleAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void addSuccess(MarketingProductAddResp resp) {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_success)
                .setImageState(R.drawable.ic_dialog_state_success)
                .setMessageTitle("恭喜您活动创建成功")
                .setMessage("活动将会在设定时间开始\n" +
                        "如发觉活动有异常可进行作废操作")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    NavCallback callback = new NavCallback() {
                        @Override
                        public void onArrival(Postcard postcard) {
                            MarketingEvent event = new MarketingEvent();
                            event.setTarget(MarketingEvent.Target.MARKETING_PRODUCT_LIST);
                            event.setRefresh(true);
                            EventBus.getDefault().post(event);
                            if (item == 1) {
                                ProductMarketingCheckActivity.start(resp.getId(), mDiscountType);
                            }
                        }
                    };
                    ARouter.getInstance()
                            .build(RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST)
                            .setProvider(new LoginInterceptor())
                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            .navigation(this, callback);
                }, "返回列表", "查看详情")
                .create().show();
    }

    @Override
    public void modifySuccess(MarketingProductAddResp marketingProductAddResp) {
        showToast("编辑商品促销信息成功");
        MarketingEvent event = new MarketingEvent();
        event.setTarget(MarketingEvent.Target.MARKETING_PRODUCT_DETAIL);
        event.setRefresh(true);
        EventBus.getDefault().post(event);
        finish();
    }

    @Override
    public String getMarketingTheme() {
        return mEditTheme.getText().toString();
    }

    @Override
    public String getStartTime() {
        if (mTxtStartTime.getTag() == null) {
            return "";
        }
        return CalendarUtils.format((Date) mTxtStartTime.getTag(), TIME_FORMATE);
    }

    @Override
    public String getEndTime() {
        if (mTxtEndTime.getTag() == null) {
            return "";
        }
        return CalendarUtils.format((Date) mTxtEndTime.getTag(), TIME_FORMATE);
    }

    @Override
    public List<SkuGoodsBean> getProducts() {
        return mMarketingProductAdpater.getData();
    }

    @Override
    public int getRuleType() {
        if (mRuleSelect.getTag() == null) {
            return -1;
        }
        String type = ((NameValue) mRuleSelect.getTag()).getValue();
        return Integer.parseInt(type);
    }

    @Override
    public List<RuleListBean> getRuleList() {
        //如果是打折
        if (getRuleType() == Integer.parseInt(RULE_DZ.getKey())) {
            List<RuleListBean> ruleListBeans = new ArrayList<>();
            RuleListBean bean = new RuleListBean();
            bean.setRuleCondition("0");
            bean.setRuleDiscountValue(mEdtRuleDZ.getText().toString());
            ruleListBeans.add(bean);
            return ruleListBeans;
        } else {
            BaseQuickAdapter<RuleListBean, BaseViewHolder> currentAdapter;
            if (TextUtils.equals(getRuleType() + "", RULE_ZQ.getKey())) {
                currentAdapter = mCouponRuleAdapter;
            } else {
                currentAdapter = mMarketingRuleAdapter;
            }
            if (currentAdapter == null) {
                return null;
            }
            return currentAdapter.getData();
        }
    }

    @Override
    public List<AreaListBean> getAreaList() {
        if (selectedAreaMap.size() == 0) {
            return null;
        }
        List<AreaListBean> areaListBeans = new ArrayList<>();
        Iterator<ArrayList<AreaBean.ChildBeanX>> cityIterator = selectedAreaMap.values().iterator();
        while (cityIterator.hasNext()) {
            for (AreaBean.ChildBeanX cityBean : cityIterator.next()) {
                AreaListBean areaListBean = new AreaListBean();
                areaListBean.setCityCode(cityBean.getCode());
                areaListBean.setProvinceCode(cityBean.getpCode());
                areaListBean.setProvinceName(cityBean.getpName());
                areaListBean.setCityName(cityBean.getName());
                areaListBeans.add(areaListBean);
            }
        }
        return areaListBeans.size() == UIUtils.SUPPORT_CITY_NUM ? null : areaListBeans;
    }

    @Override
    public int getAreaScope() {
        return (boolean) mTxtAreaSelect.getTag() ? 1 : 2;
    }

    @Override
    public int getDiscountStage() {
        return mSwitchLadder.isChecked() ? 1 : 0;
    }

    @Override
    public int getCustomerScope() {
        return Integer.parseInt(mCustomerScope.getTag().toString());
    }

    @Override
    public String getId() {
        return mDetail != null ? mDetail.getId() : null;
    }

    @Override
    public int getDiscountType() {
        return mDiscountType;
    }

    @Override
    public List<MarketingCustomerBean> getCustomerList() {
        return mSelectCustomer;
    }
}
