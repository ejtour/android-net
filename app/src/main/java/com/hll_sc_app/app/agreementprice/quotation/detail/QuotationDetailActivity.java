package com.hll_sc_app.app.agreementprice.quotation.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.agreementprice.quotation.add.QuotationAddActivity;
import com.hll_sc_app.app.simple.SimpleListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;
import com.hll_sc_app.bean.event.RefreshQuotationList;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hll_sc_app.app.agreementprice.quotation.QuotationListAdapter.getPriceDate;

/**
 * 协议价管理-报价单详情
 *
 * @author zhuyingsong
 * @date 2019/7/8
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_DETAIL, extras = Constant.LOGIN_EXTRA)
public class QuotationDetailActivity extends BaseLoadActivity implements QuotationDetailContract.IPurchaseView {
    @Autowired(name = "parcelable", required = true)
    QuotationBean mBean;
    @BindView(R.id.tb_title)
    TitleBar mTitleBar;
    @BindView(R.id.txt_reason)
    TextView mReason;
    @BindView(R.id.txt_isWarehouse)
    TextView mTxtIsWarehouse;
    @BindView(R.id.txt_select_purchaser)
    TextView mTxtSelectPurchaser;
    @BindView(R.id.txt_priceDate)
    TextView mTxtPriceDate;
    @BindView(R.id.txt_templateID)
    TextView mTxtTemplateId;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_disable)
    TextView mTxtDisable;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.include_title)
    ConstraintLayout mListTitle;
    private QuotationDetailPresenter mPresenter;
    private QuotationAddActivity.GoodsListAdapter mAdapter;
    private QuotationDetailResp mResp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresenter = QuotationDetailPresenter.newInstance(mBean.getId(), mBean.getBillNo());
        mPresenter.register(this);
        mPresenter.start();
        showContent();
    }

    private void showContent() {
        mTxtIsWarehouse.setText(TextUtils.equals("1", mBean.getIsWarehouse()) ?
                QuotationAddActivity.STRING_WARE_HOUSE : QuotationAddActivity.STRING_SELF_SUPPORT);
        int num = CommonUtils.getInt(mBean.getShopIDNum());
        if (num > 1) {
            mTxtSelectPurchaser.setClickable(true);
            mTxtSelectPurchaser.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_gray, 0);
            mTxtSelectPurchaser.setText(String.format("%s - %s 家门店", mBean.getPurchaserName(), CommonUtils.formatNumber(num)));
        } else {
            mTxtSelectPurchaser.setText(String.format("%s - %s", mBean.getPurchaserName(), mBean.getShopName()));
            mTxtSelectPurchaser.setClickable(false);
        }
        mTxtPriceDate.setText(getPriceDate(mBean.getPriceStartDate(), mBean.getPriceEndDate()));
        mTxtTemplateId.setText(TextUtils.isEmpty(mBean.getTemplateName()) ? "无" : mBean.getTemplateName());
        mListTitle.findViewById(R.id.group_delete).setVisibility(View.GONE);
        ((TextView) mListTitle.findViewById(R.id.txt_price)).setText("协议价");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuotationAddActivity.GoodsListAdapter(false);
        mRecyclerView.setAdapter(mAdapter);
        mTitleBar.setRightBtnVisible(CommonUtils.getInt(mBean.getSource()) == 0);
        mTitleBar.setRightBtnClick(view -> toCopy());
        if (!TextUtils.isEmpty(mBean.getReason()) && mBean.getBillStatus() == QuotationBean.BILL_STATUS_REJECT) {
            mReason.setVisibility(View.VISIBLE);
            mReason.setText(String.format("驳回原因：%s", mBean.getReason()));
        }
    }

    @Override
    public void showGoodsDetail(QuotationDetailResp resp) {
        mResp = resp;
        mAdapter.setNewData(resp.getRecords());
        mListTitle.setVisibility(mAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
        QuotationDetailBean bean = mAdapter.getItem(0);
        if (bean != null && (bean.getBillStatus() == QuotationBean.BILL_STATUS_AUDIT)) {
            mLlBottom.setVisibility(View.VISIBLE);
        } else {
            mLlBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void disableQuotationSuccess() {
        EventBus.getDefault().post(new RefreshQuotationList());
        showToast("停用报价单成功");
        finish();
    }

    @OnClick(R.id.txt_disable)
    public void showTipsDialog() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确认要停用报价单么")
                .setMessage("停用将对协议中的采购商恢复原价售卖\n一旦停用不可再启用，请慎重操作\n停用后的记录可在历史报价单中查看")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        mPresenter.disableQuotation();
                    }
                }, "我再看看", "确认停用").create().show();
    }

    @OnClick(R.id.txt_select_purchaser)
    public void lookOverShopList() {
        if (mResp == null) return;
        ArrayList<String> list = new ArrayList<>();
        for (ShipperShopResp.ShopBean bean : mResp.getShops()) {
            list.add(bean.getShopName());
        }
        SimpleListActivity.start(mBean.getPurchaserName(), list);
    }

    /**
     * 复制
     */
    private void toCopy() {
        QuotationReq req = new QuotationReq();
        req.setList(mAdapter.getData());
        req.setQuotation(mBean);
        RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD, this, req);
    }
}
