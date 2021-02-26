package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/8
 */

public class PayMethodDialog extends BaseDialog {
    @BindView(R.id.dss_title)
    TextView mTitle;
    @BindView(R.id.dss_list_view)
    RecyclerView mListView;
    private PayMethodAdapter mAdapter;
    private OnSelectPayMethodListener mListener;

    public PayMethodDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = UIUtils.dip2px(330);
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.white);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_single_selection, null);
        ButterKnife.bind(this, view);
        mTitle.setText("选择付款方式");
        mAdapter = new PayMethodAdapter();
        mAdapter.bindToRecyclerView(mListView);
        return view;
    }

    @Override
    @OnClick(R.id.dss_close)
    public void dismiss() {
        super.dismiss();
    }

    public PayMethodDialog refreshList(List<PayMethod> payMethods) {
        mAdapter.setNewData(payMethods);
        return this;
    }

    public PayMethodDialog select(PayMethod method) {
        mAdapter.select(method);
        return this;
    }

    public PayMethodDialog setListener(OnSelectPayMethodListener listener) {
        mListener = listener;
        return this;
    }

    public interface OnSelectPayMethodListener {
        void onSelectPayMethod(PayMethod method);
    }

    public static class PayMethod extends NameValue {
        private int icon;

        public PayMethod(String name, String value, int icon) {
            super(name, value);
            this.icon = icon;
        }
    }

    class PayMethodAdapter extends BaseQuickAdapter<PayMethod, BaseViewHolder> {

        private PayMethod mPayMethod;

        private PayMethodAdapter() {
            super(R.layout.item_pay_method);

            setOnItemClickListener((adapter, view, position) -> {
                dismiss();
                if (mListener == null) {
                    return;
                }
                mPayMethod = getItem(position);
                mListener.onSelectPayMethod(mPayMethod);
                notifyDataSetChanged();
            });
        }

        private void select(PayMethod method) {
            mPayMethod = method;
            if (!CommonUtils.isEmpty(mData)) notifyDataSetChanged();
        }

        @Override
        protected void convert(BaseViewHolder helper, PayMethod item) {
            TextView label = helper.getView(R.id.iss_label);
            label.setCompoundDrawablesWithIntrinsicBounds(item.icon, 0, 0, 0);
            label.setText(item.getName());
            label.setSelected(mPayMethod == item);
            helper.setGone(R.id.iss_check, mPayMethod == item);
        }
    }
}
