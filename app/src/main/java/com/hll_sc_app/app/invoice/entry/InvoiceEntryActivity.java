package com.hll_sc_app.app.invoice.entry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.InvoiceEvent;
import com.hll_sc_app.bean.invoice.InvoiceParam;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

@Route(path = RouterConfig.INVOICE_ENTRY)
public class InvoiceEntryActivity extends BaseLoadActivity {
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
        String[] titles = {"已提交", "已开票", "被驳回"};
        mViewPager.setAdapter(new EntryAdapter());
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setViewPager(mViewPager, titles);
        mCommitGroup.setVisibility(TextUtils.isEmpty(UserConfig.getSalesmanID()) ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.are_commit)
    public void commit() {
        showToast("新增发票待添加");
    }

    void filterDate() {
        if (mDatePickerDialog == null) {
            Calendar begin = Calendar.getInstance();
            begin.add(Calendar.YEAR, -3);
            mDatePickerDialog = DatePickerDialog.newBuilder(this)
                    .setBeginTime(begin.getTimeInMillis())
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
