package com.hll_sc_app.app.aptitude;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeReq;

import java.util.List;

/**
 * @author <a href="mailto:xzx8023@vip.qq.com">Vixb</a>
 * @since 2020/6/29
 */

public interface IAptitudeContract {
    interface IAptitudeView extends ILoadView {
        void setData(List<AptitudeBean> list);

        int getType();

        void saveSuccess();

        default String getProductID() {
            return null;
        }

        default String getExtGroupID() {
            return null;
        }
    }

    interface IAptitudePresenter extends IPresenter<IAptitudeView> {

        void save(AptitudeReq list);
    }
}
