package com.hll_sc_app.rest;

import com.hll_sc_app.api.MessageService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.message.MessageBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class Message {
    /**
     * 查询消息汇总
     */
    public static void queryMessageSummary(SimpleObserver<List<MessageBean>> observer) {
        MessageService.INSTANCE
                .queryMessageSummary(BaseMapReq.newBuilder()
                        .put("employeeID", GreenDaoUtils.getUser().getEmployeeID())
                        .put("source", "supplier")
                        .create())
                .map(listBaseResp -> {
                    if (!CommonUtils.isEmpty(listBaseResp.getData())) {
                        for (MessageBean bean : listBaseResp.getData()) {
                            bean.preProcess();
                        }
                    }
                    return listBaseResp;
                })
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询用户消息列表
     *
     * @param pageNum 页码
     */
    public static void queryMessageList(int pageNum, SimpleObserver<SingleListResp<MessageBean>> observer) {
        MessageService.INSTANCE
                .queryMessageList(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("userID", GreenDaoUtils.getUser().getEmployeeID())
                        .create())
                .map(singleListRespBaseResp -> {
                    if (singleListRespBaseResp.getData() != null
                            && !CommonUtils.isEmpty(singleListRespBaseResp.getData().getRecords())) {
                        for (MessageBean bean : singleListRespBaseResp.getData().getRecords()) {
                            bean.preProcess();
                        }
                    }
                    return singleListRespBaseResp;
                })
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
