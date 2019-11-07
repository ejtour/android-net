package com.hll_sc_app.app.setting.tax;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.user.TaxSaveReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/30
 */

public interface ITaxSettingContract {
    interface ITaxSettingView extends ILoadView {
        void setCategoryList(List<CustomCategoryBean> list);
        void saveSuccess();
    }

    interface ITaxSettingPresenter extends IPresenter<ITaxSettingView> {
        void save(TaxSaveReq req);
    }
}
