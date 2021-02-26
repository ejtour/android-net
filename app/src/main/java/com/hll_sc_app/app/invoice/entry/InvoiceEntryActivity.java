package com.hll_sc_app.app.invoice.entry;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.invoice.detail.InvoiceDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.InvoiceEvent;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;

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
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_entry);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == InvoiceDetailActivity.REQ_CODE) {
            EventBus.getDefault().post(new InvoiceEvent(InvoiceEvent.REMOVE_ITEM));
        }
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        boolean notCrm = !UserConfig.crm();
        String[] titles = {notCrm ? "未开票" : "已提交", "已开票", notCrm ? "已驳回" : "被驳回"};
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
            return InvoiceFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
