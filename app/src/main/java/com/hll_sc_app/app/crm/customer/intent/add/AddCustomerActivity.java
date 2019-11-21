package com.hll_sc_app.app.crm.customer.intent.add;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.CustomerHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.AreaSelectWindow;
import com.hll_sc_app.bean.customer.CustomerAreaBean;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

@Route(path = RouterConfig.CRM_CUSTOMER_INTENT_ADD)
public class AddCustomerActivity extends BaseLoadActivity implements IAddCustomerContract.IAddCustomerView {
    public static final int REQ_CODE = 0x337;

    @BindView(R.id.cia_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.cia_save)
    TextView mSave;
    @BindView(R.id.cia_type)
    TextView mType;
    @BindView(R.id.cia_name)
    EditText mName;
    @BindView(R.id.cia_num)
    EditText mNum;
    @BindView(R.id.cia_region)
    TextView mRegion;
    @BindView(R.id.cia_address)
    EditText mAddress;
    @BindView(R.id.cia_contact)
    EditText mContact;
    @BindView(R.id.cia_phone)
    EditText mPhone;
    @BindView(R.id.cia_level)
    TextView mLevel;
    @BindView(R.id.cia_state)
    TextView mState;
    @BindView(R.id.cia_source)
    TextView mSource;
    @BindView(R.id.cia_manager)
    TextView mManager;
    @Autowired(name = "parcelable")
    CustomerBean mBean;
    private SingleSelectionDialog mTypeDialog;
    private SingleSelectionDialog mLevelDialog;
    private SingleSelectionDialog mStateDialog;
    private SingleSelectionDialog mSourceDialog;
    private AreaSelectWindow mAreaWindow;
    private AddCustomerPresenter mPresenter;

    public static void start(Activity activity, CustomerBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_INTENT_ADD, activity, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_customer_intent_add);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        if (mBean == null) {
            UserBean user = GreenDaoUtils.getUser();
            mBean = new CustomerBean();
            mBean.setGroupID(user.getGroupID());
            mBean.setEmployeeID(user.getEmployeeID());
            mManager.setText(user.getEmployeeName());
            mBean.setActionType(1);
        } else {
            mBean.setActionType(2);
            mBean.preProcess();
            mType.setText(CustomerHelper.getCustomerType(mBean.getCustomerType()));
            mName.setText(mBean.getCustomerName());
            mNum.setText(String.valueOf(mBean.getShopCount()));
            updateNumState();
            mRegion.setText(String.format("%s-%s-%s", mBean.getCustomerProvince(), mBean.getCustomerCity(), mBean.getCustomerDistrict()));
            mAddress.setText(mBean.getCustomerAddress());
            mContact.setText(mBean.getCustomerLinkman());
            mPhone.setText(mBean.getCustomerPhone());
            mLevel.setText(CustomerHelper.getCustomerLevel(mBean.getCustomerLevel()));
            mState.setText(CustomerHelper.getCustomerState(mBean.getCustomerStatus()));
            mSource.setText(CustomerHelper.getCustomerSource(mBean.getCustomerSource()));
            mManager.setText(mBean.getCustomerAdmin());
        }
        mPresenter = AddCustomerPresenter.newInstance();
        mPresenter.register(this);
    }

    private void updateNumState() {
        mNum.setEnabled(mBean.getCustomerType() != 1);
        mNum.setTextColor(ContextCompat.getColor(this, mNum.isEnabled() ? R.color.color_222222 : R.color.color_999999));
    }

    @OnClick(R.id.cia_save)
    public void save() {
        String phone = mPhone.getText().toString();
        if (!Utils.checkPhone(phone)) {
            showToast("请输入正确的联系电话");
            return;
        }
        mBean.setCustomerName(mName.getText().toString().trim());
        mBean.setShopCount(Integer.parseInt(mNum.getText().toString()));
        mBean.setCustomerAddress(mAddress.getText().toString().trim());
        mBean.setCustomerLinkman(mContact.getText().toString().trim());
        mBean.setCustomerPhone(phone);
        mBean.setCustomerAdmin(mManager.getText().toString().trim());
        mPresenter.save(mBean);
    }

    @OnTextChanged({R.id.cia_type, R.id.cia_name, R.id.cia_num, R.id.cia_region, R.id.cia_address,
            R.id.cia_contact, R.id.cia_phone, R.id.cia_level, R.id.cia_state, R.id.cia_source, R.id.cia_manager})
    public void updateEnable() {
        mSave.setEnabled(!TextUtils.isEmpty(mType.getText().toString().trim())
                && !TextUtils.isEmpty(mName.getText().toString().trim())
                && !TextUtils.isEmpty(mNum.getText().toString().trim())
                && !TextUtils.isEmpty(mRegion.getText().toString().trim())
                && !TextUtils.isEmpty(mAddress.getText().toString().trim())
                && !TextUtils.isEmpty(mContact.getText().toString().trim())
                && !TextUtils.isEmpty(mPhone.getText().toString().trim())
                && !TextUtils.isEmpty(mLevel.getText().toString().trim())
                && !TextUtils.isEmpty(mState.getText().toString().trim())
                && !TextUtils.isEmpty(mSource.getText().toString().trim())
                && !TextUtils.isEmpty(mManager.getText().toString().trim())
        );
    }

    @OnClick(R.id.cia_type)
    public void selectType() {
        if (mTypeDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                nameValues.add(new NameValue(CustomerHelper.getCustomerType(i + 1), String.valueOf(i + 1)));
            }
            mTypeDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择客户类型")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mBean.setCustomerType(Integer.parseInt(value.getValue()));
                        if (mBean.getCustomerType() == 1) {
                            mNum.setText("1");
                        }
                        updateNumState();
                        mType.setText(value.getName());
                    })
                    .create();
        }
        mTypeDialog.show();
    }

    @OnClick(R.id.cia_region)
    public void selectRegion() {
        UIUtils.hideActivitySoftKeyboard(this);
        if (mAreaWindow == null) {
            mAreaWindow = new AreaSelectWindow(this);
            mAreaWindow.setResultSelectListener(t -> {
                CustomerAreaBean area = mBean.getCustomerArea();
                if (area == null) {
                    area = new CustomerAreaBean();
                    mBean.setCustomerArea(area);
                }
                area.setCustomerCity(t.getShopCity());
                area.setCustomerCityCode(t.getShopCityCode());
                area.setCustomerDistrict(t.getShopDistrict());
                area.setCustomerDistrictCode(t.getShopDistrictCode());
                area.setCustomerProvince(t.getShopProvince());
                area.setCustomerProvinceCode(t.getShopProvinceCode());
                mRegion.setText(String.format("%s-%s-%s", t.getShopProvince(), t.getShopCity(), t.getShopDistrict()));
            });
        }
        mAreaWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @OnClick(R.id.cia_level)
    public void selectLevel() {
        if (mLevelDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (String s : "A,B,C".split(",")) {
                nameValues.add(new NameValue(CustomerHelper.getCustomerLevel(s), s));
            }
            mLevelDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择客户级别")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mBean.setCustomerLevel(value.getValue());
                        mLevel.setText(value.getName());
                    })
                    .create();
        }
        mLevelDialog.show();
    }

    @OnClick(R.id.cia_state)
    public void selectState() {
        if (mStateDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                nameValues.add(new NameValue(CustomerHelper.getCustomerState(i + 1), String.valueOf(i + 1)));
            }
            mStateDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择客户状态")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mBean.setCustomerStatus(Integer.parseInt(value.getValue()));
                        mState.setText(value.getName());
                    })
                    .create();
        }
        mStateDialog.show();
    }

    @OnClick(R.id.cia_source)
    public void selectSource() {
        if (mSourceDialog == null) {
            List<NameValue> nameValues = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                nameValues.add(new NameValue(CustomerHelper.getCustomerSource(i + 1), String.valueOf(i + 1)));
            }
            mSourceDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("选择客户来源")
                    .refreshList(nameValues)
                    .setOnSelectListener(value -> {
                        mBean.setCustomerSource(Integer.parseInt(value.getValue()));
                        mSource.setText(value.getName());
                    })
                    .create();
        }
        mSourceDialog.show();
    }

    @Override
    public void saveSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
