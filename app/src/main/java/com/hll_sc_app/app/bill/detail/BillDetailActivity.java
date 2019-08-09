package com.hll_sc_app.app.bill.detail;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillStatus;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.bill.BillDetailHeader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

@Route(path = RouterConfig.BILL_DETAIL)
public class BillDetailActivity extends BaseLoadActivity implements IBillDetailContract.IBillDetailView {
    public static final int REQ_CODE = 0x766;
    @BindView(R.id.abd_action_btn)
    TextView mActionBtn;
    @BindView(R.id.abd_list_view)
    RecyclerView mListView;
    @BindView(R.id.abd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.abd_action_group)
    Group mActionGroup;
    private BillDetailAdapter mAdapter;
    private BillDetailHeader mHeader;
    private IBillDetailContract.IBillDetailPresenter mPresenter;
    private boolean hasChanged;

    public static void start(Activity activity, BillBean bean) {
        RouterUtil.goToActivity(RouterConfig.BILL_DETAIL, activity, REQ_CODE, bean);
    }

    @Autowired(name = "parcelable", required = true)
    BillBean mCurBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_bill_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
        updateData();
    }

    @Override
    public void onBackPressed() {
        if (hasChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    private void initData() {
        mPresenter = BillDetailPresenter.newInstance(mCurBean.getId());
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mAdapter = new BillDetailAdapter();
        mHeader = new BillDetailHeader(this);
        mAdapter.setHeaderView(mHeader);
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(50), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
    }

    @OnClick(R.id.abd_action_btn)
    public void doAction() {
        mPresenter.doAction();
    }

    @Override
    public void updateData(BillBean bean) {
        mCurBean = bean;
        updateData();
    }

    @Override
    public void actionSuccess() {
        hasChanged = true;
        mPresenter.start();
    }

    private void updateData() {
        mHeader.setData(mCurBean);
        mAdapter.setNewData(mCurBean.getRecords());
        mActionGroup.setVisibility(mCurBean.getSettlementStatus() == BillStatus.NOT_SETTLE ? View.VISIBLE : View.GONE);
        ((ViewGroup) mActionGroup.getParent()).requestLayout();
    }
}
