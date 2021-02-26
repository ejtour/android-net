package com.hll_sc_app.app.agreementprice.goods;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Tuple;
import com.hll_sc_app.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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
    @BindView(R.id.img_clear)
    ImageView mImgClear;
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
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        EmptyView emptyView = EmptyView.newBuilder(mActivity)
                .setImage(R.drawable.ic_search_empty_purchaser)
                .setTips("没有找到相关的集团噢").create();
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
        mAdapterPurchaser.setEmptyView(emptyView);


        EmptyView emptyViewShop = EmptyView.newBuilder(mActivity)
                .setImage(R.drawable.ic_empty_shop_view)
                .setTips("没有找到相关的门店噢").create();
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
        mAdapterPurchaserShop.setEmptyView(emptyViewShop);
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
        if (!CommonUtils.isEmpty(mShopList)) {
            for (PurchaserShopBean bean : mShopList) {
                if (TextUtils.equals(bean.getShopName(), STRING_SELECT_ALL)) {
                    continue;
                }
                bean.setSelect(check);
            }
        }
    }

    private void checkSelectAll() {
        boolean selectAll = true;
        if (!CommonUtils.isEmpty(mShopList)) {
            // 首位是全选的时候需要遍历判断是否要全部选中
            if (!TextUtils.equals(mShopList.get(0).getShopName(), STRING_SELECT_ALL)) {
                return;
            }
            for (PurchaserShopBean bean : mShopList) {
                if (TextUtils.equals(bean.getShopName(), STRING_SELECT_ALL)) {
                    continue;
                }
                if (!bean.isSelect()) {
                    selectAll = false;
                    break;
                }
            }
            // 设置全部的选中状态
            mShopList.get(0).setSelect(selectAll);
        }
    }

    @OnTextChanged(R.id.edt_search_content)
    public void onTextChange(CharSequence s) {
        mImgClear.setVisibility(!TextUtils.isEmpty(s) ? View.VISIBLE : View.GONE);
        toSearch();
    }

    void showShopList(List<PurchaserShopBean> list) {
        mShopList = list;
        mAdapterPurchaserShop.setNewData(list);
    }

    void setListener(ConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_confirm, R.id.txt_reset, R.id.txt_search, R.id.txt_search_type, R.id.img_clear})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.txt_reset) {
            resetPurchaserShopList();
            mAdapterPurchaserShop.notifyDataSetChanged();
        } else if (id == R.id.txt_confirm && mListener != null) {
            Tuple<List<String>, List<String>> tuple = getSelectShopIds();
            mListener.confirm(TextUtils.join(",", tuple.getKey1()), TextUtils.join(",", tuple.getKey2()));
            dismiss();
        } else if (id == R.id.txt_search) {
            toSearch();
        } else if (id == R.id.img_clear) {
            mEdtSearchContent.setText(null);
        } else if (id == R.id.txt_search_type) {
            String searchType = mTxtSearchType.getText().toString().trim();
            mTxtSearchType.setText(TextUtils.equals(searchType, STRING_GROUP) ? STRING_SHOP : STRING_GROUP);
            mEdtSearchContent.setText(null);
            toSearchGroup(null);
            toSearchShop(null);
        }
    }

    /**
     * 重置采购商门店列表
     */
    private void resetPurchaserShopList() {
        if (!CommonUtils.isEmpty(mShopList)) {
            for (PurchaserShopBean bean : mShopList) {
                bean.setSelect(false);
            }
        }
    }

    private Tuple<List<String>, List<String>> getSelectShopIds() {
        List<String> selectId = new ArrayList<>();
        List<String> selectName = new ArrayList<>();
        if (!CommonUtils.isEmpty(mShopList)) {
            for (PurchaserShopBean bean : mShopList) {
                if (TextUtils.equals(bean.getShopName(), STRING_SELECT_ALL)) {
                    continue;
                }
                if (bean.isSelect()) {
                    selectId.add(bean.getShopID());
                    if (selectName.size() < 4) // 门店名列表最多存三个
                        selectName.add(bean.getShopName());
                }
            }
        }
        Tuple<List<String>, List<String>> tuple = new Tuple<>();
        tuple.setKey1(selectId);
        tuple.setKey2(selectName);
        return tuple;
    }

    interface ConfirmListener {
        /**
         * 确定
         *
         * @param shopIds   门店Id
         * @param shopNames 门店名称
         */
        void confirm(String shopIds, String shopNames);

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
