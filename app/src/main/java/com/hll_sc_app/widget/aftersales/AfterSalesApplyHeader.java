package com.hll_sc_app.widget.aftersales;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;
import com.hll_sc_app.app.aftersales.common.AfterSalesType;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ImageUploadGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesApplyHeader extends ConstraintLayout {
    @BindView(R.id.sah_reason_label)
    TextView mReasonLabel;
    @BindView(R.id.sah_option)
    TextView mOption;
    @BindView(R.id.sah_desc_label)
    TextView mDescLabel;
    @BindView(R.id.sah_desc_edit)
    EditText mDescEdit;
    @BindView(R.id.sah_remain_count)
    TextView mRemainCount;
    @BindView(R.id.sah_upload_group)
    ImageUploadGroup mUploadGroup;
    @BindView(R.id.sah_details_label)
    TextView mDetailsLabel;
    @BindView(R.id.sah_details_edit)
    TextView mDetailsEdit;
    @BindView(R.id.sah_add_item)
    TextView mAddItem;

    public AfterSalesApplyHeader(Context context) {
        this(context, null);
    }

    public AfterSalesApplyHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AfterSalesApplyHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_after_sales_apply_header, this);
        ButterKnife.bind(this, view);
        setBackgroundColor(Color.WHITE);
        mUploadGroup.register((ILoadView) context);
    }

    public void init(IAfterSalesApplyContract.IAfterSalesApplyText afterSalesText) {
        mReasonLabel.setText(String.format("%s原因", afterSalesText.getReasonPrefix()));
        mOption.setHint(String.format("请选择%s原因", afterSalesText.getReasonPrefix()));
        mDescLabel.setText(String.format("%s说明", afterSalesText.getLabel()));
        mDescEdit.setHint(afterSalesText.getEditHint());
        mDetailsLabel.setText(afterSalesText.getDetailsLabel());
        if (!TextUtils.isEmpty(afterSalesText.getAddTip())) {
            mAddItem.setVisibility(VISIBLE);
            mAddItem.setText(afterSalesText.getAddTip());
        }
    }

    public void initData(AfterSalesApplyParam param) {
        mOption.setText(param.getReasonDesc());
        mDescEdit.setText(param.getExplain());
        updateEditVisibility(param);
        if (param.getVoucher() != null)
            mUploadGroup.showImages(param.getVoucher().split(","));
    }

    public void updateEditVisibility(AfterSalesApplyParam param) {
        if (param.getAfterSalesType() != AfterSalesType.ORDER_REJECT) {
            if (CommonUtils.isEmpty(param.getAfterSalesDetailList())) {
                mAddItem.setVisibility(VISIBLE);
                mDetailsEdit.setVisibility(GONE);
            } else {
                mAddItem.setVisibility(GONE);
                mDetailsEdit.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOption.setOnClickListener(l);
        mDetailsEdit.setOnClickListener(l);
        mAddItem.setOnClickListener(l);
    }

    @OnTextChanged(R.id.sah_desc_edit)
    public void onTextChanged(CharSequence s) {
        mRemainCount.setText(String.valueOf(100 - s.length()));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mUploadGroup.onActivityResult(requestCode, resultCode, data);
    }

    public void inflateData(AfterSalesApplyParam param){
        param.setExplain(mDescEdit.getText().toString());
        param.setVoucher(TextUtils.join(",", mUploadGroup.getUploadImgUrls()));
    }
}
