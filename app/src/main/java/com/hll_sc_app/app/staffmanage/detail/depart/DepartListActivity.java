package com.hll_sc_app.app.staffmanage.detail.depart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.StaffDepartListSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.StaffDepartListEvent;
import com.hll_sc_app.bean.staff.DepartmentListResp;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "ids")
    ArrayList<String> ids;

    private Unbinder unbinder;
    private InputDialog mAddDiolog;
    private Set<String> mSelectIds;
    private DepartAdapter mAdpater;
    private IDepartListContract.IPresent mPresent;

    public static void start(ArrayList<String> ids) {
        ARouter.getInstance()
                .build(RouterConfig.ACTIVITY_SELECT_DEPARTMENT_LIST)
                .withStringArrayList("ids", ids)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_depart_list);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this);
        mPresent = DepartListPresent.newInstance();
        mPresent.register(this);
        initView();
        mPresent.queryDepartments(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSelectIds = new HashSet<>(ids);
        SpannableString string = new SpannableString("同一位员工支持多选部门噢；点击这里这里可以新建部门");
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#5695D2")),
                string.length() - 4, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new UnderlineSpan(), string.length() - 4, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtAdd.setText(string);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdpater = new DepartAdapter(null);
        mAdpater.setOnItemClickListener((adapter, view, position) -> {
            DepartmentListResp.DepartmentLisBean bean = mAdpater.getItem(position);
            if (mSelectIds.contains(bean.getId())) {
                mSelectIds.remove(bean.getId());
            } else {
                mSelectIds.add(bean.getId());
            }
            mAdpater.notifyDataSetChanged();
        });
        mRecyclerView.setAdapter(mAdpater);
        mSearch.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, StaffDepartListSearch.class.getSimpleName());
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
    }

    @Subscribe(sticky = true)
    public void onEvent(StaffDepartListEvent event) {
        mSearch.showSearchContent(!TextUtils.isEmpty(event.getContent()), event.getContent());
    }

    @OnClick({R.id.txt_add})
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
            default:
                break;
        }
    }

    @Override
    public void querySuccess(DepartmentListResp resp) {

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

    private class DepartAdapter extends BaseQuickAdapter<DepartmentListResp.DepartmentLisBean, BaseViewHolder> {
        public DepartAdapter(@Nullable List<DepartmentListResp.DepartmentLisBean> data) {
            super(R.layout.list_item_staff_department, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DepartmentListResp.DepartmentLisBean item) {
            helper.setText(R.id.txt_name, item.getDeptName());
            if (mSelectIds.contains(item.getId())) {
                helper.setTextColor(R.id.txt_name, Color.parseColor("#222222"));
                ((CheckBox) helper.getView(R.id.checkbox)).setChecked(true);
            } else {
                helper.setTextColor(R.id.txt_name, Color.parseColor("#999999"));
                ((CheckBox) helper.getView(R.id.checkbox)).setChecked(false);
            }
        }
    }
}
