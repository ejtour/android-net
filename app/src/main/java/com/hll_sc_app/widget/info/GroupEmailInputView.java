package com.hll_sc_app.widget.info;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

public class GroupEmailInputView extends RelativeLayout {
    @BindViews({R.id.gei_email_1, R.id.gei_email_2, R.id.gei_email_3, R.id.gei_email_4, R.id.gei_email_5})
    List<AppCompatEditText> mTexts;

    public GroupEmailInputView(Context context) {
        this(context, null);
    }

    public GroupEmailInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupEmailInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_group_email_input, this);
        ButterKnife.bind(this, view);
    }

    public String getEmail() {
        List<String> list = new ArrayList<>();
        for (EditText text : mTexts) {
            String trim = text.getText().toString().trim();
            if (!TextUtils.isEmpty(trim)) {
                if (!Utils.checkEmail(trim)) {
                    ToastUtils.showShort("请输入正确的邮箱");
                    return null;
                }
                list.add(trim);
            }
        }
        return TextUtils.join(";", list);
    }

    public void setEmail(String content) {
        String[] split = content.split(";");
        for (int i = 0; i < split.length; i++) {
            mTexts.get(i).setText(split[i]);
            mTexts.get(i).setSelection(split[i].length());
        }
    }
}
