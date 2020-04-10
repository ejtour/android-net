package com.hll_sc_app.app.contractmanage.contractmount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.contract.ContractMountBean;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_CONTRACT_MOUNT)
public class ContractMountActivity extends BaseLoadActivity implements IContractMountContract.IView {
    private Unbinder unbinder;
    @Autowired(name = "parcelable")
    ContractListResp.ContractBean mContractBean;
    @BindView(R.id.txt_total_money)
    TextView mTxtTotalMoney;
    @BindView(R.id.txt_left_money)
    TextView mTxtLeftMoney;
    @BindView(R.id.txt_product_number)
    TextView mTxtProductNumber;
    @BindView(R.id.txt_shop_number)
    TextView mTxtShopNumber;
    @BindView(R.id.txt_order_number)
    TextView mTxtOrderNumber;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.img_clear)
    ImageView mImgClear;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView mOrderList;
    @BindView(R.id.txt_canlaner)
    TextView mTxtCanlaner;

    private DatePickerDialog datePickerDialog;

    private IContractMountContract.IPresent mPresenter;
    private ContractMountAdapter mOrderAdapter;

    public static void start(ContractListResp.ContractBean contractBean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MOUNT, contractBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_mount);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresenter = ContractMountPresent.newInstance();
        mPresenter.register(this);
        mPresenter.getContractMount(mContractBean.getContractID());
        mPresenter.getOrderList(true);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    private void initView() {
        mImgClear.setVisibility(View.GONE);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMoreOrder();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refreshOrder();
            }
        });

        mOrderAdapter = new ContractMountAdapter(null);
        mOrderList.setAdapter(mOrderAdapter);

        mImgClear.setOnClickListener(v -> {
            mEdtSearch.setText("");
            toSearch();
        });

        mTxtCanlaner.setText(String.format("%s - %s",
                CalendarUtils.getDateFormatString(mContractBean.getStartDate(), Constants.UNSIGNED_YYYY_MM_DD, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.getDateFormatString(mContractBean.getEndDate(), Constants.UNSIGNED_YYYY_MM_DD, Constants.SLASH_YYYY_MM_DD)
        ));
        mTxtCanlaner.setTag(R.id.date_start, mContractBean.getStartDate());
        mTxtCanlaner.setTag(R.id.date_end, mContractBean.getEndDate());
        mTxtCanlaner.setOnClickListener(v -> {
            if (datePickerDialog == null) {
                long start = CalendarUtils.parse(mContractBean.getStartDate(), Constants.UNSIGNED_YYYY_MM_DD).getTime();
                long end = CalendarUtils.parse(mContractBean.getEndDate(), Constants.UNSIGNED_YYYY_MM_DD).getTime();
                datePickerDialog = DatePickerDialog.newBuilder(this)
                        .setBeginTime(start)
                        .setEndTime(end)
                        .setSelectBeginTime(start)
                        .setSelectEndTime(end)
                        .setTitle("下单时间")
                        .setShowHour(false)
                        .setCallback(new DatePickerDialog.SelectCallback() {
                            @Override
                            public void select(Date beginTime, Date endTime) {
                                mTxtCanlaner.setText(String.format("%s - %s",
                                        CalendarUtils.format(beginTime, Constants.SLASH_YYYY_MM_DD),
                                        CalendarUtils.format(endTime, Constants.SLASH_YYYY_MM_DD)
                                ));
                                mTxtCanlaner.setTag(R.id.date_start, CalendarUtils.format(beginTime, Constants.UNSIGNED_YYYY_MM_DD));
                                mTxtCanlaner.setTag(R.id.date_end, CalendarUtils.format(endTime, Constants.UNSIGNED_YYYY_MM_DD));
                                mPresenter.refreshOrder();
                            }
                        })
                        .setCancelable(false)
                        .create();
            }
            datePickerDialog.show();
        });
    }


    private void toSearch() {
        ViewUtils.clearEditFocus(mEdtSearch);
        mPresenter.refreshOrder();
    }


    @OnEditorAction(R.id.edt_search)
    public boolean editAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            toSearch();
        }
        return true;
    }

    @OnTextChanged(R.id.edt_search)
    public void onTextChanged(CharSequence s) {
        mImgClear.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void getContractMountSuccess(ContractMountBean mountBean) {
        mTxtTotalMoney.setText(mountBean.getContractTotalAmount());
        mTxtLeftMoney.setText(mountBean.getLessContractAmount());
        mTxtOrderNumber.setText(mountBean.getOrderCount());
        mTxtProductNumber.setText(mountBean.getProductCount());
        mTxtShopNumber.setText(mountBean.getShopCount());
    }


    @Override
    public void getOrderListSuccess(List<OrderResp> orderResps, boolean isMore) {
        if (isMore) {
            mOrderAdapter.addData(orderResps);
        } else {
            if (CommonUtils.isEmpty(orderResps)) {
                mOrderAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("没有订单").create());
                mOrderAdapter.setNewData(null);
            } else {
                mOrderAdapter.setNewData(orderResps);
            }
        }
        if (!CommonUtils.isEmpty(orderResps)) {
            mRefreshLayout.setEnableLoadMore(orderResps.size() == mPresenter.getOrderPageSize());
        }
    }

    @Override
    public String getOrderNo() {
        return mEdtSearch.getText().toString();
    }

    @Override
    public ContractListResp.ContractBean getContractBean() {
        return mContractBean;
    }

    @Override
    public String getStartDate() {
        return mTxtCanlaner.getTag(R.id.date_start).toString();
    }

    @Override
    public String getEndDate() {
        return mTxtCanlaner.getTag(R.id.date_end).toString();
    }
}
