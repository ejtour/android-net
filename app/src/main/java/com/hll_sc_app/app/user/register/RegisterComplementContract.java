package com.hll_sc_app.app.user.register;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.RegisterReq;

import java.util.List;

/**
 * 注册页面-完善资料
 *
 * @author zhuyingsong
 * @date 2019/6/6
 */
public interface RegisterComplementContract {

    interface IRegisterComplementView extends ILoadView {
        /**
         * 注册成功
         */
        void registerComplementSuccess();

        /**
         * 显示经营分类列表
         *
         * @param list 分类数据
         */
        void showCategoryWindow(List<CategoryItem> list);
    }

    interface IRegisterComplementPresenter extends IPresenter<IRegisterComplementView> {
        /**
         * 查询经营品类
         *
         * @param show 是否显示弹窗
         */
        void queryCategory(boolean show);

        /**
         * 去注册
         *
         * @param req 请求参数
         */
        void toRegisterComplement(RegisterReq req);
    }
}
