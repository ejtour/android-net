package com.hll_sc_app.app.deliverymanage.ageing.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.widget.SingleSelectionDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 配送时效编辑、新增界面
 *
 * @author zhuyingsong
 * @date 2019/7/29
 */
@Route(path = RouterConfig.DELIVERY_AGEING_DETAIL, extras = Constant.LOGIN_EXTRA)
public class DeliveryAgeingDetailActivity extends BaseLoadActivity implements DeliveryAgeingDetailContract.IDeliveryAgeingDetailView {
    public static final String[] STRINGS = {"当天", "次日", "第三天", "第四天", "第五天", "第六天", "第七天"};
    @Autowired(name = "parcelable")
    DeliveryPeriodBean mBean;
    @Autowired(name = "object0")
    int mPosition;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_billUpDateTime)
    TextView mTxtBillUpDateTime;
    @BindView(R.id.txt_dayTimeFlag)
    TextView mTxtDayTimeFlag;
    @BindView(R.id.txt_arrivalTime)
    TextView mTxtArrivalTime;
    @BindView(R.id.txt_ageing_title)
    TextView mTxtAgeingTitle;
    private DeliveryAgeingDetailPresenter mPresenter;
    private SingleSelectionDialog mBillUpDateDialog;
    private SingleSelectionDialog mDayTimeFlagDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_ageing_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryAgeingDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTxtTitle.setText(isAdd() ? "新增配送时效" : "编辑配送时效");
        if (mBean != null) {
            mTxtAgeingTitle.setText(String.format(Locale.getDefault(), "时效管理%d", mBean.getPosition()));
            mTxtBillUpDateTime.setText(mBean.getBillUpDateTime());
            mTxtDayTimeFlag.setText(getDayTimeFlag(mBean.getDayTimeFlag()));
            mTxtDayTimeFlag.setTag(String.valueOf(mBean.getDayTimeFlag()));
            showArrivalTime(mBean.getArrivalStartTime(), mBean.getArrivalEndTime());
        } else {
            mTxtAgeingTitle.setText(String.format(Locale.getDefault(), "时效管理%d", mPosition));
        }
    }

    private boolean isAdd() {
        return mBean == null;
    }

    public static String getDayTimeFlag(int flag) {
        String string = null;
        if (flag >= 0 && flag < STRINGS.length) {
            string = STRINGS[flag];
        }
        return string;
    }

    private void showArrivalTime(String start, String end) {
        mTxtArrivalTime.setTag(R.id.date_start, start);
        mTxtArrivalTime.setTag(R.id.date_end, end);
        mTxtArrivalTime.setText(String.format("%s-%s", start, end));
    }

    @Subscribe
    public void onEvent(DeliveryPeriodBean bean) {
        if (bean != null) {
            showArrivalTime(bean.getArrivalStartTime(), bean.getArrivalEndTime());
        }
    }

    @OnClick({R.id.img_close, R.id.rl_billUpDateTime, R.id.rl_dayTimeFlag, R.id.rl_arrivalTime, R.id.txt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.rl_billUpDateTime:
                showBillUpdateSelectWindow();
                break;
            case R.id.rl_dayTimeFlag:
                showDayTimeFlagSelectWindow();
                break;
            case R.id.rl_arrivalTime:
                toArrivalTime();
                break;
            case R.id.txt_save:
                toSave();
                break;
            default:
                break;
        }
    }

    /**
     * 选择截单时间
     */
    private void showBillUpdateSelectWindow() {
        if (mBillUpDateDialog == null) {
            List<String> list = new ArrayList<>();
            String select = null;
            String text = mTxtBillUpDateTime.getText().toString();
            DecimalFormat format = new DecimalFormat("00");
            for (int i = 1; i < 24; i++) {
                String time = format.format(i) + ":00";
                list.add(time);
                if (TextUtils.equals(text, time)) {
                    select = time;
                }
            }
            mBillUpDateDialog = SingleSelectionDialog.newBuilder(this,
                (SingleSelectionDialog.WrapperName<String>) s -> s)
                .refreshList(list)
                .select(select)
                .setTitleText("选择截单时间")
                .setOnSelectListener(s -> mTxtBillUpDateTime.setText(s)).create();
        }
        mBillUpDateDialog.show();
    }

    /**
     * 选择最早的配送日期
     */
    private void showDayTimeFlagSelectWindow() {
        if (mDayTimeFlagDialog == null) {
            List<NameValue> list = new ArrayList<>();
            NameValue select = null;
            String text = mTxtDayTimeFlag.getText().toString();
            for (int i = 0; i < STRINGS.length; i++) {
                NameValue nameValue = new NameValue(STRINGS[i], String.valueOf(i));
                list.add(nameValue);
                if (TextUtils.equals(text, STRINGS[i])) {
                    select = nameValue;
                }
            }
            mDayTimeFlagDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .refreshList(list)
                .setTitleText("选择最早的配送日期")
                .select(select)
                .setOnSelectListener(s -> {
                    mTxtDayTimeFlag.setTag(s.getValue());
                    mTxtDayTimeFlag.setText(s.getName());
                })
                .create();
        }
        mDayTimeFlagDialog.show();
    }

    private void toArrivalTime() {
        if (TextUtils.isEmpty(mTxtBillUpDateTime.getText())) {
            showToast("请选择截单时间");
            return;
        }
        if (mTxtDayTimeFlag.getTag() == null) {
            showToast("请选择最早的配送日期");
            return;
        }
        String flg = TextUtils.equals(String.valueOf(mTxtDayTimeFlag.getTag()), "0") ? "1" : "2";
        RouterUtil.goToActivity(RouterConfig.DELIVERY_AGEING_DETAIL_PERIOD, mTxtBillUpDateTime.getText().toString(),
            flg);
    }

    private void toSave() {
        if (TextUtils.isEmpty(mTxtBillUpDateTime.getText())) {
            showToast("请选择截单时间");
            return;
        }
        if (mTxtDayTimeFlag.getTag() == null) {
            showToast("请选择最早的配送日期");
            return;
        }
        if (mTxtArrivalTime.getTag(R.id.date_start) == null) {
            showToast("请选择配送开始时间");
            return;
        }
        if (mTxtArrivalTime.getTag(R.id.date_end) == null) {
            showToast("请选择配送 结束时间");
            return;
        }

        BaseMapReq.Builder builder = BaseMapReq.newBuilder();
        builder.put("groupID", UserConfig.getGroupID())
            .put("dayTimeFlag", String.valueOf(mTxtDayTimeFlag.getTag()))
            .put("billUpDateTime", mTxtBillUpDateTime.getText().toString())
            .put("arrivalStartTime", (String) mTxtArrivalTime.getTag(R.id.date_start))
            .put("arrivalEndTime", (String) mTxtArrivalTime.getTag(R.id.date_end));
        if (isAdd()) {
            builder.put("operationType", "0");
        } else {
            builder.put("operationType", "1")
                .put("deliveryTimeID", mBean.getDeliveryTimeID())
                .create();
        }
        mPresenter.editDeliveryAgeing(builder.create());
    }

    @Override
    public void editSuccess() {
        if (isAdd()) {
            showToast("新增配送时效成功");
        } else {
            showToast("编辑配送时效成功");
        }
        ARouter.getInstance().build(RouterConfig.DELIVERY_AGEING_MANAGE)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }
}
