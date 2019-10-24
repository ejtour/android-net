package com.hll_sc_app.app.goodsdemand.commit;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandHelper;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandItem;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.widget.ImageUploadGroup;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/22
 */

public class GoodsDemandCommitAdapter extends BaseQuickAdapter<GoodsDemandItem, BaseViewHolder> {
    private final IChangeListener mListener;

    GoodsDemandCommitAdapter(List<GoodsDemandItem> list, IChangeListener listener) {
        super(R.layout.item_goods_demand_commit, list);
        mListener = listener;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ImageUploadGroup view = helper.getView(R.id.gdc_upload_group);
        view.setChangedListener(urls -> {
            GoodsDemandItem item = getItem(helper.getAdapterPosition() - getHeaderLayoutCount());
            if (item != null) item.setDemandUrl(TextUtils.join(",", urls));
        });
        EditText editText = helper.getView(R.id.gdc_explain_edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView countText = (TextView) editText.getTag();
                countText.setText(String.valueOf(50 - s.length()));
                GoodsDemandItem item = getItem(helper.getAdapterPosition() - getHeaderLayoutCount());
                if (item != null) {
                    item.setDemandContent(s.toString());
                    mListener.onChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // no-op
            }
        });
        helper.addOnClickListener(R.id.gdc_type)
                .addOnClickListener(R.id.gdc_upload_group);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDemandItem item) {
        ((EditText) helper.setText(R.id.gdc_type, GoodsDemandHelper.getType(item.getDemandType()))
                .setTag(R.id.gdc_explain_edit, helper.getView(R.id.gdc_edit_count))
                .setText(R.id.gdc_explain_edit, item.getDemandContent())
                .getView(R.id.gdc_explain_edit))
                .setSelection(TextUtils.isEmpty(item.getDemandContent()) ? 0 : item.getDemandContent().length());
        ((ImageUploadGroup) helper.getView(R.id.gdc_upload_group))
                .showImages(item.getDemandUrl() == null ? null : item.getDemandUrl().split(","));
    }
}
