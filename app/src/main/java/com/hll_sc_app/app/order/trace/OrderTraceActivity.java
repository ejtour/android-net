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
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/7
 */
@Route(path = RouterConfig.ORDER_TRACE)
public class OrderTraceActivity extends BaseLoadActivity {

    public static void start(OrderResp resp) {
        RouterUtil.goToActivity(RouterConfig.ORDER_TRACE, resp);
    }

    @BindView(R.id.aot_title)
    TextView mTitle;
    @BindView(R.id.aot_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    OrderResp mOrderResp;

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
        mTitle.setText("仓库处理中");
        List<OrderTraceBean> list = new ArrayList<>();
        list.add(new OrderTraceBean("【收货地址】" + mOrderResp.getReceiverAddress(), null, null));
        mListView.setAdapter(new OrderTraceAdapter(list));
    }

    @OnClick({R.id.aot_close, R.id.aot_complaint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aot_close:
                finish();
                break;
            case R.id.aot_complaint:
                showToast("投诉待添加");
                break;
        }
    }
}
