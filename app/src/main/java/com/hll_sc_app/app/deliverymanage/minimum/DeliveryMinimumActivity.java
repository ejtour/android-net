package com.hll_sc_app.app.deliverymanage.minimum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.deliverymanage.minimum.detail.DeliveryMinimumDetailActivity;
import com.hll_sc_app.app.deliverymanage.minimum.search.DeliveryMinSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 起送金额列表
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
@Route(path = RouterConfig.DELIVERY_MINIMUM, extras = Constant.LOGIN_EXTRA)
public class DeliveryMinimumActivity extends BaseLoadActivity implements DeliveryMinimumContract.IDeliveryMinimumView {
    @BindView(R.id.adm_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.amd_search_view)
    SearchView mSearchView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String mPurchaserID;
    private String mPurchaserShopID;
    private int mIndex;
    private EmptyView mEmptyView;
    private MinimumListAdapter mAdapter;
    private DeliveryMinimumPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_minimum);
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryMinimumPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            String value = data.getStringExtra("value");
            mIndex = data.getIntExtra("index", 0);
            mPurchaserID = mIndex == 0 ? value : "";
            mPurchaserShopID = mIndex == 1 ? value : "";
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> DeliveryMinimumDetailActivity.start(this, null));
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                DeliveryMinSearchActivity.start(DeliveryMinimumActivity.this, searchContent, String.valueOf(mIndex));
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mPurchaserID = "";
                    mPurchaserShopID = "";
                    mIndex = 0;
                }
                mPresenter.start();
            }
        });
        mEmptyView = EmptyView.newBuilder(this)
                .setTipsTitle("您还没有设置起送金额哦").setTips("点击右上角新增添加").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mAdapter = new MinimumListAdapter();
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void showTipsDialog(DeliveryMinimumBean bean) {
        TipsDialog.newBuilder(this)
                .setTitle("删除起送金额")
                .setMessage("您确认要删除" + bean.getDivideName() + "吗？")
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        mPresenter.delDeliveryMinimum(bean);
                    }
                    SwipeItemLayout.closeAllItems(mRecyclerView);
                    dialog.dismiss();
                }, "取消", "确定")
                .create()
                .show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    @Override
    public void showDeliveryList(List<DeliveryMinimumBean> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }

    @Override
    public String getPurchaserID() {
        return mPurchaserID;
    }

    @Override
    public String getPurchaserShopID() {
        return mPurchaserShopID;
    }

    class MinimumListAdapter extends BaseQuickAdapter<DeliveryMinimumBean, BaseViewHolder> {
        MinimumListAdapter() {
            super(R.layout.item_delivery_minimum);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_del).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryMinimumBean item) {
            helper.setText(R.id.txt_settings, TextUtils.equals(item.getSettings(), "0") ? getString(R.string.area) :
                    "客户")
                    .setBackgroundRes(R.id.txt_settings, TextUtils.equals(item.getSettings(), "0") ?
                            R.drawable.bg_tag_primary_tine_solid : R.drawable.bg_tag_red_solid)
                    .setText(R.id.txt_divideName, item.getDivideName())
                    .setText(R.id.txt_areaNum, TextUtils.equals(item.getSettings(), "0") ? "包含" + item.getAreaNum() +
                            "地区" : "包含" + item.getAreaNum() + "门店")
                    .setText(R.id.txt_sendPrice,
                            "¥" + CommonUtils.formatNumber(item.getSendPrice()) + "起")
            ;
        }
    }

    private class OnItemChildClickListener implements BaseQuickAdapter.OnItemChildClickListener {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            DeliveryMinimumBean bean = (DeliveryMinimumBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            int id = view.getId();
            if (id == R.id.txt_del) {
                DeliveryMinimumActivity.this.showTipsDialog(bean);
            } else if (id == R.id.content) {
                DeliveryMinimumDetailActivity.start(DeliveryMinimumActivity.this, bean);
            }
        }
    }
}
