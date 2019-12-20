package com.hll_sc_app.app.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchTitleBar;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */
@Route(path = RouterConfig.SEARCH)
public class SearchActivity extends BaseLoadActivity implements ISearchContract.ISearchView, BaseQuickAdapter.OnItemClickListener {
    protected static final int REQ_CODE = 0x844;
    @BindView(R.id.as_title_bar)
    protected SearchTitleBar mTitleBar;
    @BindView(R.id.as_list)
    RecyclerView mListView;
    @Autowired(name = "object0")
    protected String mSearchWords;
    @Autowired(name = "object1")
    protected String mKey;
    private ISearchContract.ISearchStrategy mStrategy;
    private SearchAdapter mAdapter;
    /**
     * @param searchWords 搜索词
     * @param key         搜索MAP中对应的键值
     */
    public static void start(Activity context, String searchWords, String key) {
        Object[] args = {searchWords, key};
        RouterUtil.goToActivity(RouterConfig.SEARCH, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        beforeInitView();
        initView();
        afterInitView();
    }

    /**
     * 初始化视图前调用此方法，用来获取页面传参，创建列表适配器需要的空布局
     */
    protected void beforeInitView() {
        mStrategy = SearchFactory.getSearchStrategy(mKey);
        if (mStrategy == null) return;
        mTitleBar.setHint(mStrategy.getEditHint());
    }

    /**
     * 初始化视图后调用此方法，用来进行接口初始化，搜索去抖
     */
    protected void afterInitView() {
        if (mStrategy == null) return;
        ISearchContract.ISearchPresenter presenter = mStrategy.getSearchPresenter();
        if (presenter != null) {
            presenter.register(this);
            mTitleBar.subscribe(presenter::requestSearch);
            presenter.start();
        }
    }

    private void initView() {
        mAdapter = new SearchAdapter(getLayoutRes());
        mAdapter.setEmptyView(getEmptyView());
        mAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee),
                UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mTitleBar.updateSearchWords(mSearchWords);
        mTitleBar.setOnSearchListener(this::search);
    }

    protected View getEmptyView() {
        return EmptyView.newBuilder(this)
                .setTips(mStrategy.getEmptyTip())
                .setImage(mStrategy.getEmptyImage())
                .create();
    }

    /**
     * @return 列表条目布局
     */
    protected int getLayoutRes() {
        return mStrategy.getLayoutRes();
    }

    private void search() {
        if (mStrategy != null && mStrategy.getSearchPresenter() != null && mStrategy.isSearchByResult()) {
            if (mAdapter.getData().size() == 0) {
                showToast("没有符合的搜索结果，请更换搜索词");
                return;
            }
            searchByResult(mAdapter, 0);
        } else {
            Intent intent = new Intent();
            intent.putExtra("name", mTitleBar.getSearchContent());
            beforeFinish(intent);
            setResult(Constants.SEARCH_RESULT_CODE, intent);
            onBackPressed();
        }
    }

    /**
     * 关闭页面之前调用此方法，用来回传参数
     */
    protected void beforeFinish(Intent intent) {
        // no-op
    }

    @Override
    public final void refreshSearchData(List<NameValue> list) {
        mAdapter.setNewData(list, mTitleBar.getSearchContent());
    }

    private void searchByResult(BaseQuickAdapter adapter, int position) {
        NameValue item = (NameValue) adapter.getItem(position);
        if (item != null) {
            Intent intent = new Intent();
            intent.putExtra("name", item.getName());
            intent.putExtra("value", item.getValue());
            beforeFinish(intent);
            setResult(Constants.SEARCH_RESULT_CODE, intent);
            onBackPressed();
        }
    }

    @Override
    public final void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        searchByResult(adapter, position);
    }
}
