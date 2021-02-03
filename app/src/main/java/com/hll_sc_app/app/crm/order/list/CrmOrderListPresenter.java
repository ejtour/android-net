package com.hll_sc_app.app.crm.order.list;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.filter.OrderParam;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/6
 */

public class CrmOrderListPresenter implements ICrmOrderListContract.ICrmOrderListPresenter {
    private ICrmOrderListContract.ICrmOrderListView mView;
    private int mPageNum;
    private OrderParam mOrderParam;

    public static CrmOrderListPresenter newInstance(OrderParam param) {
        return new CrmOrderListPresenter(param);
    }

    private CrmOrderListPresenter(OrderParam orderParam) {
        mOrderParam = orderParam;
    }

    @Override
    public void start() {
        queryShopList();
        reload();
    }

    private void load(boolean showLoading) {
        Order.crmQueryOrderList(mPageNum, mView.getShopID(), mView.getBillStatus(),
                mOrderParam.getFormatCreateStart(Constants.UNSIGNED_YYYY_MM_DD),
                mOrderParam.getFormatCreateEnd(Constants.UNSIGNED_YYYY_MM_DD),
                mOrderParam.getFormatExecuteStart(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mOrderParam.getFormatExecuteEnd(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mOrderParam.getFormatSignStart(Constants.UNSIGNED_YYYY_MM_DD_HH),
                mOrderParam.getFormatSignEnd(Constants.UNSIGNED_YYYY_MM_DD_HH),
                new SimpleObserver<SingleListResp<OrderResp>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<OrderResp> orderRespSingleListResp) {
                        mView.setData(orderRespSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(orderRespSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        loadMore();
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void export(String email) {
        Order.exportNormal(mOrderParam, mView.getBillStatus(), 0, null,
                mView.getShopID(), email, null, Utils.getExportObserver(mView));
    }

    @Override
    public void queryShopList() {
        UserBean user = GreenDaoUtils.getUser();
        List<String> roleCode = user.getRoleCode();
        String salesmanID = roleCode != null && roleCode.contains("SalesManager") ? "" : user.getEmployeeID();
        Common.queryCooperationShop(BaseMapReq.newBuilder()
                .put("actionType", "crmBill")
                .put("groupID", user.getGroupID())
                .put("pageNo", "1")
                .put("pageSize", "999")
                .put("salesmanID", salesmanID, true)
                .create(), new SimpleObserver<CooperationShopListResp>(mView) {
            @Override
            public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                mView.cacheShopData(cooperationShopListResp.getShopList());
            }
        });
    }

    @Override
    public void reload() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(ICrmOrderListContract.ICrmOrderListView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
