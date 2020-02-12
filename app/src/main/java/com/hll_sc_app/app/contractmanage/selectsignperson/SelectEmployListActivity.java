package com.hll_sc_app.app.contractmanage.selectsignperson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ContextOptionsWindow;
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
 * 选择员工列表
 */
@Route(path = RouterConfig.ACTIVITY_SELECT_EMPLOY_LIST, extras = Constant.LOGIN_EXTRA)
public class SelectEmployListActivity extends BaseLoadActivity implements ISelectEmployContract.IView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlTitle;
    @Autowired(name = "id")
    String mId;
    private EmployListAdapter mAdapter;
    private ISelectEmployContract.IPresent mPresenter;
    private PurchaserBean mSelectPurchaser;
    private ContextOptionsWindow mTitleOptionWindow;

    public static void start(Activity activity, int requestCode, String id) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_SELECT_EMPLOY_LIST)
                .withString("id", id)
                .setProvider(new LoginInterceptor())
                .navigation(activity, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_select_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = SelectEmployPresent.newInstance();
        mPresenter.register(this);
        mPresenter.queryList(true);
    }

    private void initView() {
        mTxtTitle.setText("选择签约人");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mAdapter = new EmployListAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            EmployeeBean bean = mAdapter.getItem(position);
            if (bean != null) {
                mId = bean.getEmployeeID();
                mAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("employ", bean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectEmployListActivity.this,
                        searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.refresh();
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.quereMore();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(true);
    }


    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)) {
                mSearchView.showSearchContent(true, name);
            }
        }
    }

    @Override
    public void querySuccess(List<EmployeeBean> employeeBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(employeeBeans);
        } else {
            if (CommonUtils.isEmpty(employeeBeans)) {
                mAdapter.setEmptyView(EmptyView.newBuilder(this)
                        .setTipsTitle(getEmptyTipstitle())
                        .create());
                mAdapter.setNewData(null);
            } else {
                mAdapter.setNewData(employeeBeans);
            }
        }
        if (!CommonUtils.isEmpty(employeeBeans)) {
            mRefreshLayout.setEnableLoadMore(employeeBeans.size() == 20);
        }
    }


    @Override
    public String getSearchText() {
        return mSearchView.getSearchContent();
    }

    private String getEmptyTipstitle() {
        return TextUtils.isEmpty(getSearchText()) ? "您还没有员工噢" : "没有符合搜索条件的员工";
    }

    private class EmployListAdapter extends BaseQuickAdapter<EmployeeBean, BaseViewHolder> {
        public EmployListAdapter(@Nullable List<EmployeeBean> data) {
            super(R.layout.list_item_select_view, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, EmployeeBean item) {
            helper.setText(R.id.txt_name, item.getEmployeeName())
                    .setVisible(R.id.img_ok, TextUtils.equals(mId, item.getEmployeeID()));
        }
    }
}
