package com.hll_sc_app.app.agreementprice.goods;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议价管理-商品-采购商门店筛选
 *
 * @author 朱英松
 * @date 2019/7/12
 */
public class GoodsPriceShopSelectWindow extends BasePopupWindow {
    static final String STRING_SELECT_ALL = "全部";
    private static final String STRING_GROUP = "集团";
    private static final String STRING_SHOP = "门店";
    @BindView(R.id.recyclerView_purchaser)
    RecyclerView mRecyclerViewPurchaser;
    @BindView(R.id.recyclerView_purchaserShop)
    RecyclerView mRecyclerViewPurchaserShop;
    @BindView(R.id.txt_search_type)
    TextView mTxtSearchType;
    @BindView(R.id.edt_search_content)
    EditText mEdtSearchContent;
    private ConfirmListener mListener;
    private List<PurchaserBean> mList;
    private PurchaserListAdapter mAdapterPurchaser;
    private List<PurchaserShopBean> mShopList;
    private PurchaserShopListAdapter mAdapterPurchaserShop;

    GoodsPriceShopSelectWindow(Activity context, List<PurchaserBean> list) {
        super(context);
        View view = View.inflate(context, R.layout.window_goods_price_shop_filter, null);
        view.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, view);
        this.mList = list;
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mEdtSearchContent.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return true;
        });
        showPurchaserList();
    }

    private void toSearch() {
        String searchParam = mEdtSearchContent.getText().toString().trim();
        String searchType = mTxtSearchType.getText().toString().trim();
        if (TextUtils.equals(searchType, STRING_GROUP)) {
            toSearchGroup(searchParam);
        } else if (TextUtils.equals(searchType, STRING_SHOP)) {
            toSearchShop(searchParam);
        }
    }

    /**
     * 显示采购商列表
     */
    private void showPurchaserList() {
        mAdapterPurchaser = new PurchaserListAdapter(mList);
        mAdapterPurchaser.setOnItemClickListener((adapter, view, position) -> {
            PurchaserBean bean = (PurchaserBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            resetPurchaserList();
            bean.setSelect(true);
            adapter.notifyDataSetChanged();
            if (mShopList != null) {
                mShopList.clear();
            }
            mAdapterPurchaserShop.setNewData(null);
            if (mListener != null && !TextUtils.equals(bean.getPurchaserName(), STRING_SELECT_ALL)) {
                mListener.queryShopList(bean.getPurchaserID());
            }
        });
        mRecyclerViewPurchaser.setAdapter(mAdapterPurchaser);

        mAdapterPurchaserShop = new PurchaserShopListAdapter();
        mAdapterPurchaserShop.setOnItemClickListener((adapter, view, position) -> {
            PurchaserShopBean shopBean = (PurchaserShopBean) adapter.getItem(position);
            if (shopBean == null) {
                return;
            }
            shopBean.setSelect(!shopBean.isSelect());
            if (TextUtils.equals(shopBean.getShopName(), STRING_SELECT_ALL)) {
                checkAll(shopBean.isSelect());
            } else {
                checkSelectAll();
            }
            adapter.notifyDataSetChanged();
        });
        mRecyclerViewPurchaserShop.setAdapter(mAdapterPurchaserShop);
    }

    private void toSearchGroup(String searchParam) {
        if (TextUtils.isEmpty(searchParam)) {
            mAdapterPurchaser.setNewData(mList);
        } else {
            if (!CommonUtils.isEmpty(mList)) {
                List<PurchaserBean> listFilter = new ArrayList<>();
                for (PurchaserBean bean : mList) {
                    if (bean.getPurchaserName().contains(searchParam)) {
                        listFilter.add(bean);
                    }
                }
                mAdapterPurchaser.setNewData(listFilter);
            }
        }
    }

    private void toSearchShop(String searchParam) {
        if (TextUtils.isEmpty(searchParam)) {
            mAdapterPurchaserShop.setNewData(mShopList);
        } else {
            if (!CommonUtils.isEmpty(mShopList)) {
                List<PurchaserShopBean> listFilter = new ArrayList<>();
                for (PurchaserShopBean bean : mShopList) {
                    if (bean.getShopName().contains(searchParam)) {
                        listFilter.add(bean);
                    }
                }
                mAdapterPurchaserShop.setNewData(listFilter);
            }
        }
    }

    private void resetPurchaserList() {
        if (!CommonUtils.isEmpty(mList)) {
            for (PurchaserBean shopBean : mList) {
                shopBean.setSelect(false);
            }
        }
    }

    /**
     * 设置全选、全不选
     *
     * @param check true-全选 false-全不选
     */
    private void checkAll(boolean check) {
        List<PurchaserShopBean> shopBeans = mAdapterPurchaserShop.getData();
        if (!CommonUtils.isEmpty(shopBeans)) {
            for (PurchaserShopBean bean : shopBeans) {
                if (TextUtils.equals(bean.getShopName(), STRING_SELECT_ALL)) {
                    continue;
                }
                bean.setSelect(check);
            }
        }
    }

    private void checkSelectAll() {
        boolean selectAll = true;
        List<PurchaserShopBean> shopBeans = mAdapterPurchaserShop.getData();
        if (!CommonUtils.isEmpty(shopBeans)) {
            // 首位是全选的时候需要遍历判断是否要全部选中
            if (!TextUtils.equals(shopBeans.get(0).getShopName(), STRING_SELECT_ALL)) {
                return;
            }
            for (PurchaserShopBean bean : shopBeans) {
                if (TextUtils.equals(bean.getShopName(), STRING_SELECT_ALL)) {
                    continue;
                }
                if (!bean.isSelect()) {
                    selectAll = false;
                    break;
                }
            }
            // 设置全部的选中状态
            shopBeans.get(0).setSelect(selectAll);
        }
    }

    void showShopList(List<PurchaserShopBean> list) {
        mShopList = list;
        mAdapterPurchaserShop.setNewData(list);
    }

    void setListener(ConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_confirm, R.id.txt_reset, R.id.txt_search, R.id.txt_search_type})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.txt_reset) {
            resetPurchaserShopList();
            mAdapterPurchaserShop.notifyDataSetChanged();
        } else if (id == R.id.txt_confirm && mListener != null) {
            mListener.confirm(TextUtils.join(",", getSelectShopIds()));
            dismiss();
        } else if (id == R.id.txt_search) {
            toSearch();
        } else if (id == R.id.txt_search_type) {
            String searchType = mTxtSearchType.getText().toString().trim();
            mTxtSearchType.setText(TextUtils.equals(searchType, STRING_GROUP) ? STRING_SHOP : STRING_GROUP);
        }
    }

    /**
     * 重置采购商门店列表
     */
    private void resetPurchaserShopList() {
        List<PurchaserShopBean> list = mAdapterPurchaserShop.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (PurchaserShopBean bean : list) {
                bean.setSelect(false);
            }
        }
    }

    private List<String> getSelectShopIds() {
        List<String> list = new ArrayList<>();
        List<PurchaserShopBean> shopBeans = mAdapterPurchaserShop.getData();
        if (!CommonUtils.isEmpty(shopBeans)) {
            for (PurchaserShopBean bean : shopBeans) {
                if (TextUtils.equals(bean.getShopName(), STRING_SELECT_ALL)) {
                    continue;
                }
                if (bean.isSelect()) {
                    list.add(bean.getShopID());
                }
            }
        }
        return list;
    }

    interface ConfirmListener {
        /**
         * 确定
         *
         * @param shopIds 门店Id
         */
        void confirm(String shopIds);

        /**
         * 查询门店列表
         *
         * @param purchaserId 采购商 Id
         */
        void queryShopList(String purchaserId);
    }

    public class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter(List<PurchaserBean> list) {
            super(R.layout.item_window_purchaser_select, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            TextView txtPurchaserName = helper.getView(R.id.txt_purchaserName);
            txtPurchaserName.setText(item.getPurchaserName());
            txtPurchaserName.setSelected(item.isSelect());
            txtPurchaserName.setBackgroundColor(item.isSelect() ? 0xFFFFFFFF : 0xFFF3F3F3);
        }
    }

    public class PurchaserShopListAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        PurchaserShopListAdapter() {
            super(R.layout.item_window_template_category);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.itemView.setBackgroundColor(0xFFFFFFFF);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            TextView txtShopName = helper.getView(R.id.txt_categoryName);
            txtShopName.setGravity(Gravity.CENTER_VERTICAL);
            txtShopName.setText(item.getShopName());
            txtShopName.setSelected(item.isSelect());
            helper.getView(R.id.img_select).setSelected(item.isSelect());
        }
    }
}
