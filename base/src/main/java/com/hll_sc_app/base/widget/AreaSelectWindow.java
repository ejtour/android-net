package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.bean.AreaDtoBean;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.FileManager;

import java.util.ArrayList;
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
        mAreaBeans = getAreaList();
        if (!CommonUtils.isEmpty(mAreaBeans)) {
            // 去掉海外的城市
            mAreaBeans.remove(mAreaBeans.size() - 1);
        }
        showProvinceList();
    }

    private List<AreaBean> getAreaList() {
        String json = FileManager.getAssetsData("productarea.json", mActivity);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        } else {
            return new Gson().fromJson(json, new TypeToken<ArrayList<AreaBean>>() {
            }.getType());
        }
    }

    private void showProvinceList() {
        mTxtProvince.setSelected(true);
        mTxtCity.setSelected(false);
        mTxtDistrict.setSelected(false);
        mProvinceAdapter.setNewData(mAreaBeans);
        mRecyclerView.setAdapter(mProvinceAdapter);
        mProvinceAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSelectProvince = (AreaBean) adapter.getItem(position);
            adapter.notifyDataSetChanged();
            mTxtProvince.setText(mSelectProvince.getName());
            mTxtProvince.setTag(mSelectProvince.getCode());
            mTxtCity.setText("请选择");
            mTxtCity.setTag(null);
            mSelectCity = null;
            mTxtDistrict.setText("请选择");
            mTxtDistrict.setTag(null);
            mSelectDistrict = null;
            showCityList(mSelectProvince.getChild());
        });
    }

    private void showCityList(List<AreaBean.ChildBeanX> list) {
        mTxtProvince.setSelected(false);
        mTxtCity.setSelected(true);
        mTxtDistrict.setSelected(false);
        mCityAdapter.setNewData(list);
        mRecyclerView.setAdapter(mCityAdapter);
        mCityAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSelectCity = (AreaBean.ChildBeanX) adapter.getItem(position);
            adapter.notifyDataSetChanged();
            mTxtCity.setText(mSelectCity.getName());
            mTxtCity.setTag(mSelectCity.getCode());
            mTxtDistrict.setText("请选择");
            mTxtDistrict.setTag(null);
            mSelectDistrict = null;
            showDistrictList(mSelectCity.getChild());
        });
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
            super(R.layout.item_select_area);
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
            super(R.layout.item_select_area);
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
            super(R.layout.item_select_area);
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
