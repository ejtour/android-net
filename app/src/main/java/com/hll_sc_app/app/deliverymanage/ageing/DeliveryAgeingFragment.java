package com.hll_sc_app.app.deliverymanage.ageing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.deliverymanage.ageing.detail.DeliveryAgeingDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 配送时效管理-配送时效
 *
 * @author 朱英松
 * @date 2019/7/29
 */
public class DeliveryAgeingFragment extends BaseLazyFragment implements DeliveryAgeingFragmentContract.IDeliveryAgeingBookView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private EmptyView mEmptyView;
    private AgeListAdapter mAdapter;
    private DeliveryAgeingFragmentContract.IDeliveryAgeingBookPresenter mPresenter;

    public static DeliveryAgeingFragment newInstance() {
        return new DeliveryAgeingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = DeliveryAgeingFragmentPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_delivery_ageing, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mEmptyView = EmptyView.newBuilder(requireActivity())
            .setTipsTitle("您还没有设置配送时效哦").setTips("点击右上角新增添加").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(),
            R.color.base_color_divider), UIUtils.dip2px(10)));
        mAdapter = new AgeListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DeliveryPeriodBean bean = (DeliveryPeriodBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (view.getId() == R.id.img_close) {
                mPresenter.delAgeing(bean.getDeliveryTimeID());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void showList(List<DeliveryPeriodBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }

    class AgeListAdapter extends BaseQuickAdapter<DeliveryPeriodBean, BaseViewHolder> {


        AgeListAdapter() {
            super(R.layout.item_delivery_ageing);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.img_close);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryPeriodBean item) {
            helper.setText(R.id.txt_ageing_title, "时效管理" + helper.getLayoutPosition())
                .setText(R.id.txt_billUpDateTime, item.getBillUpDateTime())
                .setText(R.id.txt_arrivalTime, getArrivalTime(item));
        }

        private String getArrivalTime(DeliveryPeriodBean item) {
            StringBuilder stringBuilder = new StringBuilder();
            String flag = DeliveryAgeingDetailActivity.getDayTimeFlag(item.getDayTimeFlag());
            if (!TextUtils.isEmpty(flag)) {
                stringBuilder.append(flag).append(",");
            }
            stringBuilder.append(item.getArrivalStartTime()).append("-").append(item.getArrivalEndTime());
            return stringBuilder.toString();
        }
    }
}