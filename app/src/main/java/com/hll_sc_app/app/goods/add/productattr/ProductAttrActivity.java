package com.hll_sc_app.app.goods.add.productattr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.ProductAttrBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择商品属性
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR, extras = Constant.LOGIN_EXTRA)
public class ProductAttrActivity extends BaseLoadActivity implements ProductAttrContract.IProductAttr {
    public static final String INTENT_TAG = "intent_tag_product_attr";
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable")
    ArrayList<ProductAttrBean> mProductAttrs;
    private ProductAttrAdapter mAdapter;
    private EmptyView mEmptyView;
    private List<String> mLastSelectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_attr);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        setLastSelectId();
        ProductAttrPresenter presenter = ProductAttrPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new ProductAttrAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProductAttrBean item = (ProductAttrBean) adapter.getItem(position);
            if (item != null) {
                item.setSelect(!item.isSelect());
                mAdapter.notifyItemChanged(position);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有可选择的商品属性").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setLastSelectId() {
        if (CommonUtils.isEmpty(mProductAttrs)) {
            return;
        }
        mLastSelectId = new ArrayList<>();
        for (ProductAttrBean bean : mProductAttrs) {
            mLastSelectId.add(bean.getId());
        }
    }

    @OnClick({R.id.img_close, R.id.txt_save})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.txt_save) {
            EventBus.getDefault().post(getSelectProductAttrList());
            finish();
        }
    }

    /**
     * 获取选中的商品属性
     *
     * @return 商品属性列表
     */
    private ArrayList<ProductAttrBean> getSelectProductAttrList() {
        ArrayList<ProductAttrBean> list = new ArrayList<>();
        if (mAdapter != null && !CommonUtils.isEmpty(mAdapter.getData())) {
            for (ProductAttrBean bean : mAdapter.getData()) {
                if (bean.isSelect()) {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    @Override
    public void showProductAttrList(List<ProductAttrBean> list) {
        if (!CommonUtils.isEmpty(mLastSelectId) && !CommonUtils.isEmpty(list)) {
            // 找出上次选择的属性
            for (ProductAttrBean bean : list) {
                if (mLastSelectId.contains(bean.getId())) {
                    bean.setSelect(true);
                }
            }
        }
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
    }

    class ProductAttrAdapter extends BaseQuickAdapter<ProductAttrBean, BaseViewHolder> {

        ProductAttrAdapter() {
            super(R.layout.item_deposit_product);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductAttrBean bean) {
            helper.setText(R.id.txt_productName, bean.getKeyNote())
                .setGone(R.id.txt_specContent, false)
                .getView(R.id.img_select).setSelected(bean.isSelect());
        }
    }
}
