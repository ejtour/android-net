package com.hll_sc_app.app.complainmanage.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;

import java.io.File;
import java.util.List;

public interface IComplainMangeAddContract {
    interface IView extends ILoadView {
        void queryMenuSuccess(List<DropMenuBean> dropMenuBeans);

        void showImage(String url);

        String getExplain();

        String getImgs();

        String getType();

        String getReason();

        String getBillID();

        String getPhone();

        String getPurchaserID();

        String getPurchaserName();

        String getShopID();

        String getShopName();

        List<OrderDetailBean> getProducts();

        void saveSuccess();


        boolean isEditModal();

        String getComplainID();
    }

    interface IPresent extends IPresenter<IView> {
        void queryDropMenus();

        void imageUpload(File file);

        void saveComplain();
    }


}
