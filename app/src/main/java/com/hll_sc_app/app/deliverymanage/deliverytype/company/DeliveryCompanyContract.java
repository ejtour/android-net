package com.hll_sc_app.app.deliverymanage.deliverytype.company;

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
public interface DeliveryCompanyContract {

    interface IDeliveryCompanyView extends ILoadView {
        /**
         * 展示第三方物流公司列表
         *
         * @param list list
         */
        void showCompanyList(List<DeliveryCompanyBean> list);

        /**
         * 编辑成功
         */
        void editSuccess();
    }

    interface IDeliveryCompanyPresenter extends IPresenter<IDeliveryCompanyView> {
        /**
         * 加载物流公司列表列表
         */
        void queryDeliveryCompanyList();

        /**
         * 修改三方配送公司状态
         *
         * @param list 设置为生效的三方配送公司主键ID列表
         */
        void editDeliveryCompanyStatus(List<String> list);
    }
}
