package com.hll_sc_app.app.cooperation.application.thirdpart.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserBean;


/**
 * 合作采购商-我收到的申请-第三方申请-详情
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
public interface CooperationThirdPartDetailContract {

    interface ICooperationThirdDetailView extends ILoadView {

        /**
         * 修改成功
         */
        void editSuccess();

        /**
         * 详细信息
         *
         * @param bean bean
         */
        void showView(ThirdPartyPurchaserBean bean);

        /**
         * 获取申请记录 Id
         *
         * @return 申请记录 Id
         */
        String getId();
    }

    interface ICooperationThirdPartDetailPresenter extends IPresenter<ICooperationThirdDetailView> {
        /**
         * 根据申请记录id查询第三方采购商审核记录详情
         */
        void queryThirdPartDetail();

        /**
         * 采购商申请状态变更
         *
         * @param req 请求参数
         */
        void editCooperationThirdPartStatus(BaseMapReq req);
    }
}
