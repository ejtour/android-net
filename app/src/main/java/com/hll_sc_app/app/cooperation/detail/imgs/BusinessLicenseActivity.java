package com.hll_sc_app.app.cooperation.detail.imgs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.BusinessLicensesBean;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 营业执照
 *
 * @author zhuyingsong
 * @date 2019-07-22
 */
@Route(path = RouterConfig.STORE_BUSINESS_LICENSE_IMAGE, extras = Constant.LOGIN_EXTRA)
public class BusinessLicenseActivity extends BaseLoadActivity {
    static final String LONG_TIME = "99991231";
    @Autowired(name = "parcelable", required = true)
    BusinessLicensesBean mBean;
    @BindView(R.id.img_licencePhotoUrl)
    GlideImageView mImgLicencePhotoUrl;
    @BindView(R.id.txt_licenseName)
    TextView mTxtLicenseName;
    @BindView(R.id.txt_businessNo)
    TextView mTxtBusinessNo;
    @BindView(R.id.txt_time)
    TextView mTxtTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_business_license);
        ButterKnife.bind(this);
        mImgLicencePhotoUrl.setImageURL(mBean.getLicencePhotoUrl());
        mTxtLicenseName.setText(mBean.getLicenseName());
        mTxtBusinessNo.setText(mBean.getBusinessNo());
        mTxtTime.setText(String.format("%s-%s", getTime(mBean.getStartTime()), getTime(mBean.getEndTime())));
    }

    private String getTime(String str) {
        String time = null;
        if (TextUtils.equals(str, LONG_TIME)) {
            time = "长期有效";
        } else {
            Date date = CalendarUtils.parse(str, CalendarUtils.FORMAT_LOCAL_DATE);
            if (date != null) {
                time = CalendarUtils.format(date, "yyyy/MM/dd");
            }
        }
        return time;
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }
}
