package com.hll_sc_app.widget.order;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TriangleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/9
 */
public class AppendGoodsList extends ConstraintLayout {
    @BindView(R.id.agl_arrow)
    TriangleView mArrow;
    @BindView(R.id.agl_label)
    TextView mLabel;
    @BindView(R.id.agl_list)
    RecyclerView mListView;

    public AppendGoodsList(Context context) {
        this(context, null);
    }

    public AppendGoodsList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppendGoodsList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_append_goods_list, this);
        ButterKnife.bind(this, view);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AppendGoodsList);
        int imageWidth = array.getDimensionPixelSize(R.styleable.AppendGoodsList_goodsImageWidth, ViewUtils.dip2px(context, 60));
        String label = array.getString(R.styleable.AppendGoodsList_listLabel);
        label = TextUtils.isEmpty(label) ? "含押金商品" : label;
        array.recycle();
        initView(imageWidth, label);
    }


    public void setData(List<OrderDepositBean> list) {
        if (CommonUtils.isEmpty(list)) {
            mListView.setAdapter(null);
        } else {
            mListView.setAdapter(new Adapter(list));
        }
    }

    public void setBundleList(List<GoodsBean> list) {
        if (CommonUtils.isEmpty(list)) {
            mListView.setAdapter(null);
        } else {
            List<OrderDepositBean> beans = new ArrayList<>();
            for (GoodsBean bean : list) {
                OrderDepositBean depositBean = new OrderDepositBean();
                beans.add(depositBean);
                depositBean.setProductSpec(bean.getSpecContent());
                depositBean.setProductName(bean.getProductName());
                depositBean.setProductPrice(CommonUtils.getDouble(bean.getSpecPrice()));
                depositBean.setProductNum(CommonUtils.getDouble(bean.getSpecNum()));
                depositBean.setSaleUnitName(bean.getSaleUnitName());
            }
            mListView.setAdapter(new Adapter(beans));
        }
    }

    private void initView(float imageWidth, String label) {
        ((MarginLayoutParams) mArrow.getLayoutParams()).leftMargin = (int) ((imageWidth - ViewUtils.dip2px(getContext(), 8)) / 2);
        mLabel.setText(label);
        mListView.setOverScrollMode(OVER_SCROLL_NEVER);
        mListView.setNestedScrollingEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, ViewUtils.dip2px(getContext(), 5)));
    }

    static class Adapter extends BaseQuickAdapter<OrderDepositBean, BaseViewHolder> {

        Adapter(@Nullable List<OrderDepositBean> data) {
            super(R.layout.item_order_deposit, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderDepositBean item) {
            helper.setText(R.id.iod_name_spec, TextUtils.isEmpty(item.getProductSpec()) ?
                    item.getProductName() :
                    String.format("%s（%s）", item.getProductName(), item.getProductSpec()));
            helper.setText(R.id.iod_price, "¥" + CommonUtils.formatMoney(item.getProductPrice()));
            helper.setText(R.id.iod_num, CommonUtils.formatNum(item.getProductNum()));
            helper.setText(R.id.iod_unit, item.getSaleUnitName());
        }
    }
}
