package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.AptitudeService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeInfoReq;
import com.hll_sc_app.bean.aptitude.AptitudeInfoResp;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;

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
     *
     * @param type 1-企业资质 2-商品资质
     * @param name 资质名称
     */
    public static void queryAptitudeTypeList(int type, String name, SimpleObserver<List<AptitudeBean>> observer) {
        AptitudeService.INSTANCE
                .queryAptitudeTypeList(BaseMapReq.newBuilder()
                        .put("dataType", String.valueOf(type))
                        .put("groupID", UserConfig.getGroupID())
                        .put("aptitudeName", name)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询仅接单未设置资质商品
     */
    public static void queryGoodsList(String searchWords, SimpleObserver<List<GoodsBean>> observer) {
        AptitudeService.INSTANCE
                .queryGoodsList(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("searchKey", searchWords)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * @param id       资质 id，空值新增，否则删除
     * @param dataType 企业资质-1，商品资质-2
     * @param name     资质名称
     */
    public static void editAptitudeType(String id, int dataType, String name, SimpleObserver<MsgWrapper<Object>> observer) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("id", id)
                .put("groupID", UserConfig.getGroupID())
                .put("dataType", String.valueOf(dataType))
                .put("aptitudeName", name)
                .create();
        Observable<BaseResp<Object>> observable =
                TextUtils.isEmpty(id) ?
                        AptitudeService.INSTANCE.addAptitudeType(req) :
                        AptitudeService.INSTANCE.delAptitudeType(req);
        observable.compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询商品资质
     */
    public static void queryGoodsAptitudeList(BaseMapReq req, SimpleObserver<SingleListResp<AptitudeBean>> observer) {
        AptitudeService.INSTANCE
                .queryGoodsAptitudeList(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * @param id 资质类型 id
     */
    public static void queryGoodsAptitude(String id, SimpleObserver<AptitudeBean> observer) {
        AptitudeService.INSTANCE
                .queryGoodsAptitude(BaseMapReq.newBuilder()
                        .put("id", id)
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 保存商品资质
     *
     * @param bean 商品资质
     */
    public static void saveGoodsAptitude(AptitudeBean bean, SimpleObserver<MsgWrapper<Object>> observer) {
        BaseReq<AptitudeBean> req = new BaseReq<>(bean);
        Observable<BaseResp<Object>> observable =
                TextUtils.isEmpty(bean.getId()) ?
                        AptitudeService.INSTANCE.addGoodsAptitude(req) :
                        AptitudeService.INSTANCE.editGoodsAptitude(req);
        observable.compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 删除商品资质
     *
     * @param id 资质 id
     */
    public static void delGoodsAptitude(String id, SimpleObserver<MsgWrapper<Object>> observer) {
        AptitudeService.INSTANCE
                .delGoodsAptitude(BaseMapReq.newBuilder()
                        .put("id", id)
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
