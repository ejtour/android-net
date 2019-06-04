package com.hll_sc_app.app.user.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 帐号拥有多个员工类型需要选择
 *
 * @author zhuyingsong
 * @date 20190604
 */
public class LoginChoiceDialog extends BaseDialog {
    private ItemClickListener mListener;

    LoginChoiceDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_login_choice, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = UIUtils.dip2px(305);
        params.height = UIUtils.dip2px(360);
        params.gravity = Gravity.TOP;
        params.y = (int) (UIUtils.getScreenHeight(MyApplication.getInstance()) * 0.08);
    }

    void setAddClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_business, R.id.txt_no_login, R.id.txt_manage})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_manage:
                if (mListener != null) {
                    mListener.toManage();
                }
                break;
            case R.id.txt_business:
                if (mListener != null) {
                    mListener.toBusiness();
                }
                break;
            case R.id.txt_no_login:
                if (mListener != null) {
                    mListener.noLogin();
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    public interface ItemClickListener {
        /**
         * 管理型
         */
        void toManage();

        /**
         * 业务型
         */
        void toBusiness();

        /**
         * 暂不登录
         */
        void noLogin();
    }
}
