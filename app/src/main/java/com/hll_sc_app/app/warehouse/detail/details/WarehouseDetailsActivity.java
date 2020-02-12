package com.hll_sc_app.app.warehouse.detail.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.RefreshWarehouseList;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓管理-代仓客户详情
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
    @BindView(R.id.txt_return_audit)
    TextView mTxtReturnAudit;
    @BindView(R.id.rl_return_audit)
    RelativeLayout mRlReturnAudit;
    @BindView(R.id.txt_shopsNum)
    TextView mTxtShopsNum;
    @BindView(R.id.ll_shopsNum)
    LinearLayout mLlShopsNum;
    @BindView(R.id.rl_switch_pay)
    RelativeLayout mRlSwitchPay;
    @BindView(R.id.switch_pay)
    SwitchButton mSwitchPay;
    @BindView(R.id.ll_receiver)
    LinearLayout mLlReceiver;
    @BindView(R.id.txt_receiver)
    TextView mTxtReceiver;
    @BindView(R.id.img_receiver_arrow)
    ImageView mImgReceiverArrow;
    private WarehouseDetailsPresenter mPresenter;
    private SingleSelectionDialog mDialog;
    private SingleSelectionDialog mSelectReceiverDialog;
    private WarehouseDetailResp mResp;

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

    @OnClick({R.id.img_close, R.id.rl_return_audit, R.id.ll_shopsNum, R.id.txt_receiver})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.rl_return_audit) {
            showReturnAuditWindow();
        } else if (view.getId() == R.id.ll_shopsNum) {
            List<WarehouseShopBean> shops = mResp.getShops();
            if (CommonUtils.isEmpty(shops)) {
                return;
            }
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_APPLICATION_SHOP, new ArrayList<>(shops));
        } else if (view.getId() == R.id.txt_receiver) {
            if (!UserConfig.isSelfOperated()){
                showPayReceiverSelectWindow();
            }
        }
    }

    private void showReturnAuditWindow() {
        if (mDialog == null) {
            List<NameValue> values = new ArrayList<>();
            NameValue value0 = new NameValue("代仓公司审核", "0");
            NameValue value1 = new NameValue("货主审核", "1");
            values.add(value0);
            values.add(value1);
            mDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .setTitleText("选择退货审核")
                .select(TextUtils.equals(mTxtReturnAudit.getText(), "代仓公司审核") ? value0 : value1)
                .setOnSelectListener(bean -> {
                    mTxtReturnAudit.setText(bean.getName());
                    BaseMapReq req = BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("purchaserID", mPurchaserId)
                        .put("returnAudit", bean.getValue())
                        .create();
                    mPresenter.editWarehouseParameter(req);
                })
                .refreshList(values)
                .create();
        }
        mDialog.show();
    }

    private void showPayReceiverSelectWindow() {
        if (mSelectReceiverDialog == null) {
            List<NameValue> values = new ArrayList<>();
            NameValue value0 = new NameValue("代仓公司代收货款", "1");
            NameValue value1 = new NameValue("货主直接收取货款", "2");
            values.add(value0);
            values.add(value1);
            mSelectReceiverDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("收款方")
                    .select(mResp != null && mResp.getPayee() > 0 ? values.get(mResp.getPayee() - 1) : null)
                    .setOnSelectListener(bean -> {
                        mTxtReceiver.setText(bean.getName());
                        mPresenter.changeShopParams(mPurchaserId, "1", bean.getValue());
                    })
                    .refreshList(values)
                    .create();
        }
        mSelectReceiverDialog.show();
    }
    @Override
    public void showDetail(WarehouseDetailResp resp) {
        mResp = resp;
        if (resp == null) {
            return;
        }
        PurchaserBean info = UserConfig.isSelfOperated() ? resp.getPurchaserInfo() : resp.getGroupInfo();
        if (info != null) {
            mImgLogoUrl.setImageURL(info.getLogoUrl());
            mTxtBusinessModel.setText(getBusinessModelString(info.getBusinessModel()));
            mTxtGroupName.setText(info.getGroupName());
            mTxtGroupArea.setText(info.getGroupArea());
            mTxtLinkman.setText(info.getLinkman());
            mTxtMobile.setText(info.getMobile());
            mButtonView.setListener(this, info);
        }
        mButtonView.showButton(mActionType, resp.getStatus());
        mRlReturnAudit.setVisibility(UserConfig.isSelfOperated() && TextUtils.equals(resp.getStatus(), "2") ?
            View.VISIBLE : View.GONE);
        mTxtReturnAudit.setText(TextUtils.equals(resp.getReturnAudit(), "0") ? "代仓公司审核" : "货主审核");

        mLlShopsNum.setVisibility(UserConfig.isSelfOperated()
            && TextUtils.equals(resp.getStatus(), "0")
            && TextUtils.equals(mActionType, "signApplication") ? View.VISIBLE : View.GONE);
        mTxtShopsNum.setText(String.format(Locale.getDefault(), "需代仓%d个门店", CommonUtils.isEmpty(mResp.getShops()) ? 0 :
            mResp.getShops().size()));


        //代仓支付区域显示逻辑
        //货主展示，代仓公司不展示
        mRlSwitchPay.setVisibility(UserConfig.isSelfOperated() ? View.GONE : View.VISIBLE);
        mSwitchPay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked && mResp.getSupportPay() != 0) {//开启->关闭
                mPresenter.changeShopParams(mPurchaserId, "0", "0");
            } else {//开启
                mLlReceiver.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                mTxtReceiver.setText("");
                mSelectReceiverDialog = null;
            }
        });
        mImgReceiverArrow.setVisibility(UserConfig.isSelfOperated() ? View.GONE : View.VISIBLE);
        mSwitchPay.setCheckedNoEvent(mResp.getSupportPay() == 1);
        mLlReceiver.setVisibility(mResp.getSupportPay() == 1 ? View.VISIBLE : View.GONE);
        //显示支付方名称
        if (mResp.getPayee() > 0) {
            mTxtReceiver.setText(mResp.getPayee() == 1 ? "代仓公司代收货款" : "货主直接收取货款");
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
        EventBus.getDefault().post(new RefreshWarehouseList());
        finish();
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
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        if (UserConfig.isSelfOperated()) {
            builder
                .put("originator", "1")
                .put("groupID", UserConfig.getGroupID())
                .put("purchaserID", bean.getGroupID());
        } else {
            builder
                .put("originator", "0")
                .put("groupID", bean.getGroupID())
                .put("purchaserID", UserConfig.getGroupID());
        }
        builder.put("type", type);
        if (TextUtils.equals("1", type)) {
            // 1-解除合作，2-放弃
            showTipsDialog(builder.create(), type);
        } else {
            mPresenter.delWarehouse(builder.create(), type);
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
            if (UserConfig.isSelfOperated()) {
                builder
                    .put("originator", "1")
                    .put("groupID", user.getGroupID())
                    .put("purchaserID", bean.getGroupID());
            } else {
                builder
                    .put("originator", "0")
                    .put("groupID", bean.getGroupID())
                    .put("purchaserID", user.getGroupID())
                    .put("warehouseType", "1");
            }
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
            if (UserConfig.isSelfOperated()) {
                // 是自营-代仓公司
                builder.put("groupID", user.getGroupID())
                    .put("groupName", user.getGroupName())
                    .put("originator", "1")
                    .put("purchaserID", bean.getGroupID())
                    .put("purchaserName", bean.getGroupName());
            } else {
                // 非自营-货主
                builder
                    .put("groupID", bean.getGroupID())
                    .put("groupName", bean.getGroupName())
                    .put("originator", "0")
                    .put("purchaserID", user.getGroupID())
                    .put("purchaserName", user.getGroupName())
                    .put("warehouseType", "1");
            }
        }
        return builder;
    }

    @Override
    public void changePayTypeResult(boolean isSuccess, String supportPay, String payee) {
        showToast(isSuccess ? "代仓支付设置成功" : "代仓支付设置失败");
        if (!isSuccess) {//修改失败
            mSwitchPay.setCheckedNoEvent(!mSwitchPay.isChecked());
        } else {//修改成功
            mResp.setPayee(Integer.parseInt(payee));
            mResp.setSupportPay(Integer.parseInt(supportPay));
        }
        mLlReceiver.setVisibility(mSwitchPay.isChecked() ? View.VISIBLE : View.GONE);
    }


}
