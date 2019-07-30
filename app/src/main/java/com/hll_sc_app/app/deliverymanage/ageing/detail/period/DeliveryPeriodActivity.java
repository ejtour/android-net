package com.hll_sc_app.app.deliverymanage.ageing.detail.period;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择配送时段
 *
 * @author zhuyingsong
 * @date 2019/7/29
 */
@Route(path = RouterConfig.DELIVERY_AGEING_DETAIL_PERIOD, extras = Constant.LOGIN_EXTRA)
public class DeliveryPeriodActivity extends BaseLoadActivity implements DeliveryPeriodContract.IDeliveryPeriodView {
    @Autowired(name = "object0")
    String billUpDateTime;
    @Autowired(name = "object1")
    String flg;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private EmptyView mEmptyView;
    private PeriodListAdapter mAdapter;
    private DeliveryPeriodPresenter mPresenter;
    private DeliveryPeriodSelectWindow mWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_period);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryPeriodPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.queryDeliveryPeriodList();
    }

    private void initView() {
        mEmptyView = EmptyView.newBuilder(this)
            .setTipsTitle("您还没有设置配送时段哦").setTips("点击右上角新增添加").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(5)));
        mAdapter = new PeriodListAdapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DeliveryPeriodBean bean = mAdapter.getItem(position);
                if (bean == null) {
                    return;
                }
                int id = view.getId();
                if (id == R.id.txt_del) {
                    mPresenter.editDeliveryPeriod(bean, "2");
                } else if (id == R.id.content) {
                    EventBus.getDefault().post(bean);
                    finish();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    @OnClick({R.id.img_close, R.id.txt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add:
                showPeriodWindow();
                break;
            default:
                break;
        }
    }

    private void showPeriodWindow() {
        if (mWindow == null) {
            mWindow = new DeliveryPeriodSelectWindow(this);
            mWindow.setSelectListener((start, end) -> {
                if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
                    return;
                }
                DeliveryPeriodBean bean = new DeliveryPeriodBean();
                bean.setArrivalStartTime(start);
                bean.setArrivalEndTime(end);
                mPresenter.editDeliveryPeriod(bean, "0");
            });
        }
        mWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        SwipeItemLayout.closeAllItems(mRecyclerView);
    }

    @Override
    public void showList(List<DeliveryPeriodBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }

    @Override
    public String getBillUpDateTime() {
        return billUpDateTime;
    }

    @Override
    public String getFlag() {
        return flg;
    }

    class PeriodListAdapter extends BaseQuickAdapter<DeliveryPeriodBean, BaseViewHolder> {

        PeriodListAdapter() {
            super(R.layout.item_delivery_period);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_del).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryPeriodBean item) {
            helper.setText(R.id.txt_title, "配送时间" + (helper.getLayoutPosition() + 1))
                .setText(R.id.txt_value, item.getArrivalStartTime() + "-" + item.getArrivalEndTime());
        }
    }
}
