package com.hll_sc_app.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hll_sc_app.MyApplication;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.bean.event.WXEvent;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/6
 */

public abstract class BaseWXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {
        MyApplication.getInstance().getWxApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        // no-op
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof SendAuth.Resp) { // 登录
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) baseResp).code;
                    EventBus.getDefault().post(new WXEvent(code));
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    ToastUtils.showShort("取消登录");
                    break;
                default:
                    ToastUtils.showShort("登录失败");
                    break;
            }
        }
        finish();
    }
}
