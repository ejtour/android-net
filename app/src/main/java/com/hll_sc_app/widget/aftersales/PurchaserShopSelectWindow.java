package com.hll_sc_app.widget.aftersales;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.aftersales.PurchaserBean;
import com.hll_sc_app.bean.aftersales.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class PurchaserShopSelectWindow extends BasePopupWindow {
    private static final String GROUP = "集团";
    private static final String SHOP = "门店";
    @BindView(R.id.pss_search_type)
    TextView mSearchType;
    @BindView(R.id.pss_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.pss_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.pss_right_list)
    RecyclerView mRightList;
    @BindView(R.id.pss_clear_search)
    ImageView mClearSearch;
    private PurchaserSelectAdapter mPurchaserAdapter;
    private PurchaserShopSelectAdapter mShopAdapter;
    private PurchaserBean mCurBean;
    private PurchaserShopSelectCallback mCallback;
    private List<PurchaserBean> mPurchaserBeans;

    public interface PurchaserShopSelectCallback {
        /**
         * @param flag 0 集团，1门店
         * @return 是否进行网络请求
         */
        default boolean search(String content, int flag) {
            return false;
        }

        default void loadPurchaserShop(String purchaserID) {
        }

        void onSelect(String purchaserID, String shopID);
    }

    public static PurchaserShopSelectWindow create(Activity activity, PurchaserShopSelectCallback callback) {
        PurchaserShopSelectWindow window = new PurchaserShopSelectWindow(activity);
        window.mCallback = callback;
        return window;
    }

    private PurchaserShopSelectWindow(Activity activity) {
        super(activity);
        View rootView = View.inflate(mActivity, R.layout.window_purchaser_shop_select, null);
        rootView.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mPurchaserAdapter = new PurchaserSelectAdapter();
        mShopAdapter = new PurchaserShopSelectAdapter();
        mLeftList.setAdapter(mPurchaserAdapter);
        mRightList.setAdapter(mShopAdapter);
        mPurchaserAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurBean = mPurchaserAdapter.getItem(position);
            if (mCurBean == null) return;
            if (mPurchaserAdapter.select(position)) {
                if (CommonUtils.isEmpty(mCurBean.getShopList()))
                    mCallback.loadPurchaserShop(mCurBean.getPurchaserID());
                else mShopAdapter.setNewData(mCurBean.getShopList());
            }
        });
        mShopAdapter.setOnItemChildClickListener((adapter, view, position) -> mShopAdapter.toggle(position));
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mRightList.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public PurchaserShopSelectWindow setLeftList(List<PurchaserBean> list) {
        mPurchaserBeans = list;
        mPurchaserAdapter.setNewData(list);
        return this;
    }

    public PurchaserShopSelectWindow setRightList(List<PurchaserShopBean> list) {
        mCurBean.setShopList(list);
        mShopAdapter.setNewData(list);
        return this;
    }

    @OnClick({R.id.pss_toggle_btn, R.id.pss_search_btn, R.id.pss_reset, R.id.pss_confirm, R.id.pss_clear_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pss_toggle_btn:
                if (isGroup()) mSearchType.setText(SHOP);
                else mSearchType.setText(GROUP);
                break;
            case R.id.pss_search_btn:
                String tag = mSearchEdit.getText().toString().trim();
                if (tag.equals(mSearchEdit.getTag())) return;
                mSearchEdit.setTag(tag);
                if (!mCallback.search(tag, isGroup() ? 0 : 1)) localProcessData();
                break;
            case R.id.pss_reset:
                mCallback.onSelect(null, null);
                break;
            case R.id.pss_confirm:
                String purchaserID = null, shopID = null;
                if (mCurBean != null) {
                    purchaserID = mCurBean.getPurchaserID();
                    for (PurchaserShopBean bean : mCurBean.getShopList()) {
                        if (bean.isSelected()) {
                            shopID = bean.getShopID();
                            break;
                        }
                    }
                }
                mCallback.onSelect(purchaserID, shopID);
                break;
            case R.id.pss_clear_search:
                mSearchEdit.setText("");
                break;
        }
    }

    private boolean isGroup() {
        return GROUP.contentEquals(mSearchType.getText());
    }

    private void localProcessData() {
        if (CommonUtils.isEmpty(mPurchaserBeans)) return;
        String words = mSearchEdit.getTag().toString();
        if (TextUtils.isEmpty(words)) {
            mPurchaserAdapter.setNewData(mPurchaserBeans);
            return;
        } else {
            List<PurchaserBean> list = new ArrayList<>();
            for (PurchaserBean bean : mPurchaserBeans) {
                if (isGroup()) {
                    if (bean.getPurchaserName().contains(words))
                        list.add(bean);
                } else {
                    PurchaserBean copy = bean.deepCopy();
                    List<PurchaserShopBean> shopBeans = copy.getShopList();
                    shopBeans.clear();
                    boolean add = false;
                    for (PurchaserShopBean shopBean : bean.getShopList()) {
                        if (shopBean.getShopName().contains(words)) {
                            add = true;
                            shopBeans.add(shopBean);
                        }
                    }
                    if (add) list.add(copy);
                }
            }
            mPurchaserAdapter.setNewData(list);
        }
        List<PurchaserBean> data = mPurchaserAdapter.getData();
        for (PurchaserBean bean : data) {
            if (bean.isSelected()) {
                mShopAdapter.setNewData(bean.getShopList());
                return;
            }
        }
        mShopAdapter.setNewData(null);
    }

    @OnTextChanged(R.id.pss_search_edit)
    public void onTextChanged(CharSequence s) {
        mClearSearch.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
    }

    private class PurchaserSelectAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {
        PurchaserSelectAdapter() {
            super(R.layout.item_purchase_select);
        }

        private boolean select(int position) {
            PurchaserBean item = getItem(position);
            if (item == null || item.isSelected()) return false;
            for (int i = 0; i < getData().size(); i++) {
                getData().get(i).setSelected(i == position);
            }
            notifyDataSetChanged();
            return true;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setText(item.getPurchaserName());
            itemView.setSelected(item.isSelected());
            itemView.setBackgroundResource(item.isSelected() ? R.color.color_fafafa : 0);
        }
    }

    private class PurchaserShopSelectAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        PurchaserShopSelectAdapter() {
            super(R.layout.item_purchase_shop_select);
        }

        private void toggle(int position) {
            PurchaserShopBean item = getItem(position);
            if (item == null) return;
            item.setSelected(!item.isSelected());
            if (position == 0) {
                for (PurchaserShopBean bean : getData()) {
                    bean.setSelected(item.isSelected());
                }
                notifyDataSetChanged();
            } else {
                int count = 0;
                for (int i = 1; i < getData().size(); i++) {
                    PurchaserShopBean bean = getData().get(i);
                    if (bean.isSelected()) count++;
                }
                PurchaserShopBean selectAll = getData().get(0);
                if (count == getItemCount() - 1)
                    if (!selectAll.isSelected()) {
                        selectAll.setSelected(true);
                        notifyDataSetChanged();
                    } else notifyItemChanged(position);
                else if (selectAll.isSelected()) {
                    selectAll.setSelected(false);
                    notifyDataSetChanged();
                } else notifyItemChanged(position);
            }
        }

        @Override
        public void setNewData(@Nullable List<PurchaserShopBean> data) {
            List<PurchaserShopBean> list = new ArrayList<>();
            if (!CommonUtils.isEmpty(data)) {
                PurchaserShopBean element = new PurchaserShopBean();
                element.setShopName("全部");
                list.add(element);
                list.addAll(data);
            }
            super.setNewData(list);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.addOnClickListener(R.id.pss_check_box);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            TextView textView = helper.getView(R.id.pss_name);
            textView.setText(item.getShopName());
            textView.setSelected(item.isSelected());
            helper.getView(R.id.pss_check_box).setSelected(item.isSelected());
        }
    }
}
