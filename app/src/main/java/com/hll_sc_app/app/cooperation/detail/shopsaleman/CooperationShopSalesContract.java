package com.hll_sc_app.app.cooperation.detail.shopsaleman;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.EmployeeBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;

import java.util.List;

/**
 * 合作采购商详情- 批量指派销售/司机
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
public interface CooperationShopSalesContract {

    interface ICooperationAddShopView extends ILoadView {
        /**
         * 展示员工列表
         *
         * @param list   list
         * @param append 追加
         */
        void showEmployeeList(List<EmployeeBean> list, boolean append);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getKeyWord();

        /**
         * 获取角色类型
         *
         * @return 1-销售 2-司机
         */
        String getRoleType();

        /**
         * 修改成功
         */
        void editSuccess();
    }

    interface ICooperationAddShopPresenter extends IPresenter<ICooperationAddShopView> {
        /**
         * 查询员工列表
         *
         * @param show 是否显示 loading
         */
        void queryEmployeeList(boolean show);

        /*
         *加载下一页
         */
        void queryMoreEmployeeList();

        /**
         * 修改合作商人员信息
         *
         * @param req req
         */
        void editShopEmployee(ShopSettlementReq req);
    }
}
