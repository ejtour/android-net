package com.hll_sc_app.base.utils.router;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;
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
     */
    public static void goToActivity(String url, Object object) {
        getPostcard(url, object).navigation();
    }

    /**
     * 获取基本类型传参的postcard
     *
     * @param url
     * @param object
     * @return
     */
    private static Postcard getPostcard(String url, Object object) {
        Postcard postcard = ARouter.getInstance().build(url);
        if (object == null) {
            return null;
        }
        String type = object.getClass().getSimpleName();
        if ("String".equals(type)) {
            postcard = postcard.withString("object", (String) object);
        } else if ("Integer".equals(type)) {
            postcard = postcard.withInt("object", (Integer) object);
        } else if ("Boolean".equals(type)) {
            postcard = postcard.withBoolean("object", (Boolean) object);
        } else if ("Float".equals(type)) {
            postcard = postcard.withFloat("object", (Float) object);
        } else if ("Long".equals(type)) {
            postcard = postcard.withLong("object", (Long) object);
        }
        return postcard.setProvider(new LoginInterceptor());
    }

    /**
     * 通过url跳转 startActivityForResult 传基本类型参数
     *
     * @param url
     * @param activity
     * @param requestCode
     * @param object
     */
    public static void goToActivity(String url, Activity activity, int requestCode, Object object) {
        getPostcard(url, object).navigation(activity, requestCode);
    }

    /**
     * 通过url跳转页面
     *
     * @param postCard 登录跳转前的目标页面
     */
    static void goToLogin(String postCard, Bundle bundle) {
        ARouter
            .getInstance()
            .build(RouterConfig.USER_LOGIN)
            .with(bundle)
            .withString(DESTINATION, postCard)
            .navigation();
    }

    /**
     * 通过url跳转页面
     *
     * @param postCard 登录跳转前的目标页面
     */
    public static void goToLogin(String postCard) {
        ARouter
            .getInstance()
            .build(RouterConfig.USER_LOGIN)
            .withString(DESTINATION, postCard)
            .navigation();
    }

    /**
     * 通过url跳转页面并且关闭当前页面
     * ARouter跳转后立即finish会使转场动画失效
     *
     * @param url     URL
     * @param context Activity and so on.
     */
    public static void goToActivity(String url, Activity context) {
        ARouter
            .getInstance()
            .build(url)
            .setProvider(new LoginInterceptor())
            .navigation(context, new NavCallback() {
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
     * 通过url跳转页面并且关闭当前页面
     * ARouter跳转后立即finish会使转场动画失效
     *
     * @param url     URL
     * @param context Activity and so on.
     */
    public static void goToActivity(String url, Activity context, Object object) {
        Postcard postcard = ARouter.getInstance().build(url);
        if (object == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        if ("String".equals(type)) {
            postcard = postcard.withString("object", (String) object);
        } else if ("Integer".equals(type)) {
            postcard = postcard.withInt("object", (Integer) object);
        } else if ("Boolean".equals(type)) {
            postcard = postcard.withBoolean("object", (Boolean) object);
        } else if ("Float".equals(type)) {
            postcard = postcard.withFloat("object", (Float) object);
        } else if ("Long".equals(type)) {
            postcard = postcard.withLong("object", (Long) object);
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
    public static void goToActivity(String url, Activity activity, int requestCode, ArrayList<? extends Parcelable> value) {
        ARouter.getInstance().build(url)
            .withParcelableArrayList("parcelable", value)
            .setProvider(new LoginInterceptor())
            .navigation(activity, requestCode);
    }

    /**
     * startActivityForResult
     *
     * @param url URL
     */
    public static void goToActivity(String url, ArrayList<? extends Serializable> value) {
        ARouter.getInstance().build(url)
            .withSerializable("parcelable", value)
            .setProvider(new LoginInterceptor())
            .navigation();
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
