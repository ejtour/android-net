package com.hll_sc_app.app.invoice.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/15
 */
@Route(path = RouterConfig.INVOICE_DETAIL)
public class InvoiceDetailActivity extends BaseLoadActivity {
    public static final int REQ_CODE = 0x722;

    /**
     * @param id 发票 id
     */
    public static void start(Activity context, @NonNull String id) {
        Object[] args = {id};
        RouterUtil.goToActivity(RouterConfig.INVOICE_DETAIL, context, REQ_CODE, args);
    }

    @BindView(R.id.aid_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aid_bottom_group)
    Group mBottomGroup;
    @BindView(R.id.aid_tips)
    TextView mTips;
    @BindView(R.id.aid_invoice_type)
    TextView mInvoiceType;
    @BindView(R.id.aid_invoice_amount)
    TextView mInvoiceAmount;
    @BindView(R.id.aid_invoice_title)
    TextView mInvoiceTitle;
    @BindView(R.id.aid_account)
    TextView mAccount;
    @BindView(R.id.aid_bank)
    TextView mBank;
    @BindView(R.id.aid_address)
    TextView mAddress;
    @BindView(R.id.aid_recipient)
    TextView mRecipient;
    @BindView(R.id.aid_phone)
    TextView mPhone;
    @BindView(R.id.aid_remark)
    TextView mRemark;
    @BindView(R.id.aid_relevance_order)
    TextView mRelevanceOrder;
    @BindView(R.id.aid_extra_info_label)
    TextView mExtraInfoLabel;
    @BindView(R.id.aid_extra_info)
    EditText mExtraInfo;
    @BindView(R.id.aid_invoice_license)
    ImgUploadBlock mInvoiceLicense;
    @BindView(R.id.aid_add_btn)
    TextView mAddBtn;
    @BindView(R.id.aid_list_view)
    RecyclerView mListView;
    @BindView(R.id.aid_extra_info_group)
    Group mExtraInfoGroup;
    @BindView(R.id.aid_invoice_license_group)
    Group mInvoiceLicenseGroup;
    @BindView(R.id.aid_records_group)
    Group mRecordsGroup;
    @Autowired(name = "object0")
    String mID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
    }

    @OnClick(R.id.aid_confirm)
    public void confirm() {
    }

    @OnClick(R.id.aid_reject)
    public void reject() {
    }

    @OnClick({R.id.aid_add_records, R.id.aid_add_btn})
    public void addRecord(View view) {
    }
}
