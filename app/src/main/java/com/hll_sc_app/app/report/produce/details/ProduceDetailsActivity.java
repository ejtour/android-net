package com.hll_sc_app.app.report.produce.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.produce.input.ProduceInputActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/31
 */

@Route(path = RouterConfig.REPORT_PRODUCE_DETAILS)
public class ProduceDetailsActivity extends BaseLoadActivity implements IProduceDetailsContract.IProduceDetailsView {
    private static final int[] WIDTH_ARRAY = {60, 60, 60, 60, 60, 60, 60, 60, 100};
    public static final int REQ_CODE = 0x762;

    /**
     * @param date 日期
     */
    public static void start(Activity activity, String date) {
        Object[] args = {date};
        RouterUtil.goToActivity(RouterConfig.REPORT_PRODUCE_DETAILS, activity, REQ_CODE, args);
    }

    @BindView(R.id.rpd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rpd_excel)
    ExcelLayout mExcel;
    @Autowired(name = "object0")
    String mDate;
    private boolean mHasChanged;
    private IProduceDetailsContract.IProduceDetailsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_produce_details);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ProduceInputActivity.REQ_CODE && resultCode == RESULT_OK) {
            mHasChanged = true;
            mPresenter.start();
        }
    }

    private void initData() {
        mPresenter = ProduceDetailsPresenter.newInstance(mDate);
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged)
            setResult(RESULT_OK);
        super.onBackPressed();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mExcel.setEnableLoadMore(false);
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setHeaderView(View.inflate(this, R.layout.view_report_produce_detail_header, null));
        mExcel.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // no-op
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.load(false);
            }
        });
    }

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]));
        for (int i = 2; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void hideLoading() {
        mExcel.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<ProduceDetailBean> beans) {
        mExcel.setData(beans, false);
    }
}
