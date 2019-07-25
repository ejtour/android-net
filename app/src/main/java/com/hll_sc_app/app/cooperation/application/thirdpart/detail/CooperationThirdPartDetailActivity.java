package com.hll_sc_app.app.cooperation.application.thirdpart.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.application.thirdpart.CooperationThirdPartFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserBean;
import com.hll_sc_app.widget.RemarkDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商-我收到的申请-第三方申请-详情
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_APPLICATION_THIRD_PART_DETAIL, extras = Constant.LOGIN_EXTRA)
public class CooperationThirdPartDetailActivity extends BaseLoadActivity implements CooperationThirdPartDetailContract.ICooperationThirdDetailView {
    public static final String STATUS_WAIT = "0";
    @BindView(R.id.txt_agree)
    TextView mTxtAgree;
    @BindView(R.id.txt_reject)
    TextView mTxtReject;
    @BindView(R.id.ll_status0)
    LinearLayout mLlStatus0;
    @BindView(R.id.txt_groupName)
    TextView mTxtGroupName;
    @BindView(R.id.txt_groupProvince)
    TextView mTxtGroupProvince;
    @BindView(R.id.txt_groupAddress)
    TextView mTxtGroupAddress;
    @BindView(R.id.txt_linkMan)
    TextView mTxtLinkMan;
    @BindView(R.id.txt_groupPhone)
    TextView mTxtGroupPhone;
    @BindView(R.id.txt_fax)
    TextView mTxtFax;
    @BindView(R.id.txt_mail)
    TextView mTxtMail;
    @BindView(R.id.txt_message)
    TextView mTxtMessage;
    @BindView(R.id.txt_resourceType)
    TextView mTxtResourceType;
    @Autowired(name = "object0")
    String mId;
    private CooperationThirdPartDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_third_part_detail);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = CooperationThirdPartDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @OnClick({R.id.img_close, R.id.txt_agree, R.id.txt_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_agree:
                toAgree();
                break;
            case R.id.txt_reject:
                reject();
                break;
            default:
                break;
        }
    }

    private void toAgree() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            // 1-同意 2-拒绝 3-解除
            .put("flag", "1")
            .put("id", getId())
            .put("plateSupplierID", UserConfig.getGroupID())
            .put("actionBy", userBean.getEmployeeName())
            .create();
        mPresenter.editCooperationThirdPartStatus(req);
    }

    public void reject() {
        RemarkDialog.newBuilder(this)
            .setHint("可输入驳回理由，选填")
            .setMaxLength(100)
            .setButtons("容我再想想", "确认拒绝", (dialog, positive, content) -> {
                dialog.dismiss();
                if (positive) {
                    toReject(content);
                }
            })
            .create()
            .show();
    }

    private void toReject(String content) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            // 1-同意 2-拒绝 3-解除
            .put("flag", "2")
            .put("id", getId())
            .put("plateSupplierID", UserConfig.getGroupID())
            .put("actionBy", userBean.getEmployeeName())
            .put("rejectReson", content)
            .create();
        mPresenter.editCooperationThirdPartStatus(req);
    }

    @Override
    public void editSuccess() {
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_APPLICATION)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    @Override
    public void showView(ThirdPartyPurchaserBean bean) {
        mLlStatus0.setVisibility(TextUtils.equals(bean.getStatus(), STATUS_WAIT) ? View.VISIBLE : View.GONE);
        mTxtGroupName.setText(bean.getGroupName());
        mTxtResourceType.setText(CooperationThirdPartFragment.getResourceType(bean.getResourceType()));
        mTxtGroupProvince.setText(String.format("%s%s", bean.getGroupCity(), bean.getGroupDistrict()));
        mTxtGroupAddress.setText(bean.getGroupAddress());
        mTxtLinkMan.setText(bean.getLinkman());
        mTxtGroupPhone.setText(bean.getGroupPhone());
        mTxtFax.setText(bean.getFax());
        mTxtMail.setText(bean.getMail());
        mTxtMessage.setText(bean.getMessage());
    }

    @Override
    public String getId() {
        return mId;
    }

}
