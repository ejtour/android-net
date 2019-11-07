package com.hll_sc_app.app.setting.tax.special;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.bean.user.SpecialTaxSaveReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/31
 */

public interface ISpecialTaxSettingContract {
    interface ISpecialTaxSettingView extends ILoadView {
        void setData(List<SpecialTaxBean> list);
        void cacheData(List<SpecialTaxBean> list);
    }

    interface ISpecialTaxSettingPresenter extends IPresenter<ISpecialTaxSettingView> {
        void save(SpecialTaxSaveReq req);
    }
}
