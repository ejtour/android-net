package com.hll_sc_app.app.select.shop.goodsassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/26
 */
@Route(path = RouterConfig.SELECT_SHOP_GOODS_ASSIGN)
public class SelectShopActivity extends BaseLoadActivity implements ISelectShopContract.ISelectShopView {
    private static final int REQ_CODE = 0x418;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @BindView(R.id.asl_search_view)
    SearchView mSearchView;
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @Autowired(name = "parcelable", required = true)
    GoodsAssignBean mBean;
    private Adapter mAdapter;
    private ISelectShopContract.ISelectShopPresenter mPresenter;
    private Set<String> mIdSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
        mPresenter = SelectShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initData() {
        mIdSet = new LinkedHashSet<>();
        if (!TextUtils.isEmpty(mBean.getPurchaserShopIDs())) {
            Collections.addAll(mIdSet, mBean.getPurchaserShopIDs().split(","));
        }
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mBean.getPurchaserName());
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mTitleBar.setRightText("确定");
        mTitleBar.setRightBtnClick(this::confirm);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee)
                , UIUtils.dip2px(1)));
        mAdapter = new Adapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserShopBean bean = (PurchaserShopBean) adapter.getItem(position);
            if (bean == null) return;
            if (TextUtils.isEmpty(bean.getShopID())) {
                selectAll(bean);
            } else {
                select(bean, !bean.isSelect());
                mAdapter.notifyItemChanged(position);
                checkSelectAll();
            }
        });
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有合作客户门店数据").create());
        mListView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectShopActivity.this, searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
    }

    private void selectAll(PurchaserShopBean shopBean) {
        List<PurchaserShopBean> list = mAdapter.getData();
        for (PurchaserShopBean bean : list) {
            if (TextUtils.isEmpty(bean.getShopID())) continue;
            select(bean, !shopBean.isSelect());
        }
        shopBean.setSelect(!shopBean.isSelect());
        mAdapter.notifyDataSetChanged();
    }

    private void select(PurchaserShopBean bean, boolean select) {
        bean.setSelect(select);
        if (bean.isSelect()) {
            mIdSet.add(bean.getShopID());
        } else {
            mIdSet.remove(bean.getShopID());
        }
    }

    /**
     * 判断是否设置全选
     */
    private void checkSelectAll() {
        if (CommonUtils.isEmpty(mAdapter.getData())) return;
        boolean selectAll = true;
        for (PurchaserShopBean bean : mAdapter.getData()) {
            if (TextUtils.isEmpty(bean.getShopID())) {
                continue;
            }
            if (!bean.isSelect()) {
                selectAll = false;
                break;
            }
        }
        mAdapter.getData().get(0).setSelect(selectAll);
        mAdapter.notifyItemChanged(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                mBean = data.getParcelableExtra("parcelable");
            } else {
                mBean.setProductList(null);
                mBean.setProductNum(0);
            }
        }
    }

    void confirm(View view) {
        if (CommonUtils.isEmpty(mIdSet)) {
            showToast("您还没有选择门店");
            return;
        }
        mBean.setPurchaserShopIDs(TextUtils.join(",", mIdSet));
        ARouter.getInstance()
                .build(RouterConfig.GOODS_ASSIGN_DETAIL)
                .withParcelable("parcelable", mBean)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .setProvider(new LoginInterceptor())
                .navigation(this, REQ_CODE);
    }

    /**
     * 退出提示对话框
     */
    private void showTipsDialog() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确认要离开么")
                .setMessage("您已经填写了部分数据，离开会\n丢失当前已填写的数据")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    if (item == 0) {
                        finish();
                    }
                    dialog.dismiss();
                }, "确认离开", "我再想想").create().show();
    }

    @Override
    public void onBackPressed() {
        String shopId = mBean.getPurchaserShopIDs();
        if (TextUtils.isEmpty(shopId)) {
            finish();
            return;
        }
        showTipsDialog();
    }

    @Override
    public void setData(List<PurchaserShopBean> list) {
        preProcess(list);
        mAdapter.setNewData(list);
        checkSelectAll();
    }

    private void preProcess(List<PurchaserShopBean> list) {
        if (!CommonUtils.isEmpty(list)) {

            if (!CommonUtils.isEmpty(mIdSet)) {
                for (PurchaserShopBean bean : list) {
                    bean.setSelect(mIdSet.contains(bean.getShopID()));
                }
            }
        }
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getPurchaserID() {
        return mBean.getPurchaserID();
    }

    static class Adapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        Adapter() {
            super(R.layout.item_text_left_on_pri_off_666_cirlce_checkable);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean bean) {
            TextView textView = (TextView) helper.itemView;
            textView.setText(bean.getShopName());
            textView.setSelected(bean.isSelect());
        }

        @Override
        public void setNewData(@Nullable List<PurchaserShopBean> data) {
            if (!CommonUtils.isEmpty(data)) {
                PurchaserShopBean bean = new PurchaserShopBean();
                bean.setShopName("全部");
                data.add(0, bean);
            }
            super.setNewData(data);
        }
    }
}
