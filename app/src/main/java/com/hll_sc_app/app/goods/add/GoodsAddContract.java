package com.hll_sc_app.app.goods.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CopyCategoryBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;

import java.io.File;
import java.util.List;

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
        void showCustomCategory(CopyCategoryBean bean);

        /**
         * 显示商品标签选择页面
         *
         * @param list 商品标签数据
         */
        void showLabelSelectWindow(List<LabelBean> list);

        /**
         * 选择商品属性
         */
        void toProductAttrsActivity();

        /**
         * 添加成功
         *
         * @param edit true-编辑
         */
        void addSuccess(boolean edit);
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

        /**
         * 行业标签查询接口
         */
        void queryLabelList();

        /**
         * 商品添加
         *
         * @param bean 商品
         */
        void addProduct(GoodsBean bean);

        /**
         * 商品修改
         *
         * @param bean 商品
         */
        void editProduct(GoodsBean bean);
    }
}
