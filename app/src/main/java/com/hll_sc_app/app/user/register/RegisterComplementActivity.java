package com.hll_sc_app.app.user.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.citymall.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册页面-完善资料
 *
 * @author zhuyingsong
 * @date 2019/6/6
 */
@Route(path = RouterConfig.USER_REGISTER_COMPLEMENT)
public class RegisterComplementActivity extends BaseLoadActivity implements RegisterComplementContract.IRegisterComplementView {
    @BindView(R.id.txt_category)
    TextView mTxtCategory;
    private RegisterComplementPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complement);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = RegisterComplementPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {

    }

    @OnClick({R.id.img_close, R.id.txt_confirm, R.id.rl_category})
    public void onViewClicked(View view) {
        ViewUtils.clearEditFocus(view);
        switch (view.getId()) {
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.txt_confirm:
                toRegisterComplement();
                break;
            case R.id.rl_category:
                showCategoryWindow();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        SuccessDialog.newBuilder(this)
            .setCancelable(false)
            .setImageTitle(R.drawable.ic_dialog_success)
            .setImageState(R.drawable.ic_dialog_state_success)
            .setMessageTitle("要放弃注册么")
            .setMessage("您必须补充资料才能完成注册返回将放弃注册成为供应商")
            .setButton((dialog, item) -> {
                if (item == 0) {
                    // TODO:返回登录页面
                    finish();
                }
                dialog.dismiss();
            }, "去意已决", "继续补充")
            .create().show();
    }

    private void toRegisterComplement() {

    }

    private void showCategoryWindow() {

    }

    @Override
    public void registerComplementSuccess() {
        SuccessDialog.newBuilder(this)
            .setCancelable(false)
            .setImageTitle(R.drawable.ic_dialog_success)
            .setImageState(R.drawable.ic_dialog_state_success)
            .setMessageTitle("注册完成")
            .setMessage("请等待审核")
            .setButton((dialog, item) -> {
                dialog.dismiss();
                finish();
            }, "去登录")
            .create().show();
    }
}
