package com.hll_sc_app.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/10/22
 */
public class ContactDialog extends BaseDialog {
    @BindView(R.id.dc_name)
    TextView mName;
    @BindView(R.id.dc_phone)
    TextView mPhone;

    public ContactDialog(@NonNull Activity context, String name, String phone) {
        super(context);
        mName.setText(name);
        mPhone.setTag(phone);
        mPhone.setText(PhoneUtil.formatPhoneNum(phone));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    private void initWindow() {
        if (getWindow() == null) {
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(100);
        setCancelable(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_contact, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.dc_dial)
    void dial() {
        if (mPhone.getTag() != null) {
            UIUtils.callPhone(mPhone.getTag().toString());
        }
        dismiss();
    }

    @OnClick(R.id.dc_cancel)
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
