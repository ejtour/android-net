package com.hll_sc_app.app.setting.priceratio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.setting.priceratio.add.PriceRatioTemplateAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面-价格比例设置-列表
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
@Route(path = RouterConfig.SETTING_PRICE_RATIO_LIST, extras = Constant.LOGIN_EXTRA)
public class PriceRatioTemplateListActivity extends BaseLoadActivity implements PriceRatioTemplateListContract.IPriceRatioView {
    @Autowired(name = "object0")
    String mTemplateType;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private EmptyView mEmptyView;
    private RatioTemplateListAdapter mAdapter;
    private PriceRatioTemplateListPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_price_ratio_template_list);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = PriceRatioTemplateListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTxtTitle.setText(isAgreementPriceRatio() ? "协议价比例模版列表" : "售价比例模版列表");
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePriceRatioTemplate();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPriceRatioTemplate(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(5)));
        mAdapter = new RatioTemplateListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            RatioTemplateBean ratioTemplateBean = (RatioTemplateBean) adapter.getItem(position);
            if (ratioTemplateBean == null) {
                return;
            }
            int id = view.getId();
            if (id == R.id.txt_del) {
                showDelTipsDialog(ratioTemplateBean);
            } else if (id == R.id.content) {
                RouterUtil.goToActivity(RouterConfig.SETTING_PRICE_RATIO_ADD, isAgreementPriceRatio() ?
                        PriceRatioTemplateActivity.TYPE_AGREEMENT_PRICE : PriceRatioTemplateActivity.TYPE_PRICE_MANAGE,
                    PriceRatioTemplateAddActivity.TYPE_ADD, ratioTemplateBean.getTemplateID(),
                    ratioTemplateBean.getTemplateName());
            }
        });
        mEmptyView = EmptyView.newBuilder(this)
            .setTipsTitle("您还没有" + (isAgreementPriceRatio() ? "协议价比例模版" : "售价比例模版") + "数据")
            .setTips("您可以在分类中按照百分比进行设置")
            .create();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private boolean isAgreementPriceRatio() {
        return TextUtils.equals(PriceRatioTemplateActivity.TYPE_AGREEMENT_PRICE, mTemplateType);
    }

    private void showDelTipsDialog(RatioTemplateBean bean) {
        TipsDialog.newBuilder(this)
            .setTitle("删除比例模板")
            .setMessage("确定要删除【" + bean.getTemplateName() + "】嘛？")
            .setButton((dialog, item) -> {
                if (item == 1) {
                    mPresenter.delPriceRatioTemplate(bean.getTemplateID());
                } else {
                    SwipeItemLayout.closeAllItems(mRecyclerView);
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create().show();
    }

    private void toAdd() {
        RouterUtil.goToActivity(RouterConfig.SETTING_PRICE_RATIO_ADD, isAgreementPriceRatio() ?
                PriceRatioTemplateActivity.TYPE_AGREEMENT_PRICE : PriceRatioTemplateActivity.TYPE_PRICE_MANAGE,
            PriceRatioTemplateAddActivity.TYPE_ADD);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    @OnClick({R.id.img_close, R.id.txt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add:
                toAdd();
                break;
            default:
                break;
        }
    }

    @Override
    public void showPriceRatioTemplate(List<RatioTemplateBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
    }

    @Override
    public String getTemplateType() {
        return mTemplateType;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    class RatioTemplateListAdapter extends BaseQuickAdapter<RatioTemplateBean, BaseViewHolder> {

        RatioTemplateListAdapter() {
            super(R.layout.item_price_ratio);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder baseViewHolder = super.onCreateDefViewHolder(parent, viewType);
            baseViewHolder.addOnClickListener(R.id.txt_del).addOnClickListener(R.id.content);
            return baseViewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, RatioTemplateBean bean) {
            helper.setText(R.id.txt_templateName, bean.getTemplateName())
                .setText(R.id.txt_category, "包含" + bean.getCategoryCount() + "个分类");
        }
    }
}
