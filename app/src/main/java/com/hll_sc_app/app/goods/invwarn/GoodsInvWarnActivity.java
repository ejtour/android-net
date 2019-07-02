package com.hll_sc_app.app.goods.invwarn;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.goods.GoodsAddBatchResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
    private GoodsListAdapter mAdapter;
    private GoodsInvWarnPresenter mPresenter;
    private HouseSelectWindow mHouseSelectWindow;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_inv_warn);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsInvWarnPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreGoodsInvList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsInvList(true);
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
                bean.setEditFrom(GoodsBean.EDIT_FROM_TEMPLATE);
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_ADD, GoodsInvWarnActivity.this,
                    ImgUploadBlock.REQUEST_CODE_CHOOSE, bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void addSuccess(GoodsAddBatchResp resp) {
        finish();
    }

    @Override
    public void showHouseWindow(List<HouseBean> list) {
        if (CommonUtils.isEmpty(list)) {
            showToast("没有仓库数据");
            return;
        }
        if (mHouseSelectWindow == null) {
            mHouseSelectWindow = new HouseSelectWindow(this, list);
            mHouseSelectWindow.setListener(this::showSelectHouse);
        }
        mHouseSelectWindow.showAsDropDownFix(mRlToolbar, Gravity.NO_GRAVITY);
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
    public void showGoodsInvList(List<GoodsBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到相关商品库存数据");
        } else {
            mEmptyView.setTips("还没有商品库存数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.ll_house})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                break;
            case R.id.ll_house:
                mPresenter.queryHouseList(true);
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
                .setText(R.id.txt_stockWarnNum, CommonUtils.formatNum(item.getStockWarnNum()))
                .getView(R.id.img_imgUrl))).setImageURL(item.getImgUrl());
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }

        private void showInputDialog(SpecsBean specsBean, BaseQuickAdapter adapter, int position) {
            if (mContext instanceof Activity) {
                InputDialog.newBuilder((Activity) mContext)
                    .setCancelable(false)
                    .setTextTitle("输入" + specsBean.getSpecContent() + "平台价格")
                    .setHint("输入平台价格")
                    .setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    .setText(CommonUtils.formatNumber(specsBean.getProductPrice()))
                    .setTextWatcher((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
                        if (!GoodsSpecsAddActivity.PRODUCT_PRICE.matcher(s.toString()).find() && s.length() > 1) {
                            s.delete(s.length() - 1, s.length());
                            showToast("平台价格支持7位整数或小数点后两位");
                        }
                    })
                    .setButton((dialog, item) -> {
                        if (item == 1) {
                            if (TextUtils.isEmpty(dialog.getInputString())) {
                                showToast("输入平台价格不能为空");
                                return;
                            }
                            specsBean.setProductPrice(dialog.getInputString());
                            adapter.notifyItemChanged(position);
                        }
                        dialog.dismiss();
                    }, "取消", "确定")
                    .create().show();
            }
        }
    }
}
