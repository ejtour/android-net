package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.LogUtil;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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
        getWindow().setBackgroundDrawableResource(R.drawable.bg_white_radius_5_solid);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(80);
        setCancelable(false);
    }

    private void generateQrCode(String content) {
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(content, BarcodeFormat.QR_CODE, UIUtils.dip2px(200), UIUtils.dip2px(200));
            mQrCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            LogUtil.e(TAG, content + "转二维码失败");
        }
    }
    @OnClick(R.id.dqr_close)
    public void close(){
        dismiss();
    }
}
