package com.hll_sc_app.app.stockmanage.purchaserorder.window;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.stockmanage.purchaserorder.ShareParams;
import com.hll_sc_app.bean.stockmanage.purchaserorder.UrlObject;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 采购单详情分享
 * @author chukun
 */
public class ShareQQWXWindow extends BaseShadowPopupWindow implements IUiListener, View.OnClickListener {
    /**
     * WX_APP_ID 替换为你的应用从官方网站申请到的合法appID
     */
    public static final String WX_APP_ID = "wx1c0b06dc21cfdb93";
    private static final String QQ_APP_ID = "101536988";
    private BaseLoadActivity mActivity;

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    private Tencent mTencent;
    private ShareParams shareParams;

    public ShareQQWXWindow(BaseLoadActivity activity, ShareParams shareParams) {
        super(activity);
        mActivity = activity;
        this.shareParams = shareParams;
        View mRootView = View.inflate(activity, R.layout.window_share_qq_wx, null);
        mRootView.setOnClickListener(this);
        mRootView.findViewById(R.id.txt_qq).setOnClickListener(this);
        mRootView.findViewById(R.id.txt_wx).setOnClickListener(this);
        mRootView.findViewById(R.id.img_close).setOnClickListener(this);
        this.setContentView(mRootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb000000));
        regToWx();
        regToQQ();
    }


    private void regToWx() {
        api = WXAPIFactory.createWXAPI(mActivity, WX_APP_ID, true);
        boolean isRegister = api.registerApp(WX_APP_ID);
        if (!isRegister) {
            mActivity.showToast("注册失败");
        }
    }

    private void regToQQ() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(QQ_APP_ID, mActivity);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, this);
    }

    @Override
    public void onComplete(Object o) {}

    @Override
    public void onError(UiError uiError) {
        mActivity.showToast(uiError.errorDetail);
    }

    @Override
    public void onCancel() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_wx:
                shareToWX(SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.txt_qq:
                shareToQQ(0);
                break;
            case R.id.img_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void shareToWX(int scene) {
        if (api.isWXAppInstalled()) {
            Glide.with(mActivity).asBitmap().load("http://res.hualala.com/" + shareParams.getImgUrl()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    WXWebpageObject webpage = new WXWebpageObject();
                    UrlObject urlObject = shareParams.getUrlData();
                    webpage.webpageUrl = "http://172.16.32.222:3001/client/sharePurchase?shareData=" + Base64.encodeToString(JSONObject.toJSONString(urlObject).getBytes(), Base64.DEFAULT);
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = shareParams.getTitle();
                    String hostName ="二十二城供应商";
                    msg.description = hostName + shareParams.getDescription();
                    //图片要压缩
                    msg.setThumbImage(createBitmapThumbnail(resource));
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = "webpage";
                    req.message = msg;
                    req.scene = scene;
                    api.sendReq(req);
                }
            });
        } else {
            mActivity.showToast("尚未安装微信，无法分享");
        }
    }

    private void shareToQQ(int scene) {
        if (mTencent != null && mTencent.isQQInstalled(mActivity)) {
            UrlObject urlObject = shareParams.getUrlData();
            Bundle bundle = new Bundle();
            //这条分享消息被好友点击后的跳转URL。
            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://172.16.32.222:3001/client/sharePurchase?shareData=" + Base64.encodeToString(JSONObject.toJSONString(urlObject).getBytes(), Base64.DEFAULT));
            //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
            bundle.putString(QQShare.SHARE_TO_QQ_TITLE,shareParams.getTitle());
            //分享的图片URL
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://res.hualala.com/" + shareParams.getImgUrl());
            //分享的消息摘要，最长50个字
            String hostName ="二十二城供应商";
            bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, hostName + shareParams.getDescription());
            if (scene == 0) {
                // Share to QQ.
                bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                mTencent.shareToQQ(mActivity, bundle, this);
            } else if (scene == 1) {
                // Share to Qzone.
                bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                mTencent.shareToQQ(mActivity, bundle, this);
            }
        } else {
            mActivity.showToast("尚未安装QQ，无法分享");
        }
    }

    /**
     * 创建缩略图 压缩图片
     *
     * @param bitmap
     * @return
     */
    private Bitmap createBitmapThumbnail(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 80;
        int newHeight = 80;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }


}

