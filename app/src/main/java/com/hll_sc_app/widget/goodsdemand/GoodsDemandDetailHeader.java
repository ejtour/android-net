package com.hll_sc_app.widget.goodsdemand;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
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
    @BindView(R.id.ddh_status_icon)
    ImageView mStatusIcon;
    //    @BindView(R.id.ddh_status_group)
//    Group mStatusGroup;
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
        mPic.enablePreview(true);
    }

    public void setData(GoodsDemandBean bean) {
        boolean crm = UserConfig.crm();
        showTitleGroup(crm, bean);
        if (crm) {
            if (bean.getStatus() == 1) {
                mStatusIcon.setImageResource(R.drawable.ic_exclamation_circle_yellow);
                mTxtReplyTitle.setText("待回复：反馈已提交至合作供应商，请耐心等待回复");
                mTxtReplyTitle.setTextColor(0xFFF6BB42);
            } else if (bean.getStatus() == 2) {
                mTxtReplyTitle.setText("已回复");
                mTxtReplyCustomer.setText("已回复客户");
                mTxtReplySale.setText(bean.getProductReplySale());
                mTxtReplyTitle.setTextColor(0xFF7ED321);
            } else if (bean.getStatus() == 3) {
                mTxtReplyTitle.setText("已上架：您需要的商品已上架");
                mTxtReplyTitle.setTextColor(0xFF5695D2);
            } else if (bean.getStatus() == 4){
                mTxtReplyTitle.setText("已取消：您已取消该反馈");
                mTxtReplyTitle.setTextColor(0xFF999999);
            }
        } else {
            if (bean.getStatus() == 1) {
                LayoutParams layoutParams = (LayoutParams) mPurchaserLabel.getLayoutParams();
                layoutParams.goneTopMargin = 0;
            } else if (bean.getStatus() == 2) {
                mTxtReplyTitle.setText("已回复");
                mTxtReplyTitle.setTextColor(0xFF7ED321);
                mTxtReplyCustomer.setText(bean.getProductReply());
                mTxtReplySale.setText(bean.getProductReplySale());
            } else if (bean.getStatus() == 3) {
                mTxtReplyTitle.setText("已通知客户相关商品已上架");
                mTxtReplyTitle.setTextColor(0xFF5695D2);
            } else if (bean.getStatus() == 4){
                mTxtReplyTitle.setText("已取消：该反馈已被取消");
                mTxtReplyTitle.setTextColor(0xFF999999);
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

    private void showTitleGroup(boolean isCrm, GoodsDemandBean bean) {
        int status = bean.getStatus();
        if (status == 1) {
            mReplyTitleGroup.setVisibility(isCrm ? VISIBLE : GONE);
            mReplySaleGroup.setVisibility(GONE);
            mReplyCustomerGroup.setVisibility(GONE);
        } else if (status == 2) {
            mReplyTitleGroup.setVisibility(VISIBLE);
            mReplySaleGroup.setVisibility(TextUtils.isEmpty(bean.getProductReplySale()) ? GONE : VISIBLE);
            mReplyCustomerGroup.setVisibility(TextUtils.isEmpty(bean.getProductReply()) ? GONE : VISIBLE);
        } else {
            mReplyTitleGroup.setVisibility(VISIBLE);
            mReplySaleGroup.setVisibility(GONE);
            mReplyCustomerGroup.setVisibility(GONE);
        }
    }
}
