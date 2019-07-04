package com.hll_sc_app.app.goods.invwarn;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 代仓商品库存预警-仓库选择
 * 第三方商品关联-来源选择
 *
 * @author 朱英松
 * @date 2019/7/2
 */
public class HouseSelectWindow extends BasePopupWindow {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SelectConfirmListener mListener;
    private List<HouseBean> mList;

    public HouseSelectWindow(Activity context, List<HouseBean> list) {
        super(context);
        this.mList = list;
        View view = View.inflate(context, R.layout.window_house_select, null);
        view.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(mActivity,
            R.color.base_color_divider), UIUtils.dip2px(1)));
        HouseListAdapter adapter = new HouseListAdapter(mList);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            HouseBean houseBean = (HouseBean) adapter1.getItem(position);
            if (houseBean != null) {
                clearSelectStatus();
                houseBean.setSelect(true);
                adapter1.notifyDataSetChanged();
                if (mListener != null) {
                    mListener.confirm(houseBean);
                }
                dismiss();
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void clearSelectStatus() {
        if (CommonUtils.isEmpty(mList)) {
            return;
        }
        for (HouseBean bean : mList) {
            bean.setSelect(false);
        }
    }

    public void setListener(SelectConfirmListener listener) {
        this.mListener = listener;
    }

    public interface SelectConfirmListener {
        /**
         * 确定
         *
         * @param houseBean bean
         */
        void confirm(HouseBean houseBean);
    }

    private static class HouseListAdapter extends BaseQuickAdapter<HouseBean, BaseViewHolder> {

        HouseListAdapter(@Nullable List<HouseBean> data) {
            super(R.layout.item_window_house, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HouseBean item) {
            TextView textView = helper.getView(R.id.txt_houseName);
            textView.setText(item.getHouseName());
            if (item.isSelect()) {
                helper.setVisible(R.id.img_select, true);
                textView.setSelected(true);
            } else {
                helper.setVisible(R.id.img_select, false);
                textView.setSelected(false);
            }

        }
    }
}
