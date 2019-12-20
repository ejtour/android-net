package com.hll_sc_app.app.staffmanage.salerole;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.EmployeeSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 员工列表_关联门店_选择接收人
 */
@Route(path = RouterConfig.STAFF_LIST_LINK_SHOP_SALE_LIST, extras = Constant.LOGIN_EXTRA)
public class StaffSaleListActivity extends BaseLoadActivity implements StaffSaleListContract.IView {
    @BindView(R.id.list_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @Autowired(name = "employeeId")
    String mId;

    private StaffSaleListPresenter mPresenter;
    private StaffSaleAdapter mAdapter;

    private Unbinder unbinder;

    public static void start(Activity activity, int requestcode, String employeeId) {
        ARouter.getInstance().build(RouterConfig.STAFF_LIST_LINK_SHOP_SALE_LIST)
                .withString("employeeId", employeeId)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestcode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_sale_role_list);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresenter = StaffSaleListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.queryEmployeeList(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mAdapter = new StaffSaleAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            EmployeeBean employeeBean = mAdapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra("employ", employeeBean);
            setResult(RESULT_OK, intent);
            finish();
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(StaffSaleListActivity.this,
                        searchContent, EmployeeSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.refresh();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)) {
                mSearchView.showSearchContent(true, name);
            }
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showEmployeeList(List<EmployeeBean> employeeBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(employeeBeans);
        } else {
            mAdapter.setNewData(employeeBeans);
            if (CommonUtils.isEmpty(employeeBeans)) {
                mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("没有关联门店").create());
            }
        }
        if (!CommonUtils.isEmpty(employeeBeans)) {
            mRefreshLayout.setEnableLoadMore(employeeBeans.size() == mPresenter.getPageSize());
        }
    }

    @Override
    public String getSearchContent() {
        return mSearchView.getSearchContent();
    }

    public class StaffSaleAdapter extends BaseQuickAdapter<EmployeeBean, BaseViewHolder> {

        StaffSaleAdapter() {
            super(R.layout.list_item_staff_sale);
        }

        @Override
        protected void convert(BaseViewHolder helper, EmployeeBean employeeBean) {
            boolean isSelected = TextUtils.equals(mId, employeeBean.getEmployeeID());
            helper.setText(R.id.txt_name, employeeBean.getEmployeeName())
                    .setText(R.id.txt_no, employeeBean.getGroupID() + employeeBean.getEmployeeCode())
                    .setText(R.id.txt_phone, employeeBean.getLoginPhone())
                    .setTextColor(R.id.txt_name, Color.parseColor(isSelected ? "#5695D2" : "#999999"))
                    .setTextColor(R.id.txt_no, Color.parseColor(isSelected ? "#5695D2" : "#999999"))
                    .setTextColor(R.id.txt_phone, Color.parseColor(isSelected ? "#5695D2" : "#999999"))
                    .setBackgroundColor(R.id.txt_no_title, Color.parseColor(isSelected ? "#5695D2" : "#999999"))
            ;
            ((ImageView) helper.getView(R.id.img_phone)).setImageResource(isSelected ? R.drawable.ic_employee_phone_blue : R.drawable.ic_employee_phone_gray);
        }

    }
}
