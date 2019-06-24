package com.hll_sc_app.app.order.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.hll_sc_app.bean.event.BrandSearchEvent;
import com.hll_sc_app.bean.event.GoodsSearchEvent;
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
@Route(path = RouterConfig.ORDER_SEARCH)
public class OrderSearchActivity extends BaseLoadActivity implements IOrderSearchContract.IOrderSearchView {
    public static final String FROM_GOODS = "FROM_GOODS";
    public static final String FROM_BRAND = "FROM_BRAND";
    @BindView(R.id.aos_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.aos_search_clear)
    ImageView mSearchClear;
    @BindView(R.id.aos_list)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mSearchWords;
    @Autowired(name = "object1")
    String mFrom;
    private Disposable mDisposable;
    private IOrderSearchContract.IOrderSearchPresenter mPresenter;
    private ObservableEmitter<String> mEmitter;

    public static void start(String... strings) {
        RouterUtil.goToActivity(RouterConfig.ORDER_SEARCH, strings);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mDisposable = getTextObservable().subscribe(s -> {
            if (!isFromGoods()) {
                // 商品无提示词搜索
                gotoSearch(s);
            }
        });
        mPresenter = OrderSearchPresenter.newInstance();
        mPresenter.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        mDisposable.dispose();
        super.onDestroy();
    }

    private Observable<String> getTextObservable() {
        Observable<String> observable = Observable.create(emitter -> mEmitter = emitter);
        return observable.filter(s -> s.length() > 0).debounce(500, TimeUnit.MILLISECONDS);
    }

    /**
     * 来自商品搜索
     *
     * @return true
     */
    private boolean isFromGoods() {
        return TextUtils.equals(mFrom, FROM_GOODS);
    }

    private void gotoSearch(String keywords) {
        mPresenter.requestSearch(keywords);
    }

    private void initView() {
        String tips = "您可以输入客户名称查找采购商门店";
        String hint = "请输入采购商公司名称";
        int resId = R.drawable.ic_empty_shop_view;
        if (isFromGoods()) {
            tips = "请输入商品名称或者别名进行查询";
            hint = tips;
            resId = R.drawable.ic_search_goods;
        } else if (isFromBrand()) {
            tips = "请输入品牌名称进行查询";
            hint = tips;
        }
        mSearchEdit.setHint(hint);
        mSearchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
            }
            return true;
        });
        OrderSearchAdapter adapter = new OrderSearchAdapter();
        adapter.setEmptyView(EmptyView.newBuilder(this)
            .setTips(tips)
            .setImage(resId)
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
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee),
            UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        updateSearchEdit(mSearchWords);
    }

    /**
     * 来自商品 品牌搜索
     *
     * @return true
     */
    private boolean isFromBrand() {
        return TextUtils.equals(mFrom, FROM_BRAND);
    }

    @OnClick(R.id.aos_search_button)
    public void search() {
        String trim = mSearchEdit.getText().toString().trim();
        if (isFromGoods()) {
            EventBus.getDefault().post(new GoodsSearchEvent(trim));
        } else if (isFromBrand()) {
            EventBus.getDefault().post(new BrandSearchEvent(trim));
        } else {
            EventBus.getDefault().post(new OrderEvent(OrderEvent.SEARCH_WORDS, trim));
        }
        close();
    }

    private void updateSearchEdit(String content) {
        mSearchEdit.setText(content);
        if (content != null) {
            mSearchEdit.setSelection(content.length());
        }
    }

    @OnClick(R.id.aos_close)
    public void close() {
        finish();
    }

    @OnClick(R.id.aos_search_clear)
    public void clear() {
        mSearchEdit.setText("");
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
