package com.hll_sc_app.app.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
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
    protected String mSearchWords;
    protected String mKey;
    protected Bundle mExtra;
    private ISearchContract.ISearchStrategy mStrategy;
    private SearchAdapter mAdapter;

    public static void start(Activity context, String searchWords, String key) {
        start(context, searchWords, key, null);
    }

    public static void start(Activity context, String searchWords, String key, Bundle args) {
        start(RouterConfig.SEARCH, context, searchWords, key, args);
    }

    /**
     * @param url         路由地址
     * @param searchWords 搜索词
     * @param key         搜索 MAP 中对应的键值
     * @param extra       附加参数
     */
    public static void start(String url, Activity context, String searchWords, String key, Bundle extra) {
        ARouter.getInstance()
                .build(url)
                .withString("object0", searchWords)
                .withString("object1", key)
                .withBundle("extra", extra)
                .setProvider(new LoginInterceptor())
                .navigation(context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mSearchWords = getIntent().getStringExtra("object0");
        mKey = getIntent().getStringExtra("object1");
        mExtra = getIntent().getBundleExtra("extra");
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
        if (isSearchByResult()) {
            if (mAdapter.getData().size() == 0) {
                showToast("没有符合的搜索结果，请更换搜索词");
                return;
            }
            searchByResult(0);
        } else {
            Intent intent = new Intent();
            intent.putExtra("name", mTitleBar.getSearchContent());
            beforeFinish(intent);
            setResult(Constants.SEARCH_RESULT_CODE, intent);
            onBackPressed();
        }
    }

    /**
     * 判断是否通过搜索结果第一项进行搜索
     */
    protected boolean isSearchByResult() {
        return mStrategy != null && mStrategy.isSearchByResult();
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

    private void searchByResult(int position) {
        NameValue item = mAdapter.getItem(position);
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
        searchByResult(position);
    }

    @Override
    public Bundle getExtra() {
        return mExtra;
    }
}
