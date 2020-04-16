package com.hll_sc_app.app.bill.log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillLogBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/9
 */
@Route(path = RouterConfig.BILL_LOG)
public class BillLogActivity extends BaseLoadActivity implements IBillLogContract.IBillLogView {
    @BindView(R.id.abl_image)
    GlideImageView mImage;
    @BindView(R.id.abl_shop_name)
    TextView mShopName;
    @BindView(R.id.abl_period)
    TextView mPeriod;
    @BindView(R.id.abl_group_name)
    TextView mGroupName;
    @BindView(R.id.abl_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    BillBean mBean;
    private BillLogAdapter mAdapter;

    public static void start(BillBean billBean) {
        RouterUtil.goToActivity(RouterConfig.BILL_LOG, billBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_bill_log);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        IBillLogContract.IBillLogPresenter presenter = BillLogPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mImage.setImageURL(mBean.getGroupLogoUrl());
        mShopName.setText(mBean.getShopName());
        mGroupName.setText(mBean.getGroupName());
        mPeriod.setText(String.format("账单周期：%s - %s", DateUtil.getReadableTime(mBean.getStartPaymentDay(), Constants.SLASH_YYYY_MM_DD),
                DateUtil.getReadableTime(mBean.getEndPaymentDay(), Constants.SLASH_YYYY_MM_DD)));
        mAdapter = new BillLogAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setData(List<BillLogBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public String getID() {
        return mBean.getId();
    }
}
