package com.hll_sc_app.app.goods.add.productattr.brand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

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
    private EmptyView mEmptyView;
    private ProductAttrAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_attr_brand);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        ProductBrandPresenter presenter = ProductBrandPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new ProductAttrAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            String item = (String) adapter.getItem(position);
            if (!TextUtils.isEmpty(item)) {
                showToast("item = " + item);
                finish();
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有可选择的商品品牌").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.img_close, R.id.txt_add})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.txt_add) {
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
