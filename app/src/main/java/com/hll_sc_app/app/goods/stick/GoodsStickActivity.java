package com.hll_sc_app.app.goods.stick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.GoodsStickSearchEvent;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品置顶管理
 *
 * @author zhuyingsong
 * @date 2019/7/1
 */
@Route(path = RouterConfig.GOODS_STICK_MANAGE, extras = Constant.LOGIN_EXTRA)
public class GoodsStickActivity extends BaseLoadActivity implements GoodsStickContract.IGoodsStickView {
    public static final int MAX_SELECT_COUNT = 5;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.recyclerView_level1)
    RecyclerView mRecyclerViewLevel1;
    @BindView(R.id.recyclerView_product)
    RecyclerView mRecyclerViewProduct;
    @BindView(R.id.txt_checkNum)
    TextView mTxtCheckNum;
    @BindView(R.id.text_save)
    TextView mTextSave;
    @BindView(R.id.fl_bottom)
    RelativeLayout mFlBottom;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Map<String, List<GoodsBean>> mSelectMap;
    private GoodsStickPresenter mPresenter;
    private CustomCategoryAdapter mCategoryAdapter;
    private EmptyView mEmptyView;
    private GoodsTopListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_stick);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
        mPresenter = GoodsStickPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mSelectMap = new HashMap<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_GOODS_TOP);
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryGoodsList(true);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("该分类暂无商品数据").create();
        mRecyclerViewLevel1.setLayoutManager(new LinearLayoutManager(this));
        mCategoryAdapter = new CustomCategoryAdapter();
        mRecyclerViewLevel1.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            CustomCategoryBean bean = (CustomCategoryBean) adapter.getItem(position);
            if (bean == null || bean.isChecked()) {
                return;
            }
            List<CustomCategoryBean> beanList = mCategoryAdapter.getData();
            if (CommonUtils.isEmpty(beanList)) {
                return;
            }
            for (CustomCategoryBean customCategoryBean : beanList) {
                customCategoryBean.setChecked(false);
            }
            bean.setChecked(true);
            adapter.notifyDataSetChanged();
            mPresenter.queryGoodsList(true);
        });
        mRecyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProduct.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mAdapter = new GoodsTopListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GoodsBean bean = (GoodsBean) adapter.getItem(position);
            if (bean != null) {
                if (bean.isCheck()) {
                    // 之前状态为选中，再次点击状态改为未选中
                    remove(bean);
                    adapter.notifyItemChanged(position);
                    showBottomCount(getCurrentSelectCount());
                } else {
                    // 判断当前选中数量
                    int count = getCurrentSelectCount();
                    if (count < MAX_SELECT_COUNT) {
                        add(bean);
                        adapter.notifyItemChanged(position);
                        showBottomCount(count + 1);
                    } else {
                        showToast("每个分类最多置顶5个");
                    }
                }
            }
        });
        mRecyclerViewProduct.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreGoodsList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsList(false);
            }
        });
    }

    private void remove(GoodsBean goodsBean) {
        List<GoodsBean> goodsBeans = mSelectMap.get(goodsBean.getShopProductCategorySubID());
        if (!CommonUtils.isEmpty(goodsBeans)) {
            goodsBean.setCheck(false);
            goodsBeans.remove(goodsBean);
        }
    }

    /**
     * 显示底部已选数量
     *
     * @param count 数量
     */
    private void showBottomCount(int count) {
        mTxtCheckNum.setText(String.format(Locale.getDefault(), "已选：%d", count));
    }

    /**
     * 得到当前分类选中的数目
     *
     * @return 选中数
     */
    private int getCurrentSelectCount() {
        int count = 0;
        List<GoodsBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (GoodsBean bean : list) {
                if (bean.isCheck()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void add(GoodsBean goodsBean) {
        List<GoodsBean> goodsBeans = mSelectMap.get(goodsBean.getShopProductCategorySubID());
        if (goodsBeans == null) {
            goodsBeans = new ArrayList<>();
        }
        goodsBean.setCheck(true);
        goodsBeans.add(goodsBean);
        mSelectMap.put(goodsBean.getShopProductCategorySubID(), goodsBeans);
    }

    @Subscribe
    public void onEvent(GoodsStickSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showCustomCategoryList(CustomCategoryResp resp) {
        if (resp != null) {
            mCategoryAdapter.setNewData(resp.getList2());
            // 默认去选中第一个
            if (!CommonUtils.isEmpty(mCategoryAdapter.getData())) {
                CustomCategoryBean bean = mCategoryAdapter.getItem(0);
                if (bean != null) {
                    bean.setChecked(true);
                    mCategoryAdapter.notifyItemChanged(0);
                    mPresenter.queryGoodsList(true);
                }
            }
        } else {
            mCategoryAdapter.setNewData(null);
        }
    }

    @Override
    public void showList(List<GoodsBean> list, boolean append) {
        if (!CommonUtils.isEmpty(list)) {
            for (GoodsBean goodsBean : list) {
                if (contains(goodsBean)) {
                    goodsBean.setCheck(true);
                } else if (goodsBean.getTop() >= 1) {
                    add(goodsBean);
                }
            }
        }
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到相关商品");
        } else {
            mEmptyView.setTips("该分类暂无商品数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == GoodsListReq.PAGE_SIZE);
        showBottomCount(getCurrentSelectCount());
    }

    private boolean contains(GoodsBean goodsBean) {
        boolean contains = false;
        List<GoodsBean> goodsBeans = mSelectMap.get(goodsBean.getShopProductCategorySubID());
        if (!CommonUtils.isEmpty(goodsBeans)) {
            contains = goodsBeans.contains(goodsBean);
        }
        return contains;
    }

    @Override
    public String getShopProductCategorySubId() {
        String categorySubId = null;
        List<CustomCategoryBean> beanList = mCategoryAdapter.getData();
        if (!CommonUtils.isEmpty(beanList)) {
            for (CustomCategoryBean bean : beanList) {
                if (bean.isChecked()) {
                    categorySubId = bean.getId();
                    break;
                }
            }
        }
        return categorySubId;
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void saveSuccess() {
        showToast("商品置顶保存成功");
        finish();
    }

    @OnClick({R.id.img_close, R.id.text_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.text_save:
                toSave();
                break;
            default:
                break;
        }
    }

    private void toSave() {
        if (mSelectMap == null || mSelectMap.isEmpty()) {
            showToast("您还没有选中");
            return;
        }
        mPresenter.goods2Top(mSelectMap);
    }

    class CustomCategoryAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {

        CustomCategoryAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getCategoryName());
            txtCategoryName.setSelected(item.isChecked());
        }
    }
}
