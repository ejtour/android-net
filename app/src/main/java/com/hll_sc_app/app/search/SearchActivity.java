package com.hll_sc_app.app.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.disposables.Disposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */
@Route(path = RouterConfig.SEARCH)
public class SearchActivity extends BaseLoadActivity implements ISearchContract.ISearchView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.as_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.as_search_clear)
    ImageView mSearchClear;
    @BindView(R.id.as_list)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mSearchWords;
    @Autowired(name = "object1")
    String mKey;
    private Disposable mDisposable;
    private ISearchContract.ISearchStrategy mStrategy;
    private ObservableEmitter<String> mEmitter;

    /**
     * @param searchWords 搜索词
     * @param key         搜索MAP中对应的键值
     */
    public static void start(String searchWords, String key) {
        RouterUtil.goToActivity(RouterConfig.SEARCH, searchWords, key);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mStrategy = SearchFactory.getSearchStrategy(mKey);
        if (mStrategy == null) return;
        initView();
        initData();
    }

    private void initData() {
        ISearchContract.ISearchPresenter presenter = mStrategy.getSearchPresenter();
        if (presenter != null) {
            presenter.register(this);
            mDisposable = getTextObservable().subscribe(presenter::requestSearch);
            presenter.start();
        }
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null)
            mDisposable.dispose();
        super.onDestroy();
    }

    private Observable<String> getTextObservable() {
        Observable<String> observable = Observable.create(emitter -> mEmitter = emitter);
        return observable.filter(s -> s.length() > 0).debounce(500, TimeUnit.MILLISECONDS);
    }

    private void initView() {
        mSearchEdit.setHint(mStrategy.getEditHint());
        mSearchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
            }
            return true;
        });
        SearchAdapter adapter = new SearchAdapter(mStrategy.getLayoutRes());
        adapter.setEmptyView(EmptyView.newBuilder(this)
                .setTips(mStrategy.getEmptyTip())
                .setImage(mStrategy.getEmptyImage())
                .create());
        adapter.setOnItemClickListener(this);
        mListView.setAdapter(adapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee),
                UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        updateSearchEdit(mSearchWords);
    }


    @OnClick(R.id.as_search_button)
    public void search() {
        String trim = mSearchEdit.getText().toString().trim();
        mStrategy.onSearch(trim);
        close();
    }

    private void updateSearchEdit(String content) {
        mSearchEdit.setText(content);
        if (content != null) {
            mSearchEdit.setSelection(content.length());
        }
    }

    @OnClick(R.id.as_close)
    public void close() {
        finish();
    }

    @OnClick(R.id.as_search_clear)
    public void clear() {
        mSearchEdit.setText("");
    }

    @OnTextChanged(R.id.as_search_edit)
    public void onTextChanged(CharSequence s) {
        if (s.toString().length() > 0) {
            mSearchClear.setVisibility(View.VISIBLE);
        } else {
            mSearchClear.setVisibility(View.GONE);
            ((SearchAdapter) mListView.getAdapter()).setNewData(null);
        }
        if (mEmitter != null)
            mEmitter.onNext(s.toString().trim());
    }

    @Override
    public void refreshSearchData(List<NameValue> list) {
        ((SearchAdapter) mListView.getAdapter()).setNewData(list, mSearchEdit.getText().toString().trim());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mStrategy.onClick((NameValue) adapter.getItem(position));
        close();
    }
}
