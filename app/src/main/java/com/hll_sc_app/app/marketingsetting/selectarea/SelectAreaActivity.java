package com.hll_sc_app.app.marketingsetting.selectarea;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择区域
 * 数据来源本地
 */
@Route(path = RouterConfig.ACTIVITY_SELECT_AREA_PROVINCE_CITY)
public class SelectAreaActivity extends BaseActivity {
    public static final String CITY_ALL_CODE_PREX = "000";

    /**
     * 存储已选择的 key:省id，value：市级
     */
    @Autowired(name = "selected")
    HashMap<String, ArrayList<AreaBean.ChildBeanX>> mSelectCitysMap;
    /*头部文字*/
    @Autowired(name = "title", required = true)
    String title;
    /*result的名称变量*/
    @Autowired(name = "resultname")
    String resultname;
    /*查看传进来的所有数据*/
    @Autowired(name = "areaData")
    ArrayList<AreaBean> mAreaData;
    @BindView(R.id.asa_header)
    TitleBar mTitlBar;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    @BindView(R.id.ll_button_bottom)
    LinearLayout mButtonLayout;
    @BindView(R.id.recyclerView_province)
    RecyclerView mRecyclerViewProvince;
    @BindView(R.id.recyclerView_city)
    RecyclerView mRecyclerViewCitiy;
    @BindView(R.id.txt_select_count)
    TextView mTextSelectCount;
    @BindView(R.id.rl_allCheck)
    RelativeLayout mAllCheck;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private List<AreaBean> mAreaDataList;


    private int currentProvinceIndex = 0;

    /**
     * 选择区域 数据来自本地json文件
     *
     * @param activity
     * @param requestCode
     * @param resultname
     * @param title
     * @param selectMap
     */
    public static void start(Activity activity, int requestCode, String resultname, String title, HashMap<String, ArrayList<AreaBean.ChildBeanX>> selectMap) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_SELECT_AREA_PROVINCE_CITY)
                .withString("title", title)
                .withString("resultname", resultname)
                .withSerializable("selected", selectMap)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    /**
     * 显示地区，传入所有数据
     *
     * @param title
     * @param areaBeans
     */
    public static void start(String title, ArrayList<AreaBean> areaBeans) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_SELECT_AREA_PROVINCE_CITY)
                .withString("title", title)
                .withSerializable("areaData", areaBeans)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        if (mSelectCitysMap == null) {
            mSelectCitysMap = new HashMap<>();
        }
    }

    private void initView() {
        mAreaDataList = isSelectModal() ? UIUtils.getAreaList(this, false) : mAreaData;
        mTitlBar.setHeaderTitle(title);
        mRecyclerViewProvince.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mProvinceAdapter = new ProvinceAdapter(mAreaDataList);
        mProvinceAdapter.setOnItemClickListener((adapter, view, position) -> selectProvinceBean(adapter, position));
        mRecyclerViewProvince.setAdapter(mProvinceAdapter);
        mRecyclerViewCitiy.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mCityAdapter = new CityAdapter(null);
        mCityAdapter.setOnItemClickListener((adapter, view, position) -> selectCityBean(adapter, view, position));
        mRecyclerViewCitiy.setAdapter(mCityAdapter);
        // 默认选中第一个
        selectProvinceBean(mProvinceAdapter, 0);
        if (isSelectModal()) {
            mAllCheck.setVisibility(View.VISIBLE);
            mButtonLayout.setVisibility(View.VISIBLE);
            //更新底部的显示以及同步全国数据
            updateSelectCount();
        }

    }

    /**
     * 点击省
     *
     * @param adapter
     * @param position
     */
    private void selectProvinceBean(BaseQuickAdapter adapter, int position) {
        AreaBean provinceBean = (AreaBean) adapter.getItem(position);
        if (provinceBean == null) {
            return;
        }
        currentProvinceIndex = position;
        adapter.notifyDataSetChanged();
        mCityAdapter.setNewData(getCurrentCityBeans(isSelectModal()));
    }

    /**
     * 是否是可选择模式
     *
     * @return
     */
    private boolean isSelectModal() {
        return mAreaData == null;
    }

    /**
     * 点击市
     *
     * @param adapter
     * @param view
     * @param position
     */
    private void selectCityBean(BaseQuickAdapter adapter, View view, int position) {
        if (!isSelectModal()) {
            return;
        }
        AreaBean.ChildBeanX cityBean = (AreaBean.ChildBeanX) adapter.getItem(position);
        //当前省下没有选过，则为空
        if (mSelectCitysMap.get(getCurrentProvinceCode()) == null) {
            mSelectCitysMap.put(getCurrentProvinceCode(), new ArrayList<>());
        }
        //之前的状态是否已经被勾选
        boolean preSelect = view.findViewById(R.id.img_select).isSelected();
        //点击市级的"全部"
        if (cityBean.getCode().equals(CITY_ALL_CODE_PREX)) {
            mSelectCitysMap.remove(getCurrentProvinceCode());
            if (!preSelect) {
                mSelectCitysMap.put(getCurrentProvinceCode(), getCurrentProvinceAllCity());
            }
        } else {//点击市级
            if (preSelect) {
                mSelectCitysMap.get(getCurrentProvinceCode()).remove(cityBean);
                if (mSelectCitysMap.get(getCurrentProvinceCode()).size() == 0) {
                    mSelectCitysMap.remove(getCurrentProvinceCode());
                }
            } else {
                cityBean.setpCode(getCurrentProvinceCode());
                cityBean.setpName(getCurrentProvinceName());
                mSelectCitysMap.get(getCurrentProvinceCode()).add(cityBean);
            }
        }
        mCityAdapter.notifyDataSetChanged();
        updateSelectCount();
    }

    /**
     * 返回当前省下的所有市。包括全部
     *
     * @return
     */
    List<AreaBean.ChildBeanX> getCurrentCityBeans(boolean isHasAll) {
        AreaBean.ChildBeanX allCityBean = new AreaBean.ChildBeanX();
        List<AreaBean.ChildBeanX> currentCityBeans = mAreaDataList.get(currentProvinceIndex).getChild();
        AreaBean.ChildBeanX firstCityBean = currentCityBeans.get(0);
        if (isHasAll && !TextUtils.equals(firstCityBean.getCode(), CITY_ALL_CODE_PREX)) {//防止重复添加
            allCityBean.setCode(CITY_ALL_CODE_PREX);
            allCityBean.setName("全部");
            currentCityBeans.add(0, allCityBean);
        } else if (!isHasAll && TextUtils.equals(firstCityBean.getCode(), CITY_ALL_CODE_PREX)) {//不传出"全部"
            return currentCityBeans.subList(1, currentCityBeans.size());
        }
        return mAreaDataList.get(currentProvinceIndex).getChild();
    }

    /**
     * 返回当前选择的省的code码
     *
     * @return
     */
    private String getCurrentProvinceCode() {
        return getCurrentProvince().getCode();
    }

    /**
     * 返回当前省市下的所有区的code码
     * 去掉"全部"的code码
     *
     * @return
     */
    ArrayList<AreaBean.ChildBeanX> getCurrentProvinceAllCity() {
        ArrayList<AreaBean.ChildBeanX> cityCodes = new ArrayList<>();
        for (AreaBean.ChildBeanX cityBean : getCurrentProvince().getChild()) {
            if (!cityBean.getCode().equals(CITY_ALL_CODE_PREX)) {
                cityBean.setpCode(getCurrentProvinceCode());
                cityBean.setpName(getCurrentProvinceName());
                cityCodes.add(cityBean);
            }
        }
        return cityCodes;
    }

    /**
     * 得到当前省
     *
     * @return
     */
    private AreaBean getCurrentProvince() {
        return mAreaDataList.get(currentProvinceIndex);
    }

    /**
     * 得到当前省的名称
     *
     * @return
     */
    private String getCurrentProvinceName() {
        return getCurrentProvince().getName();
    }

    @OnClick({R.id.txt_save, R.id.rl_allCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_save:
                toSave();
                break;
            case R.id.rl_allCheck:
                selectAllProvinces(!mImgAllCheck.isSelected());
                updateSelectCount();
                break;
            default:
                break;
        }
    }

    private void toSave() {
        Intent intent = new Intent();
        intent.putExtra(resultname, mSelectCitysMap);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 选中/取消 全国
     */
    private void selectAllProvinces(boolean select) {
        mSelectCitysMap.clear();
        if (select) {
            for (AreaBean province : mAreaDataList) {
                ArrayList<AreaBean.ChildBeanX> cityCodes = new ArrayList<>();
                for (AreaBean.ChildBeanX cityBean : province.getChild()) {
                    if (!cityBean.getCode().equals(CITY_ALL_CODE_PREX)) {
                        cityBean.setpName(province.getName());
                        cityBean.setpCode(province.getCode());
                        cityCodes.add(cityBean);
                    }
                }
                mSelectCitysMap.put(province.getCode(), cityCodes);
            }
        }
        mProvinceAdapter.notifyDataSetChanged();
        mCityAdapter.notifyDataSetChanged();
    }

    /**
     * 计算下方提示字段内容
     */
    private void updateSelectCount() {
        int provinceNum = mSelectCitysMap.size();
        int citysNum = getSelectAreaNum();
        mTextSelectCount.setText(String.format("已选：%s个省，%s个市", provinceNum, citysNum));
        mImgAllCheck.setSelected(citysNum == UIUtils.SUPPORT_CITY_NUM);
    }

    /**
     * 得到所选的城市的总个数
     */
    private int getSelectAreaNum() {
        int citysNum = 0;
        Iterator<ArrayList<AreaBean.ChildBeanX>> iterator = mSelectCitysMap.values().iterator();
        while (iterator.hasNext()) {
            citysNum += iterator.next().size();
        }
        return citysNum;
    }

    class ProvinceAdapter extends BaseQuickAdapter<AreaBean, BaseViewHolder> {

        public ProvinceAdapter(@Nullable List<AreaBean> data) {
            super(R.layout.item_goods_custom_category_top, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean item) {
            helper.setText(R.id.txt_categoryName, item.getName());

            //当前选择的
            if (helper.getLayoutPosition() == currentProvinceIndex) {
                helper.setBackgroundColor(R.id.txt_categoryName, 0xFFF3F3F3)
                        .setTextColor(R.id.txt_categoryName, Color.parseColor("#5695D2"));
            }//已勾选
            else if (mSelectCitysMap.containsKey(item.getCode())) {
                helper.setTextColor(R.id.txt_categoryName, Color.parseColor("#5695D2"))
                        .setBackgroundColor(R.id.txt_categoryName, 0xFFFFFFFF);
            } else {
                //默认状态
                helper.setTextColor(R.id.txt_categoryName, Color.parseColor("#666666"))
                        .setBackgroundColor(R.id.txt_categoryName, 0xFFFFFFFF);
            }
        }
    }

    class CityAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX, BaseViewHolder> {
        public CityAdapter(@Nullable List<AreaBean.ChildBeanX> data) {
            super(R.layout.item_delivery_area, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            holder.setVisible(R.id.img_select, isSelectModal());
            return holder;
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX item) {
            boolean isSelected;
            if (mSelectCitysMap.get(getCurrentProvinceCode()) == null) {
                isSelected = false;
            } else {
                if (helper.getLayoutPosition() == 0) {//市级的"全部"按钮
                    isSelected = mSelectCitysMap.get(getCurrentProvinceCode()).size() + 1 == getData().size();
                } else {
                    isSelected = mSelectCitysMap.get(getCurrentProvinceCode()).contains(item);
                }
            }
            helper.setText(R.id.txt_name, item.getName())
                    .setTextColor(R.id.txt_name, Color.parseColor(isSelected ? "#5695D2" : "#666666"));
            helper.getView(R.id.img_select).setSelected(isSelected);
        }
    }
}
