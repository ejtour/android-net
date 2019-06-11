package com.hll_sc_app.app.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
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
import com.hll_sc_app.app.order.deliver.OrderDeliverTypeAdapter;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
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
 * @since 2019/6/4
 */

public class OrderManageFragment extends BaseLazyFragment implements IOrderManageContract.IOrderManageView {
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
    @BindView(R.id.fol_list)
    RecyclerView mListView;
    @BindView(R.id.fol_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fol_bottom_bar_stub)
    ViewStub mBottomBarStub;
    @BindView(R.id.fol_deliver_type_stub)
    ViewStub mDeliverTypeStub;
    /**
     * 底部操作栏
     */
    private View mBottomBar;
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
    /**
     * 发货类型根布局
     */
    private View mDeliverTypeRoot;
    private OrderDeliverTypeAdapter mDeliverTypeAdapter;
    private OrderManageAdapter mAdapter;
    /**
     * 当前操作的订单
     */
    private OrderResp mCurResp;
    /**
     * 当前页面的订单状态
     */
    private OrderType mOrderType;
    /**
     * 分发类型
     */
    private String mDeliverType;
    /**
     * 订单参数
     */
    private OrderParam mOrderParam;
    private IOrderManageContract.IOrderManagePresenter mPresenter;
    /**
     * 已选总金额
     */
    private double mTotalAmount;
    /**
     * 已选数量
     */
    private int mSelectNum;
    Unbinder unbinder;

    public static OrderManageFragment newInstance(OrderType orderType, @NonNull OrderParam param) {
        Bundle args = new Bundle();
        args.putSerializable(ORDER_TYPE_KEY, orderType);
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
            mOrderType = (OrderType) arguments.getSerializable(ORDER_TYPE_KEY);
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
            // 计算总价格与选中数
            if (item.isSelected()) {
                mTotalAmount = CommonUtils.subDouble(mTotalAmount, item.getTotalAmount()).doubleValue();
                mSelectNum--;
            } else {
                mTotalAmount = CommonUtils.addDouble(mTotalAmount, item.getTotalAmount(), 0).doubleValue();
                mSelectNum++;
            }
            item.setSelected(!item.isSelected());
            mAdapter.notifyItemChanged(position);
            updateBottomBarData();
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurResp = mAdapter.getItem(position);
        });
    }

    /**
     * 更新底部工具栏数据
     */
    private void updateBottomBarData() {
        mConfirm.setEnabled(mSelectNum > 0);
        mTotalAmountText.setText(handleTotalAmount());
        mSelectAll.setSelected(mSelectNum == mAdapter.getSelectableNum());
        String text = mConfirm.getText().toString();
        StringBuilder stringBuilder = new StringBuilder(text);
        stringBuilder.replace(text.indexOf("(") + 1, text.indexOf(")"), String.valueOf(mSelectNum));
        mConfirm.setText(stringBuilder.toString());
    }

    /**
     * 处理总金额
     */
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
            mInterval.setText(String.format("%s ~ %s", mOrderParam.getFormatCreateStart(Constants.FORMAT_YYYY_MM_DD_DASH),
                    mOrderParam.getFormatCreateEnd(Constants.FORMAT_YYYY_MM_DD_DASH)));
        } else if (!TextUtils.isEmpty(mOrderParam.getFormatExecuteStart(Constants.FORMAT_YYYY_MM_DD_HH_DASH))) {
            mFilterHeader.setVisibility(View.VISIBLE);
            mLabel.setText("当前按到货时间筛选");
            mInterval.setText(String.format("%s ~ %s", mOrderParam.getFormatExecuteStart(Constants.FORMAT_YYYY_MM_DD_HH_DASH),
                    mOrderParam.getFormatExecuteEnd(Constants.FORMAT_YYYY_MM_DD_HH_DASH)));
        } else if (!TextUtils.isEmpty(mOrderParam.getFormatSignStart(Constants.FORMAT_YYYY_MM_DD_HH_DASH))) {
            mFilterHeader.setVisibility(View.VISIBLE);
            mLabel.setText("当前按签收时间筛选");
            mInterval.setText(String.format("%s ~ %s", mOrderParam.getFormatSignStart(Constants.FORMAT_YYYY_MM_DD_HH_DASH),
                    mOrderParam.getFormatSignEnd(Constants.FORMAT_YYYY_MM_DD_HH_DASH)));
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
        mDeliverTypeAdapter = null;
        mDeliverType = null;
        mBottomBar = null;
        mConfirm = null;
        mSelectAll = null;
        mTotalAmountText = null;
        mEmptyView = null;
        mDeliverTypeRoot = null;
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
    public String getDeliverType() {
        return mDeliverType;
    }

    @Override
    public void setDeliverType(String type) {
        if (mDeliverType != null && mDeliverType.equals(type)) return;
        mDeliverType = type;
    }

    @Override
    public void refreshListData(List<OrderResp> resps) {
        mAdapter.setNewData(resps);
        if (CommonUtils.isEmpty(resps)) {
            // 配置空视图
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTips("你还没有" + mOrderType.getLabel() + "的订单噢");
            if (mBottomBar != null)  // 隐藏底部操作栏
                mBottomBar.setVisibility(View.GONE);
            if (mDeliverTypeRoot != null) {// 隐藏发货类型
                mDeliverType = null;
                mDeliverTypeRoot.setVisibility(View.GONE);
            }
        } else if (!TextUtils.isEmpty(mOrderType.getButtonText())) { // 如果有按钮文本
            initBottomBar();
            mAdapter.setCanCheck();
            mConfirm.setText(String.format("%s(0)", mOrderType.getButtonText()));
            mBottomBar.setVisibility(View.VISIBLE);
            updateBottomBarData();
        }
    }

    /**
     * 初始化底部工具栏
     */
    private void initBottomBar() {
        if (mBottomBar == null) {
            mBottomBar = mBottomBarStub.inflate();
            mTotalAmountText = mBottomBar.findViewById(R.id.obb_sum);
            mSelectAll = mBottomBar.findViewById(R.id.obb_select_all);
            mConfirm = mBottomBar.findViewById(R.id.obb_confirm);
            mSelectAll.setOnClickListener(this::selectAll);
            mConfirm.setOnClickListener(this::confirm);
        }
    }

    @Override
    public void appendListData(List<OrderResp> resps) {
        mAdapter.addData(resps);
    }

    @Override
    public void statusChanged() {
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REMOVE_SELECTED));
    }

    @Override
    public void updateDeliverHeader(List<DeliverNumResp.DeliverType> deliverTypes) {
        String type = null;
        if (!CommonUtils.isEmpty(deliverTypes)) {
            initDeliverType();
            mDeliverTypeRoot.setVisibility(View.VISIBLE);
            type = deliverTypes.get(0).getKey();
            mDeliverTypeAdapter.setNewData(deliverTypes);
        } else {
            if (mDeliverTypeRoot != null) {
                mDeliverTypeRoot.setVisibility(View.GONE);
            }
        }
        setDeliverType(type);
    }

    /**
     * 初始化发货类型
     */
    private void initDeliverType() {
        if (mDeliverTypeRoot == null) {
            mDeliverTypeRoot = mDeliverTypeStub.inflate();
            RecyclerView listView = mDeliverTypeRoot.findViewById(R.id.dth_listView);
            listView.setLayoutManager(new LinearLayoutManager(requireContext(), OrientationHelper.HORIZONTAL, false));
            listView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
            mDeliverTypeAdapter = new OrderDeliverTypeAdapter();
            listView.setAdapter(mDeliverTypeAdapter);
            mDeliverTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
                mDeliverTypeAdapter.setSelectPos(position);
                DeliverNumResp.DeliverType item = mDeliverTypeAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                setDeliverType(item.getKey());
                mPresenter.refreshList();
            });
            View view = mDeliverTypeRoot.findViewById(R.id.dth_look_info);
            view.setOnClickListener(v -> {
                String s = ((TextView) v).getText().toString();
                showToast(s + "待添加");
            });
        }
    }

    private void removeSelectedItems() {
        mSelectNum = 0;
        ArrayList<OrderResp> data = new ArrayList<>();
        for (OrderResp resp : mAdapter.getData()) {
            if (!resp.isSelected()) {
                data.add(resp);
            }
        }
        refreshListData(data);
    }

    @OnClick(R.id.otf_cancel)
    public void cancelFilter() {
        mOrderParam.cancelTimeInterval();
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    /**
     * 全选
     */
    private void selectAll(View view) {
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
        updateBottomBarData();
    }

    private void confirm(View view) {
        String subBillIds = getSubBillIds();
        switch (mOrderType) {
            case PENDING_RECEIVE:
                mPresenter.receiveOrder(subBillIds);
                break;
            case PENDING_DELIVER:
                mPresenter.deliver(subBillIds, null, null);
                break;
        }
    }

    private String getSubBillIds() {
        StringBuilder sb = new StringBuilder();
        List<OrderResp> data = mAdapter.getData();
        for (OrderResp resp : data) {
            sb.append(resp.getSubBillID()).append(",");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Subscribe
    public void handleOrderEvent(OrderEvent event) {
        switch (event.getMessage()) {
            case OrderEvent.SEARCH_WORDS:
                if (isFragmentVisible())
                    mOrderParam.setSearchWords((String) event.getData());
            case OrderEvent.REFRESH_LIST:
                setForceLoad(true);
                lazyLoad();
                break;
            case OrderEvent.UPDATE_ITEM:
                setForceLoad(!isFragmentVisible());
                replaceItem((OrderResp) event.getData());
                break;
            case OrderEvent.REMOVE_SELECTED:
                setForceLoad(!isFragmentVisible());
                removeSelectedItems();
                break;
        }
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void replaceItem(OrderResp resp) {
        if (mOrderType.contain(resp.getSubBillStatus())) {
            mAdapter.replaceData(mCurResp, resp);
        } else {
            mAdapter.removeData(resp);
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
