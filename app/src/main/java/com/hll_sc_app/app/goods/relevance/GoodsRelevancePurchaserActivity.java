package com.hll_sc_app.app.goods.relevance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.invwarn.HouseSelectWindow;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.GoodsRelevanceSearchEvent;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
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

/**
 * 第三方商品关联-采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
@Route(path = RouterConfig.GOODS_RELEVANCE_PURCHASER_LIST, extras = Constant.LOGIN_EXTRA)
public class GoodsRelevancePurchaserActivity extends BaseLoadActivity implements GoodsRelevancePurchaserContract.IGoodsRelevancePurchaserView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.txt_houseName)
    TextView mTxtResourceType;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private PurchaserListAdapter mAdapter;
    private GoodsRelevancePurchaserPresenter mPresenter;
    private HouseSelectWindow mResourceTypeSelectWindow;
    private EmptyView mEmptyView;
    private List<HouseBean> mListResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_relevance_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsRelevancePurchaserPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
        // 来源选择
        mListResource = new ArrayList<>();
        mListResource.add(new HouseBean("全部", null));
        mListResource.add(new HouseBean("哗啦啦供应链", "1"));
        mListResource.add(new HouseBean("天财商龙", "2"));
        mListResource.get(0).setSelect(true);
        showSelectResourceType(mListResource.get(0));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_GOODS_RELEVANCE);
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
        mEmptyView = EmptyView.newBuilder(this).setTips("还没有采购商数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new PurchaserListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showSelectResourceType(HouseBean resource) {
        mTxtResourceType.setText(resource.getHouseName());
        mTxtResourceType.setTag(resource.getId());
        mPresenter.queryPurchaserList(true);
    }

    @Subscribe
    public void onEvent(GoodsRelevanceSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public String getGroupName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getResourceType() {
        String resourceType = null;
        if (mTxtResourceType.getTag() != null) {
            resourceType = (String) mTxtResourceType.getTag();
        }
        return resourceType;
    }

    @Override
    public void showResourceTypeWindow() {
        if (mResourceTypeSelectWindow == null) {
            mResourceTypeSelectWindow = new HouseSelectWindow(this, mListResource);
            mResourceTypeSelectWindow.setListener(this::showSelectResourceType);
        }
        mResourceTypeSelectWindow.showAsDropDownFix(mRlToolbar, Gravity.NO_GRAVITY);
    }

    @Override
    public void showPurchaserList(List<PurchaserBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到相关采购商数据");
        } else {
            mEmptyView.setTips("还没有采购商数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        if (total != 0) {
            mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
        } else {
            mRefreshLayout.setEnableLoadMore(list.size() == GoodsListReq.PAGE_SIZE);
        }
    }

    @OnClick({R.id.img_close, R.id.ll_house})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.ll_house:
                showResourceTypeWindow();
                break;
            default:
                break;
        }
    }

    class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter() {
            super(R.layout.item_goods_relevance_purchaser);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_purchaserName, item.getPurchaserName())
                .setText(R.id.txt_linkMan,
                    "联系人：" + getString(item.getLinkman()) + " / " + getString(PhoneUtil.formatPhoneNum(item.getGroupPhone())))
                .setText(R.id.txt_relationProductNum,
                    getSpannableString("关联品项：" + CommonUtils.formatNumber(item.getRelationProductNum())))
                .setText(R.id.txt_unRelationProductNum,
                    getSpannableString("未关联品项：" + CommonUtils.formatNumber(item.getUnRelationProductNum())));
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }

        private SpannableString getSpannableString(String str) {
            SpannableString spannableString = new SpannableString(str);
            if (str.contains("：")) {
                spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,
                    R.color.base_colorPrimary)), str.indexOf("："), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableString;
        }
    }
}
