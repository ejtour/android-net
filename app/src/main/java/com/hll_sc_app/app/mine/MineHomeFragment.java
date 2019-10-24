package com.hll_sc_app.app.mine;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.audit.AuditActivity;
import com.hll_sc_app.app.helpcenter.HelpCenterJsParams;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.HttpConfig;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.operationanalysis.AnalysisBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.text.NumberFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
@Route(path = RouterConfig.ROOT_HOME_MINE)
public class MineHomeFragment extends BaseLoadFragment implements MineHomeFragmentContract.IHomeView {
    @BindView(R.id.parallax)
    ImageView mParallax;
    @BindView(R.id.img_groupLogoUrl)
    GlideImageView mImgGroupLogoUrl;
    @BindView(R.id.txt_purchaserName)
    TextView mTxtPurchaserName;
    @BindView(R.id.txt_purchaserUserName)
    TextView mTxtPurchaserUserName;
    @BindView(R.id.login)
    TextView mLogin;
    @BindView(R.id.ll_user_message)
    LinearLayout mLlUserMessage;
    @BindView(R.id.card_title1)
    TextView mCardTitle1;
    @BindView(R.id.border1)
    View mBorder1;
    @BindView(R.id.txt_wallet)
    TextView mTxtWallet;
    @BindView(R.id.txt_agreement_price)
    TextView mTxtAgreementPrice;
    @BindView(R.id.txt_warehouse_manage)
    TextView mTxtWarehouseManage;
    @BindView(R.id.txt_cooperation_purchaser)
    TextView mTxtCooperationPurchaser;
    @BindView(R.id.rl_order)
    RelativeLayout mRlOrder;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.view_status_bar)
    View mViewStatusBar;
    @BindView(R.id.img_message)
    ImageView mImgMessage;
    @BindView(R.id.img_setting)
    ImageView mImgSetting;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_messageCount)
    TextView mTxtMessageCount;
    @BindView(R.id.img_help)
    ImageView mImgHelp;
    @BindView(R.id.rl_header)
    LinearLayout mRlHeader;
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
    private MineHomeFragmentPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_mine, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        initData();
        return rootView;
    }

    private void initData() {
        mPresenter = MineHomeFragmentPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        showStatusBar();
        showUserInfo();
        int titleBarHeight = UIUtils.dip2px(getResources().getDimensionPixelOffset(R.dimen.title_bar_height));
        mRlHeader.getBackground().mutate().setAlpha(0);
        mTxtTitle.setTextColor(Color.argb(0, 255, 255, 255));
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int alpha = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= titleBarHeight) {
                    alpha = (int) (255 * (float) scrollY / titleBarHeight);
                    mRlHeader.getBackground().mutate().setAlpha(alpha);
                    mTxtTitle.setTextColor(Color.argb(alpha, 255, 255, 255));
                } else {
                    if (alpha < 255) {
                        alpha = 255;
                        mRlHeader.getBackground().mutate().setAlpha(alpha);
                        mTxtTitle.setTextColor(Color.argb(alpha, 255, 255, 255));
                    }
                }
            }
        });
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset,
                                       int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
                mParallax.setScaleX((float) (1 + percent * 0.7));
                mParallax.setScaleY((float) (1 + percent * 0.7));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showUserInfo();
                mPresenter.refresh();
            }
        });
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewStatusBar.getLayoutParams();
            params.height = ViewUtils.getStatusBarHeight(requireContext());
        }
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

    @OnClick({R.id.txt_wallet, R.id.txt_agreement_price, R.id.txt_warehouse_manage, R.id.txt_cooperation_purchaser,
            R.id.txt_return_audit, R.id.img_setting, R.id.txt_price_setting, R.id.txt_report_center,
            R.id.txt_staff_manage, R.id.txt_delivery_manage, R.id.txt_return_time, R.id.txt_directional_selling,
            R.id.txt_store_manage, R.id.txt_account_statement, R.id.txt_payment_settings, R.id.txt_invoice_manage,
            R.id.txt_marketing_settings, R.id.img_help, R.id.ll_help, R.id.txt_check_inspection, R.id.txt_inventory_manage,
            R.id.txt_complaint_manage, R.id.txt_main_feedback, R.id.fmm_analysis_btn, R.id.txt_new_product_demand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_wallet:
                RouterUtil.goToActivity(RouterConfig.WALLET);
                break;
            case R.id.txt_agreement_price:
                // 协议价管理
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE);
                break;
            case R.id.txt_staff_manage:
                //  员工管理
                RouterUtil.goToActivity(RouterConfig.STAFF_LIST);
                break;
            case R.id.txt_delivery_manage:
                RouterUtil.goToActivity(RouterConfig.DELIVERY_MANAGE);
                break;
            case R.id.txt_warehouse_manage:
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_START);
                break;
            case R.id.txt_cooperation_purchaser:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST);
                break;
            case R.id.txt_return_audit:
                AuditActivity.start(0);
                break;
            case R.id.img_setting:
                RouterUtil.goToActivity(RouterConfig.SETTING);
                break;
            case R.id.txt_price_setting:
                RouterUtil.goToActivity(RouterConfig.PRICE_MANAGE);
                break;
            case R.id.txt_report_center:
                RouterUtil.goToActivity(RouterConfig.REPORT_ENTRY);
                break;
            case R.id.txt_marketing_settings:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_SETTING_MENU);
                break;
            case R.id.txt_return_time:
                RouterUtil.goToActivity(RouterConfig.REFUND_TIME);
                break;
            case R.id.txt_directional_selling:
                RouterUtil.goToActivity(RouterConfig.ORIENTATION_LIST);
                break;
            case R.id.txt_store_manage:
                RouterUtil.goToActivity(RouterConfig.SUPPLIER_SHOP);
                break;
            case R.id.txt_account_statement:
                RouterUtil.goToActivity(RouterConfig.BILL_LIST);
                break;
            case R.id.txt_payment_settings:
                RouterUtil.goToActivity(RouterConfig.PAY_MANAGE);
                break;
            case R.id.txt_invoice_manage:
                RouterUtil.goToActivity(RouterConfig.INVOICE_ENTRY);
                break;
            case R.id.ll_help:
            case R.id.img_help:
                String params = Base64.encodeToString(JsonUtil.toJson(new HelpCenterJsParams()).getBytes(), Base64.DEFAULT);
                WebActivity.start("帮助中心", HttpConfig.getHelpCenterHost() + "/?sourceData=" + params);
                break;
            case R.id.txt_check_inspection:
                RouterUtil.goToActivity(RouterConfig.INSPECTION_LIST);
                break;
            case R.id.txt_inventory_manage:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_STOCK_MANAGE_MENU);
                break;
            case R.id.txt_complaint_manage:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_MANAGE_LIST);
                break;
            case R.id.txt_main_feedback:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_FEED_BACK_COMPLAIN);
                break;
            case R.id.fmm_analysis_btn:
                RouterUtil.goToActivity(RouterConfig.OPERATION_ANALYSIS);
                break;
            case R.id.txt_new_product_demand:
                RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND);
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

        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(2);
        percentInstance.setMinimumFractionDigits(2);
        mOrder.setText(CommonUtils.formatNum(bean.getValidOrderNum()));

        String rateSource = String.format("环比增长：%s", percentInstance.format(bean.getRelativeRatio()));
        SpannableString rate = new SpannableString(rateSource);
        if (bean.getRelativeRatio() < 0) {
            rate.setSpan(new ForegroundColorSpan(Color.parseColor("#48CFAD")), 5, rateSource.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mRate.setText(rate);

        String priceSource = String.format("单均：¥%s", CommonUtils.formatMoney(bean.getAverageTradeAmount()));
        SpannableString price = new SpannableString(priceSource);
        price.setSpan(new ForegroundColorSpan(Color.parseColor("#FFA940")), 3, priceSource.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAvePrice.setText(price);

        Date date = DateUtil.parse(bean.getDate());
        mDate.setText(String.format("以上数据统计周期为：%s - %s", CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(CalendarUtils.getWeekDate(date, 0, 7), Constants.SLASH_YYYY_MM_DD)));
    }
}