package com.hll_sc_app.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.api.MessageService;
import com.hll_sc_app.app.submit.BackType;
import com.hll_sc_app.app.submit.IBackType;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.TipRadioButton;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.message.UnreadResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.impl.IMessageCount;
import com.hll_sc_app.impl.IReload;
import com.hll_sc_app.rest.User;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

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
    private int mOldFragmentTag;
    private long mExitTime;
    private String mEmployeeID;
    private boolean mIsStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setTranslucent(getWindow(), true);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initView();
        User.queryAuthList(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsStart = true;
        // 查询消息
        if (!BuildConfig.isDebug)
            queryUnreadMessage();
    }

    @Override
    protected void onStop() {
        mIsStart = false;
        super.onStop();
    }

    private void queryUnreadMessage() {
        if (!mIsStart) return;
        Observable.timer(mEmployeeID != null ? 3000 : 0, TimeUnit.MILLISECONDS)
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(aLong -> {
                    SimpleObserver<UnreadResp> observer = new SimpleObserver<UnreadResp>(this, false) {
                        @Override
                        public void onSuccess(UnreadResp unreadResp) {
                            String messageCount = "";
                            if (CommonUtils.getInt(unreadResp.getUnreadNum()) > 99) {
                                messageCount = "99+";
                            }
                            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(mOldFragmentTag));
                            if (currentFragment instanceof IMessageCount) {
                                ((IMessageCount) currentFragment).setMessageCount(messageCount);
                            }
                            queryUnreadMessage();
                        }

                        @Override
                        public void onFailure(UseCaseException e) {
                            super.onFailure(e);
                            queryUnreadMessage();
                        }
                    };
                    if (mEmployeeID == null) {
                        mEmployeeID = GreenDaoUtils.getUser().getEmployeeID();
                    }
                    MessageService.INSTANCE.queryUnreadNum(BaseMapReq.newBuilder()
                            .put("employeeID", mEmployeeID)
                            .put("source", "supplier")
                            .create())
                            .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                            .subscribe(observer);
                });
    }

    private void initView() {
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
            mGroupType.check(UserConfig.crm() ? PageType.CRM_ORDER : PageType.SUPPLIER_ORDER);
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
    }

    @Override
    public BackType getBackType() {
        return BackType.ORDER_LIST;
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
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(mOldFragmentTag));
        if (currentFragment instanceof IReload){
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
