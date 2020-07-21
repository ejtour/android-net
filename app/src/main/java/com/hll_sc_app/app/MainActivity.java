package com.hll_sc_app.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.message.MessageActivity;
import com.hll_sc_app.app.submit.BackType;
import com.hll_sc_app.app.submit.IBackType;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.CountlyMgr;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.TipRadioButton;
import com.hll_sc_app.bean.event.MessageEvent;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.message.ApplyMessageResp;
import com.hll_sc_app.bean.message.UnreadResp;
import com.hll_sc_app.bean.notification.Page;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.impl.IReload;
import com.hll_sc_app.receiver.NotificationMessageReceiver;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.MessageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * 首页
 *
 * @author zhuyingsong
 * @date 2019/5/31
 */
@Route(path = RouterConfig.ROOT_HOME, extras = Constant.LOGIN_EXTRA)
public class MainActivity extends BaseLoadActivity implements IBackType {
    @BindView(R.id.group_type)
    RadioGroup mGroupType;
    @BindView(R.id.txt_messageCount)
    TextView mMessageCount;
    @BindView(R.id.img_message)
    ImageView mMessage;
    @Autowired(name = "parcelable")
    Page mPage;
    private int mOldFragmentTag;
    private long mExitTime;
    private MessageUtil mMessageUtil;
    private boolean mHasApply;
    private boolean mHasDemand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setTranslucent(getWindow(), true);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
        User.queryAuthList(this);
        ARouter.getInstance().inject(this);
        NotificationMessageReceiver.handleNotification(mPage);
        if (!BuildConfig.isDebug) {
            mMessageUtil = new MessageUtil();
        }
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mMessage.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(this);
        }
    }

    private void initView() {
        showStatusBar();
        mGroupType.setOnCheckedChangeListener((group, checkedId) -> setCurrentTab(checkedId));
        if (!UserConfig.crm()) {
            addRatioButton(PageType.SUPPLIER_HOME, "首页", getResources().getDrawable(R.drawable.bg_main_button_home));
            addRatioButton(PageType.SUPPLIER_ORDER, "订单管理", getResources().getDrawable(R.drawable.bg_main_button_order));
            addRatioButton(PageType.SUPPLIER_GOODS, "商品管理", getResources().getDrawable(R.drawable.bg_main_button_goods));
            addRatioButton(PageType.SUPPLIER_MINE, "我的", getResources().getDrawable(R.drawable.bg_main_button_mine));
            mGroupType.check(PageType.SUPPLIER_HOME);
        } else {
            addRatioButton(PageType.CRM_HOME, "首页", getResources().getDrawable(R.drawable.bg_main_button_home));
            addRatioButton(PageType.CRM_ORDER, "订单", getResources().getDrawable(R.drawable.bg_main_button_order));
            addRatioButton(PageType.CRM_CUSTOMER, "客户", getResources().getDrawable(R.drawable.bg_main_button_customer));
            addRatioButton(PageType.CRM_DAILY, "日报", getResources().getDrawable(R.drawable.bg_main_button_daily));
            addRatioButton(PageType.CRM_MINE, "我的", getResources().getDrawable(R.drawable.bg_main_button_mine));
            mGroupType.check(PageType.CRM_HOME);
        }
    }

    @SuppressLint("ResourceType")
    private void addRatioButton(@PageType int page, String name, Drawable res) {
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        TipRadioButton radioButton = new TipRadioButton(this);
        radioButton.setButtonDrawable(null);
        radioButton.setCompoundDrawablePadding(UIUtils.dip2px(3));
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setPadding(0, UIUtils.dip2px(8), 0, UIUtils.dip2px(3));
        radioButton.setText(name);
        radioButton.setId(page);
        radioButton.setTextSize(10);
        radioButton.setTextColor(ContextCompat.getColorStateList(this, R.drawable.base_color_state_on_pri_off_666));
        res.setBounds(0, 0, res.getIntrinsicWidth(), res.getIntrinsicHeight());
        radioButton.setCompoundDrawables(null, res, null, null);
        mGroupType.addView(radioButton, layoutParams);
    }

    @Override
    protected void onDestroy() {
        if (mMessageUtil != null) {
            mMessageUtil.dispose();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(priority = 2, threadMode = ThreadMode.MAIN)
    public void handleOrderEvent(OrderEvent event) {
        if (event.getMessage().equals(OrderEvent.SELECT_STATUS)) {
            mGroupType.check(UserConfig.crm() ? PageType.CRM_ORDER : PageType.SUPPLIER_ORDER);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMessageEvent(MessageEvent event) {
        UIUtils.setTextWithVisibility(mMessageCount, event.getCount(), mMessage.getVisibility() == View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleApplyMessage(ApplyMessageResp resp) {
        mHasApply = resp.getTotalNum() > 0;
        handleTip();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleDemandMessage(UnreadResp resp) {
        mHasDemand = resp.getUnreadNum() > 0;
        handleTip();
    }

    private void handleTip() {
        ((TipRadioButton) mGroupType.getChildAt(3)).setTipOn(mHasApply || mHasDemand);
    }

    /**
     * 根据不同的标识位显示不同的Fragment
     *
     * @param tag 标识位
     */
    public void setCurrentTab(@PageType int tag) {
        if (tag == mOldFragmentTag) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(tag));
        if (mOldFragmentTag != 0) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(mOldFragmentTag));
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        if (currentFragment == null) {
            switch (tag) {
                case PageType.SUPPLIER_HOME:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_MAIN);
                    break;
                case PageType.SUPPLIER_ORDER:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_ORDER);
                    break;
                case PageType.SUPPLIER_GOODS:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_GOODS);
                    break;
                case PageType.SUPPLIER_MINE:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_MINE);
                    break;
                case PageType.CRM_HOME:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_HOME);
                    break;
                case PageType.CRM_ORDER:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_ORDER);
                    break;
                case PageType.CRM_CUSTOMER:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_CUSTOMER);
                    break;
                case PageType.CRM_DAILY:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_DAILY);
                    break;
                case PageType.CRM_MINE:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_MINE);
                    break;
                default:
                    break;
            }
            if (currentFragment != null) {
                transaction.add(R.id.slice_container, currentFragment, String.valueOf(tag));
            }
        } else {
            transaction.show(currentFragment);
        }
        mOldFragmentTag = tag;
        transaction.commitAllowingStateLoss();
        if (tag == PageType.CRM_HOME || tag == PageType.CRM_MINE || tag == PageType.SUPPLIER_MINE) {
            mMessage.setVisibility(View.VISIBLE);
            String count = mMessageCount.getText().toString();
            if (!TextUtils.isEmpty(count)) {
                mMessageCount.setVisibility(View.VISIBLE);
            }
        } else {
            mMessage.setVisibility(View.GONE);
            mMessageCount.setVisibility(View.GONE);
        }
        if (!UserConfig.crm()) {
            CountlyMgr.recordView(((RadioButton) findViewById(tag)).getText().toString());
        }
    }

    @Override
    public BackType getBackType() {
        return BackType.ORDER_LIST;
    }

    @OnClick(R.id.img_message)
    public void message() {
        MessageActivity.start();
    }

    @OnLongClick(R.id.img_message)
    public boolean enableMessage() {
        if (BuildConfig.isDebug) {
            if (mMessageUtil == null) {
                mMessageUtil = new MessageUtil();
                showToast("启用消息查询");
            } else {
                mMessageUtil.dispose();
                mMessageUtil = null;
                showToast("禁用消息查询");
            }
        }
        return true;
    }

    /**
     * Fragment 页面类型
     */
    @IntDef({PageType.SUPPLIER_HOME, PageType.SUPPLIER_ORDER, PageType.SUPPLIER_GOODS, PageType.SUPPLIER_MINE,
            PageType.CRM_HOME, PageType.CRM_ORDER, PageType.CRM_CUSTOMER, PageType.CRM_DAILY, PageType.CRM_MINE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageType {
        int SUPPLIER_HOME = R.id.supplier_home;
        int SUPPLIER_ORDER = R.id.supplier_order;
        int SUPPLIER_GOODS = R.id.supplier_goods;
        int SUPPLIER_MINE = R.id.supplier_mine;
        int CRM_HOME = R.id.crm_home;
        int CRM_ORDER = R.id.crm_order;
        int CRM_CUSTOMER = R.id.crm_customer;
        int CRM_DAILY = R.id.crm_daily;
        int CRM_MINE = R.id.crm_mine;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Parcelable parcelable = intent.getParcelableExtra("parcelable");
        if (parcelable instanceof Page) {
            mPage = (Page) parcelable;
            NotificationMessageReceiver.handleNotification(mPage);
            return;
        }
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(mOldFragmentTag));
        if (currentFragment instanceof IReload) {
            ((IReload) currentFragment).reload();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(mOldFragmentTag));
        if (currentFragment != null)
            currentFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort(this, "再按一次退出APP");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
