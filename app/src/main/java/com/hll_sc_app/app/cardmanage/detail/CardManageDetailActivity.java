package com.hll_sc_app.app.cardmanage.detail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cardmanage.cardlog.CardLogActivity;
import com.hll_sc_app.app.cardmanage.recharge.CardManageRechargeActivity;
import com.hll_sc_app.app.cardmanage.transactiondetail.TransactionListActivity;
import com.hll_sc_app.app.staffmanage.detail.depart.InputDialog;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cardmanage.CardManageBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_DETAIL)
public class CardManageDetailActivity extends BaseLoadActivity implements ICardManageDetailContract.IView {
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
    @BindView(R.id.txt_freeze)
    TextView mTxtFreeze;
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    private Unbinder unbinder;

    private InputDialog mFreezeDialog;
    private InputDialog mUnFreezeDialog;
    private InputDialog mRemoveDialog;

    private boolean isNeedFreshList = false;
    private ICardManageDetailContract.IPresent mPresent;
    public static void start(CardManageBean bean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CARD_MANAGE_DETAIL, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage_detail);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mPresent = CardManageDetailPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    private void initView() {
        mTitle.setLeftBtnClick(v -> {
            if (isNeedFreshList) {
                refreshList();
            } else {
                finish();
            }
        });
        mImgPurchaseUrl.setImageURL(mCardManageBean.getPurchaserImgUrl());
        mTxtPurchaserName.setText(mCardManageBean.getPurchaserName());
        mTxtCardNum.setText("集团卡号: " + mCardManageBean.getCardNo());
        mImgStatus.setImageResource(mCardManageBean.getCardStatus() == 1 ? R.drawable.ic_card_status_useing :
                mCardManageBean.getCardStatus() == 2 ? R.drawable.ic_card_status_freeze :
                        R.drawable.ic_card_status_logout);

        //1-启用 2-冻结 3-注销
        if (mCardManageBean.getCardStatus() == 3) {
            mTxtBalance.setTextColor(Color.parseColor("#999999"));
            mTxtCash.setTextColor(Color.parseColor("#999999"));
            mTxtGift.setTextColor(Color.parseColor("#999999"));
            mTxtRecharge.setVisibility(View.GONE);
            mGroupRemoveButtons.setVisibility(View.GONE);
        } else if (mCardManageBean.getCardStatus() == 2) {
            mTxtFreeze.setText("卡解冻");
        } else {
            mTxtFreeze.setText("卡冻结");
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

    private void refreshList() {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_CARD_MANAGE_LIST)
                .setProvider(new LoginInterceptor())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .navigation(CardManageDetailActivity.this);
    }

    @OnClick({R.id.txt_detail, R.id.txt_log, R.id.txt_freeze, R.id.txt_remove, R.id.txt_racharge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_detail:
                TransactionListActivity.start(mCardManageBean);
                break;
            case R.id.txt_racharge:
                CardManageRechargeActivity.start(mCardManageBean);
                break;
            case R.id.txt_freeze:
                InputDialog dialog = mCardManageBean.getCardStatus() == 1 ? mFreezeDialog : mUnFreezeDialog;
                if (dialog == null) {
                    dialog = new InputDialog(this, new InputDialog.InputDialogConfig() {
                        @Override
                        public CharSequence getCharSequenceTitle() {
                            SpannableString title = new SpannableString(String.format("   您确定要%s该储值卡嘛？\n        %s"
                                    , mCardManageBean.getCardStatus() == 1 ? "冻结" : "解冻"
                                    , mCardManageBean.getCardStatus() == 1 ? "冻结期间将无法使用储值卡进行订单支付" : "解冻后客户即可恢复使用"));
                            Drawable d = ContextCompat.getDrawable(CardManageDetailActivity.this, R.drawable.ic_exclamation_circle_yellow);
                            int dSize = UIUtils.dip2px(16);
                            d.setBounds(0, 0, dSize, dSize);
                            title.setSpan(new ImageSpan(d, DynamicDrawableSpan.ALIGN_BASELINE),
                                    0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            title.setSpan(new RelativeSizeSpan(1.5f), 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            title.setSpan(new ForegroundColorSpan(Color.parseColor("#222222")), 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            title.setSpan(new RelativeSizeSpan(0.8f), title.length() - (mCardManageBean.getCardStatus() == 1 ? 18 : 12), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            title.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), title.length() - (mCardManageBean.getCardStatus() == 1 ? 18 : 12), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            return title;
                        }

                        @Override
                        public String getHint() {
                            return "备注";
                        }

                        @Override
                        public int getMaxInputLength() {
                            return 50;
                        }

                        @Override
                        public void click(BaseDialog dialog, String content, int index) {
                            dialog.dismiss();
                            if (index == 1) {
                                mPresent.changeCardStatus(mCardManageBean.getCardStatus() == 1 ? 2 : 1, content);
                            }
                        }

                        @Override
                        public String getRightButtonText() {
                            return mCardManageBean.getCardStatus() == 1 ? "确认冻结" : "确认解冻";
                        }

                        @Override
                        public int getEdtBackgroundRes() {
                            return R.drawable.bg_gray_stroke_radius_5;
                        }

                        @Override
                        public boolean isEdtFill() {
                            return true;
                        }

                        @Override
                        public void setTitleGravity(ConstraintLayout.LayoutParams layoutParams) {
                            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                            layoutParams.endToEnd = -1;
                        }
                    });
                }
                dialog.show();
                break;
            case R.id.txt_remove:
                if (mRemoveDialog == null) {
                    mRemoveDialog = new InputDialog(this, new InputDialog.InputDialogConfig() {
                        @Override
                        public CharSequence getCharSequenceTitle() {
                            SpannableString title = new SpannableString("   您确定要注销该储值卡嘛？\n        注销后将无法再使用储值卡进行订单支付");
                            Drawable d = ContextCompat.getDrawable(CardManageDetailActivity.this, R.drawable.ic_exclamation_circle_yellow);
                            int dSize = UIUtils.dip2px(16);
                            d.setBounds(0, 0, dSize, dSize);
                            title.setSpan(new ImageSpan(d, DynamicDrawableSpan.ALIGN_BASELINE),
                                    0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            title.setSpan(new RelativeSizeSpan(1.5f), 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            title.setSpan(new ForegroundColorSpan(Color.parseColor("#222222")), 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            title.setSpan(new ForegroundColorSpan(Color.parseColor("#ED5655")), 7, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            title.setSpan(new RelativeSizeSpan(0.8f), title.length() - 18, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            title.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), title.length() - 18, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            return title;
                        }

                        @Override
                        public String getHint() {
                            return "备注";
                        }

                        @Override
                        public int getMaxInputLength() {
                            return 50;
                        }

                        @Override
                        public void click(BaseDialog dialog, String content, int index) {
                            dialog.dismiss();
                            if (index == 1) {
                                mPresent.changeCardStatus(3, content);
                            }
                        }

                        @Override
                        public String getRightButtonText() {
                            return "确认注销";
                        }

                        @Override
                        public int getEdtBackgroundRes() {
                            return R.drawable.bg_gray_stroke_radius_5;
                        }

                        @Override
                        public boolean isEdtFill() {
                            return true;
                        }

                        @Override
                        public void setTitleGravity(ConstraintLayout.LayoutParams layoutParams) {
                            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                            layoutParams.endToEnd = -1;
                        }
                    });
                }
                mRemoveDialog.show();
                break;
            case R.id.txt_log:
                CardLogActivity.start(mCardManageBean.getCardNo());
                break;
            default:
                break;
        }
    }

    @Override
    public CardManageBean getCardBean() {
        return mCardManageBean;
    }

    @Override
    public void changeSuccess(int status) {
        isNeedFreshList = true;
        mCardManageBean.setCardStatus(status);
        initView();
    }

    @Override
    public void onBackPressed() {
        if (isNeedFreshList) {
            refreshList();
        } else {
            super.onBackPressed();
        }
    }
}
