package com.hll_sc_app.app.goods.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;

import java.io.File;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
public interface GoodsAddContract {

    interface IGoodsAddView extends ILoadView {
        /**
         * 上传图片成功
         *
         * @param url         图片地址
         * @param requestCode 主图、辅图
         */
        void uploadSuccess(String url, int requestCode);

        /**
         * 显示商城分类
         *
         * @param resp resp
         */
        void showCategorySelectWindow(CategoryResp resp);

        /**
         * 显示自定义分类名称
         *
         * @param bean bean
         */
        void showCustomCategory(CustomCategoryBean bean);
    }

    interface IGoodsAddPresenter extends IPresenter<IGoodsAddView> {
        /**
         * 上传图片
         *
         * @param file        图片文件
         * @param requestCode 主图、辅图
         */
        void uploadImg(File file, int requestCode);

        /**
         * 查询经营品类
         */
        void queryCategory();

        /**
         * 复制商城分类为自定义分类
         *
         * @param categoryItem1 categoryItem
         * @param categoryItem2 categoryItem
         * @param categoryItem3 categoryItem
         */
        void copyToCustomCategory(CategoryItem categoryItem1, CategoryItem categoryItem2, CategoryItem categoryItem3);
    }
}
