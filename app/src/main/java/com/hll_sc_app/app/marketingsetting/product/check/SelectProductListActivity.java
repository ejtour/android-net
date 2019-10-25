package com.hll_sc_app.app.marketingsetting.product.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingProductAdapter;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ProductSearch;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 显示某一个商品促销选择的活动商品列表
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_SELECT_PRODUCT_LIST)
public class SelectProductListActivity extends AppCompatActivity {
    @BindView(R.id.view_search)
    SearchView mSearchView;
    @BindView(R.id.product_list)
    RecyclerView mProducRecycler;
    @Autowired(name = "productList")
    ArrayList<SkuGoodsBean> mProductList;
    private Unbinder unbinder;

    private MarketingProductAdapter mAdapter;

    public static void start(List<SkuGoodsBean> productList) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_MARKETING_SELECT_PRODUCT_LIST)
                .withSerializable("productList", (ArrayList<SkuGoodsBean>) productList)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_select_product_list);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mProducRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MarketingProductAdapter(null, MarketingProductAdapter.Modal.SHOW);
        mAdapter.setNewData(mProductList);
        mProducRecycler.setAdapter(mAdapter);

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectProductListActivity.this,
                        searchContent, ProductSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mAdapter.setNewData(mProductList);
                } else {
                    List<SkuGoodsBean> searchResults = new ArrayList<>();
                    for (int i = 0; i < mProductList.size(); i++) {
                        if (mProductList.get(i).getProductName().contains(searchContent.trim())) {
                            searchResults.add(mProductList.get(i));
                        }
                    }
                    mAdapter.setNewData(searchResults);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }
}
