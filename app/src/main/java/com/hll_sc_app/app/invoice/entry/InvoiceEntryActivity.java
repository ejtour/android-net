package com.hll_sc_app.app.invoice.entry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.InvoiceEvent;
import com.hll_sc_app.bean.invoice.InvoiceParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

@Route(path = RouterConfig.INVOICE_ENTRY)
public class InvoiceEntryActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.are_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.are_commit_group)
    Group mCommitGroup;
    @BindView(R.id.are_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.are_view_pager)
    ViewPager mViewPager;
    private final InvoiceParam mParam = new InvoiceParam();
    private DatePickerDialog mDatePickerDialog;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_invoice_entry);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date date = new Date();
        mParam.setStartTime(date);
        mParam.setEndTime(date);
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        boolean notCrm = TextUtils.isEmpty(UserConfig.getSalesmanID());
        mTitleBar.setRightBtnVisible(notCrm);
        String[] titles = {notCrm ? "未开票" : "已提交", "已开票", "被驳回"};
        mViewPager.setAdapter(new EntryAdapter());
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setViewPager(mViewPager, titles);
        mCommitGroup.setVisibility(notCrm ? View.GONE : View.VISIBLE);
    }

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_INVOICE)))
                    .setListener(this);
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    @OnClick(R.id.are_commit)
    public void commit() {
        RouterUtil.goToActivity(RouterConfig.INVOICE_SELECT_SHOP);
    }

    void filterDate() {
        if (mDatePickerDialog == null) {
            Date begin = DateUtil.parse("20170101");
            mDatePickerDialog = DatePickerDialog.newBuilder(this)
                    .setBeginTime(begin.getTime())
                    .setEndTime(System.currentTimeMillis())
                    .setTitle("按时间筛选")
                    .setCancelable(false)
                    .setCallback(new DatePickerDialog.SelectCallback() {
                        @Override
                        public void select(Date beginTime, Date endTime) {
                            mParam.setStartTime(beginTime);
                            mParam.setEndTime(endTime);
                            EventBus.getDefault().post(new InvoiceEvent(InvoiceEvent.RELOAD_LIST));
                        }
                    })
                    .create();
        }
        mDatePickerDialog.show();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter.getItem(position) instanceof OptionsBean) {
            mOptionsWindow.dismiss();
            EventBus.getDefault().post(new InvoiceEvent(InvoiceEvent.EXPORT));
        }
    }

    private class EntryAdapter extends FragmentPagerAdapter {

        EntryAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return InvoiceFragment.newInstance(mParam, position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
