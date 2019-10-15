package com.hll_sc_app.app.complainmanage.sendcomplainreply;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.ComplainManageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 投诉详情-处理投诉
 */
@Route(path = RouterConfig.ACTIVITY_COMPLAIN_SEND_REPLY)
public class SendComplainReplyActivity extends BaseLoadActivity implements ISendComplainReplyContract.IView {
    @Autowired(name = "object0")
    String mCompaintId;
    @BindView(R.id.txt_left_number)
    TextView mLeftNumber;
    @BindView(R.id.btn_reply)
    TextView mBtnReply;
    @BindView(R.id.edt_content)
    EditText mEdtReply;

    private Unbinder unbinder;
    private ISendComplainReplyContract.IPresent mPresent;


    public static void start(String compaintId) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_SEND_REPLY, compaintId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_complain_send_reply);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresent = SendComplainReplyPresent.newInstance();
        mPresent.register(this);
    }

    private void initView() {
        mEdtReply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnReply.setEnabled(s.toString().length() > 0);
                mLeftNumber.setText(String.valueOf(200 - mEdtReply.getText().toString().length()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public String getComplaintID() {
        return mCompaintId;
    }

    @Override
    public String getReply() {
        return mEdtReply.getText().toString();
    }


    @Override
    public void sendSuccess() {
        EventBus.getDefault().post(new ComplainManageEvent(ComplainManageEvent.TARGET.LIST, ComplainManageEvent.EVENT.REFRESH));
        EventBus.getDefault().post(new ComplainManageEvent(ComplainManageEvent.TARGET.DETAIL, ComplainManageEvent.EVENT.REFRESH));
        finish();
        ComplainReplyResultActivity.start(mCompaintId);
    }

    @OnClick({R.id.btn_reply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reply:
                mPresent.sendComplainReply();
                break;
            default:
                break;
        }
    }
}
