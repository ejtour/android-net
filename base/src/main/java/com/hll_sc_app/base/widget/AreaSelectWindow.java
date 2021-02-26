package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.bean.AreaDtoBean;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * 选择地区
 *
 * @author zys
 * @date 2018/8/7
 */
public class AreaSelectWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private DistrictAdapter mDistrictAdapter;
    /**
     * 选中的省
     */
    private AreaBean mSelectProvince;
    /**
     * 选中的市
     */
    private AreaBean.ChildBeanX mSelectCity;
    /**
     * 选中的区
     */
    private AreaBean.ChildBeanX.ChildBean mSelectDistrict;
    private View mRootView;
    /**
     * 结果传回调用的Activity
     */
    private OnSelectListener mResultListener;
    /**
     * 省
     */
    private TextView mTxtProvince;
    /**
     * 市
     */
    private TextView mTxtCity;
    /**
     * 区
     */
    private TextView mTxtDistrict;
    private List<AreaBean> mAreaBeans;

    public AreaSelectWindow(Activity context) {
        super(context);
        mRootView = View.inflate(context, R.layout.base_window_item_select_area, null);
        this.setContentView(mRootView);
        this.setWidth(RecyclerView.LayoutParams.MATCH_PARENT);
        this.setHeight(UIUtils.dip2px(465));
        this.setFocusable(true);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setBackgroundDrawable(new ColorDrawable(0x0000000));
        initView();
    }

    private void initView() {
        mRecyclerView = mRootView.findViewById(R.id.list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mProvinceAdapter = new ProvinceAdapter();
        mCityAdapter = new CityAdapter();
        mDistrictAdapter = new DistrictAdapter();

        mRootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        mTxtProvince = mRootView.findViewById(R.id.txt_area_province);
        mTxtProvince.setOnClickListener(this);
        mTxtCity = mRootView.findViewById(R.id.txt_area_city);
        mTxtCity.setOnClickListener(this);
        mTxtDistrict = mRootView.findViewById(R.id.txt_area_district);
        mTxtDistrict.setOnClickListener(this);
        mAreaBeans = UIUtils.getAreaList(mActivity, false);
        showProvinceList();
    }

    private void showProvinceList() {
        mTxtProvince.setSelected(true);
        mTxtCity.setSelected(false);
        mTxtDistrict.setSelected(false);
        mProvinceAdapter.setNewData(mAreaBeans);
        mRecyclerView.setAdapter(mProvinceAdapter);
        mProvinceAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSelectProvince = (AreaBean) adapter.getItem(position);
            selectProvince();
        });
    }

    private void selectProvince() {
        mProvinceAdapter.notifyDataSetChanged();
        mTxtProvince.setText(mSelectProvince.getName());
        mTxtProvince.setTag(mSelectProvince.getCode());
        mTxtCity.setText("请选择");
        mTxtCity.setTag(null);
        mSelectCity = null;
        mTxtDistrict.setText("请选择");
        mTxtDistrict.setTag(null);
        mSelectDistrict = null;
        showCityList(mSelectProvince.getChild());
    }

    private void showCityList(List<AreaBean.ChildBeanX> list) {
        mTxtProvince.setSelected(false);
        mTxtCity.setSelected(true);
        mTxtDistrict.setSelected(false);
        mCityAdapter.setNewData(list);
        mRecyclerView.setAdapter(mCityAdapter);
        mCityAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSelectCity = (AreaBean.ChildBeanX) adapter.getItem(position);
            selectCity();
        });
    }

    private void selectCity() {
        mCityAdapter.notifyDataSetChanged();
        mTxtCity.setText(mSelectCity.getName());
        mTxtCity.setTag(mSelectCity.getCode());
        mTxtDistrict.setText("请选择");
        mTxtDistrict.setTag(null);
        mSelectDistrict = null;
        showDistrictList(mSelectCity.getChild());
    }

    private void showDistrictList(List<AreaBean.ChildBeanX.ChildBean> list) {
        mTxtProvince.setSelected(false);
        mTxtCity.setSelected(false);
        mTxtDistrict.setSelected(true);
        mDistrictAdapter.setNewData(list);
        mRecyclerView.setAdapter(mDistrictAdapter);
        mDistrictAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSelectDistrict = (AreaBean.ChildBeanX.ChildBean) adapter.getItem(position);
            if (mSelectDistrict == null) {
                return;
            }
            mDistrictAdapter.notifyDataSetChanged();
            mTxtDistrict.setText(mSelectDistrict.getName());
            mTxtDistrict.setTag(mSelectDistrict.getCode());
            if (mResultListener != null) {
                // 完成选择进行回调
                AreaDtoBean areaBean = new AreaDtoBean();
                areaBean.setShopCity(mTxtCity.getText().toString());
                areaBean.setShopCityCode((String) mTxtCity.getTag());
                areaBean.setShopDistrict(mTxtDistrict.getText().toString());
                areaBean.setShopDistrictCode((String) mTxtDistrict.getTag());
                areaBean.setShopProvince(mTxtProvince.getText().toString());
                areaBean.setShopProvinceCode((String) mTxtProvince.getTag());
                mResultListener.onSelectItem(areaBean);
                dismiss();
            }
        });
    }

    /**
     * 设置选中监听器
     *
     * @param listener 选中监听—
     */
    public void setResultSelectListener(OnSelectListener listener) {
        this.mResultListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txt_area_province) {
            showProvinceList();
        } else if (view.getId() == R.id.txt_area_city) {
            if (mSelectProvince != null) {
                showCityList(mSelectProvince.getChild());
            }

        } else if (view.getId() == R.id.txt_area_district) {
            if (mSelectCity != null) {
                showDistrictList(mSelectCity.getChild());
            }
        } else if (view.getId() == R.id.txt_cancel) {
            dismiss();
        }
    }

    /**
     * 设置选中
     *
     * @param provinceCode 省-code
     * @param cityCode     市-code
     * @param districtCode 区-code
     */
    public void select(String provinceCode, String cityCode, String districtCode) {
        // 省
        if (TextUtils.isEmpty(provinceCode)) {
            return;
        }
        List<AreaBean> listProvince = mProvinceAdapter.getData();
        if (CommonUtils.isEmpty(listProvince)) {
            return;
        }
        for (AreaBean areaBean : listProvince) {
            if (TextUtils.equals(provinceCode, areaBean.getCode())) {
                mSelectProvince = areaBean;
                selectProvince();
                break;
            }
        }

        // 市
        if (TextUtils.isEmpty(cityCode)) {
            return;
        }
        List<AreaBean.ChildBeanX> listCity = mCityAdapter.getData();
        if (CommonUtils.isEmpty(listCity)) {
            return;
        }
        for (AreaBean.ChildBeanX city : listCity) {
            if (TextUtils.equals(cityCode, city.getCode())) {
                mSelectCity = city;
                selectCity();
                break;
            }
        }

        // 区
        if (TextUtils.isEmpty(districtCode)) {
            return;
        }
        List<AreaBean.ChildBeanX.ChildBean> listDistrict = mDistrictAdapter.getData();
        if (CommonUtils.isEmpty(listDistrict)) {
            return;
        }
        for (AreaBean.ChildBeanX.ChildBean district : listDistrict) {
            if (TextUtils.equals(districtCode, district.getCode())) {
                mSelectDistrict = district;
                mTxtDistrict.setText(mSelectDistrict.getName());
                mTxtDistrict.setTag(mSelectDistrict.getCode());
                mDistrictAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 选中回调接口
     */
    public interface OnSelectListener {
        /**
         * 省市区选择完成之后回调
         *
         * @param t 回调结果
         */
        void onSelectItem(AreaDtoBean t);
    }

    private class ProvinceAdapter extends BaseQuickAdapter<AreaBean, BaseViewHolder> {
        ProvinceAdapter() {
            super(R.layout.base_item_select_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            txtType.setSelected(mSelectProvince == item);
            helper.setGone(R.id.img_select, mSelectProvince == item);
        }
    }

    private class CityAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX, BaseViewHolder> {
        CityAdapter() {
            super(R.layout.base_item_select_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            txtType.setSelected(mSelectCity == item);
            helper.setGone(R.id.img_select, mSelectCity == item);
        }
    }

    private class DistrictAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX.ChildBean, BaseViewHolder> {

        DistrictAdapter() {
            super(R.layout.base_item_select_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX.ChildBean item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            txtType.setSelected(mSelectDistrict == item);
            helper.setGone(R.id.img_select, mSelectDistrict == item);
        }
    }
}
