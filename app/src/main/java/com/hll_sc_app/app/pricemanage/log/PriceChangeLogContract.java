package com.hll_sc_app.app.pricemanage.log;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.pricemanage.PriceLogBean;

import java.util.List;

/**
 * 售价设置-变更日志
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface PriceChangeLogContract {

    interface IPriceManageView extends ILoadView {
        /**
         * 展示售价变更日志列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showPriceLogList(List<PriceLogBean> list, boolean append, int total);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 获取开始时间
         *
         * @return 开始时间
         */
        String getStartTime();

        /**
         * 获取结束时间
         *
         * @return 结束时间
         */
        String getEndTime();

        /**
         * 导出成功
         *
         * @param email 邮箱地址
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param tip 失败提示
         */
        void exportFailure(String tip);

        /**
         * 绑定邮箱
         */
        void bindEmail();
    }

    interface IPriceManagePresenter extends IPresenter<IPriceManageView> {
        /**
         * 加载售价设置列表
         *
         * @param showLoading true-显示对话框
         */
        void queryPriceLogList(boolean showLoading);

        /**
         * 加载更多售价设置列表
         */
        void queryMorePriceLogList();

        /**
         * 导出日志
         *
         * @param email 邮箱地址
         */
        void exportLog(String email);
    }
}
