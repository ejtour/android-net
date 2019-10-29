package com.hll_sc_app.app.price.local;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.LocalPriceBean;
import com.hll_sc_app.bean.price.MarketBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Price;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/29
 */

public class PriceLocalPresenter implements IPriceLocalContract.IPriceLocalPresenter {
    private IPriceLocalContract.IPriceLocalView mView;
    private int mPageNum;

    private PriceLocalPresenter() {
    }

    public static PriceLocalPresenter newInstance() {
        return new PriceLocalPresenter();
    }

    @Override
    public void queryMarket(String provinceCode) {
        Price.queryMarketList(provinceCode, new SimpleObserver<List<MarketBean>>(mView) {
            @Override
            public void onSuccess(List<MarketBean> marketBeans) {
                mView.handleMarket(marketBeans);
            }
        });
    }

    @Override
    public void loadList() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void queryCategory() {
        Price.queryLocalCategory(new SimpleObserver<List<CategoryBean>>(mView) {
            @Override
            public void onSuccess(List<CategoryBean> categoryBeans) {
                mView.cacheCategory(categoryBeans);
            }
        });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void start() {
        loadList();
        queryCategory();
    }

    private void load(boolean showLoading) {
        Price.queryLocalPrice(
                mPageNum,
                mView.getProvinceCode(),
                mView.getMarketCode(),
                mView.getCategoryCode(),
                new SimpleObserver<SingleListResp<LocalPriceBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<LocalPriceBean> localPriceBeanSingleListResp) {
                        mView.setData(localPriceBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(localPriceBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(IPriceLocalContract.IPriceLocalView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
