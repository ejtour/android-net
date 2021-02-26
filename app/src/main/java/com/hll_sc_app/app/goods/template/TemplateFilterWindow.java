package com.hll_sc_app.app.goods.template;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从商品库导入-筛选弹窗
 *
 * @author 朱英松
 * @date 2019/6/28
 */
public class TemplateFilterWindow extends BaseShadowPopupWindow {
    @BindView(R.id.txt_brandNameTitle)
    TextView mTxtBrandNameTitle;
    @BindView(R.id.edt_brandName)
    EditText mEdtBrandName;
    @BindView(R.id.txt_productPlace)
    TextView mTxtProductPlace;
    @BindView(R.id.edt_productPlace)
    EditText mEdtProductPlace;
    @BindView(R.id.txt_reset)
    TextView mTxtReset;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    @BindView(R.id.ll_content)
    ConstraintLayout mLlContent;
    private ConfirmListener mListener;

    TemplateFilterWindow(Activity activity) {
        super(activity);
        this.mActivity = activity;
        View rootView = View.inflate(activity, R.layout.window_template_filter, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setAnimationStyle(R.style.BaseRightAnimation);
        this.setWidth((int) (UIUtils.getScreenWidth(activity) * 0.8F));
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable());
    }

    void setConfirmListener(ConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_reset, R.id.txt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_reset:
                mEdtBrandName.setText(null);
                mEdtProductPlace.setText(null);
                break;
            case R.id.txt_confirm:
                List<String> list = new ArrayList<>();
                String brandName = getBrandName();
                if (!TextUtils.isEmpty(brandName)) {
                    list.add(brandName);
                }
                String productPlace = getProductPlace();
                if (!TextUtils.isEmpty(productPlace)) {
                    list.add(productPlace);
                }
                mListener.confirm(TextUtils.join(",", list));
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }

    public String getBrandName() {
        return mEdtBrandName.getText().toString().trim();
    }

    public String getProductPlace() {
        return mEdtProductPlace.getText().toString().trim();
    }

    public interface ConfirmListener {
        /**
         * 确定
         */
        void confirm(String label);
    }
}
