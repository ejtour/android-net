package com.hll_sc_app.app.crm.order.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderHelper;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.order.OrderFilterView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/6
 */
@Route(path = RouterConfig.CRM_ORDER_LIST)
public class CrmOrderListActivity extends BaseLoadActivity implements ICrmOrderListContract.ICrmOrderListView, BaseQuickAdapter.OnItemClickListener {

    /**
     * @param shopID 门店id
     */
    public static void start(String shopID, String shopName) {
        RouterUtil.goToActivity(RouterConfig.CRM_ORDER_LIST, shopID, shopName);
    }

    @BindView(R.id.trl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.trl_tab_one)
    TextView mShop;
    @BindView(R.id.trl_tab_one_arrow)
    TriangleView mShopArrow;
    @BindView(R.id.trl_tab_two)
    TextView mStatus;
    @BindView(R.id.trl_tab_two_arrow)
    TriangleView mStatusArrow;
    @BindView(R.id.col_list_view)
    RecyclerView mListView;
    @BindView(R.id.col_filter_view)
    OrderFilterView mFilterHeader;
    @BindView(R.id.col_refresh_view)
    SmartRefreshLayout mRefreshView;
    private final OrderParam mOrderParam = new OrderParam();
    private ICrmOrderListContract.ICrmOrderListPresenter mPresenter;
    @Autowired(name = "object0")
    String mShopID;
    @Autowired(name = "object1")
    String mShopName;
    private Integer mBillStatus = 0;
    private List<PurchaserShopBean> mShopBeans;
    private CrmOrderListAdapter mAdapter;
    private EmptyView mEmptyView;
    private SingleSelectionWindow<PurchaserShopBean> mShopWindow;
    private SingleSelectionWindow<NameValue> mStatusWindow;
    private ContextOptionsWindow mOptionsWindow;
    private OrderResp mCurResp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_order_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = CrmOrderListPresenter.newInstance(mOrderParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void refreshList() {
        mFilterHeader.setData(mOrderParam);
        mPresenter.reload();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindows);
        mTitleBar.setHeaderTitle("订单列表");
        mShop.setText(mShopName);
        mStatus.setText("全部状态");
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(90), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new CrmOrderListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurResp = mAdapter.getItem(position);
            if (mCurResp == null) return;
            OrderDetailActivity.start(mCurResp.getSubBillID());
        });
        mListView.setAdapter(mAdapter);
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
    }

    private void showOptionsWindows(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_ORDER));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_CREATE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_EXECUTE));
            list.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_FILTER_SIGN));
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(list)
                    .setListener(this);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @OnClick({R.id.trl_tab_one_btn, R.id.trl_tab_two_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.trl_tab_one_btn:
                if (mShopBeans == null) {
                    mPresenter.queryShopList();
                    return;
                }
                showShopWindow(view);
                break;
            case R.id.trl_tab_two_btn:
                showStatusWindow(view);
                break;
        }
    }

    @OnClick({R.id.col_filter_view})
    public void dateFilter(View view) {
        mOrderParam.cancelTimeInterval();
        refreshList();
    }

    private void showShopWindow(View view) {
        if (mShopWindow == null) {
            PurchaserShopBean bean = null;
            for (PurchaserShopBean shopBean : mShopBeans) {
                if (mShopID.equals(shopBean.getShopID())) {
                    bean = shopBean;
                    break;
                }
            }
            mShopWindow = new SingleSelectionWindow<>(this, PurchaserShopBean::getShopName);
            mShopWindow.fixedHeight(UIUtils.dip2px(364));
            mShopWindow.hideDivider();
            mShopWindow.refreshList(mShopBeans);
            mShopWindow.setSelect(bean);
            mShopWindow.setSelectListener(bean1 -> {
                mShopID = bean1.getShopID();
                mShop.setText(bean1.getShopName());
                mPresenter.reload();
            });
        }
        mShopWindow.showAsDropDownFix(view);
    }

    private void showStatusWindow(View view) {
        if (mStatusWindow == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("全部状态", "0"));
            list.add(new NameValue("待接单", "1"));
            list.add(new NameValue("待发货", "2"));
            list.add(new NameValue("已收货", "3"));
            list.add(new NameValue("待结算", "4"));
            list.add(new NameValue("已结算", "5"));
            list.add(new NameValue("已完成", "6"));
            mStatusWindow = new SingleSelectionWindow<>(this, NameValue::getName);
            mStatusWindow.refreshList(list);
            mStatusWindow.hideDivider();
            mStatusWindow.setSelect(list.get(0));
            mStatusWindow.setSelectListener(nameValue -> {
                mBillStatus = Integer.valueOf(nameValue.getValue());
                mStatus.setText(nameValue.getName());
                mPresenter.reload();
            });
        }
        mStatusWindow.showAsDropDownFix(view);
    }

    @Override
    public void setData(List<OrderResp> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("哎呀，还没有订单呢");
            }
            mAdapter.setNewData(list);
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start).create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public String getShopID() {
        return mShopID;
    }

    @Override
    public Integer getBillStatus() {
        return mBillStatus;
    }

    @Override
    public void cacheShopData(List<PurchaserShopBean> list) {
        mShopBeans = list;
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter.getItem(position) instanceof OptionsBean) {
            mOptionsWindow.dismiss();
            OptionsBean item = (OptionsBean) adapter.getItem(position);
            if (item == null) return;
            if (OptionType.OPTION_EXPORT_ORDER.equals(item.getLabel())) {
                mPresenter.export("");
                return;
            }
            OrderHelper.showDatePicker(item.getLabel(), mOrderParam, this, this::refreshList);
        }
    }
}
