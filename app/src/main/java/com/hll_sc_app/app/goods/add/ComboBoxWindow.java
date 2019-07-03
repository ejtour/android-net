package com.hll_sc_app.app.goods.add;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 下拉框
 *
 * @author 朱英松
 * @date 2019/6/25
 */
public class ComboBoxWindow extends BaseShadowPopupWindow {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    private String mSelectString;
    private ItemConfirmListener mListener;

    ComboBoxWindow(Activity context, ProductAttrBean bean) {
        super(context);
        View view = View.inflate(context, R.layout.window_combo_box, null);
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(UIUtils.dip2px(350));
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xFFEEEEEE));
        mSelectString = bean.getCurrAttrValue();
        initView(context, bean);
    }

    private void initView(Activity context, ProductAttrBean bean) {
        mTxtTitle.setText(bean.getKeyNote());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new SimpleDecoration(0xFFEEEEEE, UIUtils.dip2px(1)));
        ProductAttrAdapter attrAdapter = new ProductAttrAdapter(TextUtils.isEmpty(bean.getAttrValue()) ? null :
            Arrays.asList(bean.getAttrValue().split(",")));
        attrAdapter.setOnItemClickListener((adapter, view1, position) -> {
            mSelectString = (String) adapter.getItem(position);
            adapter.notifyDataSetChanged();
            if (!TextUtils.isEmpty(mSelectString)) {
                mListener.confirm(mSelectString);
                dismiss();
            }
        });
        mRecyclerView.setAdapter(attrAdapter);
    }

    void setListener(ItemConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.img_close})
    public void onViewClicked() {
        dismiss();
    }

    interface ItemConfirmListener {
        /**
         * 确定
         *
         * @param selectString string
         */
        void confirm(String selectString);
    }

    public class ProductAttrAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        ProductAttrAdapter(@Nullable List<String> data) {
            super(R.layout.item_window_combo_box, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView textView = helper.getView(R.id.txt_specContent);
            textView.setText(item);
            if (TextUtils.equals(item, mSelectString)) {
                textView.setSelected(true);
                helper.setGone(R.id.img_select, true);
            } else {
                textView.setSelected(false);
                helper.setGone(R.id.img_select, false);
            }
        }
    }
}
