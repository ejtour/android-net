package com.hll_sc_app.app.wallet.bank;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/2
 */

@Route(path = RouterConfig.WALLET_BANK_LIST)
public class BankListActivity extends BaseLoadActivity implements IBankListContract.IBankListView {
    public static final int REQ_CODE = 0x333;
    public static final String BANK_KEY = "bank_key";

    /**
     * @param bankNo 银行编号
     */
    public static void start(Activity context, String bankNo) {
        Object[] args = {bankNo};
        RouterUtil.goToActivity(RouterConfig.WALLET_BANK_LIST, context, REQ_CODE, args);
    }

    @BindView(R.id.srl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "object0")
    String mBankNo;
    private IBankListContract.IBankListPresenter mPresenter;
    private BankListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_refresh_list);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = BankListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("选择开户行");
        mAdapter = new BankListAdapter(mBankNo);
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            intent.putExtra(BANK_KEY, mAdapter.getItem(position));
            setResult(RESULT_OK, intent);
            finish();
        });
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setBankList(List<BankBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else mAdapter.setNewData(list);
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }
}
