package com.hll_sc_app.app.warehouse.detail.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
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
public class WarehouseDetailsActivity extends BaseLoadActivity implements WarehouseDetailsContract.IWarehouseDetailsView, WarehouseButtonView.Listener {
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
    @BindView(R.id.txt_businessModel)
    TextView mTxtBusinessModel;
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
            mTxtBusinessModel.setText(getBusinessModelString(purchaserInfo.getBusinessModel()));
            mTxtGroupName.setText(purchaserInfo.getGroupName());
            mTxtGroupArea.setText(purchaserInfo.getGroupArea());
            mTxtLinkman.setText(purchaserInfo.getLinkman());
            mTxtMobile.setText(purchaserInfo.getMobile());
            mButtonView.showButton(mActionType, resp.getStatus());
            mButtonView.setListener(this, purchaserInfo);
        }
    }

    public static String getBusinessModelString(int businessModel) {
        if (businessModel == 1) {
            return "单店";
        } else if (businessModel == 2) {
            return "多店";
        } else if (businessModel == 3) {
            return "弱连锁";
        } else {
            return "";
        }
    }

    @Override
    public void editSuccess() {
        ARouter.getInstance().build(RouterConfig.WAREHOUSE_LIST)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    @Override
    public void add(PurchaserBean bean) {
        BaseMapReq.Builder builder = getAddReq(bean);
        builder.put("actionType", "normal");
        mPresenter.addWarehouse(builder.create());
    }

    @Override
    public void repeat(PurchaserBean bean) {
        BaseMapReq.Builder builder = getAddReq(bean);
        builder.put("actionType", "repeat");
        mPresenter.addWarehouse(builder.create());
    }

    @Override
    public void del(PurchaserBean bean, String type) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("originator", "1")
            .put("purchaserID", bean.getGroupID())
            .put("type", type)
            .create();
        if (TextUtils.equals("1", type)) {
            // 1-解除合作，2-放弃
            showTipsDialog(req, type);
        } else {
            mPresenter.delWarehouse(req, type);
        }
    }

    @Override
    public void refuse(PurchaserBean bean) {
        BaseMapReq.Builder builder = getAgreeOrRefuseReq(bean);
        builder.put("actionType", "refuse");
        mPresenter.agreeOrRefuseWarehouse(builder.create(), "refuse");
    }

    @Override
    public void agree(PurchaserBean bean) {
        BaseMapReq.Builder builder = getAgreeOrRefuseReq(bean);
        builder.put("actionType", "agree");
        mPresenter.agreeOrRefuseWarehouse(builder.create(), "agree");
    }

    private BaseMapReq.Builder getAgreeOrRefuseReq(PurchaserBean bean) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        UserBean user = GreenDaoUtils.getUser();
        if (user != null) {
            builder.put("groupID", user.getGroupID())
                .put("originator", "1")
                .put("purchaserID", bean.getGroupID());
        }
        return builder;
    }

    private void showTipsDialog(BaseMapReq req, String type) {
        SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("确认要解除合作嘛")
            .setMessage("您确认要解除和该公司的\n代仓合作关系嘛")
            .setCancelable(false)
            .setButton((dialog, item) -> {
                if (item == 1) {
                    mPresenter.delWarehouse(req, type);
                }
                dialog.dismiss();
            }, "我再看看", "确认解除")
            .create()
            .show();
    }

    private BaseMapReq.Builder getAddReq(PurchaserBean bean) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        UserBean user = GreenDaoUtils.getUser();
        if (user != null) {
            builder.put("groupID", user.getGroupID())
                .put("groupName", user.getGroupName())
                .put("originator", "1")
                .put("purchaserID", bean.getGroupID())
                .put("purchaserName", bean.getGroupName());
        }
        return builder;
    }
}
