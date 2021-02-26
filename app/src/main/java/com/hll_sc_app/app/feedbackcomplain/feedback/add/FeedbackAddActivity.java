package com.hll_sc_app.app.feedbackcomplain.feedback.add;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.complain.FeedbackDetailResp;
import com.hll_sc_app.widget.ImageUploadGroup;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * 意见反馈新增
 */
@Route(path = RouterConfig.ACTIVITY_FEED_BACK_ADD)
public class FeedbackAddActivity extends BaseLoadActivity implements IFeedbackAddContract.IView {
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNumber;
    @BindView(R.id.afa_upload_group)
    ImageUploadGroup mUploadGroup;
    @BindView(R.id.txt_submit)
    TextView mTxtSubmit;
    @Autowired(name = "parcelable")
    FeedbackDetailResp mDetail;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    private Unbinder unbinder;
    private IFeedbackAddContract.IPresent mPresent;

    public static void start(FeedbackDetailResp detailResp) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_FEED_BACK_ADD, detailResp);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_add);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mPresent = FeedbackAddPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        if (isEditModal()) {//编辑模式
            mTitleBar.setHeaderTitle("继续意见反馈");
        }
        mTxtSubmit.setOnClickListener(v -> {
            mPresent.addFeedback(mEdtContent.getText().toString(), mUploadGroup.getUploadImgUrls());
        });

    }

    private boolean isEditModal() {
        return mDetail != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUploadGroup.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void addSuccess() {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_FEED_BACK_LIST)
                .setProvider(new LoginInterceptor())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .navigation(this);
    }

    @Override
    public String getParentID() {
        return isEditModal() ? mDetail.getDetails().get(mDetail.getDetails().size() - 1).getSubFeedbackId() : "";
    }

    @Override
    public String getFeedbackID() {
        return isEditModal() ? mDetail.getFeedbackID() : "";
    }

    @OnTextChanged(R.id.edt_content)
    void onTextChanged(CharSequence s) {
        mTxtLeftNumber.setText(String.valueOf(300 - s.length()));
        mTxtSubmit.setEnabled(s.length() > 0);
    }
}
