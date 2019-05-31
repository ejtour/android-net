package com.hll_sc_app;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 首页
 *
 * @author zhuyingsong
 * @date 2019/5/31
 */
@Route(path = RouterConfig.ROOT_HOME)
public class MainActivity extends BaseLoadActivity {

    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTxtConfirm.setText("登录");
    }

    @OnClick(R.id.txt_confirm)
    public void onViewClicked() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("loginPhone", "13111112222")
            .put("loginPWD", "111111")
            .put("checkCode", "")
            .put("deviceId", PushServiceFactory.getCloudPushService().getDeviceId())
            .create();
        UserService.INSTANCE.login(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> showLoading())
            .doFinally(this::hideLoading)
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    mTxtConfirm.setText(o.toString());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    showError(e);
                }
            });
    }
}
