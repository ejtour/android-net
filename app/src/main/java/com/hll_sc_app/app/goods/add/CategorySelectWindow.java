package com.hll_sc_app.app.goods.add;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * 商城分类选择
 *
 * @author 朱英松
 * @date 2019/6/21
 */
public class CategorySelectWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private CategoryItem mSelectCategory1;
    private WheelAdapter mCategoryAdapter1;

    private WheelView mPicker2;
    private CategoryItem mSelectCategory2;
    private WheelAdapter mCategoryAdapter2;

    private WheelView mPicker3;
    private CategoryItem mSelectCategory3;
    private WheelAdapter mCategoryAdapter3;

    private CategoryResp mResp;
    private CategorySelectListener mSelectListener;

    CategorySelectWindow(Activity context, CategoryResp resp) {
        super(context);
        this.mResp = resp;
        View rootView = View.inflate(context, R.layout.window_add_goods_category_select, null);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb000000));
        initPicker(rootView);
    }

    private void initPicker(View rootView) {
        WheelView picker1 = rootView.findViewById(R.id.picker_category1);
        picker1.setVisibleItems(5);
        mPicker2 = rootView.findViewById(R.id.picker_category2);
        mPicker2.setVisibleItems(5);
        mPicker3 = rootView.findViewById(R.id.picker_category3);
        mPicker3.setVisibleItems(5);
        rootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        rootView.findViewById(R.id.txt_confirm).setOnClickListener(this);

        mCategoryAdapter1 = new WheelAdapter(mActivity, mResp.getList());
        picker1.setViewAdapter(mCategoryAdapter1);
        picker1.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectCategory1 = mCategoryAdapter1.getItem(wheel.getCurrentItem());
                // 联动二级
                mCategoryAdapter2.setList(getListItem(mSelectCategory1.getCategoryID(), mResp.getList2()));
                mPicker2.setCurrentItem(0, true);
                mSelectCategory2 = mCategoryAdapter2.getItem(0);
                // 二级变动联动三级
                mCategoryAdapter3.setList(getListItem(mSelectCategory2.getCategoryID(), mResp.getList3()));
                mPicker3.setCurrentItem(0, true);
                mSelectCategory3 = mCategoryAdapter3.getItem(0);
            }
        });
        picker1.setCurrentItem(0);
        mSelectCategory1 = mCategoryAdapter1.getItem(0);

        mCategoryAdapter2 = new WheelAdapter(mActivity, getListItem(mSelectCategory1.getCategoryID(),
            mResp.getList2()));
        mPicker2.setViewAdapter(mCategoryAdapter2);
        mPicker2.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectCategory2 = mCategoryAdapter2.getItem(wheel.getCurrentItem());
                // 联动三级
                mCategoryAdapter3.setList(getListItem(mSelectCategory2.getCategoryID(), mResp.getList3()));
                mPicker3.setCurrentItem(0, true);
                mSelectCategory3 = mCategoryAdapter3.getItem(0);
            }
        });
        mPicker2.setCurrentItem(0);
        mSelectCategory2 = mCategoryAdapter2.getItem(0);

        mCategoryAdapter3 = new WheelAdapter(mActivity, getListItem(mSelectCategory2.getCategoryID(),
            mResp.getList3()));
        mPicker3.setViewAdapter(mCategoryAdapter3);
        mPicker3.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                //no-op
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectCategory3 = mCategoryAdapter3.getItem(wheel.getCurrentItem());
            }
        });
        mPicker3.setCurrentItem(0);
        mSelectCategory3 = mCategoryAdapter3.getItem(0);
    }

    private List<CategoryItem> getListItem(String categoryPID, List<CategoryItem> list) {
        List<CategoryItem> categoryItemList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (CategoryItem item : list) {
                if (TextUtils.equals(item.getCategoryPID(), categoryPID)) {
                    categoryItemList.add(item);
                }
            }
        }
        return categoryItemList;
    }

    void setSelectListener(CategorySelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_confirm) {
            if (mSelectListener != null) {
                mSelectListener.select(mSelectCategory1, mSelectCategory2, mSelectCategory3);
            }
        }
        dismiss();
    }

    /**
     * 点击确定后监听
     */
    public interface CategorySelectListener {
        /**
         * 选择的分类
         *
         * @param categoryItem1 select categoryItem
         * @param categoryItem2 select categoryItem
         * @param categoryItem3 select categoryItem
         */
        void select(CategoryItem categoryItem1, CategoryItem categoryItem2, CategoryItem categoryItem3);
    }

    class WheelAdapter extends AbstractWheelTextAdapter {
        private List<CategoryItem> mList;

        WheelAdapter(Context context, List<CategoryItem> list) {
            super(context, R.layout.window_add_goods_select_item, R.id.txt_select_item);
            this.mList = list;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return mList.get(index).getCategoryName();
        }

        public CategoryItem getItem(int index) {
            return mList.get(index);
        }

        @Override
        public int getItemsCount() {
            return mList == null ? 0 : mList.size();
        }

        public void setList(List<CategoryItem> list) {
            this.mList = list;
            notifyDataInvalidatedEvent();
        }
    }
}
