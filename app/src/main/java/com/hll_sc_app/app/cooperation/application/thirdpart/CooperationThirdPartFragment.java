package com.hll_sc_app.app.cooperation.application.thirdpart;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.application.BaseCooperationApplicationFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 合作采购商-我收到的申请-第三方申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
public class CooperationThirdPartFragment extends BaseCooperationApplicationFragment implements CooperationThirdPartContract.ICooperationThirdPartView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    Unbinder unbinder;
    private String mSearchParam;
    private EmptyView mEmptyView;
    private EmptyView mNetEmptyView;
    private ThirdPartListAdapter mAdapter;
    private CooperationThirdPartContract.ICooperationThirdPartPresenter mPresenter;

    public static CooperationThirdPartFragment newInstance() {
        return new CooperationThirdPartFragment();
    }

    public static String getResourceType(String resourceType) {
        String content = null;
        if (TextUtils.equals(resourceType, "1")) {
            // 哗啦啦供应链
            content = "哗啦啦供应链";
        } else if (TextUtils.equals(resourceType, "2")) {
            // 天财供应链
            content = "天财供应链";
        } else if (TextUtils.equals(resourceType, "0")) {
            // 二十二城
            content = BuildConfig.ODM_NAME;
        } else if (TextUtils.equals(resourceType, "3")) {
            // 平台合作转三方
            content = "平台合作转三方";
        }
        return content;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = CooperationThirdPartPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cooperation_application_third, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreThirdPartList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryCooperationThirdPartList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(),
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mEmptyView = EmptyView.newBuilder(getActivity()).setTipsTitle("喔唷，居然是「 空 」的").create();
        mNetEmptyView = EmptyView.newBuilder(requireActivity()).setOnClickListener(() -> {
            setForceLoad(true);
            lazyLoad();
        }).create();
        mAdapter = new ThirdPartListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ThirdPartyPurchaserBean bean = (ThirdPartyPurchaserBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            int id = view.getId();
            if (id == R.id.txt_status && TextUtils.equals(bean.getStatus(), "0")) {
                toAgree(bean);
            } else if (id == R.id.content) {
                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_APPLICATION_THIRD_PART_DETAIL,
                    bean.getId());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void toAgree(ThirdPartyPurchaserBean bean) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            // 1-同意 2-拒绝 3-解除
            .put("flag", "1")
            .put("id", bean.getId())
            .put("plateSupplierID", UserConfig.getGroupID())
            .put("actionBy", userBean.getEmployeeName())
            .create();
        mPresenter.editCooperationThirdPartStatus(req);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showError(UseCaseException exception) {
        super.showError(exception);
        if (exception.getLevel() == UseCaseException.Level.NET) {
            mAdapter.setEmptyView(mNetEmptyView);
        }
    }

    @Override
    public void showCooperationThirdPartList(List<ThirdPartyPurchaserBean> list, boolean append, int total) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
    }

    @Override
    public String getSearchParam() {
        return mSearchParam;
    }

    @Override
    public void toSearch(String searchParam) {
        mSearchParam = searchParam;
        setForceLoad(true);
        lazyLoad();
    }

    @Override
    public void refresh() {
        setForceLoad(true);
        lazyLoad();
    }

    private static class ThirdPartListAdapter extends BaseQuickAdapter<ThirdPartyPurchaserBean, BaseViewHolder> {

        ThirdPartListAdapter() {
            super(R.layout.item_cooperation_purchaser_application_third);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.txt_status).addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, ThirdPartyPurchaserBean item) {
            helper
                .setGone(R.id.img_readStatus, TextUtils.equals(item.getReadStatus(), "0"))
                .setText(R.id.txt_groupName, item.getGroupName())
                .setText(R.id.txt_resourceType, getResourceType(item.getResourceType()));
            setStatus(helper, item);
        }

        private void setStatus(BaseViewHolder helper, ThirdPartyPurchaserBean item) {
            TextView txtStatus = helper.getView(R.id.txt_status);
            switch (item.getStatus()) {
                case "0":
                    // 待同意
                    txtStatus.setBackgroundResource(R.drawable.bg_button_mid_solid_primary);
                    txtStatus.setTextColor(0xFFFFFFFF);
                    txtStatus.setText(R.string.agree);
                    break;
                case "1":
                    // 未同意
                    txtStatus.setBackground(new ColorDrawable());
                    txtStatus.setTextColor(0xFFED5655);
                    txtStatus.setText("未同意");
                    break;
                case "2":
                    // 已同意
                    txtStatus.setBackground(new ColorDrawable());
                    txtStatus.setTextColor(0xFFAEAEAE);
                    txtStatus.setText("已同意");
                    break;
                default:
                    break;
            }
        }
    }
}