package com.hll_sc_app.app.report.produce.input.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
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
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */
@Route(path = RouterConfig.REPORT_PRODUCE_INPUT_DETAIL)
public class ProduceInputDetailActivity extends BaseLoadActivity implements IProduceInputDetailContract.IProduceInputDetailView {
    public static final int REQ_CODE = 0x961;

    public static void start(Activity context, ProduceDetailBean bean) {
        RouterUtil.goToActivity(RouterConfig.REPORT_PRODUCE_INPUT_DETAIL, context, REQ_CODE, bean);
    }

    @BindView(R.id.pid_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.pid_company)
    TextView mCompany;
    @BindView(R.id.pid_standard_pick_num)
    EditText mStandardPickNum;
    @BindView(R.id.pid_standard_pick_time)
    EditText mStandardPickTime;
    @BindView(R.id.pid_vegetable_pick_num)
    EditText mVegetablePickNum;
    @BindView(R.id.pid_vegetable_pick_time)
    EditText mVegetablePickTime;
    @BindView(R.id.pid_vegetable_pack_num)
    EditText mVegetablePackNum;
    @BindView(R.id.pid_vegetable_pack_time)
    EditText mVegetablePackTime;
    @Autowired(name = "parcelable")
    ProduceDetailBean mBean;
    private List<ManHourBean> mBeanList;
    private SingleSelectionDialog mDialog;
    private IProduceInputDetailContract.IProduceInputDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_produce_input_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = ProduceInputDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::save);
    }

    private void save(View view) {
        mBean.setStandardSortNum(string2Int(mStandardPickNum.getText()));
        mBean.setStandardSortHours(string2Double(mStandardPickTime.getText()));
        mBean.setVegetablesSortNum(string2Int(mVegetablePickNum.getText()));
        mBean.setVegetablesSortHours(string2Double(mVegetablePickTime.getText()));
        mBean.setVegetablesPackNum(string2Int(mVegetablePackNum.getText()));
        mBean.setVegetablesPackHours(string2Double(mVegetablePackTime.getText()));
        showToast("生产费用计算待添加");
        mBean.setTotalCost(Math.random() * 150 + 50);
        Intent intent = new Intent();
        intent.putExtra("parcelable", mBean);
        setResult(RESULT_OK, intent);
        finish();
    }

    private int string2Int(CharSequence s) {
        return TextUtils.isEmpty(s) ? 0 : Integer.parseInt(s.toString());
    }

    private double string2Double(CharSequence s) {
        return TextUtils.isEmpty(s) ? 0 : Double.parseDouble(s.toString());
    }

    @OnClick({R.id.pid_company_label, R.id.pid_company})
    public void selectCompany() {
        if (CommonUtils.isEmpty(mBeanList)) {
            mPresenter.start();
            return;
        }
        showDialog();
    }

    private void showDialog() {
        if (mDialog == null) {
            mDialog = SingleSelectionDialog.newBuilder(this, ManHourBean::getCoopGroupName)
                    .setTitleText("选择合作公司")
                    .refreshList(mBeanList)
                    .setOnSelectListener(manHourBean -> {
                        mBean.setCoopGroupName(manHourBean.getCoopGroupName());
                        mCompany.setText(manHourBean.getCoopGroupName());
                    })
                    .create();
        }
        mDialog.show();
    }

    @OnTextChanged(value = {R.id.pid_vegetable_pick_time, R.id.pid_standard_pick_time, R.id.pid_vegetable_pack_time},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable s) {
        if (s.toString().startsWith("."))
            s.insert(0, "0");
        if (!CommonUtils.checkMoneyNum(s.toString()) && s.length() > 1) {
            s.delete(s.length() - 1, s.length());
        }
    }

    @Override
    public void setCompanyNameData(List<ManHourBean> beanList) {
        mBeanList = beanList;
    }
}
