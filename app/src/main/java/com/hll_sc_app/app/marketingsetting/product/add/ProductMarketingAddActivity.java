package com.hll_sc_app.app.marketingsetting.product.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingProductAdapter;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingRuleAdapter;
import com.hll_sc_app.app.marketingsetting.product.selectcoupon.CouponSelectListActivity;
import com.hll_sc_app.app.marketingsetting.selectarea.SelectAreaActivity;
import com.hll_sc_app.app.marketingsetting.selectproduct.ProductSelectActivity;
import com.hll_sc_app.app.marketingsetting.view.CouponRuleSelectView;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.MarketingEvent;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.marketingsetting.AreaListBean;
import com.hll_sc_app.bean.marketingsetting.CouponListBean;
import com.hll_sc_app.bean.marketingsetting.GiveBean;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.DateTimePickerDialog;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.app.marketingsetting.product.add.MarketingRule.RULE_DZ;
import static com.hll_sc_app.app.marketingsetting.product.add.MarketingRule.RULE_MJ;
import static com.hll_sc_app.app.marketingsetting.product.add.MarketingRule.RULE_MZ;
import static com.hll_sc_app.app.marketingsetting.product.add.MarketingRule.RULE_ZJ;
import static com.hll_sc_app.app.marketingsetting.product.add.MarketingRule.RULE_ZQ;
import static com.hll_sc_app.app.marketingsetting.selectarea.SelectAreaActivity.ALL_CITYS_NUM;

/**
 * 商品促销列表
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
    @BindView(R.id.view_coupon_select)
    CouponRuleSelectView mCouponRuleSelectView;
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
    private Unbinder unbinder;
    private DateTimePickerDialog.Builder mDateTimeDialogBuilder;
    private SingleSelectionDialog mSingleRuleDilog;
    private MarketingRuleAdapter mMarketingRuleAdapter;
    private ProductMarketingAddPresenter mPresenter;
    private CouponListBean mSelectCoupon;
    /**
     * 选择的时间
     */
    private long startTime = Calendar.getInstance().getTimeInMillis();
    private long endTime = startTime;


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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_product_add);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);

    }

    private void initView() {
        mPresenter = ProductMarketingAddPresenter.newInstance();
        mPresenter.register(this);
        /*活动商品列表*/
        mMarketingProductList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mMarketingProductAdpater = new MarketingProductAdapter(null, MarketingProductAdapter.Modal.EDIT);
        mMarketingProductList.setAdapter(mMarketingProductAdpater);
        mMarketingProductAdpater.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.img_delete) {
                adapter.remove(position);
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
            } else {//关闭阶梯
                if (mListRule.getVisibility() == View.VISIBLE && mMarketingRuleAdapter.getData().size() > 1) {
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

        /*赠券点击跳转优惠券*/
        mCouponRuleSelectView.setSelectListener(() -> {
            CouponSelectListActivity.start(this, REQUEST_SELECT_COUPON, mSelectCoupon);
        });
    }

    /**
     * 新增一条规则
     *
     * @param ruleType
     */
    private void addRule(int ruleType) {
        if (mMarketingRuleAdapter == null) {
            return;
        }
        RuleListBean ruleListBean = new RuleListBean();
        ruleListBean.setRuleType(ruleType);
        mMarketingRuleAdapter.addData(ruleListBean);
    }

    /**
     * 保存
     */
    private void onSave() {
        if (checkInput()) {
            mPresenter.addMarketingProduct();
        }
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
        } else if (getAreaList().size() == 0) {
            showToast("请选择活动区域女");
            return false;
        } else if (mMarketingProductAdpater.getData().size() == 0) {
            showToast("请选择活动商品");
            return false;
        }
        //直降、满减、满赠
        if (TextUtils.equals(getRuleType() + "", RULE_ZJ.getKey()) ||
                TextUtils.equals(getRuleType() + "", RULE_MZ.getKey()) ||
                TextUtils.equals(getRuleType() + "", RULE_MJ.getKey())
        ) {
            if (mMarketingRuleAdapter == null || mMarketingProductAdpater.getData().size() == 0) {
                showToast("请填写至少一个促销规则");
                return false;
            }
        }
        //赠券
        if (TextUtils.equals(getRuleType() + "", RULE_ZQ.getKey()) && !mCouponRuleSelectView.isInputComplete()) {
            showToast("请填写完整的赠券规则");
            return false;
        }
        //打折
        if (TextUtils.equals(getRuleType() + "", RULE_DZ.getKey()) && mEdtRuleDZ.getText().toString().trim().length() == 0) {
            showToast("请填写打折规则");
            return false;
        }

        return true;
    }

    @Subscribe
    public void onEvent(List<SkuGoodsBean> skuGoodsBeans) {
        if (CommonUtils.isEmpty(skuGoodsBeans)) {
            mEmptyWord.setVisibility(View.VISIBLE);
        } else {
            mEmptyWord.setVisibility(View.GONE);
            mMarketingProductAdpater.setNewData(skuGoodsBeans);
        }
    }

    @OnClick({R.id.txt_time_start_select, R.id.txt_time_end_select, R.id.txt_add_product, R.id.txt_rule_select, R.id.txt_area_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_time_start_select:
                initDateTimeBuilder();
                mDateTimeDialogBuilder = mDateTimeDialogBuilder
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
                ProductSelectActivity.start(ProductMarketingAddActivity.class.getSimpleName(), "选择活动商品", new ArrayList<>(mMarketingProductAdpater.getData()));
                break;
            case R.id.txt_rule_select:
                /*促销规则类型window*/
                if (mSingleRuleDilog == null) {
                    mSingleRuleDilog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                            .refreshList(getRules())
                            .setTitleText("选择类型")
                            .select((NameValue) mRuleSelect.getTag())
                            .setOnSelectListener(bean -> {
                                mRuleSelect.setText(bean.getName());
                                mRuleSelect.setTag(bean);
                                toggleRuleType(bean);
                            }).create();
                }
                mSingleRuleDilog.show();
                break;
            case R.id.txt_area_select:
                SelectAreaActivity.start(this, REQUEST_SELECT_AREA, REQUEST_SELECT_AREA_NAME, "选择区域", selectedAreaMap);
                break;
            default:
                break;
        }
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
        statusBeans.add(new NameValue(RULE_ZJ.getValue(), RULE_ZJ.getKey()));
        statusBeans.add(new NameValue(RULE_MZ.getValue(), RULE_MZ.getKey()));
        statusBeans.add(new NameValue(RULE_ZQ.getValue(), RULE_ZQ.getKey()));
        statusBeans.add(new NameValue(RULE_MJ.getValue(), RULE_MJ.getKey()));
        statusBeans.add(new NameValue(RULE_DZ.getValue(), RULE_DZ.getKey()));
        return statusBeans;
    }

    private void toggleRuleType(NameValue nameValue) {
        String name = nameValue.getName();
        if (TextUtils.equals(name, RULE_ZJ.getValue())) {
            mCouponRuleSelectView.setVisibility(View.GONE);
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
            mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
            initRuleListAdapter(Integer.parseInt(nameValue.getValue()));
        } else if (TextUtils.equals(name, RULE_MZ.getValue())) {
            mCouponRuleSelectView.setVisibility(View.GONE);
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
            mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
            initRuleListAdapter(Integer.parseInt(nameValue.getValue()));
        } else if (TextUtils.equals(name, RULE_ZQ.getValue())) {
            mCouponRuleSelectView.setVisibility(View.VISIBLE);
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.GONE);
            mRuleAdd.setVisibility(View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(name, RULE_MJ.getValue())) {
            mCouponRuleSelectView.setVisibility(View.GONE);
            mRuleDZLayout.setVisibility(View.GONE);
            mListRule.setVisibility(View.VISIBLE);
            mRuleAdd.setVisibility(mSwitchLadder.isChecked() ? View.VISIBLE : View.GONE);
            mGroupLadder.setVisibility(View.VISIBLE);
            initRuleListAdapter(Integer.parseInt(nameValue.getValue()));
        } else if (TextUtils.equals(name, RULE_DZ.getValue())) {
            mCouponRuleSelectView.setVisibility(View.GONE);
            mRuleDZLayout.setVisibility(View.VISIBLE);
            mListRule.setVisibility(View.GONE);
            mRuleAdd.setVisibility(View.GONE);
            mGroupLadder.setVisibility(View.GONE);

        }
    }

    private void initRuleListAdapter(int ruleType) {
        if (mMarketingRuleAdapter == null) {
            mMarketingRuleAdapter = new MarketingRuleAdapter(null);
            mListRule.setAdapter(mMarketingRuleAdapter);
            mMarketingRuleAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                adapter.remove(position);
            });

        }
        RuleListBean ruleListBean = new RuleListBean();
        mMarketingRuleAdapter.setRultType(ruleType);
        mMarketingRuleAdapter.setNewData(null);
        mMarketingRuleAdapter.addData(ruleListBean);
        mMarketingRuleAdapter.notifyDataSetChanged();
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
                    mTxtAreaSelect.setText(citysNum == ALL_CITYS_NUM ? "全国" : String.format("%s个省，%s个市", provinceNum, citysNum));
                    mTxtAreaSelect.setTag(citysNum == ALL_CITYS_NUM);
                }
                break;
            case REQUEST_SELECT_COUPON:
                if (resultCode == RESULT_OK) {
                    mSelectCoupon = data.getParcelableExtra(CouponSelectListActivity.RESULT_NAME);
                    mCouponRuleSelectView.setCouponName(mSelectCoupon.getDiscountName());

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void addSuccess() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_success)
                .setImageState(R.drawable.ic_dialog_state_success)
                .setMessageTitle("恭喜您活动创建成功")
                .setMessage("活动将会在设定时间开始\n" +
                        "如发觉活动有异常可进行作废操作")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        //todo 查看详情-zc
                    } else {
                        MarketingEvent event = new MarketingEvent();
                        event.setRefreshProductList(true);
                        EventBus.getDefault().post(event);
                        finish();
                    }
                }, "返回列表", "查看详情")
                .create().show();
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
        }
        //赠券
        else if (getRuleType() == Integer.parseInt(RULE_ZQ.getKey())) {
            List<RuleListBean> ruleListBeans = new ArrayList<>();
            RuleListBean bean = new RuleListBean();
            bean.setRuleCondition(mCouponRuleSelectView.getValue()[0]);
            bean.setRuleDiscountValue(mCouponRuleSelectView.getValue()[1]);
            GiveBean giveBean = new GiveBean();
            giveBean.setGiveCount(mCouponRuleSelectView.getValue()[1]);
            if (mSelectCoupon != null) {
                giveBean.setGiveTargetID(mSelectCoupon.getDiscountID());
                giveBean.setGiveTargetName(mSelectCoupon.getDiscountName());
            }
            giveBean.setGiveType(2);
            bean.setGiveList(Arrays.asList(giveBean));
            ruleListBeans.add(bean);
            return ruleListBeans;
        } else {
            if (mMarketingRuleAdapter == null) {
                return null;
            }
            return mMarketingRuleAdapter.getData();
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
        return areaListBeans.size() == ALL_CITYS_NUM ? null : areaListBeans;
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
        return 1;
    }

}
