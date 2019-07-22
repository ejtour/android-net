package com.hll_sc_app.app.setting.priceratio.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.setting.priceratio.PriceRatioTemplateActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置比例模版
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
@Route(path = RouterConfig.SETTING_PRICE_RATIO_ADD, extras = Constant.LOGIN_EXTRA)
public class PriceRatioTemplateAddActivity extends BaseLoadActivity implements PriceRatioTemplateAddContract.IGoodsStickView {
    public static final String TYPE_ADD = "1";
    public static final String TYPE_EDIT = "2";

    @BindView(R.id.recyclerView_subId)
    RecyclerView mRecyclerViewSubId;
    @BindView(R.id.recyclerView_threeId)
    RecyclerView mRecyclerViewThreeId;
    @Autowired(name = "object0")
    String mTemplateType;
    @Autowired(name = "object1")
    String mSearchType;
    @Autowired(name = "object2")
    String mTemplateId;
    @BindView(R.id.txt_tips)
    TextView mTxtTips;

    private SubCategoryAdapter mSubAdapter;
    private ThreeCategoryAdapter mThreeAdapter;
    private PriceRatioTemplateAddPresenter mPresenter;
    private Map<String, List<CopyCategoryBean>> mThreeMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_price_ratio_template_add);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = PriceRatioTemplateAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTxtTips.setText(TextUtils.equals(PriceRatioTemplateActivity.TYPE_PRICE_MANAGE, mTemplateType) ?
            "分类名称（基于最后一次修改的成本价的百分比）" : "分类名称（基于平台售价的百分比）");
        mSubAdapter = new SubCategoryAdapter();
        mRecyclerViewSubId.setAdapter(mSubAdapter);
        mSubAdapter.setOnItemClickListener((adapter, view, position) -> {
            CopyCategoryBean bean = (CopyCategoryBean) adapter.getItem(position);
            if (bean == null || bean.isSelect()) {
                return;
            }
            List<CopyCategoryBean> beanList = mSubAdapter.getData();
            if (CommonUtils.isEmpty(beanList)) {
                return;
            }
            for (CopyCategoryBean customCategoryBean : beanList) {
                customCategoryBean.setSelect(false);
            }
            bean.setSelect(true);
            adapter.notifyDataSetChanged();
            mThreeAdapter.setNewData(mThreeMap.get(bean.getShopProductCategorySubID()));
        });

        mRecyclerViewThreeId.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mThreeAdapter = new ThreeCategoryAdapter();
        mRecyclerViewThreeId.setAdapter(mThreeAdapter);
    }

    @Override
    public void processData(List<CopyCategoryBean> list) {
        mThreeMap = new HashMap<>();
        if (CommonUtils.isEmpty(list)) {
            return;
        }

        Map<String, CopyCategoryBean> subMap = new HashMap<>();
        for (CopyCategoryBean bean : list) {
            String subId = bean.getShopProductCategorySubID();
            if (!subMap.containsKey(subId)) {
                subMap.put(subId, bean);
            }
            if (mThreeMap.containsKey(subId)) {
                mThreeMap.get(subId).add(bean);
            } else {
                List<CopyCategoryBean> copyCategoryBeans = new ArrayList<>();
                copyCategoryBeans.add(bean);
                mThreeMap.put(subId, copyCategoryBeans);
            }
        }
        List<CopyCategoryBean> listSub = new ArrayList<>(subMap.values());
        if (!CommonUtils.isEmpty(listSub)) {
            CopyCategoryBean bean = listSub.get(0);
            bean.setSelect(true);
            List<CopyCategoryBean> listThree = mThreeMap.get(bean.getShopProductCategorySubID());
            mThreeAdapter.setNewData(listThree);
        }
        mSubAdapter.setNewData(listSub);
    }

    @Override
    public String getTemplateType() {
        return mTemplateType;
    }

    @Override
    public String getSearchType() {
        return mSearchType;
    }

    @Override
    public String getTemplateId() {
        return mTemplateId;
    }

    @Override
    public void addSuccess() {
        showToast("新增成功");
        finish();
    }

    @OnClick({R.id.img_close, R.id.txt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            default:
                break;
        }
    }

    private void toSave() {

    }

    class SubCategoryAdapter extends BaseQuickAdapter<CopyCategoryBean, BaseViewHolder> {

        SubCategoryAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, CopyCategoryBean item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getShopProductCategorySubName());
            txtCategoryName.setSelected(item.isSelect());
        }
    }

    class ThreeCategoryAdapter extends BaseQuickAdapter<CopyCategoryBean, BaseViewHolder> {
        ThreeCategoryAdapter() {
            super(R.layout.item_goods_custom_category_three);
        }

        @Override
        protected void convert(BaseViewHolder helper, CopyCategoryBean item) {
            helper.setText(R.id.txt_categoryName, item.getShopProductCategoryThreeName())
                .setText(R.id.txt_ratio, item.getRatio() + "%");
        }
    }
}
