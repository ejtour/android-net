package com.hll_sc_app.app.goods.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从商品库导入
 *
 * @author zhuyingsong
 * @date 2019/6/27
 */
@Route(path = RouterConfig.GOODS_TEMPLATE_LIST, extras = Constant.LOGIN_EXTRA)
public class GoodsTemplateListActivity extends BaseLoadActivity implements GoodsTemplateListContract.IGoodsTemplateListView {
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.txt_searchContent)
    TextView mTxtSearchContent;
    @BindView(R.id.img_searchClear)
    ImageView mImgSearchClear;
    @BindView(R.id.rl_search)
    FrameLayout mRlSearch;
    @BindView(R.id.ll_filter)
    LinearLayout mLlFilter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    @BindView(R.id.text_commit)
    TextView mTextCommit;
    @BindView(R.id.fl_bottom)
    RelativeLayout mFlBottom;
    private GoodsTemplateAdapter mAdapter;
    private GoodsTemplateListPresenter mPresenter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_template_list);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsTemplateListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreGoodsTemplateList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsTemplateList(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new GoodsTemplateAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean goodsBean = (GoodsBean) adapter.getItem(position);
            if (goodsBean != null) {
                goodsBean.setCheck(!goodsBean.isCheck());
                adapter.notifyItemChanged(position);
            }
            showBottomCount();
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有商品模板数据").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showBottomCount() {
        int i = 0;
        if (mAdapter != null && !CommonUtils.isEmpty(mAdapter.getData())) {
            for (GoodsBean bean : mAdapter.getData()) {
                if (bean.isCheck()) {
                    i++;
                }
            }
        }
        mTextCommit.setEnabled(i > 0);
        mTextCommit.setText(String.format(Locale.getDefault(), "确定选择（%d）", i));
    }

    @OnClick({R.id.img_close, R.id.text_commit, R.id.img_allCheck, R.id.txt_allCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_commit:
                break;
            case R.id.img_close:
                finish();
                break;
            case R.id.img_allCheck:
            case R.id.txt_allCheck:
                mImgAllCheck.setSelected(!mImgAllCheck.isSelected());
                checkAll(mImgAllCheck.isSelected());
                break;
            default:
                break;
        }
    }

    private void checkAll(boolean check) {
        if (mAdapter != null && !CommonUtils.isEmpty(mAdapter.getData())) {
            for (GoodsBean bean : mAdapter.getData()) {
                bean.setCheck(check);
            }
            mAdapter.notifyDataSetChanged();
        }
        showBottomCount();
    }

    @Override
    public void showGoodsTemplateList(List<GoodsBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    class GoodsTemplateAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        GoodsTemplateAdapter() {
            super(R.layout.item_goods_template_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean bean) {
            helper.getView(R.id.img_check).setSelected(bean.isCheck());
            ((GlideImageView) helper.setText(R.id.txt_specsContent, !CommonUtils.isEmpty(bean.getSpecs()) ?
                "规格：" + bean.getSpecs().get(0).getSpecContent() : "")
                .setText(R.id.txt_productName, bean.getProductName())
                .getView(R.id.img_imgUrl)).setImageURL(bean.getImgUrl());
        }
    }
}
