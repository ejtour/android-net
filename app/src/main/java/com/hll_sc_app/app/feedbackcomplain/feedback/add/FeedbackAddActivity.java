package com.hll_sc_app.app.feedbackcomplain.feedback.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.complain.FeedbackDetailResp;
import com.hll_sc_app.widget.IUploadImageMethod;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 意见反馈新增
 */
@Route(path = RouterConfig.ACTIVITY_FEED_BACK_ADD)
public class FeedbackAddActivity extends BaseLoadActivity implements IFeedbackAddContract.IView, IUploadImageMethod {
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNumber;
    @BindView(R.id.upload_img)
    ImgUploadBlock mUpload;
    @BindView(R.id.txt_submit)
    TextView mTxtSubmit;
    @BindView(R.id.ll_scroll_photo)
    LinearLayout mLlScrollPhoto;
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
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
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
        mEdtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTxtSubmit.setEnabled(mEdtContent.getText().toString().length() > 0);
            }
        });
        mTxtSubmit.setOnClickListener(v -> {
            mPresent.addFeedback(mEdtContent.getText().toString(), getImageUrls());
        });

    }

    private boolean isEditModal() {
        return mDetail != null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImgUploadBlock.REQUEST_CODE_CHOOSE:
                if (resultCode == RESULT_OK) {
                    uploadImg(data, this);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public LinearLayout getImageContainer() {
        return mLlScrollPhoto;
    }

    @Override
    public Activity getImageActivity() {
        return this;
    }

    @Override
    public ImgUploadBlock getUploadBlock() {
        return mUpload;
    }

    @Override
    public int getMaxImageNum() {
        return 5;
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
}
