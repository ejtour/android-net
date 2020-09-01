package com.hll_sc_app.app.goods.assign;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsAssignBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public interface IGoodsAssignContract {
    interface IGoodsAssignView extends ILoadView {
        void setData(List<GoodsAssignBean> list, boolean append);

        void delSuccess();

        int getType();
    }

    interface IGoodsAssignPresenter extends IPresenter<IGoodsAssignView> {
        void refresh();

        void loadMore();

        void del(String id);
    }
}
