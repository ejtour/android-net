package com.hll_sc_app.app.aftersales.apply;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.apply.operation.AfterSalesDeposit;
import com.hll_sc_app.app.aftersales.apply.operation.AfterSalesGoods;
import com.hll_sc_app.app.aftersales.apply.operation.AfterSalesReject;
import com.hll_sc_app.app.aftersales.apply.select.AfterSalesSelectActivity;
import com.hll_sc_app.app.aftersales.common.AfterSalesType;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.aftersales.AfterSalesReasonBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.aftersales.AfterSaleApplyFooter;
import com.hll_sc_app.widget.aftersales.AfterSalesApplyHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */
@Route(path = RouterConfig.AFTER_SALES_APPLY)
public class AfterSalesApplyActivity extends BaseLoadActivity implements IAfterSalesApplyContract.IAfterSalesApplyView {

    public static void start(AfterSalesApplyParam resp) {
        RouterUtil.goToActivity(RouterConfig.AFTER_SALES_APPLY, resp);
    }

    @BindView(R.id.asa_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asa_submit)
    TextView mSubmit;
    @BindView(R.id.asa_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable", required = true)
    AfterSalesApplyParam mParam;
    private IAfterSalesApplyContract.IAfterSalesApplyStrategy mStrategy;
    private IAfterSalesApplyContract.IAfterSalesApplyPresenter mPresenter;
    private BaseQuickAdapter mAdapter;
    private AfterSalesApplyHeader mHeader;
    private AfterSaleApplyFooter mFooter;
    private SingleSelectionDialog mDialog;
    private List<AfterSalesDetailsBean> mDetailsBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_after_sales_apply);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        switch (mParam.getAfterSalesType()) {
            case AfterSalesType.ORDER_REJECT:
                mStrategy = new AfterSalesReject();
                break;
            case AfterSalesType.RETURN_GOODS:
                mStrategy = new AfterSalesGoods();
                break;
            case AfterSalesType.RETURN_DEPOSIT:
                mStrategy = new AfterSalesDeposit();
                break;
        }
        mStrategy.init(mParam);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = mStrategy.getPresenter();
        mPresenter.register(this);
        mPresenter.start();
        mHeader.initData(mParam);
        mFooter.updateMoney(mParam);
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mStrategy.getApplyText().getTitle());
        mAdapter = mStrategy.createAdapter();
        mHeader = new AfterSalesApplyHeader(this);
        mHeader.init(mStrategy.getApplyText());
        mHeader.setOnClickListener(this::headerClick);
        mAdapter.setHeaderView(mHeader);
        mFooter = new AfterSaleApplyFooter(this);
        mAdapter.setFooterView(mFooter);
        mFooter.init(mStrategy.getApplyText());
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Object item = mAdapter.getItem(position);
            if (view.getId() == R.id.asd_delete_btn && item instanceof AfterSalesDetailsBean) {
                ((AfterSalesDetailsBean) item).setSelected(false);
                mAdapter.remove(position);
                toggleViewStatus();
            }
        });
    }

    @OnClick(R.id.asa_submit)
    public void submit() {
        mHeader.inflateData(mParam);
        mPresenter.submit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == AfterSalesSelectActivity.REQ_CODE) {
                mDetailsBeans = ((AfterSalesApplyParam) data.getParcelableExtra("parcelable")).getAfterSalesDetailList();
                updateData();
            } else mHeader.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void updateDetailsData(List<AfterSalesDetailsBean> list) {
        mDetailsBeans = list;
        if (!CommonUtils.isEmpty(mParam.getAfterSalesDetailList())) {
            for (AfterSalesDetailsBean bean : mParam.getAfterSalesDetailList()) {
                for (AfterSalesDetailsBean detailsBean : list) {
                    if (bean.getId().equals(detailsBean.getId())) {
                        detailsBean.setRefundNum(bean.getRefundNum());
                        detailsBean.setSelected(true);
                        break;
                    }
                }
            }
        }
        updateData();
    }

    private void updateData() {
        List<AfterSalesDetailsBean> list = new ArrayList<>();
        for (AfterSalesDetailsBean bean : mDetailsBeans) {
            if (bean.isSelected()) list.add(bean);
        }
        mParam.setAfterSalesDetailList(list);
        mStrategy.updateAdapter();
        toggleViewStatus();
    }

    private void toggleViewStatus() {
        mFooter.updateMoney(mParam);
        updateSubmitEnable();
        mHeader.updateEditVisibility(mParam);
    }

    @Override
    public void submitSuccess(String id) {
        mStrategy.submitSuccess(id);
    }

    @Override
    public void updateReasonList(List<AfterSalesReasonBean> list) {
        List<NameValue> nameValues = new ArrayList<>();
        for (AfterSalesReasonBean bean : list) {
            if (bean.getRefundBillType() == mParam.getAfterSalesType()) {
                for (AfterSalesReasonBean.ReasonBean reason : bean.getRefundReasons()) {
                    nameValues.add(new NameValue(reason.getDesc(), String.valueOf(reason.getCode())));
                }
                break;
            }
        }
        mStrategy.setReasonList(nameValues);
    }

    private void updateSubmitEnable() {
        mSubmit.setEnabled(!TextUtils.isEmpty(mParam.getReason())
                && (!CommonUtils.isEmpty(mParam.getAfterSalesDetailList()) ||
                !CommonUtils.isEmpty(mParam.getOrderDetailList())));
    }

    public void headerClick(View view) {
        switch (view.getId()) {
            case R.id.sah_option:
                if (CommonUtils.isEmpty(mStrategy.getReasonList())) {
                    mPresenter.getAfterSalesReasonList();
                    break;
                }
                if (mDialog == null) {
                    mDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                            .setTitleText(mStrategy.getApplyText().getReasonPrefix() + "原因")
                            .refreshList(mStrategy.getReasonList())
                            .setOnSelectListener(nameValue -> {
                                mParam.setReason(nameValue.getValue());
                                mParam.setReasonDesc(nameValue.getName());
                                ((TextView) view).setText(mParam.getReasonDesc());
                                updateSubmitEnable();
                            })
                            .create();
                }
                mDialog.show();
                break;
            case R.id.sah_details_edit:
            case R.id.sah_add_item:
                if (!CommonUtils.isEmpty(mDetailsBeans)) {
                    AfterSalesSelectActivity.start(this, mDetailsBeans, mParam.getAfterSalesType());
                }
                break;
        }
    }
}
