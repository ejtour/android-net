package com.hll_sc_app.app.deliverymanage.deliverytype.company.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.DeliveryCompanyBean;

import java.util.List;

/**
 * 选择第三方物流公司
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
public interface DeliveryCompanyAddContract {

    interface IDeliveryCompanyAddView extends ILoadView {
        /**
         * 展示第三方物流公司列表
         *
         * @param list list
         */
        void showCompanyList(List<DeliveryCompanyBean> list);
    }

    interface IDeliveryCompanyAddPresenter extends IPresenter<IDeliveryCompanyAddView> {
        /**
         * 添加三方配送公司
         *
         * @param list 三方配送公司名称
         */
        void addDeliveryCompany(List<String> list);
    }
}
