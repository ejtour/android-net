package com.hll_sc_app.app.goods.invwarn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsInvWarnSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.GoodsInvWarnSearchEvent;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓商品库存预警
 *
 * @author zhuyingsong
 * @date 2019/7/2
 */
@Route(path = RouterConfig.GOODS_INVENTORY_WARNING, extras = Constant.LOGIN_EXTRA)
public class GoodsInvWarnActivity extends BaseLoadActivity implements GoodsInvWarnContract.IGoodsInvWarnView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.txt_houseName)
    TextView mTxtHouseName;
    @BindView(R.id.txt_save)
    TextView mTxtSave;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_save)
    LinearLayout mLlSave;
    private GoodsListAdapter mAdapter;
    private GoodsInvWarnPresenter mPresenter;
    private TopSingleSelectWindow<HouseBean> mHouseSelectWindow;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_inv_warn);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsInvWarnPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
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
                SearchActivity.start(searchContent, GoodsInvWarnSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mPresenter.queryGoodsInvList(true);
                } else {
                    mPresenter.searchGoodsInvList(true);
                }
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mSearchView.isSearchStatus()) {
                    mPresenter.searchMoreGoodsInvList();
                } else {
                    mPresenter.queryMoreGoodsInvList();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mSearchView.isSearchStatus()) {
                    mPresenter.searchGoodsInvList(false);
                } else {
                    mPresenter.queryGoodsInvList(false);
                }
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("还没有商品库存数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new GoodsListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GoodsBean bean = (GoodsBean) adapter.getItem(position);
            if (bean != null) {
                showInputDialog(bean, adapter, position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showInputDialog(GoodsBean bean, BaseQuickAdapter adapter, int position) {
        String stockWarnNum = CommonUtils.formatNumber(bean.getStockWarnNum());
        InputDialog.newBuilder(this)
                .setCancelable(false)
                .setTextTitle("输入" + bean.getProductName() + "预警值")
                .setHint("输入预警值")
                .setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .setText(TextUtils.equals(stockWarnNum, "0") ? "" : stockWarnNum)
                .setTextWatcher((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
                    if (!GoodsSpecsAddActivity.PRODUCT_PRICE.matcher(s.toString()).find() && s.length() > 1) {
                        s.delete(s.length() - 1, s.length());
                        showToast("预警值支持7位整数或小数点后两位");
                    }
                })
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        if (TextUtils.isEmpty(dialog.getInputString())) {
                            showToast("输入预警值不能为空");
                            return;
                        }
                        bean.setStockWarnNum(CommonUtils.getDouble(dialog.getInputString()));
                        adapter.notifyItemChanged(position);
                    }
                    dialog.dismiss();
                }, "取消", "确定")
                .create().show();
    }

    @Subscribe
    public void onEvent(GoodsInvWarnSearchEvent event) {
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
    public void saveSuccess() {
        showToast("保存成功");
        finish();
    }

    @Override
    public void showHouseWindow(List<HouseBean> list) {
        if (CommonUtils.isEmpty(list)) {
            showToast("没有仓库数据");
            return;
        }
        if (mHouseSelectWindow == null) {
            mHouseSelectWindow = new TopSingleSelectWindow<>(this, HouseBean::getHouseName);
            mHouseSelectWindow.refreshList(list);
            mHouseSelectWindow.setListener(this::showSelectHouse);
        }
        mHouseSelectWindow.showAsDropDownFix(mRlToolbar);
    }

    @Override
    public void showSelectHouse(HouseBean houseBean) {
        mTxtHouseName.setText(houseBean.getHouseName());
        mTxtHouseName.setTag(houseBean.getId());
        mPresenter.queryGoodsInvList(true);
    }

    @Override
    public String getHouseId() {
        String houseId = null;
        if (mTxtHouseName.getTag() != null) {
            houseId = (String) mTxtHouseName.getTag();
        }
        return houseId;
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showGoodsInvList(List<GoodsBean> list, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到相关商品库存数据");
        } else {
            mEmptyView.setTips("还没有商品库存数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        if (total != 0) {
            mLlSave.setVisibility(View.VISIBLE);
            mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
        } else {
            mLlSave.setVisibility(View.GONE);
            mRefreshLayout.setEnableLoadMore(list != null && list.size() == GoodsListReq.PAGE_SIZE);
        }
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
        Utils.bindEmail(this, email -> mPresenter.export(email));
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.ll_house, R.id.txt_export})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                mPresenter.setGoodsInvWarnValue(mAdapter.getData());
                break;
            case R.id.ll_house:
                mPresenter.queryHouseList(true);
                break;
            case R.id.txt_export:
                mPresenter.export(null);
                break;
            default:
                break;
        }
    }

    class GoodsListAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        GoodsListAdapter() {
            super(R.layout.item_goods_inv_warn);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            ((GlideImageView) (helper.setText(R.id.txt_productName, item.getProductName())
                    .setText(R.id.txt_specsSize, "规格：" + getString(item.getSaleSpecNum()))
                    .setText(R.id.txt_standardUnit, "标准单位：" + getString(item.getStandardUnitName()))
                    .setText(R.id.txt_cargoOwnerName, "货主：" + getString(item.getCargoOwnerName()))
                    .setText(R.id.txt_usableStock, "可用库存：" + CommonUtils.formatNumber(item.getUsableStock()))
                    .setText(R.id.txt_stockWarnNum_unit, getString(item.getStandardUnitName()))
                    .setText(R.id.txt_stockWarnNum, CommonUtils.formatNumber(item.getStockWarnNum()))
                    .addOnClickListener(R.id.txt_stockWarnNum)
                    .getView(R.id.img_imgUrl))).setImageURL(item.getImgUrl());
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }

}
