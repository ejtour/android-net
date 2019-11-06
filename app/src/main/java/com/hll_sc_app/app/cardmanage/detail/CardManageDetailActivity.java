package com.hll_sc_app.app.cardmanage.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cardmanage.recharge.CardManageRechargeActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cardmanage.CardManageBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_DETAIL)
public class CardManageDetailActivity extends BaseLoadActivity {
    @Autowired(name = "parcelable")
    CardManageBean mCardManageBean;
    @BindView(R.id.img_purchaser_url)
    GlideImageView mImgPurchaseUrl;
    @BindView(R.id.txt_purchaser_name)
    TextView mTxtPurchaserName;
    @BindView(R.id.txt_card_num)
    TextView mTxtCardNum;
    @BindView(R.id.img_card_status)
    ImageView mImgStatus;
    @BindView(R.id.txt_balance)
    TextView mTxtBalance;
    @BindView(R.id.txt_cash)
    TextView mTxtCash;
    @BindView(R.id.txt_gift)
    TextView mTxtGift;
    @BindView(R.id.txt_racharge)
    TextView mTxtRecharge;
    @BindView(R.id.group_remove_button)
    Group mGroupRemoveButtons;
    @BindView(R.id.txt_title_balance)
    TextView mTxtTitleBalance;
    @BindView(R.id.txt_title_cash)
    TextView mTxtTitleCash;
    @BindView(R.id.txt_title_gift)
    TextView mTxtTitleGift;

    private Unbinder unbinder;

    public static void start(CardManageBean bean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CARD_MANAGE_DETAIL, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_card_manage_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mImgPurchaseUrl.setImageURL(mCardManageBean.getPurchaserImgUrl());
        mTxtPurchaserName.setText(mCardManageBean.getPurchaserName());
        mTxtCardNum.setText("集团卡号: " + mCardManageBean.getCardNo());
        mImgStatus.setImageResource(mCardManageBean.getCardStatus() == 1 ? R.drawable.ic_card_status_useing :
                mCardManageBean.getCardStatus() == 2 ? R.drawable.ic_card_status_freeze :
                        R.drawable.ic_card_status_logout);

        if (mCardManageBean.getCardStatus() == 3) {
            mTxtBalance.setTextColor(Color.parseColor("#999999"));
            mTxtCash.setTextColor(Color.parseColor("#999999"));
            mTxtGift.setTextColor(Color.parseColor("#999999"));
            mTxtRecharge.setVisibility(View.GONE);
            mGroupRemoveButtons.setVisibility(View.GONE);
        }
        mTxtBalance.setText(CommonUtils.formatMoney(mCardManageBean.getBalance()));
        mTxtCash.setText(CommonUtils.formatMoney(mCardManageBean.getCashBalance()));
        mTxtGift.setText(CommonUtils.formatMoney(mCardManageBean.getGiftBalance()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick({R.id.txt_detail, R.id.txt_log, R.id.txt_freeze, R.id.txt_remove, R.id.txt_racharge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_detail:
                break;
            case R.id.txt_racharge:
                CardManageRechargeActivity.start(mCardManageBean);
                break;
            case R.id.txt_freeze:
                break;
            case R.id.txt_remove:
                break;
            case R.id.txt_log:
                break;
            default:
                break;
        }
    }


}
