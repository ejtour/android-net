package com.hll_sc_app.app.order.transfer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.app.order.transfer.details.TransferDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class OrderTransferFragment extends BaseLazyFragment implements IOrderTransferContract.IOrderTransferView {
    private static final String ORDER_TYPE_KEY = "order_type";
    /**
     * 过滤头前导文本
     */
    @BindView(R.id.otf_label)
    TextView mLabel;
    /**
     * 过滤时间间隔
     */
    @BindView(R.id.otf_interval)
    TextView mInterval;
    /**
     * 过滤头
     */
    @BindView(R.id.otf_hint)
    ConstraintLayout mFilterHeader;
    @BindView(R.id.fot_list)
    RecyclerView mListView;
    @BindView(R.id.fot_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fot_transfer_text)
    TextView mTransferText;
    @BindView(R.id.fot_bottom_bar_stub)
    ViewStub mBottomBarStub;
    /**
     * 底部操作栏
     */
    private View mBottomBarRoot;
    /**
     * 总金额
     */
    private TextView mTotalAmountText;
    /**
     * 确认按钮
     */
    private TextView mConfirm;
    /**
     * 全选按钮
     */
    private ImageView mSelectAll;
    /**
     * 空布局
     */
    private EmptyView mEmptyView;
    private OrderTransferAdapter mAdapter;
    /**
     * 当前操作的订单
     */
    private TransferBean mCurResp;
    /**
     * 当前页面的订单状态
     */
    private OrderType mOrderType = OrderType.PENDING_TRANSFER;
    /**
     * 订单参数
     */
    private OrderParam mOrderParam;
    private IOrderTransferContract.IOrderTransferPresenter mPresenter;
    Unbinder unbinder;

    public static OrderTransferFragment newInstance(@NonNull OrderParam param) {
        OrderTransferFragment fragment = new OrderTransferFragment();
        fragment.mOrderParam = param;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = OrderTransferPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_order_transfer, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
        mAdapter = new OrderTransferAdapter();
        mListView.setAdapter(mAdapter);
        updatePendingTransferNum(0);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
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
            TransferBean item = mAdapter.getItem(position);
            if (item == null) {
                return;
            }
            item.setSelected(!item.isSelected());
            mAdapter.notifyItemChanged(position);
            updateBottomBarData();
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurResp = mAdapter.getItem(position);
            if (mCurResp == null) return;
            TransferDetailActivity.start(mCurResp.getId());
        });
    }

    /**
     * 更新底部工具栏数据
     */
    private void updateBottomBarData() {
        initBottomBar();
        List<TransferBean> data = mAdapter.getData();
        if (data.size() > 0) {
            mBottomBarRoot.setVisibility(View.VISIBLE);
            double totalAmount = 0;
            int num = 0;
            for (TransferBean resp : data) {
                if (resp.isSelected()) {
                    totalAmount = CommonUtils.addDouble(totalAmount, resp.getTotalPrice(), 0).doubleValue();
                    num++;
                }
            }
            mConfirm.setEnabled(num > 0);
            mTotalAmountText.setText(handleTotalAmount(totalAmount));
            mSelectAll.setSelected(num == mAdapter.getSelectableNum() && num > 0);
            String text = mConfirm.getText().toString();
            StringBuilder stringBuilder = new StringBuilder(text);
            stringBuilder.replace(text.indexOf("(") + 1, text.indexOf(")"), String.valueOf(num));
            mConfirm.setText(stringBuilder.toString());
        } else mBottomBarRoot.setVisibility(View.GONE);
    }

    /**
     * 处理总金额
     */
    private CharSequence handleTotalAmount(double totalAmount) {
        String source = "合计：¥" + CommonUtils.formatMoney(totalAmount);
        SpannableString spannableString = new SpannableString(source);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.color_ed5655)),
                source.indexOf("¥"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.6f),
                source.indexOf("¥") + 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(mOrderParam.getFormatCreateStart(Constants.SIGNED_YYYY_MM_DD))) {
            mFilterHeader.setVisibility(View.VISIBLE);
            mLabel.setText("当前按下单时间筛选");
            mInterval.setText(String.format("%s ~ %s", mOrderParam.getFormatCreateStart(Constants.SIGNED_YYYY_MM_DD),
                    mOrderParam.getFormatCreateEnd(Constants.SIGNED_YYYY_MM_DD)));
        } else if (!TextUtils.isEmpty(mOrderParam.getFormatExecuteStart(Constants.SIGNED_YYYY_MM_DD_HH))) {
            mFilterHeader.setVisibility(View.VISIBLE);
            mLabel.setText("当前按到货时间筛选");
            mInterval.setText(String.format("%s ~ %s", mOrderParam.getFormatExecuteStart(Constants.SIGNED_YYYY_MM_DD_HH),
                    mOrderParam.getFormatExecuteEnd(Constants.SIGNED_YYYY_MM_DD_HH)));
        } else if (!TextUtils.isEmpty(mOrderParam.getFormatSignStart(Constants.SIGNED_YYYY_MM_DD_HH))) {
            mFilterHeader.setVisibility(View.VISIBLE);
            mLabel.setText("当前按签收时间筛选");
            mInterval.setText(String.format("%s ~ %s", mOrderParam.getFormatSignStart(Constants.SIGNED_YYYY_MM_DD_HH),
                    mOrderParam.getFormatSignEnd(Constants.SIGNED_YYYY_MM_DD_HH)));
        } else {
            mFilterHeader.setVisibility(View.GONE);
        }
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        dispose();
        super.onDestroyView();
    }

    private void dispose() {
        mAdapter = null;
        mBottomBarRoot = null;
        mConfirm = null;
        mSelectAll = null;
        mTotalAmountText = null;
        mEmptyView = null;
        mTransferText = null;
        unbinder.unbind();
    }

    @Override
    public OrderParam getOrderParam() {
        return mOrderParam;
    }

    @Override
    public OrderType getOrderStatus() {
        return mOrderType;
    }

    @Override
    public void setListData(List<TransferBean> beans, boolean append) {
        mRefreshLayout.setEnableLoadMore(!CommonUtils.isEmpty(beans) && beans.size() == 20);
        if (append) {
            if (!CommonUtils.isEmpty(beans))
                mAdapter.addData(beans);
            return;
        }
        mAdapter.setNewData(beans);
        if (CommonUtils.isEmpty(beans))
            handleEmptyData();
        updateBottomBarData();
    }

    /**
     * 更新待转单数目
     */
    public void updatePendingTransferNum(int pendingTransferNum) {
        String size = String.valueOf(pendingTransferNum);
        String source = "当前有 " + pendingTransferNum + " 笔待下单的订单";
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
                source.indexOf(size), source.indexOf(size) + size.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTransferText.setText(ss);
    }

    private void handleEmptyData() {
        // 配置空视图
        initEmptyView();
        mEmptyView.reset();
        mEmptyView.setTips("你还没有" + mOrderType.getLabel() + "的订单噢");
    }

    /**
     * 初始化底部工具栏
     */
    private void initBottomBar() {
        if (mBottomBarRoot == null) {
            mBottomBarRoot = mBottomBarStub.inflate();
            mTotalAmountText = mBottomBarRoot.findViewById(R.id.obb_sum);
            mSelectAll = mBottomBarRoot.findViewById(R.id.obb_select_all);
            mConfirm = mBottomBarRoot.findViewById(R.id.obb_confirm);
            mSelectAll.setOnClickListener(this::selectAll);
            mConfirm.setOnClickListener(this::confirm);
            mConfirm.setText(String.format("%s(0)", mOrderType.getButtonText()));
        }
    }

    @Override
    public void mallOrderSuccess() {
        if (getSubBillIds().size() == 1) {
            EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
        } else {
            cancelSelect();
        }
    }

    private void cancelSelect() {
        for (TransferBean bean : mAdapter.getData()) {
            if (bean.isSelected()) bean.setSelected(false);
        }
        mAdapter.notifyDataSetChanged();
        updateBottomBarData();
    }

    @OnClick(R.id.otf_cancel)
    public void cancelFilter() {
        mOrderParam.cancelTimeInterval();
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    /**
     * 全选
     */
    private void selectAll(View view) {
        for (TransferBean bean : mAdapter.getData()) {
            if (bean.isCanSelect()) bean.setSelected(!view.isSelected());
        }
        view.setSelected(!view.isSelected());
        mAdapter.notifyDataSetChanged();
        updateBottomBarData();
    }

    private void confirm(View view) {
        mPresenter.mallOrder(getSubBillIds());
    }

    private List<String> getSubBillIds() {
        List<String> billIds = new ArrayList<>();
        for (TransferBean bean : mAdapter.getData()) {
            if (bean.isSelected() || mBottomBarRoot == null) billIds.add(bean.getId());
        }
        return billIds;
    }

    @Subscribe
    public void handleOrderEvent(OrderEvent event) {
        switch (event.getMessage()) {
            case OrderEvent.SEARCH_WORDS:
                if (isFragmentVisible())
                    mOrderParam.setSearchBean((OrderSearchBean) event.getData());
            case OrderEvent.REFRESH_LIST:
                setForceLoad(true);
                lazyLoad();
                break;
            case OrderEvent.UPDATE_TRANSFER_ITEM:
                TransferBean transferBean = (TransferBean) event.getData();
                if (mCurResp != null){
                    if (transferBean.getStatus() == 1 || transferBean.getStatus() == 3)
                        mAdapter.replaceData(mCurResp, transferBean);
                    else mAdapter.removeData(mCurResp);
                    updateBottomBarData();
                }
                break;
        }
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    /**
     * 初始化空布局
     */
    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
                setForceLoad(true);
                lazyLoad();
            }).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
