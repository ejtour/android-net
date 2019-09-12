package com.hll_sc_app.app.aftersales.apply;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.aftersales.AfterSalesReasonBean;
import com.hll_sc_app.bean.window.NameValue;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public interface IAfterSalesApplyContract {

    interface IAfterSalesApplyText {
        /**
         * @return 页面标题
         */
        String getTitle();

        /**
         * @return 温馨提示
         */
        String getWarmTip();

        /**
         * @return 页面标签
         */
        String getLabel();

        /**
         * @return 原因前缀
         */
        String getReasonPrefix();

        /**
         * @return 输入框提示
         */
        String getEditHint();

        /**
         * @return 明细标签
         */
        String getDetailsLabel();

        String getAddTip();

        String getMoneyLabel();
    }

    interface IAfterSalesApplyStrategy {

        void init(AfterSalesApplyParam t);

        IAfterSalesApplyText getApplyText();

        List<NameValue> getReasonList();

        void setReasonList(List<NameValue> list);

        void submitSuccess(String id);

        IAfterSalesApplyPresenter getPresenter();

        BaseQuickAdapter createAdapter();

        void updateAdapter();
    }

    interface IAfterSalesApplyPresenter extends IPresenter<IAfterSalesApplyView> {
        void submit();

        default void getAfterSalesReasonList() {
        }

        default void getReturnableGoodsList() {
        }
    }

    interface IAfterSalesApplyView extends ILoadView {
        void updateDetailsData(List<AfterSalesDetailsBean> list);

        void submitSuccess(String id);

        void updateReasonList(List<AfterSalesReasonBean> list);
    }
}
