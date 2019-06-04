package com.hll_sc_app.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.hll_sc_app.R;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.app.goods.GoodsHomeFragment;
import com.hll_sc_app.app.main.MainHomeFragment;
import com.hll_sc_app.app.mine.MineHomeFragment;
import com.hll_sc_app.app.order.OrderHomeFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 首页
 *
 * @author zhuyingsong
 * @date 2019/5/31
 */
@Route(path = RouterConfig.ROOT_HOME)
public class MainActivity extends BaseLoadActivity {
    @BindView(R.id.group_type)
    RadioGroup mGroupType;
    private int mOldFragmentTag;
    private Fragment mOldFragment;

    private MainHomeFragment mMainFragment;
    private OrderHomeFragment mOrderFragment;
    private GoodsHomeFragment mGoodsFragment;
    private MineHomeFragment mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGroupType.setOnCheckedChangeListener(new TypeOnCheckedChangeListener());
        setCurrentTab(PageType.HOME);
    }

    /**
     * 根据不同的标识位显示不同的Fragment
     *
     * @param tag 标识位
     */
    public void setCurrentTab(int tag) {
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
                case PageType.CATEGORY:
                    mOrderFragment = (OrderHomeFragment) RouterUtil.getFragment(RouterConfig.ROOT_HOME_ORDER);
                    currentFragment = mOrderFragment;
                    break;
                case PageType.CART:
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

    public void onViewClicked() {
        showToast("txt_confirm");
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("loginPhone", "13111112222")
            .put("loginPWD", "111111")
            .put("checkCode", "")
            .put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId())
            .create();
        UserService.INSTANCE.login(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> showLoading())
            .doFinally(this::hideLoading)
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                }

                @Override
                public void onFailure(UseCaseException e) {
                    showError(e);
                }
            });
    }

    /**
     * Fragment 页面类型
     */
    public final class PageType {
        /**
         * 首页
         */
        static final int HOME = 1;
        /**
         * 分类
         */
        static final int CATEGORY = 2;
        /**
         * 进货单
         */
        static final int CART = 3;
        /**
         * 我的
         */
        static final int MINE = 4;

        private PageType() {
            throw new IllegalStateException("Utility class");
        }
    }

    private class TypeOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rbtn_home:
                    // 首页
                    setCurrentTab(PageType.HOME);
                    break;
                case R.id.rbtn_category:
                    // 分类
                    setCurrentTab(PageType.CATEGORY);
                    break;
                case R.id.rbtn_cart:
                    // 购物车
                    setCurrentTab(PageType.CART);
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
}
