package com.hll_sc_app.rest;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.hll_sc_app.MyApplication;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.impl.IStringListener;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.Calendar;

import io.reactivex.Observable;
import top.zibin.luban.Luban;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/1
 */

public class Upload {

    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String XLS = "application/vnd.ms-excel application/x-excel";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PDF = "application/pdf";
    public static final String ZIP = "application/zip";
    public static final String RAR = "application/x-rar";
    public static final String JPG = "image/jpeg";
    public static final String PNG = "image/png";

    private static final String END_POINT = "http://oss-cn-beijing.aliyuncs.com";
    private static final String BUCKET_NAME = "hualala-op";//dohko-op
    private static final String STS_SERVER = "http://app.sts.22city.cn/22city";

    public static void upload(ILoadView loadView, String filePath, IStringListener uploadFileConfig) {
        if (filePath == null) {
            ToastUtils.showShort("路径访问错误，请重试");
            return;
        }
        Observable.just(filePath)
                .map(inPath -> {
                    boolean originFile = true;
                    if (loadView instanceof Activity) {
                        Intent intent = ((Activity) loadView).getIntent();
                        if (intent != null) {
                            originFile = Matisse.obtainOriginalState(intent);
                        }
                    }
                    File file = originFile ? new File(inPath) : Luban.with(App.INSTANCE)
                            .load(inPath)
                            .setFocusAlpha(true)
                            .ignoreBy(1024) // 文件大于1mb便压缩
                            .filter(Upload::isPicture)
                            .get().get(0);
                    LogUtil.d("XZX", "file size = " + Formatter.formatFileSize(MyApplication.getInstance(), file.length()));
                    String absolutePath = file.getAbsolutePath();
                    String objectName = "supplychain/22city/" + getFileName(inPath);
                    // 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
                    OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(STS_SERVER);
                    OSS oss = new OSSClient(App.INSTANCE, END_POINT, credentialProvider);
                    PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, objectName, absolutePath);
                    oss.putObject(put);
                    return objectName;
                })
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> {
                    loadView.showLoading();
                })
                .doFinally(loadView::hideLoading)
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(loadView.getOwner())))
                .subscribe(s -> {
                    if (uploadFileConfig != null) {
                        uploadFileConfig.callback(s);
                    }
                }, throwable -> loadView.showToast(throwable.getMessage()));
    }

    private static boolean isPicture(String filePath) {
        if (TextUtils.isEmpty(filePath)) return false;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return options.outWidth != -1 && options.outHeight != -1;
    }

    /**
     * 拼接文件路径的名称：(文件名+时间戳).文件类型
     *
     * @param path
     * @return
     */
    private static String getFileName(String path) {
        String fileName = "";
        String suffix = "";
        int len = path.lastIndexOf("/");
        if (len == -1) {
            fileName = "empty_file_name";
        } else {
            fileName = path.substring(len + 1);
        }
        int suffixLen = fileName.lastIndexOf(".");
        if (suffixLen > -1) {
            suffix = fileName.substring(suffixLen);
        }
        return fileName.substring(0, suffixLen) + "_" + Calendar.getInstance().getTimeInMillis() + suffix;
    }
}
