package com.hll_sc_app.app.report.loss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.loss.LossBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客户流失率明细
 *
 * @author 初坤
 * @date 20190720
 */
public abstract class BaseLossActivity extends BaseLoadActivity implements ILossContract.ILossView {
    @BindView(R.id.rsd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rsd_excel)
    ExcelLayout mExcel;
    @BindView(R.id.rsd_type)
    TextView mType;
    @BindView(R.id.rsd_date)
    protected TextView mDate;
    @BindView(R.id.rsd_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.rsd_date_arrow)
    protected TriangleView mDateArrow;
    private ILossContract.ILossPresenter mPresenter;
    private ContextOptionsWindow mExportOptionsWindow;
    private ContextOptionsWindow mOptionsWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_refund_statistic_details);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    protected void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("flag", getFlag());
        mReq.put("dataType", "0");
        mPresenter = LossPresenter.newInstance();
        mPresenter.register(this);
        updateSelectDate();
    }

    protected abstract String getFlag();

    protected abstract String getHeaderTitle();

    protected void updateSelectDate() {
        mPresenter.start();
    }

    protected void initView() {
        mTitleBar.setHeaderTitle(getHeaderTitle());
        mTitleBar.setRightBtnClick(this::showExportOptionsWindow);
        mType.setText("7日流失");
        mType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_date, 0, 0, 0);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setHeaderView(generateHeader());
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick(R.id.rsd_type_btn)
    public void selectType(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this);
            List<OptionsBean> optionsBeans = new ArrayList<>();
            optionsBeans.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_PRE_SEVEN_LOSS));
            optionsBeans.add(new OptionsBean(R.drawable.ic_filter_option, OptionType.OPTION_PRE_THIRTY_LOSS));
            mOptionsWindow.refreshList(optionsBeans);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                OptionsBean item = (OptionsBean) adapter.getItem(position);
                if (item == null) return;
                mOptionsWindow.dismiss();
                mReq.put("dataType", TextUtils.equals(item.getLabel(), OptionType.OPTION_PRE_SEVEN_LOSS) ? "0" : "1");
                mPresenter.start();
                mType.setText(item.getLabel());
            });
            mOptionsWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.START);
    }

    @OnClick(R.id.rsd_date_btn)
    public void selectDate(View view) {
        toSelectDate(view);
    }

    protected abstract void toSelectDate(View view);

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(this, tip);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    private void showExportOptionsWindow(View v) {
        if (mExportOptionsWindow == null) {
            mExportOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option,
                            OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mExportOptionsWindow.dismiss();
                        mPresenter.export(null);
                    });
        }
        mExportOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    protected abstract View generateHeader();

    protected abstract ExcelRow.ColumnData[] generateColumnData();

    @Override
    public void setData(List<LossBean> list, boolean append) {
        mExcel.setEnableLoadMore(!CommonUtils.isEmpty(list) && list.size() == 20);
        mExcel.setData(list, append);
    }
}
