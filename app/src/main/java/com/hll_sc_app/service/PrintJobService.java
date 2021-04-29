package com.hll_sc_app.service;

import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.bean.print.PrintStatusBean;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.rest.Print;
import com.hll_sc_app.utils.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/26
 */
public class PrintJobService extends JobIntentService {

    private static final int JOB_ID = 0x574;
    private final List<Tuple<String, String>> mFallbackList = new ArrayList<>();
    private static final String ACTION_ENQUEUE = "action_enqueue";
    private Tuple<String, String> mCur;
    private final AtomicInteger mIndex = new AtomicInteger();

    public static void enqueueWork(String billNo, String subBillNo) {
        Intent intent = new Intent();
        intent.setAction(ACTION_ENQUEUE);
        intent.putExtra("billNo", billNo);
        intent.putExtra("subBillNo", subBillNo);
        enqueueWork(MyApplication.getInstance(), PrintJobService.class, JOB_ID, intent);
        showNotification(billNo, subBillNo, -1, null);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (ACTION_ENQUEUE.equals(intent.getAction())) {
            String billNo = intent.getStringExtra("billNo");
            String subBillNo = intent.getStringExtra("subBillNo");
            if (TextUtils.isEmpty(billNo)) return;
            mCur = new Tuple<>(billNo, subBillNo);
            showNotification(billNo, subBillNo, 0, null);
            mIndex.set(0);
            queryStatus(billNo);
        }
    }

    /**
     * @param status -1-等待打印 0-正在打印 1-打印成功 2-打印失败 3-打印状态获取失败
     */
    private static void showNotification(String billNo, String subBillNo, int status, String msg) {
        if (subBillNo == null){
            subBillNo = "";
        }
        String content = String.format(status == -1 ? "等待打印%s配送单" :
                status == 0 ? "正在打印%s配送单" : status == 1 ? "%s配送单打印成功" :
                        status == 2 ? "%s配送单打印失败" : "%s配送单打印状态获取失败", subBillNo);
        if (status == 2 && !TextUtils.isEmpty(msg)) {
            content = content + "：" + msg;
        }
        ToastUtils.showShort(content);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MyApplication.getInstance());
        if (!managerCompat.areNotificationsEnabled()) return;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getInstance(), "print")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("配送单打印")
                .setContentText(content)
                .setProgress(0, 0, status == 0);
        managerCompat.notify(billNo.hashCode(), builder.build());
    }

    private void queryStatus(String billNo) {
        mIndex.incrementAndGet();
        Print.queryPrintStatus(billNo, new BaseCallback<PrintStatusBean>() {
            @Override
            public void onSuccess(PrintStatusBean printStatusBean) {
                handleResult(printStatusBean.getStatus(), 60, 5, printStatusBean.getResultMsg());
            }

            private void handleResult(int status, int max, int seconds, String msg) {
                if ((status == 0 || status == 3) && mIndex.get() <= max) {
                    reReq(seconds);
                    return;
                }
                if (status != 1) {
                    mFallbackList.add(mCur);
                }
                showNotification(mCur.getKey1(), mCur.getKey2(), status, msg);
            }

            @Override
            public void onFailure(UseCaseException e) {
                handleResult(3, 30, 10, "网络异常");
            }
        });
    }

    private void reReq(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
            queryStatus(mCur.getKey1());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
