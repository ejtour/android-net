package com.hll_sc_app.app.pricemanage.log;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.pricemanage.PriceLogBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 售价设置-变更日志
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
@Route(path = RouterConfig.PRICE_MANAGE_LOG, extras = Constant.LOGIN_EXTRA)
public class PriceChangeLogActivity extends BaseLoadActivity implements PriceChangeLogContract.IPriceManageView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_options)
    ImageView mTxtOptions;
    private EmptyView mEmptyView;
    private PriceLogListAdapter mAdapter;
    private PriceChangeLogPresenter mPresenter;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage_log);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = PriceChangeLogPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePriceLogList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPriceLogList(false);
            }
        });
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PriceLogListAdapter();
        View headView = LayoutInflater.from(this).inflate(R.layout.item_price_manage_log_title, mRecyclerView, false);
        mAdapter.addHeaderView(headView);
        mEmptyView = EmptyView.newBuilder(this).setTipsTitle("当前日期下没有变更日志数据").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.img_back, R.id.txt_options, R.id.rl_select_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_options:
                showAddWindow();
                break;
            case R.id.rl_select_date:
                break;
            default:
                break;
        }
    }

    /**
     * 导出到邮箱
     */
    private void showAddWindow() {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_AGREEMENT_PRICE_EXPORT));
            mOptionsWindow = new ContextOptionsWindow(this).setListener((adapter, view, position)
                -> {
                mPresenter.exportLog(null);
                mOptionsWindow.dismiss();
            }).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(mTxtOptions, Gravity.END);
    }

    @Override
    public void showPriceLogList(List<PriceLogBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() - 1 != total);
    }

    @Override
    public String getSearchParam() {
        return null;
    }

    @Override
    public String getStartTime() {
        return "20190616";
    }

    @Override
    public String getEndTime() {
        return "20190716";
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String tip) {
        Utils.exportFailure(this, tip);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, email -> mPresenter.exportLog(email));
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    class PriceLogListAdapter extends BaseQuickAdapter<PriceLogBean, BaseViewHolder> {

        PriceLogListAdapter() {
            super(R.layout.item_price_manage_log);
        }

        @Override
        protected void convert(BaseViewHolder helper, PriceLogBean bean) {
            helper.setText(R.id.txt_productCode, bean.getProductCode())
                .setText(R.id.txt_skuCode, bean.getSkuCode())
                .setText(R.id.txt_productName, bean.getProductName())
                .setText(R.id.txt_specContent,
                    getString(bean.getSpecContent()) + "/" + getString(bean.getSaleUnitName()))
                .setText(R.id.txt_type, TextUtils.equals(bean.getType(), "1") ? "售价" : "成本价")
                .setText(R.id.txt_priceBefore, CommonUtils.formatNumber(bean.getPriceBefore()))
                .setText(R.id.txt_priceAfter, CommonUtils.formatNumber(bean.getPriceAfter()))
                .setText(R.id.txt_modifier, bean.getModifier())
                .setText(R.id.txt_modifyTime, formatModifyTime(bean.getModifyTime()));
            helper.itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ?
                R.drawable.bg_price_log_content_gray : R.drawable.bg_price_log_content_white);
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }

        private String formatModifyTime(String modifyTime) {
            String formatStr = null;
            if (!TextUtils.isEmpty(modifyTime)) {
                Date date = CalendarUtils.parse(modifyTime, CalendarUtils.FORMAT_HH_MM_SS);
                if (date != null) {
                    formatStr = CalendarUtils.format(date, "yyyy/MM/dd HH:mm");
                }
            }
            return formatStr;
        }
    }
}
