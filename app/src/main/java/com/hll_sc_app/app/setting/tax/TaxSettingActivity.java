package com.hll_sc_app.app.setting.tax;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.stockmanage.selectproduct.ProductSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.user.TaxSaveBean;
import com.hll_sc_app.bean.user.TaxSaveReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
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

@Route(path = RouterConfig.SETTING_TAX)
public class TaxSettingActivity extends BaseLoadActivity implements ITaxSettingContract.ITaxSettingView {
    @BindView(R.id.ats_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.ats_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.ats_right_list)
    RecyclerView mRightList;
    private ProductSelectActivity.CategoryAdapter mAdapter;
    private TaxSettingAdapter mSettingAdapter;
    private ITaxSettingContract.ITaxSettingPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_tax_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> {
            TaxSaveReq req = new TaxSaveReq();
            req.setGroupID(UserConfig.getGroupID());
            List<TaxSaveBean> list = new ArrayList<>();
            req.setCategorys(list);
            List<CustomCategoryBean> data = mAdapter.getData();
            for (CustomCategoryBean bean : data) {
                for (CustomCategoryBean categoryBean : bean.getSubList()) {
                    list.add(new TaxSaveBean(categoryBean.getId(), categoryBean.getTaxRate()));
                }
            }
            mPresenter.save(req);
        });
        mAdapter = new ProductSelectActivity.CategoryAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CustomCategoryBean item = mAdapter.getItem(position);
            if (item != null) {
                if (item.isChecked()) return;
                for (CustomCategoryBean bean : mAdapter.getData()) {
                    bean.setChecked(false);
                }
                item.setChecked(true);
                mAdapter.notifyDataSetChanged();
                mSettingAdapter.setNewData(item.getSubList());
            }
        });
        mLeftList.setAdapter(mAdapter);
        mSettingAdapter = new TaxSettingAdapter();
        mRightList.setAdapter(mSettingAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0);
        mRightList.addItemDecoration(decor);
    }

    private void initData() {
        mPresenter = new TaxSettingPresenter();
        mPresenter.register(this);
        mPresenter.start();
    }

    @OnClick(R.id.ats_setting)
    public void onViewClicked() {

    }

    @Override
    public void setCategoryList(List<CustomCategoryBean> list) {
        mAdapter.setNewData(list);
        if (!CommonUtils.isEmpty(list)) {
            CustomCategoryBean bean = list.get(0);
            bean.setChecked(true);
            mSettingAdapter.setNewData(bean.getSubList());
        }
    }

    @Override
    public void saveSuccess() {
        finish();
    }
}
