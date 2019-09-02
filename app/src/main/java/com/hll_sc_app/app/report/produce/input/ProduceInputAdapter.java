package com.hll_sc_app.app.report.produce.input;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IChangeListener;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceInputAdapter extends BaseQuickAdapter<ProduceDetailBean, BaseViewHolder> {
    private final IChangeListener mListener;
    ProduceInputAdapter(IChangeListener listener) {
        super(R.layout.item_report_produce_input);
        mListener = listener;
        addData(new ProduceDetailBean());
    }

    private int mOldWidth;

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.rpi_del)
                .addOnClickListener(R.id.rpi_company_name);
        ((EditText) helper.getView(R.id.rpi_money)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ProduceDetailBean item = getItem(helper.getAdapterPosition() - getHeaderLayoutCount());
                if (item == null) return;
                Utils.processMoney(s, true);
                item.setTotalCost(CommonUtils.getDouble(s.toString()));
                mListener.onChanged();
            }
        });
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProduceDetailBean item) {
        SwipeItemLayout itemView = (SwipeItemLayout) helper.itemView;
        View del = ((ViewGroup) itemView.getChildAt(1)).getChildAt(0);
        ViewGroup.LayoutParams params = del.getLayoutParams();
        if (mOldWidth == 0) mOldWidth = params.width;
        if (mData.size() == 1 && params.width != 0) {
            params.width = 0;
            del.setLayoutParams(params);
        } else if (mData.size() != 1 && params.width != mOldWidth) {
            params.width = mOldWidth;
            del.setLayoutParams(params);
        }
        helper.setText(R.id.rpi_company_name, item.getCoopGroupName())
                .setText(R.id.rpi_money, item.getTotalCost() == 0 ? "0.00" : CommonUtils.formatNumber(item.getTotalCost()));
    }

    @Override
    public void addData(@NonNull ProduceDetailBean data) {
        if (mData.size() == 1) {
            mData.add(data);
            notifyDataSetChanged();
        } else super.addData(data);
    }

    @Override
    public void remove(int position) {
        if (mData.size() == 2) {
            mData.remove(position);
            notifyDataSetChanged();
        } else super.remove(position);
    }

    private int getItemPosition(ProduceDetailBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    public void replace(ProduceDetailBean oldData, ProduceDetailBean newData) {
        if (oldData == null || newData == null) {
            return;
        }
        setData(getItemPosition(oldData), newData);
    }
}
