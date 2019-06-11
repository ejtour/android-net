package com.hll_sc_app.app.order.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.search.OrderSearchBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;

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
 * @since 2019/6/10
 */
@Route(path = RouterConfig.ROOT_HOME_ORDER_SEARCH)
public class OrderSearchActivity extends BaseLoadActivity implements IOrderSearchContract.IOrderSearchView {

    public static void start(String searchWords) {
        RouterUtil.goToActivity(RouterConfig.ROOT_HOME_ORDER_SEARCH, searchWords);
    }

    @BindView(R.id.aos_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.aos_search_clear)
    ImageView mSearchClear;
    @BindView(R.id.aos_list)
    RecyclerView mListView;
    @Autowired(name = "object")
    String mSearchWords;
    private Disposable mDisposable;
    private IOrderSearchContract.IOrderSearchPresenter mPresenter;
    private ObservableEmitter<String> mEmitter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mDisposable = getTextObservable().subscribe(this::gotoSearch);
        mPresenter = OrderSearchPresenter.newInstance();
        mPresenter.register(this);
        initView();
    }

    private void initView() {
        OrderSearchAdapter adapter = new OrderSearchAdapter();
        adapter.setEmptyView(EmptyView.newBuilder(this)
                .setTips("您可以输入客户名称查找采购商门店")
                .setImage(R.drawable.ic_empty_shop_view)
                .create());
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            OrderSearchBean item = (OrderSearchBean) adapter1.getItem(position);
            if (item == null) {
                return;
            }
            updateSearchEdit(item.getName());
            search();
        });
        mListView.setAdapter(adapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        updateSearchEdit(mSearchWords);
    }

    private void updateSearchEdit(String content) {
        mSearchEdit.setText(content);
        if (content != null) {
            mSearchEdit.setSelection(content.length());
        }
    }

    @Override
    protected void onDestroy() {
        mDisposable.dispose();
        super.onDestroy();
    }

    private void gotoSearch(String keywords) {
        mPresenter.requestSearch(keywords);
    }

    private Observable<String> getTextObservable() {
        Observable<String> observable = Observable.create(emitter -> mEmitter = emitter);
        return observable.filter(s -> s.length() > 0).debounce(500, TimeUnit.MILLISECONDS);
    }

    @OnClick(R.id.aos_search_button)
    public void search() {
        String trim = mSearchEdit.getText().toString().trim();
        EventBus.getDefault().post(new OrderEvent(OrderEvent.SEARCH_WORDS, trim));
        close();
    }

    @OnClick(R.id.aos_search_clear)
    public void clear() {
        mSearchEdit.setText("");
    }

    @OnClick(R.id.aos_close)
    public void close() {
        finish();
    }

    @Override
    public void refreshSearchData(List<OrderSearchBean> list) {
        ((OrderSearchAdapter) mListView.getAdapter()).setNewData(list, mSearchEdit.getText().toString().trim());
    }

    @OnTextChanged(R.id.aos_search_edit)
    public void onTextChanged(CharSequence s) {
        if (s.toString().length() > 0) {
            mSearchClear.setVisibility(View.VISIBLE);
        } else {
            mSearchClear.setVisibility(View.GONE);
            ((OrderSearchAdapter) mListView.getAdapter()).setNewData(null);
        }
        if (mEmitter == null) {
            return;
        }
        mEmitter.onNext(s.toString().trim());
    }
}
