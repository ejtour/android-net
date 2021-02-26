package com.hll_sc_app.app.report.ordergoods.detail;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsDetailParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

@Route(path = RouterConfig.REPORT_ORDER_GOODS_DETAIL)
public class OrderGoodsDetailActivity extends BaseLoadActivity implements IOrderGoodsDetailContract.IOrderGoodsDetailView {
    private static final int[] WIDTH_ARRAY = {100, 120, 80, 90, 90, 90, 90};
    @BindView(R.id.ogd_shop_name)
    TextView mShopName;
    @BindView(R.id.ogd_group_name)
    TextView mGroupName;
    @BindView(R.id.ogd_time)
    TextView mTime;
    @BindView(R.id.ogd_product_num)
    TextView mProductNum;
    @BindView(R.id.ogd_inspection_amount)
    TextView mInspectionAmount;
    @BindView(R.id.ogd_order_amount)
    TextView mOrderAmount;
    @BindView(R.id.ogd_delivery_amount)
    TextView mDeliveryAmount;
    @Autowired(name = "parcelable")
    OrderGoodsDetailParam mParam;
    @BindView(R.id.ogd_excel)
    ExcelLayout mExcel;
    private IOrderGoodsDetailContract.IOrderGoodsDetailPresenter mPresenter;

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
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = OrderGoodsDetailPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        OrderGoodsBean bean = mParam.getBean();
        mShopName.setText(bean.getShopName());
        mGroupName.setText(bean.getPurchaserName());
        mProductNum.setText(CommonUtils.formatNum(bean.getSkuNum()));
        mInspectionAmount.setText(String.format("¥%s", CommonUtils.formatMoney(bean.getInspectionAmount())));
        mOrderAmount.setText(String.format("¥%s", CommonUtils.formatMoney(bean.getOrderAmount())));
        mDeliveryAmount.setText(String.format("¥%s", CommonUtils.formatMoney(bean.getAdjustmentAmount())));
        mTime.setText(String.format("%s - %s", mParam.getFormatStartDate(Constants.SLASH_YYYY_MM_DD),
                mParam.getFormatEndDate(Constants.SLASH_YYYY_MM_DD)));
        mExcel.setHeaderView(generateHeader());
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("商品编码", "商品名称", "规格", "订货数量/单位", "订货金额(元)", "验货数量/单位", "验货金额(元)");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < 3; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL);
        }
        for (int i = 3; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void setList(List<OrderGoodsDetailBean> beans, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(beans) && beans.size() == 20);
        mExcel.setData(beans, append);
    }
}
