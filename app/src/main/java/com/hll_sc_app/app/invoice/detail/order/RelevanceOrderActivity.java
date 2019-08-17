package com.hll_sc_app.app.invoice.detail.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.select.order.SelectOrderAdapter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.invoice.InvoiceOrderBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */
@Route(path = RouterConfig.INVOICE_DETAIL_ORDER)
public class RelevanceOrderActivity extends BaseLoadActivity implements IRelevanceOrderContract.IRelevanceOrderView {
    /**
     * @param id     发票id
     * @param amount 开票总额
     */
    public static void start(String id, double amount) {
        RouterUtil.goToActivity(RouterConfig.INVOICE_DETAIL_ORDER, id, amount);
    }

    @BindView(R.id.iro_bottom_amount)
    TextView mBottomAmount;
    @BindView(R.id.iro_list_view)
    RecyclerView mListView;
    @BindView(R.id.iro_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private IRelevanceOrderContract.IRelevanceOrderPresenter mPresenter;
    @Autowired(name = "object0")
    String mID;
    @Autowired(name = "object1")
    double mAmount;
    private SelectOrderAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_relevance_order);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = RelevanceOrderPresenter.newInstance(mID);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new SelectOrderAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mBottomAmount.setText(processBottomAmount(mAmount));
    }

    @Override
    public void setListData(List<InvoiceOrderBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else mAdapter.setNewData(list);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    private SpannableString processBottomAmount(double amount) {
        String source = String.format("开票总额：¥%s", CommonUtils.formatMoney(amount));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)),
                source.indexOf("¥"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
