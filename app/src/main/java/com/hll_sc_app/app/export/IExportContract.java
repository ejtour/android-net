package com.hll_sc_app.app.export;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.export.ExportType;
import com.hll_sc_app.impl.IExportView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

interface IExportContract {
    interface IExportPresenter extends IPresenter<IExportView> {
        void export(ExportType type, String email, String source);
    }
}
