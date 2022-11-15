package com.hll_sc_app.app.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.bean.update.UpdateInfo;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版本更新弹窗
 *
 * @author huyongcheng
 * @date 2019/2/23
 */
public class UpgradeActivity extends BaseLoadActivity {
    private static final String INTENT_INFO = "INTENT_INFO";
    @BindView(R.id.cbox_redown)
    CheckBox mCboxRedown;
    @BindView(R.id.txt_update)
    TextView mTxtUpdate;
    private UpdateInfo mInfo;

    public static void start(UpdateInfo info) {
        Context context = App.INSTANCE;
        Intent starter = new Intent(context, UpgradeActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        starter.putExtra(INTENT_INFO, info);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_upgrade);
        ButterKnife.bind(this);
        mInfo = getIntent().getParcelableExtra(INTENT_INFO);
        if (mInfo == null) {
            finish();
        }
        initView();
    }

    private void initView() {
        // 版本名称（版本号）
        setText(R.id.txt_ver, mInfo.getVersionName());
        // 更新内容
        setText(R.id.txt_info, mInfo.getVersionDesc());
        // 是否强制 0:建议 1:强制
        setVisible(R.id.img_close, mInfo.getForce() != 1);
        String appUrl = mInfo.getAppUrl();
        File file = new File(App.INSTANCE.getExternalCacheDir().getAbsolutePath(), appUrl.substring(appUrl.lastIndexOf("/")));
        mCboxRedown.setVisibility(file.exists() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpgradeManager.INSTANCE.setDownloadListener(null);
    }

    @Override
    public void onBackPressed() {
        if (mInfo.getForce() != 1) {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.img_close, R.id.txt_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                UpgradeActivity.this.finish();
                break;
            case R.id.txt_update:
                mInfo.setPassIfAlreadyCompleted(!mCboxRedown.isChecked());
                mCboxRedown.setChecked(false);
                UpgradeManager.INSTANCE.setDownloadListener(new DownloadListener());
                UpgradeManager.INSTANCE.updateTask(mInfo);
                break;
        }
    }

    private class DownloadListener extends DownloadListener1 {

        @Override
        public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
            mTxtUpdate.setText("下载中...");
        }

        @Override
        public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

        }

        @Override
        public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
            mTxtUpdate.setText(CommonUtils.formatRound((currentOffset * 1.0 / totalLength) * 100, 0) + "%");
        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause,
                            @NonNull Listener1Assist.Listener1Model model) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (cause == EndCause.ERROR) {
                    mTxtUpdate.setText("下载中断");
                } else if (cause == EndCause.CANCELED) {
                    mTxtUpdate.setText("暂停");
                } else if (cause == EndCause.COMPLETED) {
                    mTxtUpdate.setText("下载完成");
                }
            }, 100);

        }
    }
}
