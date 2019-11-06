package com.hll_sc_app.app.invoice.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.detail.order.RelevanceOrderActivity;
import com.hll_sc_app.app.invoice.detail.record.ReturnRecordActivity;
import com.hll_sc_app.app.invoice.detail.shop.RelevanceShopActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.invoice.InvoiceBean;
import com.hll_sc_app.bean.invoice.ReturnRecordBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import butterknife.BindDrawable;
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
    @BindView(R.id.aid_relevance_shop)
    TextView mRelevanceShop;
    @BindView(R.id.aid_relevance_order)
    TextView mRelevanceOrder;
    @BindView(R.id.aid_extra_info_label)
    TextView mExtraInfoLabel;
    @BindView(R.id.aid_extra_info)
    EditText mExtraInfo;
    @BindView(R.id.aid_invoice_license)
    ImgUploadBlock mInvoiceLicense;
    @BindView(R.id.aid_identifier)
    TextView mIdentifier;
    @BindView(R.id.aid_group_name)
    TextView mGroupName;
    @BindView(R.id.aid_apply_date)
    TextView mApplyDate;
    @BindView(R.id.aid_business_date)
    TextView mBusinessDate;
    private TextView mAddBtn;
    @BindView(R.id.aid_list_view)
    RecyclerView mListView;
    @BindView(R.id.aid_extra_info_group)
    Group mExtraInfoGroup;
    @BindView(R.id.aid_invoice_license_group)
    Group mInvoiceLicenseGroup;
    @BindView(R.id.aid_records_group)
    Group mRecordsGroup;
    @BindView(R.id.aid_identifier_group)
    Group mIdentifierGroup;
    @BindDrawable(R.drawable.ic_arrow_gray)
    Drawable mArrow;
    @Autowired(name = "object0")
    String mID;
    private IInvoiceDetailContract.IInvoiceDetailPresenter mPresenter;
    private ReturnRecordAdapter mAdapter;
    private boolean mHasChanged;

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
        mListView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new ReturnRecordAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ReturnRecordBean item = mAdapter.getItem(position);
            if (item == null) return;
            switch (view.getId()) {
                case R.id.irr_edit:
                    ReturnRecordActivity.start(this, item);
                    break;
                case R.id.irr_confirm:
                    settleConfirm(item.getId());
                    break;
            }
        });
        createAddBtn();
        mAdapter.setEmptyView(mAddBtn);
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0);
        mListView.addItemDecoration(decor);
        mInvoiceAmount.setTag(0);
        mRelevanceShop.setClickable(false);
        mArrow.setBounds(0, 0, mArrow.getIntrinsicWidth(), mArrow.getIntrinsicHeight());
    }

    private void createAddBtn() {
        mAddBtn = new TextView(this);
        mAddBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(58)));
        mAddBtn.setGravity(Gravity.CENTER);
        mAddBtn.setText("点击新增，添加回款记录");
        mAddBtn.setTextColor(ContextCompat.getColor(this, R.color.color_aeaeae));
        mAddBtn.setTextSize(13);
        mAddBtn.setOnClickListener(this::addRecord);
    }

    private void settleConfirm(String id) {
        SuccessDialog.newBuilder(this)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setMessageTitle("确认将关联订单结算么")
                .setMessage("确认后将根据回款金额处理未结算订单 完成后回款记录将不允许再编辑")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) mPresenter.settle(id);
                }, "我再看看", "确认结算")
                .create().show();
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
                .setHint("请填写驳回原因（最多可输入50字）")
                .setMaxLength(50)
                .setButtons("容我再想想", "确认驳回", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive) mPresenter.doAction(2, null, 0, null, content);
                })
                .create()
                .show();
    }

    @OnClick({R.id.aid_add_records})
    public void addRecord(View view) {
        ReturnRecordActivity.start(this, mID);
    }

    @Override
    public void updateData(InvoiceBean bean) {
        boolean crm = UserConfig.crm();
        updateBaseInfo(bean, crm);
        reset();
        if (bean.getInvoiceStatus() == 3) { // 已驳回
            updateRejectData(bean, crm);
        } else if (bean.getInvoiceStatus() == 2) { // 已开票
            updateMadeInvoiceData(bean, crm);
        } else { // 已提交/未开票
            updateNotInvoiceData(crm);
        }
        mRemark.getParent().requestLayout();
        mTitleBar.getParent().requestLayout();
    }

    private void updateNotInvoiceData(boolean crm) {
        if (crm) {
            mTips.setText("开票申请已提交至供应商，请等待供应商处理");
        } else {
            mInvoiceLicense.setEditable(true);
            mInvoiceLicenseGroup.setVisibility(View.VISIBLE);
            mBottomGroup.setVisibility(View.VISIBLE);
        }
    }

    private void updateMadeInvoiceData(InvoiceBean bean, boolean crm) {
        if (crm) {
            mTips.setText("供应商已开具发票，请查收");
        } else {
            if (!isInEditMode()) {
                mTitleBar.setRightBtnVisible(true);
                mTitleBar.setTag(false);
            }
            mRecordsGroup.setVisibility(View.VISIBLE);
            mAdapter.setNewData(bean.getReturnRecordList());
        }
        if (!TextUtils.isEmpty(bean.getInvoiceVoucher()) && !isInEditMode()) {
            mInvoiceLicenseGroup.setVisibility(View.VISIBLE);
            mInvoiceLicense.showImage(bean.getInvoiceVoucher());
        }
        if (!isInEditMode()) {
            mExtraInfoLabel.setText("发票号码");
            mExtraInfoGroup.setVisibility(View.VISIBLE);
            mExtraInfo.setText(!TextUtils.isEmpty(bean.getInvoiceNO()) ? bean.getInvoiceNO() : "无");
        }
    }

    private void updateBaseInfo(InvoiceBean bean, boolean crm) {
        mGroupName.setText(bean.getPurchaserName());
        mApplyDate.setText(DateUtil.getReadableTime(bean.getCreateTime(), Constants.SLASH_YYYY_MM_DD));
        if (CommonUtils.getDouble(bean.getBusinessBeginDate()) == 0 || CommonUtils.getDouble(bean.getBusinessEndDate()) == 0) {
            mBusinessDate.setText("0 - 0");
        } else {
            mBusinessDate.setText(String.format("%s - %s", DateUtil.getReadableTime(bean.getBusinessBeginDate(), Constants.SLASH_YYYY_MM_DD),
                    DateUtil.getReadableTime(bean.getBusinessEndDate(), Constants.SLASH_YYYY_MM_DD)));
        }
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
        mRelevanceOrder.setText(String.format("包含 %s 个订单", bean.getBillTotal()));
        if (bean.getShopTotal() > 1) {
            mRelevanceShop.setClickable(true);
            mRelevanceShop.setText(String.format("包含 %s 个门店", bean.getShopTotal()));
            mRelevanceShop.setCompoundDrawables(null, null, mArrow, null);
        } else {
            mRelevanceShop.setClickable(false);
            mRelevanceShop.setText(bean.getPurchaserShopName());
            mRelevanceShop.setCompoundDrawables(null, null, null, null);
        }
        mTips.setVisibility(crm ? View.VISIBLE : View.GONE);
        mIdentifierGroup.setVisibility(bean.getTitleType() == 1 ? View.VISIBLE : View.GONE);
        mIdentifier.setText(bean.getTaxpayerNum());
    }

    private void updateRejectData(InvoiceBean bean, boolean crm) {
        mExtraInfoLabel.setText("驳回原因");
        if (crm) {
            mTips.setText(String.format("开票申请被驳回%s", TextUtils.isEmpty(bean.getRejectReason()) ? "" : "：" + bean.getRejectReason()));
        } else if (!TextUtils.isEmpty(bean.getRejectReason())) {
            mExtraInfoGroup.setVisibility(View.VISIBLE);
            mExtraInfo.setText(bean.getRejectReason());
        }
    }

    private void reset() {
        mBottomGroup.setVisibility(View.GONE);
        mRecordsGroup.setVisibility(View.GONE);
        if (!isInEditMode()) {
            mInvoiceLicense.setEditable(false);
            mInvoiceLicenseGroup.setVisibility(View.GONE);
            mExtraInfoGroup.setVisibility(View.GONE);
            mExtraInfo.setKeyListener(null);
            mTitleBar.setRightBtnVisible(false);
            mTitleBar.setTag(null);
        }
    }

    private boolean isInEditMode() {
        return mTitleBar.getTag() != null && (boolean) mTitleBar.getTag();
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
            mPresenter.modifyInvoiceInfo(mExtraInfo.getText().toString(), mInvoiceLicense.getImgUrl());
        } else {
            mExtraInfoGroup.setVisibility(View.VISIBLE);
            mInvoiceLicenseGroup.setVisibility(View.VISIBLE);
            mInvoiceLicense.setEditable(true);
            mTitleBar.setRightText("确定");
            mExtraInfo.setKeyListener(TextKeyListener.getInstance());
            mTitleBar.setTag(true);
        }
        mRemark.getParent().requestLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
                List<String> list = Matisse.obtainPathResult(data);
                if (!CommonUtils.isEmpty(list)) mPresenter.imageUpload(new File(list.get(0)));
            } else if (requestCode == ReturnRecordActivity.REQ_CODE) {
                mPresenter.start();
            }
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
        mHasChanged = true;
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void showImage(String url) {
        mInvoiceLicense.showImage(url);
    }

    @OnClick(R.id.aid_relevance_order)
    public void viewRelevanceOrder() {
        RelevanceOrderActivity.start(mID, (double) mInvoiceAmount.getTag());
    }

    @OnClick(R.id.aid_relevance_shop)
    public void onViewClicked() {
        RelevanceShopActivity.start(mID);
    }
}
