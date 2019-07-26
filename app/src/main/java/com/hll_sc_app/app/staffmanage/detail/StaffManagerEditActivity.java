package com.hll_sc_app.app.staffmanage.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 员工列表-编辑员工
 *
 * @author zhuyingsong
 * @date 2018/7/25
 */
@Route(path = RouterConfig.STAFF_EDIT, extras = Constant.LOGIN_EXTRA)
public class StaffManagerEditActivity extends BaseLoadActivity implements StaffManagerEditContract.IStaffManageEditView {
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_save)
    TextView mTxtSave;
    @BindView(R.id.txt_employeeCode)
    EditText mTxtEmployeeCode;
    @BindView(R.id.txt_employeeName)
    EditText mTxtEmployeeName;
    @BindView(R.id.txt_loginPhone)
    EditText mTxtLoginPhone;
    @BindView(R.id.txt_loginPWD)
    EditText mTxtLoginPassWord;
    @BindView(R.id.txt_email)
    EditText mTxtEmail;
    @BindView(R.id.txt_roles)
    TextView mTxtRoles;
    @Autowired(name = "parcelable")
    EmployeeBean mEmployeeBean;
    private StaffManagerEditPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manager_edit);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        mPresenter = StaffManagerEditPresenter.newInstance();
        mPresenter.register(this);
        showView(mEmployeeBean);
        mTxtTitle.setText(isAdd() ? "新增员工" : "编辑员工");
    }

    public void showView(EmployeeBean bean) {
        if (bean == null) {
            return;
        }
        mTxtEmployeeCode.setText(bean.getEmployeeCode());
        mTxtEmployeeName.setText(bean.getEmployeeName());
        mTxtLoginPhone.setText(bean.getLoginPhone());
        mTxtLoginPhone.setEnabled(false);
        mTxtLoginPassWord.setText("***********");
        mTxtLoginPassWord.setEnabled(false);
        mTxtLoginPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mTxtEmail.setText(bean.getEmail());
        List<EmployeeBean.RolesBean> rolesBeans = bean.getRoles();
        if (!CommonUtils.isEmpty(rolesBeans)) {
            StringBuilder roleId = new StringBuilder();
            for (EmployeeBean.RolesBean rolesBean : rolesBeans) {
                roleId.append(rolesBean.getRoleID()).append(",");
            }
            if (!TextUtils.isEmpty(roleId)) {
                roleId.delete(roleId.length() - 1, roleId.length());
            }
            mTxtRoles.setTag(roleId.toString());
            if (rolesBeans.size() == 1) {
                mTxtRoles.setText(rolesBeans.get(0).getRoleName());
            } else {
                mTxtRoles.setText(String.format(Locale.getDefault(), "已选择%d个岗位", rolesBeans.size()));
            }
        }
    }

    private boolean isAdd() {
        return mEmployeeBean == null;
    }

    @Override
    public void editSuccess() {
        if (isAdd()) {
            showToast("新增员工信息成功");
        } else {
            showToast("更新员工信息成功");
        }
        ARouter.getInstance().build(RouterConfig.STAFF_LIST)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    @OnClick({R.id.img_back,
        R.id.txt_save,
        R.id.ll_roles})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            case R.id.ll_roles:
                // 测试
                RouterUtil.goToActivity(RouterConfig.STAFF_PERMISSION);
                break;
            default:
                break;
        }
    }

    private void toSave() {
        if (TextUtils.isEmpty(mTxtEmployeeCode.getText().toString().trim())) {
            showToast("请填写员工编号,字母+数字组合必须3位");
            return;
        }
        if (!checkEmployeeCode(mTxtEmployeeCode.getText().toString().trim())) {
            showToast("员工编号字母+数字组合必须3位");
            return;
        }
        if (TextUtils.isEmpty(mTxtEmployeeName.getText().toString().trim())) {
            showToast("员工姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(mTxtLoginPhone.getText().toString().trim())) {
            showToast("手机号码不能为空");
            return;
        }
        if (!CommonUtils.isPhone(mTxtLoginPhone.getText().toString().trim())) {
            showToast("手机号码格式不正确");
            return;
        }
        if (isAdd() && TextUtils.isEmpty(mTxtLoginPassWord.getText().toString().trim())) {
            showToast("登录密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(mTxtEmail.getText().toString().trim())) {
            showToast("邮箱不能为空");
            return;
        }
        if (!Utils.checkEmail(mTxtEmail.getText().toString().trim())) {
            showToast("邮箱格式不正确");
            return;
        }
        if (isAdd()) {
            EmployeeBean bean = new EmployeeBean();
            bean.setEmail(mTxtEmail.getText().toString().trim());
            bean.setEmployeeCode(mTxtEmployeeCode.getText().toString().trim());
            bean.setEmployeeName(mTxtEmployeeName.getText().toString().trim());
            bean.setGroupID(UserConfig.getGroupID());
            bean.setLoginPWD(mTxtLoginPassWord.getText().toString().trim());
            bean.setLoginPhone(mTxtLoginPhone.getText().toString().trim());
            if (mTxtRoles.getTag() != null) {
                bean.setRoleID((String) mTxtRoles.getTag());
            }
            mPresenter.addStaff(bean);
        } else {
            mEmployeeBean.setEmail(mTxtEmail.getText().toString().trim());
            mEmployeeBean.setEmployeeName(mTxtEmployeeName.getText().toString().trim());
            mEmployeeBean.setEmployeeCode(mTxtEmployeeCode.getText().toString().trim());
            if (mTxtRoles.getTag() != null) {
                mEmployeeBean.setRoleID((String) mTxtRoles.getTag());
            }
            mPresenter.editStaff(mEmployeeBean);
        }
    }

    /**
     * 员工编号是否符合规范
     *
     * @param code 员工编号
     * @return true-通过
     */
    private boolean checkEmployeeCode(String code) {
        boolean flag;
        try {
            String regExp = "^[0-9a-zA-Z]{3}$";
            Pattern regex = Pattern.compile(regExp);
            Matcher matcher = regex.matcher(code);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
