package com.hll_sc_app.receiver;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.hll_sc_app.citymall.util.LogUtil;

/**
 * 推送接收
 *
 * @author zhuyingsong
 * @date 2018/12/18
 */
public class NotificationMessageReceiver extends MessageReceiver {
    public static final String PAGE_DATA_KEY = "status";
    public static final String PAGE_CODE_ORDER = "orderList";
    public static final String PAGE_CODE_DISCOUNT = "discountList";

    private static void toDiscount() {
//        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MY_DISCOUNT_LIST);
    }

    /**
     * 点击通知消息
     *
     * @param page 页面
     */
//    public static void clickNotification(Page page) {
//        switch (page.getPageCode()) {
//            case PAGE_CODE_ORDER:
//                toOrder(page);
//                break;
//            case PAGE_CODE_DISCOUNT:
//                toDiscount();
//                break;
//            default:
//                toMain();
//                break;
//        }
//    }

    /**
     * 处理订单相关的跳转
     *
     * @param page extraMap
     */
//    private static void toOrder(Page page) {
//        if (TextUtils.isEmpty(page.getPageData())) {
//            return;
//        }
//        try {
//            JSONObject object = new JSONObject(page.getPageData());
//            int status = object.getInt(PAGE_DATA_KEY);
//            if (ActivityLifecycleHandler.isApplicationInForeground()) {
//                ARouter.getInstance()
//                    .build(RouterConfig.ORDER_MAIN_SCENE)
//                    .withInt("object", status)
//                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                        Intent.FLAG_ACTIVITY_SINGLE_TOP)
//                    .setProvider(new LoginInterceptor())
//                    .navigation();
//            } else {
//                RouterUtil.goToActivity(RouterConfig.ROOT_HOME, page);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        LogUtil.d("notification", title);
        LogUtil.d("notification", summary);
        LogUtil.d("notification", extraMap);
        if (TextUtils.isEmpty(extraMap)) {
            return;
        }
//        Page page = JsonUtil.fromJson(extraMap, Page.class);
//        if (page != null) {
//            clickNotification(page);
//        }
    }

    /**
     * 跳转到首页
     */
//    private static void toMain() {
//        ARouter
//            .getInstance()
//            .build(RouterConfig.ROOT_HOME)
//            .navigation();
//    }
}
