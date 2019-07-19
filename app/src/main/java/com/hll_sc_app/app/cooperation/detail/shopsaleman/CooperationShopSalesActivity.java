package com.hll_sc_app.app.cooperation.detail.shopsaleman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopadd.CooperationSelectShopActivity;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.EmployeeBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.event.EmployeeSearchEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情- 批量指派销售/司机
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SALES, extras = Constant.LOGIN_EXTRA)
public class CooperationShopSalesActivity extends BaseLoadActivity implements CooperationShopSalesContract.ICooperationAddShopView {
    @Autowired(name = "parcelable", required = true)
    ShopSettlementReq mReq;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private EmptyView mEmptyView;
    private EmployeeListAdapter mAdapter;
    private CooperationShopSalesPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_sales);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = CooperationShopSalesPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTxtTitle.setText(isSales() ? "选择销售" : "选择司机");
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreEmployeeList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryEmployeeList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mEmptyView = EmptyView.newBuilder(this).setTips(isSales() ? "您还没有销售人员数据" : "您还没有司机人员数据").create();
        mAdapter = new EmployeeListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            EmployeeBean employeeBean = (EmployeeBean) adapter.getItem(position);
            if (employeeBean == null) {
                return;
            }
            employeeBean.setSelect(true);
            adapter.notifyItemChanged(position);
            mReq.setEmployeeID(employeeBean.getEmployeeID());
            mReq.setEmployeeName(employeeBean.getEmployeeName());
            mReq.setEmployeePhone(employeeBean.getLoginPhone());
            mPresenter.editShopEmployee(mReq);
        });
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_EMPLOYEE);
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryEmployeeList(true);
            }
        });
    }

    private boolean isSales() {
        return TextUtils.equals(mReq.getActionType(), CooperationSelectShopActivity.TYPE_SALESMAN);
    }

    @Subscribe
    public void onEvent(EmployeeSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    @Override
    public void showEmployeeList(List<EmployeeBean> list, boolean append) {
        if (!TextUtils.isEmpty(mReq.getEmployeeID()) && !CommonUtils.isEmpty(list)) {
            for (EmployeeBean bean : list) {
                if (TextUtils.equals(bean.getEmployeeID(), mReq.getEmployeeID())) {
                    bean.setSelect(true);
                    break;
                }
            }
        }

        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public String getKeyWord() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getRoleType() {
        return isSales() ? "1" : "2";
    }

    @Override
    public void editSuccess() {
        showToast(isSales() ? "指派销售成功" : "指派司机成功");
        ARouter.getInstance().build(RouterConfig.COOPERATION_PURCHASER_DETAIL)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    public static class EmployeeListAdapter extends BaseQuickAdapter<EmployeeBean, BaseViewHolder> {

        EmployeeListAdapter() {
            super(R.layout.item_cooperation_employee);
        }

        @Override
        protected void convert(BaseViewHolder helper, EmployeeBean item) {
            helper.setText(R.id.txt_employeeName, item.getEmployeeName())
                .setText(R.id.txt_employeeCode, item.getGroupID() + item.getEmployeeCode())
                .setText(R.id.txt_loginPhone, PhoneUtil.formatPhoneNum(item.getLoginPhone()))
                .setGone(R.id.img_select, item.isSelect());

            helper.getView(R.id.txt_employeeName).setSelected(item.isSelect());
            helper.getView(R.id.txt_employeeCode).setSelected(item.isSelect());
            helper.getView(R.id.txt_loginPhone).setSelected(item.isSelect());
            helper.getView(R.id.img_code).setSelected(item.isSelect());
            helper.getView(R.id.img_phone).setSelected(item.isSelect());
        }
    }
}
