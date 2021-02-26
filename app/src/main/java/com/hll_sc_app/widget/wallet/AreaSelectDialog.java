package com.hll_sc_app.widget.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.wallet.AreaInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zc
 */
public class AreaSelectDialog extends BaseDialog {
    /**
     * 2省3市4区
     */
    private static final int PROVINCE = 2;
    private static final int CITY = 3;
    private static final int DISTRIBUTE = 4;
    @BindView(R.id.txt_area_province)
    TextView mTxtProvince;
    @BindView(R.id.txt_area_city)
    TextView mTxtCity;
    @BindView(R.id.txt_area_district)
    TextView mTxtDistrict;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    /**
     * 获得的所有省列表数据、市列表数据、区列表数据
     * Integer 级别
     */
    private List<AreaInfo> provincesList = new ArrayList<>();
    private Map<String, List<AreaInfo>> citysMap = new HashMap<>();
    private Map<String, List<AreaInfo>> distributsMap = new HashMap<>();
    /**
     * 当前所选的code码
     * Integer 级别
     */
    private SparseArray<AreaInfo> selectedMap = new SparseArray<>();
    /**
     * 当前的级别：默认是2 省
     */
    private int currentLevel = PROVINCE;
    /**
     * 组件事件接口 与外界通信
     */
    private NetAreaWindowEvent mNetAreaWindowEvent;
    /**
     * 列表适配器对象
     */
    private AreaListAdapter mAreaAdapter;

    public AreaSelectDialog(Activity context) {
        super(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.base_window_item_select_area, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = UIUtils.dip2px(465);
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.white);
        }
    }

    private void initView() {
        mTxtCity.setVisibility(View.GONE);
        mTxtDistrict.setVisibility(View.GONE);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAreaAdapter = new AreaListAdapter(null);
        mListView.setAdapter(mAreaAdapter);
        mAreaAdapter.setOnItemClickListener((adapter, view, position) -> {
            setSelectedMap(currentLevel, (AreaInfo) adapter.getItem(position));
            if (currentLevel < DISTRIBUTE) {
                currentLevel++;
                setCurrentTitle();
                setTitleStyleByLevel();
                updateList();
            } else {
                setCurrentTitle();
                mAreaAdapter.notifyDataSetChanged();
                dismiss();
                mNetAreaWindowEvent.selectAreas(selectedMap.get(PROVINCE), selectedMap.get(CITY),
                    selectedMap.get(DISTRIBUTE));
            }
        });
        setTitleStyleByLevel();
    }


    /**
     * 设置已选择的
     *
     * @param LEVEL
     * @param AreaInfo
     */
    private void setSelectedMap(int LEVEL, AreaInfo AreaInfo) {
        selectedMap.put(LEVEL, AreaInfo);
        currentLevel = LEVEL;
    }

    /**
     * 根据currentLevel更新头部
     */
    private void setCurrentTitle() {
        AreaInfo select_province = selectedMap.get(PROVINCE);
        AreaInfo select_city = selectedMap.get(CITY);
        AreaInfo select_distribute = selectedMap.get(DISTRIBUTE);

        mTxtProvince.setText(select_province != null ? mNetAreaWindowEvent.getName(select_province) : "请选择");
        mTxtCity.setText(select_city != null ? mNetAreaWindowEvent.getName(select_city) : "请选择");
        mTxtDistrict.setText(select_distribute != null ? mNetAreaWindowEvent.getName(select_distribute) : "请选择");
    }

    /**
     * 根据currentLevel显示头部的样式
     */
    private void setTitleStyleByLevel() {
        switch (currentLevel) {
            case PROVINCE:
                mTxtProvince.setVisibility(View.VISIBLE);
                mTxtProvince.setSelected(true);
                mTxtCity.setVisibility(View.GONE);
                mTxtDistrict.setVisibility(View.GONE);
                break;
            case CITY:
                mTxtProvince.setVisibility(View.VISIBLE);
                mTxtProvince.setSelected(false);
                mTxtCity.setVisibility(View.VISIBLE);
                mTxtCity.setSelected(true);
                mTxtDistrict.setVisibility(View.GONE);
                break;
            case DISTRIBUTE:
                mTxtProvince.setVisibility(View.VISIBLE);
                mTxtProvince.setSelected(false);
                mTxtCity.setVisibility(View.VISIBLE);
                mTxtCity.setSelected(false);
                mTxtDistrict.setVisibility(View.VISIBLE);
                mTxtDistrict.setSelected(true);
                break;
            default:
                break;
        }
    }

    /**
     * 根据currentLevel 更新列表显示内容
     * 如果没有则去请求
     */
    private void updateList() {
        /**
         * 如果当前没有数据，则调用接口
         */
        if (getCurrentLevelAreaList() == null && mNetAreaWindowEvent != null) {
            mAreaAdapter.setNewData(null);
            switch (currentLevel) {
                case PROVINCE:
                    mNetAreaWindowEvent.getProvinces();
                    break;
                case CITY:
                    mNetAreaWindowEvent.getCitys(selectedMap.get(PROVINCE));
                    break;
                case DISTRIBUTE:
                    mNetAreaWindowEvent.getDistributes(selectedMap.get(CITY));
                    break;
                default:
                    break;
            }
        } else if (getCurrentLevelAreaList() != null) {
            mAreaAdapter.setNewData(getCurrentLevelAreaList());
        }
    }

    private List<AreaInfo> getCurrentLevelAreaList() {
        switch (currentLevel) {
            case PROVINCE:
                return provincesList;
            case CITY:
                return citysMap.get(getParentKey(CITY));
            case DISTRIBUTE:
                return distributsMap.get(getParentKey(DISTRIBUTE));
            default:
                return null;
        }
    }

    /**
     * 得到父级的key
     * 考虑到初始设置反显 有网络请求 省市区，三个请求是异步的，不能保证currentLevel正确性 所以要传该参数
     */
    private String getParentKey(int currentLevel) {
        switch (currentLevel) {
            case PROVINCE:
                return null;
            case CITY:
                AreaInfo parentBean = selectedMap.get(PROVINCE);
                return mNetAreaWindowEvent.getKey(parentBean);
            case DISTRIBUTE:
                parentBean = selectedMap.get(CITY);
                return mNetAreaWindowEvent.getKey(parentBean);
            default:
                return null;
        }
    }

    private AreaInfo getCurrentLevelSelectData() {
        return selectedMap.get(currentLevel);
    }

    /**
     * 获取省数据
     *
     * @param provinces
     */
    public void setProvinces(List<AreaInfo> provinces) {
        provincesList.addAll(provinces);
        /**
         *该接口是网络请求后外部调用的，所以会出现currentLevel不等于PROVINCE的情况：
         * 反显的时候，调用setProvince,setCity后，此时currentLevel为最后的级别，但请求返回后
         * 调用各自的setxxxxs,会出现currentLevel != PROVINCE，此时，currentLevel为设置反显的最低级别。
         */
        if (currentLevel == PROVINCE) {
            updateList();
        }
    }

    /**
     * 获取市数据
     *
     * @param citys
     */
    public void setCitys(List<AreaInfo> citys) {
        citysMap.put(getParentKey(CITY), citys);
        /**
         *该接口是网络请求后外部调用的，所以会出现currentLevel不等于PROVINCE的情况：
         * 反显的时候，调用setProvince,setCity后，此时currentLevel为最后的级别，但请求返回后
         * 调用各自的setxxxxs,会出现currentLevel != PROVINCE，此时，currentLevel为设置反显的最低级别。
         */
        if (currentLevel == CITY) {
            updateList();
        }

    }

    /**
     * 获取区数据
     *
     * @param distributes
     */
    public void setDistributes(List<AreaInfo> distributes) {
        distributsMap.put(getParentKey(DISTRIBUTE), distributes);
        /**
         *该接口是网络请求后外部调用的，所以会出现currentLevel不等于PROVINCE的情况：
         * 反显的时候，调用setProvince,setCity后，此时currentLevel为最后的级别，但请求返回后
         * 调用各自的setxxxxs,会出现currentLevel != PROVINCE，此时，currentLevel为设置反显的最低级别。
         */
        if (currentLevel == DISTRIBUTE) {
            updateList();
        }

    }

    /**
     * 设置已选择的省
     *
     * @param AreaInfo
     */
    public void setProvice(AreaInfo AreaInfo) {
        setSelectedMap(PROVINCE, AreaInfo);
        setCurrentTitle();
        setTitleStyleByLevel();
        updateList();
    }

    /**
     * 设置已选择的市
     *
     * @param AreaInfo
     */
    public void setCity(AreaInfo AreaInfo) {
        setSelectedMap(CITY, AreaInfo);
        setCurrentTitle();
        setTitleStyleByLevel();
        updateList();
    }

    /**
     * 设置已选择的区
     *
     * @param AreaInfo
     */
    public void setDistribute(AreaInfo AreaInfo) {
        setSelectedMap(DISTRIBUTE, AreaInfo);
        setCurrentTitle();
        setTitleStyleByLevel();
        updateList();
    }

    /**
     * 设置组件的事件接口
     *
     * @param event
     */
    public void addNetAreaWindowEvent(NetAreaWindowEvent event) {
        mNetAreaWindowEvent = event;
        mNetAreaWindowEvent.getProvinces();
    }

    public NetAreaWindowEvent getNetAreaWindowEvent() {
        return mNetAreaWindowEvent;
    }

    @OnClick({R.id.txt_cancel, R.id.txt_area_province, R.id.txt_area_city, R.id.txt_area_district})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_area_province:
                currentLevel = PROVINCE;
                selectedMap.remove(CITY);
                selectedMap.remove(DISTRIBUTE);
                updateList();
                setCurrentTitle();
                setTitleStyleByLevel();
                break;
            case R.id.txt_area_city:
                currentLevel = CITY;
                selectedMap.remove(DISTRIBUTE);
                updateList();
                setCurrentTitle();
                setTitleStyleByLevel();
                break;
            case R.id.txt_area_district:
                currentLevel = DISTRIBUTE;
                updateList();
                setCurrentTitle();
                setTitleStyleByLevel();
                break;
            case R.id.txt_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface NetAreaWindowEvent {
        /**
         * 请求省数据
         */
        void getProvinces();

        /**
         * 请求市数据
         */
        void getCitys(AreaInfo parentAreaInfo);

        /**
         * 得到区数据
         */
        void getDistributes(AreaInfo parentAreaInfo);

        /**
         * 返回所选数据
         */
        void selectAreas(AreaInfo... AreaInfos);

        /**
         * 用于地区名称的显示
         *
         * @param item
         * @return
         */
        String getName(AreaInfo item);

        /**
         * 获取唯一的key 用于存储和比较
         *
         * @param AreaInfo
         * @return
         */
        String getKey(AreaInfo AreaInfo);
    }

    class AreaListAdapter extends BaseQuickAdapter<AreaInfo, BaseViewHolder> {
        AreaListAdapter(@Nullable List<AreaInfo> data) {
            super(R.layout.base_item_select_area, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaInfo item) {
            helper.getView(R.id.img_select).setVisibility(View.GONE);
            TextView areaName = helper.getView(R.id.txt_area_name);
            areaName.setText(mNetAreaWindowEvent.getName(item));
            boolean isSelected = false;
            if (getCurrentLevelSelectData() != null) {
                isSelected = TextUtils.equals(mNetAreaWindowEvent.getKey(item),
                    mNetAreaWindowEvent.getKey(getCurrentLevelSelectData()));
            }
            areaName.setSelected(isSelected);
        }
    }
}
