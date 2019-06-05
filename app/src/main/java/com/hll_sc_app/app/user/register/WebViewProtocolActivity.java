package com.hll_sc_app.app.user.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.PageParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议展示页面
 *
 * @author zhuyingsong
 * @date 2019-06-05
 */
@Route(path = RouterConfig.WEB_VIEW_PROTOCOL)
public class WebViewProtocolActivity extends BaseLoadActivity {
    @Autowired(name = "parcelable", required = true)
    PageParams mPageParams;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.webView_container)
    WebView mWebViewContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        mTxtTitle.setText(mPageParams.getTitle());
        mWebViewContainer.loadUrl(mPageParams.getProtocolUrl());
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }
}
