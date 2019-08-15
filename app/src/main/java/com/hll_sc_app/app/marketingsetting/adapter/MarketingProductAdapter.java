package com.hll_sc_app.app.marketingsetting.adapter;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

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

    public MarketingProductAdapter(@Nullable List<SkuGoodsBean> data, @Modal int isEdit) {
        super(R.layout.list_item_marketing_product, data);
        this.isEdit = isEdit;
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
        helper.setVisible(R.id.img_delete, isEdit == Modal.EDIT)
                .setVisible(R.id.group_edt_promote, isEdit != Modal.HIDE)
                .setText(R.id.edt_promote_num, item.getPromoteNum())
                .setText(R.id.txt_product_name, item.getProductName())
                .setText(R.id.txt_spec_content, item.getSpecContent())
                .setText(R.id.txt_product_code, "编码：" + item.getProductCode())
                .setText(R.id.txt_product_price, "¥" + item.getProductPrice());

        GlideImageView productImage = helper.getView(R.id.img_product);
        productImage.setImageURL(item.getImgUrl());
        helper.addOnClickListener(R.id.img_delete);
        helper.getView(R.id.group_edt_promote).setEnabled(isEdit == Modal.EDIT);

    }

    @IntDef({Modal.EDIT, Modal.SHOW, Modal.HIDE})
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
    }
}

