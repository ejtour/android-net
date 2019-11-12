package com.hll_sc_app.widget.goodsdemand;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

public class GoodsSpecialDemandHeader extends ConstraintLayout {
    @BindView(R.id.sdh_image)
    GlideImageView mImage;
    @BindView(R.id.sdh_name)
    TextView mName;
    @BindView(R.id.sdh_code)
    TextView mCode;

    public GoodsSpecialDemandHeader(Context context) {
        this(context, null);
    }

    public GoodsSpecialDemandHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsSpecialDemandHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.WHITE);
        int padding = UIUtils.dip2px(10);
        setPadding(padding, padding, padding, padding);
        View view = View.inflate(context, R.layout.view_goods_special_demand_header, this);
        ButterKnife.bind(this, view);
    }

    public void setData(SpecialDemandBean bean) {
        mImage.setImageURL(bean.getProductImgUrl());
        mName.setText(bean.getProductName());
        mCode.setText(String.format("编码：%s", bean.getProductCode()));
    }
}
