package com.hll_sc_app.app.marketingsetting.product.check;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingProductAdapter;
import com.hll_sc_app.app.marketingsetting.product.MarketingRule;
import com.hll_sc_app.app.marketingsetting.view.CouponRuleSelectView;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;
import com.hll_sc_app.citymall.util.CalendarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private static String TIME_FORMAT_IN = "yyyyMMddHHmm";
    private static String TIME_FORMAT_OUT = "yyyy/MM/dd HH:mm";
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
    @BindView(R.id.rcl_product)
    RecyclerView mActivityProductList;
    private Unbinder unbinder;
    private IProductMarketingCheckContract.IPresenter mPresenter;

    public static void start(String discountId) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_PRODUCT_DETAIL, discountId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_detail_product);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresenter = ProductMarketingCheckPresenter.newInstance();
        mPresenter.register(this);
        initView();
        mPresenter.getMarketingDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
        showTitle(resp);
        showActivityProducts(resp);
    }

    /**
     * 显示头部区域
     *
     * @param resp
     */
    private void showTitle(MarketingDetailCheckResp resp) {
        mTitle.setText(resp.getDiscountName());
        mActivityTime.setText(String.format("活动时间: %s-%s", transformTime(resp.getDiscountStartTime()), transformTime(resp.getDiscountEndTime())));
        mCreateTime.setText(String.format("创建时间：%s / %s", resp.getCreateBy(), transformTime(resp.getCreateTime())));
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
        marketingProductAdapter.setNewData(resp.getProductList());
        mActivityProductList.setAdapter(marketingProductAdapter);
        mCheckProduct.setVisibility(resp.getProductList().size() > 2 ? View.VISIBLE : View.GONE);
        mCheckProduct.setOnClickListener(v -> {

        });
    }

    /**
     * 时间格式化
     *
     * @param originTime
     * @return
     */
    private String transformTime(String originTime) {
        return CalendarUtils.getDateFormatString(originTime, TIME_FORMAT_IN, TIME_FORMAT_OUT);
    }

    /**
     * 设置头部的状态 颜色 背景 文字
     */
    private void setMarketingStatus(MarketingDetailCheckResp resp) {
        switch (resp.getDiscountStatus()) {
            case STATUS_ACTIVE_NOT:
                mStatus.setBackgroundColor(Color.parseColor("#FFF1B8"));
                mStatus.setTextColor(Color.parseColor("#F6BB42"));
                break;
            case STATUS_ACTIVE_ING:
                mStatus.setBackgroundColor(Color.parseColor("#E6FED1"));
                mStatus.setTextColor(Color.parseColor("#52C41A"));
                break;
            case STATUS_ACTIVE_LOSE:
                mStatus.setBackgroundColor(Color.parseColor("#EEEEEE"));
                mStatus.setTextColor(Color.parseColor("#999999"));
                break;
            case STATUS_ACTIVE_INVALIDATE:
                mStatus.setBackgroundColor(Color.parseColor("#EEEEEE"));
                mStatus.setTextColor(Color.parseColor("#999999"));
                break;
            default:
                break;
        }
        mStatus.setText(resp.getDiscountStatusName());
    }


}
