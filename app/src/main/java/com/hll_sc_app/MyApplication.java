package com.hll_sc_app;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.greendao.DaoSessionManager;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.receiver.ActivityLifecycleHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        hideWarnDialog();
        registerActivityLifecycleCallbacks(new ActivityLifecycleHandler(new ActivityFrontListener()));
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        instance = this;
        // 初始化 ARouter
        initRouter(this);
        // SharePreference初始化
        GlobalPreference.init(this);
        LogUtil.isLog = BuildConfig.isDebug;
//        initCloudChannel(this);
        DaoSessionManager.init(this);
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
//    private void initCloudChannel(Context context) {
//        PushServiceFactory.init(context);
//        CloudPushService pushService = PushServiceFactory.getCloudPushService();
//        pushService.register(context, new CommonCallback() {
//            @Override
//            public void onSuccess(String response) {
//                LogUtil.d("PUSH", "init cloudchannel success");
//            }
//
//            @Override
//            public void onFailed(String errorCode, String errorMessage) {
//                LogUtil.d("PUSH", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
//            }
//        });
//    }

    private static class ActivityFrontListener implements ActivityLifecycleHandler.Listener {
        @Override
        public void backToFront() {
        }
    }
}
