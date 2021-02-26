package com.hll_sc_app.app.setting.priceratio.add;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.app.setting.priceratio.PriceRatioTemplateListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.CategoryRatioListBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
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
public class PriceRatioTemplateAddActivity extends BaseLoadActivity implements PriceRatioTemplateAddContract.IPriceRatioTemplateAddView {
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
    @Autowired(name = "object3")
    String mTemplateName;
    @BindView(R.id.txt_tips)
    TextView mTxtTips;
    @BindView(R.id.edt_templateName)
    EditText mEdtTemplateName;

    private SubCategoryAdapter mSubAdapter;
    private ThreeCategoryAdapter mThreeAdapter;
    private PriceRatioTemplateAddPresenter mPresenter;
    private Map<String, List<CategoryRatioListBean>> mThreeMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_price_ratio_template_add);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = PriceRatioTemplateAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mEdtTemplateName.setText(mTemplateName);
        mTxtTips.setText(TextUtils.equals(PriceRatioTemplateListActivity.TYPE_PRICE_MANAGE, mTemplateType) ?
                "分类名称（基于最后一次修改的成本价的百分比）" : "分类名称（基于平台售价的百分比）");
        mSubAdapter = new SubCategoryAdapter();
        mRecyclerViewSubId.setAdapter(mSubAdapter);
        mSubAdapter.setOnItemClickListener((adapter, view, position) -> {
            CategoryRatioListBean bean = (CategoryRatioListBean) adapter.getItem(position);
            if (bean == null || bean.isSelect()) {
                return;
            }
            List<CategoryRatioListBean> beanList = mSubAdapter.getData();
            if (CommonUtils.isEmpty(beanList)) {
                return;
            }
            for (CategoryRatioListBean customCategoryBean : beanList) {
                customCategoryBean.setSelect(false);
            }
            bean.setSelect(true);
            adapter.notifyDataSetChanged();
            mThreeAdapter.setNewData(mThreeMap.get(bean.getShopProductCategorySubID()));
        });

        mRecyclerViewThreeId.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mThreeAdapter = new ThreeCategoryAdapter();
        mThreeAdapter.setOnItemClickListener((adapter, view, position) -> {
            CategoryRatioListBean bean = (CategoryRatioListBean) adapter.getItem(position);
            if (bean != null) {
                showInputDialog(bean);
            }
        });
        mRecyclerViewThreeId.setAdapter(mThreeAdapter);
    }

    private void showInputDialog(CategoryRatioListBean bean) {
        String ration = CommonUtils.formatNumber(bean.getRatio());
        InputDialog.newBuilder(this)
            .setCancelable(false)
            .setTextTitle("输入" + bean.getShopProductCategoryThreeName() + "的比例")
            .setHint("输入比例")
            .setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
            .setText(TextUtils.equals(ration, "0") ? null : ration)
            .setTextWatcher((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
                if (!GoodsSpecsAddActivity.PRODUCT_PRICE.matcher(s.toString()).find() && s.length() > 1) {
                    s.delete(s.length() - 1, s.length());
                    showToast("比例设置支持7位整数或小数点后两位");
                }
            })
            .setButton((dialog, item) -> {
                if (item == 1) {
                    if (TextUtils.isEmpty(dialog.getInputString())) {
                        showToast("输入比例不能为空");
                        return;
                    }
                    bean.setRatio(dialog.getInputString());
                    mThreeAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create().show();
    }

    @Override
    public void processData(List<CategoryRatioListBean> list) {
        mThreeMap = new HashMap<>();
        if (CommonUtils.isEmpty(list)) {
            return;
        }

        Map<String, CategoryRatioListBean> subMap = new HashMap<>();
        for (CategoryRatioListBean bean : list) {
            String subId = bean.getShopProductCategorySubID();
            if (!subMap.containsKey(subId)) {
                subMap.put(subId, bean);
            }
            if (mThreeMap.containsKey(subId)) {
                mThreeMap.get(subId).add(bean);
            } else {
                List<CategoryRatioListBean> CategoryRatioListBeans = new ArrayList<>();
                CategoryRatioListBeans.add(bean);
                mThreeMap.put(subId, CategoryRatioListBeans);
            }
        }
        List<CategoryRatioListBean> listSub = new ArrayList<>(subMap.values());
        if (!CommonUtils.isEmpty(listSub)) {
            CategoryRatioListBean bean = listSub.get(0);
            bean.setSelect(true);
            List<CategoryRatioListBean> listThree = mThreeMap.get(bean.getShopProductCategorySubID());
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
        ARouter.getInstance().build(RouterConfig.SETTING_PRICE_RATIO_LIST)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
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
        if (TextUtils.isEmpty(mEdtTemplateName.getText())) {
            showToast("分组名称不能为空");
            return;
        }
        RatioTemplateBean bean = new RatioTemplateBean();
        bean.setGroupID(UserConfig.getGroupID());
        bean.setTemplateID(getTemplateId());
        bean.setTemplateType(getTemplateType());
        bean.setTemplateName(mEdtTemplateName.getText().toString());
        bean.setCategoryRatioList(mThreeAdapter.getData());
        if (!TextUtils.isEmpty(bean.getTemplateID())) {
            mPresenter.editRatioTemplate(bean);
        } else {
            mPresenter.addRatioTemplate(bean);
        }
    }

    class SubCategoryAdapter extends BaseQuickAdapter<CategoryRatioListBean, BaseViewHolder> {

        SubCategoryAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryRatioListBean item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getShopProductCategorySubName());
            txtCategoryName.setSelected(item.isSelect());
        }
    }

    class ThreeCategoryAdapter extends BaseQuickAdapter<CategoryRatioListBean, BaseViewHolder> {
        ThreeCategoryAdapter() {
            super(R.layout.item_goods_custom_category_three);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryRatioListBean item) {
            helper.setText(R.id.txt_categoryName, item.getShopProductCategoryThreeName())
                .setText(R.id.txt_ratio, item.getRatio());
        }
    }
}
