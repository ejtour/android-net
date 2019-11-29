package com.hll_sc_app.app.order.trace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.add.ComplainMangeAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.bean.order.trace.OrderTraceParam;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hll_sc_app.app.complainmanage.detail.ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/7
 */
@Route(path = RouterConfig.ORDER_TRACE)
public class OrderTraceActivity extends BaseLoadActivity {

    /**
     * @param address 收货地址
     * @param list    订单追踪列表
     */
    public static void start(String address, List<OrderTraceBean> list) {
        OrderTraceParam param = new OrderTraceParam(address, list);
        RouterUtil.goToActivity(RouterConfig.ORDER_TRACE, param);
    }

    @BindView(R.id.aot_title)
    TextView mTitle;
    @BindView(R.id.aot_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    OrderTraceParam mTraceParam;
    @BindView(R.id.aot_status_title)
    TextView mStatusTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_activity_bg));
        setContentView(R.layout.activity_order_trace);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle.setText(mTraceParam.getTitle());
        mStatusTitle.setText(mTraceParam.getDeliverTitle());
        mListView.setAdapter(new OrderTraceAdapter(mTraceParam.getList()));
    }

    @OnClick({R.id.aot_close, R.id.aot_complaint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aot_close:
                finish();
                break;
            case R.id.aot_complaint:
                ComplainMangeAddActivity.start(null, COMPLAIN_MANAGE);
                break;
        }
    }
}
