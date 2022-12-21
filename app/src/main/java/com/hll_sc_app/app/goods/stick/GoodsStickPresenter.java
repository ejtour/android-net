package com.hll_sc_app.app.goods.stick;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.GoodsStickReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 商品置顶管理
 *
 * @author zhuyingsong
 * @date 2019/7/1
 */
public class GoodsStickPresenter implements GoodsStickContract.IGoodsStickPresenter {
    private GoodsStickContract.IGoodsStickView mView;
    private int mPageNum;

    static GoodsStickPresenter newInstance() {
        return new GoodsStickPresenter();
    }

    @Override
    public void start() {
        queryCustomCategory();
    }

    @Override
    public void register(GoodsStickContract.IGoodsStickView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCustomCategory() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("getResource", "0")
            .put("groupID", UserConfig.getGroupID())
            .create();
        GoodsService.INSTANCE.queryCustomCategory2Top(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CustomCategoryResp>() {
                @Override
                public void onSuccess(CustomCategoryResp resp) {
                    List<CustomCategoryBean> list = resp.getList2();
                    if (list == null) {
                        list = new ArrayList<>();
                        resp.setList2(list);
                    }
                    CustomCategoryBean bean = new CustomCategoryBean();
                    bean.setCategoryName("优惠商品");
                    list.add(0, bean);
                    mView.showCustomCategoryList(resp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void queryGoodsList(boolean showLoading) {
        mPageNum = 1;
        toQueryGoodsList(showLoading);
    }

    @Override
    public void queryMoreGoodsList() {
        toQueryGoodsList(false);
    }

    @Override
    public void goods2Top(Map<String, List<GoodsBean>> map, List<String> deleteIds) {
        GoodsStickReq req = new GoodsStickReq();
        List<GoodsStickReq.RecordsBean> records = new ArrayList<>();
        for (Map.Entry<String, List<GoodsBean>> entry : map.entrySet()) {
            GoodsStickReq.RecordsBean recordsBean = new GoodsStickReq.RecordsBean();
            recordsBean.setGroupID(UserConfig.getGroupID());
            recordsBean.setShopProductCategorySubID(entry.getKey());
            List<GoodsStickReq.RecordsBean.ListBean> list = new ArrayList<>();
            List<GoodsBean> goodsBeans = entry.getValue();
            if (!CommonUtils.isEmpty(goodsBeans)) {
                //fix 分页请求时用户看到的品项和剩余品项sort 重复导致列表乱了
                GoodsBean tempGoods = goodsBeans.get(0);
                for (int i = 0; i < goodsBeans.size(); i++) {
                    GoodsBean bean = goodsBeans.get(i);
                    GoodsStickReq.RecordsBean.ListBean listBean = new GoodsStickReq.RecordsBean.ListBean();
                    listBean.setProductID(bean.getProductID());
                    listBean.setShopProductCategoryThreeID(bean.getShopProductCategoryThreeID());
                    listBean.setSort(String.valueOf(CommonUtils.getLong("" + tempGoods.getTop()) + 2 + i));
                    listBean.setProductName(bean.getProductName());
                    list.add(listBean);
                }
            }
            recordsBean.setList(list);
            records.add(recordsBean);
        }
        req.setRecords(records);
        req.setDeleteProductIDs(deleteIds);
        BaseReq<GoodsStickReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        GoodsService.INSTANCE.goods2Top(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<BaseResp<Object>>() {
                @Override
                public void onSuccess(BaseResp<Object> objectBaseResp) {
                    if (objectBaseResp.isSuccess()) {
                        mView.saveSuccess();
                    } else {
                        mView.showToast(objectBaseResp.getMessage());
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryGoodsList(boolean showLoading) {
        String categorySubId = mView.getShopProductCategorySubId();
        if (TextUtils.isEmpty(categorySubId)) {
            toQueryDiscountGoodsList(showLoading);
            return;
        }
        BaseReq<GoodsListReq> baseReq = new BaseReq<>();
        GoodsListReq req = new GoodsListReq();
        req.setPageNum(mPageNum);
        req.setShopProductCategorySubID(categorySubId);
        req.setName(mView.getName());
        req.setActionType("top");
        baseReq.setData(req);
        GoodsService.INSTANCE.queryGoodsList(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<GoodsBean>>() {
                    @Override
                    public void onSuccess(List<GoodsBean> resp) {
                        mView.showList(resp, mPageNum > 1);
                        if (CommonUtils.isEmpty(resp)) return;
                        mPageNum++;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


    private void toQueryDiscountGoodsList(boolean showLoading) {
        GoodsService.INSTANCE.queryDiscountGoodsList(BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("searchKey", mView.getName())
                .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<SingleListResp<GoodsBean>>() {
                    @Override
                    public void onSuccess(SingleListResp<GoodsBean> goodsBeanSingleListResp) {
                        mView.showList(goodsBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(goodsBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
