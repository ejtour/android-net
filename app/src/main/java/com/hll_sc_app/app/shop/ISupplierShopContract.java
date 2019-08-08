package com.hll_sc_app.app.shop;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.shop.SupplierShopBean;
import com.hll_sc_app.bean.user.CategoryItem;

import java.io.File;
import java.util.List;

public interface ISupplierShopContract {
    interface ISupplierShopView extends ILoadView {
        /**
         * 展示分类
         */
        void showCategoryWindow(List<CategoryItem> list);

        /**
         * 展示
         */
        void show(SupplierShopBean bean);

        /**
         * 保存成功
         */
        void success();
        /**
         * 展示图片
         */
        void showPhoto(String url);

    }

    interface ISupplierShopPresenter extends IPresenter<ISupplierShopContract.ISupplierShopView> {
        /**
         * 查询供应商店铺
         */
        void listSupplierShop();

        /**
         * 编辑供应商店铺
         */
        void updateSupplierShop(SupplierShopBean bean);

        /**
         * 获取分类数据
         */
        void showCategory();

        /**
         * 上传图片
         */
        void imageUpload(File imageFile);
    }
}
