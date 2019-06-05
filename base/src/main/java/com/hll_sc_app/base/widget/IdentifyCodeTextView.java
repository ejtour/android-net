package com.hll_sc_app.base.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.GetIdentifyCodeReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.service.AccountService;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 验证码
 *
 * @author zc
 */
public class IdentifyCodeTextView extends AppCompatTextView {
    private GetIdentifyCodeReq mReq;
    private IdentifyCodeTextView mTextView;
    private IdentifyCodeOption mIdentifyCodeOption;
    private Disposable mdDisposable;
    private boolean isCountdown;

    public IdentifyCodeTextView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mTextView = this;
        Observable<Long> mObservable = Observable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS);
        this.setOnClickListener(v -> {
            if (mIdentifyCodeOption == null) {
                return;
            }
            if (!CommonUtils.isPhone(mIdentifyCodeOption.getParams().getLoginPhone())) {
                mIdentifyCodeOption.getError("输入手机号格式不正确");
                return;
            }
            mReq = mIdentifyCodeOption.getParams();
            BaseReq<GetIdentifyCodeReq> baseReq = new BaseReq<>();
            baseReq.setData(mReq);
            AccountService.INSTANCE
                .getIdentifyCode(baseReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    this.setEnabled(false);
                    startCountDown(mObservable);
                })
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        // no-op
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mTextView.setEnabled(true);
                        stopCountdown();
                        if (mIdentifyCodeOption != null) {
                            mIdentifyCodeOption.getError(e.getMessage());
                        }
                    }
                });
        });
    }

    /**
     * 开启倒计时
     */
    private void startCountDown(Observable<Long> observable) {
        stopCountdown();
        isCountdown = true;
        mdDisposable = observable.observeOn(AndroidSchedulers.mainThread())
            .doOnNext(aLong -> {
                if (mIdentifyCodeOption != null) {
                    mIdentifyCodeOption.onNext(aLong);
                }
            })
            .doOnComplete(() -> {
                stopCountdown();
                mTextView.setEnabled(true);
                if (mIdentifyCodeOption != null) {
                    mIdentifyCodeOption.onComplete();
                }
            })
            .subscribe();
    }

    /**
     * 停止倒计时
     */
    public void stopCountdown() {
        isCountdown = false;
        if (mdDisposable != null && !mdDisposable.isDisposed()) {
            mdDisposable.dispose();
        }
    }

    public IdentifyCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public IdentifyCodeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 绑定参数
     */
    public void bind(IdentifyCodeOption identifyCodeOption) {
        mIdentifyCodeOption = identifyCodeOption;
    }

    /**
     * 接口 bind方法中使用
     */
    public interface IdentifyCodeOption {
        /***
         * 设置请求参数
         */
        GetIdentifyCodeReq getParams();

        /**
         * 点击按钮后，每隔1秒调用此方法
         */
        void onNext(long seconds);

        /**
         * 倒计时结束
         */
        void onComplete();

        /**
         * 请求验证码失败
         */
        void getError(String msg);

    }
}
