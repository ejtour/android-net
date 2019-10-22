package com.hll_sc_app.app.goodsdemand.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.event.PurchaserSearchEvent;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class PurchaserSearchFragment extends BaseLazyFragment implements IPurchaserSearchContract.IPurchaserSearchView {
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "object0")
    int mType;
    @Autowired(name = "object1")
    String mSelectID;
    Unbinder unbinder;
    private PurchaserSearchAdapter mAdapter;
    private IPurchaserSearchContract.IPurchaserSearchPresenter mPresenter;
    private EmptyView mEmptyView;

    public static PurchaserSearchFragment newInstance(int type, String selectID) {
        Bundle args = new Bundle();
        PurchaserSearchFragment fragment = new PurchaserSearchFragment();
        args.putInt("object0", type);
        args.putString("object1", selectID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        mPresenter = PurchaserSearchPresenter.newInstance(mType);
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rootView = inflater.inflate(R.layout.layout_simple_refresh_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new PurchaserSearchAdapter();
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            NameValue item = mAdapter.getItem(position);
            if (item == null) return;
            Intent intent = new Intent();
            intent.putExtra("parcelable", item);
            intent.putExtra("object", mType);
            requireActivity().setResult(Activity.RESULT_OK, intent);
            requireActivity().finish();
        });
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Subscribe
    public void handleSearchEvent(PurchaserSearchEvent event) {
        setForceLoad(true);
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        initEmptyView();
        mEmptyView.setNetError();
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void setData(List<NameValue> list, boolean append) {
        NameValue nameValue = null;
        if (!TextUtils.isEmpty(mSelectID)) {
            for (NameValue value : list) {
                if (value.getValue().equals(mSelectID)) {
                    nameValue = value;
                }
            }
        }
        if (nameValue != null) mAdapter.select(nameValue);
        if (append) {
            mAdapter.addData(list);
        } else {
            if (list.size() == 0) {
                initEmptyView();
                mEmptyView.setTips(String.format("暂无%s", mType == 0 ? "合作客户" : "意向客户"));
            }
            mAdapter.setNewData(list);
        }
        mRefreshView.setEnableLoadMore(list.size() == 20);
    }

    @Override
    public String getSearchWords() {
        return ((PurchaserSearchActivity) requireActivity()).getSearchWords();
    }
}
