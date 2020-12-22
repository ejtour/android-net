package com.hll_sc_app.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.hll_sc_app.R;
import com.hll_sc_app.citymall.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yufs on 2017/8/16.
 */

public class DownloadUtil {
    public static final int DOWNLOAD_FAIL = 0;
    public static final int DOWNLOAD_PROGRESS = 1;
    public static final int DOWNLOAD_SUCCESS = 2;
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    public static DownloadUtil getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient();
    }

    public void download(String url, @NonNull String dirName, final OnDownloadListener listener) {
        this.listener = listener;
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = DOWNLOAD_FAIL;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    //储存下载文件的目录
                    String savePath = isExistDir(dirName);
                    InputStream is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(call.request().url().toString()));
                    writeFileFromStreamToLocal(file, is, total, progress -> {
                        //下载中
                        Message message = Message.obtain();
                        message.what = DOWNLOAD_PROGRESS;
                        message.obj = progress;
                        mHandler.sendMessage(message);
                    });
                    //下载完成
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_SUCCESS;
                    message.obj = file.getAbsolutePath();
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_FAIL;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 将输入流写入本地文件中
     *
     * @param file
     * @param is
     * @param fileSize
     * @param progressListener
     */
    public static void writeFileFromStreamToLocal(File file, InputStream is, long fileSize, DownLoadProgressListener progressListener) {
        FileOutputStream fos = null;
        try {
            int len = 0;
            long sum = 0;
            byte[] buf = new byte[2048];
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / fileSize * 100);
                progressListener.getProgress(progress);
            }
            fos.flush();
        } catch (IOException e) {

        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {

            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {

            }
        }
    }

    public interface DownLoadProgressListener {
        void getProgress(int progress);
    }

    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    public static String isExistDir(@NonNull String dirName) throws IOException {
        String externalPath = Environment.getExternalStoragePublicDirectory(dirName)
                + File.separator + App.INSTANCE.getString(R.string.app_name);
        File downloadFile = new File(externalPath);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        return downloadFile.getAbsolutePath();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_PROGRESS:
                    listener.onDownloading((Integer) msg.obj);
                    break;
                case DOWNLOAD_FAIL:
                    listener.onDownloadFailed();
                    listener = null;
                    break;
                case DOWNLOAD_SUCCESS:
                    listener.onDownloadSuccess((String) msg.obj);
                    listener = null;
                    break;
            }
        }
    };


    OnDownloadListener listener;

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * 下载进度
         *
         * @param progress
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}