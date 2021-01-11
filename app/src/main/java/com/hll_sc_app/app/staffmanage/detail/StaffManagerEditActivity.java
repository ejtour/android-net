package com.hll_sc_app.app.staffmanage.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.staffmanage.detail.depart.DepartListActivity;
import com.hll_sc_app.app.staffmanage.linkshop.StaffLinkShopListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.RefreshStaffShopEvent;
import com.hll_sc_app.bean.event.StaffDepartListEvent;
import com.hll_sc_app.bean.event.StaffEvent;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.bean.staff.RoleBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.BindViews;
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
    @BindView(R.id.txt_depart)
    TextView mTxtDepart;
    @Autowired(name = "parcelable")
    EmployeeBean mEmployeeBean;
    @BindView(R.id.ll_shop)
    LinearLayout mLlShop;
    @BindView(R.id.txt_shop)
    TextView mTxtShop;
    @BindViews({R.id.txt_employeeNameTitle, R.id.txt_loginPhoneTitle,
            R.id.txt_loginPWDTitle, R.id.txt_rolesTitle})
    List<TextView> mRequestLabels;

    private StaffManagerEditPresenter mPresenter;

    public static void start(Context context, EmployeeBean bean) {
        if (!UserConfig.crm()) {
            if (!RightConfig.checkRight(context.getString(bean == null ? R.string.right_staffManagement_create :
                    R.string.right_staffManagement_query))) {
                ToastUtils.showShort(context.getString(R.string.right_tips));
                return;
            }
        }
        RouterUtil.goToActivity(RouterConfig.STAFF_EDIT, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manager_edit);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        mPresenter = StaffManagerEditPresenter.newInstance();
        mPresenter.register(this);
        mTxtTitle.setText(isAdd() ? "新增员工" : "编辑员工");
        ButterKnife.apply(mRequestLabels, (view, index) -> {
            SpannableString text = new SpannableString("*");
            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(view.getContext(), R.color.color_ed5655)),
                    0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.append(text);
        });
        showView(mEmployeeBean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private boolean isAdd() {
        return mEmployeeBean == null;
    }

    @Subscribe(sticky = true)
    public void onEvent(StaffDepartListEvent event) {
        mTxtDepart.setTag(TextUtils.join(",", event.getDepartIds()));
        mTxtDepart.setText("已选 " + event.getDepartIds().size() + " 个部门");
    }

    @Subscribe(sticky = true)
    public void onEvent(StaffEvent event) {
        if (event.getEmployeeBean() != null) {
            mEmployeeBean = event.getEmployeeBean();
        } else if (event.getLinkShopNum() != -1) {
            mEmployeeBean.setShopNum(event.getLinkShopNum());
        }
        showView(mEmployeeBean);
    }

    @Subscribe
    public void onEvent(RefreshStaffShopEvent event) {
        if (!TextUtils.isEmpty(event.getEmployeeID())) {
            mEmployeeBean.setShopNum(event.getShopNum());
            updateShopNum();
        }
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
        List<RoleBean> rolesBeans = bean.getRoles();
        if (!CommonUtils.isEmpty(rolesBeans)) {
            StringBuilder roleId = new StringBuilder();
            for (RoleBean rolesBean : rolesBeans) {
                roleId.append(rolesBean.getRoleID()).append(",");
            }
            if (!TextUtils.isEmpty(roleId)) {
                roleId.delete(roleId.length() - 1, roleId.length());
            }
            mTxtRoles.setTag(roleId.toString());
            if (rolesBeans.size() == 1) {
                mTxtRoles.setText(rolesBeans.get(0).getRoleName());
            } else {
                mTxtRoles.setText(String.format(Locale.getDefault(), "已选择 %d 个岗位", rolesBeans.size()));
            }
        }
        if (UserConfig.crm() || (bean.getAuthTypeList() != null && Arrays.asList(bean.getAuthTypeList()).contains("1"))) { // 岗位包含业务型
            mLlShop.setVisibility(View.VISIBLE);
            updateShopNum();
        } else {
            mLlShop.setVisibility(View.GONE);
        }
        mTxtDepart.setTag(bean.getDeptIDs());
        mTxtDepart.setText("已选 " + (!TextUtils.isEmpty(bean.getDeptIDs()) ? bean.getDeptIDs().split(",").length : 0) + " 个部门");
    }

    private void updateShopNum() {
        mTxtShop.setText(String.format("已关联 %s 个门店", mEmployeeBean.getShopNum()));
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
            R.id.ll_roles,
            R.id.ll_depart, R.id.ll_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            case R.id.ll_roles:
                RouterUtil.goToActivity(RouterConfig.STAFF_ROLE_SELECT, mTxtRoles.getTag());
                break;
            case R.id.ll_depart:
                DepartListActivity.start(getTagString(mTxtDepart));
                break;
            case R.id.ll_shop:
                StaffLinkShopListActivity.start(mEmployeeBean.getEmployeeID());
                break;
            default:
                break;
        }
    }

    private void toSave() {
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
        if (mTxtRoles.getTag() == null) {
            showToast("员工职位不能为空");
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
            bean.setDeptIDs(getTagString(mTxtDepart));
            if (mTxtRoles.getTag() != null) {
                bean.setRoleID((String) mTxtRoles.getTag());
            }
            mPresenter.addStaff(bean);
        } else {
            mEmployeeBean.setEmail(mTxtEmail.getText().toString().trim());
            mEmployeeBean.setEmployeeCode(mTxtEmployeeCode.getText().toString().trim());
            mEmployeeBean.setEmployeeName(mTxtEmployeeName.getText().toString().trim());
            mEmployeeBean.setDeptIDs(getTagString(mTxtDepart));
            if (mTxtRoles.getTag() != null) {
                mEmployeeBean.setRoleID((String) mTxtRoles.getTag());
            }
            mPresenter.editStaff(mEmployeeBean);
        }
    }

    private String getTagString(TextView target) {
        Object o = target.getTag();
        if (o == null) {
            return "";
        } else {
            return o.toString().trim();
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

    @Subscribe
    public void onEvent(List<RoleBean> list) {
        if (CommonUtils.isEmpty(list)) {
            mTxtRoles.setTag(null);
            mTxtRoles.setText(null);
        } else {
            if (list.size() == 1) {
                mTxtRoles.setText(list.get(0).getRoleName());
            } else {
                mTxtRoles.setText(String.format(Locale.getDefault(), "已选择%d个岗位", list.size()));
            }
            StringBuilder roleId = new StringBuilder();
            for (RoleBean rolesBean : list) {
                roleId.append(rolesBean.getId()).append(",");
            }
            if (!TextUtils.isEmpty(roleId)) {
                roleId.delete(roleId.length() - 1, roleId.length());
            }
            mTxtRoles.setTag(roleId.toString());
        }
    }
}
