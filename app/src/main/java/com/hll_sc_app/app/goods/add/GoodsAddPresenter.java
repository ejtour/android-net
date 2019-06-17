package com.hll_sc_app.app.goods.add;

import com.hll_sc_app.app.user.register.RegisterPresenter;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
public class GoodsAddPresenter implements GoodsAddContract.IGoodsAddPresenter {
    private GoodsAddContract.IGoodsAddView mView;

    static GoodsAddPresenter newInstance() {
        return new GoodsAddPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(GoodsAddContract.IGoodsAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void uploadImg(File file) {
        RegisterPresenter.getUploadImgObservable(file)
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    // no-op
                }

                @Override
                public void onNext(String s) {
                    mView.uploadSuccess(s);
                }

                @Override
                public void onError(Throwable e) {
                    mView.showToast(e.getMessage());
                }

                @Override
                public void onComplete() {
                    // no-op
                }
            });
    }
}
