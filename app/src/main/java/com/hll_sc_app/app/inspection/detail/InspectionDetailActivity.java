package com.hll_sc_app.app.inspection.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.inspection.InspectionBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

@Route(path = RouterConfig.INSPECTION_DETAIL)
public class InspectionDetailActivity extends BaseLoadActivity implements IInspectionDetailContract.IInspectionDetailView {

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.INSPECTION_DETAIL, id);
    }

    @BindView(R.id.aid_group)
    TextView mGroup;
    @BindView(R.id.aid_shop)
    TextView mShop;
    @BindView(R.id.aid_contact)
    TextView mContact;
    @BindView(R.id.aid_address)
    TextView mAddress;
    @BindView(R.id.aid_salesman)
    TextView mSalesman;
    @BindView(R.id.aid_time)
    TextView mTime;
    @BindView(R.id.aid_pic_container)
    ThumbnailView mPicContainer;
    @BindView(R.id.aid_remark)
    TextView mRemark;
    @Autowired(name = "object0")
    String mID;
    private IInspectionDetailContract.IInspectionDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initView() {
        mPicContainer.enablePreview(true);
    }

    private void initData() {
        mPresenter = InspectionDetailPresenter.newInstance(mID);
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    public void updateData(InspectionBean bean) {
        mGroup.setText(bean.getPurchaserName());
        mShop.setText(bean.getShopName());
        mContact.setText(String.format("%s / %s", bean.getReceiverName(), PhoneUtil.formatPhoneNum(bean.getReceiverMobile())));
        mAddress.setText(bean.getReceiverAddress());
        mSalesman.setText(String.format("%s / %s", bean.getSalesManName(), PhoneUtil.formatPhoneNum(bean.getSalesManPhone())));
        mTime.setText(DateUtil.getReadableTime(bean.getCreateTime(), Constants.SLASH_YYYY_MM_DD_HH_MM));
        mPicContainer.setData(bean.getInspectionImgUrl().split(","));
        mRemark.setText(bean.getRemark());
    }
}
