package com.hll_sc_app.app.contractmanage.detail;

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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.contractmanage.selectpurchaser.SelectPurchaserListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
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
 * 合同管理-详情
 */
@Route(path = RouterConfig.ACTIVITY_CONTRACT_MANAGE_DETAIL)
public class ContractManageDetailActivity extends BaseLoadActivity implements IContractManageDetailContract.IView {

    @BindView(R.id.txt_name)
    TextView mTxtName;
    @BindView(R.id.txt_status)
    TextView mTxtStatus;
    @BindView(R.id.txt_no)
    TextView mTxtNo;
    @BindView(R.id.txt_group_name)
    TextView mTxtGroupName;
    @BindView(R.id.txt_time_span)
    TextView mTxtTimeSpan;
    @BindView(R.id.txt_person)
    TextView mTxtPerson;
    @BindView(R.id.txt_time)
    TextView mTxtTime;
    @BindView(R.id.txt_left_days)
    TextView mTxtLeftDays;
    @BindView(R.id.txt_btn_del)
    TextView mTxtBtnDel;
    @BindView(R.id.txt_btn_mdf)
    TextView mTxtBtnMdf;
    @BindView(R.id.txt_btn_check)
    TextView mTxtBtnCheck;
    @Autowired(name = "object0")
    String mId;

    private Unbinder unbinder;
    private IContractManageDetailContract.IPresent mPresent;

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CONTRACT_MANAGE_DETAIL, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        mPresent = ContractManageDetailPresent.newInstance();
        mPresent.register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }



}
