package com.hll_sc_app.rest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.base.http.SimpleObserver;
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

    public static String getFilePath(Activity activity, Intent data) {
        String path = "";
        WeakReference<Activity> mWeekActivity = new WeakReference<>(activity);
        Uri uri = data.getData(); // 获取用户选择文件的URI
        // 通过ContentProvider查询文件路径
        ContentResolver resolver = mWeekActivity.get().getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // 未查询到，说明为普通文件，可直接通过URI获取文件路径
            path = uri.getPath();
        }
        if (cursor.moveToFirst()) {
            // 多媒体文件，从数据库中获取文件的真实路径
            path = cursor.getString(cursor.getColumnIndex("_data"));
        }
        cursor.close();
        return path;
    }
}
