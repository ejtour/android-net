package com.hll_sc_app.app.order.deliver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ProductSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */
@Route(path = RouterConfig.ORDER_DELIVER)
public class DeliverInfoActivity extends BaseLoadActivity implements IDeliverInfoContract.IDeliverInfoView {
    @BindView(R.id.odi_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.odi_search_view)
    SearchView mSearchView;
    @BindView(R.id.odi_list_view)
    RecyclerView mListView;
    @Nullable
    @BindView(R.id.odi_date)
    TextView mDate;
    @Autowired(name = "object0")
    int mSubBillStatus;
    private List<DeliverInfoResp> mList;
    private DeliverInfoAdapter mAdapter;
    private int mCurPos;
    private IDeliverInfoContract.IDeliverInfoPresenter mPresenter;
    private EmptyView mEmptyView;
    private ContextOptionsWindow mOptionsWindow;
    private DatePickerDialog mDatePickerDialog;

    public static void start(int subBillStatus) {
        RouterUtil.goToActivity(RouterConfig.ORDER_DELIVER, subBillStatus);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        setContentView(mSubBillStatus == 3 ? R.layout.activity_order_delivered_info : R.layout.activity_order_deliver_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        if (mDate != null) {
            Date endDate = new Date();
            mDate.setTag(R.id.date_start, CalendarUtils.getFirstDateInMonth(endDate));
            mDate.setTag(R.id.date_end, endDate);
            updateSelectDate();
            mDate.setVisibility(View.VISIBLE);
        }
        mPresenter = DeliverInfoPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        if (mSubBillStatus == 3) {
            mSearchView.setSearchTextLeft();
            mSearchView.setTextColorWhite();
            mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        }
        mAdapter = new DeliverInfoAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurPos = position;
            DeliverInfoResp resp = mAdapter.getItem(position);
            if (resp == null) {
                return;
            }
            if (CommonUtils.isEmpty(resp.getList())) {
                mPresenter.requestShopList(resp.getProductSpecID());
            } else {
                resp.setExpanded(!resp.isExpanded());
                mAdapter.notifyItemChanged(position);
            }
        });
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(80), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(DeliverInfoActivity.this, searchContent, ProductSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                updateData();
            }
        });
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, mSubBillStatus == 3 ? OptionType.OPTION_EXPORT_DELIVERED_GOODS
                    : OptionType.OPTION_EXPORT_PEND_DELIVERY_GOODS));
            mOptionsWindow = new ContextOptionsWindow(this);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                mPresenter.export(null);
            });
            mOptionsWindow.refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.RIGHT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void updateShopList(List<DeliverShopResp> list) {
        DeliverInfoResp resp = mAdapter.getItem(mCurPos);
        if (resp == null) {
            return;
        }
        resp.setList(list);
        resp.setExpanded(true);
        mAdapter.notifyItemChanged(mCurPos);
    }

    @Override
    public void updateInfoList(List<DeliverInfoResp> list) {
        mList = list;
        updateData();
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public int getSubBillStatus() {
        return mSubBillStatus;
    }

    @Override
    public String getStartDate() {
        if (mDate == null) return null;
        Object tag = mDate.getTag(R.id.date_start);
        return tag instanceof Date ? CalendarUtils.toLocalDate(((Date) tag)) : null;
    }

    @Override
    public String getEndDate() {
        if (mDate == null) return null;
        Object tag = mDate.getTag(R.id.date_end);
        return tag instanceof Date ? CalendarUtils.toLocalDate(((Date) tag)) : null;
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void updateSelectDate() {
        if (mDate == null) return;
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    @Optional
    @OnClick(R.id.odi_date_btn)
    public void showDateDialog() {
        if (mDate == null)return;
        if (mDatePickerDialog == null) {
            long endTime = ((Date) mDate.getTag(R.id.date_end)).getTime();
            mDatePickerDialog = DatePickerDialog.newBuilder(this)
                    .setTitle("选择时间")
                    .setBeginTime(CalendarUtils.parse("20170101", Constants.UNSIGNED_YYYY_MM_DD).getTime())
                    .setEndTime(endTime)
                    .setSelectBeginTime(((Date) mDate.getTag(R.id.date_start)).getTime())
                    .setSelectEndTime(endTime)
                    .setCallback(new DatePickerDialog.SelectCallback() {
                        @Override
                        public void select(Date beginTime, Date endTime) {
                            if (CalendarUtils.getDateBefore(endTime, 31).getTime() > beginTime.getTime()) {
                                ToastUtils.showShort("开始日期至结束日期限制选择31天以内");
                                return;
                            }
                            mDate.setTag(R.id.date_start, beginTime);
                            mDate.setTag(R.id.date_end, endTime);
                            updateSelectDate();
                            mPresenter.start();
                        }
                    })
                    .create();
        }
        mDatePickerDialog.show();
    }

    private void updateData() {
        String searchContent = mSearchView.getSearchContent();
        if (CommonUtils.isEmpty(mList) || TextUtils.isEmpty(searchContent)) {
            mAdapter.setNewData(mList);
        } else {
            List<DeliverInfoResp> list = new ArrayList<>();
            for (DeliverInfoResp resp : mList) {
                if (resp.getProductName().contains(searchContent)) {
                    list.add(resp);
                }
            }
            mAdapter.setNewData(list);
        }
        if (CommonUtils.isEmpty(mAdapter.getData())) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTips("暂无数据");
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this).setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
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
}
