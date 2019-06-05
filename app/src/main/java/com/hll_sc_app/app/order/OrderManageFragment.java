package com.hll_sc_app.app.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */

public class OrderManageFragment extends BaseLazyFragment implements IOrderManageContract.IOrderManageView {
    private static final String STATUS_KEY = "status";
    @BindView(R.id.otf_label)
    TextView mLabel;
    @BindView(R.id.otf_interval)
    TextView mInterval;
    @BindView(R.id.otf_hint)
    ConstraintLayout mFilterHeader;
    @BindView(R.id.fol_list)
    RecyclerView mListView;
    @BindView(R.id.fol_refresh)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.obb_bottom_bar)
    ViewGroup mBottomBar;
    @BindView(R.id.obb_sum)
    TextView mTotalAmountText;
    @BindView(R.id.obb_confirm)
    TextView mConfirm;
    @BindView(R.id.obb_select_all)
    ImageView mSelectAll;
    private OrderManageAdapter mAdapter;
    private OrderResp mCurResp;
    private int mBillStatus;
    private String mDeliverType;
    private OrderParam mOrderParam;
    private IOrderManageContract.IOrderManagePresenter mPresenter;
    private double mTotalAmount;
    private int mSelectNum;

    public static OrderManageFragment newInstance(int billStatus, @NonNull OrderParam param) {
        Bundle args = new Bundle();
        args.putInt(STATUS_KEY, billStatus);
        OrderManageFragment fragment = new OrderManageFragment();
        fragment.setArguments(args);
        fragment.mOrderParam = param;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBillStatus = arguments.getInt(STATUS_KEY);
        }
        mPresenter = OrderManagePresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_order_manage, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
        mAdapter = new OrderManageAdapter();
        mListView.setAdapter(mAdapter);
    }

    private void setListener() {
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
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            OrderResp item = mAdapter.getItem(position);
            if (item == null) {
                return;
            }
            if (item.isSelected()) {
                mTotalAmount = CommonUtils.subDouble(mTotalAmount, item.getTotalAmount()).doubleValue();
                mSelectNum--;
            } else {
                mTotalAmount = CommonUtils.addDouble(mTotalAmount, item.getTotalAmount(), 0).doubleValue();
                mSelectNum++;
            }
            updateTotalAmount();
            item.setSelected(!item.isSelected());
            mAdapter.notifyItemChanged(position);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurResp = mAdapter.getItem(position);
        });
    }

    private void updateTotalAmount() {
        mConfirm.setEnabled(mSelectNum > 0);
        mTotalAmountText.setText(handleTotalAmount());
        mSelectAll.setSelected(mSelectNum == mAdapter.getSelectableNum());
        String text = mConfirm.getText().toString();
        StringBuilder stringBuilder = new StringBuilder(text);
        stringBuilder.replace(text.indexOf("(") + 1, text.indexOf(")"), String.valueOf(mSelectNum));
        mConfirm.setText(stringBuilder.toString());
    }

    private CharSequence handleTotalAmount() {
        String source = "合计：¥" + CommonUtils.formatMoney(mTotalAmount);
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_ed5655)),
                source.indexOf("¥"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.6f),
                source.indexOf("¥") + 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(mOrderParam.getFormatCreateStart(Constants.FORMAT_YYYY_MM_DD_DASH))) {
            mFilterHeader.setVisibility(View.VISIBLE);
            mLabel.setText("当前按下单时间筛选");
            mInterval.setText(mOrderParam.getFormatCreateStart(Constants.FORMAT_YYYY_MM_DD_DASH) +
                    " ~ " + mOrderParam.getFormatCreateEnd(Constants.FORMAT_YYYY_MM_DD_DASH));
        } else if (!TextUtils.isEmpty(mOrderParam.getFormatExecuteStart(Constants.FORMAT_YYYY_MM_DD_DASH))) {
            mFilterHeader.setVisibility(View.VISIBLE);
            mLabel.setText("当前按到货时间筛选");
            mInterval.setText(mOrderParam.getFormatExecuteStart(Constants.FORMAT_YYYY_MM_DD_HH_DASH) +
                    " ~ " + mOrderParam.getFormatExecuteEnd(Constants.FORMAT_YYYY_MM_DD_HH_DASH));
        } else {
            mFilterHeader.setVisibility(View.GONE);
        }
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        mAdapter = null;
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public OrderParam getOrderParam() {
        return mOrderParam;
    }

    @Override
    public int getOrderStatus() {
        return mBillStatus;
    }

    @Override
    public String getDeliverType() {
        return mDeliverType;
    }

    @Override
    public void refreshListData(List<OrderResp> resps) {
        mAdapter.setNewData(resps);
        if (CommonUtils.isEmpty(resps)) {
            mBottomBar.setVisibility(View.GONE);
            if (mAdapter.getEmptyView() != null) {
                // TODO: 2019/6/5 添加空布局
            }
        } else if (mBottomBar.getVisibility() == View.GONE) {
            StringBuilder buttonText = new StringBuilder();
            switch (mBillStatus) {
                case 0:
                    buttonText.append("商城下单");
                    break;
                case 1:
                    buttonText.append("立即接单");
                    break;
                case 2:
                    buttonText.append("确认发货");
                    break;
            }
            if (!buttonText.toString().isEmpty()) {
                buttonText.append("(0)");
                mBottomBar.setVisibility(View.VISIBLE);
                mConfirm.setText(buttonText.toString());
                mAdapter.setCanCheck(true);
                updateTotalAmount();
            }
        }
    }

    @Override
    public void appendListData(List<OrderResp> resps) {
        mAdapter.addData(resps);
    }

    @OnClick(R.id.otf_cancel)
    public void cancelFilter() {
        mOrderParam.cancelTimeInterval();
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    @OnClick(R.id.obb_select_all)
    public void selectAll(View view) {
        double total = 0;
        int num = 0;
        for (OrderResp resp : mAdapter.getData()) {
            if (resp.isCanSelect()) {
                resp.setSelected(!view.isSelected());
                if (!view.isSelected()) {
                    num++;
                    total = CommonUtils.addDouble(total, resp.getTotalAmount(), 0).doubleValue();
                }
            }
        }
        view.setSelected(!view.isSelected());
        mAdapter.notifyDataSetChanged();
        mTotalAmount = total;
        mSelectNum = num;
        updateTotalAmount();
    }

    @OnClick(R.id.obb_confirm)
    public void confirm(View view) {
        // TODO: 2019/6/5 confirm
    }

    @Subscribe
    public void handleOrderEvent(OrderEvent event) {
        switch (event.getMessage()) {
            case OrderEvent.REFRESH_LIST:
            case OrderEvent.SEARCH_WORDS:
                setForceLoad(true);
                lazyLoad();
                break;
            case OrderEvent.UPDATE_ITEM:
                if (!isFragmentVisible()) {
                    setForceLoad(true);
                } else {
                    replaceItem((OrderResp) event.getData());
                }
                break;
        }
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void replaceItem(OrderResp resp) {
        if (resp.getSubBillStatus() == mBillStatus) {
            mAdapter.replaceData(mCurResp, resp);
        } else {
            mAdapter.removeData(resp);
        }
    }
}
