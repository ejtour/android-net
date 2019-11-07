package com.hll_sc_app.rest;

import com.hll_sc_app.api.PriceService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.DomesticPriceBean;
import com.hll_sc_app.bean.price.LocalPriceBean;
import com.hll_sc_app.bean.price.MarketBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */

public class Price {

    /**
     * 查询市场列表
     *
     * @param provinceCode 省份代码
     */
    public static void queryMarketList(String provinceCode, SimpleObserver<List<MarketBean>> observer) {
        PriceService.INSTANCE
                .queryMarketList(BaseMapReq.newBuilder()
                        .put("provinceCode", provinceCode)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询品类列表
     */
    public static void queryLocalCategory(SimpleObserver<List<CategoryBean>> observer) {
        PriceService.INSTANCE
                .queryLocalCategory(new BaseReq<>(new Object()))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询本地价格
     *
     * @param provinceCode 省份编码
     * @param marketCode   市场编码
     * @param fatherCode   品类编码
     */
    public static void queryLocalPrice(int pageNum, String provinceCode, String marketCode, String fatherCode, SimpleObserver<SingleListResp<LocalPriceBean>> observer) {
        PriceService.INSTANCE
                .queryLocalPrice(BaseMapReq.newBuilder()
                        .put("provinceCode", provinceCode)
                        .put("marketCode", marketCode)
                        .put("fatherCode", fatherCode)
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 国内价格-价格排行
     *
     * @param breedTypeCode 类型代码
     * @param date          日期
     */
    public static void queryPriceAvg(String breedTypeCode, String date, SimpleObserver<SingleListResp<DomesticPriceBean>> observer) {
        PriceService.INSTANCE
                .queryPriceAvg(BaseMapReq.newBuilder()
                        .put("breedTypeCode", breedTypeCode)
                        .put("reportDate", date)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 国内价格-涨幅排行
     *
     * @param breedTypeCode 类型编码
     * @param date          日期
     * @param sortType      排序类型
     */
    public static void queryPriceGain(String breedTypeCode, String date, int sortType, SimpleObserver<SingleListResp<DomesticPriceBean>> observer) {
        PriceService.INSTANCE
                .queryPriceGain(BaseMapReq.newBuilder()
                        .put("breedTypeCode", breedTypeCode)
                        .put("reportDate", date)
                        .put("sortType", String.valueOf(sortType))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询类别
     */
    public static void queryDomesticCategory(SimpleObserver<List<CategoryBean>> observer) {
        PriceService.INSTANCE
                .queryDomesticCategory(new BaseReq<>(new Object()))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
