package com.hll_sc_app.app.marketingsetting.coupon.send;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.coupon.selectshops.SelectGroupsActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.marketingsetting.CouponListResp;
import com.hll_sc_app.bean.marketingsetting.CouponSendReq;
import com.hll_sc_app.citymall.util.CalendarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 分发优惠券
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_SEND)
public class SendCouponActivity extends BaseLoadActivity implements ISendCouponContract.IView {

    @Autowired(name = "parcelable")
    CouponListResp.CouponBean mCouponBean;
    @BindView(R.id.txt_content_name)
    TextView mCouponName;
    @BindView(R.id.txt_content_value)
    TextView mCouponValue;
    @BindView(R.id.txt_content_number_span)
    TextView mConponExpire;
    @BindView(R.id.txt_content_condition)
    TextView mCouponCondition;
    @BindView(R.id.txt_content_person)
    TextView mSelectPerson;
    @BindView(R.id.edt_content_node)
    EditText mEdtNode;
    @BindView(R.id.edt_content_number)
    EditText mEdtNumber;


    private Unbinder unbinder;


    private ArrayList<CouponSendReq.GroupandShopsBean> mSelectCustomer = new ArrayList<>();

    private ISendCouponContract.IPresent mPresent;

    public static void start(CouponListResp.CouponBean couponBean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_COUPON_SEND, couponBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_coupon_send);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mPresent = SendCouponPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mCouponName.setText(mCouponBean.getDiscountName());
        mCouponValue.setText(mCouponBean.getCouponValue());
        if (mCouponBean.getValidityType() == 1) {
            mConponExpire.setText(String.format("领券起 %s 日有效", mCouponBean.getValidityDays()));
        } else {
            String startTime = CalendarUtils.getDateFormatString(mCouponBean.getDiscountStartTime(), "yyyyMMddHHmm", "yyyy-MM-dd");
            String endTime = CalendarUtils.getDateFormatString(mCouponBean.getDiscountEndTime(), "yyyyMMddHHmm", "yyyy-MM-dd");
            mConponExpire.setText(String.format("%s-%s", startTime, endTime));
        }
        if (mCouponBean.getCouponCondition() == 0) {
            mCouponCondition.setText("无使用限制");
        } else {
            mCouponCondition.setText(String.format("满%s元可用", mCouponBean.getCouponConditionValue()));
        }
    }

    @Override
    public String getNode() {
        return mEdtNode.getText().toString();
    }

    @Override
    public String getCouponSendNumber() {
        return mEdtNumber.getText().toString();
    }

    @Override
    public List<CouponSendReq.GroupandShopsBean> getCustomers() {
        return mSelectCustomer;
    }

    @Override
    public void sendSuccess(String message) {
        finish();
    }


    @OnClick({R.id.txt_content_person, R.id.txt_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_content_person:
                SelectGroupsActivity.start(mSelectCustomer);
                break;
            case R.id.txt_send:
                if (mSelectPerson.getText().toString().length() == 0) {
                    showToast("请选择发放客户");
                    return;
                } else if (mEdtNumber.getText().toString().length() == 0) {
                    showToast("请填写发放数量");
                    return;
                }
                List<CouponSendReq.GroupandShopsBean> customerListBeans = getCustomers();
                for (CouponSendReq.GroupandShopsBean bean : customerListBeans) {
                    bean.setSendCount(Integer.parseInt(getCouponSendNumber()));
                    bean.setDiscountID(mCouponBean.getId());
                }
                mPresent.sendCoupon();
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEvent(ArrayList<CouponSendReq.GroupandShopsBean> customers) {
        mSelectCustomer = customers;
        mSelectPerson.setText(String.format("已选%s个集团", customers.size()));
    }
}
