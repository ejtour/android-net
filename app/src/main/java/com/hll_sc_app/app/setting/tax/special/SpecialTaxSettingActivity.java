package com.hll_sc_app.app.setting.tax.special;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.setting.tax.goodsselect.GoodsSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/30
 */

@Route(path = RouterConfig.SETTING_TAX_SPECIAL)
public class SpecialTaxSettingActivity extends BaseLoadActivity implements ISpecialTaxSettingContract.ISpecialTaxSettingView {
    @BindView(R.id.sts_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.sts_select_all)
    TextView mSelectAll;
    @BindView(R.id.sts_del_group)
    Group mDelGroup;
    @BindView(R.id.sts_normal_group)
    Group mNormalGroup;
    @BindView(R.id.sts_bottom_bg)
    View mBottomBg;
    @BindView(R.id.sts_search_view)
    SearchView mSearchView;
    @BindView(R.id.sts_list_view)
    RecyclerView mListView;
    @BindView(R.id.sts_confirm_del)
    TextView mConfirmDel;
    private SpecialTaxSettingAdapter mAdapter;
    private EmptyView mEmptyView;
    private ISpecialTaxSettingContract.ISpecialTaxSettingPresenter mPresenter;
    private List<SpecialTaxBean> mRawList;
    private List<SpecialTaxBean> mAllList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_tax_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (resultCode == Constants.SEARCH_RESULT_CODE) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
        if (resultCode == RESULT_OK) {
            List<SpecialTaxBean> list = data.getParcelableArrayListExtra("parcelable");
            setData(list);
        }
    }

    private void initData() {
        mPresenter = SpecialTaxSettingPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::save);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SpecialTaxSettingActivity.this, searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mAdapter.setNewData(mAllList);
                } else {
                    List<SpecialTaxBean> list = new ArrayList<>();
                    for (SpecialTaxBean bean : mAllList) {
                        if (bean.getProductName().contains(searchContent)) list.add(bean);
                    }
                    mAdapter.setNewData(list);
                }
            }
        });
        mAdapter = new SpecialTaxSettingAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SpecialTaxBean item = mAdapter.getItem(position);
            if (item == null) return;
            item.setSelected(!item.isSelected());
            mAdapter.notifyItemChanged(position);
            updateNumber();
        });
        mListView.setAdapter(mAdapter);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void updateNumber() {
        int count = 0;
        List<SpecialTaxBean> data = mAdapter.getData();
        for (SpecialTaxBean bean : data) {
            if (bean.isSelected()) {
                count++;
            }
        }
        updateCount(count);
        mSelectAll.setSelected(count == data.size());
    }

    private void updateCount(int count) {
        mConfirmDel.setText(String.format("确定删除(%s)", count));
    }

    @OnClick({R.id.sts_select_all, R.id.sts_confirm_del, R.id.sts_cancel, R.id.sts_add, R.id.sts_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sts_select_all:
                view.setSelected(!view.isSelected());
                for (SpecialTaxBean bean : mAdapter.getData()) {
                    bean.setSelected(view.isSelected());
                }
                updateCount(view.isSelected() ? mAdapter.getData().size() : 0);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.sts_confirm_del:
                List<SpecialTaxBean> list = new ArrayList<>();
                for (SpecialTaxBean bean : mAdapter.getData()) {
                    if (!bean.isSelected()) {
                        list.add(bean);
                    }
                }
                setData(list);
                updateNumber();
                break;
            case R.id.sts_cancel:
                mAdapter.setEditable(false);
                updateNumber();
                mDelGroup.setVisibility(View.GONE);
                mNormalGroup.setVisibility(View.VISIBLE);
                break;
            case R.id.sts_add:
                GoodsSelectActivity.start(this, new ArrayList<>(mAdapter.getData()));
                break;
            case R.id.sts_del:
                mAdapter.setEditable(true);
                mDelGroup.setVisibility(View.VISIBLE);
                mNormalGroup.setVisibility(View.GONE);
                break;
        }
    }

    private boolean valueEquals(String oldV, String newV) {
        if (TextUtils.isEmpty(oldV) && TextUtils.isEmpty(newV)) return true;
        double oldD = TextUtils.isEmpty(oldV) ? -1 : CommonUtils.getDouble(oldV);
        double newD = TextUtils.isEmpty(newV) ? -1 : CommonUtils.getDouble(newV);
        return oldD == newD;
    }

    private void save(View view) {
        if (!RightConfig.checkRight(getString(R.string.right_taxSetting_product_save))) {
            showToast(getString(R.string.right_tips));
            return;
        }
        SpecialTaxSaveReq req = new SpecialTaxSaveReq();
        List<SpecialTaxBean> data = mAdapter.getData(); // 编辑后列表
        String groupID = UserConfig.getGroupID();
        /*
        将最终列表中与原始列表相等的元素进行税率判断，如果不一样加入update列表

        将最终列表中有，但原始列表中没有的元素加入delete列表

        将原始列表中有，但最终列表中没有的元素加入add列表
         */
        if (!CommonUtils.isEmpty(mRawList)) {  // 原始列表不为空
            for (SpecialTaxBean rawBean : mRawList) {
                boolean hasValue = false; // 标记最终列表中是否包含原始列表中的值
                boolean update = false;
                for (SpecialTaxBean bean : data) {
                    if (TextUtils.isEmpty(bean.getTaxRate())) {
                        showToast("税率不能为空");
                        return;
                    }
                    boolean equals = rawBean.getProductID().equals(bean.getProductID());
                    if (!hasValue && equals) hasValue = true;
                    if (equals && !valueEquals(rawBean.getTaxRate(), bean.getTaxRate())) {
                        SpecialTaxSaveBean updateBean = new SpecialTaxSaveBean();
                        updateBean.setId(rawBean.getId());
                        updateBean.setProductID(rawBean.getProductID());
                        updateBean.setTaxRate(bean.getTaxRate());
                        req.getUpdateList().add(updateBean);
                        update = true;
                    }
                    if (hasValue && update) break;
                }
                if (!hasValue) { // 最终列表中无该值
                    SpecialTaxSaveBean delBean = new SpecialTaxSaveBean();
                    delBean.setId(rawBean.getId());
                    delBean.setProductID(rawBean.getProductID());
                    req.getDeleteList().add(delBean);
                }
            }
        }
        for (SpecialTaxBean bean : data) {
            boolean hasValue = false;
            if (!CommonUtils.isEmpty(mRawList)) {
                for (SpecialTaxBean rawBean : mRawList) {
                    if (rawBean.getProductID().equals(bean.getProductID())) {
                        hasValue = true;
                        break;
                    }
                }
            }
            if (!hasValue) {
                if (CommonUtils.getDouble(bean.getTaxRate()) != 0) {
                    SpecialTaxSaveBean addBean = new SpecialTaxSaveBean();
                    addBean.setGroupID(groupID);
                    addBean.setProductID(bean.getProductID());
                    addBean.setTaxRate(bean.getTaxRate());
                    req.getAddList().add(addBean);
                }
            }
        }
        mPresenter.save(req);
    }

    @Override
    public void setData(List<SpecialTaxBean> list) {
        mAllList = list;
        mAdapter.setNewData(list);
        updateList();
    }

    @Override
    public void cacheData(List<SpecialTaxBean> list) {
        mRawList = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (SpecialTaxBean record : list) {
                mRawList.add(record.copy());
            }
        }
    }

    private void updateList() {
        if (CommonUtils.isEmpty(mAdapter.getData())) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTipsTitle("您还没有设置特殊税率的商品哦");
            mEmptyView.setTips("对商品做特殊设置后，税率按照商品特殊设置值计算");
            mEmptyView.setTipsButton("选择要特殊设置的商品");
            mSearchView.setVisibility(View.GONE);
            if (mBottomBg.getVisibility() == View.VISIBLE) {
                mBottomBg.setVisibility(View.GONE);
                mDelGroup.setVisibility(View.GONE);
                mNormalGroup.setVisibility(View.GONE);
            }
        } else {
            mSearchView.setVisibility(View.VISIBLE);
            if (mBottomBg.getVisibility() != View.VISIBLE) {
                mBottomBg.setVisibility(View.VISIBLE);
                mNormalGroup.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(new EmptyView.OnActionClickListener() {
                        @Override
                        public void retry() {
                            mPresenter.start();
                        }

                        @Override
                        public void action() {
                            GoodsSelectActivity.start(SpecialTaxSettingActivity.this, new ArrayList<>(mAdapter.getData()));
                        }
                    })
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
