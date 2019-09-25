package com.hll_sc_app.app.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.widget.NestedScrollView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.audit.AuditActivity;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.app.report.ReportEntryActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.common.SalesVolumeResp;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.impl.IReload;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页 fragment
 *
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */
@Route(path = RouterConfig.ROOT_HOME_MAIN)
public class MainHomeFragment extends BaseLoadFragment implements IMainHomeContract.IMainHomeView, BaseQuickAdapter.OnItemClickListener, IReload {

    @BindView(R.id.fmh_top_bg)
    ImageView mTopBg;
    @BindView(R.id.fmh_sales_volume)
    TextView mSaleVolume;
    @BindView(R.id.fmh_refresh_view)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.fmh_top_tag)
    TextView mTopTag;
    @BindView(R.id.fmh_tag_flag)
    ImageView mTagFlag;
    @BindView(R.id.fmh_message_icon)
    ImageView mMessageIcon;
    Unbinder unbinder;
    @BindView(R.id.fmh_bill_num)
    TextView mBillNum;
    @BindView(R.id.fmh_group_shop)
    TextView mGroupShop;
    @BindView(R.id.fmh_product_num)
    TextView mProductNum;
    @BindView(R.id.fmh_warehouse_bill_num)
    TextView mWarehouseBillNum;
    @BindView(R.id.fmh_warehouse_shop)
    TextView mWarehouseShop;
    @BindView(R.id.fmh_warehouse_delivery_num)
    TextView mWarehouseDeliveryNum;
    @BindView(R.id.fmh_warehouse_amount)
    TextView mWarehouseAmount;
    @BindView(R.id.fmh_warehouse_group)
    Group mWarehouseGroup;
    @BindView(R.id.fmh_pending_receive)
    TextView mPendingReceive;
    @BindView(R.id.fmh_pending_delivery)
    TextView mPendingDelivery;
    @BindView(R.id.fmh_delivered)
    TextView mDelivered;
    @BindView(R.id.fmh_pending_settle)
    TextView mPendingSettle;
    @BindView(R.id.fmh_customer_service)
    TextView mCustomerService;
    @BindView(R.id.fmh_driver)
    TextView mDriver;
    @BindView(R.id.fmh_warehouse_in)
    TextView mWarehouseIn;
    @BindView(R.id.fmh_finance)
    TextView mFinance;
    @BindView(R.id.fmh_title_bar)
    View mTitleBar;
    @BindView(R.id.fmh_scroll_view)
    NestedScrollView mScrollView;
    @BindDimen(R.dimen.title_bar_height)
    int mTitleBarHeight;
    @IMainHomeContract.DateType
    private int mDateType = IMainHomeContract.DateType.TYPE_DAY;
    private IMainHomeContract.IMainHomePresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        initData();
        return rootView;
    }

    private void initData() {
        mPresenter = MainHomePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        showStatusBar();
        mTitleBar.getBackground().mutate().setAlpha(0);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int alpha = 0;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mTitleBar == null) return;
                if (scrollY <= mTitleBarHeight) {
                    alpha = (int) (255 * (float) scrollY / mTitleBarHeight);
                    mTitleBar.getBackground().mutate().setAlpha(alpha);
                } else if (alpha < 255) {
                    alpha = 255;
                    mTitleBar.getBackground().mutate().setAlpha(alpha);
                }
            }
        });
        mRefreshView.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (mTopBg == null) return;
                mTopBg.setScaleX(1 + percent * 0.7f);
                mTopBg.setScaleY(1 + percent * 0.7f);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.querySalesVolume(false);
            }
        });
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mMessageIcon.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_day_option, OptionType.OPTION_CURRENT_DATE));
            list.add(new OptionsBean(R.drawable.ic_week_option, OptionType.OPTION_CURRENT_WEEK));
            list.add(new OptionsBean(R.drawable.ic_month_option, OptionType.OPTION_CURRENT_MONTH));
            mOptionsWindow = new ContextOptionsWindow(requireActivity())
                    .refreshList(list)
                    .setListener(this);
        }
        mOptionsWindow.showAsDropDownFix(view, -UIUtils.dip2px(5), 0, Gravity.START);
    }

    @Override
    public void updateSalesVolume(SalesVolumeResp resp) {
        updateSummary(resp);
        updateWarehouse(resp);
        updateOrderBoard(resp);
    }

    private void updateOrderBoard(SalesVolumeResp resp) {
        mPendingReceive.setText(CommonUtils.formatNumber(resp.getUnReceiveBillNum()));
        mPendingDelivery.setText(CommonUtils.formatNumber(resp.getUnDeliveryBillNum()));
        mDelivered.setText(CommonUtils.formatNumber(resp.getDeliveryBillNum()));
        mPendingSettle.setText(CommonUtils.formatNumber(resp.getUnSettlementBillNum()));
        mCustomerService.setText(CommonUtils.formatNumber(resp.getCustomServicedRefundNum()));
        mDriver.setText(CommonUtils.formatNumber(resp.getDrivedRefundNum()));
        mWarehouseIn.setText(CommonUtils.formatNumber(resp.getWareHousedRefundNum()));
        mFinance.setText(CommonUtils.formatNumber(resp.getFinanceReviewRefundNum()));
    }

    private void updateWarehouse(SalesVolumeResp resp) {
        if (resp.isWareHouse()) {
            mWarehouseGroup.setVisibility(View.VISIBLE);
            mWarehouseBillNum.setText(CommonUtils.formatNumber(resp.getWareHouseBillNum()));
            mWarehouseShop.setText(CommonUtils.formatNumber(resp.getWareHouseShopNum()));
            mWarehouseDeliveryNum.setText(CommonUtils.formatNumber(resp.getWareHouseDeliveryGoodsNum()));
            String source = String.format("¥%s", CommonUtils.formatMoney(resp.getWareHouseDeliveryGoodsAmount()));
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new RelativeSizeSpan(0.81f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new RelativeSizeSpan(0.81f), source.indexOf("."), source.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mWarehouseAmount.setText(ss);
        } else mWarehouseGroup.setVisibility(View.GONE);
    }

    private void updateSummary(SalesVolumeResp resp) {
        mSaleVolume.setText(CommonUtils.formatMoney(resp.getSales()));
        mBillNum.setText(CommonUtils.formatNumber(resp.getBillNum()));
        mGroupShop.setText(String.format("%s/%s", CommonUtils.formatNumber(resp.getPurchaserShopNum()), CommonUtils.formatNumber(resp.getPurchaserNum())));
        mProductNum.setText(CommonUtils.formatNumber(resp.getProductNum()));
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @IMainHomeContract.DateType
    @Override
    public int getDateType() {
        return mDateType;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OptionsBean item = (OptionsBean) adapter.getItem(position);
        if (item == null) return;
        mOptionsWindow.dismiss();
        switch (item.getLabel()) {
            case OptionType.OPTION_CURRENT_DATE:
                if (mDateType == 0) return;
                mDateType = 0;
                break;
            case OptionType.OPTION_CURRENT_WEEK:
                if (mDateType == 1) return;
                mDateType = 1;
                break;
            case OptionType.OPTION_CURRENT_MONTH:
                if (mDateType == 2) return;
                mDateType = 2;
                break;
            default:
                return;
        }
        mTagFlag.setImageResource(item.getIconRes());
        mTopTag.setText(item.getLabel());
        mPresenter.start();
    }

    @OnClick({R.id.fmh_pending_receive_btn, R.id.fmh_pending_delivery_btn, R.id.fmh_delivered_btn, R.id.fmh_pending_settle_btn})
    public void gotoOrderManager(View view) {
        OrderType type = OrderType.PENDING_TRANSFER;
        switch (view.getId()) {
            case R.id.fmh_pending_receive_btn:
                type = OrderType.PENDING_RECEIVE;
                break;
            case R.id.fmh_pending_delivery_btn:
                type = OrderType.PENDING_DELIVER;
                break;
            case R.id.fmh_delivered_btn:
                type = OrderType.DELIVERED;
                break;
            case R.id.fmh_pending_settle_btn:
                type = OrderType.PENDING_SETTLE;
                break;
            default:
                return;
        }
        EventBus.getDefault().postSticky(new OrderEvent(OrderEvent.CHANGE_INDEX, type.getType()));
    }

    @OnClick({R.id.fmh_customer_service_btn, R.id.fmh_driver_btn, R.id.fmh_warehouse_in_btn, R.id.fmh_finance_btn})
    public void gotoAfterSales(View view) {
        int position;
        switch (view.getId()) {
            case R.id.fmh_customer_service_btn:
                position = 1;
                break;
            case R.id.fmh_driver_btn:
                position = 2;
                break;
            case R.id.fmh_warehouse_in_btn:
                position = 3;
                break;
            case R.id.fmh_finance_btn:
                position = 4;
                break;
            default:
                return;
        }
        AuditActivity.start(position);
    }

    @OnClick({R.id.fmh_entry_add_product, R.id.fmh_entry_price_manage, R.id.fmh_entry_agreement_price, R.id.fmh_entry_bill_list,
            R.id.fmh_entry_purchaser, R.id.fmh_entry_sale, R.id.fmh_entry_report_center, R.id.fmh_entry_market_price})
    public void shortcut(View view) {
        switch (view.getId()) {
            case R.id.fmh_entry_add_product:
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_ADD);
                break;
            case R.id.fmh_entry_price_manage:
                RouterUtil.goToActivity(RouterConfig.PRICE_MANAGE);
                break;
            case R.id.fmh_entry_agreement_price:
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE);
                break;
            case R.id.fmh_entry_bill_list:
                RouterUtil.goToActivity(RouterConfig.BILL_LIST);
                break;
            case R.id.fmh_entry_purchaser:
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST);
                break;
            case R.id.fmh_entry_sale:
                RouterUtil.goToActivity(RouterConfig.ORIENTATION_LIST);
                break;
            case R.id.fmh_entry_report_center:
                ReportEntryActivity.start();
                break;
            case R.id.fmh_entry_market_price:
                showToast("市场价格待添加");
                break;
        }
    }

    @OnClick({R.id.fmh_message_icon, R.id.fmh_top_tag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fmh_message_icon:
                break;
            case R.id.fmh_top_tag:
                showOptionsWindow(view);
                break;
        }
    }

    @Override
    public void reload() {
        mPresenter.start();
    }
}