package com.hll_sc_app.base.http;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.citymall.util.SystemUtils;

import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;

/**
 * 接口返回预处理
 *
 * @author zhuyingsong
 * @date 2018/12/13
 */
public abstract class BaseCallback<T> extends DisposableObserver<T> {
    protected static final String CODE_NULL_POINTER = "1007";

    @Override
    public void onNext(T resp) {
        if (resp != null) {
            onSuccess(resp);
        } else {
            onFailure(new UseCaseException("提示", "服务异常，请稍后重试"));
        }
    }

    @Override
    public void onError(Throwable e) {
        // 接口性错误
        if (e instanceof UseCaseException) {
            if (TextUtils.equals(((UseCaseException) e).getCode(), "00120110118")) {
//                RouterUtil.goToActivity(RouterConfig.USER_LOGIN);
            }
            onFailure((UseCaseException) e);
            return;
        }
        // 网络性错误
        UseCaseException ex;
        if (!SystemUtils.isNetConnected()) {
            ex = new UseCaseException(UseCaseException.Level.NET, "999", "无网络连接，请检查是否连接到网络");
        } else if (e instanceof JsonParseException
            || e instanceof JSONException
            || e instanceof JsonSerializer
            || e instanceof NotSerializableException
            || e instanceof ParseException) {
            ex = new UseCaseException(UseCaseException.Level.NET, "1001", "解析错误");
        } else if (e instanceof ClassCastException) {
            ex = new UseCaseException(UseCaseException.Level.NET, "1002", "类型转换错误");
        } else if (e instanceof ConnectException) {
            ex = new UseCaseException(UseCaseException.Level.NET, "1003", "连接失败");
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new UseCaseException(UseCaseException.Level.NET, "1004", "证书验证失败");
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new UseCaseException(UseCaseException.Level.NET, "1005", "连接超时");
        } else if (e instanceof UnknownHostException) {
            ex = new UseCaseException(UseCaseException.Level.NET, "1006", "无法解析该域名");
        } else if (e instanceof NullPointerException) {
            ex = new UseCaseException(UseCaseException.Level.NET, CODE_NULL_POINTER, "NullPointerException");
        } else {
            ex = new UseCaseException(UseCaseException.Level.NET, "1000", "未知错误");
        }
        onFailure(ex);
    }

    @Override
    public void onComplete() {
        // no-op
    }


    /**
     * 请求成功
     *
     * @param t result
     */
    public abstract void onSuccess(T t);

    /**
     * 请求出现异常
     *
     * @param e exception
     */
    public abstract void onFailure(UseCaseException e);
}
