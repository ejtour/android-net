package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.bean.AreaDtoBean;
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
    private RecyclerView mListView;
    /**
     * 省
     */
    private TextView mTxtProvince;
    private View mViewProvince;

    /**
     * 市
     */
    private TextView mTxtCity;
    private View mViewCity;

    /**
     * 区
     */
    private TextView mTxtDistrict;
    private View mViewDistrict;

    private List<AreaBean> mAreaBeans;

    public AreaSelectWindow(Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.base_window_item_select_area, null);
        this.setContentView(mRootView);
        this.setWidth(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
        initView();
    }

    private void initView() {
        mListView = mRootView.findViewById(R.id.list_view);
        mListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRootView.findViewById(R.id.txt_cancel).setOnClickListener(this);
        mRootView.setOnTouchListener(new MyTouchListener());
        mTxtProvince = mRootView.findViewById(R.id.txt_area_province);
        mViewProvince = mRootView.findViewById(R.id.view_province);
        mTxtProvince.setOnClickListener(this);
        mTxtCity = mRootView.findViewById(R.id.txt_area_city);
        mTxtCity.setOnClickListener(this);
        mViewCity = mRootView.findViewById(R.id.view_city);
        mTxtDistrict = mRootView.findViewById(R.id.txt_area_district);
        mTxtDistrict.setOnClickListener(this);
        mViewDistrict = mRootView.findViewById(R.id.view_district);
        setDividerWidth(mTxtProvince, mViewProvince);
        mAreaBeans = getAreaList();
        showProvinceList();
    }

    /**
     * 设置底部横线的宽度
     *
     * @param textView 选中的view
     * @param view     底部横线
     */
    private void setDividerWidth(TextView textView, View view) {
        String text = textView.getText().toString();
        float width = textView.getPaint().measureText(text);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = (int) width;
        view.setLayoutParams(params);
    }

    private List<AreaBean> getAreaList() {
        String json = FileManager.getAssetsData("city.json", mActivity);
        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        } else {
            return new Gson().fromJson(json, new TypeToken<ArrayList<AreaBean>>() {
            }.getType());
        }
    }

    /**
     * 显示省列表
     */
    private void showProvinceList() {
        ProvinceAdapter adapter = new ProvinceAdapter(mAreaBeans);
        mListView.setAdapter(adapter);
        adapter.setSelectListener(bean -> {
            mSelectProvince = bean;
            adapter.notifyDataSetChanged();
            if (bean != null) {
                setCitySelect();
                mTxtProvince.setText(bean.getName());
                mTxtProvince.setTag(bean.getCode());
                mTxtCity.setText("请选择");
                mTxtCity.setTag(null);
                mSelectCity = null;
                mTxtDistrict.setText("请选择");
                mTxtDistrict.setTag(null);
                mSelectDistrict = null;
                showCityList(bean.getChild());
            }
            setDividerWidth(mTxtProvince, mViewProvince);
        });
    }

    private void setCitySelect() {
        setDividerWidth(mTxtCity, mViewCity);
        mViewProvince.setVisibility(View.GONE);
        mViewCity.setVisibility(View.VISIBLE);
        mViewDistrict.setVisibility(View.GONE);
    }

    /**
     * 显示市列表
     */
    private void showCityList(List<AreaBean.ChildBeanX> list) {
        CityAdapter adapter = new CityAdapter(list);
        mListView.setAdapter(adapter);
        adapter.setSelectListener(bean -> {
            mSelectCity = bean;
            adapter.notifyDataSetChanged();
            if (bean != null) {
                setDistrictSelect();
                mTxtCity.setText(bean.getName());
                mTxtCity.setTag(bean.getCode());
                mTxtDistrict.setText("请选择");
                mTxtDistrict.setTag(null);
                mSelectDistrict = null;
                showDistrictList(bean.getChild());
            }
            setDividerWidth(mTxtCity, mViewCity);
        });
    }

    private void setDistrictSelect() {
        setDividerWidth(mTxtDistrict, mViewDistrict);
        mViewProvince.setVisibility(View.GONE);
        mViewCity.setVisibility(View.GONE);
        mViewDistrict.setVisibility(View.VISIBLE);
    }

    /**
     * 显示区列表
     */
    private void showDistrictList(List<AreaBean.ChildBeanX.ChildBean> list) {
        DistrictAdapter adapter = new DistrictAdapter(list);
        mListView.setAdapter(adapter);
        adapter.setSelectListener(bean -> {
            mSelectDistrict = bean;
            adapter.notifyDataSetChanged();
            if (bean != null) {
                mTxtDistrict.setText(bean.getName());
                mTxtDistrict.setTag(bean.getCode());
            }
            setDividerWidth(mTxtDistrict, mViewDistrict);
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
     * @param selectListener 选中监听—
     */
    public void setResultSelectListener(OnSelectListener selectListener) {
        this.mResultListener = selectListener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txt_area_province) {
            setProvinceSelect();
            showProvinceList();
        } else if (view.getId() == R.id.txt_area_city) {
            if (mSelectProvince != null) {
                setCitySelect();
                showCityList(mSelectProvince.getChild());
            }

        } else if (view.getId() == R.id.txt_area_district) {
            if (mSelectCity != null) {
                setDistrictSelect();
                showDistrictList(mSelectCity.getChild());
            }

        } else if (view.getId() == R.id.txt_cancel) {
            dismiss();
        }
    }

    private void setProvinceSelect() {
        setDividerWidth(mTxtProvince, mViewProvince);
        mViewProvince.setVisibility(View.VISIBLE);
        mViewCity.setVisibility(View.GONE);
        mViewDistrict.setVisibility(View.GONE);
    }

    /**
     * 初始化显示 设置初始显示的省市区
     *
     * @param bean 省市区
     */
    public void setSelect(AreaDtoBean bean) {
        // 省市区的级别 1 省 2 市 3 区
        int level = 0;
        if (!TextUtils.isEmpty(bean.getShopProvinceCode())) {
            level = 1;
            if (!TextUtils.isEmpty(bean.getShopCityCode())) {
                level = 2;
                if (!TextUtils.isEmpty(bean.getShopDistrictCode())) {
                    level = 3;
                }
            }
        }
        if (level == 0) {
            return;
        }
        for (AreaBean areaBean : mAreaBeans) {
            if (TextUtils.equals(areaBean.getCode(), bean.getShopProvinceCode())) {
                mSelectProvince = areaBean;
                if (level == 1) {
                    break;
                }
                for (AreaBean.ChildBeanX cityBean : areaBean.getChild()) {
                    if (TextUtils.equals(cityBean.getCode(), bean.getShopCityCode())) {
                        mSelectCity = cityBean;
                        if (level == 2) {
                            break;
                        }
                        for (AreaBean.ChildBeanX.ChildBean districtBean : cityBean.getChild()) {
                            if (TextUtils.equals(districtBean.getCode(), bean.getShopDistrictCode())) {
                                mSelectDistrict = districtBean;
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }
        if (level == 1 && mSelectProvince != null) {
            mTxtProvince.setText(bean.getShopProvince());
            setProvinceSelect();
            showProvinceList();
        } else if (level == 2 && mSelectCity != null && mSelectProvince != null) {
            mTxtProvince.setText(bean.getShopProvince());
            mTxtCity.setText(bean.getShopCity());
            setCitySelect();
            showCityList(mSelectProvince.getChild());
        } else if (level == 3 && mSelectCity != null && mSelectProvince != null && mSelectDistrict != null) {
            mTxtProvince.setText(bean.getShopProvince());
            mTxtCity.setText(bean.getShopCity());
            mTxtDistrict.setText(bean.getShopDistrict());
            setDistrictSelect();
            showDistrictList(mSelectCity.getChild());
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

    /**
     * 省份选择回调
     */
    interface OnProvinceSelect {
        /**
         * 省份选择完成
         *
         * @param bean 省份bean
         */
        void onSelect(AreaBean bean);
    }

    /**
     * 市选择回调
     */
    interface OnCitySelect {
        /**
         * 市选择完成
         *
         * @param bean 省份bean
         */
        void onSelect(AreaBean.ChildBeanX bean);
    }

    /**
     * 区选择回调
     */
    interface OnDistrictSelect {
        /**
         * 区选择完成
         *
         * @param bean 省份bean
         */
        void onSelect(AreaBean.ChildBeanX.ChildBean bean);
    }

    /**
     * 省适配器
     */
    private class ProvinceAdapter extends BaseQuickAdapter<AreaBean, BaseViewHolder> {
        private OnProvinceSelect mListener;

        ProvinceAdapter(@Nullable List<AreaBean> data) {
            super(R.layout.item_select_area, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            helper.setGone(R.id.img_select, mSelectProvince == item);
            txtType.setSelected(mSelectProvince == item);
            helper.itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onSelect(item);
                }
            });
        }

        void setSelectListener(OnProvinceSelect listener) {
            this.mListener = listener;
        }
    }

    /**
     * 市适配器
     */
    private class CityAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX, BaseViewHolder> {
        private OnCitySelect mListener;

        CityAdapter(@Nullable List<AreaBean.ChildBeanX> data) {
            super(R.layout.item_select_area, data);
        }

        void setSelectListener(OnCitySelect listener) {
            this.mListener = listener;
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            helper.setGone(R.id.img_select, mSelectCity == item);
            txtType.setSelected(mSelectCity == item);
            helper.itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onSelect(item);
                }
            });
        }
    }

    /**
     * 市适配器
     */
    private class DistrictAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX.ChildBean, BaseViewHolder> {
        private OnDistrictSelect mListener;

        DistrictAdapter(@Nullable List<AreaBean.ChildBeanX.ChildBean> data) {
            super(R.layout.item_select_area, data);
        }

        void setSelectListener(OnDistrictSelect listener) {
            this.mListener = listener;
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX.ChildBean item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            helper.setGone(R.id.img_select, mSelectDistrict == item);
            txtType.setSelected(mSelectDistrict == item);
            helper.itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    mListener.onSelect(item);
                }
            });
        }
    }

    /**
     * 触摸监听器
     */
    private class MyTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int height = mRootView.findViewById(R.id.pop_layout).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
                if (y < height) {
                    dismiss();
                }
            }
            return true;
        }
    }

}
