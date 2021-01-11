package com.hll_sc_app.app.refundtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.refundtime.RefundTimeBean;
import com.hll_sc_app.bean.refundtime.RefundTimeResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REFUND_TIME, extras = Constant.LOGIN_EXTRA)
public class RefundTimeActivity extends BaseLoadActivity implements IRefundTimeContract.IView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_vip)
    TextView mVip;
    @BindView(R.id.txt_set)
    TextView mTxtSet;
    @BindView(R.id.txt_title)
    TextView mTitleView;
    private List<NameValue> values;
    @Autowired(name = "parcelable")
    public RefundTimeResp mResp;
    private RefundTimePresent mPresenter;
    private EmptyView mEmptyView;
    private RefundTimeAdapter mAdapter;
    //当前状态 0-查看模式 1-编辑模式
    private Integer status;
    private SingleSelectionDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_time);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = RefundTimePresent.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
    }

    private void initView() {
        if (mResp == null) {
            mResp = new RefundTimeResp();
            mResp.setLevel(0);
        }
        status = 0;
        //vip界面隐藏vip按钮
        mVip.setVisibility(mResp.getLevel() == 1 ? View.GONE : View.VISIBLE);
        mTitleView.setText(mResp.getLevel() == 1 ? "VIP客户退货时效设置" : "退货时效设置");
        mEmptyView = EmptyView.newBuilder(this).setTips("没有配送时效数据").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new RefundTimeAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            RefundTimeBean bean = mAdapter.getItem(position);
            if (bean != null) {
                if (status != null && status == 1) {
                    showDeliveryPeriodWindow(bean, position);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        if (mResp.getRecords() == null || mResp.getRecords().size() == 0) {
            mPresenter.listRefundTime(mResp.getLevel());
        } else {
            mAdapter.setNewData(mResp.getRecords());
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    private void showDeliveryPeriodWindow(RefundTimeBean timeBean, int position) {
        values = new ArrayList<>();
        values.add(new NameValue("不可退货", "0"));
        values.add(new NameValue("1天", "1"));
        values.add(new NameValue("2天", "2"));
        values.add(new NameValue("3天", "3"));
        values.add(new NameValue("4天", "4"));
        values.add(new NameValue("5天", "5"));
        values.add(new NameValue("6天", "6"));
        values.add(new NameValue("7天", "7"));
        values.add(new NameValue("15天", "15"));
        values.add(new NameValue("30天", "30"));

        mDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .setTitleText("选择时效")
                .setOnSelectListener(bean -> {
                    timeBean.setNum(CommonUtils.getInt(bean.getValue()));
                    mAdapter.notifyItemChanged(position);
                })
                .refreshList(values)
                .create();
        NameValue nameValue = null;
        RefundTimeBean refundTimeBean = mAdapter.getItem(position);
        if (refundTimeBean.getNum() != null) {
            for (NameValue value : values) {
                if (refundTimeBean.getNum().toString().equalsIgnoreCase(value.getValue())) {
                    nameValue = value;
                    break;
                }
            }
        }
        if (nameValue != null) {
            mDialog.selectItem(nameValue);
        }
        mDialog.show();
    }

    @Override
    public Integer getLevel() {
        return mResp.getLevel();
    }

    @Override
    public RefundTimeAdapter getMAdapter() {
        return mAdapter;
    }

    @Override
    public void show(RefundTimeResp resp) {
        mResp.setRecords(resp.getRecords());
        if (resp.getLevel() == 0) {
            mAdapter.setNewData(resp.getRecords());
            mAdapter.setEmptyView(mEmptyView);
        } else {
            RouterUtil.goToActivity(RouterConfig.REFUND_TIME, resp);
        }
    }

    private void goToVip() {
        mPresenter.listRefundTime(1);
    }

    @OnClick({R.id.img_close, R.id.txt_vip, R.id.txt_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_vip:
                //进入vip界面
                goToVip();
                break;
            case R.id.txt_set:
                if (status != null && status == 1) {
                    status = 0;
                    mTxtSet.setText("编辑");
                    mPresenter.setRefundTime();
                } else {
                    status = 1;
                    mTxtSet.setText("保存");
                }
                if (mAdapter != null) {
                    mAdapter.setStatus(status);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
