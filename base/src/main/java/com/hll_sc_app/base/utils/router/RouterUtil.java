package com.hll_sc_app.base.utils.router;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;

/**
 * RouterUtil
 *
 * @author zhuyingsong
 * @date 2018/12/14
 */
public class RouterUtil {
    public static final String DESTINATION = "DESTINATION";

    /**
     * 通过url跳转页面
     *
     * @param url URL
     */
    public static void goToActivity(String url) {
        ARouter.getInstance().build(url).setProvider(new LoginInterceptor()).navigation();
    }

    /**
     * 通过url跳转页面
     *
     * @param url URL
     */
    public static void goToActivity(String url, Parcelable parcelable) {
        ARouter.getInstance().build(url).withParcelable("parcelable", parcelable).setProvider(new LoginInterceptor()).navigation();
    }

    /**
     * 通过url跳转页面
     *
     * @param url URL
     */
    public static void goToActivity(String url, Bundle bundle) {
        ARouter.getInstance().build(url).withBundle("bundle", bundle).setProvider(new LoginInterceptor()).navigation();
    }

    /***
     * 页面基本类型传参适配
     * @param objects object
     */
    public static void goToActivity(String url, Object... objects) {
        Postcard postcard = ARouter.getInstance().build(url);
        for (int i = 0; i < objects.length; i++) {
            getPostcard(postcard, "object" + i, objects[i]);
        }
        postcard.setProvider(new LoginInterceptor()).navigation();
    }

    /**
     * 获取基本类型传参的postcard
     *
     * @param postcard postcard
     * @param value    object
     * @param key      key
     */
    private static void getPostcard(Postcard postcard, String key, Object value) {
        String type = value.getClass().getSimpleName();
        if ("String".equals(type)) {
            postcard.withString(key, (String) value);
        } else if ("Integer".equals(type)) {
            postcard.withInt(key, (Integer) value);
        } else if ("Boolean".equals(type)) {
            postcard.withBoolean(key, (Boolean) value);
        } else if ("Float".equals(type)) {
            postcard.withFloat(key, (Float) value);
        } else if ("Long".equals(type)) {
            postcard.withLong(key, (Long) value);
        }
    }

    /***
     * 页面基本类型传参适配
     * @param objects object
     * @param context  context
     */
    public static void goToActivity(String url, Activity context, Object... objects) {
        Postcard postcard = ARouter.getInstance().build(url);
        for (int i = 0; i < objects.length; i++) {
            getPostcard(postcard, "object" + i, objects[i]);
        }
        postcard.setProvider(new LoginInterceptor())
                .navigation(context, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        context.finish();
                    }
                });
    }

    /**
     * 通过url跳转页面
     *
     * @param postCard 登录跳转前的目标页面
     */
    static void goToLogin(String postCard, Bundle bundle) {
        ARouter.getInstance().build(RouterConfig.USER_LOGIN).with(bundle).withString(DESTINATION, postCard).navigation();
    }

    /**
     * 通过url跳转页面
     *
     * @param postCard 登录跳转前的目标页面
     */
    public static void goToLogin(String postCard) {
//        ARouter
//            .getInstance()
//            .build(RouterConfig.USER_LOGIN)
//            .withString(DESTINATION, postCard)
//            .navigation();
    }

    /**
     * 通过url跳转页面并且关闭当前页面
     * ARouter跳转后立即finish会使转场动画失效
     *
     * @param url     URL
     * @param context Activity and so on.
     */
    public static void goToActivity(String url, Activity context) {
        ARouter.getInstance().build(url).setProvider(new LoginInterceptor()).navigation(context, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                context.finish();
            }
        });
    }

    /**
     * 通过url跳转页面并且关闭当前页面
     * ARouter跳转后立即finish会使转场动画失效
     *
     * @param url     URL
     * @param context Activity and so on.
     */
    public static void goToActivity(String url, Activity context, Parcelable parcelable) {
        ARouter.getInstance().build(url)
                .withParcelable("parcelable", parcelable)
                .setProvider(new LoginInterceptor())
                .navigation(context, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        context.finish();
                    }
                });
    }

    /**
     * startActivityForResult
     *
     * @param url         URL
     * @param activity    activity
     * @param requestCode requestCode
     * @param args        args
     */
    public static void goToActivity(String url, Activity activity, int requestCode, Object... args) {
        Postcard postcard = ARouter.getInstance().build(url);
        for (int i = 0; i < args.length; i++) {
            getPostcard(postcard, "object" + i, args[i]);
        }
        postcard.setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    /**
     * startActivityForResult
     *
     * @param url         URL
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void goToActivity(String url, Activity activity, int requestCode, Parcelable parcelable) {
        ARouter.getInstance().build(url)
                .withParcelable("parcelable", parcelable)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    /**
     * startActivityForResult
     *
     * @param url         URL
     * @param activity    activity
     * @param requestCode requestCode
     * @param bundle      bundle
     */
    public static void goToActivity(String url, Activity activity, int requestCode, Bundle bundle) {
        ARouter.getInstance().build(url)
                .withBundle("bundle", bundle)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }


    /**
     * startActivityForResult
     *
     * @param url         URL
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void goToActivity(String url, Activity activity, int requestCode,
                                    ArrayList<? extends Parcelable> value) {
        ARouter.getInstance().build(url)
                .withParcelableArrayList("parcelable", value)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    /**
     * 页面间传递集合数据-Parcelable
     *
     * @param url URL
     */
    public static void goToActivity(String url, ArrayList<? extends Parcelable> value) {
        ARouter.getInstance().build(url)
                .withParcelableArrayList("parcelable", value)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    /**
     * 通过url跳转页面并且关闭当前页面
     * ARouter跳转后立即finish会使转场动画失效
     *
     * @param url     URL
     * @param context Activity and so on.
     */
    public static void goToActivity(String url, Activity context, ArrayList<? extends Parcelable> value) {
        ARouter.getInstance().build(url)
                .withParcelableArrayList("parcelable", value)
                .setProvider(new LoginInterceptor())
                .navigation(context, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        context.finish();
                    }
                });
    }

    /**
     * startActivityForResult
     *
     * @param url         URL
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void goToActivity(String url, Activity activity, int requestCode) {
        ARouter.getInstance().build(url).setProvider(new LoginInterceptor()).navigation(activity, requestCode);
    }

    /**
     * 获取Fragment
     *
     * @return Fragment
     */
    public static Fragment getFragment(String url) {
        return (Fragment) ARouter.getInstance().build(url).navigation();
    }


}
