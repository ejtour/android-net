package com.hll_sc_app.app.marketingsetting.coupon.check;

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
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;
import com.hll_sc_app.citymall.util.CalendarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 查看优惠券
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_CHECK)
public class MarketingCouponCheckActivity extends BaseLoadActivity implements IMarketingCouponCheckContract.IView {
    private static final String FORMAT_TIME_OUT = "yyyy/MM/dd HH:mm";
    private static final String FORMAT_TIME_SPAN_OUT = "yyyy-MM-dd";
    @Autowired(name = "object0")
    String mID;
    @BindView(R.id.txt_content_name)
    TextView mName;
    @BindView(R.id.txt_content_value)
    TextView mValue;
    @BindView(R.id.txt_content_time_span)
    TextView mTimeSpan;
    @BindView(R.id.txt_content_condition)
    TextView mCondition;
    @BindView(R.id.txt_content_person)
    TextView mPerson;
    @BindView(R.id.txt_content_time)
    TextView mTime;

    private Unbinder unbinder;
    private IMarketingCouponCheckContract.IPresent mpresent;

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_COUPON_CHECK, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_coupon_check);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mpresent = MarketingCouponCheckPresent.newInstance();
        mpresent.register(this);
        mpresent.getDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick({R.id.txt_content_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_content_detail:
                //todo 使用详情-zc
                break;
            default:
                break;
        }
    }

    @Override
    public void showDetail(MarketingDetailCheckResp resp) {
        mName.setText(resp.getDiscountName());
        mValue.setText(resp.getCouponValue());
        if (TextUtils.equals(resp.getValidityType(), "1")) {
            mTimeSpan.setText(String.format("领券起%s日有效", resp.getValidityDays()));
        } else {
            mTimeSpan.setText(CalendarUtils.getDateFormatString(resp.getDiscountStartTime(),"yyyyMMddHHmm", FORMAT_TIME_SPAN_OUT) + "~" + CalendarUtils.getDateFormatString(resp.getDiscountEndTime(), "yyyyMMddHHmm", FORMAT_TIME_SPAN_OUT));
        }

        if (TextUtils.equals(resp.getCouponCondition(), "0")) {
            mCondition.setText("无使用限制");
        } else {
            mCondition.setText(String.format("满%s元可使用", resp.getRuleList().get(0).getRuleCondition()));
        }
        mPerson.setText(resp.getCreateBy());
        mTime.setText(CalendarUtils.getDateFormatString(resp.getCreateTime(), "yyyyMMddHHmm", FORMAT_TIME_OUT));

    }

    @Override
    public String getDiscountID() {
        return mID;
    }
}
