package com.hll_sc_app.app.deliverymanage.range;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.stockmanage.depot.DepotHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.delivery.AreaListBean;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.bean.stockmanage.DepotRangeReq;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.GridSimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.adr_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.recyclerView_select)
    RecyclerView mRecyclerViewSelect;
    @BindView(R.id.recyclerView_area)
    RecyclerView mRecyclerViewArea;
    @BindView(R.id.adr_select_all)
    TextView mSelectAll;
    @BindView(R.id.adr_optional_label)
    TextView mOptional;

    private AreaListAdapter mAreaAdapter;
    private DeliveryRangePresenter mPresenter;
    private SelectListAdapter mSelectAdapter;
    @Autowired(name = "parcelable")
    DepotResp mDepotResp;

    public static void start(Activity context, int reqCode, DepotResp resp) {
        if (resp == null) return;
        RouterUtil.goToActivity(RouterConfig.DELIVERY_RANGE, context, reqCode, resp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_range);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresenter = DeliveryRangePresenter.newInstance();
        mPresenter.register(this);
        if (mDepotResp == null) {
            mPresenter.start();
        } else {
            processProvinceList(mDepotResp.getWarehouseDeliveryRangeList());
            mPresenter.processAreaData(mDepotResp.getWarehouseDeliveryRangeList());
        }
        EventBus.getDefault().register(this);
    }

    private void processProvinceList(List<ProvinceListBean> list) {
        if (CommonUtils.isEmpty(list)) return;
        Map<String, Integer> map = DepotHelper.getTotalNumMap(this);
        for (ProvinceListBean bean : list) {
            if (map.containsKey(bean.getProvinceCode())) {
                int count = 0;
                for (CityListBean city : bean.getCityList()) {
                    if (!CommonUtils.isEmpty(city.getAreaList())) {
                        count += city.getAreaList().size();
                    }
                }
                bean.setSelectedNum(count);
                bean.setOptionalNum(map.get(bean.getProvinceCode()) - count);
            }
        }
    }

    @OnClick(R.id.adr_select_all)
    public void selectAll(View view) {
        mPresenter.processAreaData(view.isSelected() ? null : DepotHelper.getAllProvinceList(this));
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
        if (mDepotResp != null) {
            mSelectAll.setVisibility(View.VISIBLE);
            mTitleBar.setRightText("保存");
            mTitleBar.setRightBtnClick(v -> {
                DepotRangeReq req = new DepotRangeReq();
                req.setGroupID(mDepotResp.getGroupID());
                req.setHouseID(mDepotResp.getId());
                boolean selected = mSelectAll.isSelected();
                if (selected) {
                    req.setIsWholeCountry(1);
                    req.setWarehouseDeliveryRangeList(new ArrayList<>());
                } else {
                    req.setWarehouseDeliveryRangeList(mSelectAdapter.getData());
                }
                mPresenter.setDepotRange(req);
            });
        }
    }

    private void toSelectArea(ProvinceListBean bean) {
        if (mDepotResp == null && !RightConfig.checkRight(getString(R.string.right_distributionArea_query))) {
            showToast(getString(R.string.right_tips));
            return;
        }
        if (bean != null) {
            ARouter.getInstance().build(RouterConfig.DELIVERY_AREA)
                    .withString("object", mDepotResp == null ? getString(R.string.right_distributionArea_update) : null)
                    .withParcelable("parcelable", bean)
                    .setProvider(new LoginInterceptor())
                    .navigation();
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
                        province.setOptionalNum(bean.getOptionalNum());
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

        if (mDepotResp == null) {
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
    }

    @Override
    public void showAreaList(List<ProvinceListBean> list) {
        mAreaAdapter.setNewData(list);
    }

    @Override
    public void showSelectAreaList(List<ProvinceListBean> list) {
        if (mDepotResp != null && CommonUtils.isEmpty(list)) {
            mOptional.setVisibility(View.GONE);
            mRecyclerViewSelect.setVisibility(View.GONE);
        } else {
            mOptional.setVisibility(View.VISIBLE);
            mRecyclerViewSelect.setVisibility(View.VISIBLE);
        }
        mSelectAdapter.setNewData(list);

        if (mDepotResp != null) {
            int count = 0;
            if (mAreaAdapter.getItemCount() == 0) {
                for (ProvinceListBean bean : mSelectAdapter.getData()) {
                    count += bean.getOptionalNum();
                }
                mSelectAll.setSelected(count == 0);
            } else {
                mSelectAll.setSelected(false);
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void success() {
        setResult(RESULT_OK);
        finish();
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
