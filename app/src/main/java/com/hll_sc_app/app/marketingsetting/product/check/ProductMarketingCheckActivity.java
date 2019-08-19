package com.hll_sc_app.app.marketingsetting.product.check;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingProductAdapter;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingRuleAdapter;
import com.hll_sc_app.app.marketingsetting.product.MarketingRule;
import com.hll_sc_app.app.marketingsetting.product.add.ProductMarketingAddActivity;
import com.hll_sc_app.app.marketingsetting.selectarea.SelectAreaActivity;
import com.hll_sc_app.app.marketingsetting.view.CouponRuleSelectView;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.MarketingEvent;
import com.hll_sc_app.bean.marketingsetting.AreaListBean;
import com.hll_sc_app.bean.marketingsetting.GiveBean;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;
import com.hll_sc_app.citymall.util.CalendarUtils;

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

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_HH_MM_SS;

/**
 * 商品促销查看页面
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_PRODUCT_DETAIL, extras = Constant.LOGIN_EXTRA)
public class ProductMarketingCheckActivity extends BaseLoadActivity implements IProductMarketingCheckContract.IView {
    /**
     * 未生效
     */
    private static final int STATUS_ACTIVE_NOT = 1;
    /**
     * 生效中
     */
    private static final int STATUS_ACTIVE_ING = 2;
    /**
     * 失效
     */
    private static final int STATUS_ACTIVE_LOSE = 3;
    /**
     * 已作废
     */
    private static final int STATUS_ACTIVE_INVALIDATE = 4;
    private static String ACTIVITY_TIME_FORMAT_IN = "yyyyMMddHHmm";
    private static String ACTIVITY_TIME_FORMAT_OUT = "yyyy/MM/dd HH:mm";
    private static String CREATE_TIME_FORMAT_OUT = "yyyy/MM/dd HH:mm:ss";
    @Autowired(name = "object0")
    String discountId;
    @BindView(R.id.txt_title)
    TextView mTitle;
    @BindView(R.id.txt_activity_time)
    TextView mActivityTime;
    @BindView(R.id.txt_status)
    TextView mStatus;
    @BindView(R.id.txt_create_time)
    TextView mCreateTime;
    @BindView(R.id.txt_rule)
    TextView mRuleContent;
    @BindView(R.id.txt_check_product)
    TextView mCheckProduct;
    @BindView(R.id.view_coupon)
    CouponRuleSelectView mCouponRuleSelectView;
    @BindView(R.id.rl_rule)
    RecyclerView mRuleList;
    @BindView(R.id.txt_area)
    TextView mArea;
    @BindView(R.id.img_area)
    ImageView mImgArea;
    @BindView(R.id.rcl_product)
    RecyclerView mActivityProductList;
    @BindView(R.id.ll_rule_dz)
    LinearLayout mllRuleDz;
    @BindView(R.id.txt_rule_dz)
    TextView mTxtRuleDz;
    @BindView(R.id.ll_button_bottom)
    LinearLayout mllButtonBottom;
    @BindView(R.id.txt_delete)
    TextView mTxtDelete;

    private Unbinder unbinder;
    private IProductMarketingCheckContract.IPresenter mPresenter;
    private MarketingDetailCheckResp mDetail;

    public static void start(String discountId) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_PRODUCT_DETAIL, discountId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_detail_product);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        mPresenter = ProductMarketingCheckPresenter.newInstance();
        mPresenter.register(this);
        initView();
        mPresenter.getMarketingDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mActivityProductList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public String getDiscountId() {
        return discountId;
    }

    @Override
    public void showDetai(MarketingDetailCheckResp resp) {
        mDetail = resp;
        showTitle(resp);
        showActivityProducts(resp);
        showRuleList(resp);
        showArea(resp);
        showBottomButton(resp);
    }

    /**
     * 显示头部区域
     *
     * @param resp
     */
    private void showTitle(MarketingDetailCheckResp resp) {
        mTitle.setText(resp.getDiscountName());
        mActivityTime.setText(String.format("活动时间: %s-%s", transformActivityTime(resp.getDiscountStartTime()), transformActivityTime(resp.getDiscountEndTime())));
        mCreateTime.setText(String.format("创建时间：%s / %s", resp.getCreateBy(), CalendarUtils.getDateFormatString(resp.getCreateTime(), FORMAT_HH_MM_SS, CREATE_TIME_FORMAT_OUT)));
        mStatus.setText(resp.getDiscountStatusName());
        setMarketingStatus(resp);
        mRuleContent.setText("促销规则：" + MarketingRule.getRuleEnum(resp.getDiscountRuleType()).getValue() + (resp.getDiscountStage() != 0 ? "/阶梯促销" : ""));
    }

    /**
     * 显示活动商品
     *
     * @param resp
     */
    private void showActivityProducts(MarketingDetailCheckResp resp) {
        MarketingProductAdapter marketingProductAdapter = new MarketingProductAdapter(null, MarketingProductAdapter.Modal.SHOW);
        if (resp.getProductList().size() > 3) {
            marketingProductAdapter.setItemCount(3);
        }
        marketingProductAdapter.setNewData(resp.getProductList());
        mActivityProductList.setAdapter(marketingProductAdapter);
        mCheckProduct.setVisibility(resp.getProductList().size() > 3 ? View.VISIBLE : View.GONE);
        mCheckProduct.setOnClickListener(v -> {
            SelectProductListActivity.start(resp.getProductList());
        });
    }

    /**
     * 显示促销规则
     *
     * @param resp
     */
    private void showRuleList(MarketingDetailCheckResp resp) {
        if (TextUtils.equals(resp.getDiscountRuleType() + "", MarketingRule.RULE_ZQ.getKey())) {
            mCouponRuleSelectView.setVisibility(View.VISIBLE);
            RuleListBean ruleBean = resp.getRuleList().get(0);
            List<GiveBean> giveBeans = JsonUtil.parseJsonList(ruleBean.getGiveTarget(), GiveBean.class);
            mCouponRuleSelectView.setData(ruleBean.getRuleCondition() + "", giveBeans.get(0));
        } else if (TextUtils.equals(resp.getDiscountRuleType() + "", MarketingRule.RULE_DZ.getKey())) {
            mllRuleDz.setVisibility(View.VISIBLE);
            RuleListBean ruleBean = resp.getRuleList().get(0);
            mTxtRuleDz.setText(ruleBean.getRuleDiscountValue());
        } else {
            MarketingRuleAdapter ruleAdapter = new MarketingRuleAdapter(null, false);
            ruleAdapter.setRultType(resp.getDiscountRuleType());
            ruleAdapter.setNewData(resp.getRuleList());
            mRuleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRuleList.setAdapter(ruleAdapter);
        }

    }

    /**
     * 显示地区
     *
     * @param resp
     */
    private void showArea(MarketingDetailCheckResp resp) {
        mArea.setText(resp.getAreaDesc());
        if (TextUtils.equals(resp.getAreaDesc(), "全国")) {
            mImgArea.setVisibility(View.GONE);
        } else {
            mImgArea.setVisibility(View.VISIBLE);
            mArea.setOnClickListener(v -> {
                SelectAreaActivity.start("查看活动区域", transformArea(resp.getAreaList()));
            });
        }
    }

    /**
     * 底部按钮
     * 生效中：作废活动
     * 未生效：作废活动 编辑活动
     *
     * @param resp
     */
    private void showBottomButton(MarketingDetailCheckResp resp) {
        if (resp.getDiscountStatus() == STATUS_ACTIVE_ING || resp.getDiscountStatus() == STATUS_ACTIVE_NOT) {
            mllButtonBottom.setVisibility(View.VISIBLE);
            if (resp.getDiscountStatus() == STATUS_ACTIVE_ING) {
                mTxtDelete.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 时间格式化
     *
     * @param originTime
     * @return
     */
    private String transformActivityTime(String originTime) {
        return CalendarUtils.getDateFormatString(originTime, ACTIVITY_TIME_FORMAT_IN, ACTIVITY_TIME_FORMAT_OUT);
    }

    /**
     * 设置头部的状态 颜色 背景 文字
     */
    private void setMarketingStatus(MarketingDetailCheckResp resp) {
        GradientDrawable gradientDrawable = (GradientDrawable)mStatus.getBackground();
        switch (resp.getDiscountStatus()) {
            case STATUS_ACTIVE_NOT:
                gradientDrawable.setColor(getResources().getColor(R.color.color_FFF1B8));
                mStatus.setTextColor(Color.parseColor("#F6BB42"));
                break;
            case STATUS_ACTIVE_ING:
                gradientDrawable.setColor(getResources().getColor(R.color.color_E6FED1));
                mStatus.setTextColor(Color.parseColor("#52C41A"));
                break;
            case STATUS_ACTIVE_LOSE:
                gradientDrawable.setColor(getResources().getColor(R.color.color_eeeeee));
                mStatus.setTextColor(Color.parseColor("#999999"));
                break;
            case STATUS_ACTIVE_INVALIDATE:
                gradientDrawable.setColor(getResources().getColor(R.color.color_eeeeee));
                mStatus.setTextColor(Color.parseColor("#999999"));
                break;
            default:
                break;
        }
        mStatus.setText(resp.getDiscountStatusName());
    }

    /**
     * 将请求回来的地区数据转换格式
     *
     * @param areaListBeans
     * @return
     */
    private ArrayList<AreaBean> transformArea(List<AreaListBean> areaListBeans) {
        //key provincecode value:数组的index
        Map<String, Integer> provinceCodeLog = new HashMap<>();
        ArrayList<AreaBean> outputBean = new ArrayList<>();
        for (int i = 0; i < areaListBeans.size(); i++) {
            AreaListBean currentArea = areaListBeans.get(i);
            AreaBean.ChildBeanX cityBean = new AreaBean.ChildBeanX();
            cityBean.setName(currentArea.getCityName());
            cityBean.setCode(currentArea.getCityCode());
            if (provinceCodeLog.containsKey(currentArea.getProvinceCode())) {
                outputBean.get(provinceCodeLog.get(currentArea.getProvinceCode())).getChild().add(cityBean);
            } else {
                provinceCodeLog.put(currentArea.getProvinceCode(), outputBean.size());
                AreaBean provinceBean = new AreaBean();
                provinceBean.setName(currentArea.getProvinceName());
                provinceBean.setCode(currentArea.getProvinceCode());
                List<AreaBean.ChildBeanX> cityBeans = new ArrayList<>();
                cityBeans.add(cityBean);
                provinceBean.setChild(cityBeans);
                outputBean.add(provinceBean);
            }
        }
        return outputBean;
    }

    @Override
    public void changeMarketingStatusSuccess() {
        showToast("作废成功");
        MarketingEvent event = new MarketingEvent();
        event.setTarget(MarketingEvent.Target.MARKETING_PRODUCT_LIST);
        event.setRefreshProductList(true);
        EventBus.getDefault().post(event);
        finish();
    }

    @OnClick({R.id.txt_delete, R.id.txt_edt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_delete:
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("确定要作废这个活动么")
                        .setMessage("如发觉活动有异常可进行作废操作\n" +
                                "作废后活动终止")
                        .setButton((dialog, index) -> {
                            dialog.dismiss();
                            if (index == 1) {
                                mPresenter.changeMarketingStatus();
                            }
                        }, "我再看看", "确定作废")
                        .create().show();
                break;
            case R.id.txt_edt:
                ProductMarketingAddActivity.start(mDetail);
                break;
            default:
                break;
        }
    }


    @Subscribe
    public void onEvent(MarketingEvent event) {
        if (event.getTarget() != MarketingEvent.Target.MARKETING_PRODUCT_DETAIL) {
            return;
        }
        if (event.isRefreshProductDetail()) {
            mPresenter.getMarketingDetail();
        }
    }

}
