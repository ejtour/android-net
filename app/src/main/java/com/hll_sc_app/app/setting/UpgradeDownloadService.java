package com.hll_sc_app.app.setting;

import android.app.IntentService;
import android.content.Intent;

import com.hll_sc_app.bean.update.UpdateInfo;


/**
 * 更新下载服务
 *
 * @author huyongcheng
 * @date 2019/2/27
 */
public class UpgradeDownloadService extends IntentService {

    public UpgradeDownloadService() {
        super("UpgradeDownload");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        UpdateInfo info = intent.getParcelableExtra("UpdateInfo");
        UpgradeManager.INSTANCE.startTask(info);
    }

}
