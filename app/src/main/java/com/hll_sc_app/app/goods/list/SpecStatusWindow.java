package com.hll_sc_app.app.goods.list;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择规格上下架弹窗
 *
 * @author 朱英松
 * @date 2019/6/11
 */
public class SpecStatusWindow extends BaseShadowPopupWindow {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    private SpecAdapter mAdapter;
    private ItemSelectListener mListener;

    SpecStatusWindow(Activity context, GoodsBean bean) {
        super(context);
        View view = View.inflate(context, R.layout.window_spec_status, null);
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(UIUtils.dip2px(350));
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xFFEEEEEE));
        initView(context, bean);
    }

    private void initView(Activity context, GoodsBean goodsBean) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new SimpleDecoration(0xFFEEEEEE, UIUtils.dip2px(1)));
        mAdapter = new SpecAdapter(goodsBean);
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            SpecsBean bean = (SpecsBean) adapter.getItem(position);
            if (bean != null) {
                if (mListener != null) {
                    mListener.confirm(Collections.singletonList(bean));
                }
                dismiss();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        if (GoodsListAdapter.isUp(goodsBean)) {
            mTxtConfirm.setText("全部下架");
            mTxtTitle.setText("选择规格下架");
        } else {
            mTxtConfirm.setText("全部上架");
            mTxtTitle.setText("选择规格上架");
        }
    }

    void setListener(ItemSelectListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.txt_confirm && mListener != null) {
            mListener.confirm(mAdapter.getData());
        }
        dismiss();
    }

    interface ItemSelectListener {
        /**
         * 确定
         *
         * @param beans 规格
         */
        void confirm(List<SpecsBean> beans);
    }

    public static class SpecAdapter extends BaseQuickAdapter<SpecsBean, BaseViewHolder> {
        private boolean mIsDepositProduct;
        private boolean mEditable = true;

        public SpecAdapter(GoodsBean goodsBean) {
            super(R.layout.item_window_spec_list, goodsBean != null ? goodsBean.getSpecs() : null);
            if (goodsBean != null) {
                mIsDepositProduct = TextUtils.equals(goodsBean.getDepositProductType(), GoodsBean.DEPOSIT_GOODS_TYPE);
            }
        }

        /**
         * @param editable 组合商品下的商品明细在商品详情 不能操作上下架
         */
        public void setNewData(GoodsBean goodsBean, boolean editable) {
            if (goodsBean != null) {
                super.setNewData(goodsBean.getSpecs());
                mEditable = editable;
                mIsDepositProduct = TextUtils.equals(goodsBean.getDepositProductType(), GoodsBean.DEPOSIT_GOODS_TYPE);
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, SpecsBean item) {
            helper.setText(R.id.txt_productPrice, getProductPrice(item))
                    .setGone(R.id.txt_standardUnitStatus, TextUtils.equals(item.getStandardUnitStatus(), "1"))
                    .setGone(R.id.txt_assistUnitStatus, TextUtils.equals(item.getAssistUnitStatus(), "1"))
                    .setText(R.id.txt_specContent, getMiddleContent(item))
                    .setText(R.id.txt_productCode, getBottomContent(item))
                    // 规格状态：4-已上架，5-未上架
                    .setText(R.id.txt_update, TextUtils.equals(SpecsBean.SPEC_STATUS_UP, item.getSpecStatus()) ? "下架" :
                            "上架")
                    // 押金商品不能操作上下架
                    .setGone(R.id.txt_update, !mIsDepositProduct && mEditable)
                    .addOnClickListener(R.id.txt_update);
        }

        private SpannableString getProductPrice(SpecsBean item) {
            String price =
                "¥" + CommonUtils.formatMoney(CommonUtils.getDouble(item.getProductPrice())) + "/" + item.getSaleUnitName();
            SpannableString spannableString = new SpannableString(price);
            spannableString.setSpan(new RelativeSizeSpan(0.625F), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(0.625F), price.indexOf("/"), price.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        private String getMiddleContent(SpecsBean item) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(item.getSpecContent())) {
                stringBuilder.append("规格：").append(item.getSpecContent()).append(" | ");
            }
            // 押金商品不展示库存
            if (!TextUtils.isEmpty(item.getProductStock())) {
                stringBuilder.append("库存：").append(item.getProductStock()).append(" | ");
            }
            if (!TextUtils.isEmpty(item.getBuyMinNum())) {
                stringBuilder.append("起购：").append(item.getBuyMinNum()).append(item.getSaleUnitName()).append("起");
            }
            return stringBuilder.toString();
        }

        private String getBottomContent(SpecsBean item) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(item.getSkuCode())) {
                stringBuilder.append("条码：").append(item.getSkuCode()).append(" | ");
            }
            if (!TextUtils.isEmpty(item.getRation())) {
                stringBuilder.append("转换率：").append(item.getRation());
            }
            return stringBuilder.toString();
        }
    }
}
