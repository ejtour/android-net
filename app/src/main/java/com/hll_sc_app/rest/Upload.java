package com.hll_sc_app.rest;


import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.hll_sc_app.base.BaseLoadActivity;

import java.lang.ref.WeakReference;
import java.util.Calendar;

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

    public static String upload(BaseLoadActivity activity, String filePath, UploadFileConfig uploadFileConfig) {
        activityWeakReference = new WeakReference<>(activity);
        try {

            String objectName = "supplychain/22city/" + getFileName(filePath);
            PutObjectRequest put = new PutObjectRequest(bucketName, objectName, filePath);

            // 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
            OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);
            activityWeakReference.get().showLoading();
            OSS oss = new OSSClient(activityWeakReference.get(), endpoint, credentialProvider);
            OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    if (!isActivity()) {
                        return;
                    }
                    activityWeakReference.get().hideLoading();
                    activityWeakReference.get().runOnUiThread(() -> {
                        uploadFileConfig.success(objectName);
                    });
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    if (!isActivity()) {
                        return;
                    }
                    activityWeakReference.get().hideLoading();
                    activityWeakReference.get().runOnUiThread(() -> {
                        if (clientExcepion != null) {
                            uploadFileConfig.clientError(clientExcepion);
                        }
                        if (serviceException != null) {
                            uploadFileConfig.serverError(serviceException);
                        }
                    });

                }
            });
        } catch (Exception e) {

        } finally {

        }
        return null;
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
        return activityWeakReference != null && activityWeakReference.get().isActive();
    }


    public interface UploadFileConfig {
        void success(String filepath);

        /**
         * 本地异常，如网络异常等。
         *
         * @param e
         */
        default void clientError(ClientException e) {
            if (activityWeakReference == null) {
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
            if (activityWeakReference == null) {
                e.printStackTrace();
            } else {
                BaseLoadActivity activity = activityWeakReference.get();
                activity.showToast(e.getMessage());
            }
        }
    }
}

//    public static void imageUpload(File file, SimpleObserver<String> observer) {
//        upload("image/JPEG", file, observer);
//    }
//
//    private static void upload(String mediaType, File file, SimpleObserver<String> observer) {
//        RequestBody body = RequestBody.create(MediaType.parse(mediaType), file);
//        MultipartBody.Part photo;
//        try {
//            photo = MultipartBody.Part.createFormData("upload", file.getName(), body);
//        } catch (IllegalArgumentException e) {
//            //因为文件名含有中文 会抛错 进行转码再重新操作
//            String name = URLEncoder.encode(file.getName());
//            photo = MultipartBody.Part.createFormData("upload", name, body);
//        }
//        HttpFactory.createImgUpload(UserService.class)
//                .imageUpload(photo)
//                .compose(ApiScheduler.getObservableScheduler())
//                .doOnSubscribe(disposable -> observer.startReq())
//                .doFinally(observer::reqOver)
//                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
//                .subscribe(observer);
//
//    }
//
//    private static void upload(BaseLoadActivity activity, String filePath, HttpUploadFile.UploadFileConfig uploadFileConfig){
//        HttpUploadFile.formUploadAliOss(activity,filePath,uploadFileConfig);
//    }
//
//    public static void fileUpload(File file, SimpleObserver<String> observer) {
//        upload("*/*", file, observer);
//    }
//
//
//
//    //设置文件类型
//    private static void setMimeType(Intent intent, String[] mimeTypes) {
////        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
//            if (mimeTypes.length > 0) {
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//            }
//        } else {
//            String mimeTypess = "";
//            for (String mimeType : mimeTypes) {
//                mimeTypess += mimeType + "|";
//            }
//            intent.setType(mimeTypess.substring(0, mimeTypess.length() - 1));
//        }
//    }
//
//    /**
//     * 后续要作废
//     * @param activity
//     * @param requestcode
//     * @param mimeTypes
//     */
//    public static void pickFile(Activity activity, int requestcode, String[] mimeTypes) {
//        WeakReference<Activity> mWeekActivity = new WeakReference<>(activity);
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        setMimeType(intent, mimeTypes);
//        mWeekActivity.get().startActivityForResult(intent, requestcode);
//    }
//
//    /**
//     * 后续作废
//     * @param activity
//     * @param uri
//     * @return
//     */
//    public static String getFilePath(Activity activity, Uri uri) {
//        String path = "";
//        WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
//        //使用第三方应用打开
//        if ("file" .equalsIgnoreCase(uri.getScheme())) {
//            path = uri.getPath();
//        }
//        //4.4以后
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            // 获取文件路径
//            path = FileUtils.getPath(weakReference.get(), uri);
//
//        } else {//4.4以下下系统调用方法
//            path = FileUtils.getRealPathFromURI(weakReference.get(), uri);
//        }
//        return path;
//    }

