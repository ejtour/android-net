package com.hll_sc_app.app.select.group.goodsassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/26
 */
@Route(path = RouterConfig.SELECT_GROUP_GOODS_ASSIGN)
public class SelectGroupActivity extends BaseLoadActivity implements ISelectGroupContract.ISelectGroupView {
    @BindView(R.id.srl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.srl_search_view)
    SearchView mSearchView;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private ISelectGroupContract.ISelectGroupPresenter mPresenter;
    private Adapter mAdapter;
    @Autowired(name = "parcelable", required = true)
    GoodsAssignBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_refresh_list);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = SelectGroupPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("选择合作采购商");
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1)));
        mAdapter = new Adapter();
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有合作采购商").create());
        mListView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectGroupActivity.this, searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
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
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<GoodsAssignBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    class Adapter extends BaseQuickAdapter<GoodsAssignBean, BaseViewHolder> {
        Adapter() {
            super(R.layout.item_text_left_on_pri_off_666_clickable);
            setOnItemClickListener((adapter, view, position) -> {
                GoodsAssignBean bean = (GoodsAssignBean) adapter.getItem(position);
                if (bean != null) {
                    if (mBean != null) {
                        bean.setId(mBean.getId());
                        bean.setAssignType(mBean.getAssignType());
                        bean.setProductList(mBean.getProductList());
                        bean.setProductNum(mBean.getProductNum());
                        if (TextUtils.equals(bean.getPurchaserID(), mBean.getPurchaserID())) {
                            bean.setPurchaserShopIDs(mBean.getPurchaserShopIDs());
                        }
                    }
                    RouterUtil.goToActivity(RouterConfig.SELECT_SHOP_GOODS_ASSIGN, bean);
                }
            });
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsAssignBean bean) {
            TextView textView = (TextView) helper.itemView;
            textView.setText(bean.getPurchaserName());
            textView.setSelected(mBean != null && TextUtils.equals(mBean.getPurchaserID(), bean.getPurchaserID()));
        }
    }
}
