package com.hll_sc_app.app.wallet.account.create;

import android.support.annotation.IntDef;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AuthInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public interface ICreateAccountContract {
    @IntDef({AreaType.PROVINCE, AreaType.CITY, AreaType.DISTRIBUTE})
    @Retention(RetentionPolicy.SOURCE)
    @interface AreaType {
        int PROVINCE = 2;
        int CITY = 3;
        int DISTRIBUTE = 4;
    }

    interface ICreateAccountView extends ILoadView {
        void handleAuthInfo(AuthInfo info);

        void handleAreaList(List<AreaInfo> areaInfoList);

        void createSuccess();
    }

    interface ICreateAccountPresenter extends IPresenter<ICreateAccountView> {
        void queryAreaList(@AreaType int areaType,String areaParentId);

        void createAccount(AuthInfo info);
    }
}
