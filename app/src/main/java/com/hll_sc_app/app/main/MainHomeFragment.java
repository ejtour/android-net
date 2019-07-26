package com.hll_sc_app.app.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.common.SalesVolumeResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
@Route(path = RouterConfig.ROOT_HOME_MAIN)
public class MainHomeFragment extends BaseLoadFragment implements IMainHomeContract.IMainHomeView, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.fmh_top_bg)
    ImageView mTopBg;
    @BindView(R.id.fmh_sales_volume)
    TextView mSaleVolume;
    @BindView(R.id.fmh_refresh_view)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.fmh_top_tag)
    TextView mTopTag;
    @BindView(R.id.fmh_tag_flag)
    ImageView mTagFlag;
    @BindView(R.id.fmh_message_icon)
    ImageView mMessageIcon;
    Unbinder unbinder;
    @IMainHomeContract.DateType
    private int mDateType = IMainHomeContract.DateType.TYPE_DAY;
    private IMainHomeContract.IMainHomePresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        initData();
        return rootView;
    }

    private void initData() {
        mPresenter = MainHomePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        showStatusBar();
        mRefreshView.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mTopBg.setScaleX(1 + percent * 0.7f);
                mTopBg.setScaleY(1 + percent * 0.7f);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.querySalesVolume(false);
            }
        });
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mMessageIcon.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fmh_message_icon, R.id.fmh_top_tag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fmh_message_icon:
                break;
            case R.id.fmh_top_tag:
                showOptionsWindow(view);
                break;
        }
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_day_option, OptionType.OPTION_REPORT_CURRENT_DATE));
            list.add(new OptionsBean(R.drawable.ic_week_option, OptionType.OPTION_REPORT_CURRENT_WEEK));
            list.add(new OptionsBean(R.drawable.ic_month_option, OptionType.OPTION_REPORT_CURRENT_MONTH));
            mOptionsWindow = new ContextOptionsWindow(requireActivity())
                    .refreshList(list)
                    .setListener(this);
        }
        mOptionsWindow.showAsDropDownFix(view, -UIUtils.dip2px(5), 0, Gravity.START);
    }

    @Override
    public void updateSalesVolume(SalesVolumeResp resp) {
        mSaleVolume.setText(CommonUtils.formatMoney(resp.getSales()));
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @IMainHomeContract.DateType
    @Override
    public int getDateType() {
        return mDateType;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OptionsBean item = (OptionsBean) adapter.getItem(position);
        if (item == null) return;
        mOptionsWindow.dismiss();
        switch (item.getLabel()) {
            case OptionType.OPTION_REPORT_CURRENT_DATE:
                if (mDateType == 0) return;
                mDateType = 0;
                break;
            case OptionType.OPTION_REPORT_CURRENT_WEEK:
                if (mDateType == 1) return;
                mDateType = 1;
                break;
            case OptionType.OPTION_REPORT_CURRENT_MONTH:
                if (mDateType == 2) return;
                mDateType = 2;
                break;
            default:
                return;
        }
        mTagFlag.setImageResource(item.getIconRes());
        mTopTag.setText(item.getLabel());
        mPresenter.start();
    }
}