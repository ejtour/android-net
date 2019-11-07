package com.hll_sc_app.app.upload;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

public interface IImageUploadContract {
    interface IImageUploadView extends ILoadView{
        void setImageUrl(String url);
    }

    interface IImageUploadPresenter extends IPresenter<IImageUploadView>{
        void upload(File file);
    }
}
