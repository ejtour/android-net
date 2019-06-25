package com.hll_sc_app.app.goods.add.productattr.brand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.BrandBackEvent;
import com.hll_sc_app.bean.event.BrandSearchEvent;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品品牌
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR_BRAND, extras = Constant.LOGIN_EXTRA)
public class ProductBrandActivity extends BaseLoadActivity implements ProductBrandContract.IProductAttrBrand {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_searchContent)
    TextView mTxtSearchContent;
    @BindView(R.id.img_searchClear)
    ImageView mImgSearchClear;
    @Autowired(name = "object0")
    String mId;
    private EmptyView mEmptyView;
    private ProductAttrAdapter mAdapter;
    private ProductBrandPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_attr_brand);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = ProductBrandPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new ProductAttrAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            String item = (String) adapter.getItem(position);
            if (!TextUtils.isEmpty(item)) {
                EventBus.getDefault().post(new BrandBackEvent(item, mId));
                finish();
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有可选择的商品品牌").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe
    public void onEvent(BrandSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            showSearchContent(true, name);
        }
    }

    private void showSearchContent(boolean show, String content) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTxtSearchContent.getLayoutParams();
        if (show) {
            mImgSearchClear.setVisibility(View.VISIBLE);
            mTxtSearchContent.setText(content);
            params.weight = 1;
        } else {
            mImgSearchClear.setVisibility(View.GONE);
            mTxtSearchContent.setText(content);
            params.weight = 0;
        }
        mPresenter.queryProductBrandList(getSearchContent());
    }

    /**
     * 获取搜索词
     *
     * @return 搜索词
     */
    private String getSearchContent() {
        if (mImgSearchClear.getVisibility() == View.VISIBLE) {
            return mTxtSearchContent.getText().toString();
        }
        return "";
    }

    @OnClick({R.id.img_close, R.id.txt_add, R.id.rl_search, R.id.img_searchClear})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.img_close) {
            finish();
        } else if (id == R.id.txt_add) {
            RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR_BRAND_ADD);
        } else if (id == R.id.rl_search) {
            OrderSearchActivity.start(getSearchContent(), OrderSearchActivity.FROM_BRAND);
        } else if (id == R.id.img_searchClear) {
            showSearchContent(false, null);
        }
    }

    @Override
    public void showProductBrandList(List<String> list) {
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }

    class ProductAttrAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        ProductAttrAdapter() {
            super(R.layout.item_deposit_product);
        }

        @Override
        protected void convert(BaseViewHolder helper, String string) {
            helper.setText(R.id.txt_productName, string)
                .setGone(R.id.txt_specContent, false)
                .setGone(R.id.img_select, false);
        }
    }
}
