package com.hll_sc_app.app.pricemanage;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 售价设置-筛选弹窗
 *
 * @author 朱英松
 * @date 2019/7/15
 */
public class PriceManageFilterWindow extends BaseShadowPopupWindow {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_productStatus4)
    TextView mTxtProductStatus4;
    @BindView(R.id.txt_productStatus5)
    TextView mTxtProductStatus5;
    @BindView(R.id.txt_product_self)
    TextView mTxtProductSelf;
    @BindView(R.id.txt_product_warehouse)
    TextView mTxtProductWarehouse;
    private ConfirmListener mListener;
    private CustomCategoryResp mResp;
    private CategoryListAdapter mAdapter;

    PriceManageFilterWindow(Activity activity, CustomCategoryResp resp) {
        super(activity);
        this.mResp = resp;
        this.mActivity = activity;
        View rootView = View.inflate(activity, R.layout.window_price_manage_filter, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setAnimationStyle(R.style.BaseRightAnimation);
        this.setWidth((int) (UIUtils.getScreenWidth(activity) * 0.8F));
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable());
        // 二级分类展示
        mAdapter = new CategoryListAdapter(resp.getList2());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CustomCategoryBean bean = (CustomCategoryBean) adapter.getItem(position);
            if (bean != null) {
                bean.setChecked(!bean.isChecked());
                adapter.notifyItemChanged(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<CustomCategoryBean> getListItem(String id, List<CustomCategoryBean> list) {
        List<CustomCategoryBean> categoryItemList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (CustomCategoryBean item : list) {
                if (TextUtils.equals(item.getShopCategoryPID(), id)) {
                    categoryItemList.add(item);
                }
            }
        }
        return categoryItemList;
    }

    void setConfirmListener(ConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_reset, R.id.txt_confirm, R.id.txt_productStatus4, R.id.txt_productStatus5, R.id.txt_product_self, R.id.txt_product_warehouse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_reset:
                mTxtProductStatus4.setSelected(false);
                mTxtProductStatus5.setSelected(false);
                mTxtProductWarehouse.setSelected(false);
                mTxtProductSelf.setSelected(false);
                resetCategoryIds();
                break;
            case R.id.txt_confirm:
                mListener.confirm();
                dismiss();
                break;
            case R.id.txt_productStatus4:
                mTxtProductStatus4.setSelected(!mTxtProductStatus4.isSelected());
                mTxtProductStatus5.setSelected(false);
                break;
            case R.id.txt_productStatus5:
                mTxtProductStatus5.setSelected(!mTxtProductStatus5.isSelected());
                mTxtProductStatus4.setSelected(false);
                break;
            case R.id.txt_product_self:
                mTxtProductSelf.setSelected(!mTxtProductSelf.isSelected());
                mTxtProductWarehouse.setSelected(false);
                break;
            case R.id.txt_product_warehouse:
                mTxtProductWarehouse.setSelected(!mTxtProductWarehouse.isSelected());
                mTxtProductSelf.setSelected(false);
                break;
            default:
                dismiss();
                break;
        }
    }

    private void resetCategoryIds() {
        if (mResp != null) {
            List<CustomCategoryBean> list3 = mResp.getList3();
            if (!CommonUtils.isEmpty(list3)) {
                for (CustomCategoryBean bean : list3) {
                    bean.setChecked(false);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public String getProductStatus() {
        String productStatus = "";
        if (mTxtProductStatus4.isSelected()) {
            productStatus = "4";
        } else if (mTxtProductStatus5.isSelected()) {
            productStatus = "5";
        }
        return productStatus;
    }

    public int getIsWareHourse() {
        if (mTxtProductSelf.isSelected()) {
            return 0;
        } else if (mTxtProductWarehouse.isSelected()) {
            return 1;
        } else {
            return -1;
        }
    }

    String getCategoryIds() {
        List<String> listSelect = new ArrayList<>();
        if (mResp != null) {
            List<CustomCategoryBean> list3 = mResp.getList3();
            if (!CommonUtils.isEmpty(list3)) {
                for (CustomCategoryBean bean : list3) {
                    if (bean.isChecked()) {
                        listSelect.add(bean.getId());
                    }
                }
            }
        }
        return TextUtils.join(",", listSelect);
    }

    public interface ConfirmListener {
        /**
         * 确定
         */
        void confirm();
    }

    private class CategoryListAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {

        CategoryListAdapter(@Nullable List<CustomCategoryBean> data) {
            super(R.layout.window_price_manage_filter_item_title, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_open);
            RecyclerView recyclerView = viewHolder.getView(R.id.recyclerView);
            CategoryThreeListAdapter categoryThreeListAdapter = new CategoryThreeListAdapter();
            categoryThreeListAdapter.setOnItemClickListener((adapter, view, position) -> {
                CustomCategoryBean bean = categoryThreeListAdapter.getItem(position);
                if (bean != null) {
                    bean.setChecked(!bean.isChecked());
                    categoryThreeListAdapter.notifyItemChanged(position);
                }
            });
            recyclerView.setAdapter(categoryThreeListAdapter);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            List<CustomCategoryBean> list3 = getListItem(item.getId(), mResp.getList3());
            helper.setText(R.id.txt_categoryName, item.getCategoryName())
                    .setText(R.id.txt_open, item.isChecked() ? "收起" : "展开")
                    .setGone(R.id.txt_open, list3.size() > 2);
            RecyclerView recyclerView = helper.getView(R.id.recyclerView);
            CategoryThreeListAdapter adapter = (CategoryThreeListAdapter) recyclerView.getAdapter();
            adapter.setOpen(item.isChecked());
            adapter.setNewData(list3);
        }
    }

    private class CategoryThreeListAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {
        private boolean open;

        CategoryThreeListAdapter() {
            super(R.layout.window_price_manage_filter_item);
        }

        @Override
        public int getItemCount() {
            return open ? super.getItemCount() : (super.getItemCount() > 2 ? 2 : super.getItemCount());
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            helper.setText(R.id.txt_name, item.getCategoryName())
                    .getView(R.id.txt_name).setSelected(item.isChecked());
        }

        void setOpen(boolean open) {
            this.open = open;
        }
    }
}
