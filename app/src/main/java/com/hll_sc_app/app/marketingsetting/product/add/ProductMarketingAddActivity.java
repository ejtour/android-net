package com.hll_sc_app.app.marketingsetting.product.add;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商品促销列表
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST_ADD)
public class ProductMarketingAddActivity extends BaseLoadActivity implements IProductMarketingAddContract.IView {

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_product_add);
        unbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

}
