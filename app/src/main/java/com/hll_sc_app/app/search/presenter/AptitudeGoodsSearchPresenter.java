package com.hll_sc_app.app.search.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/25
 */
public class AptitudeGoodsSearchPresenter extends BaseSearchPresenter {
    @Override
    public void requestSearch(String searchWords) {
        Bundle extra = mView.getExtra();
        boolean enableReq = false;
        if (extra != null)
            enableReq = extra.getBoolean("enableReq");
        if (TextUtils.isEmpty(searchWords) || !enableReq) {
            Observable.timer(0, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mView.refreshSearchData(new ArrayList<>()));
            return;
        }
        Aptitude.queryGoodsAptitudeList(BaseMapReq.newBuilder()
                .put("aptitudeName", searchWords)
                .put("groupID", UserConfig.getGroupID())
                .create(), new SimpleObserver<SingleListResp<AptitudeBean>>(mView, false) {
            @Override
            public void onSuccess(SingleListResp<AptitudeBean> aptitudeBeanSingleListResp) {
                List<AptitudeBean> records = aptitudeBeanSingleListResp.getRecords();
                Set<NameValue> set = new LinkedHashSet<>();
                if (!CommonUtils.isEmpty(records)) {
                    for (AptitudeBean bean : records) {
                        set.add(new NameValue(bean.getAptitudeName(), bean.getAptitudeType()));
                    }
                }
                mView.refreshSearchData(new ArrayList<>(set));
            }
        });
    }
}
