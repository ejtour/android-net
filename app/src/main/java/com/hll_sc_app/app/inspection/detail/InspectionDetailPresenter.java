package com.hll_sc_app.app.inspection.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.inspection.InspectionBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Inspection;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

public class InspectionDetailPresenter implements IInspectionDetailContract.IInspectionDetailPresenter {
    private String mID;
    private IInspectionDetailContract.IInspectionDetailView mView;

    private InspectionDetailPresenter() {
    }

    public static InspectionDetailPresenter newInstance(String id) {
        InspectionDetailPresenter presenter = new InspectionDetailPresenter();
        presenter.mID = id;
        return presenter;
    }

    @Override
    public void start() {
        Inspection.getInspectionDetail(mID, new SimpleObserver<InspectionBean>(mView) {
            @Override
            public void onSuccess(InspectionBean bean) {
                mView.updateData(bean);
            }
        });
    }

    @Override
    public void register(IInspectionDetailContract.IInspectionDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
