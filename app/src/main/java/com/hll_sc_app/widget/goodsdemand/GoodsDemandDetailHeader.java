package com.hll_sc_app.widget.goodsdemand;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.detail.GoodsDetailActivity;
import com.hll_sc_app.app.goodsdemand.GoodsDemandHelper;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
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
    @BindView(R.id.ddh_reply_customer_group)
    Group mReplyCustomerGroup;
    @BindView(R.id.ddh_reply_sale_group)
    Group mReplySaleGroup;
    @BindView(R.id.ddh_replay_title_group)
    Group mReplyTitleGroup;
    @BindView(R.id.ddh_reply_customer)
    TextView mTxtReplyCustomer;
    @BindView(R.id.ddh_reply_sale)
    TextView mTxtReplySale;
    @BindView(R.id.ddh_title_status)
    TextView mTxtReplyTitle;


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
                mReplyTitleGroup.setVisibility(VISIBLE);
                mReplySaleGroup.setVisibility(GONE);
                mReplyCustomerGroup.setVisibility(GONE);
                mTxtReplyTitle.setText("待回复：反馈已提交至合作供应商，请耐心等待回复");
            } else {
                LayoutParams layoutParams = (LayoutParams) mPurchaserLabel.getLayoutParams();
                layoutParams.goneTopMargin = 0;
            }
        } else {
            mStatusGroup.setVisibility(VISIBLE);
            mReplyTitleGroup.setVisibility(VISIBLE);

            if (bean.getStatus() == 3) {
                mTxtReplyTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                if (crm) {
                    String title = "已上架：您需要的商品已上架 点击查看商品详情";
                    SpannableString content = new SpannableString(title);
                    content.setSpan(new UnderlineSpan(), title.length() - 8, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTxtReplyTitle.setOnClickListener(v -> {
                        GoodsBean goodsBean = JSONObject.parseObject(bean.getProductReply(), GoodsBean.class);
                        GoodsDetailActivity.start(goodsBean.getProductID(), false, false);
                    });
                } else {
                    mTxtReplyTitle.setText("已通知采购商相关商品已上架");
                }
            } else {
                mTxtReplyTitle.setText("已回复");
                if (!TextUtils.isEmpty(bean.getProductReplySale())) {
                    mReplySaleGroup.setVisibility(VISIBLE);
                    mTxtReplySale.setText(bean.getProductReplySale());
                }
                if (!TextUtils.isEmpty(bean.getProductReply())) {
                    mReplyCustomerGroup.setVisibility(VISIBLE);
                    mTxtReplyCustomer.setText(bean.getProductReply());
                }
            }


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
