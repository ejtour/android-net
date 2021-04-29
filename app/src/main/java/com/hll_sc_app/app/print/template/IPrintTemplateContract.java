package com.hll_sc_app.app.print.template;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.print.PrintTemplateBean;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
interface IPrintTemplateContract {
    interface IPrintTemplateView extends ILoadView {
        int getCurrentTab();

        PrintTemplateBean getCurTemplate();

        void setData(List<PrintTemplateBean> list);

        void dataChanged();
    }

    interface IPrintTemplatePresenter extends IPresenter<IPrintTemplateView> {
        void addToMyList();
        void enable();
        void delete();
    }
}
