package com.hll_sc_app.app.invoice.input;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.detail.InvoiceDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.InvoiceEvent;
import com.hll_sc_app.bean.invoice.InvoiceHistoryBean;
import com.hll_sc_app.bean.invoice.InvoiceMakeReq;
import com.hll_sc_app.bean.invoice.InvoiceMakeResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.KeyboardWatcher;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */
@Route(path = RouterConfig.INVOICE_INPUT)
public class InvoiceInputActivity extends BaseLoadActivity implements RadioGroup.OnCheckedChangeListener, IInvoiceInputContract.IInvoiceInputView, BaseQuickAdapter.OnItemClickListener, KeyboardWatcher.SoftKeyboardStateListener {

    /**
     * @param req 发票请求参数
     */
    public static void start(InvoiceMakeReq req) {
        RouterUtil.goToActivity(RouterConfig.INVOICE_INPUT, req);
    }

    @BindView(R.id.aii_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aii_confirm)
    TextView mConfirm;
    @BindView(R.id.aii_invoice_type)
    TextView mInvoiceType;
    @BindView(R.id.aii_invoice_amount)
    TextView mInvoiceAmount;
    @BindView(R.id.aii_radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.aii_type_company)
    RadioButton mCompany;
    @BindView(R.id.aii_type_personal)
    RadioButton mPersonal;
    @BindView(R.id.aii_invoice_title)
    EditText mInvoiceTitle;
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
    EditText mRecipient;
    @BindView(R.id.aii_phone)
    EditText mPhone;
    @BindView(R.id.aii_remark)
    EditText mRemark;
    @BindView(R.id.aii_list_view)
    RecyclerView mListView;
    @BindView(R.id.aii_scroll_view)
    ScrollView mScrollView;
    @Autowired(name = "parcelable")
    InvoiceMakeReq mMakeReq;
    private SingleSelectionDialog mTypeDialog;
    private IInvoiceInputContract.IInvoiceInputPresenter mPresenter;
    private ObservableEmitter<String> mEmitter;
    private InvoiceHistoryAdapter mAdapter;
    //    private InvoiceHistoryResp mHistoryResp;
    private InvoiceMakeResp mMakeResp;
    private boolean mNeedAssociate = true;
    private KeyboardWatcher mKeyboardWatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_input);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        initView();
        initData();
        /*mInvoiceTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showHistoryWindow(v);
        });*/
    }

    private void initData() {
        mPresenter = InvoiceInputPresenter.newInstance();
        mPresenter.register(this);
        textObservable();
        inflateData();
    }

    @Override
    protected void onDestroy() {
        mKeyboardWatcher.removeSoftKeyboardStateListener(this);
        super.onDestroy();
    }

    private void initView() {
        mKeyboardWatcher = new KeyboardWatcher(this);
        mKeyboardWatcher.addSoftKeyboardStateListener(this);
        mInvoiceAmount.setText(CommonUtils.formatNumber(mMakeReq.getInvoicePrice()));
        mRecipient.setText(mMakeReq.getReceiver());
        mPhone.setText(mMakeReq.getTelephone());
        mAdapter = new InvoiceHistoryAdapter();
        mAdapter.bindToRecyclerView(mListView);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mAdapter.setOnItemClickListener(this);
    }

    private void inflateData() {
        mInvoiceType.setText(mMakeReq.getInvoiceTypeLabel());
        if (mMakeReq.getTitleType() == 1) mCompany.setChecked(true);
        else mPersonal.setChecked(true);
        setInvoiceTile(mMakeReq.getInvoiceTitle());
        mIdentifier.setText(mMakeReq.getTaxpayerNum());
        mAccount.setText(mMakeReq.getAccount());
        mBank.setText(mMakeReq.getOpenBank());
        mAddress.setText(mMakeReq.getAddress());
        mRemark.setText(mMakeReq.getNote());
    }

    private void textObservable() {
        Observable.<String>create(emitter -> mEmitter = emitter)
                // .debounce(500, TimeUnit.MILLISECONDS) // 本地匹配暂时不去抖
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(this))).subscribe(mPresenter::search);
    }


    @OnFocusChange({R.id.aii_identifier, R.id.aii_invoice_amount, R.id.aii_remark,
            R.id.aii_account, R.id.aii_bank, R.id.aii_address, R.id.aii_recipient, R.id.aii_phone})
    public void focusChanged(boolean hasFocus) {
        mListView.setVisibility(View.GONE);
    }

    @OnTextChanged(R.id.aii_invoice_title)
    public void onTextChanged(CharSequence s) {
        if (mEmitter != null && mNeedAssociate)
            mEmitter.onNext(s.toString().trim());
    }

    @OnClick(R.id.aii_invoice_type)
    public void selectType() {
        if (mTypeDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("纸质普通发票", String.valueOf(1)));
            list.add(new NameValue("纸质专用发票", String.valueOf(2)));
            list.add(new NameValue("电子普通发票", String.valueOf(3)));
            NameValue cur = list.get(mMakeReq.getInvoiceType() - 1);
            mTypeDialog = SingleSelectionDialog
                    .newBuilder(this, NameValue::getName)
                    .setTitleText("选择发票类型")
                    .refreshList(list)
                    .setOnSelectListener(nameValue -> {
                        mMakeReq.setInvoiceType(Integer.parseInt(nameValue.getValue()));
                        mInvoiceType.setText(nameValue.getName());
                    })
                    .select(cur)
                    .create();
        }
        mTypeDialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.aii_type_company) {
            mMakeReq.setTitleType(1);
            mInvoiceTitle.setHint("请输入企业名称");
            mIdentifierGroup.setVisibility(View.VISIBLE);
        } else if (checkedId == R.id.aii_type_personal) {
            mMakeReq.setTitleType(2);
            mInvoiceTitle.setHint("请输入抬头名称");
            mIdentifierGroup.setVisibility(View.GONE);
        } else return;
        mPresenter.reqInvoiceHistory(mMakeReq.getTitleType());
        updateEnable();
    }

    @OnTextChanged(value = {R.id.aii_invoice_type, R.id.aii_invoice_title, R.id.aii_identifier,
            R.id.aii_account, R.id.aii_bank, R.id.aii_address, R.id.aii_recipient, R.id.aii_phone}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged() {
        updateEnable();
    }

    @OnTextChanged(value = R.id.aii_invoice_amount, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChanged(Editable s) {
        Utils.processMoney(s, true);
        mMakeReq.setInvoicePrice(CommonUtils.getDouble(s.toString()));
        updateEnable();
    }

    private void updateEnable() {
        mConfirm.setEnabled(!TextUtils.isEmpty(mInvoiceTitle.getText())
                && !TextUtils.isEmpty(mRecipient.getText())
                && !TextUtils.isEmpty(mPhone.getText())
                && mMakeReq.getInvoicePrice() > 0
                && (mIdentifierGroup.getVisibility() == View.GONE || !TextUtils.isEmpty(mIdentifier.getText()))
                && (mMakeReq.getInvoiceType() != 2 || (!TextUtils.isEmpty(mAccount.getText())
                && !TextUtils.isEmpty(mBank.getText())
                && !TextUtils.isEmpty(mAddress.getText())))
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
            mMakeReq.setTaxpayerNum(mIdentifierGroup.getVisibility() == View.GONE ? "" : mIdentifier.getText().toString());
            mMakeReq.setTelephone(mPhone.getText().toString());
            mMakeReq.setUserID(user.getEmployeeID());
            mPresenter.makeInvoice(mMakeReq);
        }
    }

    private boolean verifyValidity() {
        if (mIdentifierGroup.getVisibility() == View.VISIBLE && !mIdentifier.getText().toString().matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]+$")) {
            showToast("纳税人识别号请勿包含特殊字符");
            return false;
        }
        if (!CommonUtils.isPhone(mPhone.getText().toString())) {
            showToast("请输入正确的联系电话");
            return false;
        }
        return true;
    }

    /*@OnClick(R.id.aii_invoice_title)
    public void showHistoryWindow(View view) {
        if (mHistoryResp == null || CommonUtils.isEmpty(mHistoryResp.getRecords())) {
            if (mHistoryWindow != null) {
                mHistoryWindow.dismiss();
            }
        } else {
            if (mHistoryWindow == null) {
                mHistoryWindow = new InvoiceHistoryWindow(this, this);
                mHistoryWindow.setOnDismissListener(() -> getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE));
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            mHistoryWindow.setList(mHistoryResp.getRecords())
                    .showAsDropDownFix(view);
        }
    }*/

    /*@Override
    public void cacheInvoiceHistoryResp(InvoiceHistoryResp resp) {
        mHistoryResp = resp;
    }*/

    @Override
    public void showInvoiceHistory(List<InvoiceHistoryBean> list) {
        runOnUiThread(() -> mAdapter.setNewData(list, mInvoiceTitle.getText().toString().trim()));
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
                    callback = new NavCallback() {
                        @Override
                        public void onArrival(Postcard postcard) {
                            EventBus.getDefault().post(new InvoiceEvent(InvoiceEvent.RELOAD_LIST));
                            if (item != 0)
                                InvoiceDetailActivity.start(InvoiceInputActivity.this, mMakeResp.getId());
                        }
                    };
                    ARouter.getInstance().build(RouterConfig.INVOICE_ENTRY)
                            .setProvider(new LoginInterceptor())
                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            .navigation(this, callback);
                }, "返回列表", "查看记录")
                .create()
                .show();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        InvoiceHistoryBean item = (InvoiceHistoryBean) adapter.getItem(position);
        if (item == null) return;
//        mHistoryWindow.dismiss();
        mListView.setVisibility(View.GONE);
        setInvoiceTile(item.getInvoiceTitle());
        mInvoiceTitle.setSelection(item.getInvoiceTitle().length());
        if (mIdentifierGroup.getVisibility() == View.VISIBLE) {
            mIdentifier.setText(item.getTaxpayerNum());
        } else mIdentifier.setText("");
        mAccount.setText(item.getAccount());
        mBank.setText(item.getOpenBank());
        mAddress.setText(item.getAddress());
    }

    private void setInvoiceTile(String title) {
        mNeedAssociate = false;
        mInvoiceTitle.setText(title);
        mNeedAssociate = true;
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        View view = getCurrentFocus();
        if (view != null && view.getId() == mInvoiceTitle.getId()) {
            int[] location = new int[2];
            mInvoiceTitle.getLocationOnScreen(location);
            int y = location[1];
            mScrollView.getLocationOnScreen(location);
            mScrollView.scrollBy(0, y - location[1]);
        }
    }

    @Override
    public void onSoftKeyboardClosed() {

    }
}
