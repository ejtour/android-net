package com.hll_sc_app.app.setting;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.bean.update.UpdateInfo;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.citymall.util.SystemUtils;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新管理器
 *
 * @author huyongcheng
 * @date 2019/2/28
 */
public class UpgradeManager {

    public static final UpgradeManager INSTANCE = new UpgradeManager();

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mNotifyBuilder;
    private DownloadListener1 mDownloadListener;

    private Map<String, DownloadTask> mTaskMap;

    private UpgradeManager() {
        initNotify();
        mTaskMap = new HashMap<>();
    }

    /**
     * 初始化通知
     */
    private void initNotify() {
        mNotifyManager = (NotificationManager) App.INSTANCE.getSystemService(Context.NOTIFICATION_SERVICE);

        Bitmap appIcon = BitmapFactory.decodeResource(App.INSTANCE.getResources(), R.mipmap.ic_launcher);

        final String channelId = "okdownload";

        // 适配 Android O
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, SystemUtils.getAppName(App.INSTANCE),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(null, null);
            mNotifyManager.createNotificationChannel(notificationChannel);
        }

        mNotifyBuilder = new NotificationCompat.Builder(App.INSTANCE, channelId)
                .setOngoing(true)   // 是否进行中通知
                .setPriority(NotificationCompat.PRIORITY_MAX) // 设置优先级
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(appIcon)
                .setAutoCancel(false) // 是否能取消
                .setSound(null)
                .setContentText("开始下载");

    }

    public void startTask(UpdateInfo info) {

        LogUtil.d("Upgrade", "startTask：" + info.getAppUrl());

        String appUrl = info.getAppUrl();
        String appName = appUrl.substring(appUrl.lastIndexOf("/"));

        DownloadTask downloadTask = new DownloadTask.Builder(appUrl, App.INSTANCE.getExternalCacheDir().getAbsolutePath(),
                appName)
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(30)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(info.isPassIfAlreadyCompleted())
                .setConnectionCount(1)
                .build();

        if (info.isPassIfAlreadyCompleted() && StatusUtil.isCompleted(downloadTask)) {
            SystemUtils.installApp(downloadTask.getFile());
            return;
        }

        mTaskMap.put(appUrl, downloadTask);

        setContentIntent(info);

        /**
         * 监听下载进度，显示通知
         */
        downloadTask.execute(new DownloadListener1() {
            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
                if (mDownloadListener != null) {
                    mDownloadListener.taskStart(task, model);
                }
                mNotifyBuilder.setContentTitle("新版本 " + info.getVersionName());
                mNotifyBuilder.setContentText("下载中...");
                mNotifyManager.notify(task.getId(), mNotifyBuilder.build());
            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
                if (mDownloadListener != null) {
                    mDownloadListener.retry(task, cause);
                }
            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
                if (mDownloadListener != null) {
                    mDownloadListener.connected(task, blockCount, currentOffset, totalLength);
                }
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                if (mDownloadListener != null) {
                    mDownloadListener.progress(task, currentOffset, totalLength);
                }
                mNotifyBuilder.setContentText("下载进度 " + CommonUtils.formatRound((currentOffset * 1.0 / totalLength) * 100, 0) + "%");
                mNotifyManager.notify(task.getId(), mNotifyBuilder.build());
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause,
                                @NonNull Listener1Assist.Listener1Model model) {
                if (mDownloadListener != null) {
                    mDownloadListener.taskEnd(task, cause, realCause, model);
                }

                mTaskMap.remove(info.getAppUrl());

                if (cause == EndCause.CANCELED) {
                    mNotifyBuilder.setAutoCancel(true);
                    mNotifyBuilder.setOngoing(false);
                    mNotifyBuilder.setContentText("暂停");

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        mNotifyManager.notify(task.getId(), mNotifyBuilder.build());
                    }, 100);
                } else if (cause == EndCause.COMPLETED) {
                    mNotifyBuilder.setOngoing(false);
                    mNotifyBuilder.setAutoCancel(true);
                    mNotifyBuilder.setContentText("下载完成");

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        mNotifyManager.notify(task.getId(), mNotifyBuilder.build());
                        SystemUtils.installApp(task.getFile().getPath());
                    }, 100);
                }
            }
        });
    }

    private void setContentIntent(UpdateInfo info) {
        Intent intent = new Intent();
        intent.setClass(App.INSTANCE, UpgradeReceiver.class);
        intent.putExtra("UpdateInfo", info);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(App.INSTANCE, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotifyBuilder.setContentIntent(pendingIntent);
    }

    /**
     * 更新动作
     *
     * @param info
     */
    public void updateTask(UpdateInfo info) {
        DownloadTask downloadTask = mTaskMap.get(info.getAppUrl());
        if (downloadTask != null) {
            if (StatusUtil.isCompleted(downloadTask)) {
                SystemUtils.installApp(downloadTask.getFile());
            } else {
                downloadTask.cancel();
                mTaskMap.remove(info.getAppUrl());
            }
        } else {
            startDownloadService(info);
        }
    }

    public void startDownloadService(UpdateInfo info) {
        Intent intent = new Intent(App.INSTANCE, UpgradeDownloadService.class);
        intent.putExtra("UpdateInfo", info);
        App.INSTANCE.startService(intent);
    }

    public void setDownloadListener(DownloadListener1 downloadListener) {
        this.mDownloadListener = downloadListener;
    }

}
