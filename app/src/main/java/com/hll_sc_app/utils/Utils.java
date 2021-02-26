package com.hll_sc_app.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.DrawableRes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.widget.ExportDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class Utils {
    private static final NumberFormat PERCENT;
    private static final SimpleDateFormat SDF;

    static {
        PERCENT = NumberFormat.getPercentInstance();
        PERCENT.setMaximumFractionDigits(2);
        PERCENT.setMinimumFractionDigits(2);
        SDF = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
    }

    public static String numToPercent(double num) {
        return PERCENT.format(num);
    }

    /**
     * 邮箱是否符合规范
     *
     * @param email 邮箱
     * @return true-通过
     */
    public static boolean checkEmail(String email) {
        boolean flag;
        try {
            String regExp = "^[A-Za-z0-9\\u4e00-\\u9fa5_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
            Pattern regex = Pattern.compile(regExp);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 将输入的数字格式化成8位整数，2位小数
     *
     * @param symbol 是否显示金钱符号，如果显示，需要控制输入框可输入金钱符号 "¥"，可通过指定 digits 属性来控制
     */
    public static void processMoney(Editable s, boolean symbol) {
        if (s.length() == 0) {
            if (symbol) s.append("¥");
            return;
        }
        String process = s.toString();
        if (symbol) {
            if (!process.startsWith("¥"))
                process = s.insert(0, "¥").toString().substring(1);
            else process = s.toString().substring(1);
            if (process.contains("¥"))
                process = s.delete(1, s.length())
                        .append(process.replaceAll("¥", ""))
                        .toString().substring(1);
        }
        if (process.startsWith(".")) {
            if (symbol) process = s.insert(1, "0").toString().substring(1);
            else process = s.insert(0, "0").toString();
        }
        if (!CommonUtils.checkMoneyNum(process) && process.length() > 1)
            s.delete(s.length() - 1, s.length());
    }

    private static void export(Activity context, String title, @DrawableRes int state, String tip, String action, ExportDialog.OnClickListener listener) {
        ExportDialog.newBuilder(context)
                .setCancelable(false)
                .setTitle(title)
                .setState(state)
                .setTip(tip)
                .setButton(action, listener)
                .create().show();
    }

    public static void exportSuccess(Activity context, String email) {
        export(context, "导出成功", R.drawable.ic_dialog_state_success, "已发送至邮箱\n" + email.replaceAll(";", "\n"), "我知道了", null);
    }

    public static void exportFailure(Activity context, String tip) {
        export(context, "导出失败", R.drawable.ic_dialog_state_failure, tip, "稍后再试", null);
    }

    public static void bindEmail(Activity context, ExportDialog.OnClickListener listener) {
        export(context, "您还没绑定邮箱", R.drawable.ic_dialog_state_failure, null, "绑定并导出", listener);
    }

    public static SimpleObserver<ExportResp> getExportObserver(IExportView export) {
        return new SimpleObserver<ExportResp>(export) {
            @Override
            public void onSuccess(ExportResp resp) {
                ILoadView view = getView();
                if (!(view instanceof IExportView)) return;
                if (!TextUtils.isEmpty(resp.getEmail()))
                    ((IExportView) view).exportSuccess(resp.getEmail());
                else ((IExportView) view).exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }

            @Override
            public void onFailure(UseCaseException e) {
                ILoadView view = getView();
                if (!(view instanceof IExportView)) return;
                if ("00120112037".equals(e.getCode())) ((IExportView) view).bindEmail();
                else
                    ((IExportView) view).exportFailure(TextUtils.isEmpty(e.getMsg()) ? "噢，服务器暂时开了小差\n攻城狮正在全力抢修" : e.getMsg());
            }
        };
    }

    public static Bitmap createBitmapThumbnail(Bitmap bitmap) {
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

    public static void saveViewToFile(View view, String path) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File file = new File(path);
        file.mkdirs();
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Uri saveViewToGallery(View view) {
        ContentValues values = new ContentValues();
        String displayName = "IMG_" + SDF.format(new Date()) + ".png";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()
                + File.separator + displayName;
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            values.put(MediaStore.MediaColumns.IS_PENDING, 0);
        } else {
            values.put(MediaStore.MediaColumns.DATA, path);
        }
        Uri uri = view.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        OutputStream out = null;
        try {
            out = view.getContext().getContentResolver().openOutputStream(uri);
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.fromFile(new File(path));
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     * @param margin  白边宽度
     */
    public static Bitmap generateQRCode(String content, int width, int height, int margin) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, margin);
        BitMatrix matrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = Color.BLACK;
                } else {
                    pixels[y * width + x] = Color.WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
