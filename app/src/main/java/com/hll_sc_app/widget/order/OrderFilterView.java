package com.hll_sc_app.widget.order;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/5
 */

public class OrderFilterView extends ConstraintLayout {
    @BindView(R.id.otf_label)
    TextView mLabel;
    @BindView(R.id.otf_interval)
    TextView mInterval;
    @BindView(R.id.otf_cancel)
    ImageView mCancel;

    public OrderFilterView(Context context) {
        this(context, null);
    }

    public OrderFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_order_time_filter_hint, this);
        ButterKnife.bind(this, view);
    }

    public void setData(OrderParam param) {
        if (!TextUtils.isEmpty(param.getFormatCreateStart(Constants.SIGNED_YYYY_MM_DD))) {
            setVisibility(View.VISIBLE);
            mLabel.setText("当前按下单时间筛选");
            mInterval.setText(String.format("%s ~ %s", param.getFormatCreateStart(Constants.SIGNED_YYYY_MM_DD),
                    param.getFormatCreateEnd(Constants.SIGNED_YYYY_MM_DD)));
        } else if (!TextUtils.isEmpty(param.getFormatExecuteStart(Constants.SIGNED_YYYY_MM_DD_HH))) {
            setVisibility(View.VISIBLE);
            mLabel.setText("当前按到货时间筛选");
            mInterval.setText(String.format("%s ~ %s", param.getFormatExecuteStart(Constants.SIGNED_YYYY_MM_DD_HH),
                    param.getFormatExecuteEnd(Constants.SIGNED_YYYY_MM_DD_HH)));
        } else if (!TextUtils.isEmpty(param.getFormatSignStart(Constants.SIGNED_YYYY_MM_DD_HH))) {
            setVisibility(View.VISIBLE);
            mLabel.setText("当前按签收时间筛选");
            mInterval.setText(String.format("%s ~ %s", param.getFormatSignStart(Constants.SIGNED_YYYY_MM_DD_HH),
                    param.getFormatSignEnd(Constants.SIGNED_YYYY_MM_DD_HH)));
        } else {
            setVisibility(View.GONE);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mCancel.setOnClickListener(l);
    }
}
