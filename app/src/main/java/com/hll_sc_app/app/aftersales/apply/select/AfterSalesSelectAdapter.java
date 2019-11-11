package com.hll_sc_app.app.aftersales.apply.select;

import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public class AfterSalesSelectAdapter extends BaseQuickAdapter<AfterSalesDetailsBean, BaseViewHolder> {
    private final DetailsType mLabel;
    private IChangeListener mListener;

    public AfterSalesSelectAdapter(List<AfterSalesDetailsBean> list, DetailsType type,IChangeListener listener) {
        super(R.layout.item_after_sales_select,list);
        mLabel = type;
        mListener = listener;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        EditText editText = helper.getView(R.id.ass_return_num);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                AfterSalesDetailsBean item = getItem(helper.getAdapterPosition());
                if (item != null) {
                    Utils.processMoney(s,false);
                    if (!TextUtils.isEmpty(s.toString()) && Double.parseDouble(s.toString()) > item.getRefundableNum()) {
                        s.clear();
                        ToastUtils.showShort(helper.itemView.getContext(), "输入数量大于可退数量，请重新输入");
                    }
                    item.setRefundNum(TextUtils.isEmpty(s.toString()) ? 0 : Double.parseDouble(s.toString()));
                    if (mListener != null) {
                        mListener.onChanged();
                    }
                }
            }
        });

        helper.getView(R.id.ass_check).setOnClickListener(v -> {
            AfterSalesDetailsBean item = getItem(helper.getAdapterPosition());
            if (item != null) {
                item.setSelected(!item.isSelected());
                notifyItemChanged(helper.getAdapterPosition());
                if (mListener != null) {
                    mListener.onChanged();
                }
            }
        });

        // 退货时可为小数，押金不可为小数
        if (mLabel == DetailsType.DEPOSIT) {
            editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        }
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AfterSalesDetailsBean item) {
        // 输入框默认值为可退全部数量
        EditText editText = (helper.getView(R.id.ass_return_num));
        editText.setText(item.getRefundNum() == 0 ? "" : CommonUtils.formatNumber(item.getRefundNum()));
        // 选中状态
        CheckBox view = helper.getView(R.id.ass_check);
        view.setChecked(item.isSelected());
        view.setEnabled(item.isCanRefund() /*&& item.getNewPrice() > 0*/);

        // 商品缩略图
        ((GlideImageView) helper.getView(R.id.ass_img)).setImageURL(item.getImgUrl());
        helper.setText(R.id.ass_productName, item.getProductName())
                .setText(R.id.ass_spec_content, item.getProductSpec())
                .setText(R.id.ass_spec_price, processSpecPrice(String.format("¥%s/%s", CommonUtils.formatMoney(item.getNewPrice()), item.getStandardUnit())))
                .setText(R.id.ass_order_num, String.format("订货：%s%s", CommonUtils.formatNum(item.getProductNum()), item.getSaleUnitName()))
                .setText(R.id.ass_order_delivery_num, String.format("发货：%s%s", CommonUtils.formatNum(item.getAdjustmentNum()), item.getAdjustmentUnit()))
                .setText(R.id.ass_order_confirmed_num, String.format("签收：%s%s", CommonUtils.formatNum(item.getInspectionNum()), item.getInspectionUnit()))
                .setText(R.id.ass_return_num_hint, item.isCanRefund() ?
                        String.format("%s%s%s", mLabel.getLabel(), CommonUtils.formatNum(item.getRefundableNum()), item.getInspectionUnit()) :
                        item.getCanNotRefundReason())
                .setText(R.id.ass_return_unit, item.getInspectionUnit())
                .setText(R.id.ass_return_label, mLabel.getEditPrefix())
                .setGone(R.id.ass_edit_group, item.isSelected());
    }

    private SpannableString processSpecPrice(String source) {
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.3f), source.indexOf("¥") + 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
