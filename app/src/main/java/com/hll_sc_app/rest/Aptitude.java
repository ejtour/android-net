package com.hll_sc_app.rest;

import com.hll_sc_app.api.AptitudeService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class Aptitude {

    /**
     * 保存基础信息
     *
     * @param req 请求体
     */
    public static void saveBaseInfo(AptitudeInfoReq req, SimpleObserver<MsgWrapper<Object>> observer) {
        AptitudeService.INSTANCE
                .saveBaseInfo(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询基础信息
     */
    public static void queryBaseInfo(SimpleObserver<AptitudeInfoResp> observer) {
        AptitudeService.INSTANCE
                .queryBaseInfo(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 编辑企业资质信息
     *
     * @param req 请求体
     */
    public static void saveAptitudeList(AptitudeReq req, SimpleObserver<MsgWrapper<Object>> observer) {
        AptitudeService.INSTANCE
                .saveAptitudeList(new BaseReq<>(req))
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询资质
     *
     * @param productID 商品id
     */
    public static void queryAptitudeList(String extGroupID, String productID, SimpleObserver<SingleListResp<AptitudeBean>> observer) {
        AptitudeService.INSTANCE
                .queryAptitudeList(BaseMapReq.newBuilder()
                        .put("extGroupID", extGroupID)
                        .put("productID", productID)
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询资质类型
     * 1-企业资质 2-商品资质
     */
    public static void queryAptitudeTypeList(int type, SimpleObserver<List<AptitudeTypeBean>> observer) {
        AptitudeService.INSTANCE
                .queryAptitudeTypeList(BaseMapReq.newBuilder()
                        .put("dataType", String.valueOf(type))
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询仅接单资质商品
     */
    public static void queryGoodsList(String searchWords, boolean isSet, SimpleObserver<List<GoodsBean>> observer) {
        AptitudeService.INSTANCE
                .queryGoodsList(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("isSetAptitude", String.valueOf(isSet))
                        .put("searchKey", searchWords)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
