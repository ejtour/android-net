package com.hll_sc_app.app.contractmanage.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.selectpurchaser.SelectPurchaserListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.citymall.util.CalendarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_DATE_TIME;
import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_LOCAL_DATE;

/**
 * 合同管理-新增
 */
@Route(path = RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD)
public class ContractManageAddActivity extends BaseLoadActivity implements IContractManageAddContract.IView {

    private final int REQUEST_CODE_SELECT_PURCHASER = 100;
    @BindView(R.id.edt_contract_name)
    EditText mEdtName;
    @BindView(R.id.edt_contract_no)
    EditText mEdtNo;
    @BindView(R.id.txt_contract_time_span)
    TextView mTxtTimeSpan;
    @BindView(R.id.txt_group_name)
    TextView mTxtGroupName;
    @BindView(R.id.edt_contract_person)
    EditText mEdtPerson;
    @BindView(R.id.txt_contract_time)
    TextView mTxtContractTime;
    @BindView(R.id.ll_scroll_photo)
    LinearLayout mLlPhoto;
    @BindView(R.id.upload_img)
    ImgUploadBlock mImgUploadBlock;
    @BindView(R.id.edt_bk)
    EditText mEdtBk;
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNumber;
    @BindView(R.id.txt_btn_submit)
    TextView mTxtSubmit;
    private Unbinder unbinder;
    private IContractManageAddContract.IPresent mPresent;

    private DateSelectWindow mDateRangeSelectWindow;
    private DateWindow mDateWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_add);
        unbinder = ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        mPresent = ContractManageAddPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTxtSubmit.setEnabled(isInputComplete());
            }
        };

        mEdtName.addTextChangedListener(textWatcher);
        mEdtNo.addTextChangedListener(textWatcher);
        mEdtPerson.addTextChangedListener(textWatcher);


        mTxtGroupName.setOnClickListener(v -> {
            String id = mTxtGroupName.getTag() == null ? "" : ((PurchaserBean) mTxtGroupName.getTag()).getPurchaserID();
            SelectPurchaserListActivity.start(this, REQUEST_CODE_SELECT_PURCHASER, id);
        });

        mTxtTimeSpan.setOnClickListener(v -> {
            if (mDateRangeSelectWindow == null) {
                mDateRangeSelectWindow = new DateSelectWindow(this);
                mDateRangeSelectWindow.setSelectListener((startDate, endDate) -> {
                    mDateRangeSelectWindow.dismiss();
                    mTxtTimeSpan.setText(CalendarUtils.getDateFormatString(startDate, FORMAT_LOCAL_DATE, FORMAT_DATE_TIME) + "-" + CalendarUtils.getDateFormatString(endDate, FORMAT_LOCAL_DATE, FORMAT_DATE_TIME));
                    mTxtTimeSpan.setTag(R.id.base_tag_1, startDate);
                    mTxtTimeSpan.setTag(R.id.base_tag_2, endDate);
                    mTxtSubmit.setEnabled(isInputComplete());
                });
            }
            mDateRangeSelectWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        });

        mTxtContractTime.setOnClickListener(v -> {
            if (mDateWindow == null) {
                mDateWindow = new DateWindow(this);
                mDateWindow.setSelectListener(date -> {
                    mDateWindow.dismiss();
                    mTxtContractTime.setText(CalendarUtils.format(date, FORMAT_DATE_TIME));
                    mTxtContractTime.setTag(CalendarUtils.format(date, FORMAT_LOCAL_DATE));
                    mTxtSubmit.setEnabled(isInputComplete());
                });
            }
            mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        });
    }

    private boolean isInputComplete() {
        boolean isEmpty = TextUtils.isEmpty(mEdtName.getText().toString()) ||
                TextUtils.isEmpty(mEdtNo.getText().toString()) ||
                TextUtils.isEmpty(mTxtTimeSpan.getText().toString()) ||
                TextUtils.isEmpty(mEdtPerson.getText().toString()) ||
                TextUtils.isEmpty(mTxtContractTime.getText().toString());


        return !isEmpty;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_PURCHASER && resultCode == RESULT_OK) {
            PurchaserBean bean = data.getParcelableExtra("purchaser");
            mTxtGroupName.setText(bean.getPurchaserName());
            mTxtGroupName.setTag(bean);
            mTxtSubmit.setEnabled(isInputComplete());
        }
    }


}
