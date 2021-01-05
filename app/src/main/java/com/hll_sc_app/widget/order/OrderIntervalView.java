package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/25/20.
 */
public class OrderIntervalView extends FrameLayout {
    @BindView(R.id.voi_interval)
    TextView mInterval;
    @BindView(R.id.voi_cancel)
    ImageView mCancel;
    private OrderParam mParam;
    private String mLabel;

    public OrderIntervalView(Context context) {
        this(context, null);
    }

    public OrderIntervalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderIntervalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_order_interval, this);
        ButterKnife.bind(this, view);
        setOnClickListener(v -> {
            filter(mLabel);
        });
    }

    @OnClick(R.id.voi_cancel)
    void cancelFilter() {
        mParam.cancelTimeInterval();
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    public void with(OrderParam param) {
        mParam = param;
    }

    public void updateData() {
        if (mParam.getCreateStart() != 0) {
            mLabel = OptionType.OPTION_FILTER_CREATE;
            setVisibility(View.VISIBLE);
            mInterval.setText(String.format("%s：%s ~ %s", "下单时间", mParam.getFormatCreateStart(Constants.SIGNED_YYYY_MM_DD),
                    mParam.getFormatCreateEnd(Constants.SIGNED_YYYY_MM_DD)));
        } else if (mParam.getExecuteStart() != 0) {
            mLabel = OptionType.OPTION_FILTER_EXECUTE;
            setVisibility(View.VISIBLE);
            mInterval.setText(String.format("%s：%s ~ %s", "要求到货时间", mParam.getFormatExecuteStart(Constants.SIGNED_YYYY_MM_DD_HH),
                    mParam.getFormatExecuteEnd(Constants.SIGNED_YYYY_MM_DD_HH)));
        } else if (mParam.getSignStart() != 0) {
            mLabel = OptionType.OPTION_FILTER_SIGN;
            setVisibility(View.VISIBLE);
            mInterval.setText(String.format("%s：%s ~ %s", "签收时间", mParam.getFormatSignStart(Constants.SIGNED_YYYY_MM_DD_HH),
                    mParam.getFormatSignEnd(Constants.SIGNED_YYYY_MM_DD_HH)));
        } else {
            setVisibility(View.GONE);
        }
    }

    public void filter(String label) {
        mLabel = label;
        OrderHelper.showDatePicker(label, mParam, (Activity) getContext(),
                () -> EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST)));
    }

    public void setCancelVisible(boolean visible) {
        mCancel.setVisibility(visible ? VISIBLE : GONE);
    }
}
