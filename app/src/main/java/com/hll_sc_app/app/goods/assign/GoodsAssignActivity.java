package com.hll_sc_app.app.goods.assign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.assign.detail.GoodsAssignDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.CountlyMgr;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
@Route(path = RouterConfig.GOODS_ASSIGN)
public class GoodsAssignActivity extends BaseLoadActivity implements IGoodsAssignContract.IGoodsAssignView, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.srl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.srl_list_view)
    RecyclerView mListView;
    @BindView(R.id.srl_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "type")
    GoodsAssignType mAssignType;
    private IGoodsAssignContract.IGoodsAssignPresenter mPresenter;
    private GoodsAssignAdapter mAdapter;
    private EmptyView mEmptyView;
    private GoodsAssignBean mCurBean;

    /**
     * @param type 商品分配类型
     */
    public static void start(GoodsAssignType type) {
        CountlyMgr.recordView(type.getTitle());
        ARouter.getInstance().build(RouterConfig.GOODS_ASSIGN)
                .setProvider(new LoginInterceptor())
                .withSerializable("type", type)
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_refresh_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresenter = GoodsAssignPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightText("新增");
        mTitleBar.setHeaderTitle(mAssignType.getTitle());
        mTitleBar.setRightBtnClick(this::add);
        mAdapter = new GoodsAssignAdapter();
        int space = UIUtils.dip2px(10);
        mListView.setPadding(0, space, 0, space);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        mListView.setAdapter(mAdapter);
        mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<GoodsAssignBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTipsTitle(String.format("您还没有%s哦", mAssignType.getDetailLabel()));
                mEmptyView.setTips("点击右上角新增添加");
            }
            mAdapter.setNewData(list);
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    void add(View view) {
        if (!RightConfig.checkRight(getString(R.string.right_targetedSale_add))) {
            showToast(getString(R.string.right_tips));
            return;
        }
        GoodsAssignBean bean = new GoodsAssignBean();
        bean.setAssignType(mAssignType);
        RouterUtil.goToActivity(RouterConfig.SELECT_GROUP_GOODS_ASSIGN, bean);
    }

    @Override
    public void delSuccess() {
        showToast("删除成功");
        if (mAdapter.getData().size() == 1) {
            setData(new ArrayList<>(), false);
        } else if (mCurBean != null) {
            mAdapter.removeData(mCurBean);
        }
    }

    @Override
    public int getType() {
        return mAssignType.getType();
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mCurBean = mAdapter.getItem(position);
        if (mCurBean == null) return;
        if (view.getId() == R.id.iga_root) {
            mCurBean.setAssignType(mAssignType);
            GoodsAssignDetailActivity.start(mCurBean);
        } else if (view.getId() == R.id.iga_del) {
            mPresenter.del(mCurBean.getId());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Object object = intent.getParcelableExtra("parcelable");
        if (object instanceof GoodsAssignBean) {
            GoodsAssignBean data = (GoodsAssignBean) object;
            if (mCurBean == null || !TextUtils.equals(mCurBean.getId(), data.getId())) {
                mPresenter.start();
            } else {
                mAdapter.replaceData(mCurBean, data);
            }
        }
    }
}
