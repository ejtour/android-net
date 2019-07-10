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
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;
import com.hll_sc_app.bean.event.RefreshQuotationList;

import org.greenrobot.eventbus.EventBus;

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
        mTxtSelectPurchaser.setText(String.format("%s-%s", mBean.getPurchaserName(), mBean.getShopName()));
        mTxtPriceDate.setText(getPriceDate(mBean.getPriceStartDate(), mBean.getPriceEndDate()));
        mTxtTemplateId.setText(TextUtils.isEmpty(mBean.getTemplateName()) ? "无" : mBean.getTemplateName());
        mListTitle.findViewById(R.id.group_delete).setVisibility(View.GONE);
        ((TextView) mListTitle.findViewById(R.id.txt_price)).setText("协议价");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuotationAddActivity.GoodsListAdapter(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showGoodsDetail(QuotationDetailResp resp) {
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

    @OnClick({R.id.img_close, R.id.txt_disable, R.id.txt_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_disable:
                mPresenter.disableQuotation();
                break;
            case R.id.txt_copy:
                toCopy();
                break;
            default:
                break;
        }
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
