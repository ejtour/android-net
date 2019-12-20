package com.hll_sc_app.app.crm.daily;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.daily.edit.CrmDailyEditActivity;
import com.hll_sc_app.app.crm.daily.list.CrmDailyListActivity;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyEditReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.daily.CrmDailyHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

@Route(path = RouterConfig.CRM_DAILY)
public class CrmDailyFragment extends BaseLoadFragment implements ICrmDailyContract.ICrmDailyView {
    @BindView(R.id.fcd_bg)
    View mBg;
    @BindView(R.id.fcd_note)
    TextView mNote;
    @BindView(R.id.fcd_list_view)
    RecyclerView mListView;
    @BindView(R.id.fcd_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private ICrmDailyContract.ICrmDailyPresenter mPresenter;
    private CrmDailyAdapter mAdapter;
    private CrmDailyHeader mHeader;
    private DailyEditReq mReq;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crm_daily, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CrmDailyEditActivity.REQ_CODE && data != null)
                mReq = data.getParcelableExtra("parcelable");
            else mPresenter.start();
        }
    }

    private void initData() {
        mPresenter = CrmDailyPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        fitsStatusBar();
        mHeader = new CrmDailyHeader(requireContext());
        mHeader.setOnClickListener(this::note);
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> mPresenter.loadMore());
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mAdapter = new CrmDailyAdapter(true);
        mListView.setAdapter(mAdapter);
    }

    private void fitsStatusBar() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mBg.getLayoutParams()).height -= ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fcd_send)
    public void send() {
        CrmDailyListActivity.start(true);
    }

    @OnClick(R.id.fcd_receive)
    public void receive() {
        CrmDailyListActivity.start(false);
    }

    @Override
    public void setData(List<DailyBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                mNote.setVisibility(View.GONE);
                mHeader.refreshDate();
                mAdapter.setHeaderView(mHeader);
            } else {
                mAdapter.removeAllHeaderView();
                mNote.setVisibility(View.VISIBLE);
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick(R.id.fcd_note)
    public void note(View view) {
        CrmDailyEditActivity.start(requireActivity(), mReq);
    }
}
