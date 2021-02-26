package com.hll_sc_app.app.goods.template;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从商品库导入-分类过滤弹窗
 *
 * @author 朱英松
 * @date 2019/6/28
 */
public class CategoryFilterWindow extends BasePopupWindow {
    private static final String STRING_SELECT_ALL = "全选";
    @BindView(R.id.recyclerView_category)
    RecyclerView mRecyclerViewCategory;
    @BindView(R.id.recyclerView_categorySub)
    RecyclerView mRecyclerViewCategorySub;
    @BindView(R.id.recyclerView_categoryThree)
    RecyclerView mRecyclerViewCategoryThree;
    @BindView(R.id.txt_reset)
    TextView mTxtReset;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    private CategoryAdapter mAdapter2;
    private CategoryAdapter mAdapter3;
    private ConfirmListener mListener;

    private CategoryItem mCategoryItem1;
    private CategoryItem mCategoryItem2;
    private CategoryResp mResp;

    private Set<CategoryItem> mSelectList;

    public CategoryFilterWindow(Activity context, CategoryResp resp) {
        super(context);
        View view = View.inflate(context, R.layout.window_template_category_filter, null);
        view.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, view);
        this.mResp = resp;
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mSelectList = new HashSet<>();
        initView(context);
    }

    private void initView(Activity context) {
        // 一级
        showFirst(context);
        // 二级
        showSecond(context);
        // 三级
        showThird(context);
    }

    /**
     * 展示一级分类
     *
     * @param context Activity
     */
    private void showFirst(Activity context) {
        mRecyclerViewCategory.setLayoutManager(new LinearLayoutManager(context));
        if (!CommonUtils.isEmpty(mResp.getList())) {
            mCategoryItem1 = mResp.getList().get(0);
            mCategoryItem1.setSelected(true);
        }
        CategoryAdapter mAdapter1 = new CategoryAdapter(false, mResp.getList());
        mAdapter1.setOnItemClickListener((adapter, view, position) -> {
            CategoryItem bean = (CategoryItem) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            // 重置一二级状态
            resetItem(mResp.getList());
            resetItem(mResp.getList2());
            // 设置选中
            bean.setSelected(true);
            mCategoryItem1 = bean;
            adapter.notifyDataSetChanged();
            // 联动二级
            List<CategoryItem> list2 = getListItem(bean.getCategoryID(), mResp.getList2());
            if (CommonUtils.isEmpty(list2)) {
                mAdapter2.setData(list2);
                return;
            }
            CategoryItem bean2 = list2.get(0);
            bean2.setSelected(true);
            mCategoryItem2 = bean2;
            mAdapter2.setData(list2);
            // 联动三级
            mAdapter3.setData(getListItem(bean2.getCategoryID(), mResp.getList3()));
        });
        mRecyclerViewCategory.setAdapter(mAdapter1);
    }

    /**
     * 展示二级分类
     *
     * @param context Activity
     */
    private void showSecond(Activity context) {
        mRecyclerViewCategorySub.setLayoutManager(new LinearLayoutManager(context));
        List<CategoryItem> list2 = null;
        if (mCategoryItem1 != null) {
            list2 = getListItem(mCategoryItem1.getCategoryID(), mResp.getList2());
            if (!CommonUtils.isEmpty(list2)) {
                mCategoryItem2 = list2.get(0);
                mCategoryItem2.setSelected(true);
            }
        }
        mAdapter2 = new CategoryAdapter(false, list2);
        mAdapter2.setOnItemClickListener((adapter, view, position) -> {
            CategoryItem bean2 = (CategoryItem) adapter.getItem(position);
            if (bean2 == null) {
                return;
            }
            // 重置二级
            resetItem(mResp.getList2());
            bean2.setSelected(true);
            adapter.notifyDataSetChanged();
            // 联动三级
            mAdapter3.setData(getListItem(bean2.getCategoryID(), mResp.getList3()));
        });
        mRecyclerViewCategorySub.setAdapter(mAdapter2);
    }

    /**
     * 展示三级分类
     *
     * @param context Activity
     */
    private void showThird(Activity context) {
        mRecyclerViewCategoryThree.setLayoutManager(new LinearLayoutManager(context));
        List<CategoryItem> list3 = null;
        if (mCategoryItem2 != null) {
            list3 = getListItem(mCategoryItem2.getCategoryID(), mResp.getList3());
        }
        mAdapter3 = new CategoryAdapter(true, list3);
        mAdapter3.setOnItemClickListener((adapter, view, position) -> {
            CategoryItem bean3 = (CategoryItem) adapter.getItem(position);
            if (bean3 == null) {
                return;
            }
            bean3.setSelected(!bean3.isSelected());
            if (bean3.isSelected()) {
                // 添加到选中列表中
                add(bean3);
            } else {
                // 从选中列表移除
                remove(bean3);
            }

            if (TextUtils.equals(STRING_SELECT_ALL, bean3.getCategoryName())) {
                // 点击了第一个的全选框，根据全选框选中状态判断全选还是全不选
                List<CategoryItem> itemList = mAdapter3.getData();
                for (CategoryItem item : itemList) {
                    item.setSelected(bean3.isSelected());
                    if (item.isSelected()) {
                        add(item);
                    } else {
                        remove(item);
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                // 没选中或者取消选中一个，都要判断整个数据的状态，用于第一个全选状态的更新
                adapter.notifyItemChanged(position);
                CategoryItem categoryItem = mAdapter3.getItem(0);
                if (categoryItem == null) {
                    return;
                }
                if (isSelectAll()) {
                    categoryItem.setSelected(true);
                    adapter.notifyItemChanged(0);
                } else {
                    categoryItem.setSelected(false);
                    adapter.notifyItemChanged(0);
                }
            }
        });
        mRecyclerViewCategoryThree.setAdapter(mAdapter3);
    }

    /**
     * 重置为未选中状态
     *
     * @param list list
     */
    private void resetItem(List<CategoryItem> list) {
        if (CommonUtils.isEmpty(list)) {
            return;
        }
        for (CategoryItem item : list) {
            item.setSelected(false);
        }
    }

    /**
     * 查询下一级分类数据
     *
     * @param id   父分类 ID
     * @param list 数据源
     * @return 对应的分类数据
     */
    private List<CategoryItem> getListItem(String id, List<CategoryItem> list) {
        List<CategoryItem> categoryItemList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (CategoryItem item : list) {
                item.setSelected(false);
                if (TextUtils.equals(item.getCategoryPID(), id)) {
                    categoryItemList.add(item);
                }
            }
        }
        return categoryItemList;
    }

    private void add(CategoryItem categoryItem) {
        if (!TextUtils.equals(categoryItem.getCategoryName(), STRING_SELECT_ALL)) {
            mSelectList.add(categoryItem);
        }
    }

    private void remove(CategoryItem categoryItem) {
        mSelectList.remove(categoryItem);
    }

    private boolean isSelectAll() {
        return isSelectAll(mAdapter3.getData());
    }

    private boolean isSelectAll(List<CategoryItem> categoryItemList) {
        boolean selectAll = true;
        if (!CommonUtils.isEmpty(categoryItemList)) {
            for (CategoryItem item : categoryItemList) {
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

    public void setListener(ConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_confirm, R.id.txt_reset})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.txt_reset) {
            // 三级全部清空选中
            resetItem(mResp.getList3());
            // 当前的清除
            if (mAdapter3 != null && !CommonUtils.isEmpty(mAdapter3.getData())) {
                CategoryItem item = mAdapter3.getItem(0);
                if (item != null) {
                    item.setSelected(false);
                }
            }
            mSelectList.clear();
            mAdapter3.notifyDataSetChanged();
        } else if (id == R.id.txt_confirm && mListener != null) {
            mListener.confirm(TextUtils.join(",", mSelectList));
            dismiss();
        }
    }

    public Tuple<List<String>, List<String>> getCategoryThreeIds() {
        Tuple<List<String>, List<String>> tuple = new Tuple<>();
        List<String> listId = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        if (!CommonUtils.isEmpty(mSelectList)) {
            for (CategoryItem item : mSelectList) {
                listId.add(item.getCategoryID());
                listName.add(item.getCategoryName());
            }
        }
        tuple.setKey1(listName);
        tuple.setKey2(listId);
        return tuple;
    }

    public interface ConfirmListener {
        /**
         * 确定
         *
         * @param beans 规格
         */
        void confirm(String beans);
    }

    public class CategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {
        private boolean mThree;

        CategoryAdapter(boolean isThree, List<CategoryItem> list) {
            super(R.layout.item_window_template_category, list);
            this.mThree = isThree;
            if (mThree) {
                CategoryItem item = new CategoryItem();
                item.setCategoryName(STRING_SELECT_ALL);
                list.add(0, item);
            }
        }

        public void setData(@Nullable List<CategoryItem> data) {
            if (mThree && !CommonUtils.isEmpty(data)) {
                CategoryItem item = new CategoryItem();
                item.setCategoryName(STRING_SELECT_ALL);
                data.add(0, item);
                for (CategoryItem categoryItem : data) {
                    if (mSelectList.contains(categoryItem)) {
                        categoryItem.setSelected(true);
                    }
                }
                // 判断首位的选中状态
                item.setSelected(isSelectAll(data));
            }
            super.setNewData(data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryItem item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getCategoryName());
            ImageView imgSelect = helper.getView(R.id.img_select);
            imgSelect.setVisibility(mThree ? View.VISIBLE : View.GONE);
            if (item.isSelected()) {
                txtCategoryName.setSelected(true);
                imgSelect.setSelected(true);
            } else {
                txtCategoryName.setSelected(false);
                imgSelect.setSelected(false);
            }
        }
    }
}
