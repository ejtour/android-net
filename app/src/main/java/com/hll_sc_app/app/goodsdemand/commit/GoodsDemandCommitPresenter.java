package com.hll_sc_app.app.goodsdemand.commit;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;
import com.hll_sc_app.rest.Upload;

import java.io.File;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/22
 */

public class GoodsDemandCommitPresenter implements IGoodsDemandCommitContract.IGoodsDemandCommitPresenter {
    private IGoodsDemandCommitContract.IGoodsDemandCommitView mView;

    private GoodsDemandCommitPresenter() {
    }

    public static GoodsDemandCommitPresenter newInstance() {
        return new GoodsDemandCommitPresenter();
    }

    @Override
    public void commit(GoodsDemandReq req) {
        Other.addGoodsDemand(req, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        });
    }

    @Override
    public void upload(File file) {
        Upload.imageUpload(file, new SimpleObserver<String>(mView) {
            @Override
            public void onSuccess(String s) {
                mView.showImg(s);
            }
        });
    }

    @Override
    public void register(IGoodsDemandCommitContract.IGoodsDemandCommitView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
