package com.hll_sc_app.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.GoodsHomeFragment;
import com.hll_sc_app.app.main.MainHomeFragment;
import com.hll_sc_app.app.mine.MineHomeFragment;
import com.hll_sc_app.app.order.OrderHomeFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
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
    @BindView(R.id.rbtn_order)
    RadioButton mOrderBtn;
    private int mOldFragmentTag;
    private Fragment mOldFragment;
    private long mExitTime;

    private MainHomeFragment mMainFragment;
    private OrderHomeFragment mOrderFragment;
    private GoodsHomeFragment mGoodsFragment;
    private MineHomeFragment mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setTranslucent(getWindow(), true);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        mGroupType.setOnCheckedChangeListener(new TypeOnCheckedChangeListener());
        setCurrentTab(PageType.HOME);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(priority = 2, threadMode = ThreadMode.MAIN)
    public void handleOrderEvent(OrderEvent event) {
        if (event.getMessage().equals(OrderEvent.CHANGE_INDEX)) {
            mOrderBtn.setChecked(true);
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
        if (mOldFragment != null) {
            transaction.hide(mOldFragment);
        }
        if (currentFragment == null) {
            switch (tag) {
                case PageType.HOME:
                    mMainFragment = (MainHomeFragment) RouterUtil.getFragment(RouterConfig.ROOT_HOME_MAIN);
                    currentFragment = mMainFragment;
                    break;
                case PageType.ORDER:
                    mOrderFragment = (OrderHomeFragment) RouterUtil.getFragment(RouterConfig.ROOT_HOME_ORDER);
                    currentFragment = mOrderFragment;
                    break;
                case PageType.GOODS:
                    mGoodsFragment = (GoodsHomeFragment) RouterUtil.getFragment(RouterConfig.ROOT_HOME_GOODS);
                    currentFragment = mGoodsFragment;
                    break;
                case PageType.MINE:
                    mMineFragment = (MineHomeFragment) RouterUtil.getFragment(RouterConfig.ROOT_HOME_MINE);
                    currentFragment = mMineFragment;
                    break;
                default:
                    break;
            }
            if (currentFragment != null) {
                transaction.add(R.id.flayout_container, currentFragment, String.valueOf(tag));
            }
        } else {
            transaction.show(currentFragment);
        }
        mOldFragmentTag = tag;
        mOldFragment = currentFragment;
        transaction.commitAllowingStateLoss();
    }

    /**
     * Fragment 页面类型
     */
    @IntDef({PageType.HOME, PageType.ORDER, PageType.GOODS, PageType.MINE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PageType {
        /**
         * 首页
         */
        int HOME = 1;
        /**
         * 订单
         */
        int ORDER = 2;
        /**
         * 商品
         */
        int GOODS = 3;
        /**
         * 我的
         */
        int MINE = 4;
    }

    private class TypeOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rbtn_home:
                    // 首页
                    setCurrentTab(PageType.HOME);
                    break;
                case R.id.rbtn_order:
                    // 分类
                    setCurrentTab(PageType.ORDER);
                    break;
                case R.id.rbtn_product:
                    // 购物车
                    setCurrentTab(PageType.GOODS);
                    break;
                case R.id.rbtn_mine:
                    // 我的
                    setCurrentTab(PageType.MINE);
                    break;
                default:
                    break;
            }
        }
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
