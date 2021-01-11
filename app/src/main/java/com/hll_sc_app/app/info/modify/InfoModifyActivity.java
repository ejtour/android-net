package com.hll_sc_app.app.info.modify;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.info.GroupEmailInputView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/5
 */

@Route(path = RouterConfig.INFO_MODIFY)
public class InfoModifyActivity extends BaseLoadActivity implements IInfoModifyContract.IInfoModifyView {
    private static final int REQ_CODE = 0x775;
    @BindView(R.id.aim_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aim_edit_text)
    EditText mEditText;
    @BindView(R.id.aim_div)
    View mDiv;
    @BindView(R.id.aim_root_view)
    LinearLayout mRootView;
    @ModifyType
    @Autowired(name = "object0")
    int mModifyType;
    @Autowired(name = "object1")
    String mContent;
    private GroupEmailInputView mEmailInputView;
    private IInfoModifyContract.IInfoModifyPresenter mPresenter;

    public static void start(Activity context, @ModifyType int modifyType, String content) {
        Object[] args = {modifyType, content};
        RouterUtil.goToActivity(RouterConfig.INFO_MODIFY, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_modify);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = InfoModifyPresenter.newInstance(mModifyType);
        mPresenter.register(this);
    }

    private void save(View view) {
        String content = getContent();
        switch (mModifyType) {
            case ModifyType.EMAIL:
                if (!Utils.checkEmail(content)) {
                    showToast("请输入正确的邮箱地址");
                    return;
                }
                break;
            case ModifyType.ID_CARD:
                if (!TextUtils.isEmpty(content) && !content.toUpperCase().matches("^[1-9](\\d{14}|(\\d{16}X)|\\d{17})$")) {
                    showToast("请输入正确的身份证号");
                    return;
                }
            case ModifyType.NAME:
                success();
                return;
            case ModifyType.GROUP_EMAIL:
                if (content == null) return;
                break;
            case ModifyType.PHONE:
                if (!TextUtils.isEmpty(content) && !CommonUtils.isPhone(content)) {
                    showToast("请输入正确的手机号");
                    return;
                }
                break;
            case ModifyType.CONTACT:
            case ModifyType.FAX:
            default:
                break;
        }
        mPresenter.save(content);
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::save);
        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO)
                save(v);
            return true;
        });
        if (!UserConfig.crm()) {
            mRootView.setBackgroundColor(Color.WHITE);
            mEditText.setBackgroundColor(Color.TRANSPARENT);
            mDiv.setVisibility(View.VISIBLE);
        }
        switch (mModifyType) {
            case ModifyType.EMAIL:
                mTitleBar.setHeaderTitle("我的邮箱");
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                mEditText.setHint("请填写您的个人电子邮箱");
                break;
            case ModifyType.CONTACT:
                mTitleBar.setHeaderTitle("联系人");
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                mEditText.setHint("请输入联系人");
                break;
            case ModifyType.PHONE:
                mTitleBar.setHeaderTitle("联系电话");
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                mEditText.setHint("请输入联系电话");
                break;
            case ModifyType.FAX:
                mTitleBar.setHeaderTitle("传真");
                mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                mEditText.setHint("请输入传真");
                break;
            case ModifyType.NAME:
                mTitleBar.setHeaderTitle("法人姓名");
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                mEditText.setHint("请输入法人姓名");
                break;
            case ModifyType.ID_CARD:
                mTitleBar.setHeaderTitle("身份证号");
                mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                mEditText.setKeyListener(DigitsKeyListener.getInstance("1234567890xX"));
                mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                mEditText.setHint("请输入身份证号");
                break;
            case ModifyType.GROUP_EMAIL:
                mTitleBar.setHeaderTitle("邮箱");
                mEditText.setVisibility(View.GONE);
                mDiv.setVisibility(View.GONE);
                mEmailInputView = new GroupEmailInputView(this);
                mRootView.addView(mEmailInputView);
                mEmailInputView.setEmail(mContent);
                break;
        }
        if (!TextUtils.isEmpty(mContent) && mEditText.getVisibility() == View.VISIBLE) {
            mEditText.setText(mContent);
            mEditText.setSelection(mContent.length());
        }
    }

    private String getContent() {
        return mEmailInputView == null ? mEditText.getText().toString().trim() : mEmailInputView.getEmail();
    }

    @Override
    public void success() {
        if (mModifyType == ModifyType.EMAIL) {
            UserBean user = GreenDaoUtils.getUser();
            user.setEmail(getContent());
            GreenDaoUtils.updateUser(user);
        }
        Intent intent = null;
        if (mModifyType == ModifyType.NAME || mModifyType == ModifyType.ID_CARD) {
            intent = new Intent();
            intent.putExtra("type", mModifyType);
            intent.putExtra("content", getContent());
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
