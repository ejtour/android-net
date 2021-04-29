package com.hll_sc_app.app.print.preview;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.web.WebViewProxy;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.print.PrintPreviewResp;
import com.hll_sc_app.bean.print.PrinterBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.service.PrintJobService;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.KeyboardWatcher;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/8
 */
@Route(path = RouterConfig.PRINT_PREVIEW)
public class PrintPreviewActivity extends BaseLoadActivity implements IPrintPreviewContract.IPrintPreviewView, KeyboardWatcher.SoftKeyboardStateListener {

    private static final int REQ_CODE = 0x996;
    @BindView(R.id.app_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.app_web_view_container)
    FrameLayout mWebViewContainer;
    @BindView(R.id.app_bottom_bar)
    FrameLayout mBottomBar;
    @BindView(R.id.app_print)
    TextView mPrint;
    @BindView(R.id.app_select_label)
    TextView mSelectLabel;
    @BindView(R.id.app_select)
    TextView mSelect;
    @BindView(R.id.app_copies)
    EditText mCopies;
    @BindView(R.id.app_copies_minus)
    ImageView mMinus;
    @BindView(R.id.app_copies_add)
    ImageView mAdd;
    @Autowired(name = "object0")
    String mTemplateId;
    @Autowired(name = "object1")
    String mSubBillNo;
    private WebViewProxy mProxy;
    private IPrintPreviewContract.IPrintPreviewPresenter mPresenter;
    private PrintPreviewResp mResp;
    private PrinterBean mPrinter;
    private KeyboardWatcher mKeyboardWatcher;

    /**
     * @param templateId 模板id
     * @param subBillNo  订单号
     */
    public static void start(String templateId, String subBillNo) {
        RouterUtil.goToActivity(RouterConfig.PRINT_PREVIEW, templateId, subBillNo);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_print_preview);
        ButterKnife.bind(this);
        mPresenter = new PrintPreviewPresenter();
        mPresenter.register(this);
        if (!TextUtils.isEmpty(mTemplateId)) {
            mPresenter.loadTemplate(mTemplateId);
        } else {
            mPresenter.loadBill(mSubBillNo);
        }
        initView();
    }

    private void initView() {
        mMinus.setEnabled(false);
        SpannableString ss = new SpannableString(" *");
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSelectLabel.append(ss);
        mKeyboardWatcher = new KeyboardWatcher(this);
        mKeyboardWatcher.addSoftKeyboardStateListener(this);
    }

    private void initWebView(String url) {
        Bundle args = new Bundle();
        args.putBoolean(Constants.WEB_ZOOM, true);
        args.putString(Constants.WEB_URL, url);
        mProxy = new WebViewProxy(args, mWebViewContainer);
        mProxy.initWebView(null, new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBottomBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
    }

    @OnClick(R.id.app_print)
    void print() {
        if (mResp != null && mPrinter != null) {
            mPresenter.print(mResp.getPdfUrl(), mResp.getHtml(),
                    getCopies(), mPrinter.getDeviceID());
        }
    }

    @OnClick(R.id.app_select_label)
    void selectPrinter() {
        Object[] args = {true};
        RouterUtil.goToActivity(RouterConfig.PRINTER_LIST, this, REQ_CODE, args);
    }

    @OnClick(R.id.app_copies_add)
    void add() {
        mCopies.setText(String.valueOf(getCopies() + 1));
    }

    @OnFocusChange(R.id.app_copies)
    void onFocusChanged(boolean hasFocus) {
        int copies = getCopies();
        if (hasFocus) {
            mCopies.setTag(copies);
        } else if (copies == 0) {
            showToast("支持份数：1-99");
            mCopies.setText(mCopies.getTag().toString());
        }
    }

    private int getCopies() {
        return CommonUtils.getInt(mCopies.getText().toString());
    }

    @OnTextChanged(R.id.app_copies)
    void onCopiesTextChanged(CharSequence s) {
        int value = CommonUtils.getInt(s.toString());
        mMinus.setEnabled(value > 1);
        mAdd.setEnabled(value < 99);
    }

    @OnClick(R.id.app_copies_minus)
    void minus() {
        mCopies.setText(String.valueOf(getCopies() - 1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            mPrinter = data.getParcelableExtra("printer");
            mSelect.setText(String.format("%s%s", mPrinter.getDeviceName(),
                    TextUtils.isEmpty(mPrinter.getReturnMsg()) ? ""
                            : "（" + mPrinter.getReturnMsg() + "）"));
        }
    }

    @Override
    public void onBackPressed() {
        if (mProxy != null && mProxy.canGoBack()) {
            mProxy.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (mProxy == null) {
            onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (mKeyboardWatcher != null) {
            mKeyboardWatcher.removeSoftKeyboardStateListener(this);
        }
        if (mProxy != null) {
            mProxy.destroy();
        }
        super.onDestroy();
    }

    @OnTextChanged(R.id.app_select)
    void onTextChanged(CharSequence s) {
        mPrint.setEnabled(s.length() > 0);
    }

    @Override
    public void setData(PrintPreviewResp resp) {
        mResp = resp;
        initWebView(resp.getFileUrl());
    }

    @Override
    public void printSendSuccess(String billNo) {
        PrintJobService.enqueueWork(billNo, mSubBillNo);
    }

    @OnTouch(R.id.app_root)
    boolean onTouch() {
        UIUtils.hideActivitySoftKeyboard(this);
        onSoftKeyboardClosed();
        return false;
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        // no-op
    }

    @Override
    public void onSoftKeyboardClosed() {
        mSelectLabel.requestFocus();
    }
}
