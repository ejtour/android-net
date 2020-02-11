package com.hll_sc_app.app.order.place.select;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.order.place.ProductBean;
import com.hll_sc_app.bean.order.place.SelectGoodsParam;
import com.hll_sc_app.bean.order.place.SettlementInfoReq;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/17
 */

public class SelectGoodsPresenter implements ISelectGoodsContract.ISelectGoodsPresenter {
    private SelectGoodsParam mParam;
    private ISelectGoodsContract.ISelectGoodsView mView;
    private int mPageNum;

    private SelectGoodsPresenter(SelectGoodsParam param) {
        mParam = param;
    }

    public static SelectGoodsPresenter newInstance(SelectGoodsParam param) {
        return new SelectGoodsPresenter(param);
    }

    public void load(boolean showLoading) {
        Order.queryGoodsList(mPageNum,
                mParam.isWarehouse(),
                mParam.getSubID(),
                mParam.getThreeID(),
                mParam.getSearchWords(),
                mParam.getPurchaserID(),
                mParam.getShopID(),
                new SimpleObserver<List<ProductBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(List<ProductBean> goodsBeans) {
                        mView.setGoodsList(goodsBeans, mPageNum > 1);
                        if (CommonUtils.isEmpty(goodsBeans)) return;
                        mPageNum++;
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
    public void loadList() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void confirm(SettlementInfoReq req) {
        Order.getSettlementInfo(req, new SimpleObserver<SettlementInfoResp>(mView) {
            @Override
            public void onSuccess(SettlementInfoResp resp) {
                mView.confirmSuccess(resp);
            }
        });
    }

    @Override
    public void start() {
        Order.queryGoodsCategory(mParam.isWarehouse(), new SimpleObserver<CustomCategoryResp>(mView) {
            @Override
            public void onSuccess(CustomCategoryResp customCategoryResp) {
                customCategoryResp.processList();
                mView.setCategoryInfo(customCategoryResp.getList2());
                if (CommonUtils.isEmpty(customCategoryResp.getList2())) return;
                mParam.setSubID(customCategoryResp.getList2().get(0).getId());
                loadList();
            }
        });
    }

    @Override
    public void register(ISelectGoodsContract.ISelectGoodsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
