package com.hll_sc_app.app.upload;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;

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
    public void upload(String path) {
        Upload.upload(mView, path, mView::setImageUrl);
    }

    @Override
    public void register(IImageUploadContract.IImageUploadView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
