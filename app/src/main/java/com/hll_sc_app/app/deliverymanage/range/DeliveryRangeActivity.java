package com.hll_sc_app.app.deliverymanage.range;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.delivery.AreaListBean;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.widget.GridSimpleDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配送管理-配送范围
 *
 * @author zhuyingsong
 * @date 2019/08/01
 */
@Route(path = RouterConfig.DELIVERY_RANGE, extras = Constant.LOGIN_EXTRA)
public class DeliveryRangeActivity extends BaseLoadActivity implements DeliveryRangeContract.IDeliveryRangeView {
    @BindView(R.id.recyclerView_select)
    RecyclerView mRecyclerViewSelect;
    @BindView(R.id.recyclerView_area)
    RecyclerView mRecyclerViewArea;

    private AreaListAdapter mAreaAdapter;
    private DeliveryRangePresenter mPresenter;
    private SelectListAdapter mSelectAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_range);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryRangePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mAreaAdapter = new AreaListAdapter();
        mAreaAdapter.setOnItemClickListener((adapter, view, position) ->
            toSelectArea((ProvinceListBean) adapter.getItem(position)));
        mRecyclerViewArea.addItemDecoration(new GridSimpleDecoration());
        mRecyclerViewArea.setAdapter(mAreaAdapter);

        mSelectAdapter = new SelectListAdapter();
        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_delivery_range_empty,
            mRecyclerViewSelect, false);
        mSelectAdapter.setEmptyView(emptyView);
        mSelectAdapter.setOnItemClickListener((adapter, view, position) ->
            toSelectArea((ProvinceListBean) adapter.getItem(position)));
        mRecyclerViewSelect.addItemDecoration(new GridSimpleDecoration());
        mRecyclerViewSelect.setAdapter(mSelectAdapter);
    }

    private void toSelectArea(ProvinceListBean bean) {
        if (bean != null) {
            RouterUtil.goToActivity(RouterConfig.DELIVERY_AREA, bean);
        }
    }

    @Subscribe
    public void onEvent(ProvinceListBean bean) {
        if (bean == null) {
            return;
        }
        List<ProvinceListBean> provincesBefore = mSelectAdapter.getData();
        boolean find = false;
        boolean empty = CommonUtils.isEmpty(provincesBefore);
        if (!empty) {
            for (int position = 0, size = provincesBefore.size(); position < size; position++) {
                ProvinceListBean province = provincesBefore.get(position);
                if (TextUtils.equals(province.getProvinceCode(), bean.getProvinceCode())) {
                    find = true;
                    if (bean.getSelectedNum() == 0) {
                        mSelectAdapter.remove(position);
                    } else {
                        province.setCityList(bean.getCityList());
                        province.setSelectedNum(bean.getSelectedNum());
                        mSelectAdapter.notifyItemChanged(position);
                    }
                    break;
                }
            }
        }
        if (empty || !find) {
            mSelectAdapter.addData(bean);
        }
        mPresenter.processAreaData(mSelectAdapter.getData());

        List<String> codeList = new ArrayList<>();
        List<CityListBean> cityListBeans = bean.getCityList();
        if (!CommonUtils.isEmpty(cityListBeans)) {
            for (CityListBean cityListBean : cityListBeans) {
                List<AreaListBean> areaListBeans = cityListBean.getAreaList();
                if (!CommonUtils.isEmpty(areaListBeans)) {
                    for (AreaListBean areaListBean : areaListBeans) {
                        codeList.add(areaListBean.getAreaCode());
                    }
                }
            }
        }
        DeliveryMinimumReq req = new DeliveryMinimumReq();
        req.setCodeList(codeList);
        req.setGroupID(UserConfig.getGroupID());
        req.setProvinceCode(bean.getProvinceCode());
        mPresenter.editDeliveryMinimum(req);
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showAreaList(List<ProvinceListBean> list) {
        mAreaAdapter.setNewData(list);
    }

    @Override
    public void showSelectAreaList(List<ProvinceListBean> list) {
        mSelectAdapter.setNewData(list);
    }

    @Override
    public Context getContext() {
        return this;
    }

    class SelectListAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {
        SelectListAdapter() {
            super(R.layout.item_delivery_range_select_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProvinceListBean item) {
            helper.setText(R.id.txt_provinceName, item.getProvinceName())
                .setText(R.id.txt_selectedNum, String.valueOf(item.getSelectedNum()));
        }
    }

    class AreaListAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {
        AreaListAdapter() {
            super(R.layout.item_delivery_minimum_detail_area);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.setGone(R.id.txt_selectedNum, false)
                .setGone(R.id.txt_optionalNum, false);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, ProvinceListBean item) {
            helper.setText(R.id.txt_provinceName, item.getProvinceName());
        }
    }
}
