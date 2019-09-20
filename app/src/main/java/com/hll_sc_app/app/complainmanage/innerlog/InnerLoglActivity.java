package com.hll_sc_app.app.complainmanage.innerlog;

import android.graphics.Color;
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
import com.hll_sc_app.bean.complain.DepartmentsBean;
import com.hll_sc_app.widget.TitleBar;
import com.zhy.view.flowlayout.TagFlowLayout;

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
                mProblemAdpter.setBackgroundColorandTextColor("#ffffff","#222222");
                mLinkAdpter.setBackgroundColorandTextColor("#ffffff","#222222");
                mTitlebar.setRightText("保存");
                isEdit = !isEdit;
            } else {

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
        String linkDepartments = complainInnerLogResp.getRelationDepartmentName();
        String issueDepartmentName = complainInnerLogResp.getIssueDepartmentName();
        if (TextUtils.isEmpty(linkDepartments) || TextUtils.isEmpty(issueDepartmentName)) {
            return;
        }
        isEdit = true;
        mLinkAdpter = new DepartFlowAdpter(this, Arrays.asList(linkDepartments.split(",")));
        mTagFlowLinkDepart.setAdapter(mLinkAdpter);
        mProblemAdpter = new DepartFlowAdpter(this, Arrays.asList(issueDepartmentName.split(",")));
        mTagFlowProblemDepart.setAdapter(mProblemAdpter);
        mEdtLog.setText(complainInnerLogResp.getResult());
        mEdtLog.setEnabled(false);
        mProblemArrow.setVisibility(View.GONE);
        mLinkArrow.setVisibility(View.GONE);
        mTxtLeftNumber.setVisibility(View.GONE);
        mTitlebar.setRightText("编辑");
    }

    @Override
    public void queryDepartmentsSuccess(List<DepartmentsBean> departmentsBeanList) {

    }

    @OnClick({R.id.ll_line_1, R.id.ll_line_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_line_1:
                break;
            case R.id.ll_line_2:
                break;
            default:
                break;
        }

    }
}
