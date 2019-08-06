package com.hll_sc_app.app.mine;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
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
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

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
        return rootView;
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
                refreshLayout.closeHeaderOrFooter();
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
        R.id.txt_return_audit, R.id.img_setting, R.id.txt_price_setting,R.id.txt_report_center, R.id.txt_return_time, R.id.txt_directional_selling})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_wallet:
                break;
            case R.id.txt_agreement_price:
                // 协议价管理
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE);
                break;
            case R.id.txt_warehouse_manage:
                break;
            case R.id.txt_cooperation_purchaser:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST);
                break;
            case R.id.txt_return_audit:
                AuditActivity.start();
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
            case R.id.txt_return_time:
                RouterUtil.goToActivity(RouterConfig.REFUND_TIME, 0);
                break;
            case R.id.txt_directional_selling:
                RouterUtil.goToActivity(RouterConfig.ORIENTATION_LIST);
                break;
            default:
                break;
        }
    }
}