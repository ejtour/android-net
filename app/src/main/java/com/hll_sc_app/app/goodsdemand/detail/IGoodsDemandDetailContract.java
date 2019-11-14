package com.hll_sc_app.app.goodsdemand.detail;

import android.support.annotation.IntDef;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

public interface IGoodsDemandDetailContract {
    interface IGoodsDemandDetailView extends ILoadView {
        void replySuccess();
    }

    interface IGoodsDemandDetailPresenter extends IPresenter<IGoodsDemandDetailView> {
        void reply(String content, @TARGET int target);
    }

    @IntDef({TARGET.CUSTOMER, TARGET.SALE})
    @Retention(RetentionPolicy.SOURCE)
    @interface TARGET {
        int SALE = 0;
        int CUSTOMER = 1;
    }
}
