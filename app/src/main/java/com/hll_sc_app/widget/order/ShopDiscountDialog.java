package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.order.place.DiscountPlanBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/23
 */

public class ShopDiscountDialog extends BaseDialog {
    @BindView(R.id.dss_title)
    TextView mTitle;
    @BindView(R.id.dss_list_view)
    RecyclerView mListView;
    private DiscountAdapter mAdapter;

    public ShopDiscountDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_single_selection, null);
        ButterKnife.bind(this, view);
        mTitle.setText("选择优惠方式");
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mListView.getLayoutParams();
        params.leftMargin = params.rightMargin = 0;
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(getContext(), R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(33), 0, UIUtils.dip2px(10), 0);
        mListView.addItemDecoration(decor);
        mAdapter = new DiscountAdapter();
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = UIUtils.dip2px(280);
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.white);
        }
    }

    @OnClick(R.id.dss_close)
    public void onViewClicked() {
        dismiss();
    }

    public ShopDiscountDialog refreshList(List<DiscountPlanBean.DiscountBean> list) {
        mAdapter.setNewData(list);
        return this;
    }

    public ShopDiscountDialog select(DiscountPlanBean.DiscountBean bean) {
        mAdapter.select(bean);
        return this;
    }

    public ShopDiscountDialog setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
        return this;
    }

    private static class DiscountAdapter extends BaseQuickAdapter<DiscountPlanBean.DiscountBean, BaseViewHolder> {
        private DiscountPlanBean.DiscountBean mCur;

        DiscountAdapter() {
            super(R.layout.item_shop_discount);
        }

        private void select(DiscountPlanBean.DiscountBean bean) {
            mCur = bean;
            notifyDataSetChanged();
        }

        @Override
        protected void convert(BaseViewHolder helper, DiscountPlanBean.DiscountBean item) {
            int icon;
            switch (item.getRuleType()) {
                case 1:
                case 2:
                case 5:
                    icon = R.drawable.ic_discount_sub;
                    break;
                case 3:
                    icon = R.drawable.ic_discount_off;
                    break;
                case 4:
                case -1:
                    icon = R.drawable.ic_discount_product;
                    break;
                default:
                    icon = R.drawable.ic_discount_ban;
                    break;
            }
            helper.setText(R.id.isd_name, item.getRuleName())
                    .setImageResource(R.id.isd_icon, icon)
                    .setAlpha(R.id.isd_check, item == mCur ? 1 : 0)
                    .setText(R.id.isd_value, item.getDiscountValue() == 0 ? "" : String.format("预计优惠%s元", CommonUtils.formatMoney(item.getDiscountValue())))
                    .getView(R.id.isd_name).setSelected(item == mCur);
        }
    }
}
