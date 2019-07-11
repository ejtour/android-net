package com.hll_sc_app.app.agreementprice.goods.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.QuotationListAdapter;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.TimeBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议价管理-商品详情
 *
 * @author zhuyingsong
 * @date 2019/7/11
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_GOODS_DETAIL, extras = Constant.LOGIN_EXTRA)
public class GoodsPriceDetailActivity extends BaseLoadActivity implements GoodsPriceDetailContract.IPurchaseView {
    @Autowired(name = "parcelable", required = true)
    QuotationDetailBean mBean;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.img_search_close)
    ImageView mImgSearchClose;

    private EmptyView mEmptyView;
    private PurchaserListAdapter mAdapter;
    private GoodsPriceDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_price_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresenter = GoodsPriceDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mPresenter.queryPriceUsePurchaser(mBean.getProductSpecID());
        showView();
    }

    private void showView() {
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("喔唷，居然是「 空 」的").create();
        mEdtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch(mEdtSearch.getText().toString().trim());
                ViewUtils.clearEditFocus(mEdtSearch);
            }
            return true;
        });
        mEdtSearch.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
            mImgSearchClose.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            if (s != null) {
                toSearch(s.toString());
            } else {
                toSearch("");
            }
        });
        mTxtTitle.setText(mBean.getProductName());
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee),
            UIUtils.dip2px(5)));
        mAdapter = new PurchaserListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserBean bean = (PurchaserBean) adapter.getItem(position);
            if (bean != null) {
                bean.setSelect(!bean.isSelect());
                adapter.notifyItemChanged(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void toSearch(String searchParam) {
        if (TextUtils.isEmpty(searchParam)) {
            showPurchaserList(mPresenter.getPriceGoodsList());
        } else {
            List<PurchaserBean> listFilter = new ArrayList<>();
            List<PurchaserBean> listSource = mAdapter.getData();
            if (!CommonUtils.isEmpty(listSource)) {
                for (PurchaserBean bean : listSource) {
                    if (bean.getPurchaserName().contains(searchParam)) {
                        listFilter.add(bean);
                    }
                }
            }
            mAdapter.setNewData(listFilter);
        }
    }

    @Override
    public void showPurchaserList(List<PurchaserBean> list) {
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setNewData(list);
    }

    @OnClick({R.id.img_close, R.id.img_search_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.img_search_close:
                mEdtSearch.setText("");
                ViewUtils.clearEditFocus(mEdtSearch);
                break;
            default:
                break;
        }
    }

    public class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter(@Nullable List<PurchaserBean> data) {
            super(R.layout.list_item_price_manager_goods_detail, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            RecyclerView recyclerView = holder.getView(R.id.recyclerView);
            recyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(mContext, R.color.base_white),
                UIUtils.dip2px(5)));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(new PurchaserShopListAdapter());
            return holder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            helper.setText(R.id.txt_purchaserName, item.getPurchaserName())
                .getView(R.id.img_arrow).setRotation(item.isSelect() ? 90F : 0F);
            RecyclerView recyclerView = helper.getView(R.id.recyclerView);
            if (item.isSelect()) {
                recyclerView.setVisibility(View.VISIBLE);
                ((PurchaserShopListAdapter) recyclerView.getAdapter()).setNewData(item.getShopList());
            } else {
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    public class PurchaserShopListAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        PurchaserShopListAdapter() {
            super(R.layout.list_item_price_manager_goods_shop_detail);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            RecyclerView recyclerView = holder.getView(R.id.recyclerView);
            recyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(mContext, R.color.color_fafafa),
                UIUtils.dip2px(5)));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(new TimeBeanListAdapter());
            return holder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            helper.setText(R.id.txt_shopName, item.getShopName());
            RecyclerView recyclerView = helper.getView(R.id.recyclerView);
            ((TimeBeanListAdapter) recyclerView.getAdapter()).setNewData(item.getTime());
        }
    }

    public class TimeBeanListAdapter extends BaseQuickAdapter<TimeBean, BaseViewHolder> {

        TimeBeanListAdapter() {
            super(R.layout.list_item_price_manager_goods_shop_time);
        }

        @Override
        protected void convert(BaseViewHolder helper, TimeBean item) {
            helper.setText(R.id.txt_date, QuotationListAdapter.getPriceDate(item.getActiveTime(), item.getEndDate()))
                .setGone(R.id.txt_isActive, item.getIsActive() == 1)
                .setText(R.id.txt_price, "¥" + CommonUtils.formatMoney(item.getPrice()));
        }
    }
}
