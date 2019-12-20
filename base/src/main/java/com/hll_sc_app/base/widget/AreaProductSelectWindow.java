package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.bean.AreaDtoBean;
import com.hll_sc_app.base.bean.WrapperChildBeanX;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.FileManager;

import java.util.ArrayList;
import java.util.List;

import qdx.indexbarlayout.IndexLayout;

/**
 * 选择地区
 *
 * @author zys
 * @date 2019/6/25
 */
public class AreaProductSelectWindow extends BaseShadowPopupWindow implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private CityWrapperAdapter mCityWrapperAdapter;
    /**
     * 选中的省
     */
    private AreaBean mSelectProvince;
    /**
     * 选中的市
     */
    private AreaBean.ChildBeanX mSelectCity;
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
    private List<AreaBean> mAreaBeans;
    private IndexLayout mIndexLayout;

    public AreaProductSelectWindow(Activity context) {
        super(context);
        mRootView = View.inflate(context, R.layout.base_window_item_select_product_area, null);
        this.setContentView(mRootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(UIUtils.dip2px(465));
        this.setFocusable(true);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setBackgroundDrawable(new ColorDrawable(0x0000000));
        initView();
    }

    private void initView() {
        mRootView.findViewById(R.id.img_cancel).setOnClickListener(this);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mProvinceAdapter = new ProvinceAdapter();
        mCityAdapter = new CityAdapter();
        mCityWrapperAdapter = new CityWrapperAdapter();
        mRecyclerView.setAdapter(mProvinceAdapter);
        mTxtProvince = mRootView.findViewById(R.id.txt_area_province);
        mTxtProvince.setOnClickListener(this);
        mTxtCity = mRootView.findViewById(R.id.txt_area_city);
        mTxtCity.setOnClickListener(this);
        mAreaBeans = getAreaList();
        mIndexLayout = mRootView.findViewById(R.id.index_layout);
        showProvinceList();
        mIndexLayout.setCircleDuration(1000);
        mIndexLayout.setCircleColor(0x70000000);
        mIndexLayout.setIndexBarHeightRatio(0.9f);
        List<String> indexList = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            indexList.add(String.valueOf((char) (64 + i)));
        }
        mIndexLayout.getIndexBar().setIndexTextSize(UIUtils.dip2px(12));
        mIndexLayout.getIndexBar().setIndexsList(indexList);
        mIndexLayout.getIndexBar().setIndexChangeListener(s -> {
            if (mCityWrapperAdapter != null) {
                for (int i = 0; i < mCityWrapperAdapter.getData().size(); i++) {
                    WrapperChildBeanX wrapper = mCityWrapperAdapter.getItem(i);
                    if (wrapper != null && wrapper.isHeader && TextUtils.equals(s, wrapper.header)) {
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        break;
                    }
                }
            }
        });
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

    /**
     * 显示省列表
     */
    private void showProvinceList() {
        mIndexLayout.setVisibility(View.GONE);
        mTxtProvince.setSelected(true);
        mTxtCity.setSelected(false);
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
            showCityList(mSelectProvince.getChild(), TextUtils.equals(mSelectProvince.getName(), "海外"));
        });
    }

    /**
     * 显示市列表
     */
    private void showCityList(List<AreaBean.ChildBeanX> list, boolean isOverSeas) {
        mTxtCity.setSelected(true);
        mTxtProvince.setSelected(false);
        if (isOverSeas) {
            mIndexLayout.setVisibility(View.VISIBLE);
            mCityWrapperAdapter.setNewData(processData(list));
            mCityWrapperAdapter.setOnItemClickListener((adapter, view, position) -> {
                WrapperChildBeanX wrapperBean = (WrapperChildBeanX) adapter.getItem(position);
                if (wrapperBean == null || wrapperBean.isHeader) {
                    return;
                }
                mSelectCity = wrapperBean.t;
                adapter.notifyDataSetChanged();
                selectCity();
            });
            mRecyclerView.setAdapter(mCityWrapperAdapter);
        } else {
            mIndexLayout.setVisibility(View.GONE);
            mCityAdapter.setNewData(list);
            mCityAdapter.setOnItemClickListener((adapter, view, position) -> {
                mSelectCity = (AreaBean.ChildBeanX) adapter.getItem(position);
                adapter.notifyDataSetChanged();
                selectCity();
            });
            mRecyclerView.setAdapter(mCityAdapter);
        }
    }

    private List<WrapperChildBeanX> processData(List<AreaBean.ChildBeanX> list) {
        List<WrapperChildBeanX> wrapperList = new ArrayList<>();
        if (CommonUtils.isEmpty(list)) {
            return wrapperList;
        }
        String title = null;
        for (AreaBean.ChildBeanX bean : list) {
            if (!TextUtils.equals(title, bean.getInitial())) {
                title = bean.getInitial();
                wrapperList.add(new WrapperChildBeanX(true, bean.getInitial()));
            }
            wrapperList.add(new WrapperChildBeanX(bean));
        }
        return wrapperList;
    }

    private void selectCity() {
        mTxtCity.setText(mSelectCity.getName());
        mTxtCity.setTag(mSelectCity.getCode());
        // 选中市直接关闭
        AreaDtoBean areaBean = new AreaDtoBean();
        areaBean.setShopProvince(mTxtProvince.getText().toString());
        areaBean.setShopProvinceCode((String) mTxtProvince.getTag());
        areaBean.setShopCity(mTxtCity.getText().toString());
        areaBean.setShopCityCode((String) mTxtCity.getTag());
        mResultListener.onSelectItem(areaBean);
        dismiss();
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
        int id = view.getId();
        if (id == R.id.img_cancel) {
            dismiss();
        } else if (id == R.id.txt_area_province) {
            showProvinceList();
        } else if (id == R.id.txt_area_city) {
            if (mSelectProvince != null) {
                showCityList(mSelectProvince.getChild(), TextUtils.equals(mSelectProvince.getName(), "海外"));
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
            super(R.layout.base_item_select_area, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            if (mSelectProvince == item) {
                helper.setGone(R.id.img_select, true);
                txtType.setSelected(true);
            } else {
                txtType.setSelected(false);
                helper.setGone(R.id.img_select, false);
            }
        }

    }

    private class CityAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX, BaseViewHolder> {

        CityAdapter() {
            super(R.layout.base_item_select_area, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.getName());
            if (mSelectCity == item) {
                helper.setGone(R.id.img_select, true);
                txtType.setSelected(true);
            } else {
                helper.setGone(R.id.img_select, false);
                txtType.setSelected(false);
            }
        }
    }

    private class CityWrapperAdapter extends BaseSectionQuickAdapter<WrapperChildBeanX, BaseViewHolder> {

        CityWrapperAdapter() {
            super(R.layout.base_item_select_area, R.layout.base_item_area_title, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, WrapperChildBeanX item) {
            TextView txtType = helper.getView(R.id.txt_area_name);
            txtType.setText(item.t.getName());
            if (mSelectCity == item.t) {
                helper.setGone(R.id.img_select, true);
                txtType.setSelected(true);
            } else {
                helper.setGone(R.id.img_select, false);
                txtType.setSelected(false);
            }
        }

        @Override
        protected void convertHead(BaseViewHolder helper, WrapperChildBeanX item) {
            helper.setText(R.id.txt_title, item.header);
        }
    }
}
