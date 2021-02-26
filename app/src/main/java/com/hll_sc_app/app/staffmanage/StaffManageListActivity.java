package com.hll_sc_app.app.staffmanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.EmployeeSearch;
import com.hll_sc_app.app.staffmanage.detail.StaffManagerEditActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.event.RefreshStaffShopEvent;
import com.hll_sc_app.bean.event.StaffEvent;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 员工列表
 *
 * @author zhuyingsong
 * @date 2019/7/25
 */
@Route(path = RouterConfig.STAFF_LIST, extras = Constant.LOGIN_EXTRA)
public class StaffManageListActivity extends BaseLoadActivity implements StaffManageListContract.IStaffListView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_staffNum)
    TextView mTxtStaffNum;
    @BindView(R.id.txt_add)
    TextView mAdd;
    private EmptyView mEmptyView;
    private EmptyView mSearchEmptyView;
    private StaffManageListPresenter mPresenter;
    private StaffListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
        mPresenter = StaffManageListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        if (UserConfig.crm()) mAdd.setVisibility(View.GONE);
        mSearchView.setHint("可以根据姓名或手机号搜索");
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(StaffManageListActivity.this,
                        searchContent, EmployeeSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryStaffList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreStaffList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryStaffList(false);
            }
        });

        mEmptyView = EmptyView.newBuilder(this)
                .setTipsTitle("哎呀，还没有员工呢")
                .setTipsButton("添加员工")
                .setOnClickListener(new EmptyView.OnActionClickListener() {
                    @Override
                    public void retry() {
                        // no-op
                    }

                    @Override
                    public void action() {
                        toAdd();
                    }
                })
                .create();

        mSearchEmptyView = EmptyView.newBuilder(this)
                .setTipsTitle("没有找到相关的员工噢")
                .setTips("换个名字或者电话再试试吧")
                .create();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(5)));
        mAdapter = new StaffListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            EmployeeBean bean = mAdapter.getItem(position);
            if (bean != null) {
                if (view.getId() == R.id.txt_del) {
                    showDelTipsDialog(bean);
                } else if (view.getId() == R.id.content) {
                    StaffManagerEditActivity.start(this, bean);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void toAdd() {
        StaffManagerEditActivity.start(this, null);
    }

    /**
     * 删除员工提示框
     *
     * @param bean 员工
     */
    private void showDelTipsDialog(EmployeeBean bean) {
        TipsDialog.newBuilder(this)
                .setTitle("删除员工")
                .setMessage("确定要删除员工【" + bean.getEmployeeName() + "】嘛？")
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        mPresenter.delStaff(bean.getEmployeeID(), false);
                    } else {
                        SwipeItemLayout.closeAllItems(mRecyclerView);
                    }
                    dialog.dismiss();
                }, "取消", "确定")
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
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showStaffList(List<EmployeeBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mAdapter.setEmptyView(mSearchEmptyView);
        } else {
            mAdapter.setEmptyView(mEmptyView);

        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == GoodsListReq.PAGE_SIZE);
    }

    @Override
    public void showStaffNum(String num) {
        mTxtStaffNum.setText(String.format("员工总数：%s", num));
    }

    @Override
    public void dropSea(String employeeId, String msg) {
        TipsDialog.newBuilder(this)
                .setTitle("删除员工")
                .setMessage(msg)
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        mPresenter.delStaff(employeeId, true);
                    }
                    dialog.dismiss();
                }, "取消", "确定")
                .create().show();
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    public static class StaffListAdapter extends BaseQuickAdapter<EmployeeBean, BaseViewHolder> {
        private boolean mCrm;

        StaffListAdapter() {
            super(R.layout.item_staff_list);
            mCrm = UserConfig.crm();
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            if (mCrm) {
                View del = viewHolder.getView(R.id.txt_del);
                ViewGroup.LayoutParams layoutParams = del.getLayoutParams();
                layoutParams.width = 0;
                del.setLayoutParams(layoutParams);
            }
            viewHolder.addOnClickListener(R.id.txt_del).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, EmployeeBean item) {
            helper.setText(R.id.txt_employeeName, item.getEmployeeName())
                    .setText(R.id.txt_loginPhone, PhoneUtil.formatPhoneNum(item.getLoginPhone()))
                    .setText(R.id.txt_roles, "拥有职务" + (CommonUtils.isEmpty(item.getRoles()) ? "0" :
                            item.getRoles().size()) + "个")
                    .setText(R.id.txt_departs, "归属部门" + (TextUtils.isEmpty(item.getDeptIDs()) ? 0 : item.getDeptIDs().split(",").length) + "个");

            TextView view = helper.getView(R.id.txt_roles);
            if (mCrm) {
                view.setText(String.format("关联门店%s个", item.getShopNum()));
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_staff_shop, 0, 0, 0);
            } else {
                view.setText(String.format("拥有职务%s个", CommonUtils.isEmpty(item.getRoles()) ? "0" :
                        item.getRoles().size()));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onEvent(StaffEvent staffEvent) {
        if (staffEvent.isRefreshList()) {
            Observable.timer(900, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
                    .subscribe(aLong -> mPresenter.start());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshStaffShopEvent event) {
        if (!TextUtils.isEmpty(event.getEmployeeID()) && mAdapter != null) {
            List<EmployeeBean> data = mAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                EmployeeBean bean = data.get(i);
                if (TextUtils.equals(bean.getEmployeeID(), event.getEmployeeID()) && bean.getShopNum() != event.getShopNum()) {
                    bean.setShopNum(event.getShopNum());
                    mAdapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }
}
