package com.hll_sc_app.app.goods.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

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
         * @param url 图片地址
         */
        void uploadSuccess(String url);
    }

    interface IGoodsAddPresenter extends IPresenter<IGoodsAddView> {
        /**
         * 上传图片
         *
         * @param file 图片文件
         */
        void uploadImg(File file);
    }
}
