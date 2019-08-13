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
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.refundtime.RefundTimeBean;
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
    /**
     * 当前等级 0-普通等级 1-vip等级
     */
    @Autowired(name = "object0", required = true)
    int level;
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
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = RefundTimePresent.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        status = 0;
        //vip界面隐藏vip按钮
        mVip.setVisibility(level == 1 ? View.GONE : View.VISIBLE);
        mTitleView.setText(level == 1 ? "VIP客户退货时效设置" : "退货时效设置");
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
    }

    private void showDeliveryPeriodWindow(RefundTimeBean timeBean, int position) {
        List<NameValue> values = new ArrayList<>();
        if (mDialog == null) {
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
        }
        mDialog.show();
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public RefundTimeAdapter getMAdapter() {
        return mAdapter;
    }

    @Override
    public void show(List<RefundTimeBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }

    @OnClick({R.id.img_close, R.id.txt_vip, R.id.txt_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_vip:
                //进入vip界面
                RouterUtil.goToActivity(RouterConfig.REFUND_TIME, 1);
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
