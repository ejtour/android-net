package com.hll_sc_app.app.wallet.authentication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.wallet.WalletInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hll_sc_app.app.wallet.authentication.CommonMethod.setUploadImg;


/**
 * 经营信息
 *
 * @author zc
 */
public class OperateInfoFragment extends BaseLazyFragment implements IAuthenticationContract.IFragment, IAuthenticationContract.IUploadFragment {


    @BindView(R.id.upload_img_head)
    ImgUploadBlock mUploadHead;
    @BindView(R.id.upload_img_inner)
    ImgUploadBlock mUploadInner;
    @BindView(R.id.upload_img_cash_desk)
    ImgUploadBlock mUploadCashDesk;

    private Unbinder unbinder;
    private IAuthenticationContract.IView mView;
    private ImgUploadBlock mCurrentImgShowDelBlock;

    @Override
    public void setUploadUrl(String url) {
        if (mCurrentImgShowDelBlock == mUploadHead) {
            mView.getWalletInfo().setImgBusiDoor(url);
        } else if (mCurrentImgShowDelBlock == mUploadInner) {
            mView.getWalletInfo().setImgBusiEnv(url);
        } else if (mCurrentImgShowDelBlock == mUploadCashDesk) {
            mView.getWalletInfo().setImgBusiCounter(url);
        }
        mCurrentImgShowDelBlock.showImage(url);
        isSubmit();
    }

    /**
     * 设置上传组件的公共配置
     *
     * @param title
     */
    private void initUpload(ImgUploadBlock imgUploadBlock, String title) {
        setUploadImg(imgUploadBlock, title, v -> {
            imgUploadBlock.showImage("");
            WalletInfo walletInfo = mView.getWalletInfo();
            if (imgUploadBlock == mUploadHead) {
                walletInfo.setImgBusiDoor("");
            } else if (imgUploadBlock == mUploadInner) {
                walletInfo.setImgBusiEnv("");
            } else if (imgUploadBlock == mUploadCashDesk) {
                walletInfo.setImgBusiCounter("");
            }
            isSubmit();
        }, uploadImgBlock -> {
            mCurrentImgShowDelBlock = imgUploadBlock;
            return true;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initUpload(mUploadHead,"点击上传商户门头图");
        initUpload(mUploadInner,"点击上传店内照");
        initUpload(mUploadCashDesk,"点击上传收银台照");
    }


    @Override
    public void registerView(IAuthenticationContract.IView view) {
        mView = view;
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        isSubmit();
    }

    private void isSubmit() {
        mView.setSubmitButtonEnable(isInputComplete());
    }

    /**
     * 判断是否填充完整
     *
     * @return
     */
    private boolean isInputComplete() {
        WalletInfo walletInfo = mView.getWalletInfo();
        return
                !TextUtils.isEmpty(walletInfo.getImgBusiDoor())
                && !TextUtils.isEmpty(walletInfo.getImgBusiEnv())
                && !TextUtils.isEmpty(walletInfo.getImgBusiCounter());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_operate_info, null);
    }

    @Override
    protected void initData() {
        //初始化显示页面
        WalletInfo walletInfo = mView.getWalletInfo();
        mUploadHead.showImage(walletInfo.getImgBusiDoor());
        mUploadInner.showImage(walletInfo.getImgBusiEnv());
        mUploadCashDesk.showImage(walletInfo.getImgBusiCounter());
    }

}
