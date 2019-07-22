package com.hll_sc_app.base.http;

import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.MsgWrapper;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * 消除重复代码
 *
 * @author zhuyingsong
 * @date 2018/12/14
 */
public class ApiScheduler {

    public static <T> ObservableTransformer<T, T> getObservableScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    public static <R, T extends BaseResp<R>> ObservableTransformer<T, R> getDefaultObservableWithLoadingScheduler(SimpleObserver<R> observer) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> observer.startReq())
            .doFinally((Action) observer::reqOver);
    }

    public static <R, T extends BaseResp<R>> ObservableTransformer<T, MsgWrapper<R>> getMsgLoadingScheduler(SimpleObserver<MsgWrapper<R>> observer) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new MsgPrecondition<>())
                .doOnSubscribe(disposable -> observer.startReq())
                .doFinally((Action) observer::reqOver);
    }
}
