package com.hll_sc_app.app.pricemanage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.ToastUtils;

/**
 * 修改售价、成本价
 *
 * @author zhuyingsong
 * @date 2019-07-15
 */
public class PriceInputDialog extends BaseDialog {

    protected PriceInputDialog(@NonNull Activity context) {
        super(context);
    }

    private PriceInputDialog(@NonNull Activity context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected PriceInputDialog(@NonNull Activity context, boolean cancelable,
                               @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static Builder newBuilder(Activity context) {
        return new Builder(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_price_input, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            WindowManager.LayoutParams params = window.getAttributes();
            params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
            params.width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(110);
            ;
        }
    }

    private void setTextTitle(String text) {
        TextView textView = mRootView.findViewById(R.id.txt_title);
        textView.setText(text);
    }

    private void setProductName(String mProductName) {
        TextView textView = mRootView.findViewById(R.id.txt_productName);
        textView.setText(mProductName);
    }

    private void setSpecContent(String mSpecContent) {
        TextView textView = mRootView.findViewById(R.id.txt_specContent);
        textView.setVisibility(TextUtils.isEmpty(mSpecContent) ? View.GONE : View.VISIBLE);
        textView.setText(mSpecContent);
    }

    private void setPrice(String price) {
        EditText edt = mRootView.findViewById(R.id.edt_content);
        edt.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
            if (s.toString().startsWith(".")) {
                s.insert(0, "0");
            }
            if (!GoodsSpecsAddActivity.PRODUCT_PRICE.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                ToastUtils.showShort(getContext(), "价格支持7位整数或小数点后两位");
            }
        });
        edt.setText(price);
        edt.setSelection(!TextUtils.isEmpty(price) ? price.length() : 0);
    }

    public String getInputString() {
        EditText editText = mRootView.findViewById(R.id.edt_content);
        return editText.getText().toString().trim();
    }

    public void setRootView(View view) {
        mRootView = view;
    }

    public void setButton(final OnClickListener listener, String[] items) {
        TextView txtCancel = mRootView.findViewById(R.id.txt_cancel);
        txtCancel.setText(items[0]);
        txtCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItem(PriceInputDialog.this, 0);
            }
        });

        TextView txtConfirm = mRootView.findViewById(R.id.txt_confirm);
        txtConfirm.setText(items[1]);
        txtConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItem(PriceInputDialog.this, 1);
            }
        });
    }

    public interface OnClickListener {
        void onItem(PriceInputDialog dialog, int item);
    }

    public static class Builder {
        private final Params p;

        private Builder(Activity context) {
            p = new Params();
            p.mContext = context;
            p.mCancelable = true;
        }

        public Builder setTextTitle(String textTitle) {
            p.mTextTitle = textTitle;
            return this;
        }

        public Builder setProductName(String productName) {
            p.mProductName = productName;
            return this;
        }

        public Builder setSpecContent(String specContent) {
            p.mSpecContent = specContent;
            return this;
        }

        public Builder setPrice(String price) {
            p.mPrice = price;
            return this;
        }

        public Builder setButton(OnClickListener listener, String cancel, String verify) {
            p.mOnClickListener = listener;
            p.items = new String[]{cancel, verify};
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            p.mCancelable = cancelable;
            return this;
        }

        public Builder setText(String text) {
            p.mText = text;
            return this;
        }

        public PriceInputDialog create() {
            final PriceInputDialog dialog = new PriceInputDialog(p.mContext, R.style.BaseDialog);
            dialog.setTextTitle(p.mTextTitle);
            dialog.setProductName(p.mProductName);
            dialog.setSpecContent(p.mSpecContent);
            dialog.setPrice(p.mPrice);
            dialog.setButton(p.mOnClickListener, p.items);
            dialog.setCancelable(p.mCancelable);
            dialog.setCanceledOnTouchOutside(p.mCancelable);
            return dialog;
        }
    }


    static class Params {
        Activity mContext;
        boolean mCancelable;
        String mTextTitle;
        String mProductName;
        String mSpecContent;
        String mPrice;
        OnClickListener mOnClickListener;
        String[] items;
        String mText;
    }
}
