package com.hll_sc_app.app.report.customreceivequery.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 客户收货查询
 * */
@Route(path = RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE_DETAIL)
public class CustomReceiveDetailActivity extends BaseLoadActivity implements ICustomReceiveDetailContract.IView {
    @BindView(R.id.txt_no)
    TextView mTxtNo;
    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.txt_person)
    TextView mTxtPerson;
    @BindView(R.id.img_status)
    ImageView mImgStatus;
    @BindView(R.id.txt_type)
    TextView mTxtType;
    @BindView(R.id.txt_warehouse)
    TextView mTxtWarehouse;
    @BindView(R.id.txt_ly_warehouse)
    TextView mTxtLyWarehouse;
    @BindView(R.id.txt_mark)
    TextView mTxtMark;
    @BindView(R.id.sync_scroll_title)
    SyncHorizontalScrollView mScrollTitle;
    @BindView(R.id.sync_scroll_content)
    SyncHorizontalScrollView mScrollContent;
    @BindView(R.id.recyclerView)
    RecyclerView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_footer)
    TextView mTxtFooter;

    private Unbinder unbinder;
    private ICustomReceiveDetailContract.IPresent mPresent;
    private ReceiveAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activirty_report_custom_receive_detail);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = CustomReceiveDetailPresent.newInstance();
        mPresent.register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mAdapter = new ReceiveAdapter(null);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            //todo 跳入详情

        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh(false);
            }
        });


    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);

    }

    @Override
    public void querySuccess(List<CustomReceiveListResp.CustomReceiveBean> customReceiveBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(customReceiveBeans);
        } else {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("喔唷，居然是「 空 」的").create());
            mAdapter.setNewData(customReceiveBeans);
        }
        if (!CommonUtils.isEmpty(customReceiveBeans)) {
            mRefreshLayout.setEnableLoadMore(customReceiveBeans.size() == mPresent.getPageSize());
        } else {
            mRefreshLayout.setEnableLoadMore(false);
        }
    }


    private class ReceiveAdapter extends BaseQuickAdapter<CustomReceiveListResp.CustomReceiveBean, BaseViewHolder> {

        public ReceiveAdapter(@Nullable List<CustomReceiveListResp.CustomReceiveBean> data) {
            super(R.layout.list_item_query_custom_receive, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomReceiveListResp.CustomReceiveBean item) {
            helper.setText(R.id.txt_no, item.getNo())
                    .setText(R.id.txt_status, item.getStatus())
                    .setText(R.id.txt_type, "类型：" + item.getTypeName())
                    .setText(R.id.txt_count, "数量：" + item.getCount())
                    .setText(R.id.txt_money, "金额：¥" + CommonUtils.formatMoney(item.getMoney()));
        }
    }

}
