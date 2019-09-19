package com.hll_sc_app.app.order.place.select;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.order.place.GoodsCategoryBean;
import com.hll_sc_app.bean.order.place.GoodsCategoryResp;
import com.hll_sc_app.bean.order.place.SelectGoodsParam;
import com.hll_sc_app.bean.order.place.SettlementInfoReq;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.ArrayList;
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
                mParam.getSubID(),
                mParam.getThreeID(),
                mParam.getSearchWords(),
                mParam.getPurchaserID(),
                mParam.getShopID(),
                new SimpleObserver<List<GoodsBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(List<GoodsBean> goodsBeans) {
                        mView.setGoodsList(goodsBeans);
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
        Order.queryGoodsCategory(new SimpleObserver<GoodsCategoryResp>(mView) {
            @Override
            public void onSuccess(GoodsCategoryResp goodsCategoryResp) {
                if (CommonUtils.isEmpty(goodsCategoryResp.getList2())) return;
                if (!CommonUtils.isEmpty(goodsCategoryResp.getList3())) {
                    for (GoodsCategoryBean bean : goodsCategoryResp.getList3()) {
                        for (GoodsCategoryBean categoryBean : goodsCategoryResp.getList2()) {
                            if (bean.getShopCategoryPID().equals(categoryBean.getId())) {
                                if (categoryBean.getSubList() == null) {
                                    List<GoodsCategoryBean> list = new ArrayList<>();
                                    list.add(bean);
                                    categoryBean.setSubList(list);
                                } else categoryBean.getSubList().add(bean);
                                break;
                            }
                        }
                    }
                }
                mView.setCategoryInfo(goodsCategoryResp.getList2());
                mParam.setSubID(goodsCategoryResp.getList2().get(0).getId());
                loadList();
            }
        });
    }

    @Override
    public void register(ISelectGoodsContract.ISelectGoodsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
