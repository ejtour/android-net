package com.hll_sc_app.app.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hll_sc_app.bean.update.UpdateInfo;
import com.hll_sc_app.citymall.util.LogUtil;

/**
 * 更新广播
 *
 * @author huyongcheng
 * @date 2019/2/28
 */
public class UpgradeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        UpdateInfo info = intent.getParcelableExtra("UpdateInfo");
        info.setPassIfAlreadyCompleted(true);
        UpgradeManager.INSTANCE.updateTask(info);
        LogUtil.d("Upgrade", "收到通知点击事件：" + info);
    }
}
