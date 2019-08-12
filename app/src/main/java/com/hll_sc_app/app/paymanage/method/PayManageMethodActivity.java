package com.hll_sc_app.app.paymanage.method;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付管理-设置账期日
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
@Route(path = RouterConfig.PAY_MANAGE_METHOD, extras = Constant.LOGIN_EXTRA)
public class PayManageMethodActivity extends BaseLoadActivity implements PayManageMethodContract.IAccountView {
    @Autowired(name = "object0", required = true)
    boolean mIsOnline;
    @Autowired(name = "object1", required = true)
    boolean mIsOpen;
    @Autowired(name = "object2", required = true)
    String mMethod;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.img_9)
    ImageView mImg9;
    @BindView(R.id.img_10)
    ImageView mImg10;
    @BindViews({R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_9, R.id.img_10, R.id.img_13, R.id.img_14})
    List<ImageView> mViews;
    @BindViews({R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_9, R.id.rl_10, R.id.rl_13, R.id.rl_14})
    List<RelativeLayout> mRlViews;
    private PayManageMethodPresenter mPresenter;

    public static void start(String payTermType, String payTerm, String settleDate) {
        RouterUtil.goToActivity(RouterConfig.PAY_MANAGE_ACCOUNT, payTermType, payTerm, settleDate);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage_method);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = PayManageMethodPresenter.newInstance();
        mPresenter.register(this);
        showView();
    }

    private void showView() {
        mTxtTitle.setText(mIsOnline ? "在线支付设置" : "货到付款设置");
        // 设置监听
        ButterKnife.apply(mRlViews, (view, index) -> view.setOnClickListener(v -> {
            ImageView img = mViews.get(index);
            img.setSelected(!img.isSelected());
            if (!checkSelect()) {
                img.setSelected(true);
                showToast("至少有一种结算方式噢");
            }
        }));

        if (mIsOnline) {

        } else {
            ButterKnife.apply(mViews, (view, index) -> {
                if (view != mImg9 && view != mImg10) {
                    view.setEnabled(false);
                }
            });
            mImg9.setSelected(mMethod.contains("9"));
            mImg10.setSelected(mMethod.contains("10"));
        }
    }

    private boolean checkSelect() {
        boolean select = false;
        if (!CommonUtils.isEmpty(mViews)) {
            for (ImageView img : mViews) {
                if (img.isEnabled() && img.isSelected()) {
                    select = true;
                    break;
                }
            }
        }
        return select;
    }

    @OnClick({R.id.img_close, R.id.txt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            default:
                break;
        }
    }

    private void toSave() {
        String payType = mIsOnline ? "1" : "2";
        List<String> selectList = new ArrayList<>();
        if (!CommonUtils.isEmpty(mViews)) {
            for (ImageView imageView : mViews) {
                if (imageView.isSelected()) {
                    selectList.add(String.valueOf(imageView.getTag()));
                }
            }
        }
        mPresenter.editSettlementMethod(payType, TextUtils.join(",", selectList));
    }

    @Override
    public void editSuccess() {
        showToast("修改支付方式列表成功");
        ARouter.getInstance().build(RouterConfig.PAY_MANAGE)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }
}
