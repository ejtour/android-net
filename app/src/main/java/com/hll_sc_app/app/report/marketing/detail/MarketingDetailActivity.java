package com.hll_sc_app.app.report.marketing.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.marketing.MarketingDetailResp;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ExcelFooter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;

import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */

public abstract class MarketingDetailActivity extends BaseLoadActivity implements IMarketingDetailContract.IMarketingDetailView {
    @BindView(R.id.rmd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rmd_name)
    TextView mName;
    @BindView(R.id.rmd_excel)
    ExcelLayout mExcel;
    String mDiscountID;
    String mDiscountName;
    String mStartDate;
    String mEndDate;
    private IMarketingDetailContract.IMarketingDetailPresenter mPresenter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    protected AtomicInteger mIndex = new AtomicInteger();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_marketing_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initArgs();
        initView();
        initData();
    }

    private void initArgs() {
        Intent intent = getIntent();
        mDiscountID = intent.getStringExtra("object0");
        mDiscountName = intent.getStringExtra("object1");
        mStartDate = intent.getStringExtra("object2");
        mEndDate = intent.getStringExtra("object3");
    }

    private void initData() {
        mReq.put("discountID", mDiscountID);
        mReq.put("startDate", mStartDate);
        mReq.put("endDate", mEndDate);
        mReq.put("opType", String.valueOf(getOpType()));
        mReq.put("groupID", UserConfig.getGroupID());
        mPresenter = MarketingDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(getHeaderTitle());
        mName.setText(mDiscountName);
        View footerView = generateFooter();
        if (footerView instanceof ExcelFooter) {
            mExcel.setFooterView(footerView);
        } else {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) footerView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mExcel.getLayoutParams();
            footerView.setId(R.id.base_tag_1);
            layoutParams.addRule(RelativeLayout.ABOVE, R.id.base_tag_1);
            ((RelativeLayout) mExcel.getParent()).addView(footerView);
        }
        mExcel.setHeaderView(generateHeader());
        mExcel.setColumnDataList(generateColumnData());
        mExcel.setEnableRefresh(false);
        mExcel.setEnableLoadMore(false);
    }

    public final View generateHeader() {
        int[] widthArray = getWidthArray();
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(widthArray.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[widthArray.length];
        for (int i = 0; i < widthArray.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(widthArray[i]));
        }
        row.updateItemData(array);
        row.updateRowDate(getTitleArray());
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    public abstract int getOpType();

    public abstract int[] getWidthArray();

    public abstract CharSequence[] getTitleArray();

    public abstract String getHeaderTitle();

    public abstract View generateFooter();

    public abstract ExcelRow.ColumnData[] generateColumnData();

    public abstract void handleData(ExcelLayout excel, MarketingDetailResp resp);

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void setData(MarketingDetailResp resp) {
        handleData(mExcel, resp);
    }
}
