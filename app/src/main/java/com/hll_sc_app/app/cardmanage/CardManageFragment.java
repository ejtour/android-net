package com.hll_sc_app.app.cardmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cardmanage.add.SelectPurchaserListActivity;
import com.hll_sc_app.app.cardmanage.detail.CardManageDetailActivity;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cardmanage.CardManageBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CardManageFragment extends BaseLazyFragment implements ICardManageContract.IFragment, Observer {

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    private Unbinder unbinder;
    private ICardManageContract.IPresent mPresent;
    private int cardStatus;

    private CardManageAdapter mAdapter;

    /***
     * 卡状态 1-启用 2-冻结 3-注销
     * @param cardStatus
     * @return
     */
    static CardManageFragment newInstance(int cardStatus) {
        Bundle args = new Bundle();
        args.putInt("status", cardStatus);
        CardManageFragment fragment = new CardManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardStatus = getArguments().getInt("status");
        mPresent = CardManagePresent.newInstance();
        mPresent.register(this);

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_manage_list, container, false);
    }

    @Override
    protected void initData() {
        mPresent.queryList(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.queryMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
        mAdapter = new CardManageAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view1, position) -> {
            CardManageDetailActivity.start(mAdapter.getItem(position));
        });
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void querySuccess(List<CardManageBean> cardManageBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(cardManageBeans);
        } else {
            if (cardStatus == 1 && TextUtils.isEmpty(getCardManageActivity().getSearchText())) {//不是搜索状态下
                mAdapter.setEmptyView(EmptyView.newBuilder(getActivity())
                        .setTipsTitle(getEmptyText())
                        .setTips("选择合作采购商创建一个储值卡吧")
                        .setTipsButton("新建一个储值卡")
                        .setImage(R.drawable.ic_dialog_failure)
                        .setOnClickListener(new EmptyView.OnActionClickListener() {
                            @Override
                            public void retry() {
                                //no-op
                            }

                            @Override
                            public void action() {
                                SelectPurchaserListActivity.start(RouterConfig.ACTIVITY_CARD_MANAGE_LIST);
                            }
                        }).create());
            } else {
                mAdapter.setEmptyView(EmptyView.newBuilder(getActivity())
                        .setImage(R.drawable.ic_dialog_failure)
                        .setTipsTitle(getEmptyText())
                        .create());
            }
            mAdapter.setNewData(cardManageBeans);
        }

        if (!CommonUtils.isEmpty(cardManageBeans)) {
            mRefreshLayout.setEnableLoadMore(cardManageBeans.size() == mPresent.getPageSize());
        }
    }

    @Override
    public int getCardStatus() {
        return cardStatus;
    }

    @Override
    public ICardManageContract.IView getCardManageActivity() {
        return (ICardManageContract.IView) getActivity();
    }

    private String getEmptyText() {
        if (TextUtils.isEmpty(getCardManageActivity().getSearchText())) {
            return cardStatus == 1 ? "您没有任何启用中的储值卡噢" : cardStatus == 2 ? "您没有任何已冻结的储值卡噢" : "您没有任何已注销的储值卡噢";
        } else {
            return "没有搜索到符合该卡号的储值卡噢";
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            if (TextUtils.equals(arg.toString(), "refresh")) {
                mPresent.refresh();
            }
        }
    }

    private class CardManageAdapter extends BaseQuickAdapter<CardManageBean, BaseViewHolder> {
        public CardManageAdapter(@Nullable List<CardManageBean> data) {
            super(R.layout.list_item_card_manage, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CardManageBean item) {
            SpannableString balance = new SpannableString("¥ " + CommonUtils.formatMoney(item.getBalance()));
            balance.setSpan(new RelativeSizeSpan(0.7f), balance.length() - 2, balance.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((GlideImageView) helper.setText(R.id.txt_purchaser_name, item.getPurchaserName())
                    .setText(R.id.txt_card_num, item.getCardNo())
                    .setText(R.id.txt_balance, balance)
                    .getView(R.id.img_purchaser_url))
                    .setImageURL(item.getPurchaserImgUrl());
        }
    }
}
