package com.hll_sc_app.app.invoice.input;

import android.content.Intent;
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

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.detail.InvoiceDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.invoice.InvoiceHistoryBean;
import com.hll_sc_app.bean.invoice.InvoiceHistoryResp;
import com.hll_sc_app.bean.invoice.InvoiceMakeReq;
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
    private InvoiceMakeResp mMakeResp;

    /**
     * @param req 发票请求参数
     */
    public static void start(InvoiceMakeReq req) {
        req.setInvoiceType(1);
        RouterUtil.goToActivity(RouterConfig.INVOICE_INPUT, req);
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
    @Autowired(name = "parcelable")
    InvoiceMakeReq mMakeReq;
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
        mInvoiceAmount.setText(processMoney());
        mRecipient.setText(mMakeReq.getReceiver());
        mPhone.setText(mMakeReq.getTelephone());
    }

    private SpannableString processMoney() {
        String source = String.format("¥%s", CommonUtils.formatMoney(mMakeReq.getInvoicePrice()));
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
                        mMakeReq.setInvoiceType(Integer.valueOf(nameValue.getValue()));
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
            UserBean user = GreenDaoUtils.getUser();
            mMakeReq.setAccount(mAccount.getText().toString());
            mMakeReq.setAddress(mAddress.getText().toString());
            mMakeReq.setGroupID(user.getGroupID());
            mMakeReq.setInvoiceTitle(mInvoiceTitle.getText().toString());
            mMakeReq.setNote(mRemark.getText().toString());
            mMakeReq.setOpenBank(mBank.getText().toString());
            mMakeReq.setTaxpayerNum(mIdentifier.getText().toString());
            mMakeReq.setTelephone(mPhone.getText().toString());
            mMakeReq.setTitleType(mIdentifierGroup.getVisibility() == View.GONE ? 1 : 2);
            mMakeReq.setUserID(user.getEmployeeID());
            mPresenter.makeInvoice(mMakeReq);
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
        mMakeResp = resp;
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_good)
                .setImageState(R.drawable.ic_dialog_state_success)
                .setMessageTitle("您已成功提交开票申请")
                .setMessage("预计财务将在1-3个工作日内开票\n请耐心等候～")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    NavCallback callback = null;
                    if (item != 0) {
                        callback = new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {
                                InvoiceDetailActivity.start(InvoiceInputActivity.this, mMakeResp.getId());
                            }
                        };
                    }
                    ARouter.getInstance().build(RouterConfig.INVOICE_ENTRY)
                            .setProvider(new LoginInterceptor())
                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                            .navigation(this, callback);
                }, "返回列表", "查看记录")
                .create()
                .show();
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
