package com.hll_sc_app.app.message.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

@Route(path = RouterConfig.MESSAGE_NOTICE)
public class MessageNoticeActivity extends BaseActivity {

    @BindView(R.id.amn_title)
    TextView mTitle;
    @BindView(R.id.amn_author)
    TextView mAuthor;
    @BindView(R.id.amn_time)
    TextView mTime;
    @BindView(R.id.amn_message)
    TextView mMessage;
    @BindView(R.id.amn_image)
    GlideImageView mImage;
    @Autowired(name = "object0")
    String mTextTitle;
    @Autowired(name = "object1")
    String mTextTime;
    @Autowired(name = "object2")
    String mTextMessage;
    @Autowired(name = "object3")
    String mTextUrl;

    /**
     * @param title   标题
     * @param time    时间
     * @param message 内容
     * @param url     图片
     */
    public static void start(String title, String time, String message, String url) {
        RouterUtil.goToActivity(RouterConfig.MESSAGE_NOTICE, title, time, message, url);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_message_notice);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        mAuthor.setText(String.format("%s官方", BuildConfig.ODM_NAME));
        mTitle.setText(mTextTitle);
        mTime.setText(mTextTime);
        mMessage.setText(Html.fromHtml(mTextMessage));
        if (!TextUtils.isEmpty(mTextUrl)) {
            mImage.setVisibility(View.VISIBLE);
            mImage.setScaleByWidth(true);
            mImage.setImageURL(mTextUrl);
        }
    }
}
