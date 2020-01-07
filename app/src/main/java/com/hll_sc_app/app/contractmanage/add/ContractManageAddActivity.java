package com.hll_sc_app.app.contractmanage.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONArray;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.selectpurchaser.SelectPurchaserListActivity;
import com.hll_sc_app.app.contractmanage.selectsignperson.SelectEmployListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.event.ContractManageEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private final int REQUEST_CODE_SELECT_EMPLOY = 102;
    private final int REQUEST_CODE_SELECT_FILE = 101;
    @BindView(R.id.edt_contract_name)
    EditText mEdtName;
    @BindView(R.id.edt_contract_no)
    EditText mEdtNo;
    @BindView(R.id.txt_contract_time_span)
    TextView mTxtTimeSpan;
    @BindView(R.id.txt_group_name)
    TextView mTxtGroupName;
    @BindView(R.id.txt_contract_person)
    TextView mTxtPerson;
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
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @Autowired(name = "parcelable")
    ContractListResp.ContractBean mDetailBean;
    private Unbinder unbinder;
    private IContractManageAddContract.IPresent mPresent;

    private DateSelectWindow mDateRangeSelectWindow;
    private DateWindow mDateWindow;

    private List<NameValue> mAttcchment;

    public static void start(ContractListResp.ContractBean contractBean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD, contractBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_add);
        ARouter.getInstance().inject(this);
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
        if (mDetailBean != null) {
            mTitleBar.setHeaderTitle("编辑合同");
            mEdtName.setText(mDetailBean.getContractName());
            mEdtNo.setText(mDetailBean.getContractCode());
            String startDate = CalendarUtils.getDateFormatString(mDetailBean.getStartDate(), "yyyyMMdd", "yyyy/MM/dd");
            String endDate = CalendarUtils.getDateFormatString(mDetailBean.getEndDate(), "yyyyMMdd", "yyyy/MM/dd");
            mTxtTimeSpan.setText(startDate + " - " + endDate);
            mTxtTimeSpan.setTag(R.id.base_tag_1, startDate);
            mTxtTimeSpan.setTag(R.id.base_tag_2, endDate);
            mTxtGroupName.setText(mDetailBean.getGroupName());
            PurchaserBean purchaserBean = new PurchaserBean();
            purchaserBean.setPurchaserName(mDetailBean.getPurchaserName());
            purchaserBean.setPurchaserID(mDetailBean.getPurchaserID());
            mTxtGroupName.setTag(purchaserBean);
            mTxtPerson.setText(mDetailBean.getSignEmployeeName());
            mTxtContractTime.setText(CalendarUtils.getDateFormatString(mDetailBean.getSignDate(), "yyyyMMdd", "yyyy/MM/dd"));
            mEdtBk.setText(mDetailBean.getRemarks());
            if (!TextUtils.isEmpty(mDetailBean.getAttachment())) {
                List<NameValue> nameValues = JSONArray.parseArray(mDetailBean.getAttachment(), NameValue.class);
                for (NameValue nameValue : nameValues) {
                    addImgUrlDetail(nameValue.getName(), nameValue.getValue());
                }
            }
            isInputComplete();
        }
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


        mTxtGroupName.setOnClickListener(v -> {
            String id = mTxtGroupName.getTag() == null ? "" : ((PurchaserBean) mTxtGroupName.getTag()).getPurchaserID();
            SelectPurchaserListActivity.start(this, REQUEST_CODE_SELECT_PURCHASER, id);
        });

        mTxtTimeSpan.setOnClickListener(v -> {
            if (mDateRangeSelectWindow == null) {
                mDateRangeSelectWindow = new DateSelectWindow(this);
                if (mDetailBean!=null){
                    mDateRangeSelectWindow.setSelectRange(mDetailBean.getStartDate(), mDetailBean.getEndDate());
                }
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
                if (mDetailBean!=null){
                    mDateWindow.setCalendar(CalendarUtils.parse(mDetailBean.getSignDate()));
                }
                mDateWindow.setSelectListener(date -> {
                    mDateWindow.dismiss();
                    mTxtContractTime.setText(CalendarUtils.format(date, FORMAT_DATE_TIME));
                    mTxtContractTime.setTag(CalendarUtils.format(date, FORMAT_LOCAL_DATE));
                    mTxtSubmit.setEnabled(isInputComplete());
                });
            }
            mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        });

        mImgUploadBlock.addClickListener(v -> {
            Upload.pickFile(this, REQUEST_CODE_SELECT_FILE, new String[]{
                    Upload.DOC,
                    Upload.DOCX,
                    Upload.PDF,
                    Upload.JPG,
                    Upload.PNG,
                    Upload.RAR,
                    Upload.ZIP,
            });
        });

        mTxtSubmit.setOnClickListener(v -> {
            mPresent.addContract();
        });

        mTxtPerson.setOnClickListener(v -> {
            String id = mTxtPerson.getTag() == null ? "" : ((EmployeeBean) mTxtPerson.getTag()).getEmployeeID();
            SelectEmployListActivity.start(this, REQUEST_CODE_SELECT_EMPLOY, id);
        });
    }

    private boolean isInputComplete() {
        boolean isEmpty = TextUtils.isEmpty(mEdtName.getText().toString()) ||
                TextUtils.isEmpty(mEdtNo.getText().toString()) ||
                TextUtils.isEmpty(mTxtTimeSpan.getText().toString()) ||
                TextUtils.isEmpty(mTxtPerson.getText().toString()) ||
                TextUtils.isEmpty(mTxtContractTime.getText().toString());
        return !isEmpty;
    }

    private void addImgUrlDetail(String fileName, String url) {
        if (mAttcchment == null) {
            mAttcchment = new ArrayList<>();
        }
        mAttcchment.add(new NameValue(fileName, url));
        int curChildCount = mLlPhoto.getChildCount();
        ImgShowDelBlock block = new ImgShowDelBlock(this);
        if (ImgShowDelBlock.isImageFile(url)) {
            block.setImgUrl(url);
        } else {
            block.setFileUrl(url, fileName);
        }
        block.setDeleteListener(v -> {
            mAttcchment.remove(curChildCount - 1);
            mLlPhoto.removeView(block);
            mImgUploadBlock.setSubTitle(String.format("%s/%s", mLlPhoto.getChildCount() - 1, 5));
            mImgUploadBlock.setVisibility(curChildCount == 5 ? View.GONE : View.VISIBLE);
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(60),
                UIUtils.dip2px(60));
        params.rightMargin = UIUtils.dip2px(10);
        mLlPhoto.addView(block, curChildCount - 1, params);
        mImgUploadBlock.setSubTitle(String.format("%s/%s", mLlPhoto.getChildCount() - 1, 5));
        mImgUploadBlock.setVisibility(curChildCount == 5 ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_PURCHASER && resultCode == RESULT_OK) {
            PurchaserBean bean = data.getParcelableExtra("purchaser");
            mTxtGroupName.setText(bean.getPurchaserName());
            mTxtGroupName.setTag(bean);
            mTxtSubmit.setEnabled(isInputComplete());
        } else if (requestCode == REQUEST_CODE_SELECT_EMPLOY && resultCode == RESULT_OK) {
            EmployeeBean bean = data.getParcelableExtra("employ");
            mTxtPerson.setText(bean.getEmployeeName());
            mTxtPerson.setTag(bean);
            mTxtSubmit.setEnabled(isInputComplete());
        } else if (requestCode == REQUEST_CODE_SELECT_FILE && data != null && data.getData() != null) {
            File file = new File(Upload.getFilePath(this, data.getData()));
            if (file.length() > 2 * 1024 * 1024) {//2M
                showToast("请选择大小不大于2M的文件");
                return;
            }
            Upload.fileUpload(file, new SimpleObserver<String>(this) {
                @Override
                public void onSuccess(String s) {
                    addImgUrlDetail(file.getName(), s);
                }
            });
        }
    }

    @Override
    public void addSuccess() {
        showToast("新增成功");
        EventBus.getDefault().post(new ContractManageEvent(true));
        finish();
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("恭喜您合同创建成功")
                .setMessage("合同将会在设定时间开始生效\n您可以点击查看合同详情")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        //todo 怎么查看详情
                    } else {
                        finish();
                    }
                }, "返回列表", "查看详情").create().show();
    }

    @Override
    public String getActionType() {
        return mDetailBean != null ? "update" : "insert";
    }

    @Override
    public String getAttachment() {
        if (CommonUtils.isEmpty(mAttcchment)) {
            return "";
        }
        return JSONArray.toJSONString(mAttcchment);
    }

    @Override
    public String getContractCode() {
        return mEdtNo.getText().toString();
    }

    @Override
    public String getContractName() {
        return mEdtName.getText().toString();
    }

    @Override
    public String getEndDate() {
        Object o = mTxtTimeSpan.getTag(R.id.base_tag_2);
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    @Override
    public String getStartDate() {
        Object o = mTxtTimeSpan.getTag(R.id.base_tag_1);
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    @Override
    public String getSignDate() {
        Object o = mTxtContractTime.getTag();
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    @Override
    public String getPurchaserID() {
        Object o = mTxtGroupName.getTag();
        if (o == null) {
            return "";
        }
        return ((PurchaserBean) o).getPurchaserID();
    }

    @Override
    public String getPurchaserName() {
        Object o = mTxtGroupName.getTag();
        if (o == null) {
            return "";
        }
        return ((PurchaserBean) o).getPurchaserName();
    }

    @Override
    public String getSignEmployeeName() {
        Object o = mTxtPerson.getTag();
        if (o == null) {
            return "";
        }
        return ((EmployeeBean) o).getEmployeeName();
    }

    @Override
    public String getSignEmployeeID() {
        Object o = mTxtPerson.getTag();
        if (o == null) {
            return "";
        }
        return ((EmployeeBean) o).getEmployeeID();
    }

    @Override
    public int getPurchaserType() {
        Object o = mTxtGroupName.getTag();
        if (o == null) {
            return 0;

        }
        return ((PurchaserBean) o).getPurchaserType();
    }

    @Override
    public String getRemarks() {
        return mEdtBk.getText().toString();
    }
}
