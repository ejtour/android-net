package com.hll_sc_app.app.crm.daily.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyEditReq;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ImageUploadGroup;
import com.hll_sc_app.widget.MultiSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */

@Route(path = RouterConfig.CRM_DAILY_EDIT)
public class CrmDailyEditActivity extends BaseLoadActivity implements ICrmDailyEditContract.ICrmDailyEditView {
    public static final int REQ_CODE = 226;
    @BindView(R.id.cde_last_time)
    TextView mLastTime;
    @BindView(R.id.cde_submit)
    TextView mSubmit;
    @BindView(R.id.cde_today_label)
    TextView mTodayLabel;
    @BindView(R.id.cde_today_content)
    EditText mTodayContent;
    @BindView(R.id.cde_today_num)
    TextView mTodayNum;
    @BindView(R.id.cde_tomorrow_content)
    EditText mTomorrowContent;
    @BindView(R.id.cde_tomorrow_num)
    TextView mTomorrowNum;
    @BindView(R.id.cde_help_content)
    EditText mHelpContent;
    @BindView(R.id.cde_help_num)
    TextView mHelpNum;
    @BindView(R.id.cde_remark_content)
    EditText mRemarkContent;
    @BindView(R.id.cde_remark_num)
    TextView mRemarkNum;
    @BindView(R.id.cde_remark_upload)
    ImageUploadGroup mRemarkUpload;
    @BindView(R.id.cde_receiver)
    TextView mReceiver;
    @Autowired(name = "parcelable")
    DailyEditReq mReq;
    @BindView(R.id.cde_title_bar)
    TitleBar mTitleBar;
    private ICrmDailyEditContract.ICrmDailyEditPresenter mPresenter;
    private List<EmployeeBean> mList;
    private MultiSelectionDialog mDialog;

    public static void start(Activity context, DailyEditReq req) {
        RouterUtil.goToActivity(RouterConfig.CRM_DAILY_EDIT, context, REQ_CODE, req);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_daily_edit);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRemarkUpload.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        SpannableString spannableString = new SpannableString("今日完成工作*");
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_ed5655)),
                spannableString.length() - 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTodayLabel.setText(spannableString);
        if (mReq == null) {
            mReq = new DailyEditReq();
        } else {
            mTodayContent.setText(mReq.getTodayWork());
            mTomorrowContent.setText(mReq.getTomorrowPlan());
            mHelpContent.setText(mReq.getNeedHelp());
            mRemarkContent.setText(mReq.getRemark());
            if (!TextUtils.isEmpty(mReq.getImgurls()))
                mRemarkUpload.showImages(mReq.getImgurls().split(","));
            if (!TextUtils.isEmpty(mReq.getId())) {
                mReceiver.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mReceiver.setClickable(false);
                mTitleBar.setHeaderTitle("编辑日报");
            }
        }
    }

    private void initData() {
        mPresenter = CrmDailyEditPresenter.newInstance(mReq);
        mPresenter.register(this);
        mPresenter.start();
    }

    @OnClick(R.id.cde_receiver)
    public void selectReceiver() {
        if (mList == null) {
            mPresenter.reqReceiverList();
            return;
        }
        if (mDialog == null) {
            MultiSelectionDialog.WrapperName<EmployeeBean> wrapperName = new MultiSelectionDialog.WrapperName<EmployeeBean>() {
                @Override
                public String getName(EmployeeBean employeeBean) {
                    return employeeBean.getEmployeeName();
                }

                @Override
                public String getKey(EmployeeBean employeeBean) {
                    return employeeBean.getEmployeeID();
                }
            };
            List<String> list = new ArrayList<>();
            if (mReq.getReceiver() != null) {
                list = Arrays.asList(mReq.getReceiver().split(","));
            }
            mDialog = MultiSelectionDialog.newBuilder(this, wrapperName)
                    .refreshList(mList)
                    .selectByKey(list)
                    .setTitleText("选择接收人")
                    .setOnSelectListener(list1 -> {
                        List<String> ids = new ArrayList<>();
                        for (EmployeeBean bean : list1) {
                            ids.add(bean.getEmployeeID());
                        }
                        mReq.setReceiver(TextUtils.join(",", ids));
                        updateReceiver();
                    })
                    .create();
        }
        mDialog.show();
    }

    @OnClick(R.id.cde_submit)
    public void submit() {
        inflateData();
        mPresenter.submit();
    }

    @OnTextChanged(R.id.cde_today_content)
    public void onTodayTextChanged(CharSequence s) {
        mTodayNum.setText(String.valueOf(200 - s.length()));
        updateEnable();
    }

    @OnTextChanged(R.id.cde_tomorrow_content)
    public void onTomorrowTextChanged(CharSequence s) {
        mTomorrowNum.setText(String.valueOf(200 - s.length()));
    }

    @OnTextChanged(R.id.cde_help_content)
    public void onHelpTextChanged(CharSequence s) {
        mHelpNum.setText(String.valueOf(200 - s.length()));
    }

    @OnTextChanged(R.id.cde_remark_content)
    public void onRemarkTextChanged(CharSequence s) {
        mRemarkNum.setText(String.valueOf(200 - s.length()));
    }

    @OnTextChanged(R.id.cde_receiver)
    public void updateEnable() {
        mSubmit.setEnabled(!TextUtils.isEmpty(mTodayContent.getText().toString().trim())
                && !TextUtils.isEmpty(mReceiver.getText().toString()));
    }

    @Override
    public void updateLastCommit(List<DailyBean> list) {
        if (!CommonUtils.isEmpty(list)) {
            DailyBean bean = list.get(0);
            Set<String> ids = new HashSet<>();
            Collections.addAll(ids, bean.getReceiver().split(",")); // id 去重
            mLastTime.setText(String.format("上次提交：%s", DateUtil.getReadableTime(bean.getCreateTime(), Constants.SLASH_YYYY_MM_DD_HH_MM)));
            mReq.setReceiver(TextUtils.join(",", ids));
            updateReceiver();
        }
    }

    @Override
    public void onBackPressed() {
        inflateData();
        Intent intent = new Intent();
        intent.putExtra("parcelable", mReq);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void inflateData() {
        mReq.setTodayWork(mTodayContent.getText().toString().trim());
        mReq.setTomorrowPlan(mTomorrowContent.getText().toString().trim());
        mReq.setNeedHelp(mHelpContent.getText().toString().trim());
        mReq.setRemark(mRemarkContent.getText().toString().trim());
        mReq.setImgurls(TextUtils.join(",", mRemarkUpload.getUploadImgUrls()));
    }

    @Override
    public void success() {
        showToast("日报提交成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void cacheReceiverList(List<EmployeeBean> list) {
        mList = list;
        updateReceiver();
    }

    private void updateReceiver() {
        if (!CommonUtils.isEmpty(mList) && mReq.getReceiver() != null) {
            List<String> names = new ArrayList<>();
            String[] array = mReq.getReceiver().split(",");
            for (String s : array) {
                for (EmployeeBean bean : mList) {
                    if (s.equals(bean.getEmployeeID())) {
                        names.add(bean.getEmployeeName());
                        break;
                    }
                }
            }
            mReceiver.setText(TextUtils.join(",", names));
        }
    }
}
