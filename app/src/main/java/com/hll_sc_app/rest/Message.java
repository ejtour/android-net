package com.hll_sc_app.rest;

import android.text.TextUtils;

import com.hll_sc_app.api.IMService;
import com.hll_sc_app.api.MessageService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.message.ApplyMessageResp;
import com.hll_sc_app.bean.message.MessageBean;
import com.hll_sc_app.bean.message.MessageDetailBean;
import com.hll_sc_app.bean.message.MessageSettingBean;
import com.hll_sc_app.bean.message.UnreadResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.MessageUtil;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
        IMService.INSTANCE
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

    /**
     * 查询消息详情
     *
     * @param pageNum 页码
     * @param code    消息代码
     */
    public static void queryMessageDetail(int pageNum, int code, SimpleObserver<List<MessageDetailBean>> observer) {
        MessageService.INSTANCE
                .queryMessageDetail(BaseMapReq.newBuilder()
                        .put("pageNum", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("employeeID", GreenDaoUtils.getUser().getEmployeeID())
                        .put("source", "supplier")
                        .put("messageTypeCode", String.valueOf(code))
                        .create())
                .map(listBaseResp -> {
                    if (!CommonUtils.isEmpty(listBaseResp.getData())) {
                        for (MessageDetailBean bean : listBaseResp.getData()) {
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
     * 标记消息已读
     *
     * @param messageID 消息id
     * @param code      消息代码
     */
    public static void markAsRead(String messageID, int code, SimpleObserver<Object> observer) {
        MessageService.INSTANCE
                .markAsRead(BaseMapReq.newBuilder()
                        .put("employeeID", GreenDaoUtils.getUser().getEmployeeID())
                        .put("source", "supplier")
                        .put("messageTypeCode", String.valueOf(code))
                        .put("messageID", messageID)
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 清除未读消息
     *
     * @param topic 主题
     */
    public static void clearUnreadMessage(String topic, SimpleObserver<Object> observer) {
        IMService.INSTANCE
                .clearUnreadMessage(BaseMapReq.newBuilder()
                        .put("topic", topic)
                        .put("userID", GreenDaoUtils.getUser().getEmployeeID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询消息
     *
     * @param employeeID 员工id
     * @param groupID    集团id
     */
    public static void queryMessage(String employeeID, String groupID, BaseCallback<Object> callback) {
        if (!UserConfig.isLogin()) return;
        if (UserConfig.crm()) {
            MessageService.INSTANCE.queryUnreadNum(BaseMapReq.newBuilder()
                    .put("employeeID", employeeID)
                    .put("source", "supplier")
                    .create())
                    .map(new Precondition<>())
                    .flatMap((Function<UnreadResp, Observable<Object>>) unreadResp -> {
                        MessageUtil.instance().setUnreadNum(unreadResp.getUnreadNum());
                        return Observable.just(0);
                    })
                    .subscribe(callback);
        } else {
            MessageService.INSTANCE.queryUnreadNum(BaseMapReq.newBuilder()
                    .put("employeeID", employeeID)
                    .put("source", "supplier")
                    .create())
                    .map(new Precondition<>())
                    .flatMap((Function<UnreadResp, Observable<ApplyMessageResp>>) unreadResp -> {
                        MessageUtil.instance().setUnreadNum(unreadResp.getUnreadNum());
                        return MessageService.INSTANCE.queryApplyMessage(BaseMapReq.newBuilder()
                                .put("groupID", groupID)
                                .create())
                                .map(new Precondition<>());
                    })
                    .flatMap((Function<ApplyMessageResp, Observable<UnreadResp>>) applyMessageResp -> {
                        EventBus.getDefault().postSticky(applyMessageResp);
                        return MessageService.INSTANCE.queryDemandMessage(BaseMapReq.newBuilder()
                                .put("groupID", groupID)
                                .put("source", "2")
                                .create())
                                .map(new Precondition<>());
                    })
                    .flatMap((Function<UnreadResp, ObservableSource<?>>) unreadResp -> {
                        EventBus.getDefault().postSticky(unreadResp);
                        return Observable.just(0);
                    }).subscribe(callback);
        }
    }

    /**
     * 标记全部消息已读
     */
    public static void markAllAsRead(SimpleObserver<MsgWrapper<Object>> observer) {
        UserBean user = GreenDaoUtils.getUser();
        MessageService.INSTANCE
                .markAllAsRead(BaseMapReq.newBuilder()
                        .put("groupID", user.getGroupID())
                        .put("employeeID", user.getEmployeeID())
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 查询消息设置
     */
    public static void queryMessageSettings(SimpleObserver<List<MessageSettingBean>> observer) {
        MessageService.INSTANCE
                .queryMessageSettings(BaseMapReq.newBuilder()
                        .put("groupId", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 保存消息设置
     *
     * @param types 消息类型列表
     */
    public static void saveMessageSettings(List<String> types, SimpleObserver<MsgWrapper<Object>> observer) {
        MessageService.INSTANCE
                .saveMessageSettings(BaseMapReq.newBuilder()
                        .put("groupId", UserConfig.getGroupID())
                        .put("ignoreServiceType", TextUtils.join(",", types), true)
                        .create())
                .compose(ApiScheduler.getMsgLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
