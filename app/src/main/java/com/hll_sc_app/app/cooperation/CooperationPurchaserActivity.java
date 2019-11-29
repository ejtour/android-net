package com.hll_sc_app.app.cooperation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_LIST, extras = Constant.LOGIN_EXTRA)
public class CooperationPurchaserActivity extends BaseLoadActivity implements CooperationPurchaserContract.IGoodsRelevancePurchaserView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    private EmptyView mEmptyView;
    private CooperationPurchaserPresenter mPresenter;
    private PurchaserListAdapter mAdapter;
    private ContextOptionsWindow mOptionsWindow;
    private TextView mTxtGroupTotal;
    private TextView mTitleGroupTotal;
    private TextView mTxtShopTotal;
    private ContextOptionsWindow mTitleOptionWindow;
    private SwipeItemLayout.OnSwipeItemTouchListener swipeItemTouchListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationPurchaserPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CooperationPurchaserActivity.this,
                        searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryPurchaserList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserList(false);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("还没有合作采购商数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        View headView = LayoutInflater.from(this).inflate(R.layout.view_cooperation_purchaser_list_title,
            mRecyclerView, false);
        mTxtGroupTotal = headView.findViewById(R.id.txt_groupTotal);
        mTitleGroupTotal = headView.findViewById(R.id.txt_title_group_total);
        mTxtShopTotal = headView.findViewById(R.id.txt_shopTotal);
        mAdapter = new PurchaserListAdapter();
        mAdapter.addHeaderView(headView);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = mAdapter.getItem(position);
            if (bean != null) {
                if (view.getId() == R.id.txt_del) {
                    showDelTipsDialog(bean);
                } else if (view.getId() == R.id.content) {
                    RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_DETAIL, bean.getPurchaserID());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        swipeItemTouchListener = new SwipeItemLayout.OnSwipeItemTouchListener(this);
    }

    /**
     * 删除合作提示框
     *
     * @param bean 采购商
     */
    private void showDelTipsDialog(PurchaserBean bean) {
        TipsDialog.newBuilder(this)
            .setTitle("解除合作")
            .setMessage("确定要解除合作采购商【" + bean.getPurchaserName() + "】嘛？")
            .setButton((dialog, item) -> {
                if (item == 1) {
                    mPresenter.delCooperationPurchaser(bean.getPurchaserID());
                } else {
                    SwipeItemLayout.closeAllItems(mRecyclerView);
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create().show();
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
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showPurchaserList(CooperationPurchaserResp resp, boolean append) {
        mTitleGroupTotal.setText(getCooperationActive() == 0 ? "您已合作 " : "您已停止合作 ");
        mTxtGroupTotal.setText(CommonUtils.formatNumber(resp.getGroupTotal()));
        mTxtShopTotal.setText(CommonUtils.formatNumber(resp.getShopTotal()));
        List<PurchaserBean> list = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (getCooperationActive() == 0) {
                mRecyclerView.addOnItemTouchListener(swipeItemTouchListener);
            } else {
                mRecyclerView.removeOnItemTouchListener(swipeItemTouchListener);
            }
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到合作采购商数据");
        } else {
            mEmptyView.setTips("还没有合作采购商数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == GoodsListReq.PAGE_SIZE);
    }

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
        Utils.bindEmail(this, email -> mPresenter.exportPurchaser(email));
    }

    @OnClick({R.id.img_close, R.id.txt_options, R.id.txt_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_options:
                if (UserConfig.crm()) {
                    RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_ADD);
                } else showOptionsWindow(view);
                break;
            case R.id.txt_title:
                if (mTitleOptionWindow == null) {
                    mTitleOptionWindow = new ContextOptionsWindow(this);
                    List<OptionsBean> optionsBeans = new ArrayList<>();
                    optionsBeans.add(new OptionsBean(OptionType.OPTION_COOPER_PURCHASER));
                    optionsBeans.add(new OptionsBean(OptionType.OPTION_STOP_COOPER_PURCHASER));
                    int padh = UIUtils.dip2px(10);
                    mTitleOptionWindow.setListPadding(padh,0,padh,0);
                    mTitleOptionWindow
                            .refreshList(optionsBeans)
                            .setItemGravity(Gravity.CENTER)
                            .setListener((adapter, view1, position) -> {
                                mTitleOptionWindow.dismiss();
                                mTxtTitle.setTag(position);
                                OptionsBean item = (OptionsBean) adapter.getItem(position);
                                if (item == null) return;
                                mTxtTitle.setText(item.getLabel());
                                mPresenter.queryPurchaserList(true);
                            });
                }
                mTitleOptionWindow.showAsDropDownFix(mRlToolbar, Gravity.CENTER_HORIZONTAL);
                break;
            default:
                break;
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_cooperation_add, OptionType.OPTION_COOPERATION_ADD));
            list.add(new OptionsBean(R.drawable.ic_cooperation_receive, OptionType.OPTION_COOPERATION_RECEIVE));
            list.add(new OptionsBean(R.drawable.ic_cooperation_send, OptionType.OPTION_COOPERATION_SEND));
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_COOPERATION_EXPORT));
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_ADD)) {
            RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_ADD);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_RECEIVE)) {
            // 我收到的申请
            RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_APPLICATION);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_SEND)) {
            // 我发出的申请
            RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_MY_APPLICATION);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_EXPORT)) {
            mPresenter.exportPurchaser(null);
        }
        mOptionsWindow.dismiss();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    public static class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {
        private boolean mAdd;

        PurchaserListAdapter() {
            super(R.layout.item_cooperation_purchaser);
        }

        public PurchaserListAdapter(boolean add) {
            super(R.layout.item_cooperation_purchaser);
            this.mAdd = add;
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_del).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_purchaserName, item.getPurchaserName())
                .setText(R.id.txt_linkMan,
                    getString(item.getLinkman()) + " / " + getString(PhoneUtil.formatPhoneNum(item.getMobile())))
                .setText(R.id.txt_shopCount, getShopCountString(item))
                .setGone(R.id.txt_shopCount, !mAdd)
                .setGone(R.id.txt_newShopNum, CommonUtils.getDouble(item.getNewShopNum()) != 0);
            GlideImageView imageView = helper.getView(R.id.img_logoUrl);
            if (TextUtils.equals(item.getGroupActiveLabel(), "1")) {
                // 禁用
                imageView.setDisableImageUrl(item.getLogoUrl(), GlideImageView.GROUP_BLOCK_UP);
                helper.setTextColor(R.id.txt_purchaserName, Color.parseColor("#999999"));
            } else if (TextUtils.equals(item.getGroupActiveLabel(), "2")) {
                // 注销
                imageView.setDisableImageUrl(item.getLogoUrl(), GlideImageView.GROUP_LOG_OUT);
                helper.setTextColor(R.id.txt_purchaserName, Color.parseColor("#999999"));
            } else {
                imageView.setImageURL(item.getLogoUrl());
                helper.setTextColor(R.id.txt_purchaserName, Color.parseColor("#222222"));
            }
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }

        private String getShopCountString(PurchaserBean item) {
            String content = null;
            if (CommonUtils.getDouble(item.getShopCount()) != 0) {
                content = "合作" + CommonUtils.formatNumber(item.getShopCount()) + "个门店";
            } else {
                if (TextUtils.equals(item.getResourceType(), "1")) {
                    // 哗啦啦供应链
                    content = "哗啦啦供应链";
                } else if (TextUtils.equals(item.getResourceType(), "2")) {
                    // 天财供应链
                    content = "天财供应链";
                } else if (TextUtils.equals(item.getResourceType(), "0")) {
                    // 二十二城
                    content = "二十二城";
                }
            }
            return content;
        }
    }


    @Override
    public int getCooperationActive() {
        Object o = mTxtTitle.getTag();
        if (o == null) {
            return 0;
        }
        return Integer.parseInt(o.toString());
    }
}
