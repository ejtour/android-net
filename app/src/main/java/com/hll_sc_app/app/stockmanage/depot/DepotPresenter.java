package com.hll_sc_app.app.stockmanage.depot;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Stock;
import com.hll_sc_app.rest.User;

import java.util.List;

public class DepotPresenter implements IDepotContract.IDepotPresenter {
    private IDepotContract.IDepotView mView;

    private int mPageNum = 1;

    public static DepotPresenter newInstance() {
        return new DepotPresenter();
    }

    @Override
    public void register(IDepotContract.IDepotView view) {
        mView = view;
    }

    @Override
    public void start() {
        User.queryGroupParam("28", new SimpleObserver<List<GroupParamBean>>(mView) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                if (!CommonUtils.isEmpty(groupParamBeans)) {
                    mView.enableDetail(groupParamBeans.get(0).getParameValue() == 2);
                }
            }
        });
        loadList();
    }

    private void load(boolean showLoading) {
        Stock.queryDepotList(mPageNum, mView.getSearchWords(), new SimpleObserver<SingleListResp<DepotResp>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<DepotResp> storehouseBeanSingleListResp) {
                mView.setData(storehouseBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(storehouseBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void setDefault(String id) {
        Stock.setDefaultDepot(id, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.defaultIsOk();
            }
        });
    }

    @Override
    public void toggleStatus(String id, int active) {
        Stock.toggleDepotStatus(id, active, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.toggleSuccess();
            }
        });
    }

    @Override
    public void loadList() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }
}
