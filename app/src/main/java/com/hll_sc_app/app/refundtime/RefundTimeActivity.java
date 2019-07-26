package com.hll_sc_app.app.refundtime;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.REFUND_TIME, extras = Constant.LOGIN_EXTRA)
public class RefundTimeActivity  extends BaseLoadActivity implements IRefundTimeContract.IView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_recyclerView)
    LinearLayout mLinearLayoutVip;
    @BindView(R.id.rl_vip)
    RelativeLayout mVip;
    @BindView(R.id.txt_set)
    TextView mTxtSet;
    private RefundTimePresent mPresenter;
    private EmptyView mEmptyView;
    private RefundTimeAdapter mAdapter;
    private SingleSelectionDialog mDialog;
    //当前状态 0-查看模式 1-编辑模式
    private Integer status;
    //当前等级 0-普通等级 1-vip等级
    @Autowired(name = "object0", required = true)
    int level;

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
        if(level == 1) {
            //vip界面隐藏vip按钮
            mVip.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLinearLayoutVip.getLayoutParams();
            params.setMargins(0, 10, 0, 0);
        }
        mEmptyView = EmptyView.newBuilder(this).setTips("没有配送时效数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new RefundTimeAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            RefundTimeBean bean = mAdapter.getItem(position);
            if (bean != null) {
                if(status != null && status == 1) {
                    if (view.getId() == R.id.rl_refund_time_detail) {
                        showDeliveryPeriodWindow(view, position);
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void show(List<RefundTimeBean> list) {
        if(list == null || list.size() == 0) {

        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
    }

    @OnClick({R.id.img_close, R.id.rl_vip, R.id.txt_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.rl_vip:
                //进入vip界面
                RouterUtil.goToActivity(RouterConfig.REFUND_TIME, 1);
                break;
            case R.id.txt_set:
                changeStatus();
                break;
            default:
                break;
        }
    }

    private void changeStatus() {
        if(status != null && status == 0) {
            //切换至编辑模式
            Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_gray);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            for(int i = 0;;i++) {
                View view = mRecyclerView.getLayoutManager().findViewByPosition(i);
                if (view == null) {
                    break;
                }
                TextView textView = view.findViewById(R.id.txt_refund_time_value);
                textView.setCompoundDrawables(null, null, drawable, null);
            }
            status = 1;
            mTxtSet.setText("保存");
        } else {
            //切换至查看模式
            for(int i = 0;;i++) {
                View view = mRecyclerView.getLayoutManager().findViewByPosition(i);
                if (view == null) {
                    break;
                }
                TextView textView = view.findViewById(R.id.txt_refund_time_value);
                textView.setCompoundDrawables(null, null, null, null);
            }
            status = 0;
            mTxtSet.setText("编辑");
            //调用接口
            mPresenter.setRefundTime(mAdapter);
        }
    }

    private void showDeliveryPeriodWindow(View view, Integer position) {
        List<NameValue> values = new ArrayList<>();
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
        SingleSelectionDialog.newBuilder(this, (SingleSelectionDialog.WrapperName<NameValue>) bean
                -> bean.getName())
                .setTitleText("选择时效")
                .setOnSelectListener(bean -> {
                    TextView textView = view.findViewById(R.id.txt_refund_time_value);
                    textView.setText(bean.getName());
                    RefundTimeBean refundTimeBean = mAdapter.getItem(position);
                    refundTimeBean.setNum(Integer.parseInt(bean.getValue()));
                })
                .refreshList(values)
                .create().show();
    }
}
