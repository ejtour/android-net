package com.hll_sc_app.app.price.domestic;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.DomesticPriceBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Price;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/29
 */

public class PriceDomesticPresenter implements IPriceDomesticContract.IPriceDomesticPresenter {
    private IPriceDomesticContract.IPriceDomesticView mView;

    private PriceDomesticPresenter() {
    }

    public static PriceDomesticPresenter newInstance() {
        return new PriceDomesticPresenter();
    }

    @Override
    public void start() {
        Price.queryDomesticCategory(new SimpleObserver<List<CategoryBean>>(mView) {
            @Override
            public void onSuccess(List<CategoryBean> list) {
                mView.cacheCategory(list);
            }
        });
        loadList();
    }

    @Override
    public void register(IPriceDomesticContract.IPriceDomesticView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void loadList() {
        if (mView.getSortType() == 0) Price.queryPriceAvg(
                mView.getCategory(),
                mView.getDate(),
                new SimpleObserver<SingleListResp<DomesticPriceBean>>(mView) {
                    @Override
                    public void onSuccess(SingleListResp<DomesticPriceBean> domesticPriceBeanSingleListResp) {
                        mView.setData(domesticPriceBeanSingleListResp.getRecords());
                    }
                });
        else Price.queryPriceGain(
                mView.getCategory(),
                mView.getDate(),
                mView.getSortType(),
                new SimpleObserver<SingleListResp<DomesticPriceBean>>(mView) {
                    @Override
                    public void onSuccess(SingleListResp<DomesticPriceBean> domesticPriceBeanSingleListResp) {
                        mView.setData(domesticPriceBeanSingleListResp.getRecords());
                    }
                });
    }
}
