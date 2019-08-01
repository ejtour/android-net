package com.hll_sc_app.rest;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.HttpFactory;
import com.hll_sc_app.base.http.SimpleObserver;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
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
    public static void imageUpload(File file, SimpleObserver<String> observer) {
        RequestBody body = RequestBody.create(MediaType.parse("image/JPEG"), file);
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
}
