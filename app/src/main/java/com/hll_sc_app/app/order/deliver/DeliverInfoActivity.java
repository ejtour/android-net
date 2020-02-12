package com.hll_sc_app.app.order.deliver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ProductSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */
@Route(path = RouterConfig.ORDER_DELIVER)
public class DeliverInfoActivity extends BaseLoadActivity implements IDeliverInfoContract.IDeliverInfoView {
    @BindView(R.id.odi_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.odi_search_view)
    SearchView mSearchView;
    @BindView(R.id.odi_list_view)
    RecyclerView mListView;
    private List<DeliverInfoResp> mList;
    private DeliverInfoAdapter mAdapter;
    private int mCurPos;
    private IDeliverInfoContract.IDeliverInfoPresenter mPresenter;
    private EmptyView mEmptyView;
    private ContextOptionsWindow mOptionsWindow;

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.ORDER_DELIVER);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_deliver_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = DeliverInfoPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        mAdapter = new DeliverInfoAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurPos = position;
            DeliverInfoResp resp = mAdapter.getItem(position);
            if (resp == null) {
                return;
            }
            if (CommonUtils.isEmpty(resp.getList())) {
                mPresenter.requestShopList(resp.getProductSpecID());
            } else {
                resp.setExpanded(!resp.isExpanded());
                mAdapter.notifyItemChanged(position);
            }
        });
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(80), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(DeliverInfoActivity.this, searchContent, ProductSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                updateData();
            }
        });
    }

    private void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_PEND_DELIVERY_GOODS));
            mOptionsWindow = new ContextOptionsWindow(this);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                mPresenter.export(null);
            });
            mOptionsWindow.refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.RIGHT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void updateShopList(List<DeliverShopResp> list) {
        DeliverInfoResp resp = mAdapter.getItem(mCurPos);
        if (resp == null) {
            return;
        }
        resp.setList(list);
        resp.setExpanded(true);
        mAdapter.notifyItemChanged(mCurPos);
    }

    @Override
    public void updateInfoList(List<DeliverInfoResp> list) {
        mList = list;
        updateData();
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void updateData() {
        String searchContent = mSearchView.getSearchContent();
        if (CommonUtils.isEmpty(mList) || TextUtils.isEmpty(searchContent)) {
            mAdapter.setNewData(mList);
        } else {
            List<DeliverInfoResp> list = new ArrayList<>();
            for (DeliverInfoResp resp : mList) {
                if (resp.getProductName().contains(searchContent)) {
                    list.add(resp);
                }
            }
            mAdapter.setNewData(list);
        }
        if (CommonUtils.isEmpty(mAdapter.getData())) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTips("暂无数据");
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this).setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresenter::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }
}
