package com.hll_sc_app.app.agreementprice.goods;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Tuple;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议价管理-商品-分类过滤弹窗
 *
 * @author 朱英松
 * @date 2019/7/11
 */
public class GoodsPriceCategoryWindow extends BasePopupWindow {
    private static final String STRING_SELECT_ALL = "全选";
    @BindView(R.id.recyclerView_categorySub)
    RecyclerView mRecyclerViewCategorySub;
    private CategoryResp mResp;
    private CategoryAdapter mAdapter2;
    private ConfirmListener mListener;

    GoodsPriceCategoryWindow(Activity context, CategoryResp resp) {
        super(context);
        View view = View.inflate(context, R.layout.window_goods_price_category_filter, null);
        view.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, view);
        this.mResp = resp;
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        showSub();
    }

    /**
     * 展示二级分类
     */
    private void showSub() {
        mAdapter2 = new CategoryAdapter(mResp.getList2());
        mAdapter2.setOnItemClickListener((adapter, view, position) -> {
            CategoryItem bean2 = (CategoryItem) adapter.getItem(position);
            if (bean2 == null) {
                return;
            }
            bean2.setSelected(!bean2.isSelected());
            if (TextUtils.equals(STRING_SELECT_ALL, bean2.getCategoryName())) {
                // 点击了第一个的全选框，根据全选框选中状态判断全选还是全不选
                List<CategoryItem> itemList = mAdapter2.getData();
                for (CategoryItem item : itemList) {
                    item.setSelected(bean2.isSelected());
                }
                adapter.notifyDataSetChanged();
            } else {
                // 没选中或者取消选中一个，都要判断整个数据的状态，用于第一个全选状态的更新
                adapter.notifyItemChanged(position);
                CategoryItem categoryItem = mAdapter2.getItem(0);
                if (categoryItem != null && TextUtils.equals(STRING_SELECT_ALL, categoryItem.getCategoryName())) {
                    // 为全选按钮
                    categoryItem.setSelected(isSelectAll());
                    adapter.notifyItemChanged(0);
                }
            }
        });
        mRecyclerViewCategorySub.setAdapter(mAdapter2);
    }

    /**
     * 是否全部选中
     *
     * @return true-全选
     */
    private boolean isSelectAll() {
        boolean selectAll = true;
        List<CategoryItem> items = mAdapter2.getData();
        if (!CommonUtils.isEmpty(items)) {
            for (CategoryItem item : items) {
                if (TextUtils.equals(item.getCategoryName(), STRING_SELECT_ALL)) {
                    continue;
                }
                if (!item.isSelected()) {
                    selectAll = false;
                    break;
                }
            }
        }
        return selectAll;
    }

    void setListener(ConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_confirm, R.id.txt_reset})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.txt_reset) {
            resetItem();
        } else if (id == R.id.txt_confirm && mListener != null) {
            Tuple<List<String>, List<String>> tuple = getSelectList();
            mListener.confirm(TextUtils.join(",", tuple.getKey1()), TextUtils.join(",", tuple.getKey2()));
            dismiss();
        }
    }

    /**
     * 重置为未选中状态
     */
    private void resetItem() {
        List<CategoryItem> list = mAdapter2.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (CategoryItem item : list) {
                item.setSelected(false);
            }
            mAdapter2.notifyDataSetChanged();
        }
    }

    /**
     * 获取选中的分类
     *
     * @return 选中的分类集合
     */
    private Tuple<List<String>, List<String>> getSelectList() {
        List<String> selectId = new ArrayList<>();
        List<String> selectName = new ArrayList<>();
        List<CategoryItem> items = mAdapter2.getData();
        if (!CommonUtils.isEmpty(items)) {
            for (CategoryItem item : items) {
                if (TextUtils.equals(item.getCategoryName(), STRING_SELECT_ALL)) {
                    continue;
                }
                if (item.isSelected()) {
                    selectName.add(item.getCategoryName());
                    selectId.add(item.getCategoryID());
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
         * @param categoryIds 选中的分类数据
         * @param names       选中的分类名称
         */
        void confirm(String categoryIds, String names);
    }

    public class CategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {

        CategoryAdapter(List<CategoryItem> list) {
            super(R.layout.item_window_template_category, list);
            CategoryItem item = new CategoryItem();
            item.setCategoryName(STRING_SELECT_ALL);
            list.add(0, item);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryItem item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getCategoryName());
            txtCategoryName.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            txtCategoryName.setSelected(item.isSelected());
            helper.getView(R.id.img_select).setSelected(item.isSelected());
        }
    }
}
