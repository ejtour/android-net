package com.hll_sc_app;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.hll_sc_app.app.submit.BackType;
import com.hll_sc_app.app.submit.IBackType;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.DaoSessionManager;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.receiver.ActivityLifecycleHandler;
import com.hll_sc_app.receiver.NotificationMessageReceiver;
import com.hll_sc_app.utils.Constants;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyApplication extends Application {
    private static MyApplication instance;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            ClassicsHeader header = new ClassicsHeader(context);
            header.setEnableLastTime(false);
            header.setTextSizeTitle(13F);
            return header;
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            ClassicsFooter footer = new ClassicsFooter(context);
            footer.setTextSizeTitle(13F);
            return footer;
        });
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private ActivityLifecycleHandler mActivityLifecycleHandler;

    private IWXAPI mWxApi;

    public IWXAPI getWxApi() {
        if (mWxApi == null)
            regToWx();
        return mWxApi;
    }

    private void regToWx() {
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, true);
        mWxApi.registerApp(Constants.WX_APP_ID);
    }

    @Override
    public void onTerminate() {
        Beta.unInit();
        unRegFromWx();
        super.onTerminate();
    }

    private void unRegFromWx() {
        if (mWxApi == null) return;
        mWxApi.unregisterApp();
        mWxApi = null;
    }

    public BackType getLastBackType() {
        IBackType backType = null;
        if (mActivityLifecycleHandler != null)
            backType = mActivityLifecycleHandler.getLastBackType();
        if (backType != null) return backType.getBackType();
        return BackType.ORDER_LIST;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        hideWarnDialog();
        mActivityLifecycleHandler = new ActivityLifecycleHandler(new ActivityFrontListener());
        registerActivityLifecycleCallbacks(mActivityLifecycleHandler);
        ToastUtils.init(this);
        instance = this;
        // 初始化 ARouter
        initRouter(this);
        // SharePreference初始化
        GlobalPreference.init(this);
        LogUtil.isLog = BuildConfig.isDebug;
        initCloudChannel(this);
        DaoSessionManager.init(this);
        NotificationMessageReceiver.createChannel(this);
        initBugly();
    }

    private void initBugly() {
        Beta.initDelay = 3000;
        Beta.enableNotification = true;
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade;
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(App.INSTANCE);
        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            @Override
            public Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                UserBean bean = GreenDaoUtils.getUser();
                if (bean != null) {
                    try {
                        map.put("employeeName", bean.getEmployeeName());
                        map.put("groupID", bean.getGroupID());
                        map.put("groupName", bean.getGroupName());
                        map.put("authType", bean.getAuthType());
                        map.put("accountType", bean.getAccountType());
                        map.put("curRole", bean.getCurRole());
                        map.put("loginPhone", bean.getLoginPhone());
                    } catch (Exception e) {
                        map.put("strategy", e.getClass().getSimpleName() + ":" + e.getMessage());
                    }
                }
                return map;
            }

            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                try {
                    return "Extra data.".getBytes("UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        });
        Bugly.init(this, Constants.BUGLY_ID, BuildConfig.isDebug, strategy);
    }

    /**
     * 隐藏Android P使用私有API弹出warning
     */
    private void hideWarnDialog() {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initRouter(MyApplication myApplication) {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.isDebug) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！
            //线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(myApplication);
    }

    /**
     * 初始化云推送通道
     *
     * @param context context
     */
    private void initCloudChannel(Context context) {
        PushServiceFactory.init(context);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(context, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtil.d("PUSH", "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtil.d("PUSH", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    private static class ActivityFrontListener implements ActivityLifecycleHandler.Listener {
        @Override
        public void backToFront() {
        }
    }
}
