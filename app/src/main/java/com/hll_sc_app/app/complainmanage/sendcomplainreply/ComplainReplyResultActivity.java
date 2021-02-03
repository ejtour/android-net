package com.hll_sc_app.app.complainmanage.sendcomplainreply;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.innerlog.InnerLoglActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 投诉详情-处理投诉结果页
 */
@Route(path = RouterConfig.ACTIVITY_COMPLAIN_SEND_RESULT)
public class ComplainReplyResultActivity extends BaseLoadActivity {
    @Autowired(name = "object0")
    String mCompaintId;

    @BindView(R.id.ll_container)
    LinearLayout mContainer;

    private Unbinder unbinder;

    public static void start(String compaintId) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_SEND_RESULT, compaintId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_reply_result);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        SpannableString title = new SpannableString("您已回复客户的投诉");
        title.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.base_colorPrimary)),
                0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        EmptyView emptyView = EmptyView.newBuilder(this)
                .setImage(R.drawable.ic_dialog_good)
                .setTipsTitle(title)
                .setTips("若您与客户无法达成友好的协商\n您可以申请由平台介入噢")
                .create();

        mContainer.addView(emptyView,0);
    }

    @OnClick({R.id.btn_list, R.id.btn_log})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_list:
                finish();
                break;
            case R.id.btn_log:
                finish();
                InnerLoglActivity.start(mCompaintId);
                break;
            default:
                break;
        }
    }
}
