package com.hll_sc_app.app.info.modify;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

public interface IInfoModifyContract {
    interface IInfoModifyView extends ILoadView {
        void success();
    }

    interface IInfoModifyPresenter extends IPresenter<IInfoModifyView> {
        void save(String content);
    }
}
