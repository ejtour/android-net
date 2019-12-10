package com.hll_sc_app.app.feedbackcomplain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*反馈投诉选项页面*/
@Route(path = RouterConfig.ACTIVITY_FEED_BACK_COMPLAIN)
public class FeedbackComplainActivity extends AppCompatActivity {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_feedback_complain);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_feedback, R.id.txt_complain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_feedback:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_FEED_BACK_LIST);
                break;
            case R.id.txt_complain:
                RouterUtil.goToActivity(RouterConfig.ACTIVITY_PLATFORM_COMPLAIN_LIST);
                break;
            default:
                break;
        }
    }
}
