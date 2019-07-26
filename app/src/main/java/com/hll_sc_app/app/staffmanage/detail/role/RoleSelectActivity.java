package com.hll_sc_app.app.staffmanage.detail.role;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.staff.RoleBean;
import com.hll_sc_app.bean.staff.RoleResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 员工管理-选择员工岗位
 *
 * @author zhuyingsong
 * @date 2019/7/25
 */
@Route(path = RouterConfig.STAFF_ROLE_SELECT, extras = Constant.LOGIN_EXTRA)
public class RoleSelectActivity extends BaseLoadActivity {
    static final String[] STR_TITLE = {"管理型", "业务型", "配送型"};
    public static int TYPE_BUSINESS = 1;
    public static int TYPE_MANAGE = 2;
    public static int TYPE_DELIVERY = 3;
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<RoleSelectFragment> mListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_role_select);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        queryRoleList();
    }

    private void queryRoleList() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNum", "1")
            .put("pageSize", "50")
            .put("groupID", UserConfig.getGroupID())
            .put("authorityType", "2")
            .create();
        StaffManageService.INSTANCE
            .queryRoles(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> showLoading())
            .doFinally(this::hideLoading)
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
            .subscribe(new BaseCallback<RoleResp>() {
                @Override
                public void onSuccess(RoleResp resp) {
                    initView(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    showError(e);
                }
            });
    }

    private void initView(RoleResp resp) {
        mListFragment = new ArrayList<>();
        ArrayList<RoleBean> listManage = new ArrayList<>();
        ArrayList<RoleBean> listBusiness = new ArrayList<>();
        ArrayList<RoleBean> listDelivery = new ArrayList<>();
        List<RoleBean> listResp = resp.getRecords();
        if (!CommonUtils.isEmpty(listResp)) {
            for (RoleBean bean : listResp) {
                if (bean.getAuthType() == TYPE_BUSINESS) {
                    listBusiness.add(bean);
                } else if (bean.getAuthType() == TYPE_MANAGE) {
                    listManage.add(bean);
                } else if (bean.getAuthType() == TYPE_DELIVERY) {
                    listDelivery.add(bean);
                }
            }
        }
        mListFragment.add(RoleSelectFragment.newInstance(TYPE_MANAGE, listManage));
        mListFragment.add(RoleSelectFragment.newInstance(TYPE_BUSINESS, listBusiness));
        mListFragment.add(RoleSelectFragment.newInstance(TYPE_DELIVERY, listDelivery));
        mViewPager.setAdapter(new FragmentListAdapter(getSupportFragmentManager(), mListFragment));
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_confirm:
                toConfirm();
                break;
            default:
                break;
        }
    }

    private void toConfirm() {
        List<RoleBean> listSelect = new ArrayList<>();
        if (!CommonUtils.isEmpty(mListFragment)) {
            for (RoleSelectFragment fragment : mListFragment) {
                listSelect.addAll(fragment.getSelectRole());
            }
        }
        EventBus.getDefault().post(listSelect);
        finish();
    }

    class FragmentListAdapter extends FragmentPagerAdapter {
        private List<RoleSelectFragment> mListFragment;

        FragmentListAdapter(FragmentManager fm, List<RoleSelectFragment> list) {
            super(fm);
            this.mListFragment = list;
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public RoleSelectFragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }
    }
}