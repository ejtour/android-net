package com.hll_sc_app.app.complainmanage.innerlog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.complain.ComplainInnerLogResp;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.widget.MultipSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 内部记录
 */
@Route(path = RouterConfig.ACTIVITY_COMPLAIN_INNER_LOG)
public class InnerLoglActivity extends BaseLoadActivity implements IInnerLogContract.IView {
    private final int MAX_INPUT_LENGTH = 200;
    @Autowired(name = "object0")
    String mCompaintId;
    @BindView(R.id.title_bar)
    TitleBar mTitlebar;
    @BindView(R.id.flow_link_depart)
    TagFlowLayout mTagFlowLinkDepart;
    @BindView(R.id.flow_problem_depart)
    TagFlowLayout mTagFlowProblemDepart;
    @BindView(R.id.edt_log)
    EditText mEdtLog;
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNumber;
    @BindView(R.id.img_problem_depart)
    ImageView mProblemArrow;
    @BindView(R.id.img_link_depart)
    ImageView mLinkArrow;

    private Unbinder unbinder;
    private IInnerLogContract.IPresent mPresent;
    private boolean isEdit = false;

    private DepartFlowAdpter mLinkAdpter;
    private DepartFlowAdpter mProblemAdpter;

    private MultipSelectionDialog mLinkSelectDialog;
    private MultipSelectionDialog mProblemSelectDialog;
    private ComplainInnerLogResp mComplainInnerLogResp;

    public static void start(String compaintId) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_INNER_LOG, compaintId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_inner_log);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresent = InnerLogPresent.newInstance();
        mPresent.register(this);
        mPresent.start();
    }

    private void initView() {
        mEdtLog.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LENGTH)});
        mEdtLog.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTxtLeftNumber.setText(String.valueOf(MAX_INPUT_LENGTH - s.toString().length()));
            }
        });

        mTitlebar.setRightBtnClick(v -> {
            if (isEdit) {
                mEdtLog.setEnabled(true);
                mEdtLog.setSelection(mEdtLog.getText().toString().length());
                mProblemArrow.setVisibility(View.VISIBLE);
                mLinkArrow.setVisibility(View.VISIBLE);
                mTxtLeftNumber.setVisibility(View.VISIBLE);
                mProblemAdpter.setBackgroundColorandTextColor(false,"#ffffff","#222222");
                mLinkAdpter.setBackgroundColorandTextColor(false,"#ffffff","#222222");
                mTitlebar.setRightText("保存");
                isEdit = !isEdit;
            } else {
                mPresent.saveComplainInnerLog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public String getComplaintID() {
        return mCompaintId;
    }

    @Override
    public void queryInnerLogSucess(ComplainInnerLogResp complainInnerLogResp) {
        mComplainInnerLogResp = complainInnerLogResp;
        mLinkAdpter = new DepartFlowAdpter(this, new ArrayList<>());
        mTagFlowLinkDepart.setAdapter(mLinkAdpter);
        mTagFlowLinkDepart.setOnTagClickListener((view, position, parent) -> {
            if (mLinkSelectDialog != null) {
                mLinkSelectDialog.show();
            }
            return false;
        });

        mProblemAdpter = new DepartFlowAdpter(this, new ArrayList<>());
        mTagFlowProblemDepart.setAdapter(mProblemAdpter);
        mTagFlowProblemDepart.setOnTagClickListener((view, position, parent) -> {
            if (mProblemSelectDialog != null) {
                mProblemSelectDialog.show();
            }
            return false;
        });
        String linkDepartmentName = complainInnerLogResp.getRelationDepartmentName();
        String issueDepartmentName = complainInnerLogResp.getIssueDepartmentName();
        if (TextUtils.isEmpty(linkDepartmentName) || TextUtils.isEmpty(issueDepartmentName)) {
            mProblemAdpter.setBackgroundColorandTextColor(false,"#ffffff", "#222222");
            mLinkAdpter.setBackgroundColorandTextColor(false,"#ffffff", "#222222");
            return;
        }
        isEdit = true;
        mLinkAdpter.setNewData(Arrays.asList(linkDepartmentName.split(",")));
        mProblemAdpter.setNewData(Arrays.asList(issueDepartmentName.split(",")));
        mEdtLog.setText(complainInnerLogResp.getResult());
        mEdtLog.setEnabled(false);
        mProblemArrow.setVisibility(View.GONE);
        mLinkArrow.setVisibility(View.GONE);
        mTxtLeftNumber.setVisibility(View.GONE);
        mTitlebar.setRightText("编辑");

    }

    @Override
    public void queryDropMenusSuccess(List<DropMenuBean> departmentsBeanList) {
        if (TextUtils.isEmpty(mComplainInnerLogResp.getRelationDepartment())) {
            mComplainInnerLogResp.setRelationDepartment("");
        }
        if (TextUtils.isEmpty(mComplainInnerLogResp.getIssueDepartment())) {
            mComplainInnerLogResp.setIssueDepartment("");
        }
        mLinkSelectDialog = MultipSelectionDialog.newBuilder(this, new MultipSelectionDialog.WrapperName<DropMenuBean>() {
            @Override
            public String getName(DropMenuBean departmentsBean) {
                return departmentsBean.getValue();
            }

            @Override
            public String getKey(DropMenuBean departmentsBean) {
                return departmentsBean.getKey();
            }
        })
                .refreshList(departmentsBeanList)
                .setTitleText("选择关联部门")
                .setOnSelectListener(ts -> {
                    List<String> names = new ArrayList<>();
                    StringBuilder builder = new StringBuilder();
                    for (DropMenuBean departmentsBean : ts) {
                        names.add(departmentsBean.getValue());
                        builder.append(departmentsBean.getKey()).append(",");
                    }
                    mComplainInnerLogResp.setRelationDepartment(builder.toString());
                    mLinkAdpter.setNewData(names);
                })
                .selectByKey(Arrays.asList(mComplainInnerLogResp.getRelationDepartment().split(",")))
                .create();

        mProblemSelectDialog = MultipSelectionDialog.newBuilder(this, new MultipSelectionDialog.WrapperName<DropMenuBean>() {
            @Override
            public String getName(DropMenuBean departmentsBean) {
                return departmentsBean.getValue();
            }

            @Override
            public String getKey(DropMenuBean departmentsBean) {
                return departmentsBean.getKey();
            }
        })
                .refreshList(departmentsBeanList)
                .setTitleText("选择问题部门")
                .setOnSelectListener(ts -> {
                    StringBuilder builder = new StringBuilder();
                    List<String> names = new ArrayList<>();
                    for (DropMenuBean departmentsBean : ts) {
                        names.add(departmentsBean.getValue());
                        builder.append(departmentsBean.getKey()).append(",");
                    }
                    mComplainInnerLogResp.setIssueDepartment(builder.toString());
                    mProblemAdpter.setNewData(names);
                })
                .selectByKey(Arrays.asList(mComplainInnerLogResp.getIssueDepartment().split(",")))
                .create();


    }

    @Override
    public void saveLogSuccess() {
        finish();
    }

    @Override
    public String getIssueDepartment() {
        return mComplainInnerLogResp.getIssueDepartment();
    }

    @Override
    public String getrelationDepartment() {
        return mComplainInnerLogResp.getRelationDepartment();
    }

    @Override
    public String getResult() {
        return mEdtLog.getText().toString();
    }

    @OnClick({R.id.ll_line_1, R.id.ll_line_2, R.id.flow_link_depart, R.id.flow_problem_depart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_line_1:
            case R.id.flow_link_depart:
                if (mLinkSelectDialog != null) {
                    mLinkSelectDialog.show();
                }
                break;
            case R.id.ll_line_2:
            case R.id.flow_problem_depart:
                if (mProblemSelectDialog != null) {
                    mProblemSelectDialog.show();
                }
                break;
            default:
                break;
        }

    }
}
