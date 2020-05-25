package com.hll_sc_app.app.stockmanage.depot.detail;

import android.content.Context;

import com.hll_sc_app.app.stockmanage.depot.DepotHelper;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.stockmanage.DepotGoodsReq;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

class DepotDetailPresenter implements IDepotDetailContract.IDepotDetailPresenter {
    private IDepotDetailContract.IDepotDetailView mView;
    private int mPageNum;


    public static DepotDetailPresenter newInstance() {
        return new DepotDetailPresenter();
    }

    private DepotDetailPresenter() {
    }

    @Override
    public void start() {
        Stock.queryDepotInfo(mView.getDepotID(), new SimpleObserver<DepotResp>(mView) {
            @Override
            public void onSuccess(DepotResp depotResp) {
                inflateInfo(depotResp);
                mView.setData(depotResp);
            }
        });
        searchGoodsList();
    }

    private void inflateInfo(DepotResp depotResp) {
        if (depotResp.getIsWholeCountry() == 1) {
            depotResp.setWarehouseDeliveryRangeList(DepotHelper.getAllProvinceList((Context) mView));
        }
    }

    @Override
    public void register(IDepotDetailContract.IDepotDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void searchGoodsList() {
        mPageNum = 1;
        load(true, 10, mView.getSearchWords());
    }

    private void load(boolean showLoading, int size, String searchWords) {
        Stock.getDepotStoreGoods(mView.getDepotID(),
                mPageNum, size, searchWords,
                new SimpleObserver<SingleListResp<GoodsBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<GoodsBean> goodsBeanSingleListResp) {
                        if (size != 10) {
                            mView.cacheAllGoods(goodsBeanSingleListResp.getRecords());
                            return;
                        }
                        mView.setGoodsList(goodsBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(goodsBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false, 10, mView.getSearchWords());
    }

    @Override
    public void loadMore() {
        load(false, 10, mView.getSearchWords());
    }

    @Override
    public void delGoods(String goodsID) {
        Stock.delDepotGoods(mView.getDepotID(), goodsID, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.removeSuccess();
            }
        });
    }

    @Override
    public void getAllGoods() {
        load(true, 999, "");
    }

    @Override
    public void saveGoodsList(DepotGoodsReq req) {
        Stock.saveDepotGoodsList(req, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }
}
