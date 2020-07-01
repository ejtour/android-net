package com.hll_sc_app.app.goodsdemand.commit;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;
import com.hll_sc_app.rest.Upload;

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
        SimpleObserver<Object> observer = new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        };
        if (TextUtils.isEmpty(req.getId())) {
            Other.addGoodsDemand(req, observer);
        } else {
            Other.editGoodsDemand(req, observer);
        }
    }

    @Override
    public void upload(String path) {
        Upload.upload(mView, path, mView::showImg);
    }

    @Override
    public void register(IGoodsDemandCommitContract.IGoodsDemandCommitView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
