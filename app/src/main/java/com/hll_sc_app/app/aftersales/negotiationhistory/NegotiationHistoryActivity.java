package com.hll_sc_app.app.aftersales.negotiationhistory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.aftersales.NegotiationHistoryResp;
import com.hll_sc_app.widget.SimpleDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 售后协商历史
 */
@Route(path = RouterConfig.AFTER_SALES_NEGOTIATION_HISTORY)
public class NegotiationHistoryActivity extends BaseLoadActivity implements INegotiationHistoryContract.INegotiationHistoryView {
    
    public static void start(AfterSalesBean bean) {
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_NEGOTIATION_HISTORY, bean);
    }

    @BindView(R.id.anh_list)
    RecyclerView anhList;
    @Autowired(name = "parcelable", required = true)
    AfterSalesBean mResp;
    private NegotiationHistoryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_after_sales_negotiation_history);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        // 分割线
        anhList.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mAdapter = new NegotiationHistoryAdapter(null);
        anhList.setAdapter(mAdapter);
    }

    private void initData() {
        INegotiationHistoryContract.INegotiationHistoryPresenter iNegotiationHistoryPresenter = NegotiationHistoryPresenter.newInstance();
        iNegotiationHistoryPresenter.register(this);
        iNegotiationHistoryPresenter.getHistoryList(mResp.getId(), mResp.getSubBillID());
    }

    @Override
    public void showHistoryList(NegotiationHistoryResp result) {
        mAdapter.setNewData(result.getList());
    }
}
