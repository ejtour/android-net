package com.hll_sc_app.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.TipRadioButton;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.citymall.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 *
 * @author zhuyingsong
 * @date 2019/5/31
 */
@Route(path = RouterConfig.ROOT_HOME, extras = Constant.LOGIN_EXTRA)
public class MainActivity extends BaseLoadActivity {
    @BindView(R.id.group_type)
    RadioGroup mGroupType;
    private int mOldFragmentTag;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setTranslucent(getWindow(), true);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mGroupType.setOnCheckedChangeListener((group, checkedId) -> setCurrentTab(checkedId));
        if (TextUtils.isEmpty(UserConfig.getSalesmanID())) {
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
        radioButton.setTextColor(ContextCompat.getColorStateList(this, R.drawable.bg_main_button_text));
        res.setBounds(0, 0, res.getIntrinsicWidth(), res.getIntrinsicHeight());
        radioButton.setCompoundDrawables(null, res, null, null);
        mGroupType.addView(radioButton, layoutParams);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(priority = 2, threadMode = ThreadMode.MAIN)
    public void handleOrderEvent(OrderEvent event) {
        if (event.getMessage().equals(OrderEvent.CHANGE_INDEX)) {
            mGroupType.check(PageType.SUPPLIER_ORDER);
        }
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
            transaction.hide(getSupportFragmentManager().findFragmentByTag(String.valueOf(mOldFragmentTag)));
        }
        if (currentFragment == null) {
            switch (tag) {
                case PageType.SUPPLIER_HOME:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_MAIN);
                    break;
                case PageType.SUPPLIER_ORDER:
                case PageType.CRM_ORDER:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_ORDER);
                    break;
                case PageType.SUPPLIER_GOODS:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_GOODS);
                    break;
                case PageType.SUPPLIER_MINE:
                case PageType.CRM_MINE:
                    currentFragment = RouterUtil.getFragment(RouterConfig.ROOT_HOME_MINE);
                    break;
                case PageType.CRM_HOME:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_HOME);
                    break;
                case PageType.CRM_CUSTOMER:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_CUSTOMER);
                    break;
                case PageType.CRM_DAILY:
                    currentFragment = RouterUtil.getFragment(RouterConfig.CRM_DAILY);
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
        if (intent.getBooleanExtra("reload", false)) {
            if (intent.getBooleanExtra("item", false))
                EventBus.getDefault().post(new OrderEvent(OrderEvent.RELOAD_ITEM));
            else EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
        }
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
