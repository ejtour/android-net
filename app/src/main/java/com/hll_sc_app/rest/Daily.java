package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.DailyService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyEditReq;
import com.hll_sc_app.bean.daily.DailyReplyBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

public class Daily {

    /**
     * 查询日报列表
     *
     * @param send       是否为发送日报
     * @param pageNum    页码
     * @param typeString 类型 0-全部日报 1-未读日报 2-已读日报 3-未回复日报 4-已回复日报
     * @param startDate  起始日期
     * @param endDate    结束日期
     */
    public static void queryDaily(boolean send, int pageNum, int pageSize, String searchWords, String typeString, String startDate, String endDate, SimpleObserver<SingleListResp<DailyBean>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        List<String> roleCode = user.getRoleCode();
        boolean isManager = !CommonUtils.isEmpty(roleCode) && roleCode.contains("SalesManager");
        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        builder.put("pageNum", String.valueOf(pageNum))
                .put("pageSize", String.valueOf(pageSize))
                .put(send ? "employeeID" : "receiver", user.getEmployeeID())
                .put("employeeName", searchWords)
                .put("startDate", TextUtils.isEmpty(startDate) ? startDate : startDate + "000000")
                .put("groupID", user.getGroupID())
                .put("endDate", TextUtils.isEmpty(endDate) ? endDate : endDate + "235959");
        if (isManager && !send) builder.put("isSalesManager", "1");
        int type = CommonUtils.getInt(typeString);
        if (type > 0) {
            switch (type) {
                case 1:
                    builder.put("readStatus", "0");
                    break;
                case 2:
                    builder.put("readStatus", "1");
                    break;
                case 3:
                    builder.put("replyStatus", "0");
                    break;
                case 4:
                    builder.put("replyStatus", "1");
                    break;
            }
        }
        Observable<BaseResp<SingleListResp<DailyBean>>> observable;
        if (send) observable = DailyService.INSTANCE.querySendDaily(builder.create());
        else observable = DailyService.INSTANCE.queryReceiveDaily(builder.create());
        observable.compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询日报详情
     *
     * @param send 是否为发送日报
     * @param id   日报id
     */
    public static void queryDailyDetail(boolean send, String id, SimpleObserver<DailyBean> observer) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("id", id)
                .create();
        Observable<BaseResp<DailyBean>> observable;
        if (send) observable = DailyService.INSTANCE.querySendDailyDetail(req);
        else observable = DailyService.INSTANCE.queryReceiveDailyDetail(req);
        observable.compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 提交日报
     */
    public static void submitDaily(DailyEditReq req, SimpleObserver<Object> observer) {
        Observable<BaseResp<Object>> observable = TextUtils.isEmpty(req.getId()) ? DailyService.INSTANCE.addDaily(new BaseReq<>(req))
                : DailyService.INSTANCE.editDaily(new BaseReq<>(req));
        observable.compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 回复日报
     *
     * @param reply    回复内容
     * @param reportID 日报id
     */
    public static void replyDaily(String reply, String reportID, SimpleObserver<Object> observer) {
        UserBean user = GreenDaoUtils.getUser();
        DailyService.INSTANCE
                .replyDaily(BaseMapReq.newBuilder()
                        .put("reply", reply)
                        .put("reportID", reportID)
                        .put("employeeID", user.getEmployeeID())
                        .put("employeeName", user.getEmployeeName())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询日报回复
     *
     * @param pageNum  页码
     * @param reportID 日报id
     */
    public static void queryDailyReply(int pageNum, String reportID, SimpleObserver<SingleListResp<DailyReplyBean>> observer) {
        DailyService.INSTANCE
                .queryDailyReply(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("reportID", reportID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
