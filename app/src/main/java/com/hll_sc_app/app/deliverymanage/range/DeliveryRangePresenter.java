package com.hll_sc_app.app.deliverymanage.range;

import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.app.deliverymanage.minimum.area.DeliveryAreaActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.delivery.AreaListBean;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.bean.delivery.ProvinceListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 配送管理-配送范围
 *
 * @author zhuyingsong
 * @date 2019/08/01
 */
public class DeliveryRangePresenter implements DeliveryRangeContract.IDeliveryRangePresenter {
    private DeliveryRangeContract.IDeliveryRangeView mView;
    private List<AreaBean> mAreaBeans;

    static DeliveryRangePresenter newInstance() {
        return new DeliveryRangePresenter();
    }

    @Override
    public void start() {
        queryDeliveryRange();
    }

    @Override
    public void register(DeliveryRangeContract.IDeliveryRangeView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryRange() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .create();
        DeliveryManageService.INSTANCE
            .queryDeliveryRangeArea(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ProvinceListResp>() {
                @Override
                public void onSuccess(ProvinceListResp resp) {
                    processAreaData(resp.getProvinceList());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
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
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void processAreaData(List<ProvinceListBean> backList) {
        // 已选择的地区
        Map<String, ProvinceListBean> map = new HashMap<>();
        if (!CommonUtils.isEmpty(backList)) {
            for (ProvinceListBean bean : backList) {
                map.put(bean.getProvinceCode(), bean);
            }
        }
        if (CommonUtils.isEmpty(mAreaBeans)) {
            mAreaBeans = DeliveryAreaActivity.getAreaListWithOutOverSeas(mView.getContext());
        }
        if (CommonUtils.isEmpty(mAreaBeans)) {
            return;
        }
        List<ProvinceListBean> provinceListBeans = new ArrayList<>();
        for (AreaBean bean : mAreaBeans) {
            if (!map.isEmpty() && map.containsKey(bean.getCode())) {
                ProvinceListBean provinceListBean = map.get(bean.getCode());
                provinceListBean.setProvinceName(bean.getName());
                List<CityListBean> seconds = provinceListBean.getCityList();
                if (!CommonUtils.isEmpty(seconds)) {
                    for (CityListBean second : seconds) {
                        List<AreaListBean> thirds = second.getAreaList();
                        if (!CommonUtils.isEmpty(thirds)) {
                            for (AreaListBean third : thirds) {
                                third.setFlag("3");
                            }
                        }
                    }
                }
            } else {
                ProvinceListBean provinceListBean = new ProvinceListBean();
                provinceListBean.setProvinceCode(bean.getCode());
                provinceListBean.setProvinceName(bean.getName());
                provinceListBeans.add(provinceListBean);
            }
        }
        mView.showAreaList(provinceListBeans);
        mView.showSelectAreaList(new ArrayList<>(map.values()));
    }
}
