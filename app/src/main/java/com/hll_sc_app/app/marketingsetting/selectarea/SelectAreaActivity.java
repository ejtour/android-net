package com.hll_sc_app.app.marketingsetting.selectarea;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll_sc_app.R;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.FileManager;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择区域
 * 数据来源本地
 */
@Route(path = RouterConfig.ACTIVITY_SELECT_AREA_PROVINCE_CITY)
public class SelectAreaActivity extends AppCompatActivity {
    public static final int ALL_CITYS_NUM = 354;
    public static final String CITY_ALL_CODE_PREX = "000";

    /**
     * 存储已选择的 key:省id，value：set 市id
     */
    @Autowired(name = "selected", required = true)
    HashMap<String, Set<String>> mSelectIDMap;
    @Autowired(name = "title", required = true)
    String title;
    @BindView(R.id.asa_header)
    TitleBar mTitlBar;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    @BindView(R.id.recyclerView_province)
    RecyclerView mRecyclerViewProvince;
    @BindView(R.id.recyclerView_city)
    RecyclerView mRecyclerViewCitiy;
    @BindView(R.id.txt_select_count)
    TextView mTextSelectCount;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;

    private ArrayList<AreaBean> mAreaDataList;

    private int currentProvinceIndex = 0;

    public static void start(String title, HashMap<String, Set<String>> selectIdMap) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_SELECT_AREA_PROVINCE_CITY)
                .withString("title", title)
                .withSerializable("selected", selectIdMap)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        if (mSelectIDMap == null) {
            mSelectIDMap = new HashMap<>();
        }
    }

    private void initView() {
        mAreaDataList = getAreaListWithOutOverSeas(this);
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


    }

    /**
     * 获取城市数据（不包含海外数据）
     *
     * @param context 上下文
     * @return 城市数据
     */
    public static ArrayList<AreaBean> getAreaListWithOutOverSeas(Context context) {
        ArrayList<AreaBean> areaBeans = null;
        String json = FileManager.getAssetsData("productarea.json", context);
        if (!TextUtils.isEmpty(json)) {
            areaBeans = new Gson().fromJson(json, new TypeToken<ArrayList<AreaBean>>() {
            }.getType());
            if (!CommonUtils.isEmpty(areaBeans)) {
                // 去掉海外的城市
                areaBeans.remove(areaBeans.size() - 1);
            }
        }
        return areaBeans;
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
        mCityAdapter.setNewData(getCurrentCityBeans());
    }


    /**
     * 点击市
     *
     * @param adapter
     * @param view
     * @param position
     */
    private void selectCityBean(BaseQuickAdapter adapter, View view, int position) {
        AreaBean.ChildBeanX cityBean = (AreaBean.ChildBeanX) adapter.getItem(position);
        //当前省下没有选过，则为空
        if (mSelectIDMap.get(getCurrentProvinceCode()) == null) {
            mSelectIDMap.put(getCurrentProvinceCode(), new HashSet<>());
        }
        //之前的状态是否已经被勾选
        boolean preSelect = view.findViewById(R.id.img_select).isSelected();
        //点击市级的全部
        if (cityBean.getCode().equals(CITY_ALL_CODE_PREX)) {
            if (preSelect) {
                mSelectIDMap.remove(getCurrentProvinceCode());
            } else {
                mSelectIDMap.get(getCurrentProvinceCode()).addAll(getCurrentProvinceAllCityCodes());
            }
        } else {//点击市级
            if (preSelect) {
                mSelectIDMap.get(getCurrentProvinceCode()).remove(cityBean.getCode());
                if (mSelectIDMap.get(getCurrentProvinceCode()).size() == 0) {
                    mSelectIDMap.remove(getCurrentProvinceCode());
                }
                mImgAllCheck.setSelected(false);
            } else {
                mSelectIDMap.get(getCurrentProvinceCode()).add(cityBean.getCode());
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
    List<AreaBean.ChildBeanX> getCurrentCityBeans() {
        AreaBean.ChildBeanX allCityBean = new AreaBean.ChildBeanX();
        AreaBean.ChildBeanX firstCityBean = mAreaDataList.get(currentProvinceIndex).getChild().get(0);
        if (!TextUtils.equals(firstCityBean.getCode(), CITY_ALL_CODE_PREX)) {
            allCityBean.setCode(CITY_ALL_CODE_PREX);
            allCityBean.setName("全部");
            mAreaDataList.get(currentProvinceIndex).getChild().add(0, allCityBean);
        }
        return mAreaDataList.get(currentProvinceIndex).getChild();
    }

    /**
     * 返回当前选择的省的code码
     *
     * @return
     */
    private String getCurrentProvinceCode() {
        return mAreaDataList.get(currentProvinceIndex).getCode();
    }

    /**
     * 返回当前省市下的所有区的code码
     * 去掉"全部"的code码
     *
     * @return
     */
    List<String> getCurrentProvinceAllCityCodes() {
        List<String> cityCodes = new ArrayList<>();
        for (AreaBean.ChildBeanX cityBean : mAreaDataList.get(currentProvinceIndex).getChild()) {
            if (!cityBean.getCode().equals(CITY_ALL_CODE_PREX)) {
                cityCodes.add(cityBean.getCode());
            }
        }
        return cityCodes;
    }

    /**
     * 计算下方提示字段内容
     */
    private void updateSelectCount() {
        int provinceNum = mSelectIDMap.size();
        int citysNum = 0;
        Iterator<Set<String>> iterator = mSelectIDMap.values().iterator();
        while (iterator.hasNext()) {
            citysNum += iterator.next().size();
        }
        mTextSelectCount.setText(String.format("已选：%s个省，%s个市", provinceNum, citysNum));

        mImgAllCheck.setSelected(citysNum == ALL_CITYS_NUM);
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
        finish();
    }

    /**
     * 选中/取消 全国
     */
    private void selectAllProvinces(boolean select) {
        mSelectIDMap.clear();
        if (select) {
            for (AreaBean province : mAreaDataList) {
                HashSet<String> cityCodes = new HashSet<>();
                for (AreaBean.ChildBeanX cityBean : province.getChild()) {
                    if (!cityBean.getCode().equals(CITY_ALL_CODE_PREX)) {
                        cityCodes.add(cityBean.getCode());
                    }
                }
                mSelectIDMap.put(province.getCode(), cityCodes);
            }
        }
        mProvinceAdapter.notifyDataSetChanged();
        mCityAdapter.notifyDataSetChanged();
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
            else if (mSelectIDMap.containsKey(item.getCode())) {
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
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX item) {
            boolean isSelected;
            if (mSelectIDMap.get(getCurrentProvinceCode()) == null) {
                isSelected = false;
            } else {
                if (helper.getLayoutPosition() == 0) {//市级的"全部"按钮
                    isSelected = mSelectIDMap.get(getCurrentProvinceCode()).size() + 1 == getData().size();
                } else {
                    isSelected = mSelectIDMap.get(getCurrentProvinceCode()).contains(item.getCode());
                }
            }
            helper.setText(R.id.txt_name, item.getName())
                    .setTextColor(R.id.txt_name, Color.parseColor(isSelected ? "#5695D2" : "#666666"));
            helper.getView(R.id.img_select).setSelected(isSelected);

        }
    }

}
