package com.hll_sc_app.app.stockmanage.stocklogquery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.StockLogSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.bean.event.StockManageEvent;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.stockmanage.BusinessTypeBean;
import com.hll_sc_app.bean.stockmanage.StockLogResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
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

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_DATE_TIME;
import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_LOCAL_DATE;

/**
 * 库存日志查询
 */
@Route(path = RouterConfig.ACTIVITY_STOCK_LOG_QUERY)
public class StockLogQueryActivity extends BaseLoadActivity implements IStockLogQueryContract.IView {

    @BindView(R.id.sync_scroll_title)
    SyncHorizontalScrollView mSyncTitle;
    @BindView(R.id.sync_scroll_content)
    SyncHorizontalScrollView mSyncContent;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_stock_name)
    TextView mTxtStockName;
    @BindView(R.id.txt_time)
    TextView mTxtTimeSpan;
    @BindView(R.id.txt_type)
    TextView mTxtBusinessType;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.txt_add)
    ImageView mImageAdd;
    @BindView(R.id.ll_filter)
    LinearLayout mLlFilter;
    private SingleSelectionDialog mHourseSelectDialog;
    private SingleSelectionDialog mBusinessSelectDialog;
    private ContextOptionsWindow mMenuWindow;
    private DateSelectWindow mDateSelectWindow;
    private Unbinder unbinder;
    private LogAdpater mAdapter;
    private IStockLogQueryContract.IPresent mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_log_query);
        unbinder = ButterKnife.bind(this);
        mPresent = StockLogQueryPresent.newInstance();
        mPresent.register(this);
        EventBus.getDefault().register(this);
        initView();
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    private void initView() {
        mSyncContent.setLinkageViews(mSyncTitle);
        mSyncTitle.setLinkageViews(mSyncContent);
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, StockLogSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.refresh();
            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.fetchMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new LogAdpater(null);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Subscribe
    public void subScribe(StockManageEvent stockLogEvent) {
        if (stockLogEvent != null && stockLogEvent.getType() == StockManageEvent.TYPE_LOG) {
            mSearchView.showSearchContent(!TextUtils.isEmpty(stockLogEvent.getContent()), stockLogEvent.getContent());
        }
    }

    @OnClick({R.id.ll_stock_name, R.id.ll_time, R.id.ll_type, R.id.txt_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_stock_name:
                if (mHourseSelectDialog != null) {
                    mHourseSelectDialog.show();
                }
                break;
            case R.id.ll_time:
                if (mDateSelectWindow == null) {
                    mDateSelectWindow = new DateSelectWindow(this);
                    mDateSelectWindow.setForbiddenStartBeforeCurrent(false);
                    mDateSelectWindow.setSelectListener((startDate, endDate) -> {
                        mTxtTimeSpan.setText(CalendarUtils.getDateFormatString(startDate, FORMAT_LOCAL_DATE, FORMAT_DATE_TIME) + "-" + CalendarUtils.getDateFormatString(endDate, FORMAT_LOCAL_DATE, FORMAT_DATE_TIME));
                        mTxtTimeSpan.setTag(R.id.base_tag_1, startDate);
                        mTxtTimeSpan.setTag(R.id.base_tag_2, endDate);
                        mPresent.refresh();
                    });
                }
                mDateSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_type:
                if (mBusinessSelectDialog != null) {
                    mBusinessSelectDialog.show();
                }
                break;
            case R.id.txt_add:
                if (mMenuWindow == null) {
                    mMenuWindow = new ContextOptionsWindow(this);
                    List<OptionsBean> optionsBeans = new ArrayList<>();
                    optionsBeans.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_STOCK_LOG_EXPORT));
                    mMenuWindow.refreshList(optionsBeans);
                    mMenuWindow.setListener((adapter, view1, position) -> {
                        mPresent.export("");
                    });
                }
                mMenuWindow.showAsDropDownFix(mImageAdd, Gravity.END);
                break;
            default:
                break;
        }
    }

    @Override
    public void queryHouseListSuccess(List<HouseBean> houseBeans) {
        if (mHourseSelectDialog == null) {
            houseBeans.add(0, new HouseBean("全部仓库", ""));
            mHourseSelectDialog = SingleSelectionDialog.newBuilder(this, HouseBean::getHouseName)
                    .setOnSelectListener(houseBean -> {
                        mTxtStockName.setTag(houseBean);
                        mTxtStockName.setText(houseBean.getHouseName());
                        mPresent.refresh();
                    })
                    .setTitleText("选择仓库")
                    .refreshList(houseBeans)
                    .create();
        }
    }

    @Override
    public void queryBusinessTypeSuccess(List<BusinessTypeBean> businessTypeBeans) {
        if (mBusinessSelectDialog == null) {
            mBusinessSelectDialog = SingleSelectionDialog.newBuilder(this, BusinessTypeBean::getBusinessName)
                    .setOnSelectListener(businessTypeBean -> {
                        mTxtBusinessType.setTag(businessTypeBean);
                        mTxtBusinessType.setText(businessTypeBean.getBusinessName());
                        mPresent.refresh();
                    })
                    .setTitleText("选择交易类型")
                    .refreshList(businessTypeBeans)
                    .create();
        }
    }

    @Override
    public void fetchStockLogsSuccess(StockLogResp resp, boolean isMore) {
        if (isMore && resp.getList().size() > 0) {
            mAdapter.addData(resp.getList());
        } else if (!isMore) {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("当前条件下没有数据").create());
            mAdapter.setNewData(resp.getList());
        }
    }

    @Override
    public String getHouseID() {
        if (mTxtStockName.getTag() != null) {
            return ((HouseBean) mTxtStockName.getTag()).getId();
        }
        return "";
    }

    @Override
    public String getCreateTimeStart() {
        if (mTxtTimeSpan.getTag(R.id.base_tag_1) != null) {
            return mTxtTimeSpan.getTag(R.id.base_tag_1).toString();
        }
        return "";
    }

    @Override
    public String getCreateTimeEnd() {
        if (mTxtTimeSpan.getTag(R.id.base_tag_2) != null) {
            return mTxtTimeSpan.getTag(R.id.base_tag_2).toString();
        }
        return "";
    }

    @Override
    public String getBusinessType() {
        if (mTxtBusinessType.getTag() != null) {
            return ((BusinessTypeBean) mTxtBusinessType.getTag()).getBusinessType();
        }
        return "";
    }

    @Override
    public String getSearchKey() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    private class LogAdpater extends BaseQuickAdapter<StockLogResp.StockLog, BaseViewHolder> {
        public LogAdpater(@Nullable List<StockLogResp.StockLog> data) {
            super(R.layout.list_item_stock_log_query, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, StockLogResp.StockLog item) {
            helper.setText(R.id.txt_name, item.getProductName())
                    .setText(R.id.txt_code, item.getProductCode())
                    .setText(R.id.txt_id, item.getHouseID())
                    .setText(R.id.txt_type, item.getBusinessTypeName())
                    .setText(R.id.txt_number, CommonUtils.formatNumber(item.getStockChange()))
                    .setText(R.id.txt_time, item.getCreateTime())
                    .setText(R.id.txt_no, item.getBusinessNo())
                    .setText(R.id.txt_source, getSource(item.getSource()));

            helper.getView(R.id.ll_item).setBackgroundColor(Color.parseColor(helper.getLayoutPosition() % 2 == 0 ? "#ffffff" : "#FAFAFA"));
        }


        private String getSource(int source) {
            switch (source) {
                case 0:
                    return "补位";
                case 1:
                    return "供应链";
                case 2:
                    return "商城";
                case 3:
                    return "天财商龙";
                default:
                    return "";
            }
        }
    }
}
