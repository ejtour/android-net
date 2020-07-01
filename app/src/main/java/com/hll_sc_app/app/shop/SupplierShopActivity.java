package com.hll_sc_app.app.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.user.register.RegisterCategoryWindow;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.AreaSelectWindow;
import com.hll_sc_app.bean.shop.SupplierShopBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TimeIntervalWindow;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.SUPPLIER_SHOP, extras = Constant.LOGIN_EXTRA)
public class SupplierShopActivity extends BaseLoadActivity implements ISupplierShopContract.ISupplierShopView {

    @BindView(R.id.txt_category_content)
    TextView mCategoryView;
    @BindView(R.id.txt_shop_area_content)
    TextView mAreaView;
    @BindView(R.id.txt_open_time_content)
    TextView mOpenTimeView;
    @BindView(R.id.et_shop_phone)
    EditText mPhoneText;
    @BindView(R.id.et_shop_admin)
    EditText mAdminText;
    @BindView(R.id.et_shop_address)
    EditText mAddressText;
    @BindView(R.id.txt_shop_name_content)
    TextView mShopNameView;
    @BindView(R.id.img_imagePath)
    GlideImageView mImageView;
    @BindView(R.id.txt_status_content)
    TextView mStatusView;

    private SupplierShopBean mShopBean;

    private ISupplierShopContract.ISupplierShopPresenter mPresenter;
    private RegisterCategoryWindow mCategoryWindow;
    private AreaSelectWindow mAreaWindow;
    private TimeIntervalWindow mTimeWindow;
    private SingleSelectionDialog mStatusDialog;

    public static final int REQUEST_CODE_CHOOSE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_shop_detail);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        mPresenter = SupplierShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mPresenter.listSupplierShop();
    }

    @Override
    public void show(SupplierShopBean bean) {
        this.mShopBean = bean;
        mImageView.setImageURL(bean.getLogoUrl());
        mShopNameView.setText(bean.getShopName());
        mPhoneText.setText(bean.getShopPhone());
        mAdminText.setText(bean.getShopAdmin());
        mAreaView.setText(bean.getShopProvince() + "-" + bean.getShopCity() + "-" + bean.getShopDistrict());
        mAddressText.setText(bean.getShopAddress());
        mOpenTimeView.setText(bean.getBusinessStartTime() + "-" + bean.getBusinessEndTime());
        mStatusView.setText(bean.getIsActive() == 0 ? "暂停营业" : "正常营业");
        mCategoryView.setText(bean.getCategoryNameList());
    }

    @Override
    public void success() {
        showToast("保存店铺成功");
        finish();
    }

    @Override
    public void showPhoto(String url) {
        mShopBean.setLogoUrl(url);
        mImageView.setImageURL(url);
    }

    @OnClick({R.id.img_close, R.id.txt_set, R.id.rl_shop_area, R.id.rl_open_time, R.id.rl_status,
            R.id.rl_category, R.id.img_imagePath})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_set:
                //保存
                save();
                break;
            case R.id.rl_shop_area:
                showAreaWindow();
                break;
            case R.id.rl_open_time:
                showOpenTimeWindow();
                break;
            case R.id.rl_status:
                showStatusWindow();
                break;
            case R.id.rl_category:
                mPresenter.showCategory();
                break;
            case R.id.img_imagePath:
                selectPhoto();
                break;
            default:
                break;
        }
    }

    private void selectPhoto() {
        UIUtils.selectPhoto(this, REQUEST_CODE_CHOOSE);
    }

    private void save() {
        mShopBean.setShopPhone(mPhoneText.getText().toString());
        mShopBean.setShopAdmin(mAdminText.getText().toString());
        mShopBean.setShopAddress(mAddressText.getText().toString());
        mPresenter.updateSupplierShop(mShopBean);
    }

    /**
     * 营业状态选择
     */
    private void showStatusWindow() {
        if (mStatusDialog == null) {
            List<NameValue> values = new ArrayList<>();
            values.add(new NameValue("正常营业", "1"));
            values.add(new NameValue("暂停营业", "0"));
            mStatusDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("营业状态")
                    .setOnSelectListener(bean -> {
                        mShopBean.setIsActive(CommonUtils.getInt(bean.getValue()));
                        mStatusView.setText(bean.getName());
                    })
                    .refreshList(values)
                    .create();
        }
        mStatusDialog.show();
    }

    /**
     * 营业时间选择
     */
    private void showOpenTimeWindow() {
        if (mTimeWindow == null) {
            mTimeWindow = new TimeIntervalWindow(this);
            mTimeWindow.initTime(mShopBean.getBusinessStartTime() + "-" + mShopBean.getBusinessEndTime());
            mTimeWindow.setOnTimeSelectListener(bean -> {
                String[] time = bean.split("-");
                if (time.length == 2) {
                    mShopBean.setBusinessStartTime(time[0]);
                    mShopBean.setBusinessEndTime(time[1]);
                }
                mOpenTimeView.setText(bean);
            });
        }
        mTimeWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 地区选择
     */
    private void showAreaWindow() {
        if (mAreaWindow == null) {
            mAreaWindow = new AreaSelectWindow(this);
            mAreaWindow.select(mShopBean.getShopProvinceCode(), mShopBean.getShopCityCode(), mShopBean.getShopDistrictCode());
            mAreaWindow.setResultSelectListener(bean -> {
                mAreaView.setText(String.format("%s-%s-%s",
                        bean.getShopProvince(), bean.getShopCity(), bean.getShopDistrict()));
                mAreaView.setTag(bean);
                mShopBean.setShopProvince(bean.getShopProvince());
                mShopBean.setShopProvinceCode(bean.getShopProvinceCode());
                mShopBean.setShopCity(bean.getShopCity());
                mShopBean.setShopCityCode(bean.getShopCityCode());
                mShopBean.setShopDistrict(bean.getShopDistrict());
                mShopBean.setShopDistrictCode(bean.getShopDistrictCode());
            });
        }
        mAreaWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示分类列表
     *
     * @param list list 集合数据
     */
    public void showCategoryWindow(List<CategoryItem> list) {
        if (mCategoryWindow == null) {
            mCategoryWindow = new RegisterCategoryWindow(this);

            mCategoryWindow.setListener(() -> {
                List<CategoryItem> listSelect = mCategoryWindow.getSelectList();
                StringBuffer categoryNames = new StringBuffer();
                StringBuffer categoryIDs = new StringBuffer();
                for (int i = 0; i < listSelect.size(); i++) {
                    CategoryItem item = listSelect.get(i);
                    categoryNames.append(item.getCategoryName());
                    categoryIDs.append(item.getCategoryID());
                    if (i != listSelect.size() - 1) {
                        categoryNames.append(",");
                        categoryIDs.append("、");
                    }
                }
                mCategoryView.setText(categoryNames.toString());
                mShopBean.setCategoryIDList(categoryIDs.toString());
                mShopBean.setCategoryNameList(categoryNames.toString());
            });
        }
        mCategoryWindow.setList(list);
        if (mShopBean.getCategoryIDList() != null && !mShopBean.getCategoryIDList().equalsIgnoreCase("")) {
            List<String> categoryList = Arrays.asList(mShopBean.getCategoryIDList().split(","));
            mCategoryWindow.setSelectList(categoryList);
        }
        mCategoryWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && data != null) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) {
                mPresenter.imageUpload(list.get(0));
            }
        }
    }
}
