package com.hll_sc_app.app.print.template;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.print.PrintTemplateBean;
import com.hll_sc_app.bean.print.PrintTemplateResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Print;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
class PrintTemplatePresenter implements IPrintTemplateContract.IPrintTemplatePresenter {
    private IPrintTemplateContract.IPrintTemplateView mView;

    @Override
    public void start() {
        Print.queryPrintTemplateList(mView.getCurrentTab(), new SimpleObserver<PrintTemplateResp>(mView) {
            @Override
            public void onSuccess(PrintTemplateResp printTemplateResp) {
                mView.setData(printTemplateResp.getList());
            }
        });
    }

    @Override
    public void register(IPrintTemplateContract.IPrintTemplateView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void addToMyList() {
        PrintTemplateBean template = mView.getCurTemplate();
        if (template == null) return;
        Print.templateAction(0, template.getId(), template.getTemplateType(), new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.dataChanged();
                start();
            }
        });
    }

    @Override
    public void enable() {
        PrintTemplateBean template = mView.getCurTemplate();
        if (template == null) return;
        Print.templateAction(1, template.getId(), template.getTemplateType(), getObserver());
    }

    @Override
    public void delete() {
        PrintTemplateBean template = mView.getCurTemplate();
        if (template == null) return;
        Print.templateAction(2, template.getId(), template.getTemplateType(), getObserver());
    }

    private SimpleObserver<Object> getObserver() {
        return new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                start();
            }
        };
    }
}
