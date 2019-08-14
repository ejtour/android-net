package com.hll_sc_app.app.marketingsetting.product.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.selectproduct.ProductSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.DateTimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 商品促销列表
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST_ADD)
public class ProductMarketingAddActivity extends BaseLoadActivity implements IProductMarketingAddContract.IView {

    private Unbinder unbinder;

    private DateTimePickerDialog.Builder mDateTimeDialogBuilder;

    /**
     * 选择的时间
     */
    private long startTime = Calendar.getInstance().getTimeInMillis();
    private long endTime = startTime;

    /**
     * 选择的商品
     *
     * @param savedInstanceState
     */
    private ArrayList<SkuGoodsBean> mSelectedGoods;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_product_add);
        unbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }


    @OnClick({R.id.txt_time_start_select, R.id.txt_time_end_select, R.id.txt_add_product})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_time_start_select:
                initDateTimeBuilder();
                mDateTimeDialogBuilder
                        .setSelectTime(startTime)
                        .setCallback(new DateTimePickerDialog.SelectCallback() {
                            @Override
                            public void select(Date time) {
                                startTime = time.getTime();
                            }
                        })
                        .setTitle("活动开始时间")
                        .create().show();
                break;
            case R.id.txt_time_end_select:
                initDateTimeBuilder();
                mDateTimeDialogBuilder
                        .setSelectTime(endTime)
                        .setCallback(new DateTimePickerDialog.SelectCallback() {
                            @Override
                            public void select(Date time) {
                                endTime = time.getTime();
                            }
                        })
                        .setTitle("活动结束时间")
                        .create().show();
                break;
            case R.id.txt_add_product:
                ProductSelectActivity.start(ProductMarketingAddActivity.class.getSimpleName(), "选择活动商品", mSelectedGoods);
                break;
            default:
                break;
        }
    }


    private void initDateTimeBuilder() {
        if (mDateTimeDialogBuilder == null) {
            mDateTimeDialogBuilder = DateTimePickerDialog.newBuilder(this)
                    .setBeginTime(CalendarUtils.parse("201701010000", Constants.UNSIGNED_YYYY_MM_DD_HH_MM).getTime())
                    .setEndTime(CalendarUtils.parse("203012312359", Constants.UNSIGNED_YYYY_MM_DD_HH_MM).getTime());
        }
    }
}
