package com.hll_sc_app.app.marketingsetting.adapter;

import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.goods.SkuGoodsBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 营销中心 活动商品展示 删除的适配器
 */
public class MarketingProductAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {
    private @Modal
    int isEdit;

    private int itemCount;

    public MarketingProductAdapter(@Nullable List<SkuGoodsBean> data, @Modal int isEdit) {
        super(R.layout.list_item_marketing_product, data);
        this.isEdit = isEdit;
    }

    @Override
    public int getItemCount() {
        return itemCount != 0 ? itemCount : super.getItemCount();
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        EditText mEditPromoteNum = holder.getView(R.id.edt_promote_num);
        if (isEdit == Modal.EDIT) {
            mEditPromoteNum.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    SkuGoodsBean skuGoodsBean = getItem(holder.getAdapterPosition());
                    if (skuGoodsBean != null) {
                        skuGoodsBean.setPromoteNum(s.toString());
                    }
                }
            });
        }
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, SkuGoodsBean item) {
        toggleModal(helper);
        helper.setText(R.id.edt_promote_num, item.getPromoteNum())
                .setText(R.id.txt_product_name, item.getProductName())
                .setText(R.id.txt_spec_content, item.getSpecContent())
                .setText(R.id.txt_product_code, "编码：" + item.getProductCode())
                .setText(R.id.txt_product_price, "¥" + item.getProductPrice())
                .setText(R.id.txt_promote_num_content_show, item.getPromoteNum());

        /*打折模式下*/
        if (isEdit == Modal.EDIT_DZ || isEdit == Modal.SHOW_DZ) {
            helper.setText(R.id.txt_product_price, "¥" + (TextUtils.isEmpty(item.getDiscountPrice()) ? item.getProductPrice() : item.getDiscountPrice()))
                    .setText(R.id.txt_product_old_price, (TextUtils.isEmpty(item.getDiscountPrice()) ? "- -" : ("¥" + item.getProductPrice())));
            // 设置中划线并加清晰
            TextView mOldPriceTxt = helper.getView(R.id.txt_product_old_price);
            mOldPriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
        GlideImageView productImage = helper.getView(R.id.img_product);
        productImage.setImageURL(item.getImgUrl());
    }

    private void toggleModal(BaseViewHolder holder) {
        switch (isEdit) {
            case Modal.EDIT:
                holder.addOnClickListener(R.id.img_delete);
                holder.getView(R.id.img_delete).setVisibility(View.VISIBLE);
                holder.getView(R.id.group_edt_promote).setVisibility(View.VISIBLE);
                holder.getView(R.id.group_promote_show).setVisibility(View.GONE);
                holder.getView(R.id.txt_product_old_price).setVisibility(View.GONE);
                break;
            case Modal.SHOW:
                holder.getView(R.id.img_delete).setVisibility(View.GONE);
                holder.getView(R.id.group_edt_promote).setVisibility(View.GONE);
                holder.getView(R.id.group_promote_show).setVisibility(View.VISIBLE);
                holder.getView(R.id.txt_product_old_price).setVisibility(View.GONE);
                break;
            case Modal.HIDE:
                holder.getView(R.id.img_delete).setVisibility(View.GONE);
                holder.getView(R.id.group_edt_promote).setVisibility(View.GONE);
                holder.getView(R.id.group_promote_show).setVisibility(View.GONE);
                holder.getView(R.id.txt_product_old_price).setVisibility(View.GONE);
                break;
            case Modal.EDIT_DZ:
                holder.addOnClickListener(R.id.img_delete);
                holder.getView(R.id.img_delete).setVisibility(View.VISIBLE);
                holder.getView(R.id.group_edt_promote).setVisibility(View.VISIBLE);
                holder.getView(R.id.group_promote_show).setVisibility(View.GONE);
                holder.getView(R.id.txt_product_old_price).setVisibility(View.VISIBLE);
                break;
            case Modal.SHOW_DZ:
                holder.getView(R.id.img_delete).setVisibility(View.GONE);
                holder.getView(R.id.group_edt_promote).setVisibility(View.GONE);
                holder.getView(R.id.group_promote_show).setVisibility(View.VISIBLE);
                holder.getView(R.id.txt_product_old_price).setVisibility(View.GONE);
                holder.getView(R.id.txt_product_old_price).setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public @Modal
    int getModal() {
        return isEdit;
    }

    public void setModal(@Modal int modal) {
        this.isEdit = modal;
        notifyDataSetChanged();
    }

    @IntDef({Modal.EDIT, Modal.SHOW, Modal.HIDE, Modal.EDIT_DZ, Modal.SHOW_DZ})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Modal {
        /**
         * 显示删除 显示促销量且编辑
         */
        int EDIT = 0;

        /**
         * 隐藏删除 显示促销量不能编辑
         */
        int SHOW = 1;


        /**
         * 隐藏删除 隐藏促销量
         */
        int HIDE = 2;

        /**
         * 打折编辑
         */
        int EDIT_DZ = 3;

        /**
         * 打折查看
         */
        int SHOW_DZ = 4;
    }
}

