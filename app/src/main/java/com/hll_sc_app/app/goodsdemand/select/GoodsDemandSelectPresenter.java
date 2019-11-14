package com.hll_sc_app.app.goodsdemand.select;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/24
 */

public class GoodsDemandSelectPresenter implements IGoodsDemandSelectContract.IGoodsDemandSelectPresenter {
    private final GoodsListReq mReq;
    private int mPageNum;
    private IGoodsDemandSelectContract.IGoodsDemandSelectView mView;

    private GoodsDemandSelectPresenter() {
        mReq = new GoodsListReq();
        mReq.setProductStatus(String.valueOf(4));
    }

    public static GoodsDemandSelectPresenter newInstance() {
        return new GoodsDemandSelectPresenter();
    }

    private void load(boolean showLoading) {
        mReq.setPageNum(mPageNum);
        mReq.setName(mView.getSearchWords());
        SimpleObserver<List<GoodsBean>> observer = new SimpleObserver<List<GoodsBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<GoodsBean> list) {
                mView.setData(list, mPageNum > 1);
                if (CommonUtils.isEmpty(list)) return;
                mPageNum++;
            }
        };
        GoodsService.INSTANCE
                .queryGoodsList(new BaseReq<>(mReq))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
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
    public void confirm(GoodsBean bean) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productID", bean.getProductID());
            jsonObject.put("productName", bean.getProductName());
            jsonObject.put("imgUrl", bean.getImgUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Other.replyGoodsDemand(mView.getID(), jsonObject.toString(), "", mView.getPurchaserID(), 3, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        });
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IGoodsDemandSelectContract.IGoodsDemandSelectView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
