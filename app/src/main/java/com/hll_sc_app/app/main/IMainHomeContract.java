package com.hll_sc_app.app.main;

import android.support.annotation.IntDef;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.common.SalesVolumeResp;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
interface IMainHomeContract {

    interface IMainHomeView extends ILoadView {
        void updateSalesVolume(SalesVolumeResp resp);

        @DateType
        int getDateType();
    }

    interface IMainHomePresenter extends IPresenter<IMainHomeView> {
        void querySalesVolume(boolean showLoading);
    }

    @IntDef({DateType.TYPE_DAY, DateType.TYPE_WEEK, DateType.TYPE_MONTH})
    @Retention(RetentionPolicy.SOURCE)
    @interface DateType {
        int TYPE_DAY = 0;
        int TYPE_WEEK = 1;
        int TYPE_MONTH = 2;
    }
}
