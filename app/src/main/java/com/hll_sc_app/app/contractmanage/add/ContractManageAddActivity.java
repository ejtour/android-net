package com.hll_sc_app.app.contractmanage.add;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.selectcontract.SelectContractListActivity;
import com.hll_sc_app.app.contractmanage.selectpurchaser.SelectPurchaserListActivity;
import com.hll_sc_app.app.contractmanage.selectsignperson.SelectEmployListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.JsonUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.contract.ContractGroupShopBean;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.contract.ContractProductListResp;
import com.hll_sc_app.bean.event.ContractManageEvent;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Upload;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ExcelFooter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final int REQUEST_CODE_SELECT_LINK_CONTRACT = 103;
    private static final int[] WIDTH_ARRAY = {40, 80, 120, 80, 80, 200,};
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
    @BindView(R.id.edt_bk)
    EditText mEdtBk;
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNumber;
    @BindView(R.id.txt_btn_submit)
    TextView mTxtSubmit;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.txt_type)
    TextView mTxtType;
    @BindView(R.id.txt_shop_name)
    TextView mTxtShopName;
    @Autowired(name = "parcelable")
    ContractListResp.ContractBean mDetailBean;
    @Autowired(name = "groupshop")
    ContractGroupShopBean mGroupShopBean;
    @BindView(R.id.txt_link_main)
    TextView mTxtLinkmain;
    @BindView(R.id.edt_money)
    EditText mEdtMoney;
    @BindView(R.id.execl_product)
    ExcelLayout mexeclProduct;
    @BindView(R.id.group_shop)
    Group mGroupShop;
    private Unbinder unbinder;
    private IContractManageAddContract.IPresent mPresent;

    private DateSelectWindow mDateRangeSelectWindow;
    private DateWindow mDateWindow;

    private List<NameValue> mAttcchment;

    private SingleSelectionDialog mSingleSelectType;

    private ExcelFooter footer;

    //编辑进来
    public static void start(ContractListResp.ContractBean contractBean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD, contractBean);
    }

    //新增时进来
    public static void start(ContractGroupShopBean groupShopBean) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_CONTRACT_MANAGE_ADD)
                .withParcelable("groupshop", groupShopBean)
                .setProvider(new LoginInterceptor())
                .navigation();
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

    private ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]));
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[5] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[5]), Gravity.CENTER_VERTICAL | Gravity.LEFT);
        return array;
    }

    private View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("序号", "商品编号", "商品名称", "规格/单位", "计划数量", "备注");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }


    private void initExeclProduct() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[3];
        int[] WIDTH_ARRAY = {320, 80, 200,};
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        footer = new ExcelFooter(this);
        footer.updateChildView(WIDTH_ARRAY.length);
        footer.updateItemData(array);
        footer.updateRowDate("合计", "0", "");
        mexeclProduct.setHeaderView(generateHeader());
        mexeclProduct.setFooterView(footer);

        ContractProductListResp.ProduceBean produceBean = new ContractProductListResp.ProduceBean();
        produceBean.setProductCode("111");
        produceBean.setProductName("name");
        produceBean.setSaleUnitName("桶");
        produceBean.setSpecContent("规格");
        produceBean.setIndex("1");
        mexeclProduct.setColumnDataList(generateColumnData());
        mexeclProduct.setData(Arrays.asList(produceBean),false);
    }

    private void initView() {
        initExeclProduct();
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
            ContractGroupShopBean groupShopBean = new ContractGroupShopBean();
            groupShopBean.setPurchaserName(mDetailBean.getPurchaserName());
            groupShopBean.setPurchaserID(mDetailBean.getPurchaserID());
            groupShopBean.setShopID(mDetailBean.getShopID());
            groupShopBean.setShopName(mDetailBean.getShopName());
            groupShopBean.setPurchaserType(mDetailBean.getPurchaserType());
            mTxtGroupName.setTag(groupShopBean);
            mTxtShopName.setText(mDetailBean.getShopName());
            mTxtPerson.setText(mDetailBean.getSignEmployeeName());
            mTxtContractTime.setText(CalendarUtils.getDateFormatString(mDetailBean.getSignDate(), "yyyyMMdd", "yyyy/MM/dd"));
            mEdtBk.setText(mDetailBean.getRemarks());
            if (!TextUtils.isEmpty(mDetailBean.getAttachment())) {
                List<NameValue> nameValues = JsonUtil.parseJsonList(mDetailBean.getAttachment(), NameValue.class);
                for (NameValue nameValue : nameValues) {
                    addImgUrlDetail(nameValue.getName(), nameValue.getValue());
                }
            }
            isInputComplete();
            mTxtType.setText(mDetailBean.getTranContractType());
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

        if (mGroupShopBean != null) {
            mTxtGroupName.setTag(mGroupShopBean);
            mTxtGroupName.setText(mGroupShopBean.getPurchaserName());
            mTxtShopName.setText(mGroupShopBean.getShopName());
            toggleShop(mGroupShopBean.getPurchaserType());
        }

        mTxtGroupName.setOnClickListener(v -> {
            SelectPurchaserListActivity.start((ContractGroupShopBean) mTxtGroupName.getTag(), true, false);
        });

        mTxtShopName.setOnClickListener(v -> {
//            ContractGroupShopBean shopBean = (ContractGroupShopBean) mTxtGroupName.getTag();
            SelectPurchaserListActivity.start((ContractGroupShopBean) mTxtGroupName.getTag(), false, false);
        });

        mTxtTimeSpan.setOnClickListener(v -> {
            if (mDateRangeSelectWindow == null) {
                mDateRangeSelectWindow = new DateSelectWindow(this);
                if (mDetailBean != null) {
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
                if (mDetailBean != null) {
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

       /* Upload.DOC,
                    Upload.DOCX,
                    Upload.PDF,
                    Upload.JPG,
                    Upload.PNG,
                    Upload.RAR,
                    Upload.ZIP,*/

        mTxtSubmit.setOnClickListener(v -> {
            mPresent.addContract();
        });

        mTxtPerson.setOnClickListener(v -> {
            String id = mTxtPerson.getTag() == null ? "" : ((EmployeeBean) mTxtPerson.getTag()).getEmployeeID();
            SelectEmployListActivity.start(this, REQUEST_CODE_SELECT_EMPLOY, id);
        });

        mTxtType.setOnClickListener(v -> {
            if (mSingleSelectType == null) {
                List<NameValue> types = new ArrayList<>();
                types.add(new NameValue("销售合同", "1"));
                types.add(new NameValue("采购合同", "2"));
                mSingleSelectType = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                        .refreshList(types)
                        .setTitleText("合同类型")
                        .setOnSelectListener(nameValue -> {
                            mTxtType.setText(nameValue.getName());
                            mTxtType.setTag(nameValue.getValue());
                        })
                        .select(mDetailBean != null ? types.get(mDetailBean.getContractType() - 1) : null)
                        .create();
            }
            mSingleSelectType.show();
        });

        mTxtLinkmain.setOnClickListener(v -> {
            ContractListResp.ContractBean contractBean = new ContractListResp.ContractBean();
            contractBean.setContractID(mTxtLinkmain.getTag() != null ? mTxtLinkmain.getTag().toString() : "");
            SelectContractListActivity.start(this, REQUEST_CODE_SELECT_LINK_CONTRACT, contractBean);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_EMPLOY && resultCode == RESULT_OK) {
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
        } else if (requestCode == REQUEST_CODE_SELECT_LINK_CONTRACT && data != null && data.getParcelableExtra("bean") != null) {
            ContractListResp.ContractBean contractBean = data.getParcelableExtra("bean");
            mTxtLinkmain.setText(contractBean.getContractName());
            mTxtLinkmain.setTag(contractBean);
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
        return JsonUtil.toJson(mAttcchment);
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
        return ((ContractGroupShopBean) o).getPurchaserID();
    }

    @Override
    public String getPurchaserName() {
        Object o = mTxtGroupName.getTag();
        if (o == null) {
            return "";
        }
        return ((ContractGroupShopBean) o).getPurchaserName();
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
        return ((ContractGroupShopBean) o).getPurchaserType();
    }

    @Override
    public String getRemarks() {
        return mEdtBk.getText().toString();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Parcelable parcelable = intent.getParcelableExtra("parcelable");
        if (parcelable instanceof ContractGroupShopBean) {
            ContractGroupShopBean contractBean = (ContractGroupShopBean) parcelable;
            mTxtGroupName.setTag(contractBean);
            mTxtGroupName.setText(contractBean.getPurchaserName());
            mTxtShopName.setText(contractBean.getShopName());
            mTxtSubmit.setEnabled(isInputComplete());
            toggleShop(contractBean.getPurchaserType());
        }

    }

    private void toggleShop(int type) {
        mGroupShop.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
    }
}
