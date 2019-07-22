package com.hll_sc_app.app.setting.priceratio;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;

import java.util.List;

/**
 * 设置界面-价格比例设置-列表
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
public interface PriceRatioTemplateListContract {

    interface IPriceRatioView extends ILoadView {
        /**
         * 展示比例模板列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showPriceRatioTemplate(List<RatioTemplateBean> list, boolean append, int total);

        /**
         * 模板类型
         *
         * @return (1 - 协议价比例模板, 2 - 售价比例模板)
         */
        String getTemplateType();
    }

    interface IPriceRatioPresenter extends IPresenter<IPriceRatioView> {
        /**
         * 加载比例模板列表
         *
         * @param showLoading true-显示对话框
         */
        void queryPriceRatioTemplate(boolean showLoading);

        /**
         * 加载更多比例模板列表
         */
        void queryMorePriceRatioTemplate();

        /**
         * 删除报价模板
         *
         * @param templateId 模板ID
         */
        void delPriceRatioTemplate(String templateId);
    }
}
