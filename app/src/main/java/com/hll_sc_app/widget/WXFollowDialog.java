package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/22
 */
public class WXFollowDialog extends BaseDialog {
    private static final String TAG = "WXFollowDialog";
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
    @BindView(R.id.dwf_qr)
    ImageView mImageView;
    @BindView(R.id.dwf_close)
    ImageView mClose;

    public WXFollowDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().width = UIUtils.dip2px(311);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_wx_follow, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.dwf_save)
    void save(View view) {
        if (!MyApplication.getInstance().getWxApi().isWXAppInstalled()) {
            ToastUtils.showShort("请安装微信后重试");
            return;
        }

        view.setVisibility(View.GONE);
        mClose.setVisibility(View.GONE);

        view.post(() -> {
            dismiss();

            String path = String.format("%s%sIMG_%s.png", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                    File.separator, SDF.format(new Date()));
            Utils.saveViewToFile(mRootView, path);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(new File(path)));
            mActivity.sendBroadcast(intent);

            ToastUtils.showShort("保存成功");

            Intent intentForPackage = mActivity.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            intentForPackage.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intentForPackage.setAction("android.intent.action.VIEW");
            intentForPackage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mActivity.startActivity(intentForPackage);
        });
    }

    @OnClick(R.id.dwf_close)
    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void show(String qrcodeUrl) {
        try {
            mImageView.setImageBitmap(Utils.generateQRCode(qrcodeUrl, UIUtils.dip2px(140), UIUtils.dip2px(140), 0));
            super.show();
        } catch (Exception e) {
            LogUtil.e(TAG, qrcodeUrl + "转二维码失败");
        }
    }
}
