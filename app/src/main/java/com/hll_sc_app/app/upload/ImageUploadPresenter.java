package com.hll_sc_app.app.upload;

import android.os.Bundle;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

public class ImageUploadPresenter implements IImageUploadContract.IImageUploadPresenter {
    private IImageUploadContract.IImageUploadView mView;

    private ImageUploadPresenter() {
    }

    public static ImageUploadPresenter newInstance() {
        return new ImageUploadPresenter();
    }

    @Override
    public void upload(File file) {
        Upload.imageUpload(file, new SimpleObserver<String>(mView) {
            @Override
            public void onSuccess(String s) {
                mView.setImageUrl(s);
            }
        });
    }

    @Override
    public void register(IImageUploadContract.IImageUploadView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
