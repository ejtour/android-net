package com.hll_sc_app.app.wallet.details.show;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.wallet.details.DetailsRecord;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/7
 */
@Route(path = RouterConfig.WALLET_DETAILS_SHOW)
public class DetailsShowActivity extends BaseLoadActivity {

    /**
     * 金额描述
     */
    @BindView(R.id.wds_money_label)
    TextView mMoneyLabel;

    /**
     * 金额
     */
    @BindView(R.id.wds_money)
    TextView mMoney;
    /**
     * 交易流水号
     */
    @BindView(R.id.wds_content_water)
    TextView mTradeWater;
    /**
     * 支付单号
     */
    @BindView(R.id.wds_content_pay)
    TextView mOrder;
    /**
     * 交易类型
     */
    @BindView(R.id.wds_content_type)
    TextView mType;
    /**
     * 业务单号
     */
    @BindView(R.id.wds_content_business)
    TextView mBusinessNo;
    /**
     * 付款集团
     */
    @BindView(R.id.wds_content_purchaser)
    TextView mPurchaser;
    /**
     * 付款门店
     */
    @BindView(R.id.wds_content_shop)
    TextView mShop;
    /**
     * 备注
     */
    @BindView(R.id.wds_content_remark)
    TextView mRemark;
    /**
     * 交易余额
     */
    @BindView(R.id.wds_content_balance)
    TextView mBalance;
    /**
     * 交易费用
     */
    @BindView(R.id.wds_fee)
    TextView mFee;
    /**
     * 交易时间
     */
    @BindView(R.id.wds_time)
    TextView mTime;
    @Autowired(name = "parcelable", required = true)
    DetailsRecord mDetailsRecord;

    /**
     * @param record 明细详情
     */
    public static void start(DetailsRecord record) {
        RouterUtil.goToActivity(RouterConfig.WALLET_DETAILS_SHOW, record);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_wallet_details_show);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // 金额
        mMoney.setText((mDetailsRecord.getDirection() + CommonUtils.formatMoney(mDetailsRecord.getSettleAccount())));
        // 交易流水
        mTradeWater.setText(mDetailsRecord.getTradeOrderNo());
        // 单号
        mOrder.setText(mDetailsRecord.getPayOrderKey());
        // 交易类型
        mType.setText(mDetailsRecord.getTransTypeDesc());
        mMoneyLabel.setText(String.format("%s金额", mDetailsRecord.getTransTypeDesc()));
        // 交易时间
        mTime.setText(DateUtil.getReadableTime(mDetailsRecord.getAccountTime()));
        // 交易费用
        mFee.setText(String.format("¥%s", mDetailsRecord.getTransSalesCommission()));
        // 业务单号
        mBusinessNo.setText(mDetailsRecord.getBusinessNo());
        // 付款集团
        mPurchaser.setText(mDetailsRecord.getPurchaserName());
        // 付款门店
        mShop.setText(mDetailsRecord.getShopName());
        // 备注
        mRemark.setText(mDetailsRecord.getExplains());
        //交易余额
        mBalance.setText(String.format("¥%s", mDetailsRecord.getTransAfterBalance()));
    }
}
