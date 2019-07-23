package com.hll_sc_app.app.report.orderGoods.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsDetailParam;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

@Route(path = RouterConfig.REPORT_ORDER_GOODS_DETAIL)
public class OrderGoodsDetailActivity extends BaseLoadActivity {
    @BindView(R.id.ogd_shop_name)
    TextView mShopName;
    @BindView(R.id.ogd_group_name)
    TextView mGroupName;
    @BindView(R.id.ogd_time)
    TextView mTime;
    @BindView(R.id.ogd_product_num)
    TextView mProductNum;
    @BindView(R.id.ogd_inspection_num)
    TextView mInspectionNum;
    @BindView(R.id.ogd_amount)
    TextView mAmount;
    @Autowired(name = "parcelable")
    OrderGoodsDetailParam mParam;

    /**
     * @param bean      订货门店条目
     * @param startDate 起始日期
     * @param endDate   结束日期
     */
    public static void start(OrderGoodsBean bean, Date startDate, Date endDate) {
        OrderGoodsDetailParam param = new OrderGoodsDetailParam();
        param.setEndDate(endDate);
        param.setStartDate(startDate);
        param.setBean(bean);
        RouterUtil.goToActivity(RouterConfig.REPORT_ORDER_GOODS_DETAIL, param);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_order_goods_detail);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        OrderGoodsBean bean = mParam.getBean();
        mShopName.setText(bean.getShopName());
        mGroupName.setText(bean.getPurchaserName());
        mProductNum.setText(CommonUtils.formatNum(bean.getSkuNum()));
        mInspectionNum.setText(CommonUtils.formatNum(bean.getInspectionNum()));
        mAmount.setText(String.format("¥%s",CommonUtils.formatMoney(bean.getInspectionAmount())));
        mTime.setText(String.format("%s - %s",
                CalendarUtils.format(mParam.getStartDate(), Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(mParam.getEndDate(), Constants.SLASH_YYYY_MM_DD)));
    }
}
