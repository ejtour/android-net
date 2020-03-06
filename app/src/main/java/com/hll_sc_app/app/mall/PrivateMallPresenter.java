package com.hll_sc_app.app.mall;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.mall.PrivateMallBean;
import com.hll_sc_app.bean.mall.PrivateMallResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/27
 */

public class PrivateMallPresenter implements IPrivateMallContract.IPrivateMallPresenter {
    private IPrivateMallContract.IPrivateMallView mView;

    @Override
    public void start() {
        User.queryGroupQRCode(new SimpleObserver<PrivateMallResp>(mView) {
            @Override
            public void onSuccess(PrivateMallResp privateMallResp) {
                List<PrivateMallBean> list = new ArrayList<>();
                if (privateMallResp != null) {
                    if (!TextUtils.isEmpty(privateMallResp.getToCCode())) {
                        list.add(PrivateMallBean.createToC(privateMallResp.getToCCode(), privateMallResp.getToCLinkUrl()));
                    }
                    if (!TextUtils.isEmpty(privateMallResp.getToBCode())) {
                        list.add(PrivateMallBean.createToB(privateMallResp.getToBCode(), privateMallResp.getToBLinkUrl()));
                    }
                }
                mView.setData(list);
            }
        });
    }

    @Override
    public void register(IPrivateMallContract.IPrivateMallView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
