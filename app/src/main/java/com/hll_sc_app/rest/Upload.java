package com.hll_sc_app.rest;


import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import io.reactivex.Observable;

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

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String bucketName = "hualala-op";//dohko-op
    private static String stsServer = "http://app.sts.22city.cn/22city";

    private static WeakReference<BaseLoadActivity> activityWeakReference;

    public static void upload(String filePath, SimpleObserver<String> observer) {
        Observable.just(filePath)
                .map(s -> {
                    String path = "supplychain/22city/" + getFileName(s);
                    OSS oss = new OSSClient(UIUtils.getContext(), endpoint, new OSSAuthCredentialsProvider(stsServer));
                    try {
                        oss.putObject(new PutObjectRequest(bucketName, path, s));
                    } catch (ClientException e) {
                        e.printStackTrace();
                        throw new UseCaseException(UseCaseException.Level.FAIL, e.getMessage());
                    } catch (ServiceException e) {
                        e.printStackTrace();
                        throw new UseCaseException(UseCaseException.Level.NET, e.getErrorCode() + ":" + e.getMessage());
                    }
                    return path;
                }).compose(ApiScheduler.getObservableScheduler())
                .doOnSubscribe(disposable -> observer.startReq())
                .doFinally(observer::reqOver)
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    public static void upload(BaseLoadActivity activity, String filePath, UploadFileConfig uploadFileConfig) {
        activityWeakReference = new WeakReference<>(activity);
        String objectName = "supplychain/22city/" + getFileName(filePath);
        PutObjectRequest put = new PutObjectRequest(bucketName, objectName, filePath);

        // 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);
        activityWeakReference.get().showLoading();
        OSS oss = new OSSClient(activityWeakReference.get(), endpoint, credentialProvider);
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (!isActivity()) {
                    return;
                }
                activityWeakReference.get().runOnUiThread(() -> {
                    activityWeakReference.get().hideLoading();
                    uploadFileConfig.success(objectName);
                    activityWeakReference.clear();
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (!isActivity()) {
                    return;
                }
                activityWeakReference.get().runOnUiThread(() -> {
                    activityWeakReference.get().hideLoading();
                    if (clientExcepion != null) {
                        uploadFileConfig.clientError(clientExcepion);
                    }
                    if (serviceException != null) {
                        uploadFileConfig.serverError(serviceException);
                    }
                    activityWeakReference.clear();
                });
            }
        });
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

    /**
     * activity
     *
     * @return
     */
    private static boolean isActivity() {
        return activityWeakReference != null && activityWeakReference.get() != null && activityWeakReference.get().isActive();
    }


    public interface UploadFileConfig {
        void success(String filepath);

        /**
         * 本地异常，如网络异常等。
         *
         * @param e
         */
        default void clientError(ClientException e) {
            if (activityWeakReference == null || activityWeakReference.get() == null) {
                e.printStackTrace();
            } else {
                BaseLoadActivity activity = activityWeakReference.get();
                activity.showToast(e.getMessage());
            }
        }

        /**
         * 服务异常
         *
         * @param e
         */
        default void serverError(ServiceException e) {
            if (activityWeakReference == null || activityWeakReference.get() == null) {
                e.printStackTrace();
            } else {
                BaseLoadActivity activity = activityWeakReference.get();
                activity.showToast(e.getMessage());
            }
        }
    }
}
