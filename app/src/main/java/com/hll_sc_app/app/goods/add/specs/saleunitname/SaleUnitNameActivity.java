package com.hll_sc_app.app.goods.add.specs.saleunitname;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.SaleUnitNameWrapper;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_SPECS_SALE_UNIT_NAME, extras = Constant.LOGIN_EXTRA)
public class SaleUnitNameActivity extends BaseLoadActivity implements SaleUnitNameContract.ISaleUnitNameAddView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SaleUnitNameAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_unit_name);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        SaleUnitNamePresenter presenter = SaleUnitNamePresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }


    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider),
            UIUtils.dip2px(1)));
        mAdapter = new SaleUnitNameAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }


    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    @Override
    public void showSaleUnitNameList(List<SaleUnitNameWrapper> list) {
        mAdapter.setNewData(list);
    }

    class SaleUnitNameAdapter extends BaseSectionQuickAdapter<SaleUnitNameWrapper, BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        SaleUnitNameAdapter(List<SaleUnitNameWrapper> data) {
            super(R.layout.item_sale_unit_name, R.layout.item_sale_unit_name_title, data);
        }

        @Override
        protected void convertHead(BaseViewHolder helper, SaleUnitNameWrapper item) {
            helper.setText(R.id.txt_title, item.header);
        }

        @Override
        protected void convert(BaseViewHolder helper, SaleUnitNameWrapper item) {
            helper.setText(R.id.txt_content, item.t.getSaleUnitName());
        }
    }
}
