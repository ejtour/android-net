package com.hll_sc_app.app.stockmanage.depot.category;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.stockmanage.CategorySubBean;
import com.hll_sc_app.bean.stockmanage.CategoryThreeBean;
import com.hll_sc_app.bean.stockmanage.DepotCategoryReq;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/15
 */
@Route(path = RouterConfig.ACTIVITY_DEPOT_CATEGORY)
public class DepotCategoryActivity extends BaseLoadActivity implements IDepotCategoryContract.IDepotCategoryView {

    @BindView(R.id.adc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.adc_select_all)
    TextView mSelectAll;
    @BindView(R.id.adc_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.adc_right_list)
    RecyclerView mRightList;
    @Autowired(name = "parcelable", required = true)
    DepotResp mResp;
    private IDepotCategoryContract.IDepotCategoryPresenter mPresenter;
    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;

    public static void start(Activity context, int reqCode, DepotResp resp) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_DEPOT_CATEGORY, context, reqCode, resp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot_category);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = DepotCategoryPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::save);
        mLeftList.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, ViewUtils.dip2px(this, 0.5f)));
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f));
        int space = UIUtils.dip2px(10);
        decor.setLineMargin(space, 0, space, 0, Color.WHITE);
        mRightList.addItemDecoration(decor);
        ((SimpleItemAnimator) mRightList.getItemAnimator()).setSupportsChangeAnimations(false);
        mLeftAdapter = new LeftAdapter();
        mLeftAdapter.bindToRecyclerView(mLeftList);
        mLeftAdapter.setOnItemClickListener((adapter, view, position) -> mRightAdapter.setNewData(mLeftAdapter.select(position).getSubList()));
        mRightAdapter = new RightAdapter();
        mRightAdapter.bindToRecyclerView(mRightList);
        mRightAdapter.setOnItemClickListener((adapter, view, position) -> {
            mRightAdapter.select(position);
            mSelectAll.setSelected(isSelectAll());
        });
    }

    private void save(View view) {
        DepotCategoryReq req = new DepotCategoryReq();
        req.setGroupID(mResp.getGroupID());
        req.setHouseID(mResp.getId());
        req.setIsWholeCountry(mResp.getIsWholeCountry());
        List<CategorySubBean> beans = new ArrayList<>();
        if (!CommonUtils.isEmpty(mLeftAdapter.getData())) {
            for (CustomCategoryBean bean : mLeftAdapter.getData()) {
                CategorySubBean subBean = null;
                for (CustomCategoryBean categoryBean : bean.getSubList()) {
                    if (categoryBean.isChecked()) {
                        if (subBean == null) {
                            subBean = new CategorySubBean();
                            subBean.setShopProductCategorySubID(bean.getId());
                            subBean.setShopProductCategorySubName(bean.getCategoryName());
                            List<CategoryThreeBean> list = new ArrayList<>();
                            subBean.setThreeList(list);
                        }
                        CategoryThreeBean threeBean = new CategoryThreeBean();
                        threeBean.setShopProductCategoryThreeID(categoryBean.getId());
                        threeBean.setShopProductCategoryThreeName(categoryBean.getCategoryName());
                        subBean.getThreeList().add(threeBean);
                    }
                }
                if (subBean != null) {
                    beans.add(subBean);
                }
            }
        }
        req.setWarehouseStoreCategoryList(beans);
        mPresenter.save(req);
    }

    @OnClick(R.id.adc_select_all)
    public void onClick(View view) {
        if (!CommonUtils.isEmpty(mLeftAdapter.getData())) {
            boolean selected = !view.isSelected();
            for (CustomCategoryBean bean : mLeftAdapter.getData()) {
                for (CustomCategoryBean categoryBean : bean.getSubList()) {
                    categoryBean.setChecked(selected);
                }
            }
            mRightAdapter.setNewData(mLeftAdapter.getSelectBean().getSubList());
            view.setSelected(selected);
        }
    }

    @Override
    public void success() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setData(List<CustomCategoryBean> list) {
        mLeftAdapter.setNewData(list);
        if (!CommonUtils.isEmpty(list)) {
            mRightAdapter.setNewData(list.get(0).getSubList());
        }
        mSelectAll.setSelected(isSelectAll());
    }

    private boolean isSelectAll() {
        boolean result = true;
        List<CustomCategoryBean> list = mLeftAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            LABEL:
            for (CustomCategoryBean bean : list) {
                for (CustomCategoryBean categoryBean : bean.getSubList()) {
                    if (!categoryBean.isChecked()) {
                        result = false;
                        break LABEL;
                    }
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public List<String> getSelectedIDList() {
        List<String> ids = new ArrayList<>();
        if (!CommonUtils.isEmpty(mResp.getWarehouseStoreCategoryList())) {
            for (CategorySubBean bean : mResp.getWarehouseStoreCategoryList()) {
                for (CategoryThreeBean threeBean : bean.getThreeList()) {
                    ids.add(threeBean.getShopProductCategoryThreeID());
                }
            }
        }
        return ids;
    }

    private static class LeftAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {
        private CustomCategoryBean mSelectBean;

        public LeftAdapter() {
            super(null);
        }

        CustomCategoryBean select(int position) {
            mSelectBean = getItem(position);
            if (mSelectBean != null) {
                for (CustomCategoryBean categoryBean : mData) {
                    categoryBean.setChecked(categoryBean.getId().equals(mSelectBean.getId()));
                }
                notifyDataSetChanged();
            }
            return mSelectBean;
        }

        public CustomCategoryBean getSelectBean() {
            return mSelectBean;
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45)));
            TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_City22_Middle);
            textView.setGravity(Gravity.CENTER);
            return new BaseViewHolder(textView);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setText(item.getCategoryName());
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), item.isChecked() ? R.color.base_activity_bg : android.R.color.transparent));
        }

        @Override
        public void setNewData(@Nullable List<CustomCategoryBean> data) {
            super.setNewData(data);
            select(0);
        }
    }

    private static class RightAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {
        private int mSpace;

        public RightAdapter() {
            super(null);
            mSpace = UIUtils.dip2px(10);
        }

        void select(int position) {
            CustomCategoryBean item = getItem(position);
            if (item == null) return;
            if (TextUtils.isEmpty(item.getId())) {
                boolean select = item.isChecked();
                for (CustomCategoryBean bean : mData) {
                    bean.setChecked(!select);
                }
            } else {
                item.setChecked(!item.isChecked());
                boolean select = true;
                for (CustomCategoryBean bean : mData) {
                    if (mData.indexOf(bean) == 0) {
                        continue;
                    }
                    if (!bean.isChecked()) {
                        select = false;
                        break;
                    }
                }
                mData.get(0).setChecked(select);
            }
            notifyDataSetChanged();
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45)));
            TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_City22_Middle);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setBackgroundColor(Color.WHITE);
            textView.setPadding(mSpace, 0, mSpace, 0);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.bg_selector_check_box_circular, 0);
            return new BaseViewHolder(textView);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            helper.itemView.setSelected(item.isChecked());
            ((TextView) helper.itemView).setText(item.getCategoryName());
        }

        @Override
        public void setNewData(@Nullable List<CustomCategoryBean> data) {
            List<CustomCategoryBean> list = new ArrayList<>();
            if (!CommonUtils.isEmpty(data)) {
                boolean checkAll = true;
                for (CustomCategoryBean bean : data) {
                    if (!bean.isChecked()) {
                        checkAll = false;
                        break;
                    }
                }
                CustomCategoryBean all = new CustomCategoryBean();
                all.setCategoryName("全部");
                all.setChecked(checkAll);
                list.add(all);
                list.addAll(data);
            }
            super.setNewData(list);
        }
    }
}
