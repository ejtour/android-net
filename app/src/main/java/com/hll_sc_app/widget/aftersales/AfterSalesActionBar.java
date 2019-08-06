package com.hll_sc_app.widget.aftersales;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AfterSalesActionBar extends ConstraintLayout {
    /**
     * 审核员驳回
     */
    @BindView(R.id.after_sales_actions_reject)
    TextView mActionsReject;
    /**
     * 客服审核
     */
    @BindView(R.id.after_sales_actions_customer_service)
    TextView mActionsCustomerService;
    /**
     * 司机取消
     */
    @BindView(R.id.after_sales_actions_driver_cancel)
    TextView mActionDriverCancel;
    /**
     * 司机提货
     */
    @BindView(R.id.after_sales_actions_driver)
    TextView mActionDriver;
    /**
     * 仓库收货
     */
    @BindView(R.id.after_sales_actions_warehouse)
    TextView mActionWarehouse;
    /**
     * 财务审核
     */
    @BindView(R.id.after_sales_actions_finance)
    TextView mActionsFinance;

    /**
     * 生成投诉单
     */
    @BindView(R.id.after_sales_actions_complain)
    TextView mActionComplain;

    @BindViews({R.id.after_sales_actions_reject, R.id.after_sales_actions_customer_service,
            R.id.after_sales_actions_driver_cancel, R.id.after_sales_actions_driver, R.id.after_sales_actions_warehouse,
            R.id.after_sales_actions_finance, R.id.after_sales_actions_complain})
    List<View> mButtons;

    private boolean isItem;

    public AfterSalesActionBar(Context context) {
        this(context, null);
    }

    public AfterSalesActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AfterSalesActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 加载布局
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AfterSalesActionBar);
        isItem = typedArray.getBoolean(R.styleable.AfterSalesActionBar_sab_item, false);
        typedArray.recycle();
        ViewGroup rootView = (ViewGroup) View.inflate(context, !isItem ? R.layout.view_after_sales_action : R.layout.item_after_sales_action, this);
        ButterKnife.bind(this, rootView);
    }

    public void setData(List<Integer> buttons) {
        setVisibility(VISIBLE);
        ButterKnife.apply(mButtons, (view, index) -> view.setVisibility(GONE));
        if (CommonUtils.isEmpty(buttons)) { // 如果按钮数组为 0
            setVisibility(GONE);
        }
        for (Integer button : buttons) {
            switch (button) {
                case 2: // 客服审核
                    mActionsCustomerService.setVisibility(VISIBLE);
                    break;
                case 3: // 审核驳回
                    mActionsReject.setVisibility(VISIBLE);
                    break;
                case 4: // 司机提货
                    mActionDriver.setVisibility(VISIBLE);
                    break;
                case 5: // 仓库收货
                    mActionWarehouse.setVisibility(VISIBLE);
                    break;
                case 6: // 财务审核
                    mActionsFinance.setVisibility(VISIBLE);
                    break;
                case 8: // 司机取消订单
                    mActionDriverCancel.setVisibility(VISIBLE);
                    break;
                case 9:// 生成投诉单
                    if (!isItem) { //在详情页中才显示 列表中不显示
                        mActionComplain.setVisibility(VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}