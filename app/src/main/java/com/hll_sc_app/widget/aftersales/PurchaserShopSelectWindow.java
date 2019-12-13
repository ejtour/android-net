package com.hll_sc_app.widget.aftersales;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/9
 */

public class PurchaserShopSelectWindow extends BasePopupWindow {
    private static final String GROUP = "集团";
    private static final String SHOP = "门店";
    private static final String STRING_ALL = "全部";
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
    @BindView(R.id.pss_reset)
    TextView mReset;
    @BindView(R.id.pss_empty_view)
    LinearLayout mEmptyView;
    private PurchaserSelectAdapter mPurchaserAdapter;
    private PurchaserShopSelectAdapter mShopAdapter;
    private PurchaserBean mCurBean;
    private PurchaserShopBean mCurShopBean;
    private PurchaserShopSelectCallback mCallback;
    private List<PurchaserBean> mPurchaserBeans;
    private boolean mIsMulti;

    public interface PurchaserShopSelectCallback {
        /**
         * @param flag        0 集团，1门店
         * @param purchaserID 搜索门店时传purchaserID
         * @return 是否进行网络请求
         */
        default boolean search(String searchWords, int flag, String purchaserID) {
            return false;
        }

        default void loadPurchaserShop(String purchaserID, String searchWords) {
        }

        void onSelect(String purchaserID, String shopID, List<String> shopNameList);
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
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
    }

    private void initView() {
        mPurchaserAdapter = new PurchaserSelectAdapter();
        mShopAdapter = new PurchaserShopSelectAdapter();
        mLeftList.setAdapter(mPurchaserAdapter);
        mRightList.setAdapter(mShopAdapter);
        mPurchaserAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurBean = mPurchaserAdapter.getItem(position);
            if (mCurBean == null) return;
            if (mPurchaserAdapter.select(position)) {
                if (CommonUtils.isEmpty(mCurBean.getShopList())) {
                    mShopAdapter.setNewData(mCurBean.getShopList());
                    if (!TextUtils.isEmpty(mCurBean.getPurchaserID()))
                        mCallback.loadPurchaserShop(mCurBean.getPurchaserID(), isGroup() ?
                                "" : mSearchEdit.getText().toString().trim());
                } else mShopAdapter.setNewData(mCurBean.getShopList());
            }
        });
        mShopAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurShopBean = mShopAdapter.getItem(position);
            if (mCurShopBean == null) return;
            if (mIsMulti) mShopAdapter.toggle(position);
            else mShopAdapter.select(position);
        });
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mRightList.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public PurchaserShopSelectWindow setLeftList(List<PurchaserBean> list) {
        mPurchaserBeans = list;
        refreshList(list);
        return this;
    }

    public PurchaserShopSelectWindow setRightList(List<PurchaserShopBean> list) {
        if (mCurBean != null) mCurBean.setShopList(list);
        mShopAdapter.setNewData(list);
        return this;
    }

    public PurchaserShopSelectWindow setMulti(boolean multi) {
        mIsMulti = multi;
        mShopAdapter.notifyDataSetChanged();
        return this;
    }

    public void refreshList(List<PurchaserBean> list) {
        preProcess(list);
        mPurchaserAdapter.setNewData(list);
        mEmptyView.setVisibility(CommonUtils.isEmpty(list) ? View.VISIBLE : View.GONE);
    }

    private void preProcess(List<PurchaserBean> list) {
        if (mCurBean != null && !CommonUtils.isEmpty(list)) {
            PurchaserBean temp = mCurBean;
            mCurBean = null;
            for (PurchaserBean bean : list) {
                if (bean.getPurchaserID().equals(temp.getPurchaserID())) {
                    bean.setSelected(true);
                    mShopAdapter.setNewData(temp.getShopList());
                    bean.setShopList(temp.getShopList());
                    mCurBean = bean;
                } else bean.setSelected(false);
            }
        }
    }

    @OnClick({R.id.pss_toggle_btn, R.id.pss_search_btn, R.id.pss_reset, R.id.pss_confirm, R.id.pss_clear_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pss_toggle_btn:
                if (isGroup()) mSearchType.setText(SHOP);
                else mSearchType.setText(GROUP);
                break;
            case R.id.pss_search_btn:
                toSearch();
                break;
            case R.id.pss_reset:
                reset();
                break;
            case R.id.pss_confirm:
                String purchaserID = null, shopID = null;
                List<String> shopList = new ArrayList<>();
                List<String> shopIDList = new ArrayList<>();
                if (mCurBean != null) {
                    purchaserID = mCurBean.getPurchaserID();
                    if (!CommonUtils.isEmpty(mCurBean.getShopList()))
                        for (PurchaserShopBean bean : mCurBean.getShopList()) {
                            if (bean.isSelected()) {
                                if (shopList.size() < 4) // 门店名列表最多存三个
                                    shopList.add(bean.getShopName());
                                if (mIsMulti) {
                                    if (!TextUtils.isEmpty(bean.getShopID())) {
                                        shopIDList.add(bean.getShopID());
                                    }
                                } else {
                                    shopID = bean.getShopID();
                                    break;
                                }
                            }
                        }
                }
                mCallback.onSelect(purchaserID, mIsMulti ? TextUtils.join(",", shopIDList) : shopID, shopList);
                break;
            case R.id.pss_clear_search:
                mSearchEdit.setText("");
                toSearch();
                break;
        }
    }

    private void reset() {
        for (PurchaserBean bean : mPurchaserBeans) {
            bean.setSelected(false);
        }
        mCurBean = mCurShopBean = null;
        mShopAdapter.setNewData(null);
        mSearchType.setText(GROUP);
        mSearchEdit.setText("");
        mSearchEdit.setTag("");
        if (!mCallback.search("", 0, null))
            localProcessData();
    }

    private void toSearch() {
        String tag = mSearchEdit.getText().toString().trim();
        if (tag.equals(mSearchEdit.getTag())) return;
        mSearchEdit.setTag(tag);
        if (!mCallback.search(tag, isGroup() ? 0 : 1, mCurBean == null ? null : mCurBean.getPurchaserID()))
            localProcessData();
        else {
            mShopAdapter.setNewData(null);
        }
    }

    private boolean isGroup() {
        return GROUP.contentEquals(mSearchType.getText());
    }

    private void localProcessData() {
        if (CommonUtils.isEmpty(mPurchaserBeans)) return;
        String words = mSearchEdit.getTag().toString();
        if (TextUtils.isEmpty(words)) {
            refreshList(mPurchaserBeans);
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
            refreshList(list);
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

    @OnEditorAction(R.id.pss_search_edit)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) toSearch();
        return true;
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
        public void setNewData(@Nullable List<PurchaserBean> data) {
            List<PurchaserBean> list = new ArrayList<>();
            if (!CommonUtils.isEmpty(data)) {
                PurchaserBean element = new PurchaserBean();
                element.setPurchaserName(STRING_ALL);
                list.add(element);
                boolean selectAll = true;
                for (PurchaserBean bean : data) {
                    if (bean.isSelected()) {
                        selectAll = false;
                        break;
                    }
                }
                element.setSelected(selectAll);
                list.addAll(data);
            }
            super.setNewData(list);
            if (mCurBean != null) {
                mLeftList.scrollToPosition(mPurchaserAdapter.getData().indexOf(mCurBean));
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setText(item.getPurchaserName());
            itemView.setSelected(item.isSelected());
            itemView.setBackgroundResource(item.isSelected() ? android.R.color.white : 0);
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
            if (position == 0 && STRING_ALL.equals(item.getShopName())) {
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

        private void select(int position) {
            PurchaserShopBean item = getItem(position);
            if (item == null || item.isSelected()) return;
            for (int i = 0; i < getData().size(); i++) {
                getData().get(i).setSelected(i == position);
            }
            notifyDataSetChanged();
        }

        @Override
        public void setNewData(@Nullable List<PurchaserShopBean> data) {
            List<PurchaserShopBean> list = new ArrayList<>();
            PurchaserShopBean element = new PurchaserShopBean();
            element.setSelected(!mIsMulti);
            element.setShopName(STRING_ALL);
            list.add(element);
            if (!CommonUtils.isEmpty(data)) {
                for (PurchaserShopBean bean : data) {
                    bean.setSelected(false);
                }
                list.addAll(data);
            }
            super.setNewData(list);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
            TextView textView = helper.getView(R.id.pss_name);
            textView.setText(item.getShopName());
            textView.setSelected(item.isSelected());
            if (mIsMulti)
                helper.setImageResource(R.id.pss_check_box, R.drawable.bg_selector_check_box)
                        .getView(R.id.pss_check_box).setSelected(item.isSelected());
            else
                helper.setImageResource(R.id.pss_check_box, item.isSelected() ? R.drawable.ic_ok : 0);
        }
    }
}
