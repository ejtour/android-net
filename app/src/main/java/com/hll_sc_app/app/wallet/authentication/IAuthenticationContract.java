package com.hll_sc_app.app.wallet.authentication;


import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.wallet.AreaInfo;
import com.hll_sc_app.bean.wallet.AreaListReq;
import com.hll_sc_app.bean.wallet.BankBean;
import com.hll_sc_app.bean.wallet.OcrImageResp;
import com.hll_sc_app.bean.wallet.WalletInfo;

import java.io.File;
import java.util.List;

/**
 * 实名认证 主页面
 *
 * @author zc
 */
public interface IAuthenticationContract {

    //基本信息
    interface IBaseInfoFragment extends IFragment{
        /**
         * 获取地址成功
         */
        void getAddressSuccess(List<AreaInfo> areaBeans);

        /**
         * 获取地址失败
         */
        void getAddressFail();

    }

    interface ISettleInfoFragment extends IFragment{
        /**
         * 结算信息更改开户行
         */
        void updateBank(BankBean bankBean);
    }

    //需要上传图片的
    interface IUploadFragment extends IFragment{
        /**
         * 传入上传图片的url
         */
        void setUploadUrl(String url);

    }



    interface IFragment extends ILoadView {
        /**
         * 注册页面 传入Activity对象
         *
         * @param view
         */
        void registerView(IView view);



    }

    interface IView extends ILoadView {
        /**
         * 获取实名认证数据成功
         */
        void getWalletInfoSuccess(WalletInfo walletInfo);

        /**
         * 获取实名认证数据
         *
         * @return
         */
        WalletInfo getWalletInfo();

        /**
         * 实名认证成功
         */
        void setWalletInfoSuccess();

        /**
         * 设置底部按钮可用的状态
         */
        void setSubmitButtonEnable(boolean enable);

        /**
         * 上传图片成功
         */
        void uploadImgSuccess(String url);

        void goToNextStep();

        void ocrImage(int lpCardType, String imageUrl);

        void orcImageSuccess(OcrImageResp resp);
    }

    interface IOcrFragment extends IFragment{
        void showOcrInfo(OcrImageResp resp);
    }

    interface IPresent extends IPresenter<ILoadView> {
        /**
         * 获取所在地区
         */
        void getAddress(AreaListReq areaListReq);

        /**
         * 得到实名认证数据
         */
        void getWalletInfo();

        /**
         * 实名认证请求
         */
        void setWalletInfo();

        /**
         * 上传图片
         *
         * @param file
         */
        void imageUpload(File file);

        void ocrImage(int lpCardType, String imageUrl);


    }


}
