package com.hll_sc_app.app.setting.priceratio.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.CategoryRatioListBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;

import java.util.List;

/**
 * 设置比例模版
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
public interface PriceRatioTemplateAddContract {

    interface IPriceRatioTemplateAddView extends ILoadView {

        /**
         * 处理数据
         *
         * @param list list
         */
        void processData(List<CategoryRatioListBean> list);

        /**
         * 模板类型
         *
         * @return (1 - 协议价比例模板, 2 - 售价比例模板)
         */
        String getTemplateType();

        /**
         * 新增、修改
         *
         * @return 新增-1 修改-2
         */
        String getSearchType();

        /**
         * 获取模板Id
         *
         * @return 模板Id
         */
        String getTemplateId();

        /**
         * 新增成功
         */
        void addSuccess();
    }

    interface IPriceRatioTemplateAddPresenter extends IPresenter<IPriceRatioTemplateAddView> {
        /**
         * 查询自定义分类
         */
        void queryCustomCategory();

        /**
         * 修改报价模板
         *
         * @param bean 模板
         */
        void editRatioTemplate(RatioTemplateBean bean);

        /**
         * 新增报价模板
         *
         * @param bean 模板
         */
        void addRatioTemplate(RatioTemplateBean bean);

    }
}
