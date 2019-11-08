package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/7
 */

public class ShareDialog extends BaseDialog {

    @BindView(R.id.ds_title)
    TextView mTitle;
    @BindView(R.id.ds_wx)
    TextView mWx;
    @BindView(R.id.ds_timeline)
    TextView mTimeline;
    @BindView(R.id.ds_qq)
    TextView mQq;
    @BindView(R.id.ds_qzone)
    TextView mQzone;
    private ShareParam mParam;
    private IUiListener mListener;
    private Tencent mQqApi;

    public ShareDialog(@NonNull Activity context) {
        super(context);
        mQqApi = Tencent.createInstance(Constants.QQ_APP_ID, context);
    }

    public IUiListener getListener() {
        if (mListener == null) {
            mListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                }

                @Override
                public void onError(UiError uiError) {
                }

                @Override
                public void onCancel() {
                }
            };
        }
        return mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = UIUtils.dip2px(180);
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.white);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_QQ_SHARE)
            Tencent.onActivityResultData(requestCode, resultCode, data, getListener());
    }

    public void setData(ShareParam param) {
        mParam = param;
        mTitle.setText(mParam.dialogTitle);
        mWx.setVisibility(mParam.shareWX ? View.VISIBLE : View.GONE);
        mTimeline.setVisibility(mParam.shareTimeLine ? View.VISIBLE : View.GONE);
        mQq.setVisibility(mParam.shareQQ ? View.VISIBLE : View.GONE);
        mQzone.setVisibility(mParam.shareQzone ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.ds_close)
    @Override
    public void dismiss() {
        super.dismiss();
        if (!TextUtils.isEmpty(mParam.imagePath)) {
            new File(mParam.imagePath).delete();
        }
    }

    public void release() {
        mQqApi.releaseResource();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_share, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.ds_wx, R.id.ds_timeline, R.id.ds_qq, R.id.ds_qzone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ds_wx:
                shareToWx(SendMessageToWX.Req.WXSceneSession);
                break;
            case R.id.ds_timeline:
                shareToWx(SendMessageToWX.Req.WXSceneTimeline);
                break;
            case R.id.ds_qq:
                shareToQQ(QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                break;
            case R.id.ds_qzone:
                shareToQQ(QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                break;
        }
    }

    private void shareToWx(int scene) {
        IWXAPI api = MyApplication.getInstance().getWxApi();
        if (!api.isWXAppInstalled()) {
            ToastUtils.showShort(mActivity, "尚未安装微信，无法分享");
            return;
        }
        if (mParam.shareType == ShareParam.IMAGE) {
            Bitmap bmp = BitmapFactory.decodeFile(mParam.imagePath);
            WXImageObject imgObj = new WXImageObject(bmp);
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = imgObj;
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
            bmp.recycle();
            msg.setThumbImage(Utils.createBitmapThumbnail(thumbBmp));
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            req.scene = scene;
            api.sendReq(req);
        } else if (mParam.shareType == ShareParam.WEB) {
            Glide.with(mActivity).asBitmap().load(mParam.imgUrl).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = mParam.url;
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = mParam.title;
                    msg.description = mParam.description;
                    //图片要压缩
                    msg.setThumbImage(Utils.createBitmapThumbnail(resource));
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("webpage");
                    req.message = msg;
                    req.scene = scene;
                    MyApplication.getInstance().getWxApi().sendReq(req);
                }
            });
        }
    }

    private void shareToQQ(int scene) {
        if (!mQqApi.isQQInstalled(mActivity)) {
            ToastUtils.showShort(mActivity, "尚未安装QQ，无法分享");
            return;
        }
        Bundle bundle = new Bundle();
        if (mParam.shareType == ShareParam.IMAGE) {
            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mParam.imagePath);
        } else if (mParam.shareType == ShareParam.WEB) {
            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mParam.url);
            bundle.putString(QQShare.SHARE_TO_QQ_TITLE, mParam.title);
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mParam.imgUrl);
            bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, mParam.description);
        }
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "二十二城供应商");
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, scene);
        mQqApi.shareToQQ(mActivity, bundle, getListener());
    }

    private String buildTransaction(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static class ShareParam {
        public static final int WEB = 1;
        public static final int IMAGE = 2;
        private String dialogTitle;
        private boolean shareQQ = true;
        private boolean shareWX = true;
        private boolean shareTimeLine = true;
        private boolean shareQzone = true;
        private int shareType = WEB;
        private String imgUrl;
        private String title;
        private String description;
        private String url;
        private String imagePath;

        public static ShareParam createImageShareParam(String dialogTitle, String imagePath) {
            ShareParam param = new ShareParam();
            param.shareType = IMAGE;
            param.dialogTitle = dialogTitle;
            param.imagePath = imagePath;
            return param;
        }

        public static ShareParam createWebShareParam(String dialogTitle, String imgUrl, String title, String description, String url) {
            ShareParam param = new ShareParam();
            param.shareType = WEB;
            param.dialogTitle = dialogTitle;
            param.imgUrl = imgUrl;
            param.title = title;
            param.description = description;
            param.url = url;
            return param;
        }

        public void setDialogTitle(String dialogTitle) {
            this.dialogTitle = dialogTitle;
        }

        public ShareParam setShareQQ(boolean shareQQ) {
            this.shareQQ = shareQQ;
            return this;
        }

        public ShareParam setShareWX(boolean shareWX) {
            this.shareWX = shareWX;
            return this;
        }

        public ShareParam setShareTimeLine(boolean shareTimeLine) {
            this.shareTimeLine = shareTimeLine;
            return this;
        }

        public ShareParam setShareQzone(boolean shareQzone) {
            this.shareQzone = shareQzone;
            return this;
        }

        public int getShareType() {
            return shareType;
        }

        public void setShareType(int shareType) {
            this.shareType = shareType;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }
}
