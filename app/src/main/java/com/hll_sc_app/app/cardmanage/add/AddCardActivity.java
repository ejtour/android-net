package com.hll_sc_app.app.cardmanage.add;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.api.CardManageService;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.uber.autodispose.AutoDispose.autoDisposable;

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_ADD_CARD, extras = Constant.LOGIN_EXTRA)
public class AddCardActivity extends BaseLoadActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.txt_purchaser_name)
    TextView mTxtPurchaserName;
    @BindView(R.id.radio_group_type)
    RadioGroup mRadioGroup;
    @BindView(R.id.radio_cash)
    RadioButton mRadioCash;
    @BindView(R.id.radio_gift)
    RadioButton mRadioGift;
    @BindView(R.id.radio_proportion)
    RadioButton mRadioProportion;
    @BindView(R.id.txt_add_card)
    TextView mTxtAdd;

    @Autowired(name = "parcelable")
    PurchaserBean mPurchaserBean;
    @Autowired(name = "source")
    String mSource;
    private Unbinder unbinder;

    public static void start(String source, PurchaserBean purchaserBean) {
        ARouter.getInstance()
                .build(RouterConfig.ACTIVITY_CARD_MANAGE_ADD_CARD)
                .withParcelable("parcelable", purchaserBean)
                .withString("source", source)
                .setProvider(new LoginInterceptor())
                .navigation();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage_add);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mTxtPurchaserName.setText(mPurchaserBean.getPurchaserName());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.txt_purchaser_name, R.id.txt_add_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_purchaser_name:
                finish();
                break;
            case R.id.txt_add_card:
                CardManageService.INSTANCE
                        .openCard(BaseMapReq.newBuilder()
                                .put("consumptionModel", getConsumptionModel())
                                .put("groupID", GreenDaoUtils.getUser().getGroupID())
                                .put("purchaserID", mPurchaserBean.getPurchaserID())
                                .put("purchaserName", mTxtPurchaserName.getText().toString())
                                .create())
                        .compose(ApiScheduler.getObservableScheduler())
                        .map(new Precondition<>())
                        .doOnSubscribe(disposable -> {
                            showLoading();
                        })
                        .doFinally(() -> hideLoading())
                        .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
                        .subscribe(new BaseCallback<Object>() {
                            @Override
                            public void onSuccess(Object o) {
                                ARouter.getInstance().build(mSource)
                                        .setProvider(new LoginInterceptor())
                                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                        .navigation(AddCardActivity.this);
                            }

                            @Override
                            public void onFailure(UseCaseException e) {
                                showError(e);
                            }
                        });
                break;
            default:
                break;
        }
    }

    private String getConsumptionModel() {
        if (mRadioGroup.getCheckedRadioButtonId() == R.id.radio_cash) {
            return "1";
        } else if (mRadioGroup.getCheckedRadioButtonId() == R.id.radio_gift) {
            return "2";
        } else if (mRadioGroup.getCheckedRadioButtonId() == R.id.radio_proportion) {
            return "3";
        } else {
            return "";
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.radio_group_type) {
            mTxtAdd.setEnabled(true);
            switch (checkedId) {
                case R.id.radio_cash:
                    mRadioCash.setTextColor(Color.parseColor("#5695D2"));
                    mRadioGift.setTextColor(Color.parseColor("#666666"));
                    mRadioProportion.setTextColor(Color.parseColor("#666666"));
                    break;
                case R.id.radio_gift:
                    mRadioCash.setTextColor(Color.parseColor("#666666"));
                    mRadioGift.setTextColor(Color.parseColor("#5695D2"));
                    mRadioProportion.setTextColor(Color.parseColor("#666666"));
                    break;
                case R.id.radio_proportion:
                    mRadioCash.setTextColor(Color.parseColor("#666666"));
                    mRadioGift.setTextColor(Color.parseColor("#666666"));
                    mRadioProportion.setTextColor(Color.parseColor("#5695D2"));
                    break;
                default:
                    break;
            }
        }
    }

}
