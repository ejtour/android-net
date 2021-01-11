package com.hll_sc_app.app.crm.customer.seas.allot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.shopsaleman.CooperationShopSalesActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.EmployeeSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/29
 */

@Route(path = RouterConfig.CRM_CUSTOMER_SEAS_ALLOT)
public class SalesmanAllotActivity extends BaseLoadActivity implements ISalesmanAllotContract.ISalesmanAllotView {
    @BindView(R.id.csa_search_view)
    SearchView mSearchView;
    @BindView(R.id.csa_list_view)
    RecyclerView mListView;
    @BindView(R.id.csa_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mID;
    @Autowired(name = "object1")
    String mName;
    @Autowired(name = "object2")
    String mPurchaserID;
    private CooperationShopSalesActivity.EmployeeListAdapter mAdapter;
    private ISalesmanAllotContract.ISalesmanAllotPresenter mPresenter;
    private EmptyView mEmptyView;
    private EmployeeBean mCurBean;

    /**
     * @param id          意向客户的客户id或合作客户的门店id
     * @param name        意向客户的客户名称 或合作客户的店铺名
     * @param purchaserID 合作客户的集团id
     */
    public static void start(String id, String name, String purchaserID) {
        UserBean user = GreenDaoUtils.getUser();
        List<String> roleCode = user.getRoleCode();
        boolean isManager = !CommonUtils.isEmpty(roleCode) && roleCode.contains("SalesManager");
        if (!isManager) {
            ToastUtils.showShort("销售经理才可以分配哦~");
            return;
        }
        RouterUtil.goToActivity(RouterConfig.CRM_CUSTOMER_SEAS_ALLOT, id, name, purchaserID);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_salesman_allot);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = SalesmanAllotPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mAdapter = new CooperationShopSalesActivity.EmployeeListAdapter();
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            allotDialog();
        });
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SalesmanAllotActivity.this, searchContent, EmployeeSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
    }

    private void allotDialog() {
        SuccessDialog.newBuilder(this)
                .setMessageTitle("确认分配销售么")
                .setMessage(String.format("确认将“%s”分配给销售“%s”么", mName, mCurBean.getEmployeeName()))
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1)
                        mPresenter.allot(mCurBean.getEmployeeID(), mCurBean.getEmployeeName(), mCurBean.getLoginPhone());
                }, "取消", "确认")
                .create().show();
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
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<EmployeeBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("暂时没有可分配的销售代表哦");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    @Override
    public String getID() {
        return mID;
    }

    @Override
    public String getPurchaserID() {
        return mPurchaserID;
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public boolean isIntent() {
        return TextUtils.isEmpty(mPurchaserID);
    }
}
