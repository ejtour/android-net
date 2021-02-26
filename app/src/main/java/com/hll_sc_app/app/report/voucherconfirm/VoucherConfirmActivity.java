package com.hll_sc_app.app.report.voucherconfirm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.voucherconfirm.detail.VoucherConfirmDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.bean.report.voucherconfirm.VoucherGroupBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/17
 */
@Route(path = RouterConfig.REPORT_VOUCHER_CONFIRM)
public class VoucherConfirmActivity extends BaseLoadActivity implements IVoucherConfirmContract.IVoucherConfirmView {
    @BindView(R.id.rrc_date)
    TextView mDate;
    @BindView(R.id.rrc_list_view)
    RecyclerView mListView;
    @BindView(R.id.rrc_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private IVoucherConfirmContract.IVoucherConfirmPresenter mPresenter;
    private final BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private Adapter mAdapter;
    private DateSelectWindow mDateSelectWindow;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_receipt_check);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        Date endDate = new Date();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(endDate);
        startDate.set(CalendarUtils.getYear(endDate), Calendar.JANUARY, 1);
        mDate.setTag(R.id.date_start, startDate.getTime());
        mDate.setTag(R.id.date_end, endDate);
        updateSelectDate();
        mPresenter = VoucherConfirmPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        getReq().put("startVoucherDate", CalendarUtils.toLocalDate(startDate));
        getReq().put("endVoucherDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mRefreshLayout.setEnablePureScrollMode(true);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mAdapter = new Adapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            VoucherGroupBean item = mAdapter.getItem(position);
            if (item == null) return;
            VoucherConfirmDetailActivity.start(this, item, CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_start)),
                    CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_end)));
        });
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPresenter.start();
        }
    }

    @OnClick(R.id.rrc_date_btn)
    void selectDate() {
        if (mDateSelectWindow == null) {
            mDateSelectWindow = new DateSelectWindow(this);
            mDateSelectWindow.setForbiddenStartBeforeCurrent(false);
            mDateSelectWindow.setSelectRange(CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_start)),
                    CalendarUtils.toLocalDate((Date) mDate.getTag(R.id.date_end)));
            mDateSelectWindow.setSelectListener((startDate, endDate) -> {
                Date start = DateUtil.parse(startDate);
                Date end = DateUtil.parse(endDate);
                if (CalendarUtils.getYear(start) != CalendarUtils.getYear(end)) {
                    showToast("日期查询不允许跨年");
                    return;
                }
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectDate();
                mPresenter.start();
            });
        }
        mDateSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void setData(List<VoucherGroupBean> list) {
        if (CommonUtils.isEmpty(list)) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTips("暂无数据");
        }
        mAdapter.setNewData(list);
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    static class Adapter extends BaseQuickAdapter<VoucherGroupBean, BaseViewHolder> {

        public Adapter() {
            super(R.layout.item_report_receipt_check);
        }

        @Override
        protected void convert(BaseViewHolder helper, VoucherGroupBean item) {
            String source = String.format("%s笔待确认", item.getNum());
            SpannableString ss = new SpannableString(source);
            if (item.getNum() > 0) {
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(), R.color.colorPrimary)),
                        0, source.indexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            ss.setSpan(new StyleSpan(Typeface.BOLD), 0, source.indexOf("笔"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            helper.setText(R.id.rrc_name, item.getPurchaserName())
                    .setText(R.id.rrc_num, ss);
        }
    }
}
