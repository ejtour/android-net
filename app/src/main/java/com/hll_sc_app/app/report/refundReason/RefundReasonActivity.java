package com.hll_sc_app.app.report.refundReason;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.mikephil.charting.charts.PieChart;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 退货原因统计
 *
 * @author zc
 */
@Route(path = RouterConfig.REFUND_REASON_STATICS)
public class RefundReasonActivity extends BaseLoadActivity implements IRefundReasonContract.IView {

    @BindView(R.id.txt_filter_deposit)
    TextView mTxtFilterDeposit;
    @BindView(R.id.txt_filter_date)
    TextView mTxtFilterDate;
    @BindView(R.id.chart_pie)
    PieChart mPie;
    @BindView(R.id.sync_scroll_title)
    SyncHorizontalScrollView mSyncScrollTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.sync_scroll_content)
    SyncHorizontalScrollView mSyncScrollContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_reason_statistics);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.img_close, R.id.txt_filter_deposit})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_close:
                break;
            case R.id.txt_filter_deposit:
                break;
            default:
                break;
        }
    }
}
