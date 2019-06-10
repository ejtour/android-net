package com.hll_sc_app.app.user.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.RegisterReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;

import java.util.List;

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
    @Autowired(name = "parcelable", required = true)
    RegisterReq mReq;
    private RegisterComplementPresenter mPresenter;
    private RegisterCategoryWindow mCategoryWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complement);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = RegisterComplementPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
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
                mPresenter.queryCategory(true);
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
            .setMessage("您必须补充资料才能完成注册\n返回将放弃注册成为供应商")
            .setButton((dialog, item) -> {
                if (item == 0) {
                    finish();
                }
                dialog.dismiss();
            }, "去意已决", "继续补充")
            .create().show();
    }

    private void toRegisterComplement() {
        if (mCategoryWindow == null) {
            showToast("请选择经营品类");
            return;
        }
        List<CategoryItem> list = mCategoryWindow.getSelectList();
        if (CommonUtils.isEmpty(list)) {
            showToast("请选择经营品类");
            return;
        }
        // 补充资料界面
        mReq.setSource(1);
        mReq.setCategory(list);
        mPresenter.toRegisterComplement(mReq);
    }

    @Override
    public void registerComplementSuccess() {
        SuccessDialog.newBuilder(this)
            .setCancelable(false)
            .setImageTitle(R.drawable.ic_dialog_success)
            .setImageState(R.drawable.ic_dialog_state_success)
            .setMessageTitle("注册完成")
            .setMessage("提交成功，审核结果会以短信形式发送到您的手机，请耐心等待~")
            .setButton((dialog, item) -> {
                dialog.dismiss();
                finish();
            }, "去登录")
            .create().show();
    }

    /**
     * 显示列表
     *
     * @param list list 集合数据
     */
    @Override
    public void showCategoryWindow(List<CategoryItem> list) {
        if (mCategoryWindow == null) {
            mCategoryWindow = new RegisterCategoryWindow(this);
            mCategoryWindow.setListener(() -> {
                List<CategoryItem> listSelect = mCategoryWindow.getSelectList();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < listSelect.size(); i++) {
                    builder.append(listSelect.get(i).getCategoryName());
                    if (i != listSelect.size() - 1) {
                        builder.append(",");
                    }
                }
                mTxtCategory.setText(builder.toString());
            });
        }
        mCategoryWindow.setList(list);
        mCategoryWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
}
