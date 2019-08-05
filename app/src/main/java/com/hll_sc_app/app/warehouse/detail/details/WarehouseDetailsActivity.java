package com.hll_sc_app.app.warehouse.detail.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.warehouse.detail.WarehouseButtonView;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓管理-代仓详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
@Route(path = RouterConfig.WAREHOUSE_DETAILS, extras = Constant.LOGIN_EXTRA)
public class WarehouseDetailsActivity extends BaseLoadActivity implements WarehouseDetailsContract.IWarehouseDetailsView {
    @BindView(R.id.img_logoUrl)
    GlideImageView mImgLogoUrl;
    @BindView(R.id.txt_groupName)
    TextView mTxtGroupName;
    @BindView(R.id.txt_groupArea)
    TextView mTxtGroupArea;
    @BindView(R.id.txt_linkman)
    TextView mTxtLinkman;
    @BindView(R.id.txt_mobile)
    TextView mTxtMobile;
    @Autowired(name = "object0")
    String mPurchaserId;
    @Autowired(name = "object1")
    String mActionType;
    @BindView(R.id.buttonView)
    WarehouseButtonView mButtonView;
    private WarehouseDetailsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_details);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        mPresenter = WarehouseDetailsPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.queryCooperationWarehouseDetail(mPurchaserId);
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    @Override
    public void showDetail(WarehouseDetailResp resp) {
        if (resp == null) {
            return;
        }
        PurchaserBean purchaserInfo = resp.getPurchaserInfo();
        if (purchaserInfo != null) {
            mImgLogoUrl.setImageURL(purchaserInfo.getLogoUrl());
            mTxtGroupName.setText(purchaserInfo.getGroupName());
            mTxtGroupArea.setText(purchaserInfo.getGroupArea());
            mTxtLinkman.setText(purchaserInfo.getLinkman());
            mTxtMobile.setText(purchaserInfo.getMobile());
            mButtonView.showButton(mActionType, resp.getStatus());
        }
    }
}
