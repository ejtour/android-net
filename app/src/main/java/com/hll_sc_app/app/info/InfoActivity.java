package com.hll_sc_app.app.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.info.license.InfoLicenseParam;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.user.CertifyReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.adapter.ViewPagerAdapter;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.info.GroupInfoBaseView;
import com.hll_sc_app.widget.info.GroupInfoCertifyView;
import com.hll_sc_app.widget.info.UserInfoView;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/5
 */

@Route(path = RouterConfig.INFO)
public class InfoActivity extends BaseLoadActivity implements IInfoContract.IInfoView {
    public static final int REQ_CODE = 0x482;
    @BindView(R.id.stp_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.stp_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.stp_view_pager)
    ViewPager mViewPager;
    private IInfoContract.IInfoPresenter mPresenter;
    private UserBean mUser;
    private GroupInfoBaseView mBaseView;
    private GroupInfoCertifyView mCertifyView;
    private UserInfoView mInfoView;
    private CertifyReq mReq = new CertifyReq();
    private boolean mHasChanged;
    private String mAvatar;

    public static void start(Activity context) {
        RouterUtil.goToActivity(RouterConfig.INFO, context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        mUser = GreenDaoUtils.getUser();
        initView();
        initData();
    }

    private void initData() {
        if (isMaster()) {
            mPresenter = new InfoPresenter();
            mPresenter.register(this);
            mPresenter.start();
        } else {
            mInfoView.setData();
        }
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged)
            setResult(RESULT_OK);
        super.onBackPressed();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mTabLayout.setVisibility(View.GONE);
        if (isMaster()) {
            mBaseView = new GroupInfoBaseView(this);
            mCertifyView = new GroupInfoCertifyView(this);
            mCertifyView.withReq(mReq);
            mViewPager.setAdapter(new ViewPagerAdapter(mBaseView/*, mCertifyView*/));
//            mTabLayout.setViewPager(mViewPager, new String[]{"基本信息", "认证信息"});
            mTitleBar.setHeaderTitle("供应商信息");
            mCertifyView.setOnClickListener(v -> mPresenter.reqCertify(mReq));
        } else {
            mInfoView = new UserInfoView(this);
            mViewPager.setAdapter(new ViewPagerAdapter(mInfoView));
            mTitleBar.setHeaderTitle("个人信息");
        }
    }

    private boolean isMaster() {
        return "0".equals(mUser.getAccountType());
    }

    @Override
    public void setData(GroupInfoResp resp) {
        if (isMaster()) {
            UserBean user = GreenDaoUtils.getUser();
            user.setEmail(resp.getGroupMail());
            user.setEmployeeName(resp.getLinkman());
            GreenDaoUtils.updateUser(user);
            mBaseView.setData(resp);
            mReq.inflate(resp);
            mCertifyView.refreshData();
        }
    }

    @Override
    public void cacheUrl(String url) {
        mBaseView.setAvatar(url);
        mAvatar = url;
    }

    @Override
    public void avatarChanged() {
        UserBean user = GreenDaoUtils.getUser();
        user.setGroupLogoUrl(mAvatar);
        GreenDaoUtils.updateUser(user);
        mHasChanged = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.IMG_SELECT_REQ_CODE) {
                if (data == null) return;
                List<String> list = Matisse.obtainPathResult(data);
                if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
                return;
            }
            if (isMaster()) {
                if (data == null) {
                    mHasChanged = true;
                    mPresenter.start();
                } else {
                    int type = data.getIntExtra("type", 0);
                    String content = data.getStringExtra("content");
                    if (type == ModifyType.NAME) {
                        mReq.setBusinessEntity(content);
                    } else if (type == ModifyType.ID_CARD) {
                        mReq.setEntityIDNo(content);
                    } else if (type == ModifyType.DOORWAY) {
                        mReq.setFrontImg(content);
                    } else if (type == ModifyType.LICENSE) {
                        InfoLicenseParam param = data.getParcelableExtra("obj");
                        param.inflateToCertifyReq(mReq);
                    } else if (type == ModifyType.OTHER) {
                        List<GroupInfoResp.OtherLicensesBean> list = data.getParcelableArrayListExtra("array");
                        mReq.setOtherLicenses(list);
                    }
                    mCertifyView.refreshData();
                }
            } else mInfoView.setData();
        }
    }
}
