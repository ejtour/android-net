package com.hll_sc_app.rest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.utils.FileUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public static void imageUpload(File file, SimpleObserver<String> observer) {
        upload("image/JPEG", file, observer);
    }

    private static void upload(String mediaType, File file, SimpleObserver<String> observer) {
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), file);
        MultipartBody.Part photo;
        try {
            photo = MultipartBody.Part.createFormData("upload", file.getName(), body);
        } catch (IllegalArgumentException e) {
            //因为文件名含有中文 会抛错 进行转码再重新操作
            String name = URLEncoder.encode(file.getName());
            photo = MultipartBody.Part.createFormData("upload", name, body);
        }
        HttpFactory.createImgUpload(UserService.class)
                .imageUpload(photo)
                .compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> observer.startReq())
                .doFinally(observer::reqOver)
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    public static void fileUpload(File file, SimpleObserver<String> observer) {
        upload("*/*", file, observer);
    }

    public static void pickFile(Activity activity, int requestcode, String[] mimeTypes) {
        WeakReference<Activity> mWeekActivity = new WeakReference<>(activity);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        setMimeType(intent, mimeTypes);
        mWeekActivity.get().startActivityForResult(intent, requestcode);
    }

    //设置文件类型
    private static void setMimeType(Intent intent, String[] mimeTypes) {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypess = "";
            for (String mimeType : mimeTypes) {
                mimeTypess += mimeType + "|";
            }
            intent.setType(mimeTypess.substring(0, mimeTypess.length() - 1));
        }
    }

    //获取路径
    public static String getFilePath(Activity activity, Uri uri) {
        String path = "";
        WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
        //使用第三方应用打开
        if ("file" .equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        //4.4以后
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            // 获取文件路径
            path = FileUtils.getPath(weakReference.get(), uri);

        } else {//4.4以下下系统调用方法
            path = FileUtils.getRealPathFromURI(weakReference.get(), uri);
        }
        return path;
    }

}
