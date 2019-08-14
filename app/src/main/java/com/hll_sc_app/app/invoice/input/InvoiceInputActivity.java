package com.hll_sc_app.app.invoice.input;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.invoice.InvoiceHistoryBean;
import com.hll_sc_app.bean.invoice.InvoiceHistoryResp;
import com.hll_sc_app.bean.invoice.InvoiceMakeResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.invoice.InvoiceHistoryWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */
@Route(path = RouterConfig.INVOICE_INPUT)
public class InvoiceInputActivity extends BaseLoadActivity implements RadioGroup.OnCheckedChangeListener, IInvoiceInputContract.IInvoiceInputView, BaseQuickAdapter.OnItemClickListener {

    private InvoiceHistoryResp mHistoryResp;

    /**
     * @param money 发票金额
     */
    public static void start(double money, String phone) {
        RouterUtil.goToActivity(RouterConfig.INVOICE_INPUT, money, phone);
    }

    @BindView(R.id.aii_confirm)
    TextView mConfirm;
    @BindView(R.id.aii_invoice_type)
    TextView mInvoiceType;
    @BindView(R.id.aii_invoice_amount)
    TextView mInvoiceAmount;
    @BindView(R.id.aii_radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.aii_invoice_title)
    TextView mInvoiceTitle;
    @BindView(R.id.aii_identifier)
    TextView mIdentifier;
    @BindView(R.id.aii_identifier_group)
    Group mIdentifierGroup;
    @BindView(R.id.aii_account)
    TextView mAccount;
    @BindView(R.id.aii_bank)
    TextView mBank;
    @BindView(R.id.aii_address)
    TextView mAddress;
    @BindView(R.id.aii_recipient)
    TextView mRecipient;
    @BindView(R.id.aii_phone)
    EditText mPhone;
    @BindView(R.id.aii_remark)
    EditText mRemark;
    @Autowired(name = "object0")
    double mMoney;
    @Autowired(name = "object1")
    String mPhoneIn;
    private SingleSelectionDialog mTypeDialog;
    private InvoiceHistoryWindow mHistoryWindow;
    private IInvoiceInputContract.IInvoiceInputPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_input);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        initView();
        initData();
        mInvoiceTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showHistoryWindow(v);
        });
    }

    private void initData() {
        mPresenter = InvoiceInputPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.reqInvoiceHistory(1);
    }

    private void initView() {
        mInvoiceType.setTag("1");
        mInvoiceAmount.setText(processMoney());
        UserBean user = GreenDaoUtils.getUser();
        mRecipient.setText(user.getEmployeeName());
        mPhone.setText(mPhoneIn);
    }

    private SpannableString processMoney() {
        String source = String.format("¥%s", CommonUtils.formatMoney(mMoney));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.23f), 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @OnClick(R.id.aii_invoice_type)
    public void selectType() {
        if (mTypeDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("普通发票", String.valueOf(1)));
            list.add(new NameValue("专用发票", String.valueOf(2)));
            NameValue cur = list.get(0);
            mTypeDialog = SingleSelectionDialog
                    .newBuilder(this, NameValue::getName)
                    .setTitleText("选择发票类型")
                    .refreshList(list)
                    .setOnSelectListener(nameValue -> {
                        mInvoiceType.setText(nameValue.getName());
                        mInvoiceType.setTag(nameValue.getValue());
                    })
                    .select(cur)
                    .create();
        }
        mTypeDialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.aii_type_company) {
            mIdentifierGroup.setVisibility(View.VISIBLE);
            mPresenter.reqInvoiceHistory(1);
            mInvoiceTitle.setHint("请输入企业名称");
            updateVisibility();
        } else {
            mIdentifierGroup.setVisibility(View.GONE);
            mIdentifier.setText("");
            mPresenter.reqInvoiceHistory(2);
            mInvoiceTitle.setHint("请输入抬头名称");
        }
    }

    @OnTextChanged(value = {R.id.aii_invoice_title, R.id.aii_identifier,
            R.id.aii_account, R.id.aii_bank, R.id.aii_address, R.id.aii_recipient, R.id.aii_phone}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged() {
        updateVisibility();
    }

    private void updateVisibility() {
        mConfirm.setEnabled(!TextUtils.isEmpty(mInvoiceTitle.getText())
                && !TextUtils.isEmpty(mAccount.getText())
                && !TextUtils.isEmpty(mBank.getText())
                && !TextUtils.isEmpty(mAddress.getText())
                && !TextUtils.isEmpty(mRecipient.getText())
                && !TextUtils.isEmpty(mPhone.getText())
                && (mIdentifierGroup.getVisibility() == View.GONE || !TextUtils.isEmpty(mIdentifier.getText()))
        );
    }

    @OnClick(R.id.aii_confirm)
    public void confirm() {
        if (verifyValidity()) {
            showToast("确认待添加");
        }
    }

    private boolean verifyValidity() {
        return true;
    }

    @OnClick(R.id.aii_invoice_title)
    public void showHistoryWindow(View view) {
        if (mHistoryResp == null || CommonUtils.isEmpty(mHistoryResp.getRecords())) {
            if (mHistoryWindow != null) {
                mHistoryWindow.dismiss();
            }
        } else {
            if (mHistoryWindow == null) {
                mHistoryWindow = new InvoiceHistoryWindow(this, this);
            }
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            mHistoryWindow.setList(mHistoryResp.getRecords()).showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    @Override
    public void cacheInvoiceHistoryResp(InvoiceHistoryResp resp) {
        mHistoryResp = resp;
    }

    @Override
    public void makeSuccess(InvoiceMakeResp resp) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        InvoiceHistoryBean item = (InvoiceHistoryBean) adapter.getItem(position);
        if (item == null) return;
        mHistoryWindow.dismiss();
        mInvoiceTitle.setText(item.getInvoiceTitle());
        if (mIdentifierGroup.getVisibility() == View.VISIBLE) {
            mIdentifier.setText(item.getTaxpayerNum());
        } else mIdentifier.setText("");
        mAccount.setText(item.getAccount());
        mBank.setText(item.getOpenBank());
        mAddress.setText(item.getAddress());
    }
}
