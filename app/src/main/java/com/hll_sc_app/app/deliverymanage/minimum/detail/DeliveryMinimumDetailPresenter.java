package com.hll_sc_app.app.deliverymanage.minimum.detail;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.app.deliverymanage.minimum.detail.area.DeliveryAreaActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 起送金额详情
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
public class DeliveryMinimumDetailPresenter implements DeliveryMinimumDetailContract.IDeliveryMinimumDetailPresenter {
    private DeliveryMinimumDetailContract.IDeliveryMinimumDetailView mView;

    static DeliveryMinimumDetailPresenter newInstance() {
        return new DeliveryMinimumDetailPresenter();
    }

    @Override
    public void start() {
        queryDeliveryMinimumDetail();
    }

    @Override
    public void register(DeliveryMinimumDetailContract.IDeliveryMinimumDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryMinimumDetail() {
        DeliveryMinimumBean bean = mView.getDeliveryMinimumBean();
        if (mView.isAreaType()) {
            queryArea(bean);
        } else {
            queryPurchaser(bean);
        }
    }

    @Override
    public void editDeliveryMinimum(DeliveryMinimumReq bean) {
        DeliveryManageService.INSTANCE
            .editDeliveryMinimum(new BaseReq<>(bean))
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void processAreaData(List<ProvinceListBean> backList) {
        Map<String, ProvinceListBean> map = new HashMap<>();
        if (!CommonUtils.isEmpty(backList)) {
            for (ProvinceListBean bean : backList) {
                map.put(bean.getProvinceCode(), bean);
            }
        }
        List<AreaBean> areaBeans = DeliveryAreaActivity.getAreaListWithOutOverSeas(mView.getContext());
        if (CommonUtils.isEmpty(areaBeans)) {
            return;
        }
        List<ProvinceListBean> provinceListBeans = new ArrayList<>();
        for (AreaBean bean : areaBeans) {
            ProvinceListBean provinceListBean = new ProvinceListBean();
            provinceListBean.setProvinceCode(bean.getCode());
            provinceListBean.setProvinceName(bean.getName());
            if (!map.isEmpty() && map.containsKey(bean.getCode())) {
                ProvinceListBean backBean = map.get(bean.getCode());
                provinceListBean.setSelect(backBean.getSelectedNum() != 0);
                provinceListBean.setCityList(backBean.getCityList());
                provinceListBean.setSelectedNum(backBean.getSelectedNum());
            }

            // 省下面的所有区数量
            if (!CommonUtils.isEmpty(bean.getChild())) {
                // 市
                int count = 0;
                List<AreaBean.ChildBeanX> cityList = bean.getChild();
                if (!CommonUtils.isEmpty(cityList)) {
                    for (AreaBean.ChildBeanX cityBean : cityList) {
                        // 区
                        if (!CommonUtils.isEmpty(cityBean.getChild())) {
                            count += cityBean.getChild().size();
                        }
                    }
                }
                int disableCount = 0;
                List<CityListBean> disableList = provinceListBean.getCityList();
                if (!CommonUtils.isEmpty(disableList)) {
                    for (CityListBean cityListBean : disableList) {
                        if (!CommonUtils.isEmpty(cityListBean.getAreaList())) {
                            disableCount += cityListBean.getAreaList().size();
                        }
                    }
                }
                provinceListBean.setOptionalNum(count - disableCount);
            } else {
                provinceListBean.setOptionalNum(0);
            }
            provinceListBeans.add(provinceListBean);
        }
        mView.showAreaList(provinceListBeans);
    }

    private void queryArea(DeliveryMinimumBean bean) {
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        if (bean == null) {
            builder.put("supplyID", UserConfig.getGroupID())
                .put("settings", "0")
                .put("sendAmountID", "0");
        } else {
            builder.put("sendAmountID", bean.getSendAmountID())
                .put("supplyID", bean.getSupplyID())
                .put("supplyShopID", bean.getSupplyShopID());
        }
        DeliveryManageService.INSTANCE
            .queryDeliveryMinimumArea(builder.create())
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<ProvinceListBean>>() {
                @Override
                public void onSuccess(List<ProvinceListBean> provinceListBeans) {
                    processAreaData(provinceListBeans);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void queryPurchaser(DeliveryMinimumBean bean) {
    }
}
