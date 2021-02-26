package com.hll_sc_app.app.cooperation.detail.details;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.app.cooperation.detail.details.basic.CooperationDetailsBasicFragment;
import com.hll_sc_app.app.cooperation.detail.details.certification.CooperationDetailsCertificationFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.event.CooperationEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情-详细资料
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, extras = Constant.LOGIN_EXTRA)
public class CooperationDetailsActivity extends BaseLoadActivity {
    static final String[] STR_TITLE = {"基本信息", "认证信息"};
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @Autowired(name = "object0", required = true)
    String mPurchaserId;
    @Autowired(name = "object1")
    boolean mFromMessage;
    private List<BaseCooperationDetailsFragment> mListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_details);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        queryPurchaserDetail();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void handleEvent(CooperationEvent event) {
        if (event.getMessage().equals(CooperationEvent.SHOP_NUM_CHANGED)) {
            queryPurchaserDetail();
        }
    }

    public void queryPurchaserDetail() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("originator", "1")
                .put("groupID", UserConfig.getGroupID())
                .put(mFromMessage ? "cooperationID" : "purchaserID", mPurchaserId)
                .create();
        SimpleObserver<CooperationPurchaserDetail> observer = new SimpleObserver<CooperationPurchaserDetail>(this) {
            @Override
            public void onSuccess(CooperationPurchaserDetail resp) {
                if (!CommonUtils.isEmpty(mListFragment)) {
                    refreshFragment(resp);
                } else {
                    initView(resp);
                }
            }
        };
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserDetail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
                .subscribe(observer);
    }

    public void refreshFragment(CooperationPurchaserDetail detail) {
        if (!CommonUtils.isEmpty(mListFragment)) {
            for (BaseCooperationDetailsFragment fragment : mListFragment) {
                fragment.refreshFragment(detail);
            }
        }
    }

    private void initView(CooperationPurchaserDetail detail) {
        mListFragment = new ArrayList<>();
        mListFragment.add(CooperationDetailsBasicFragment.newInstance(detail));
        mListFragment.add(CooperationDetailsCertificationFragment.newInstance(detail));
        mViewPager.setAdapter(new FragmentListAdapter(getSupportFragmentManager(), mListFragment));
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }

    class FragmentListAdapter extends FragmentPagerAdapter {
        private List<BaseCooperationDetailsFragment> mListFragment;

        FragmentListAdapter(FragmentManager fm, List<BaseCooperationDetailsFragment> list) {
            super(fm);
            this.mListFragment = list;
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public BaseCooperationDetailsFragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }
    }
}
