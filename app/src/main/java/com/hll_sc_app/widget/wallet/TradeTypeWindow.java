package com.hll_sc_app.widget.wallet;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.GridSimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public class TradeTypeWindow extends BasePopupWindow {
    @BindView(R.id.wtt_list_view)
    RecyclerView mListView;
    private NameValue mCurNameValue;
    private ITradeTypeCallback mCallback;

    public interface ITradeTypeCallback {
        void onSelectTradeType(String value);
    }

    public TradeTypeWindow(Activity activity, ITradeTypeCallback callback) {
        super(activity);
        View rootView = View.inflate(activity, R.layout.window_wallet_trade_type, null);
        ButterKnife.bind(this, rootView);
        rootView.setOnClickListener(v -> dismiss());
        setContentView(rootView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xbb000000);
        setBackgroundDrawable(dw);
        initView();
        mCallback = callback;
    }

    private void initView() {
        mListView.addItemDecoration(new GridSimpleDecoration());
        List<NameValue> list = new ArrayList<>();
        list.add(new NameValue("入账", "118"));
        list.add(new NameValue("退款", "201"));
        list.add(new NameValue("提现", "219"));
        list.add(new NameValue("余额支付扣减", "204"));
        list.add(new NameValue("调增", "199"));
        list.add(new NameValue("调减", "299"));
        list.add(new NameValue("充值", "102"));
        list.add(new NameValue("余额支付退款", "302"));
        list.add(new NameValue("订货", "110"));
        TradeTypeAdapter adapter = new TradeTypeAdapter(list);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            mCurNameValue = (NameValue) adapter1.getItem(position);
            adapter1.notifyDataSetChanged();
        });
        mListView.setAdapter(adapter);
    }

    @OnClick({R.id.wtt_reset, R.id.wtt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wtt_reset:
                mCurNameValue = null;
                mListView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.wtt_confirm:
                dismiss();
                mCallback.onSelectTradeType(mCurNameValue == null ? null : mCurNameValue.getValue());
                break;
        }
    }

    private class TradeTypeAdapter extends BaseQuickAdapter<NameValue, BaseViewHolder> {

        TradeTypeAdapter(@Nullable List<NameValue> data) {
            super(R.layout.item_wallet_trade_type, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NameValue item) {
            ((TextView) helper.itemView).setText(item.getName());
            helper.itemView.setSelected(item == mCurNameValue);
        }
    }
}
