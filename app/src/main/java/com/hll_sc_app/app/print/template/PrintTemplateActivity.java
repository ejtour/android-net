package com.hll_sc_app.app.print.template;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.print.preview.PrintPreviewActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.print.PrintTemplateBean;
import com.hll_sc_app.utils.adapter.ViewPagerAdapter;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
@Route(path = RouterConfig.PRINT_TEMPLATE)
public class PrintTemplateActivity extends BaseLoadActivity implements IPrintTemplateContract.IPrintTemplateView, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.stp_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.stp_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.stp_view_pager)
    ViewPager mViewPager;
    private final RecyclerView[] mList = new RecyclerView[2];
    private final boolean[] mReloadList = {true, true};
    private IPrintTemplateContract.IPrintTemplatePresenter mPresenter;
    private PrintTemplateBean mCurBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        initView();
        mPresenter = new PrintTemplatePresenter();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("打印机模板设置");
        mTabLayout.setIndicatorWidth(20);
        mTabLayout.setIndicatorCornerRadius(1);
        mTabLayout.setTextSelectColor(ContextCompat.getColor(this, R.color.color_333333));
        mTabLayout.setTextUnselectColor(ContextCompat.getColor(this, R.color.color_666666));
        for (int i = 0; i < 2; i++) {
            mList[i] = new RecyclerView(this);
            PrintTemplateAdapter adapter = new PrintTemplateAdapter();
            adapter.setOnItemChildClickListener(this);
            mList[i].setAdapter(adapter);
            mList[i].setLayoutManager(new LinearLayoutManager(this));
            int space = UIUtils.dip2px(10);
            mList[i].setPadding(space, space, space, space);
            mList[i].setClipToPadding(false);
            mList[i].addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        }
        mViewPager.setAdapter(new ViewPagerAdapter(mList));
        mTabLayout.setViewPager(mViewPager, new String[]{"我的", "系统预置"});
    }

    @Override
    public int getCurrentTab() {
        return mViewPager.getCurrentItem();
    }

    @Override
    public PrintTemplateBean getCurTemplate() {
        return mCurBean;
    }

    @Override
    public void setData(List<PrintTemplateBean> list) {
        mReloadList[getCurrentTab()] = false;
        ((PrintTemplateAdapter) mList[getCurrentTab()].getAdapter()).setNewData(list);
    }

    @Override
    public void dataChanged() {
        showToast("添加成功");
        mReloadList[0] = true;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mCurBean = (PrintTemplateBean) adapter.getItem(position);
        if (mCurBean == null) return;
        switch (view.getId()) {
            case R.id.ipt_add_list:
                mPresenter.addToMyList();
                break;
            case R.id.ipt_enable:
                mPresenter.enable();
                break;
            case R.id.ipt_delete:
                SuccessDialog.newBuilder(this)
                        .setMessageTitle("确认要删除此模板吗")
                        .setMessage(String.format("您将删除打印模板【%s】\n" +
                                "删除后可到系统预置模板中重新添加", mCurBean.getTemplateName()))
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1)
                                mPresenter.delete();
                        }, "我再看看", "确认删除")
                        .create().show();
                break;
            case R.id.ipt_preview:
                PrintPreviewActivity.start(mCurBean.getId(), null);
                break;
        }
    }

    @OnPageChange(R.id.stp_view_pager)
    void onPageSelected(int position) {
        if (mReloadList[position]) {
            mPresenter.start();
        }
    }
}
