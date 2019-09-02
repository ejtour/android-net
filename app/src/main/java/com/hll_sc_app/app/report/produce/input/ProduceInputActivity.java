package com.hll_sc_app.app.report.produce.input;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.produce.input.detail.ProduceInputDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.bean.report.produce.ProduceInputReq;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.report.ProduceInputFooter;
import com.hll_sc_app.widget.report.ProduceInputHeader;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

@Route(path = RouterConfig.REPORT_PRODUCE_INPUT)
public class ProduceInputActivity extends BaseLoadActivity implements IProduceInputContract.IProduceInputView {
    public static final int REQ_CODE = 0x216;

    /**
     * @param classes 班次
     * @param date    日期
     */
    public static void start(Activity context, String classes, String date) {
        Object[] args = {classes, date};
        RouterUtil.goToActivity(RouterConfig.REPORT_PRODUCE_INPUT, context, REQ_CODE, args);
    }

    public static void start(Activity activity) {
        start(activity, null, null);
    }

    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mClasses;
    @Autowired(name = "object1")
    String mDate;
    private ProduceInputAdapter mAdapter;
    private ProduceInputHeader mHeader;
    private ProduceInputFooter mFooter;
    private IProduceInputContract.IProduceInputPresenter mPresenter;
    private List<ManHourBean> mBeanList;
    private ProduceDetailBean mCurBean;
    private SingleSelectionDialog mDialog;
    private ProduceInputReq mReq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = ProduceInputPresenter.newInstance(mClasses, mDate);
        mPresenter.register(this);
        mPresenter.start();
        mReq = new ProduceInputReq();
        if (mDate == null)
            mReq.setDate(CalendarUtils.toLocalDate(new Date()));
        else {
            mReq.setDate(mDate);
            mReq.setFlag(1);
            mReq.setClasses(mClasses);
        }
    }

    private void initView() {
        initTitle();
        mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mHeader = new ProduceInputHeader(this);
        mHeader.setOnClickListener(this::selectShift);
        mHeader.setShift(mClasses);
        mFooter = new ProduceInputFooter(this);
        mFooter.setOnClickListener(this::addData);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        mAdapter = new ProduceInputAdapter(this::updateAmount);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            switch (view.getId()) {
                case R.id.rpi_del:
                    mAdapter.remove(position);
                    break;
                case R.id.rpi_company_name:
                    ProduceInputDetailActivity.start(this, mCurBean);
                    break;
            }
        });
        mAdapter.setHeaderView(mHeader);
        mAdapter.setFooterView(mFooter);
        mListView.setAdapter(mAdapter);
    }

    private void initTitle() {
        mTitleBar.setHeaderTitle("录入生产数据");
        mTitleBar.setRightBtnVisible(true);
        mTitleBar.setRightText("保存");
        mTitleBar.setRightBtnClick(this::save);
    }

    private void save(View view) {
        UserBean user = GreenDaoUtils.getUser();
        mReq.setGroupID(user.getGroupID());
        mReq.setInputPer(user.getEmployeeName());
        List<ProduceDetailBean> data = mAdapter.getData();
        for (ProduceDetailBean bean : data) {
            if (TextUtils.isEmpty(bean.getCoopGroupName())) {
                showToast("请选择合作公司");
                return;
            }
        }
        mReq.setRecords(data);
        mPresenter.save(mReq);
    }

    private void selectShift(View view) {
        if (CommonUtils.isEmpty(mBeanList)) {
            mPresenter.reqShiftList();
            return;
        }
        showDialog();
    }

    private void showDialog() {
        if (mDialog == null) {
            mDialog = SingleSelectionDialog.newBuilder(this, ManHourBean::getValue)
                    .setTitleText("选择班次")
                    .refreshList(mBeanList)
                    .setOnSelectListener(manHourBean -> {
                        mReq.setClasses(manHourBean.getValue());
                        mHeader.setShift(manHourBean.getValue());
                    })
                    .create();
        }
        mDialog.show();
    }

    private void addData(View view) {
        mAdapter.addData(new ProduceDetailBean());
    }

    @Override
    public void setShiftData(List<ManHourBean> beanList) {
        mBeanList = beanList;
    }

    @Override
    public void setData(List<ProduceDetailBean> list) {
        if (!CommonUtils.isEmpty(list)) mAdapter.setNewData(list);
    }

    private void updateAmount() {
        double total = 0;
        for (ProduceDetailBean bean : mAdapter.getData()) {
            total = CommonUtils.addDouble(total, bean.getTotalCost()).doubleValue();
        }
        mFooter.setAmount(CommonUtils.formatMoney(total));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ProduceInputDetailActivity.REQ_CODE && resultCode == RESULT_OK && data != null) {
            mAdapter.replace(mCurBean, data.getParcelableExtra("parcelable"));
        }
    }

    @Override
    public void saveSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
