package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideApp;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/1
 */

public class QRCodeDialog extends Dialog {
    private static final String TAG = "QRCodeDialog";
    private final View mView;
    @BindView(R.id.dqr_amount)
    TextView mAmount;
    @BindView(R.id.dqr_qr_code)
    ImageView mQrCode;
    @BindView(R.id.dqr_tip)
    TextView mTip;
    @BindView(R.id.dqr_success_anim)
    ImageView mSuccessAnim;
    @BindView(R.id.dqr_success_group)
    Group mSuccessGroup;
    @BindView(R.id.dqr_qr_group)
    Group mQrGroup;

    public static QRCodeDialog create(Activity context, double amount, String qrContent, String payPlatform) {
        QRCodeDialog dialog = new QRCodeDialog(context);
        dialog.mAmount.setText(String.format("¥ %s", amount));
        dialog.generateQrCode(qrContent);
        dialog.mTip.setText(String.format("请使用%s扫码并支付", payPlatform));
        return dialog;
    }

    private QRCodeDialog(@NonNull Activity context) {
        super(context);
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_qr_code, null);
        ButterKnife.bind(this, mView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        initWindow();
    }

    private void initWindow() {
        if (getWindow() == null) {
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        getWindow().getAttributes().width = UIUtils.dip2px(300);
        setCancelable(false);
    }

    private void generateQrCode(String content) {
        try {
            mQrCode.setImageBitmap(Utils.generateQRCode(content, UIUtils.dip2px(200), UIUtils.dip2px(200), 0));
        } catch (Exception e) {
            LogUtil.e(TAG, content + "转二维码失败");
        }
    }

    @OnClick({R.id.dqr_close, R.id.dqr_finish})
    public void close() {
        dismiss();
    }

    public void showPaySuccess() {
        mSuccessGroup.setVisibility(View.VISIBLE);
        mQrGroup.setVisibility(View.GONE);
        GlideApp.with(getContext())
                .load(R.drawable.settle_success_anim)
                .apply(new RequestOptions()
                        .override(UIUtils.dip2px(90), UIUtils.dip2px(90))
                        .priority(Priority.HIGH)
                        .fitCenter())
                .into(new DrawableImageViewTarget(mSuccessAnim) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).setLoopCount(1);
                        }
                        super.onResourceReady(resource, transition);
                    }
                });
    }
}
