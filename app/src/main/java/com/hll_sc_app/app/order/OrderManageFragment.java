package com.hll_sc_app.app.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import com.hll_sc_app.app.order.deliver.DeliverInfoActivity;
import com.hll_sc_app.app.order.deliver.OrderDeliverTypeAdapter;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.app.order.summary.OrderSummaryActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.event.ExportEvent;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.deliver.DeliverNumResp;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.order.ExpressInfoDialog;
import com.hll_sc_app.widget.order.OrderFilterView;
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
    @BindView(R.id.fom_filter_view)
    OrderFilterView mFilterHeader;
    @BindView(R.id.fom_order_summary)
    TextView mOrderSummary;
    @BindView(R.id.fom_list)
    RecyclerView mListView;
    @BindView(R.id.fom_refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fom_bottom_bar_stub)
    ViewStub mBottomBarStub;
    @BindView(R.id.fom_deliver_type_stub)
    ViewStub mDeliverTypeStub;
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
    Unbinder unbinder;
    private String mEventMessage;
    private ExpressInfoDialog mExpressInfoDialog;

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
        if (OrderType.PENDING_RECEIVE == mOrderType || OrderType.DELIVERED == mOrderType) {
            mOrderSummary.setVisibility(View.VISIBLE);
            if (OrderType.DELIVERED == mOrderType) {
                mOrderSummary.setText("本月已发货商品总量");
            }
        }
        mAdapter = new OrderManageAdapter();
        mListView.setAdapter(mAdapter);
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
            OrderResp item = mAdapter.getItem(position);
            if (item == null) {
                return;
            }
            if ("3".equals(mDeliverType) && mConfirm.isEnabled() && !item.isSelected()) {
                showToast("由于三方物流配送订单需输入物流单号 所以该类型订单不可批量操作");
                return;
            }
            item.setSelected(!item.isSelected());
            mAdapter.notifyItemChanged(position);
            updateBottomBarData();
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurResp = mAdapter.getItem(position);
            if (mCurResp == null) return;
            OrderDetailActivity.start(mCurResp.getSubBillID());
        });
    }

    /**
     * 更新底部工具栏数据
     */
    private void updateBottomBarData() {
        if (TextUtils.isEmpty(mOrderType.getButtonText())) { // 如果没有按钮文本
            return;
        }
        initBottomBar();
        List<OrderResp> data = mAdapter.getData();
        if (data.size() > 0) {
            mBottomBarRoot.setVisibility(View.VISIBLE);
            double totalAmount = 0;
            int num = 0;
            for (OrderResp resp : data) {
                if (resp.isSelected()) {
                    totalAmount = CommonUtils.addDouble(totalAmount, resp.getTotalAmount(), 0).doubleValue();
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
        mFilterHeader.setData(mOrderParam);
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
        mBottomBarRoot = null;
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
    public boolean setDeliverType(String type) {
        if (mDeliverType != null && mDeliverType.equals(type)) return false;
        mDeliverType = type;
        return true;
    }

    @Override
    public void updateListData(List<OrderResp> resps, boolean append) {
        mRefreshLayout.setEnableLoadMore(!CommonUtils.isEmpty(resps) && resps.size() == 20);
        if (append) {
            if (!CommonUtils.isEmpty(resps))
                mAdapter.addData(resps);
        } else {
            mAdapter.setNewData(resps);
            if (CommonUtils.isEmpty(resps))
                handleEmptyData();
        }
        updateBottomBarData();
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
            mAdapter.setCanCheck();
            mConfirm.setText(String.format("%s(0)", mOrderType.getButtonText()));
        }
    }

    @Override
    public void statusChanged() {
        boolean reload = false;
        if (!CommonUtils.isEmpty(mAdapter.getData()))
            for (OrderResp resp : mAdapter.getData()) {
                if (resp.getShipperType() == 2 && mOrderType == OrderType.PENDING_RECEIVE) {
                    reload = true;
                    break;
                }
            }
        EventBus.getDefault().post(new OrderEvent(reload ? OrderEvent.REFRESH_LIST : OrderEvent.REMOVE_SELECTED));
    }

    @Override
    public boolean updateDeliverHeader(List<DeliverNumResp.DeliverType> deliverTypes) {
        if (CommonUtils.isEmpty(deliverTypes)) {
            if (mDeliverTypeRoot != null)
                mDeliverTypeRoot.setVisibility(View.GONE);
            updateListData(null, false);
            return true;
        } else {
            initDeliverType();
            mDeliverTypeRoot.setVisibility(View.VISIBLE);
            mDeliverTypeAdapter.setNewData(deliverTypes);
            int selectPos = 0;
            for (int i = 0; i < deliverTypes.size(); i++) {
                if (deliverTypes.get(i).getKey().equals(mDeliverType)) {
                    selectPos = i;
                    break;
                }
            }
            mDeliverTypeAdapter.setSelectPos(selectPos);
            if (selectPos == 0) mDeliverType = deliverTypes.get(0).getKey();
            return false;
        }
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(requireActivity(), email -> {
            if (mEventMessage != null) {
                EventBus.getDefault().post(new ExportEvent(mEventMessage, email));
                mEventMessage = null;
            }
        });
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(requireActivity(), email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(requireActivity(), msg);
    }

    @Override
    public void showExpressCompanyList(List<ExpressResp.ExpressBean> beans, ExpressResp.ExpressBean company) {
        SingleSelectionDialog selectionDialog = SingleSelectionDialog.newBuilder(requireActivity(), ExpressResp.ExpressBean::getDeliveryCompanyName)
                .setTitleText("物流公司")
                .refreshList(beans)
                .select(company)
                .setOnSelectListener(bean -> mExpressInfoDialog.setCompany(bean, beans))
                .create();
        selectionDialog.setOnDismissListener(dialog -> mExpressInfoDialog.show());
        selectionDialog.show();
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
                if (setDeliverType(item.getKey())) {
                    mPresenter.start();
                }
            });
            View view = mDeliverTypeRoot.findViewById(R.id.dth_look_info);
            view.setOnClickListener(v -> DeliverInfoActivity.start(mOrderType.getStatus()));
        }
    }

    @OnClick(R.id.fom_filter_view)
    public void cancelFilter() {
        mOrderParam.cancelTimeInterval();
        EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_LIST));
    }

    @OnClick(R.id.fom_order_summary)
    public void toSummary() {
        if (mOrderType == OrderType.PENDING_RECEIVE) {
            OrderSummaryActivity.start(requireActivity());
        } else if (mOrderType == OrderType.DELIVERED) {
            DeliverInfoActivity.start(mOrderType.getStatus());
        }
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
        if ("3".equals(mDeliverType)) {
            showToast("由于三方物流配送订单需输入物流单号 所以该类型订单不可批量操作");
            return;
        }
        String groupID = UserConfig.getGroupID();
        for (OrderResp resp : mAdapter.getData()) {
            if (resp.isCanSelect(groupID)) resp.setSelected(!view.isSelected());
        }
        mAdapter.notifyDataSetChanged();
        updateBottomBarData();
    }

    private void confirm(View view) {
        if ("3".equals(mDeliverType)) {
            showExpressInfoDialog();
            return;
        }
        switch (mOrderType) {
            case PENDING_RECEIVE:
                mPresenter.receiveOrder(TextUtils.join(",", getSubBillIds()));
                break;
            case PENDING_DELIVER:
                mPresenter.deliver(TextUtils.join(",", getSubBillIds()), null, null);
                break;
        }
    }

    /**
     * 显示物流信息弹窗
     */
    private void showExpressInfoDialog() {
        mExpressInfoDialog = new ExpressInfoDialog(requireActivity(), new ExpressInfoDialog.ExpressCallback() {
            @Override
            public void onGetExpressInfo(String name, String orderNo) {
                mPresenter.deliver(TextUtils.join(",", getSubBillIds()), name, orderNo);
            }

            @Override
            public void onSelectCompany(ExpressResp.ExpressBean company, List<ExpressResp.ExpressBean> beans) {
                if (!CommonUtils.isEmpty(beans)) {
                    showExpressCompanyList(beans, company);
                    return;
                }
                OrderResp resp = null;
                for (OrderResp data : mAdapter.getData()) {
                    if (data.isSelected()) resp = data;
                }
                if (resp == null) {
                    return;
                }
                mPresenter.getExpressCompanyList(resp.getGroupID(), resp.getShopID());
            }
        });
        mExpressInfoDialog.show();
    }

    private List<String> getSubBillIds() {
        List<String> billIds = new ArrayList<>();
        for (OrderResp resp : mAdapter.getData()) {
            if (resp.isSelected() || mBottomBarRoot == null) billIds.add(resp.getSubBillID());
        }
        return billIds;
    }

    @Subscribe
    public void handleOrderEvent(OrderEvent event) {
        switch (event.getMessage()) {
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
            case OrderEvent.RELOAD_ITEM:
                if (isFragmentVisible() && mCurResp != null)
                    mPresenter.getOrderDetails(mCurResp.getSubBillID());
                break;
        }
    }

    @Subscribe
    public void handleSearchEvent(ShopSearchEvent event){
        if (isFragmentVisible())
            mOrderParam.setSearchBean(event);
        setForceLoad(true);
        lazyLoad();
    }

    @Subscribe
    public void handleExportEvent(ExportEvent event) {
        if (!isFragmentVisible()) {
            return;
        }
        mEventMessage = event.getMessage(); // 保存当前消息，用于绑定邮箱后重新请求
        switch (mEventMessage) {
            case OptionType.OPTION_EXPORT_ASSEMBLY:
                List<String> subBillIds = getSubBillIds();
                if (CommonUtils.isEmpty(subBillIds)) {
                    showToast("请选择需要导出的订单");
                    break;
                }
                mPresenter.exportAssemblyOrder(subBillIds,
                        event.getData().toString());
                break;
            case OptionType.OPTION_EXPORT_CHECK_CATEGORY:
                mPresenter.exportSpecialOrder(2, event.getData().toString());
                break;
            case OptionType.OPTION_EXPORT_CHECK_DETAILS:
                mPresenter.exportSpecialOrder(0, event.getData().toString());
                break;
            case OptionType.OPTION_EXPORT_ORDER:
                mPresenter.exportNormalOrder(0, event.getData().toString());
                break;
            case OptionType.OPTION_EXPORT_ORDER_DETAILS:
                mPresenter.exportNormalOrder(1, event.getData().toString());
                break;
            case OptionType.OPTION_EXPORT_OUT_CATEGORY:
                mPresenter.exportSpecialOrder(1, event.getData().toString());
                break;
            case OptionType.OPTION_EXPORT_OUT_DETAILS:
                mPresenter.exportDeliveryOrder(getSubBillIds(),
                        event.getData().toString());
                break;
        }
    }

    private void removeSelectedItems() {
        if (!isFragmentVisible()) {
            return;
        }
        ArrayList<OrderResp> data = new ArrayList<>();
        for (OrderResp resp : mAdapter.getData()) {
            if (!resp.isSelected()) {
                data.add(resp);
            }
        }
        updateListData(data, false);
    }

    private void replaceItem(OrderResp resp) {
        if (!isFragmentVisible()) {
            return;
        }
        if (mOrderType.contain(resp.getSubBillStatus())) mAdapter.replaceData(mCurResp, resp);
        else {
            mAdapter.removeData(mCurResp);
            if (CommonUtils.isEmpty(mAdapter.getData())) updateListData(null, false);
        }
        updateBottomBarData();
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
