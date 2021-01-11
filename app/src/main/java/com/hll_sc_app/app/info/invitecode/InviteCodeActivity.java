package com.hll_sc_app.app.info.invitecode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.InviteCodeResp;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ShareDialog;
import com.hll_sc_app.widget.TitleBar;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/7
 */

@Route(path = RouterConfig.INFO_INVITE_CODE)
public class InviteCodeActivity extends BaseLoadActivity implements IInviteCodeContract.IInviteCodeView {

    @BindView(R.id.aic_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aic_name)
    TextView mName;
    @BindView(R.id.aic_code)
    TextView mCode;
    @BindView(R.id.aic_pic)
    GlideImageView mPic;
    @BindView(R.id.aic_container)
    LinearLayout mContainer;
    @BindView(R.id.aic_crm_tip)
    TextView mCrmTip;
    @BindView(R.id.aic_tip)
    TextView mTip;
    @BindView(R.id.aic_div)
    View mDiv;
    private ShareDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mDialog != null) mDialog.onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        IInviteCodeContract.IInviteCodePresenter presenter = new InviteCodePresenter();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mName.setText(GreenDaoUtils.getUser().getGroupName());
        mTitleBar.setRightBtnClick(this::share);
        if (UserConfig.crm()) {
            mTitleBar.setHeaderTitle("销售推荐码");
            mCrmTip.setVisibility(View.VISIBLE);
            mCode.setVisibility(View.GONE);
            ((ViewGroup.MarginLayoutParams) mDiv.getLayoutParams()).topMargin = 0;
            ((ViewGroup.MarginLayoutParams) mName.getLayoutParams()).topMargin = UIUtils.dip2px(30);
            ((ViewGroup.MarginLayoutParams) mPic.getLayoutParams()).topMargin = UIUtils.dip2px(30);
            mTip.setText("扫码注册通过审核后会自动添加合作关系\n并且成为该业务员的合作客户");
        }
    }

    private void share(View view) {
        File cacheDir = getExternalCacheDir();
        if (cacheDir == null) return;
        String imagePath = cacheDir.getAbsolutePath() + "/invite_code_22city.png";
        Utils.saveViewToFile(mContainer, imagePath);
        if (mDialog == null) {
            mDialog = new ShareDialog(this);
            mDialog.setData(ShareDialog.ShareParam.createImageShareParam("分享推荐码", imagePath));
        }
        mDialog.show();
    }

    @Override
    public void setData(InviteCodeResp resp) {
        mPic.setImageURL(resp.getImgUrl());
        if (UserConfig.crm()) {
            mName.setText(String.format("推荐码：%s", resp.getRecommendCode()));
        } else {
            mCode.setText(String.format("邀请码：%s", resp.getRecommendCode()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null) mDialog.release();
    }
}
