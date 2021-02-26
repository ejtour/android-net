package com.hll_sc_app.app.invoice.detail.record;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.common.InvoiceHelper;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.invoice.ReturnRecordBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */
@Route(path = RouterConfig.INVOICE_RETURN_RECORD)
public class ReturnRecordActivity extends BaseLoadActivity implements IReturnRecordContract.IReturnRecordView {
    public static final int REQ_CODE = 0x337;

    /**
     * @param bean 回款记录实体类
     */
    public static void start(Activity context, ReturnRecordBean bean) {
        RouterUtil.goToActivity(RouterConfig.INVOICE_RETURN_RECORD, context, REQ_CODE, bean);
    }

    /**
     * @param invoiceID 发票id
     */
    public static void start(Activity context, String invoiceID) {
        Object[] args = {invoiceID};
        RouterUtil.goToActivity(RouterConfig.INVOICE_RETURN_RECORD, context, REQ_CODE, args);
    }

    @Autowired(name = "parcelable")
    ReturnRecordBean mBean;
    @Autowired(name = "object0")
    String mInvoiceID;
    @BindView(R.id.irr_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.irr_time)
    TextView mTime;
    @BindView(R.id.irr_type)
    TextView mType;
    @BindView(R.id.irr_money)
    EditText mMoney;
    private DateWindow mDateWindow;
    private SingleSelectionDialog mDialog;
    private IReturnRecordContract.IReturnRecordPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_return_record);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        if (mBean != null) {
            mTime.setTag(mBean.getReturnDate());
            mType.setTag(String.valueOf(mBean.getReturnPayType()));
        }
        mPresenter = ReturnRecordPresenter.newInstance(mBean == null ? 0 : 1, mInvoiceID);
        mPresenter.register(this);
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> {
            if (mTime.getTag() == null || mType.getTag() == null || TextUtils.isEmpty(mMoney.getText())) {
                showToast("请完善全部信息");
                return;
            }
            mPresenter.commit(mBean == null ? null : mBean.getId(),
                    mTime.getTag().toString(),
                    mMoney.getText().toString(), (String) mType.getTag());
        });
        if (mBean == null) {
            mTitleBar.setHeaderTitle("新增回款记录");
        } else {
            mInvoiceID = mBean.getInvoiceID();
            mTitleBar.setHeaderTitle("编辑回款记录");
            mTime.setText(DateUtil.getReadableTime(mBean.getReturnDate()));
            mType.setText(InvoiceHelper.getReturnType(mBean.getReturnPayType()));
            mMoney.setText(CommonUtils.formatNumber(mBean.getReturnMoney()));
        }
    }

    @OnClick(R.id.irr_time)
    public void selectTime() {
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(this);
            mDateWindow.setCalendar(new Date());
            mDateWindow.setSelectListener(date -> {
                mTime.setText(CalendarUtils.format(date, Constants.SIGNED_YYYY_MM_DD));
                mTime.setTag(CalendarUtils.format(date, Constants.UNSIGNED_YYYY_MM_DD));
            });
        }
        mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    @OnClick(R.id.irr_type)
    public void selectType() {
        if (mDialog == null) {
            List<NameValue> list = new ArrayList<>();
            NameValue select = null;
            for (int i = 1; i < 5; i++) {
                NameValue temp = new NameValue(InvoiceHelper.getReturnType(i), String.valueOf(i));
                list.add(temp);
                if (mBean != null && mBean.getReturnPayType() == i) select = temp;
            }
            mDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("回款方式")
                    .refreshList(list)
                    .select(select)
                    .setOnSelectListener(nameValue -> {
                        mType.setText(nameValue.getName());
                        mType.setTag(nameValue.getValue());
                    })
                    .create();
        }
        mDialog.show();
    }

    @OnTextChanged(value = R.id.irr_money, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable s) {
        Utils.processMoney(s, false);
    }

    @Override
    public void success() {
        setResult(RESULT_OK);
        finish();
    }
}
