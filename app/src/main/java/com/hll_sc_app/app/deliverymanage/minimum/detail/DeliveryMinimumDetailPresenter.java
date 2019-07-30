package com.hll_sc_app.app.deliverymanage.minimum.detail;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hll_sc_app.api.DeliveryManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.FileManager;
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
    public void register(DeliveryMinimumDetailContract.IDeliveryMinimumDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryMinimumDetail() {
        DeliveryMinimumBean bean = mView.getDeliveryMinimumBean();
        if (bean == null) {
            return;
        }
        if (TextUtils.equals(bean.getSettings(), "0")) {
            // 地区
            queryArea(bean);
        } else {
            // 采购商
            queryPurchaser(bean);
        }
    }

    private void queryArea(DeliveryMinimumBean bean) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("sendAmountID", bean.getSendAmountID())
            .put("supplyID", bean.getSupplyID())
            .put("supplyShopID", bean.getSupplyShopID())
            .create();
        DeliveryManageService.INSTANCE
            .queryDeliveryMinimumArea(req)
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

    /**
     * 处理地区数据
     *
     * @param backList 选中的地区数据
     */
    @Override
    public void processAreaData(List<ProvinceListBean> backList) {
        Map<String, ProvinceListBean> map = new HashMap<>();
        if (!CommonUtils.isEmpty(backList)) {
            for (ProvinceListBean bean : backList) {
                map.put(bean.getProvinceCode(), bean);
            }
        }
        String json = FileManager.getAssetsData("productarea.json", mView.getContext());
        if (TextUtils.isEmpty(json)) {
            return;
        }
        List<AreaBean> list = new Gson().fromJson(json, new TypeToken<ArrayList<AreaBean>>() {
        }.getType());
        if (!CommonUtils.isEmpty(list)) {
            // 去掉海外的城市
            list.remove(list.size() - 1);
        }
        if (CommonUtils.isEmpty(list)) {
            return;
        }
        List<ProvinceListBean> provinceListBeans = new ArrayList<>();
        for (AreaBean bean : list) {
            ProvinceListBean provinceListBean = new ProvinceListBean();
            provinceListBean.setProvinceCode(bean.getCode());
            provinceListBean.setProvinceName(bean.getName());
            if (!map.isEmpty() && map.containsKey(bean.getCode())) {
                ProvinceListBean backBean = map.get(bean.getCode());
                provinceListBean.setSelect(true);
                provinceListBean.setSelectedNum(backBean.getSelectedNum());
                provinceListBean.setCityList(backBean.getCityList());
            }
            if (!CommonUtils.isEmpty(bean.getChild())) {
                int count = 0;
                List<AreaBean.ChildBeanX> cityList = bean.getChild();
                if (!CommonUtils.isEmpty(cityList)) {
                    for (AreaBean.ChildBeanX cityBean : cityList) {
                        if (!CommonUtils.isEmpty(cityBean.getChild())) {
                            count += cityBean.getChild().size();
                        }
                    }
                }
                provinceListBean.setOptionalNum(count - provinceListBean.getSelectedNum());
            } else {
                provinceListBean.setOptionalNum(0);
            }
            provinceListBeans.add(provinceListBean);
        }
        mView.showAreaList(provinceListBeans);
    }

    @Override
    public void delDeliveryMinimum(ProvinceListBean bean) {

    }
}
