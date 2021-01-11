package com.hll_sc_app.app.staffmanage.detail.depart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.StaffDepartSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.StaffDepartListEvent;
import com.hll_sc_app.bean.staff.DepartmentListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*选择员工部门*/
@Route(path = RouterConfig.ACTIVITY_SELECT_DEPARTMENT_LIST)
public class DepartListActivity extends BaseLoadActivity implements IDepartListContract.IView {
    @BindView(R.id.search_view)
    SearchView mSearch;
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.txt_add)
    TextView mTxtAdd;
    @BindView(R.id.txt_edt)
    TextView mTxtEdt;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mrefreshLayout;
    @BindView(R.id.ll_btn)
    LinearLayout mLlButton;
    @BindView(R.id.txt_or)
    TextView mTxtOr;
    @BindView(R.id.txt_alert)
    TextView mTxtAlert;
    @Autowired(name = "ids")
    ArrayList<String> ids;

    private Unbinder unbinder;
    private InputDialog mAddDiolog;
    private InputDialog mEdtDiolog;
    private Set<String> mSelectIds = new HashSet<>();
    private DepartAdapter mAdapter;
    private IDepartListContract.IPresent mPresent;

    public static void start(String ids) {
        ArrayList<String> idList = new ArrayList<>();
        if (!TextUtils.isEmpty(ids)) {
            idList.addAll(Arrays.asList(ids.split(",")));
        }
        ARouter.getInstance()
                .build(RouterConfig.ACTIVITY_SELECT_DEPARTMENT_LIST)
                .withStringArrayList("ids", idList)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_depart_list);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mPresent = DepartListPresent.newInstance();
        mPresent.register(this);
        initView();
        mPresent.queryDropMenus(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        if (ids != null) {
            mSelectIds.addAll(ids);
        }
        mTxtEdt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTxtAdd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DepartAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mAdapter.isEdt()) {
                return;
            }
            DepartmentListResp.DepartmentLisBean bean = mAdapter.getItem(position);
            if (mSelectIds.contains(bean.getId())) {
                mSelectIds.remove(bean.getId());
            } else {
                mSelectIds.add(bean.getId());
            }
            mAdapter.notifyDataSetChanged();
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!mAdapter.isEdt()) {
                return;
            }
            if (view.getId() == R.id.img_edt) {
                if (mEdtDiolog == null) {
                    mEdtDiolog = new InputDialog(this, new InputDialog.InputDialogConfig() {
                        @Override
                        public String getTitle() {
                            return "编辑部门";
                        }

                        @Override
                        public String getValue() {
                            return mAdapter.getItem(position).getDeptName();
                        }

                        @Override
                        public String getHint() {
                            return "部门名称要在10个字以内";
                        }

                        @Override
                        public int getMaxInputLength() {
                            return 10;
                        }

                        @Override
                        public void click(BaseDialog dialog, String content, int index) {
                            dialog.dismiss();
                            if (index == 1) {
                                mPresent.modifyDepartment(mAdapter.getItem(position).getId(), content);
                            }
                        }

                        @Override
                        public String getRightButtonText() {
                            return "确认修改";
                        }
                    });
                }
                mEdtDiolog.show();
            } else if (view.getId() == R.id.img_remove) {
                mPresent.removeDepartment(mAdapter.getItem(position).getId());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mSearch.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(DepartListActivity.this,
                        searchContent, StaffDepartSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.refresh();
            }
        });

        mTitle.setRightBtnClick(v -> {
            EventBus.getDefault().post(new StaffDepartListEvent(new ArrayList<>(mSelectIds)));
            finish();
        });

        mrefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearch.showSearchContent(true, name);
        }
    }

    @OnClick({R.id.txt_add, R.id.txt_edt, R.id.txt_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_add:
                if (mAddDiolog == null) {
                    mAddDiolog = new InputDialog(this, new InputDialog.InputDialogConfig() {
                        @Override
                        public String getTitle() {
                            return "新建部门";
                        }

                        @Override
                        public String getHint() {
                            return "部门名称要在10个字以内";
                        }

                        @Override
                        public int getMaxInputLength() {
                            return 10;
                        }

                        @Override
                        public void click(BaseDialog dialog, String content, int index) {
                            dialog.dismiss();
                            if (index == 1) {
                                mPresent.addDepartment(content);
                            }
                        }

                        @Override
                        public String getRightButtonText() {
                            return "确认新建";
                        }
                    });
                }
                mAddDiolog.show();
                break;
            case R.id.txt_edt:
                if (CommonUtils.isEmpty(mAdapter.getData())) {
                    showToast("暂无可编辑部门");
                    return;
                }
                mTxtAlert.setText("您正在编辑部门信息…");
                mTxtOr.setVisibility(View.GONE);
                mTxtAdd.setVisibility(View.GONE);
                mTxtEdt.setVisibility(View.GONE);
                mLlButton.setVisibility(View.VISIBLE);
                mAdapter.isEdtModal(true);
                break;
            case R.id.txt_finish:
                mTxtAlert.setText("同一位员工支持多选部门；点击这里可以");
                mTxtOr.setVisibility(View.VISIBLE);
                mTxtAdd.setVisibility(View.VISIBLE);
                mTxtEdt.setVisibility(View.VISIBLE);
                mLlButton.setVisibility(View.GONE);
                mAdapter.isEdtModal(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void querySuccess(DepartmentListResp resp, boolean isMore) {
        if (isMore && !CommonUtils.isEmpty(resp.getList())) {
            mAdapter.addData(resp.getList());
        } else if (!isMore) {
            mAdapter.setNewData(resp.getList());
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("您还没有配置过部门哦").setTips("点击新建部门进行添加").create());
        }

        if (!CommonUtils.isEmpty(resp.getList())) {
            mrefreshLayout.setEnableLoadMore(resp.getList().size() == mPresent.getPageSize());
        }

    }

    @Override
    public String getDepartName() {
        return mSearch.getSearchContent();
    }

    @Override
    public void addSuccess() {
        showToast("新增部门成功");
        mPresent.refresh();
    }

    @Override
    public void removeSuccess(String id) {
        showToast("删除成功");
        mSelectIds.remove(id);
        mPresent.refresh();
    }

    @Override
    public void modifySuccess() {
        showToast("编辑成功");
        mPresent.refresh();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mrefreshLayout.closeHeaderOrFooter();
    }

    private class DepartAdapter extends BaseQuickAdapter<DepartmentListResp.DepartmentLisBean, BaseViewHolder> {
        private boolean isEdt = false;

        public DepartAdapter(@Nullable List<DepartmentListResp.DepartmentLisBean> data) {
            super(R.layout.list_item_staff_department, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            holder.addOnClickListener(R.id.img_edt);
            holder.addOnClickListener(R.id.img_remove);
            return holder;
        }

        @Override
        protected void convert(BaseViewHolder helper, DepartmentListResp.DepartmentLisBean item) {
            helper.setGone(R.id.checkbox, !isEdt)
                    .setGone(R.id.img_edt, isEdt)
                    .setGone(R.id.img_remove, isEdt);
            helper.setText(R.id.txt_name, item.getDeptName());
            if (mSelectIds.contains(item.getId())) {
                helper.setTextColor(R.id.txt_name, Color.parseColor("#222222"));
                ((CheckBox) helper.getView(R.id.checkbox)).setChecked(true);
            } else {
                helper.setTextColor(R.id.txt_name, Color.parseColor("#999999"));
                ((CheckBox) helper.getView(R.id.checkbox)).setChecked(false);
            }
        }

        public void isEdtModal(boolean isEdt) {
            this.isEdt = isEdt;
            notifyDataSetChanged();
        }

        public boolean isEdt() {
            return isEdt;
        }
    }
}
