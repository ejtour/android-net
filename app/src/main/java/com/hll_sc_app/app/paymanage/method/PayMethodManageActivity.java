package com.hll_sc_app.app.paymanage.method;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.paymanage.PayManagePresenter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.paymanage.PayBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付管理-货到付款设置or在线支付设置
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
@Route(path = RouterConfig.PAY_MANAGE_METHOD, extras = Constant.LOGIN_EXTRA)
public class PayMethodManageActivity extends BaseLoadActivity implements PayMethodManageContract.IMethodView {
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;
    @Autowired(name = "parcelable")
    ArrayList<PayBean> mPayList;
    @Autowired(name = "isChecked")
    boolean isChecked;
    @BindView(R.id.txt_pay_type)
    TextView mTxtPayType;
    @BindView(R.id.switch_pay_type)
    SwitchButton mSwitchPayType;

    private PayMethodManagePresenter mPresenter;

    public static void start(ArrayList<PayBean> payBeans, boolean isChecked) {
        ARouter.getInstance().build(RouterConfig.PAY_MANAGE_METHOD)
                .withParcelableArrayList("parcelable", payBeans)
                .withBoolean("isChecked", isChecked)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage_method);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = PayMethodManagePresenter.newInstance();
        mPresenter.register(this);
        initView();
    }

    private void initView() {
        if (isOnline()) {
            mTxtTitle.setText("在线支付设置");
            mTxtPayType.setText("在线支付");
        } else {
            mTxtTitle.setText("货到付款设置");
            mTxtPayType.setText("货到付款");
        }
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        PayListAdapter payAdapter = new PayListAdapter(mPayList);
        payAdapter.setOnItemClickListener((adapter, view, position) -> clickItem(adapter, position));
        mRecyclerView.setAdapter(payAdapter);

        //顶部支付设置checkbox
        mSwitchPayType.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            PayManagePresenter.editSettlement(isOnline() ? "0" : "1", isChecked1 ? "1" : "0", new SimpleObserver<MsgWrapper<Object>>(this) {
                @Override
                public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                    showPayList(isChecked1);
                    showToast(objectMsgWrapper.getMessage());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mSwitchPayType.setCheckedNoEvent(!isChecked1);
                    showToast(e.getMessage());
                }
            });

        });
        mSwitchPayType.setCheckedNoEvent(isChecked);
        showPayList(isChecked);

    }

    /**
     * 在线支付设置 or 货到付款设置
     *
     * @return true-在线支付设置
     */
    private boolean isOnline() {
        boolean isOnline = false;
        if (!CommonUtils.isEmpty(mPayList)) {
            isOnline = TextUtils.equals("1", mPayList.get(0).getPayType());
        }
        return isOnline;
    }

    private void clickItem(BaseQuickAdapter adapter, int position) {
        PayBean payBean = (PayBean) adapter.getItem(position);
        if (payBean == null) {
            return;
        }
        if (payBean.isEnable()) {
            payBean.setSelect(!payBean.isSelect());
            if (!checkSelect()) {
                payBean.setSelect(true);
                showToast("至少有一种结算方式噢");
            } else {
                adapter.notifyItemChanged(position);
            }
        } else {//禁用的点击
            if (TextUtils.equals("15", payBean.getId()) || TextUtils.equals("16", payBean.getId())) {//储值卡
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_introduce)
                        .setMessageTitle("您需要先创建储值卡噢")
                        .setMessage("使用该支付方式\n" +
                                "您需要先给合作采购商创建储值卡")
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                //todo:跳转到储值卡
                            }
                        }, "我再想想", "马上创建")
                        .create()
                        .show();
                ;
            } else if (TextUtils.equals("12", payBean.getId()) || TextUtils.equals("4", payBean.getId())) {//微信直连
                SpannableString msg = new SpannableString("使用该支付方式您需要先通过微信收款认证\n" +
                        "详情请咨询客服：400-0088-822");
                int len = msg.length();
                msg.setSpan(new ForegroundColorSpan(Color.parseColor("#5695D2")), len - 13, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msg.setSpan(new UnderlineSpan(), len - 13, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_introduce)
                        .setMessageTitle("您需要先通过微信收款认证噢")
                        .setMessage(msg, v -> {
                            UIUtils.callPhone(this, "400-0088-822");
                        })
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                UIUtils.callPhone(this, "400-0088-822");
                            }
                        }, "我再想想", "联系客服")
                        .create()
                        .show();
            } else {//钱包没开通
                SpannableString msg = new SpannableString("使用该支付方式您需要先开通企业钱包\n" +
                        "详情可咨询客服：400-0088-822");
                int len = msg.length();
                msg.setSpan(new ForegroundColorSpan(Color.parseColor("#5695D2")), len - 13, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msg.setSpan(new UnderlineSpan(), len - 13, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_introduce)
                        .setMessageTitle("您需要先开通企业钱包噢")
                        .setMessage(msg, v -> {
                            UIUtils.callPhone(this, "400-0088-822");
                        })
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                RouterUtil.goToActivity(RouterConfig.WALLET);
                            }
                        }, "我再想想", "马上开通")
                        .create()
                        .show();
            }
        }
    }

    private void showPayList(boolean isShow) {
        mRecyclerView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRlBottom.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 至少有一种结算方式
     *
     * @return false-无结算方式
     */
    private boolean checkSelect() {
        boolean select = false;
        if (!CommonUtils.isEmpty(mPayList)) {
            for (PayBean payBean : mPayList) {
                if (payBean.isSelect() && payBean.isEnable()) {
                    select = true;
                }
            }
        }
        return select;
    }

    @OnClick({R.id.img_close, R.id.txt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                goBack();
                break;
            case R.id.txt_save:
                toSave();
                break;
            default:
                break;
        }
    }

    /**
     * 保存
     */
    private void toSave() {
        String payType = isOnline() ? "1" : "2";
        List<String> selectList = new ArrayList<>();
        if (!CommonUtils.isEmpty(mPayList)) {
            for (PayBean bean : mPayList) {
                if (bean.isEnable() && bean.isSelect()) {
                    selectList.add(String.valueOf(bean.getId()));
                }
            }
        }
        mPresenter.editPayMethod(payType, TextUtils.join(",", selectList));
    }

    @Override
    public void onBackPressed() {
       goBack();
    }

    private void goBack(){
        ARouter.getInstance().build(RouterConfig.PAY_MANAGE)
                .setProvider(new LoginInterceptor())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .navigation(this);
    }
    @Override
    public void editSuccess() {
        showToast("修改支付方式列表成功");
        goBack();
    }

    private class PayListAdapter extends BaseQuickAdapter<PayBean, BaseViewHolder> {

        PayListAdapter(@Nullable List<PayBean> data) {
            super(R.layout.item_pay_manage, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayBean item) {
            helper
                    .setText(R.id.txt_payMethodName, item.getPayMethodName());
            GlideImageView imageView = helper.getView(R.id.img_imgPath);
            imageView.setImageURL(item.getImgPath());
            ImageView imageView1 = helper.getView(R.id.img_select);
            if (!item.isEnable()) {
                imageView1.setEnabled(false);
            } else {
                imageView1.setEnabled(true);
                imageView1.setSelected(item.isSelect());
            }
        }
    }
}
