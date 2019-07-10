package com.hll_sc_app.app.agreementprice.quotation.add.ratiotemplate;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.RatioTemplateBean;

import java.util.List;

/**
 * 选择协议价比例模板列表
 *
 * @author zhuyingsong
 * @date 2019/7/10
 */
public interface QuotationRatioContract {

    interface IQuotationRatioView extends ILoadView {
        /**
         * 展示比例模板列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showRatioTemplateList(List<RatioTemplateBean> list, boolean append, int total);
    }

    interface IQuotationRatioPresenter extends IPresenter<IQuotationRatioView> {
        /**
         * 加载比例模板列表
         *
         * @param showLoading true-显示对话框
         */
        void queryRatioTemplateList(boolean showLoading);

        /**
         * 加载更多比例模板列表
         */
        void queryMoreRatioTemplateList();
    }
}
