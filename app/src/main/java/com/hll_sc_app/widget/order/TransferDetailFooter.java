package com.hll_sc_app.widget.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class TransferDetailFooter extends ConstraintLayout {
    @BindView(R.id.tdf_remark)
    TextView mRemark;
    @BindView(R.id.tdf_order_amount)
    TextView mOrderAmount;
    @BindView(R.id.tdf_amount)
    TextView mAmount;
    @BindView(R.id.tdf_order_no)
    TextView mOrderNo;
    @BindView(R.id.tdf_order_time)
    TextView mOrderTime;
    @BindView(R.id.tdf_pay_method)
    TextView mPayMethod;
    @BindView(R.id.tdf_copy_order_no)
    TextView mCopyOrderNo;
    @BindView(R.id.tdf_order_source)
    TextView mOrderSource;

    public TransferDetailFooter(Context context) {
        this(context, null);
    }

    public TransferDetailFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransferDetailFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_transfer_detail_footer, this);
        ButterKnife.bind(this, view);
    }

    public void setData(TransferBean data) {
        mRemark.setText(data.getBillRemark());
        mOrderAmount.setText(handleItemAmount(data.getTotalPrice()));
        mAmount.setText(handleAmount(data.getTotalPrice(), 10f / 16));
        mOrderNo.setText(data.getPurchaseBillNo());
        mCopyOrderNo.setTag(data.getPurchaseBillNo());
        mOrderTime.setText(DateUtil.getReadableTime(data.getBillCreateTime()));
        mPayMethod.setText(OrderHelper.getPayType(data.getPayType()));
        mOrderSource.setText(data.getBillSource() == 1 ? "哗啦啦供应链" : "天财供应链");
    }

    private CharSequence handleItemAmount(double money) {
        return handleAmount(money, 1);
    }

    private CharSequence handleAmount(double money, float proportion) {
        String source = String.format("¥%s", CommonUtils.formatMoney(money));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(proportion), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @OnClick({R.id.tdf_copy_order_no})
    public void onViewClicked(View view) {
        if (view.getTag() == null) return;
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm == null) {
            return;
        }
        ClipData clipData = ClipData.newPlainText("编号", view.getTag().toString());
        cm.setPrimaryClip(clipData);
        ToastUtils.showShort(getContext(), "复制成功");
    }
}
