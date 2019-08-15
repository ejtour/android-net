package com.hll_sc_app.app.invoice.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.invoice.InvoiceBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/15
 */
@Route(path = RouterConfig.INVOICE_DETAIL)
public class InvoiceDetailActivity extends BaseLoadActivity implements IInvoiceDetailContract.IInvoiceDetailView {
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
    private IInvoiceDetailContract.IInvoiceDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::toggleEdit);
    }

    private void initData() {
        mPresenter = InvoiceDetailPresenter.newInstance(mID);
        mPresenter.register(this);
        mPresenter.start();
    }

    @OnClick(R.id.aid_confirm)
    public void confirm() {
        RemarkDialog.newBuilder(this)
                .setHint("请填写发票号，多个号码请用逗号隔开")
                .setButtons("容我再想想", "确认开票", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive)
                        mPresenter.doAction(1, content, (double) mInvoiceAmount.getTag(), mInvoiceLicense.getImgUrl(), null);
                })
                .create()
                .show();
    }

    @OnClick(R.id.aid_reject)
    public void reject() {
        RemarkDialog.newBuilder(this)
                .setHint("请填写驳回原因(最多可输入50字)")
                .setMaxLength(50)
                .setButtons("容我再想想", "确认驳回", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive) mPresenter.doAction(2, null, 0, null, content);
                })
                .create()
                .show();
    }

    @OnClick({R.id.aid_add_records, R.id.aid_add_btn})
    public void addRecord(View view) {
        showToast("新增回款记录待添加");
    }

    @Override
    public void updateData(InvoiceBean bean) {
        boolean crm = !TextUtils.isEmpty(UserConfig.getSalesmanID());
        mInvoiceType.setText(bean.getInvoiceType() == 1 ? "普通发票" : "专用发票");
        mInvoiceAmount.setText(processMoney(bean.getInvoicePrice()));
        mInvoiceAmount.setTag(bean.getInvoicePrice());
        mInvoiceTitle.setText(bean.getInvoiceTitle());
        mAccount.setText(bean.getAccount());
        mBank.setText(bean.getOpenBank());
        mAddress.setText(bean.getAddress());
        mRecipient.setText(bean.getReceiver());
        mPhone.setText(bean.getTelephone());
        mRemark.setText(bean.getNote());
        mRelevanceOrder.setText(String.format("包含 %s 张订单", bean.getBillTotal()));
        reset();
        mTips.setVisibility(crm ? View.VISIBLE : View.GONE);
        if (bean.getInvoiceStatus() == 3) { // 已驳回
            mExtraInfoLabel.setText("驳回原因");
            if (crm) {
                mTips.setText(String.format("开票申请被驳回%s", TextUtils.isEmpty(bean.getRejectReason()) ? "" : "：" + bean.getRejectReason()));
            } else if (!TextUtils.isEmpty(bean.getRejectReason())) {
                mExtraInfoGroup.setVisibility(View.VISIBLE);
                mExtraInfo.setText(bean.getRejectReason());
            }
        } else if (bean.getInvoiceStatus() == 2) { // 已开票
            if (crm) {
                mTips.setText("供应商已开具发票，请查收");
            } else {
                mTitleBar.setRightBtnVisible(true);
                mTitleBar.setTag(false);
                mRecordsGroup.setVisibility(View.VISIBLE);
                mAddBtn.setVisibility(CommonUtils.isEmpty(bean.getReturnRecordList()) ? View.VISIBLE : View.GONE);
            }
            if (!TextUtils.isEmpty(bean.getInvoiceVoucher())) {
                mInvoiceLicenseGroup.setVisibility(View.VISIBLE);
                mInvoiceLicense.showImage(bean.getInvoiceVoucher());
            }
            mExtraInfoLabel.setText("发票号码");
            if (!TextUtils.isEmpty(bean.getInvoiceNO())) {
                mExtraInfoGroup.setVisibility(View.VISIBLE);
                mExtraInfo.setText(bean.getInvoiceNO());
            }
        } else {
            if (crm) {
                mTips.setText("开票申请已提交至供应商，请等待供应商处理");
            } else {
                mInvoiceLicense.setEditable(true);
                mInvoiceLicenseGroup.setVisibility(View.VISIBLE);
                mBottomGroup.setVisibility(View.VISIBLE);
            }
        }
        mRemark.getParent().requestLayout();
        mTitleBar.getParent().requestLayout();
    }

    private void reset() {
        mBottomGroup.setVisibility(View.GONE);
        mInvoiceLicenseGroup.setVisibility(View.GONE);
        mRecordsGroup.setVisibility(View.GONE);
        mExtraInfoGroup.setVisibility(View.GONE);
        mTitleBar.setRightBtnVisible(false);
        mInvoiceLicense.setEditable(false);
        mExtraInfo.setKeyListener(null);
        mTitleBar.setTag(null);
    }

    private void toggleEdit(View view) {
        if (((boolean) mTitleBar.getTag())) {
            mExtraInfo.clearFocus();
            mInvoiceLicense.setEditable(false);
            mTitleBar.setTag(false);
            mExtraInfo.setKeyListener(null);
            mTitleBar.setRightText("编辑");
            if (TextUtils.isEmpty(mInvoiceLicense.getImgUrl()))
                mInvoiceLicenseGroup.setVisibility(View.GONE);
            if (TextUtils.isEmpty(mExtraInfo.getText()))
                mExtraInfoGroup.setVisibility(View.GONE);
            showToast("发票信息修改待添加");
        } else {
            mExtraInfoGroup.setVisibility(View.VISIBLE);
            mInvoiceLicenseGroup.setVisibility(View.VISIBLE);
            mInvoiceLicense.setEditable(true);
            mTitleBar.setRightText("完成");
            mExtraInfo.setKeyListener(TextKeyListener.getInstance());
            mTitleBar.setTag(true);
        }
        mRemark.getParent().requestLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.imageUpload(new File(list.get(0)));
        }
    }

    private SpannableString processMoney(double money) {
        String source = String.format("¥%s", CommonUtils.formatMoney(money));
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(1.23f), 1, source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void actionSuccess() {
        mPresenter.start();
    }

    @Override
    public void settleSuccess() {

    }

    @Override
    public void showImage(String url) {
        mInvoiceLicense.showImage(url);
    }

    @OnClick(R.id.aid_relevance_order)
    public void viewRelevanceOrder() {
        showToast("查看关联订单待添加");
    }
}
