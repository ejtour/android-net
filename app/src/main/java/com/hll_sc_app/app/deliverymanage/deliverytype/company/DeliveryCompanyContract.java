package com.hll_sc_app.app.deliverymanage.deliverytype.company;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.util.List;

/**
 * 选择第三方物流公司
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
public interface DeliveryCompanyContract {

    interface IDeliveryCompanyView extends ILoadView {
        /**
         * 编辑成功
         */
        void editSuccess();
    }

    interface IDeliveryCompanyPresenter extends IPresenter<IDeliveryCompanyView> {
        /**
         * 修改三方配送公司状态
         *
         * @param list 设置为生效的三方配送公司主键ID列表
         */
        void editDeliveryCompanyStatus(List<String> list);
    }
}
