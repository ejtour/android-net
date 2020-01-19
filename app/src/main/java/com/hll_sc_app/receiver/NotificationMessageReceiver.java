package com.hll_sc_app.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.notification.Page;
import com.hll_sc_app.citymall.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 推送接收
 *
 * @author zhuyingsong
 * @date 2018/12/18
 */
public class NotificationMessageReceiver extends MessageReceiver {
    public static final String PAGE_BILL_STATUS = "status";
    public static final String PAGE_BILL_ID = "subBillID";
    public static final String PAGE_CODE_ORDER = "orderList";
    public static final String PAGE_CODE_ORDER_DETAIL = "orderDetail";

    public static void createChannel(Context ctx) {
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        NotificationChannel channel = new NotificationChannel("default", ctx.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        channel.enableVibration(true);
        manager.createNotificationChannel(channel);
    }

    /**
     * 点击通知消息
     *
     * @param page 页面
     */
    public static void handleNotification(Page page) {
        if (page == null) return;
        switch (page.getPageCode()) {
            case PAGE_CODE_ORDER:
                toOrder(page);
                break;
            case PAGE_CODE_ORDER_DETAIL:
                toOrderDetail(page);
                break;
            default:
                toMain(page);
                break;
        }
    }

    /**
     * 处理订单详情相关的跳转
     *
     * @param page extraMap
     */
    private static void toOrderDetail(Page page) {
        if (TextUtils.isEmpty(page.getPageData())) {
            return;
        }
        try {
            JSONObject object = new JSONObject(page.getPageData());
            String subBillID = object.getString(PAGE_BILL_ID);
            ARouter.getInstance()
                    .build(RouterConfig.ORDER_DETAIL)
                    .withString("object0", subBillID)
                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setProvider(new LoginInterceptor())
                    .navigation();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理订单相关的跳转
     *
     * @param page extraMap
     */
    private static void toOrder(Page page) {
        if (TextUtils.isEmpty(page.getPageData())) {
            return;
        }
        try {
            JSONObject object = new JSONObject(page.getPageData());
            int status = object.getInt(PAGE_BILL_STATUS);
            EventBus.getDefault().postSticky(new OrderEvent(OrderEvent.SELECT_STATUS, status));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到首页
     */
    private static void toMain(Page page) {
        ARouter.getInstance()
                .build(RouterConfig.ROOT_HOME)
                .withParcelable("parcelable", page)
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        LogUtil.d("PUSH", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        if (TextUtils.isEmpty(extraMap)) return;
        Page page = JsonUtil.fromJson(extraMap, Page.class);
        if (page != null) {
            if (PAGE_CODE_ORDER.equals(page.getPageCode()) || !ActivityLifecycleHandler.isApplicationInForeground()) {
                toMain(page);
            } else {
                handleNotification(page);
            }
        }
    }

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        LogUtil.d("PUSH", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
    }
}
