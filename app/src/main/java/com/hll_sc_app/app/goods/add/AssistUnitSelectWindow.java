package com.hll_sc_app.app.goods.add;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择辅助单位
 *
 * @author 朱英松
 * @date 2019/6/21
 */
public class AssistUnitSelectWindow extends BaseShadowPopupWindow {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SpecAdapter mAdapter;
    private ItemConfirmListener mListener;

    AssistUnitSelectWindow(Activity context) {
        super(context);
        View view = View.inflate(context, R.layout.window_assist_unit_select, null);
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(UIUtils.dip2px(420));
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xFFEEEEEE));
        initView(context);
    }

    private void initView(Activity context) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new SimpleDecoration(0xFFFFFFFF, UIUtils.dip2px(10)));
        mAdapter = new SpecAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SpecsBean bean = (SpecsBean) adapter.getItem(position);
            if (bean != null) {
                bean.setSelect(!bean.isSelect());
                adapter.notifyItemChanged(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    public void refreshList(List<SpecsBean> list) {
        List<SpecsBean> listFilter = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (SpecsBean specsBean : list) {
                // 过滤掉标准规格
                if (!TextUtils.equals(specsBean.getStandardUnitStatus(), SpecsBean.STANDARD_UNIT)) {
                    specsBean.setSelect(TextUtils.equals(specsBean.getAssistUnitStatus(), "1"));
                    listFilter.add(specsBean);
                }
            }
        }
        mAdapter.setNewData(listFilter);
    }

    void setListener(ItemConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.txt_confirm && mListener != null) {
            mListener.confirm(getSelectList());
        }
        dismiss();
    }

    private List<SpecsBean> getSelectList() {
        List<SpecsBean> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(mAdapter.getData())) {
            List<SpecsBean> specsBeanList = mAdapter.getData();
            for (SpecsBean specsBean : specsBeanList) {
                if (specsBean.isSelect()) {
                    list.add(specsBean);
                }
            }
        }
        return list;
    }

    interface ItemConfirmListener {
        /**
         * 确定
         *
         * @param beans 规格
         */
        void confirm(List<SpecsBean> beans);
    }

    public static class SpecAdapter extends BaseQuickAdapter<SpecsBean, BaseViewHolder> {

        SpecAdapter() {
            super(R.layout.item_window_assist_unit);
        }

        @Override
        protected void convert(BaseViewHolder helper, SpecsBean item) {
            helper.setText(R.id.txt_specContent, "规格内容：" + item.getSpecContent())
                    .setText(R.id.txt_ration, String.format("转换率：%s %s = %s %s",
                            item.getCurrentRationCount(), item.getSaleUnitName(),
                            item.getStandardRationCount(), item.getStandardUnitName()))
                .setText(R.id.txt_saleUnitName, "售卖单位：" + item.getSaleUnitName())
                .setText(R.id.txt_productPrice, "单价：" + item.getProductPrice());
            if (item.isSelect()) {
                helper.setGone(R.id.img_select, true);
                helper.getView(R.id.txt_specContent).setSelected(true);
                helper.getView(R.id.txt_ration).setSelected(true);
                helper.getView(R.id.txt_saleUnitName).setSelected(true);
                helper.getView(R.id.txt_productPrice).setSelected(true);
            } else {
                helper.setGone(R.id.img_select, false);
                helper.getView(R.id.txt_specContent).setSelected(false);
                helper.getView(R.id.txt_ration).setSelected(false);
                helper.getView(R.id.txt_saleUnitName).setSelected(false);
                helper.getView(R.id.txt_productPrice).setSelected(false);
            }
        }
    }
}
