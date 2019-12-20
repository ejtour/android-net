package com.hll_sc_app.app.stockmanage.purchaserorder.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchTitleBar;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 采购单详情查询
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.STOCK_PURCHASER_ORDER_SEARCH)
public class PurchaserOrderSearchActivity extends BaseLoadActivity implements PurchaserOrderSearchContract.IPurchaserOrderSearchView {
    private static final int REQ_CODE = 0x144;
    @BindView(R.id.pos_title_bar)
    SearchTitleBar mTitleBar;
    @BindView(R.id.pos_list_view)
    RecyclerView mListView;
    @BindView(R.id.pos_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private PurchaserOrderSearchAdapter mAdapter;
    private PurchaserOrderSearchPresenter mPresenter;
    private List<PurchaserOrderSearchBean> mSelectedList = new ArrayList<>();

    public static void start(Activity context) {
        RouterUtil.goToActivity(RouterConfig.STOCK_PURCHASER_ORDER_SEARCH, context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_stock_purchaser_order_search);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = PurchaserOrderSearchPresenter.newInstance();
        mPresenter.register(this);
        mTitleBar.subscribe(s -> mPresenter.start());
    }

    private void initView() {
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new PurchaserOrderSearchAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserOrderSearchBean item = (PurchaserOrderSearchBean) adapter.getItem(position);
            if (item == null) return;
            if (mSelectedList.contains(item)) {
                mSelectedList.remove(item);
            } else {
                mSelectedList.add(item);
            }
            mAdapter.notifyItemChanged(position);
        });
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setImage(R.drawable.ic_empty_group_view).setTips("请输入供应商的名称").create());
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mListView.setAdapter(mAdapter);
        mTitleBar.setOnSearchListener(() -> {
            List<String> ids = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (PurchaserOrderSearchBean bean : mSelectedList) {
                ids.add(bean.getSupplierID());
                names.add(bean.getSupplierName());
            }
            if (!CommonUtils.isEmpty(ids)) {
                Intent intent = new Intent();
                intent.putExtra("ids", TextUtils.join(",", ids));
                intent.putExtra("names", TextUtils.join(",", names));
                setResult(Constants.SEARCH_RESULT_CODE, intent);
            }
            onBackPressed();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> mPresenter.loadMore());
    }

    @Override
    public void setData(List<PurchaserOrderSearchBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public String getSearchKey() {
        return mTitleBar.getSearchContent();
    }

    class PurchaserOrderSearchAdapter extends BaseQuickAdapter<PurchaserOrderSearchBean, BaseViewHolder> {

        PurchaserOrderSearchAdapter() {
            super(R.layout.item_stock_purchaser_order_search);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserOrderSearchBean bean) {
            TextView view = (TextView) helper.itemView;
            view.setText(bean.getSupplierName());
            view.setSelected(mSelectedList.contains(bean));
        }
    }
}
