package com.hll_sc_app.app.mall;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.mall.PrivateMallBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/27
 */

public interface IPrivateMallContract {
    interface IPrivateMallView extends ILoadView{
        void setData(List<PrivateMallBean> list);
    }

    interface IPrivateMallPresenter extends IPresenter<IPrivateMallView>{

    }
}
