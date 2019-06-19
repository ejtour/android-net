package com.hll_sc_app.app.goods.add.specs.saleunitname;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

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
import com.hll_sc_app.widget.StickyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qdx.indexbarlayout.IndexLayout;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_SPECS_SALE_UNIT_NAME, extras = Constant.LOGIN_EXTRA)
public class SaleUnitNameActivity extends BaseLoadActivity implements SaleUnitNameContract.ISaleUnitNameAddView {
    public static final String INTENT_TAG = "intent_tag";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.index_layout)
    IndexLayout mIndexLayout;
    private SaleUnitNameAdapter mAdapter;
    private StickyItemDecoration mStickyItemDecoration;

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
        mStickyItemDecoration = new StickyItemDecoration();
        mRecyclerView.addItemDecoration(mStickyItemDecoration);
        mAdapter = new SaleUnitNameAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SaleUnitNameWrapper wrapper = (SaleUnitNameWrapper) adapter.getItem(position);
            if (wrapper != null && !wrapper.isHeader) {
                Intent intent = new Intent();
                intent.putExtra(INTENT_TAG, wrapper.t);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mIndexLayout.setCircleDuration(1000);
        mIndexLayout.setCircleColor(0x70000000);
        mIndexLayout.setIndexBarHeightRatio(0.9f);
        List<String> indexList = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            indexList.add(String.valueOf((char) (64 + i)));
        }
        mIndexLayout.getIndexBar().setIndexTextSize(UIUtils.dip2px(12));
        mIndexLayout.getIndexBar().setIndexsList(indexList);
        mIndexLayout.getIndexBar().setIndexChangeListener(s -> {
            if (mAdapter != null) {
                for (int i = 0; i < mAdapter.getData().size(); i++) {
                    SaleUnitNameWrapper wrapper = mAdapter.getItem(i);
                    if (wrapper != null && wrapper.isHeader && TextUtils.equals(s, wrapper.header)) {
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    @Override
    public void showSaleUnitNameList(List<SaleUnitNameWrapper> list, List<String> indexList) {
        mStickyItemDecoration.notifyChanged();
        mAdapter.setNewData(list);

    }

    class SaleUnitNameAdapter extends BaseSectionQuickAdapter<SaleUnitNameWrapper, BaseViewHolder> {

        SaleUnitNameAdapter() {
            super(R.layout.item_sale_unit_name, R.layout.item_sale_unit_name_title, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, SaleUnitNameWrapper item) {
            helper.setText(R.id.txt_saleUnitName, item.t.getSaleUnitName());
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.itemView.setTag(viewType == SECTION_HEADER_VIEW);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            if (getDefItemViewType(position) == SECTION_HEADER_VIEW) {
                setFullSpan(holder);
                convertHead(holder, getItem(position - getHeaderLayoutCount()));
            } else {
                super.onBindViewHolder(holder, position);
            }
        }

        @Override
        protected void convertHead(BaseViewHolder helper, SaleUnitNameWrapper item) {
            helper.setText(R.id.txt_title, item.header);
        }
    }
}
