package com.hll_sc_app.widget.goodsdemand;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandHelper;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public class GoodsDemandDetailHeader extends ConstraintLayout {
    @BindView(R.id.ddh_status)
    TextView mStatus;
    @BindView(R.id.ddh_status_icon)
    ImageView mStatusIcon;
    @BindView(R.id.ddh_status_group)
    Group mStatusGroup;
    @BindView(R.id.ddh_supplier)
    TextView mSupplier;
    @BindView(R.id.ddh_supplier_group)
    Group mSupplierGroup;
    @BindView(R.id.ddh_purchaser_label)
    TextView mPurchaserLabel;
    @BindView(R.id.ddh_purchaser)
    TextView mPurchaser;
    @BindView(R.id.ddh_name)
    TextView mName;
    @BindView(R.id.ddh_desc)
    TextView mDesc;
    @BindView(R.id.ddh_brand)
    TextView mBrand;
    @BindView(R.id.ddh_spec)
    TextView mSpec;
    @BindView(R.id.ddh_origin)
    TextView mOrigin;
    @BindView(R.id.ddh_price)
    TextView mPrice;
    @BindView(R.id.ddh_pack)
    TextView mPack;
    @BindView(R.id.ddh_producer)
    TextView mProducer;
    @BindView(R.id.ddh_pic)
    ThumbnailView mPic;
    @BindView(R.id.ddh_pic_group)
    Group mPicGroup;

    public GoodsDemandDetailHeader(Context context) {
        this(context, null);
    }

    public GoodsDemandDetailHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoodsDemandDetailHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_goods_demand_detail_header, this);
        ButterKnife.bind(this, view);
    }

    public void setData(GoodsDemandBean bean) {
        boolean crm = UserConfig.crm();
        if (bean.getStatus() == 1) {
            if (crm) {
                mStatusGroup.setVisibility(VISIBLE);
                mStatus.setText("待回复：反馈已提交至合作供应商，请耐心等待回复");
            } else {
                LayoutParams layoutParams = (LayoutParams) mPurchaserLabel.getLayoutParams();
                layoutParams.goneTopMargin = 0;
            }
        } else {
            mStatusGroup.setVisibility(VISIBLE);
            if (bean.getStatus() == 3) {
                mStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                mStatus.setText(crm ? "已上架：您需要的商品已上架" : "已通知采购商相关商品已上架");
            } else mStatus.setText(String.format("已回复：%s", bean.getProductReply()));
        }
        mStatusIcon.setImageResource(GoodsDemandHelper.getIcon(bean.getStatus()));
        if (crm) {
            mSupplierGroup.setVisibility(VISIBLE);
            mSupplier.setText(bean.getSupplyName());
        }
        mPurchaser.setText(bean.getPurchaserName());
        mName.setText(bean.getProductName());
        mDesc.setText(bean.getProductBrief());
        mBrand.setText(bean.getProductBrand());
        mSpec.setText(bean.getSpecContent());
        if (!TextUtils.isEmpty(bean.getPlaceProvince()) && !TextUtils.isEmpty(bean.getPlaceCity())) {
            mOrigin.setText(String.format("%s - %s", bean.getPlaceProvince(), bean.getPlaceCity()));
        }
        mPrice.setText(0 == bean.getMarketPrice() ? null : CommonUtils.formatMoney(bean.getMarketPrice()));
        mPack.setText(bean.getPackMethod());
        mProducer.setText(bean.getProducer());
        if (!TextUtils.isEmpty(bean.getImgUrl())) {
            mPicGroup.setVisibility(VISIBLE);
            mPic.setData(bean.getImgUrl().split(","));
        }
        requestLayout();
    }
}
