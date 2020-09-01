package com.hll_sc_app.app.select.group.goodsassign;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsAssignBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/27
 */
public interface ISelectGroupContract {
    interface ISelectGroupView extends ILoadView {
        void setData(List<GoodsAssignBean> list, boolean append);

        String getSearchWords();
    }

    interface ISelectGroupPresenter extends IPresenter<ISelectGroupView> {
        void refresh();

        void loadMore();
    }
}
