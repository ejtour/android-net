package com.hll_sc_app.app.cooperation.detail.details.certification;

import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * 合作采购商详情-详细资料-认证信息
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
public class CooperationDetailsCertificationPresenter implements CooperationDetailsCertificationContract.ICertificationPresenter {
    private CooperationDetailsCertificationContract.ICertificationView mView;

    static CooperationDetailsCertificationPresenter newInstance() {
        return new CooperationDetailsCertificationPresenter();
    }

    @Override
    public void register(CooperationDetailsCertificationContract.ICertificationView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
