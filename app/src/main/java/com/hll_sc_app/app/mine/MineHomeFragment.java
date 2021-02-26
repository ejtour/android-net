package com.hll_sc_app.app.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.audit.AuditActivity;
import com.hll_sc_app.app.agreementprice.AgreementPriceActivity;
import com.hll_sc_app.app.goods.assign.GoodsAssignActivity;
import com.hll_sc_app.app.goods.assign.GoodsAssignType;
import com.hll_sc_app.app.goodsdemand.GoodsDemandActivity;
import com.hll_sc_app.app.info.InfoActivity;
import com.hll_sc_app.app.menu.MenuActivity;
import com.hll_sc_app.app.menu.stratery.DeliveryMenu;
import com.hll_sc_app.app.menu.stratery.FeedbackMenu;
import com.hll_sc_app.app.menu.stratery.MarketingMenu;
import com.hll_sc_app.app.menu.stratery.ReportMenu;
import com.hll_sc_app.app.menu.stratery.StockMenu;
import com.hll_sc_app.app.setting.SettingActivity;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.bean.UserEvent;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.CountlyMgr;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.message.ApplyMessageResp;
import com.hll_sc_app.bean.message.UnreadResp;
import com.hll_sc_app.bean.mine.MenuItem;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.bean.web.WebParam;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.mine.MenuGridLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.ViewCollections;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
@Route(path = RouterConfig.ROOT_HOME_MINE)
public class MineHomeFragment extends BaseLoadFragment implements MineHomeFragmentContract.IHomeView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.fmm_parallax)
    ImageView mParallax;
    @BindView(R.id.fmm_avatar)
    GlideImageView mImgGroupLogoUrl;
    @BindView(R.id.fmm_group_name)
    TextView mTxtPurchaserName;
    @BindView(R.id.fmm_staff_name)
    TextView mTxtPurchaserUserName;
    @BindView(R.id.fmm_login)
    TextView mLogin;
    @BindView(R.id.fmm_user_group)
    LinearLayout mLlUserMessage;
    @BindViews({R.id.fmm_common_tools, R.id.fmm_base_tools, R.id.fmm_money_tools, R.id.fmm_customer_tools, R.id.fmm_comprehensive_tools})
    List<MenuGridLayout> mMenuViews;
    @BindView(R.id.fmm_scroll_view)
    NestedScrollView mScrollView;
    @BindView(R.id.fmm_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fmm_settings)
    ImageView mImgSetting;
    @BindView(R.id.fmm_title)
    TextView mTxtTitle;
    @BindView(R.id.fmm_help_image)
    ImageView mImgHelp;
    @BindView(R.id.fmm_title_bar)
    ViewGroup mFlHeader;
    Unbinder unbinder;
    @BindView(R.id.fmm_amount)
    TextView mAmount;
    @BindView(R.id.fmm_order)
    TextView mOrder;
    @BindView(R.id.fmm_rate)
    TextView mRate;
    @BindView(R.id.fmm_ave_price)
    TextView mAvePrice;
    @BindView(R.id.fmm_date)
    TextView mDate;
    @BindView(R.id.fmm_analysis_root)
    ConstraintLayout mAnalysisRoot;
    @BindView(R.id.fmm_bottom_area)
    View mBottomArea;
    private int mTopBgHeight;
    private MineHomeFragmentPresenter mPresenter;

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_mine, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mTopBgHeight = UIUtils.dip2px(220);
        initView();
        initData();
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleApplyMessage(ApplyMessageResp resp) {
        mMenuViews.get(0).setOnList(resp.getTotalNum() > 0 ? Collections.singletonList(MenuItem.CO_PURCHASER) : new ArrayList<>());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleDemandMessage(UnreadResp resp) {
        mMenuViews.get(3).setOnList(resp.getUnreadNum() > 0 ? Collections.singletonList(MenuItem.GOODS_DEMAND) : new ArrayList<>());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleUserEvent(UserEvent event) {
        if (event.getName().equals(UserEvent.ONLY_RECEIVE)) {
            ViewCollections.run(mMenuViews, (view, index) -> view.updateMenu());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == InfoActivity.REQ_CODE) {
            showUserInfo();
        }
    }

    private void initData() {
        mPresenter = MineHomeFragmentPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        StatusBarUtil.fitSystemWindowsWithPaddingTop(mFlHeader);
        StatusBarUtil.fitSystemWindowsWithMarginTop(mLlUserMessage);
        showUserInfo();
        mFlHeader.getBackground().mutate().setAlpha(0);
        mTxtTitle.setTextColor(Color.argb(0, 255, 255, 255));
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int alpha = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mFlHeader == null || mTxtTitle == null) return;
                if (scrollY <= mTopBgHeight) {
                    alpha = (int) (255 * (float) scrollY / mTopBgHeight);
                    mFlHeader.getBackground().mutate().setAlpha(alpha);
                    mTxtTitle.setTextColor(Color.argb(alpha, 255, 255, 255));
                } else {
                    if (alpha < 255) {
                        alpha = 255;
                        mFlHeader.getBackground().mutate().setAlpha(alpha);
                        mTxtTitle.setTextColor(Color.argb(alpha, 255, 255, 255));
                    }
                }
                if (mParallax == null) return;
                mParallax.setTranslationY(scrollY <= mTopBgHeight ? -scrollY : -mTopBgHeight);
            }
        });
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset,
                                       int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
                if (mParallax == null) return;
                mParallax.setScaleX((float) (1 + percent * 0.7));
                mParallax.setScaleY((float) (1 + percent * 0.7));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showUserInfo();
                mPresenter.refresh();
            }
        });
        if (BuildConfig.isOdm) {
            mImgHelp.setVisibility(View.GONE);
            mBottomArea.setVisibility(View.GONE);
        }
        ViewCollections.run(mMenuViews, (view, index) -> {
            view.setOnItemClickListener(this);
        });
    }

    private void showUserInfo() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            mLogin.setVisibility(View.GONE);
            mTxtPurchaserName.setVisibility(View.VISIBLE);
            mTxtPurchaserName.setText(userBean.getGroupName());
            mTxtPurchaserUserName.setVisibility(View.VISIBLE);
            mTxtPurchaserUserName.setText(userBean.getEmployeeName());
            mImgGroupLogoUrl.setImageURL(userBean.getGroupLogoUrl());
            return;
        }
        mLogin.setVisibility(View.VISIBLE);
        mTxtPurchaserName.setVisibility(View.GONE);
        mTxtPurchaserUserName.setVisibility(View.GONE);
        mImgGroupLogoUrl.setImageURL(0);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MenuItem item = (MenuItem) adapter.getItem(position);
        if (item == null) return;
        switch (item) {
            case WALLET:
                RouterUtil.goToActivity(RouterConfig.WALLET);
                break;
            case AGREEMENT_PRICE:
                // 协议价管理
                AgreementPriceActivity.start(this);
                break;
            case STAFF:
                //  员工管理
                RouterUtil.goToActivity(RouterConfig.STAFF_LIST);
                break;
            case DELIVERY:
                MenuActivity.start(DeliveryMenu.class.getSimpleName());
                break;
            case WAREHOUSE:
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_START);
                break;
            case CO_PURCHASER:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST);
                break;
            case RETURN_AUDIT:
                AuditActivity.start(0);
                break;
            case SELL_PRICE:
                RouterUtil.goToActivity(RouterConfig.PRICE_MANAGE);
                break;
            case REPORT_CENTER:
                MenuActivity.start(ReportMenu.class.getSimpleName());
                break;
            case INQUIRY:
                RouterUtil.goToActivity(RouterConfig.INQUIRY);
                break;
            case MARKETING:
                MenuActivity.start(MarketingMenu.class.getSimpleName());
                break;
            case RETURN_AGING:
                RouterUtil.goToActivity(RouterConfig.REFUND_TIME);
                break;
            case TARGET_SALE:
                GoodsAssignActivity.start(GoodsAssignType.TARGET_SALE);
                break;
            case SHOP:
                RouterUtil.goToActivity(RouterConfig.SUPPLIER_SHOP);
                break;
            case BILL_LIST:
                RouterUtil.goToActivity(RouterConfig.BILL_LIST);
                break;
            case PAYMENT:
                RouterUtil.goToActivity(RouterConfig.PAY_MANAGE);
                break;
            case INVOICE:
                RouterUtil.goToActivity(RouterConfig.INVOICE_ENTRY);
                break;
            case INSPECTION:
                RouterUtil.goToActivity(RouterConfig.INSPECTION_LIST);
                break;
            case INVENTORY:
                MenuActivity.start(StockMenu.class.getSimpleName());
                break;
            case COMPLIANT:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_MANAGE_LIST);
                break;
            case GOODS_DEMAND:
                GoodsDemandActivity.start();
                break;
            case MARKET_PRICE:
                RouterUtil.goToActivity(RouterConfig.PRICE);
                break;
            case PURCHASE_TEMPLATE:
                RouterUtil.goToActivity(RouterConfig.PURCHASE_TEMPLATE);
                break;
            case CARD:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_CARD_MANAGE_LIST);
                break;
            case GOODS_SPECIAL_DEMAND:
                RouterUtil.goToActivity(RouterConfig.GOODS_SPECIAL_DEMAND_ENTRY);
                break;
            case PRIVATE_MALL:
                RouterUtil.goToActivity(RouterConfig.PRIVATE_MALL);
                break;
            case ITEM_BLOCK_LIST:
                GoodsAssignActivity.start(GoodsAssignType.BLOCK_LIST);
                break;
            case APTITUDE:
                RouterUtil.goToActivity(RouterConfig.APTITUDE);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.fmm_settings, R.id.fmm_help_text, R.id.fmm_help_image, R.id.fmm_feedback,
            R.id.fmm_analysis_btn, R.id.fmm_user_group, R.id.fmm_invite_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fmm_settings:
                SettingActivity.start();
                break;
            case R.id.fmm_help_text:
            case R.id.fmm_help_image:
                CountlyMgr.recordView("帮助中心");
                String params = Base64.encodeToString(JsonUtil.toJson(new WebParam()).getBytes(), Base64.DEFAULT);
                WebActivity.start("帮助中心", HttpConfig.getWebHost() + "/help_mobile/?sourceData=" + params);
                break;
            case R.id.fmm_feedback:
                MenuActivity.start(FeedbackMenu.class.getSimpleName());
                break;
            case R.id.fmm_analysis_btn:
                RouterUtil.goToActivity(RouterConfig.OPERATION_ANALYSIS);
                break;
            case R.id.fmm_user_group:
                InfoActivity.start(requireActivity());
                break;
            case R.id.fmm_invite_code:
                RouterUtil.goToActivity(RouterConfig.INFO_INVITE_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(AnalysisBean bean) {
        mAnalysisRoot.setVisibility(View.VISIBLE);

        String amountSource = String.format("¥%s", CommonUtils.formatMoney(bean.getValidTradeAmount()));
        SpannableString ss = new SpannableString(amountSource);
        ss.setSpan(new RelativeSizeSpan(0.65f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new RelativeSizeSpan(0.65f), amountSource.indexOf("."), amountSource.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAmount.setText(ss);

        mOrder.setText(CommonUtils.formatNum(bean.getValidOrderNum()));

        String rateSource = String.format("环比增长：%s", bean.getAmountRate());
        SpannableString rate = new SpannableString(rateSource);
        if (bean.getAmountRate().startsWith("-")) {
            rate.setSpan(new ForegroundColorSpan(Color.parseColor("#48CFAD")), 5, rateSource.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mRate.setText(rate);

        String priceSource = String.format("单均：¥%s", CommonUtils.formatMoney(bean.getAverageTradeAmount()));
        SpannableString price = new SpannableString(priceSource);
        price.setSpan(new ForegroundColorSpan(Color.parseColor("#FFA940")), 3, priceSource.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAvePrice.setText(price);

        Date date = DateUtil.parse(bean.getDate());
        mDate.setText(String.format("以上数据统计周期为：%s - %s", CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(CalendarUtils.getDateAfter(date, 6), Constants.SLASH_YYYY_MM_DD)));
    }
}