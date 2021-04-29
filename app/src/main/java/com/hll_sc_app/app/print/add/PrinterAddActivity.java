package com.hll_sc_app.app.print.add;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.print.PrinterBean;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.ViewCollections;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
@Route(path = RouterConfig.PRINTER_ADD)
public class PrinterAddActivity extends BaseLoadActivity implements IPrinterAddContract.IPrinterAddView {
    private static final int REQ_CODE = 0x531;
    @BindView(R.id.apa_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.apa_name)
    EditText mName;
    @BindView(R.id.apa_no)
    EditText mNo;
    @BindView(R.id.apa_code)
    EditText mCode;
    @BindViews({R.id.apa_name_label, R.id.apa_no_label, R.id.apa_code_label})
    List<TextView> mLabels;
    @BindViews({R.id.apa_name, R.id.apa_no, R.id.apa_code})
    List<EditText> mEditList;
    @Autowired(name = "parcelable")
    PrinterBean mPrinter;
    private IPrinterAddContract.IPrinterAddPresenter mPresenter;

    public static void start(Activity context, PrinterBean printer) {
        RouterUtil.goToActivity(RouterConfig.PRINTER_ADD, context, REQ_CODE, printer);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_printer_add);
        ButterKnife.bind(this);
        mPresenter = new PrinterAddPresenter();
        mPresenter.register(this);
        initView();
    }

    private void initView() {
        if (mPrinter == null) {
            mTitleBar.setHeaderTitle("添加打印机");
            mTitleBar.setRightText("保存");
            mTitleBar.setRightBtnClick(this::save);
            ViewCollections.run(mLabels, (view, index) -> {
                SpannableString ss = new SpannableString(" *");
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)),
                        0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                view.append(ss);
            });
        } else {
            mTitleBar.setHeaderTitle("查看打印机");
            mName.setText(mPrinter.getDeviceName());
            mNo.setText(mPrinter.getDeviceID());
            mCode.setText(mPrinter.getDeviceCode());
            ViewCollections.run(mEditList, (view, index) -> view.setEnabled(false));
        }
    }

    private void save(View view) {
        for (EditText text : mEditList) {
            if (TextUtils.isEmpty(text.getText().toString().trim())) {
                showToast("填写所有必填项后重试");
                return;
            }
        }
        mPrinter = new PrinterBean();
        mPrinter.setDeviceName(mName.getText().toString().trim());
        mPrinter.setDeviceID(mNo.getText().toString().trim());
        mPrinter.setDeviceCode(mCode.getText().toString().trim());
        mPresenter.save(mPrinter);
    }

    @Override
    public void success() {
        setResult(RESULT_OK);
        onBackPressed();
    }
}
