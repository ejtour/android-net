package com.hll_sc_app.app.goods;

import android.support.annotation.StringDef;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 首页商品管理Fragment
 *
 * @author 朱英松
 * @date 2019/7/3
 */
interface GoodsHomeContract {
    @StringDef({
        ExportType.EXPORT_GOODS, ExportType.EXPORT_RECORDS
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface ExportType {
        /**
         * 导出商品列表
         */
        String EXPORT_GOODS = "1";
        /**
         * 上下架记录导出
         */
        String EXPORT_RECORDS = "2";
    }

    interface IGoodsHomeView extends ILoadView {
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
        void bindEmail(@GoodsHomeContract.ExportType String type);

        /**
         * 邮箱绑定成功
         *
         * @param type 1-商品导出 2-上下架记录
         */
        void bindSuccess(@ExportType String type);
    }

    interface IGoodsHomePresenter extends IPresenter<IGoodsHomeView> {
        /**
         * 商品列表导出
         *
         * @param actionType    操作类型（warehouse-采购商代仓商品查询，normalProduct-普通商品）
         * @param productStatus 商品状态(1-未提交，2-待审核，3-未通过，4-已上架，5-未上架，6-其他商品)
         */
        void exportGoodsList(String actionType, String productStatus);

        /**
         * 绑定员工邮箱
         *
         * @param email 员工邮箱
         * @param type  导出类型
         */
        void toBindEmail(String email, @GoodsHomeContract.ExportType String type);
    }
}
