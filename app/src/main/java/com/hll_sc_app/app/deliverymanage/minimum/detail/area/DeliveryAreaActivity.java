package com.hll_sc_app.app.deliverymanage.minimum.detail.area;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.AreaListBean;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.FileManager;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地区选择
 *
 * @author zhuyingsong
 * @date 2019/7/31
 */
@Route(path = RouterConfig.DELIVERY_AREA, extras = Constant.LOGIN_EXTRA)
public class DeliveryAreaActivity extends BaseLoadActivity {
    @Autowired(name = "parcelable", required = true)
    ProvinceListBean mBean;
    @BindView(R.id.recyclerView_city)
    RecyclerView mRecyclerViewCity;
    @BindView(R.id.recyclerView_area)
    RecyclerView mRecyclerViewArea;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;

    private AreaListAdapter mAreaAdapter;
    private CityListAdapter mCityAdapter;
    private List<AreaBean.ChildBeanX> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_area);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTxtTitle.setText(mBean.getProvinceName());
        mData = processData();

        mAreaAdapter = new AreaListAdapter();
        mRecyclerViewArea.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mRecyclerViewArea.setAdapter(mAreaAdapter);

        mCityAdapter = new CityListAdapter(mData);
        mCityAdapter.setOnItemClickListener((adapter, view, position) -> selectCityBean(adapter, position));
        mRecyclerViewCity.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mRecyclerViewCity.setAdapter(mCityAdapter);
        // 默认选中第一个
        selectCityBean(mCityAdapter, 0);
        // 只有一个城市的直接选择地区
        if (!CommonUtils.isEmpty(mData) && mData.size() == 1) {
            mRecyclerViewCity.setVisibility(View.GONE);
        }
    }

    private List<AreaBean.ChildBeanX> processData() {
        List<AreaBean.ChildBeanX> list = null;
        // 过滤
        List<AreaBean> areaBeans = getAreaListWithOutOverSeas(this);
        if (!CommonUtils.isEmpty(areaBeans)) {
            String provinceCode = mBean.getProvinceCode();
            for (AreaBean areaBean : areaBeans) {
                if (TextUtils.equals(areaBean.getCode(), provinceCode)) {
                    list = areaBean.getChild();
                    break;
                }
            }
        }

        // 设置选择
        Map<String, AreaListBean> mapArea = new HashMap<>();
        List<CityListBean> cityListBeans = mBean.getCityList();
        if (!CommonUtils.isEmpty(cityListBeans)) {
            for (CityListBean cityListBean : cityListBeans) {
                List<AreaListBean> areaListBeans = cityListBean.getAreaList();
                if (!CommonUtils.isEmpty(areaListBeans)) {
                    for (AreaListBean areaListBean : areaListBeans) {
                        mapArea.put(areaListBean.getAreaCode(), areaListBean);
                    }
                }
            }
        }

        if (!CommonUtils.isEmpty(list)) {
            for (AreaBean.ChildBeanX cityBean : list) {
                List<AreaBean.ChildBeanX.ChildBean> childBeans = cityBean.getChild();
                if (!CommonUtils.isEmpty(childBeans)) {
                    for (AreaBean.ChildBeanX.ChildBean childBean : childBeans) {
                        if (mapArea.containsKey(childBean.getCode())) {
                            childBean.setFlag(mapArea.get(childBean.getCode()).getFlag());
                        }
                    }
                }
            }
        }
        return list;
    }

    private void selectCityBean(BaseQuickAdapter adapter, int position) {
        AreaBean.ChildBeanX cityBean = (AreaBean.ChildBeanX) adapter.getItem(position);
        if (cityBean == null) {
            return;
        }
        unSelectAllCity();
        cityBean.setSelect(true);
        adapter.notifyDataSetChanged();
        mAreaAdapter.setNewData(cityBean.getChild());
    }

    /**
     * 获取城市数据（不包含海外数据）
     *
     * @param context 上下文
     * @return 城市数据
     */
    public static List<AreaBean> getAreaListWithOutOverSeas(Context context) {
        List<AreaBean> areaBeans = null;
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

    private void unSelectAllCity() {
        if (!CommonUtils.isEmpty(mData)) {
            for (AreaBean.ChildBeanX cityBean : mData) {
                cityBean.setSelect(false);
            }
        }
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.rl_allCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            case R.id.rl_allCheck:
                break;
            default:
                break;
        }
    }

    private void toSave() {

    }

    class CityListAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX, BaseViewHolder> {

        CityListAdapter(List<AreaBean.ChildBeanX> list) {
            super(R.layout.item_goods_custom_category_top, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX item) {
            helper.setText(R.id.txt_categoryName, item.getName())
                .setBackgroundColor(R.id.txt_categoryName, item.isSelect() ? 0xFFF3F3F3 : 0xFFFFFFFF);
        }
    }


    class AreaListAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX.ChildBean, BaseViewHolder> {
        AreaListAdapter() {
            super(R.layout.item_delivery_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX.ChildBean item) {
            helper.setText(R.id.txt_name, item.getName())
                .setTextColor(R.id.txt_name, getTextColor(item));
            ImageView imgSelect = helper.getView(R.id.img_select);
            if (TextUtils.equals(item.getFlag(), "1")) {
                imgSelect.setSelected(true);
                imgSelect.setEnabled(false);
            } else {
                imgSelect.setEnabled(true);
                imgSelect.setSelected(TextUtils.equals("3", item.getFlag()));
            }
        }

        private int getTextColor(AreaBean.ChildBeanX.ChildBean item) {
            int color = Color.parseColor("#666666");
            switch (item.getFlag()) {
                case "1":
                    color = Color.parseColor("#999999");
                    break;
                case "2":
                    color = Color.parseColor("#666666");
                    break;
                case "3":
                    color = Color.parseColor("#222222");
                    break;
                default:
                    break;
            }
            return color;
        }
    }
}
