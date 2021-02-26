package com.hll_sc_app.app.aptitude.goods;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.IAptitudeCallback;
import com.hll_sc_app.app.aptitude.goods.detail.AptitudeGoodsDetailActivity;
import com.hll_sc_app.app.aptitude.goods.search.AptitudeGoodsSearchActivity;
import com.hll_sc_app.app.search.stratery.AptitudeGoodsSearch;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/9
 */

public class AptitudeGoodsFragment extends BaseLazyFragment implements IAptitudeGoodsContract.IAptitudeGoodsView, IAptitudeCallback {
    @BindView(R.id.fag_search_view)
    SearchView mSearchView;
    @BindView(R.id.fag_list_view)
    RecyclerView mListView;
    @BindView(R.id.fag_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private IAptitudeGoodsContract.IAptitudeGoodsPresenter mPresenter;
    private EmptyView mEmptyView;
    private AptitudeGoodsAdapter mAdapter;
    private final BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private int mSearchType;
    private AptitudeBean mCurBean;

    public static AptitudeGoodsFragment newInstance() {
        return new AptitudeGoodsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = AptitudeGoodsPresenter.newInstance();
        mPresenter.register(this);
        mReq.put("groupID", UserConfig.getGroupID());
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_aptitude_goods, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mSearchView.setSearchBackgroundColor(R.drawable.bg_white_radius_15_solid);
        mSearchView.setHint("请输入资质名称、商品名称查询");
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                AptitudeGoodsSearchActivity.start(requireActivity(),
                        searchContent,
                        AptitudeGoodsSearch.class.getSimpleName(), mSearchType);
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mSearchType = 0;
                    mReq.put("aptitudeName", "");
                    mReq.put("aptitudeType", "");
                    mReq.put("searchKey", "");
                }
                mReq.put(mSearchType == 0 ? "aptitudeName" : "searchKey", searchContent);
                mReq.put(mSearchType == 1 ? "aptitudeName" : "searchKey", "");
                mPresenter.load(true);
            }
        });
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee), ViewUtils.dip2px(requireContext(), 0.5f));
        decor.setLineMargin(UIUtils.dip2px(85), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(requireActivity()));
        mAdapter = new AptitudeGoodsAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            if (view.getId() == R.id.iag_root) {
                AptitudeGoodsDetailActivity.start(requireActivity(), mCurBean);
            } else if (view.getId() == R.id.iag_del) {
                delConfirm();
            }
        });
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.load(false));
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void delConfirm() {
        SuccessDialog.newBuilder(requireActivity())
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确认要删除该商品资质吗")
                .setMessage(String.format("“%s”已有商品使用\n删除后不可恢复，请谨慎操作", mCurBean.getAptitudeName()))
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        mPresenter.delAptitude(mCurBean.getId());
                    }
                }, "我再看看", "确认删除")
                .create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            String searchValue = data.getStringExtra("value");
            mSearchType = data.getIntExtra("index", 0);
            mReq.put("aptitudeType", mSearchType == 0 ? searchValue : null);
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
        if (requestCode == AptitudeGoodsDetailActivity.REQ_CODE && resultCode == Activity.RESULT_OK) {
            mPresenter.load(true);
        }
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setData(List<AptitudeBean> list) {
        if (CommonUtils.isEmpty(list)) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTipsTitle("您还没有设置商品资质噢");
        }
        mAdapter.setNewData(list);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void delSuccess() {
        if (mCurBean != null && mAdapter.getData().size() > 1) {
            mAdapter.remove(mAdapter.getData().indexOf(mCurBean));
        } else {
            mPresenter.load(true);
        }
    }

    @Override
    public void expireTip(String msg) {
        TipsDialog.newBuilder(requireActivity())
                .setMessage(msg)
                .setButton((dialog, item) -> dialog.dismiss(), "知道了")
                .setTitle("资质到期")
                .create().show();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @OnClick(R.id.fag_time_filter)
    void clickFilter(View view) {
        view.setSelected(!view.isSelected());
        mReq.put("expire", view.isSelected() ? "Expire" : "");
        mPresenter.load(true);
    }

    @Override
    public void rightClick() {
        AptitudeGoodsDetailActivity.start(requireActivity(), null);
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public void setEditable(boolean editable) {
        // no-op
    }
}
