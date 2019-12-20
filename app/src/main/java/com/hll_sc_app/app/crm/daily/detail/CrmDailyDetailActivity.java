package com.hll_sc_app.app.crm.daily.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyReplyBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.daily.CrmDailyDetailHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_HH_MM_SS;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/13
 */
@Route(path = RouterConfig.CRM_DAILY_DETAIL)
public class CrmDailyDetailActivity extends BaseLoadActivity implements ICrmDailyDetailContract.ICrmDailyDetailView {

    private static final int REQ_CODE = 0x636;
    @BindView(R.id.cdd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.cdd_edit)
    EditText mEdit;
    @BindView(R.id.cdd_bottom_group)
    Group mBottomGroup;
    @BindView(R.id.cdd_list_view)
    RecyclerView mListView;
    @BindView(R.id.cdd_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "parcelable")
    DailyBean mBean;
    private CrmDailyDetailHeader mHeader;
    private ICrmDailyDetailContract.ICrmDailyDetailPresenter mPresenter;
    private CrmDailyDetailAdapter mAdapter;
    private boolean mHasChanged;

    /**
     * @param bean 日报数据
     */
    public static void start(Activity context, DailyBean bean) {
        RouterUtil.goToActivity(RouterConfig.CRM_DAILY_DETAIL, context, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_daily_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = CrmDailyDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        UserBean user = GreenDaoUtils.getUser();
        if (!user.getEmployeeID().equals(mBean.getEmployeeID())) { // 如果不是日报作者
            mTitleBar.setHeaderTitle(mBean.getEmployeeName());
            mBottomGroup.setVisibility(View.VISIBLE);
        }
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mHeader = new CrmDailyDetailHeader(this);
        mHeader.setData(mBean);
        mAdapter = new CrmDailyDetailAdapter();
        mAdapter.setHeaderView(mHeader);
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
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) setResult(RESULT_OK);
        super.onBackPressed();
    }

    @OnClick(R.id.cdd_send)
    public void onViewClicked() {
        String content = mEdit.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("回复内容不能为空");
            return;
        }
        mPresenter.send(content);
    }

    @Override
    public void setData(DailyBean data) {
        if (mBean.getReadStatus() != data.getReadStatus()) {
            mHasChanged = true;
        }
        mBean = data;
        mHeader.setData(mBean);
    }

    @Override
    public void setData(List<DailyReplyBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void success() {
        mHasChanged = true;
        DailyReplyBean bean = new DailyReplyBean();
        UserBean user = GreenDaoUtils.getUser();
        bean.setEmployeeName(user.getEmployeeName());
        bean.setEmployeeID(user.getEmployeeID());
        bean.setReply(mEdit.getText().toString().trim());
        bean.setCreateTime(CalendarUtils.format(new Date(), FORMAT_HH_MM_SS));
        mAdapter.addData(0, bean);
        mBean.setReplyNum(mBean.getReplyNum() + 1);
        mHeader.updateNum(mBean.getReplyNum());
        mEdit.setText("");
    }

    @Override
    public boolean isSend() {
        return TextUtils.isEmpty(mBean.getReportID());
    }

    @Override
    public String getReplyID() {
        return mBean.getReplyID();
    }

    @Override
    public String getID() {
        return mBean.getId();
    }
}
